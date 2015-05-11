package com.foxinmy.weixin4j.dispatcher;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.bean.BeanFactory;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.ReflectionUtil;

/**
 * 微信消息分发器
 * 
 * @className WeixinMessageDispatcher
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public class WeixinMessageDispatcher {

	private final Logger log = LoggerFactory.getLogger(getClass());

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

	public WeixinMessageDispatcher() {
	}

	public void doDispatch(final ChannelHandlerContext context,
			final WeixinRequest request, final WeixinMessage message)
			throws WeixinException {
		MessageHandlerExecutor handlerExecutor = getHandlerExecutor(context,
				request, message);
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
			log.info(
					"\n=================message response=================\n{}",
					response);
			handlerExecutor.applyPostHandle(request, response, message);
			context.write(response);
		} catch (WeixinException e) {
			dispatchException = e;
		}
		handlerExecutor.triggerAfterCompletion(request, message,
				dispatchException);
	}

	protected void noHandlerFound(ChannelHandlerContext ctx,
			WeixinRequest request, WeixinMessage message) {
		ctx.writeAndFlush(HttpUtil.createHttpResponse(null, NOT_FOUND, null))
				.addListener(ChannelFutureListener.CLOSE);
	}

	protected MessageHandlerExecutor getHandlerExecutor(
			ChannelHandlerContext context, WeixinRequest request,
			WeixinMessage message) throws WeixinException {
		WeixinMessageHandler messageHandler = null;
		WeixinMessageHandler[] messageHandlers = getMessageHandlers();
		if (messageHandlers == null) {
			return null;
		}
		for (WeixinMessageHandler handler : messageHandlers) {
			if (handler.canHandle(request, message)) {
				messageHandler = handler;
				break;
			}
		}
		return new MessageHandlerExecutor(context, messageHandler,
				getMessageInterceptors());
	}

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
}
