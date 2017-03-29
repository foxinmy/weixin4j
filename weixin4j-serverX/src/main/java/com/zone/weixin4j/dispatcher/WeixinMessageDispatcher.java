package com.zone.weixin4j.dispatcher;

import com.zone.weixin4j.annotation.WxMessageHandler;
import com.zone.weixin4j.annotation.WxMessageInterceptor;
import com.zone.weixin4j.exception.HttpResponseException;
import com.zone.weixin4j.exception.MessageInterceptorException;
import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.handler.DebugMessageHandler;
import com.zone.weixin4j.handler.WeixinMessageHandler;
import com.zone.weixin4j.interceptor.WeixinMessageInterceptor;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.BlankResponse;
import com.zone.weixin4j.response.WeixinResponse;
import com.zone.weixin4j.service.WeiXin4jContextAware;
import com.zone.weixin4j.service.context.WeiXin4jContextAwareImpl;
import com.zone.weixin4j.socket.WeixinMessageTransfer;
import com.zone.weixin4j.util.ServerToolkits;
import com.zone.weixin4j.xml.MessageTransferHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信消息分发器
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className WeixinMessageDispatcher
 * @date 2015年5月7日
 * @updateBy Yz(174975857@qq.com)
 * @since JDK 1.6
 */
@Component
@DependsOn({"weiXin4jContextAware"})
public class WeixinMessageDispatcher {

    @Autowired
    private WeiXin4jContextAware weiXin4jContextAware;

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * 消息处理器
     */
    private List<WeixinMessageHandler> messageHandlerList = new ArrayList<WeixinMessageHandler>();
    private WeixinMessageHandler[] messageHandlers;

    /**
     * 消息拦截器
     */
    private List<WeixinMessageInterceptor> messageInterceptorList = new ArrayList<WeixinMessageInterceptor>();
    private WeixinMessageInterceptor[] messageInterceptors;

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

