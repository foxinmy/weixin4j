package com.foxinmy.weixin4j.dispatcher;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.BlankResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.socket.WeixinMessageTransfer;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.ServerToolkits;
import com.foxinmy.weixin4j.xml.MessageTransferHandler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 微信消息分发器
 *
 * @className WeixinMessageDispatcher
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.handler.WeixinMessageHandler
 * @see com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor
 * @see com.foxinmy.weixin4j.dispatcher.WeixinMessageMatcher
 * @see com.foxinmy.weixin4j.dispatcher.MessageHandlerExecutor
 * @see com.foxinmy.weixin4j.dispatcher.BeanFactory
 */
public class WeixinMessageDispatcher {

    private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

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
        this.messageUnmarshaller = new ConcurrentHashMap<Class<? extends WeixinMessage>, Unmarshaller>();
    }

    /**
     * 对消息进行一系列的处理,包括 拦截、匹配、分发等动作
     *
     * @param context
     *            上下文环境
     * @param request
     *            微信请求
     * @param messageTransfer
     *            微信消息 @
     */
    public void doDispatch(final ChannelHandlerContext context, final WeixinRequest request) {
        WeixinMessageTransfer messageTransfer = MessageTransferHandler.parser(request);
        context.channel().attr(ServerToolkits.MESSAGE_TRANSFER_KEY).set(messageTransfer);
        WeixinMessageKey messageKey = defineMessageKey(messageTransfer, request);
        Class<? extends WeixinMessage> targetClass = messageMatcher.match(messageKey);
        WeixinMessage message = messageRead(request.getOriginalContent(), targetClass);
        logger.info("define '{}' matched '{}'", messageKey, targetClass);
        MessageHandlerExecutor handlerExecutor = getHandlerExecutor(context, request, messageKey, message,
                messageTransfer.getNodeNames());
        if (handlerExecutor == null || handlerExecutor.getMessageHandler() == null) {
            noHandlerFound(context, request, message);
            return;
        }
        if (!handlerExecutor.applyPreHandle(request, message)) {
            return;
        }
        Exception exception = null;
        WeixinResponse response = null;
        try {
            response = handlerExecutor.getMessageHandler().doHandle(request, message);
            handlerExecutor.applyPostHandle(request, response, message);
            context.writeAndFlush(response);
        } catch (Exception e) {
            exception = e;
        }
        handlerExecutor.triggerAfterCompletion(request, response, message, exception);
    }

    /**
     * 声明messagekey
     *
     * @param messageTransfer
     *            基础消息
     * @param request
     *            请求信息
     * @return
     */
    protected WeixinMessageKey defineMessageKey(WeixinMessageTransfer messageTransfer, WeixinRequest request) {
        return new WeixinMessageKey(messageTransfer.getMsgType(), messageTransfer.getEventType(),
                messageTransfer.getAccountType());
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
    protected void noHandlerFound(ChannelHandlerContext context, WeixinRequest request, WeixinMessage message) {
        logger.warn("no handler found for {}", request);
        if (alwaysResponse) {
            context.write(BlankResponse.global);
        } else {
            FullHttpResponse response = new DefaultFullHttpResponse(request.getProtocolVersion(), NOT_FOUND);
            HttpUtil.resolveHeaders(response);
            context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
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
     * @see MessageHandlerExecutor @
     */
    protected MessageHandlerExecutor getHandlerExecutor(ChannelHandlerContext context, WeixinRequest request,
            WeixinMessageKey messageKey, WeixinMessage message, Set<String> nodeNames) {
        WeixinMessageHandler[] messageHandlers = getMessageHandlers();
        if (messageHandlers == null) {
            return null;
        }
        logger.info("resolve message handlers '{}'", this.messageHandlerList);
        List<WeixinMessageHandler> matchedMessageHandlers = new ArrayList<WeixinMessageHandler>();
        for (WeixinMessageHandler handler : messageHandlers) {
            if (handler.canHandle(request, message, nodeNames)) {
                matchedMessageHandlers.add(handler);
            }
        }
        if (matchedMessageHandlers.isEmpty()) {
            return null;
        }
        Collections.sort(matchedMessageHandlers, new Comparator<WeixinMessageHandler>() {
            @Override
            public int compare(WeixinMessageHandler m1, WeixinMessageHandler m2) {
                return m2.weight() - m1.weight();
            }
        });
        logger.info("matched message handlers '{}'", matchedMessageHandlers);
        return new MessageHandlerExecutor(context, matchedMessageHandlers.get(0), getMessageInterceptors());
    }

    /**
     * 获取所有的handler
     *
     * @return handler集合
     * @see com.foxinmy.weixin4j.handler.WeixinMessageHandler @
     */
    public WeixinMessageHandler[] getMessageHandlers() {
        if (this.messageHandlers == null) {
            if (messageHandlerPackages != null) {
                List<Class<?>> messageHandlerClass = new ArrayList<Class<?>>();
                for (String packageName : messageHandlerPackages) {
                    messageHandlerClass.addAll(ClassUtil.getClasses(packageName));
                }
                if (beanFactory != null) {
                    for (Class<?> clazz : messageHandlerClass) {
                        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())
                                || !WeixinMessageHandler.class.isAssignableFrom(clazz)) {
                            continue;
                        }
                        try {
                            messageHandlerList.add((WeixinMessageHandler) beanFactory.getBean(clazz));
                        } catch (RuntimeException ex) { // multiple
                            for (Object o : beanFactory.getBeans(clazz).values()) {
                                if (o.getClass() == clazz) {
                                    messageHandlerList.add((WeixinMessageHandler) o);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    for (Class<?> clazz : messageHandlerClass) {
                        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())
                                || !WeixinMessageHandler.class.isAssignableFrom(clazz)) {
                            continue;
                        }
                        try {
                            Constructor<?> ctor = clazz.getDeclaredConstructor();
                            ServerToolkits.makeConstructorAccessible(ctor);
                            messageHandlerList.add((WeixinMessageHandler) ctor.newInstance((Object[]) null));
                        } catch (Exception ex) {
                            throw new RuntimeException(clazz.getName() + " instantiate fail", ex);
                        }
                    }
                }
            }
            if (messageHandlerList != null && !this.messageHandlerList.isEmpty()) {
                this.messageHandlers = this.messageHandlerList
                        .toArray(new WeixinMessageHandler[this.messageHandlerList.size()]);
            }
        }
        return this.messageHandlers;
    }

    /**
     * 获取所有的interceptor
     *
     * @return interceptor集合
     * @ @see com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor
     */
    public WeixinMessageInterceptor[] getMessageInterceptors() {
        if (this.messageInterceptors == null) {
            if (this.messageInterceptorPackages != null) {
                List<Class<?>> messageInterceptorClass = new ArrayList<Class<?>>();
                for (String packageName : messageInterceptorPackages) {
                    messageInterceptorClass.addAll(ClassUtil.getClasses(packageName));
                }
                if (beanFactory != null) {
                    for (Class<?> clazz : messageInterceptorClass) {
                        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())
                                || !WeixinMessageInterceptor.class.isAssignableFrom(clazz)) {
                            continue;
                        }
                        try {
                            messageInterceptorList.add((WeixinMessageInterceptor) beanFactory.getBean(clazz));
                        } catch (RuntimeException ex) { // multiple
                            for (Object o : beanFactory.getBeans(clazz).values()) {
                                if (o.getClass() == clazz) {
                                    messageInterceptorList.add((WeixinMessageInterceptor) o);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    for (Class<?> clazz : messageInterceptorClass) {
                        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())
                                || !WeixinMessageInterceptor.class.isAssignableFrom(clazz)) {
                            continue;
                        }
                        try {
                            Constructor<?> ctor = clazz.getDeclaredConstructor();
                            ServerToolkits.makeConstructorAccessible(ctor);
                            messageInterceptorList.add((WeixinMessageInterceptor) ctor.newInstance((Object[]) null));
                        } catch (Exception ex) {
                            throw new RuntimeException(clazz.getName() + " instantiate fail", ex);
                        }
                    }
                }
            }
            if (this.messageInterceptorList != null && !this.messageInterceptorList.isEmpty()) {
                Collections.sort(messageInterceptorList, new Comparator<WeixinMessageInterceptor>() {
                    @Override
                    public int compare(WeixinMessageInterceptor m1, WeixinMessageInterceptor m2) {
                        return m2.weight() - m1.weight();
                    }
                });
                this.messageInterceptors = this.messageInterceptorList
                        .toArray(new WeixinMessageInterceptor[this.messageInterceptorList.size()]);
            }
        }
        logger.info("resolve message interceptors '{}'", this.messageInterceptorList);
        return this.messageInterceptors;
    }

    /**
     * jaxb读取微信消息
     *
     * @param message
     *            xml消息
     * @param clazz
     *            消息类型
     * @return 消息对象 @
     */
    protected <M extends WeixinMessage> M messageRead(String message, Class<M> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            Source source = new StreamSource(new ByteArrayInputStream(ServerToolkits.getBytesUtf8(message)));
            JAXBElement<M> jaxbElement = getUnmarshaller(clazz).unmarshal(source, clazz);
            return jaxbElement.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * xml消息转换器
     *
     * @param clazz
     *            消息类型
     * @return 消息转换器 @
     */
    protected Unmarshaller getUnmarshaller(Class<? extends WeixinMessage> clazz) {
        Unmarshaller unmarshaller = messageUnmarshaller.get(clazz);
        if (unmarshaller == null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                unmarshaller = jaxbContext.createUnmarshaller();
                messageUnmarshaller.put(clazz, unmarshaller);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return unmarshaller;
    }

    public void setMessageHandlerList(List<WeixinMessageHandler> messageHandlerList) {
        this.messageHandlerList = messageHandlerList;
    }

    public void setMessageInterceptorList(List<WeixinMessageInterceptor> messageInterceptorList) {
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

    public void setMessageInterceptorPackages(String... messageInterceptorPackages) {
        this.messageInterceptorPackages = messageInterceptorPackages;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void registMessageClass(WeixinMessageKey messageKey, Class<? extends WeixinMessage> messageClass) {
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
