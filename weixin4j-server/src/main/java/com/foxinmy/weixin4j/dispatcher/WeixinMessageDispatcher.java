package com.foxinmy.weixin4j.dispatcher;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.MessageHandlerAdapter;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.messagekey.DefaultMessageKeyDefiner;
import com.foxinmy.weixin4j.messagekey.WeixinMessageKeyDefiner;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.ReflectionUtil;
import com.foxinmy.weixin4j.xml.CruxMessageHandler;

/**
 * 微信消息分发器
 * 
 * @className WeixinMessageDispatcher
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.handler.WeixinMessageHandler
 * @see com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor
 * @see com.foxinmy.weixin4j.dispatcher.WeixinMessageMatcher
 * @see com.foxinmy.weixin4j.messagekey.WeixinMessageKeyDefiner
 * @see com.foxinmy.weixin4j.dispatcher.MessageHandlerExecutor
 * @see com.foxinmy.weixin4j.dispatcher.BeanFactory
 */
public class WeixinMessageDispatcher {

	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	/**
	 * 消息处理器
	 */
	private List<WeixinMessageHandler> messageHandlerList;
	private WeixinMessageHandler[] messageHandlers;
	/**
	 * 消息处理器所在的包
	 */
	private String[] messageHandlerPackages;

	/**
	 * 消息拦截器
	 */
	private List<WeixinMessageInterceptor> messageInterceptorList;
	private WeixinMessageInterceptor[] messageInterceptors;
	/**
	 * 消息拦截器所在的包
	 */
	private String[] messageInterceptorPackages;

	/**
	 * Bean构造
	 */
	private BeanFactory beanFactory;

	/**
	 * 消息匹配
	 */
	private WeixinMessageMatcher messageMatcher;
	/**
	 * 消息key
	 */
	private WeixinMessageKeyDefiner messageKeyDefiner;
	/**
	 * 消息转换
	 */
	private Map<Class<?>, Unmarshaller> messageUnmarshaller;

	public WeixinMessageDispatcher() {
		this(new DefaultMessageKeyDefiner());
	}

	public WeixinMessageDispatcher(WeixinMessageKeyDefiner messageKeyDefiner) {
		messageMatcher = new WeixinMessageMatcher(messageKeyDefiner);
		messageUnmarshaller = new HashMap<Class<?>, Unmarshaller>();
		this.messageKeyDefiner = messageKeyDefiner;
	}

	/**
	 * 对消息进行一系列的处理,包括 拦截、匹配、分发等动作
	 * 
	 * @param context
	 *            上下文环境
	 * @param request
	 *            微信请求
	 * @param messageKey
	 *            消息的key
	 * @throws WeixinException
	 */
	public void doDispatch(final ChannelHandlerContext context,
			final WeixinRequest request, final CruxMessageHandler messageHandler)
			throws WeixinException {
		String messageKey = messageKeyDefiner.defineMessageKey(
				messageHandler.getMsgType(), messageHandler.getEventType(),
				messageHandler.getAccountType());
		Class<?> targetClass = messageMatcher.find(messageKey);
		Object message = request.getOriginalContent();
		if (targetClass != null) {
			message = messageRead(request.getOriginalContent(), targetClass);
		}
		logger.info("define [{}] messageKey matched [{}] unmarshal to {}",
				messageKey, targetClass, message);
		MessageHandlerExecutor handlerExecutor = getHandlerExecutor(context,
				request, messageKey, message);
		if (handlerExecutor == null
				|| handlerExecutor.getMessageHandler() == null) {
			noHandlerFound(context, request, message);
			return;
		}
		if (!handlerExecutor.applyPreHandle(request, message)) {
			return;
		}
		WeixinException dispatchException = null;
		try {
			WeixinResponse response = handlerExecutor.getMessageHandler()
					.doHandle(request, message);
			handlerExecutor.applyPostHandle(request, response, message);
			context.write(response);
		} catch (WeixinException e) {
			dispatchException = e;
		}
		handlerExecutor.triggerAfterCompletion(request, message,
				dispatchException);
	}

