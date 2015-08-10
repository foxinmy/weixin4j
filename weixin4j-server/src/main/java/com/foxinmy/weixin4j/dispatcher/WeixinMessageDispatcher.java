package com.foxinmy.weixin4j.dispatcher;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.BlankResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.socket.WeixinMessageTransfer;
import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.ReflectionUtil;

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
	 * 消息转换
	 */
	private Map<Class<? extends WeixinMessage>, Unmarshaller> messageUnmarshaller;
	/**
	 * 是否总是响应请求,如未匹配到MessageHandler时回复空白消息
	 */
	private boolean alwaysResponse;

	public WeixinMessageDispatcher() {
		this(new DefaultMessageMatcher());
	}

	public WeixinMessageDispatcher(WeixinMessageMatcher messageMatcher) {
		this.messageMatcher = messageMatcher;
		this.messageUnmarshaller = new HashMap<Class<? extends WeixinMessage>, Unmarshaller>();
	}

	/**
	 * 对消息进行一系列的处理,包括 拦截、匹配、分发等动作
	 * 
	 * @param context
	 *            上下文环境
	 * @param request
	 *            微信请求
	 * @param messageTransfer
	 *            微信消息
	 * @throws WeixinException
	 */
	public void doDispatch(final ChannelHandlerContext context,
			final WeixinRequest request,
			final WeixinMessageTransfer messageTransfer) throws WeixinException {
		WeixinMessageKey messageKey = defineMessageKey(
				messageTransfer.getMsgType(), messageTransfer.getEventType(),
				messageTransfer.getAccountType());
		Class<? extends WeixinMessage> targetClass = messageMatcher
				.match(messageKey);
		Object message = messageRead(request.getOriginalContent(), targetClass);
		logger.info("define '{}' matched '{}'", messageKey, targetClass);
		MessageHandlerExecutor handlerExecutor = getHandlerExecutor(context,
				request, messageKey, message, messageTransfer.getNodeNames());
		if (handlerExecutor == null
				|| handlerExecutor.getMessageHandler() == null) {
			noHandlerFound(context, request, message);
			return;
		}
		if (!handlerExecutor.applyPreHandle(request, message)) {
			return;
		}
		Exception exception = null;
		WeixinResponse response = null;
		try {
			response = handlerExecutor.getMessageHandler().doHandle(request,
					message, messageTransfer.getNodeNames());
			handlerExecutor.applyPostHandle(request, response, message);
			context.write(response);
		} catch (Exception e) {
			exception = e;
		}
		handlerExecutor.triggerAfterCompletion(request, response, message,
				exception);
	}

	/**
	 * 声明messagekey
	 * 
	 * @param messageType
	 *            消息类型
	 * @param eventType
	 *            事件类型
	 * @param accountType
	 *            账号类型
	 * @return
	 */
	protected WeixinMessageKey defineMessageKey(String messageType,
			String eventType, AccountType accountType) {
		return new WeixinMessageKey(messageType, eventType, accountType);
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
		if (alwaysResponse) {
			context.write(BlankResponse.global);
		} else {
			context.writeAndFlush(HttpUtil.createHttpResponse(NOT_FOUND))
					.addListener(ChannelFutureListener.CLOSE);
		}
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
	 * @param nodeNames
	 *            节点名称集合
	 * @return MessageHandlerExecutor
	 * @see MessageHandlerExecutor
	 * @throws WeixinException
	 */
	protected MessageHandlerExecutor getHandlerExecutor(
			ChannelHandlerContext context, WeixinRequest request,
			WeixinMessageKey messageKey, Object message, Set<String> nodeNames)
			throws WeixinException {
		WeixinMessageHandler[] messageHandlers = getMessageHandlers();
		if (messageHandlers == null) {
			return null;
		}
		WeixinMessageHandler messageHandler = null;
		for (WeixinMessageHandler handler : messageHandlers) {
			if (handler.canHandle(request, message, nodeNames)) {
				messageHandler = handler;
				break;
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
						messageHandlerList.add(0,
								(WeixinMessageHandler) beanFactory
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
							messageHandlerList.add(0,
									(WeixinMessageHandler) ctor
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
						messageInterceptorList.add(0,
								(WeixinMessageInterceptor) beanFactory
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
							messageInterceptorList.add(0,
									(WeixinMessageInterceptor) ctor
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
	protected <M extends WeixinMessage> M messageRead(String message,
			Class<M> clazz) throws WeixinException {
		if (clazz == null) {
			return null;
		}
		try {
			Source source = new StreamSource(new ByteArrayInputStream(
					message.getBytes(Consts.UTF_8)));
			JAXBElement<M> jaxbElement = getUnmarshaller(clazz).unmarshal(
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
	protected Unmarshaller getUnmarshaller(Class<? extends WeixinMessage> clazz)
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

	public void registMessageClass(WeixinMessageKey messageKey,
			Class<? extends WeixinMessage> messageClass) {
		messageMatcher.regist(messageKey, messageClass);
	}

	public WeixinMessageMatcher getMessageMatcher() {
		return this.messageMatcher;
	}

	/**
	 * 打开总是响应开关,如未匹配到MessageHandler时回复空白消息
	 */
	public void openAlwaysResponse() {
		this.alwaysResponse = true;
	}
}