    @PostConstruct
    public void init() {
        try {
            this.getMessageHandlers();
            this.getMessageInterceptors();
            if (weiXin4jContextAware.isOpenAlwaysResponse()) {
                this.openAlwaysResponse();
            }
            if (weiXin4jContextAware.isUseDebugMessageHandler()) {
                if(null == messageHandlerList){
                    messageHandlerList = new ArrayList<WeixinMessageHandler>();
                    messageHandlerList.add(DebugMessageHandler.global);
                }
            }
            this.messageMatcher = weiXin4jContextAware.getWeixinMessageMatcher() == null ? new DefaultMessageMatcher() : weiXin4jContextAware.getWeixinMessageMatcher();
            this.messageUnmarshaller = new ConcurrentHashMap<Class<? extends WeixinMessage>, Unmarshaller>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对消息进行一系列的处理,包括 拦截、匹配、分发等动作
     *
     * @param request 微信请求
     * @throws WeixinException
     */
    public WeixinResponse doDispatch(final WeixinRequest request) throws WeixinException, HttpResponseException, MessageInterceptorException {
        WeixinMessageTransfer messageTransfer = MessageTransferHandler.parser(request);
        WeiXin4jContextAwareImpl.getWeixinMessageTransfer().set(messageTransfer);
        WeixinMessageKey messageKey = defineMessageKey(messageTransfer, request);
        Class<? extends WeixinMessage> targetClass = messageMatcher.match(messageKey);
        WeixinMessage message = messageRead(request.getOriginalContent(), targetClass);
        logger.info(String.format("define %s matched %s", messageKey, targetClass));
        MessageHandlerExecutor handlerExecutor = getHandlerExecutor(request, messageKey, message, messageTransfer.getNodeNames());
        if (handlerExecutor == null || handlerExecutor.getMessageHandler() == null) {
            return noHandlerFound(request, message);
        }
        if (!handlerExecutor.applyPreHandle(request, message)) {
            throw new MessageInterceptorException(" Interceptor Not Accept !! ");
        }
        Exception exception = null;
        WeixinResponse response = null;
        try {
            response = handlerExecutor.getMessageHandler().doHandle(request, message, messageTransfer.getNodeNames());
            handlerExecutor.applyPostHandle(request, response, message);
        } catch (Exception e) {
            exception = e;
        }
        handlerExecutor.triggerAfterCompletion(request, response, message, exception);
        return response;
    }

    /**
     * 声明messagekey
     *
     * @param messageTransfer 基础消息
     * @param request         请求信息
     * @return
     */
    protected WeixinMessageKey defineMessageKey(
            WeixinMessageTransfer messageTransfer, WeixinRequest request) {
        return new WeixinMessageKey(messageTransfer.getMsgType(),
                messageTransfer.getEventType(),
                messageTransfer.getAccountType());
    }

    /**
     * 未匹配到handler时触发
     *
     * @param request 微信请求
     * @param message 微信消息
     */
    protected WeixinResponse noHandlerFound(WeixinRequest request, WeixinMessage message) throws HttpResponseException {
        logger.warn(String.format("no handler found for %s", request));
        if (alwaysResponse) {
            return BlankResponse.global;
        } else {
            throw new HttpResponseException(HttpResponseException.HttpResponseStatus.NOT_FOUND);
        }
    }

    /**
     * MessageHandlerExecutor
     *
     * @param request    微信请求
     * @param messageKey 消息的key
     * @param message    微信消息
     * @param nodeNames  节点名称集合
     * @return MessageHandlerExecutor
     * @throws WeixinException
     * @see MessageHandlerExecutor
     */
    protected MessageHandlerExecutor getHandlerExecutor(
            WeixinRequest request,
            WeixinMessageKey messageKey, WeixinMessage message,
            Set<String> nodeNames) throws WeixinException {
        WeixinMessageHandler[] messageHandlers = getMessageHandlers();
        if (messageHandlers == null) {
            return null;
        }
        logger.info(String.format("resolve message handlers %s", this.messageHandlerList));
        List<WeixinMessageHandler> matchedMessageHandlers = new ArrayList<WeixinMessageHandler>();
        for (WeixinMessageHandler handler : messageHandlers) {
            if (handler.canHandle(request, message, nodeNames)) {
                matchedMessageHandlers.add(handler);
            }
        }
        if (matchedMessageHandlers.isEmpty()) {
            return null;
        }
        Collections.sort(matchedMessageHandlers,
                new Comparator<WeixinMessageHandler>() {
                    @Override
                    public int compare(WeixinMessageHandler m1,
                                       WeixinMessageHandler m2) {
                        return m2.weight() - m1.weight();
                    }
                });
        logger.info(String.format("matched message handlers %s", matchedMessageHandlers));
        return new MessageHandlerExecutor(matchedMessageHandlers.get(0), getMessageInterceptors());
    }

    /**
     * 获取所有的handler
     *
     * @return handler集合
     * @throws WeixinException
     * @see com.zone.weixin4j.handler.WeixinMessageHandler
     */
    public WeixinMessageHandler[] getMessageHandlers() throws WeixinException {
        if (this.messageHandlers == null) {
            String[] beanNamesForAnnotation = this.weiXin4jContextAware.getApplicationContext().getBeanNamesForAnnotation(WxMessageHandler.class);
            for (String str : beanNamesForAnnotation) {
                Object bean = this.weiXin4jContextAware.getApplicationContext().getBean(str);
                if (bean instanceof WeixinMessageHandler) {
                    this.messageHandlerList.add((WeixinMessageHandler) this.weiXin4jContextAware.getApplicationContext().getBean(str));
                }
            }
        }
        if (messageHandlerList != null && !this.messageHandlerList.isEmpty()) {
            this.messageHandlers = this.messageHandlerList.toArray(new WeixinMessageHandler[this.messageHandlerList.size()]);
        }
        return this.messageHandlers;
    }

    /**
     * 获取所有的interceptor
     *
     * @return interceptor集合
     * @throws WeixinException
     */
    public WeixinMessageInterceptor[] getMessageInterceptors()
            throws WeixinException {
        if (this.messageInterceptors == null) {
            String[] beanNamesForAnnotation = this.weiXin4jContextAware.getApplicationContext().getBeanNamesForAnnotation(WxMessageInterceptor.class);
            for (String str : beanNamesForAnnotation) {
                Object bean = this.weiXin4jContextAware.getApplicationContext().getBean(str);
                if (bean instanceof WeixinMessageInterceptor) {
                    this.messageInterceptorList.add((WeixinMessageInterceptor) this.weiXin4jContextAware.getApplicationContext().getBean(str));
                }
            }
        }
        if (this.messageInterceptorList != null && !this.messageInterceptorList.isEmpty()) {
            Collections.sort(messageInterceptorList,
                    new Comparator<WeixinMessageInterceptor>() {
                        @Override
                        public int compare(WeixinMessageInterceptor m1, WeixinMessageInterceptor m2) {
                            return m2.weight() - m1.weight();
                        }
                    });
            this.messageInterceptors = this.messageInterceptorList.toArray(new WeixinMessageInterceptor[this.messageInterceptorList.size()]);
        }
        logger.info(String.format("resolve message interceptors %s", this.messageInterceptorList));
        return this.messageInterceptors;
    }

    /**
     * jaxb读取微信消息
     *
     * @param message xml消息
     * @param clazz   消息类型
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
                    ServerToolkits.getBytesUtf8(message)));
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
     * @param clazz 消息类型
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