	/**
	 * 未匹配到handler时触发
	 * 
	 * @param context
	 *            上下文环境
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 */
	protected void noHandlerFound(ChannelHandlerContext context,
			WeixinRequest request, Object message) {
		context.writeAndFlush(
				HttpUtil.createHttpResponse(null, NOT_FOUND, null))
				.addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * MessageHandlerExecutor
	 * 
	 * @param context
	 *            上下文环境
	 * @param request
	 *            微信请求
	 * @param messageKey
	 *            消息的key
	 * @param message
	 *            微信消息
	 * @return MessageHandlerExecutor
	 * @see com.foxinmy.weixin4j.dispatcher.MessageHandlerExecutor
	 * @throws WeixinException
	 */
	protected MessageHandlerExecutor getHandlerExecutor(
			ChannelHandlerContext context, WeixinRequest request,
			String messageKey, Object message) throws WeixinException {
		WeixinMessageHandler messageHandler = null;
		WeixinMessageHandler[] messageHandlers = getMessageHandlers();
		if (messageHandlers == null) {
			return null;
		}
		for (WeixinMessageHandler handler : messageHandlers) {
			if (handler instanceof MessageHandlerAdapter) {
				Class<?> genericType = genericTypeRead(handler);
				if (!messageMatcher.match(genericType)) {
					message = messageRead(request.getOriginalContent(),
							genericType);
					messageMatcher.regist(messageKey, genericType);
				}
				if (genericType == message.getClass()
						&& handler.canHandle(request, message)) {
					messageHandler = handler;
					break;
				}
			}
		}
		if (messageHandler == null) {
			for (WeixinMessageHandler handler : messageHandlers) {
				if (!(handler instanceof MessageHandlerAdapter)
						&& handler.canHandle(request, message)) {
					messageHandler = handler;
					break;
				}
			}
		}
		return new MessageHandlerExecutor(context, messageHandler,
				getMessageInterceptors());
	}

	/**
	 * 获取所有的handler
	 * 
	 * @return handler集合
	 * @see com.foxinmy.weixin4j.handler.WeixinMessageHandler
	 * @throws WeixinException
	 */
	public WeixinMessageHandler[] getMessageHandlers() throws WeixinException {
		if (this.messageHandlers == null) {
			if (messageHandlerPackages != null) {
				List<Class<?>> messageHandlerClass = new LinkedList<Class<?>>();
				for (String packageName : messageHandlerPackages) {
					messageHandlerClass.addAll(ClassUtil
							.getClasses(packageName));
				}
				if (beanFactory != null) {
					for (Class<?> clazz : messageHandlerClass) {
						messageHandlerList
								.add((WeixinMessageHandler) beanFactory
										.getBean(clazz));
					}
				} else {
					for (Class<?> clazz : messageHandlerClass) {
						if (clazz.isInterface()
								|| Modifier.isAbstract(clazz.getModifiers())) {
							continue;
						}
						try {
							Constructor<?> ctor = clazz
									.getDeclaredConstructor();
							ReflectionUtil.makeAccessible(ctor);
							messageHandlerList.add((WeixinMessageHandler) ctor
									.newInstance((Object[]) null));
						} catch (Exception ex) {
							throw new WeixinException(clazz.getName()
									+ " instantiate fail", ex);
						}
					}
				}
			}
			if (messageHandlerList != null
					&& !this.messageHandlerList.isEmpty()) {
				this.messageHandlers = this.messageHandlerList
						.toArray(new WeixinMessageHandler[this.messageHandlerList
								.size()]);
			}
		}
		return this.messageHandlers;

	}

	/**
	 * 获取所有的interceptor
	 * 
	 * @return interceptor集合
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor
	 */
	public WeixinMessageInterceptor[] getMessageInterceptors()
			throws WeixinException {
		if (this.messageInterceptors == null) {
			if (this.messageInterceptorPackages != null) {
				List<Class<?>> messageInterceptorClass = new LinkedList<Class<?>>();
				for (String packageName : messageInterceptorPackages) {
					messageInterceptorClass.addAll(ClassUtil
							.getClasses(packageName));
				}
				if (beanFactory != null) {
					for (Class<?> clazz : messageInterceptorClass) {
						messageInterceptorList
								.add((WeixinMessageInterceptor) beanFactory
										.getBean(clazz));
					}
				} else {
					for (Class<?> clazz : messageInterceptorClass) {
						if (clazz.isInterface()
								|| Modifier.isAbstract(clazz.getModifiers())) {
							continue;
						}
						try {
							Constructor<?> ctor = clazz
									.getDeclaredConstructor();
							ReflectionUtil.makeAccessible(ctor);
							messageInterceptorList
									.add((WeixinMessageInterceptor) ctor
											.newInstance((Object[]) null));
						} catch (Exception ex) {
							throw new WeixinException(clazz.getName()
									+ " instantiate fail", ex);
						}
					}
				}
			}
			if (this.messageInterceptorList != null
					&& !this.messageInterceptorList.isEmpty()) {
				this.messageInterceptors = this.messageInterceptorList
						.toArray(new WeixinMessageInterceptor[this.messageInterceptorList
								.size()]);
			}
		}
		return this.messageInterceptors;
	}

	/**
	 * jaxb读取微信消息
	 * 
	 * @param message
	 *            xml消息
	 * @param clazz
	 *            消息类型
	 * @return 消息对象
	 * @throws WeixinException
	 */
	protected Object messageRead(String message, Class<?> clazz)
			throws WeixinException {
		try {
			Source source = new StreamSource(new ByteArrayInputStream(
					message.getBytes(Consts.UTF_8)));
			JAXBElement<?> jaxbElement = getUnmarshaller(clazz).unmarshal(
					source, clazz);
			return jaxbElement.getValue();
		} catch (JAXBException e) {
			throw new WeixinException(e);
		}
	}

	/**
	 * xml消息转换器
	 * 
	 * @param clazz
	 *            消息类型
	 * @return 消息转换器
	 * @throws WeixinException
	 */
	protected Unmarshaller getUnmarshaller(Class<?> clazz)
			throws WeixinException {
		Unmarshaller unmarshaller = messageUnmarshaller.get(clazz);
		if (unmarshaller == null) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				unmarshaller = jaxbContext.createUnmarshaller();
				messageUnmarshaller.put(clazz, unmarshaller);
			} catch (JAXBException e) {
				throw new WeixinException(e);
			}
		}
		return unmarshaller;
	}

	/**
	 * 获得泛型类型
	 * 
	 * @param object
	 * @return
	 */
	private Class<?> genericTypeRead(Object object) {
		Class<?> clazz = null;
		Type type = object.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType ptype = ((ParameterizedType) type);
			Type[] args = ptype.getActualTypeArguments();
			clazz = (Class<?>) args[0];
		}
		return clazz;
	}

	public void setMessageHandlerList(
			List<WeixinMessageHandler> messageHandlerList) {
		this.messageHandlerList = messageHandlerList;
	}

	public void setMessageInterceptorList(
			List<WeixinMessageInterceptor> messageInterceptorList) {
		this.messageInterceptorList = messageInterceptorList;
	}

	public String[] getMessageHandlerPackages() {
		return messageHandlerPackages;
	}

	public String[] getMessageInterceptorPackages() {
		return messageInterceptorPackages;
	}

	public void setMessageHandlerPackages(String... messageHandlerPackages) {
		this.messageHandlerPackages = messageHandlerPackages;
	}

	public void setMessageInterceptorPackages(
			String... messageInterceptorPackages) {
		this.messageInterceptorPackages = messageInterceptorPackages;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void registMessageMatch(String messageType, String eventType,
			AccountType accountType, Class<?> messageClass) {
		registMessageMatch(messageKeyDefiner.defineMessageKey(messageType,
				eventType, accountType), messageClass);
	}

	public void registMessageMatch(String messageKey, Class<?> messageClass) {
		messageMatcher.regist(messageKey, messageClass);
	}

	public WeixinMessageMatcher getMessageMatcher() {
		return this.messageMatcher;
	}

	public WeixinMessageKeyDefiner getMessageKeyDefiner() {
		return this.messageKeyDefiner;
	}
}
