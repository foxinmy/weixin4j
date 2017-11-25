package com.foxinmy.weixin4j.server.test;

import io.netty.channel.ChannelHandlerContext;

import org.springframework.context.ApplicationContext;

import com.foxinmy.weixin4j.dispatcher.BeanFactory;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.handler.MessageHandlerAdapter;
import com.foxinmy.weixin4j.handler.MultipleMessageHandlerAdapter;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.message.TextMessage;
import com.foxinmy.weixin4j.message.VoiceMessage;
import com.foxinmy.weixin4j.mp.event.ScanEventMessage;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.spring.SpringBeanFactory;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;

/**
 * 服务启动测试类
 *
 * @className MessageServerStartup
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 * @see
 */
public class MessageServerStartup {

    // 公众号ID
    final String weixinId = "wx4ab8f8de58159a57";
    // 开发者token
    final String token = "weixin4j";
    // AES密钥(安全模式)
    final String aesKey = "";

    /**
     * 调试输出用户发来的消息
     *
     */
    public void test1() {
        // 明文模式
        new WeixinServerBootstrap(token).addHandler(DebugMessageHandler.global).startup();
        // 密文模式
        // new WeixinServerBootstrap(weixinId, token, aesKey).addHandler(
        // DebugMessageHandler.global).startup();
    }

    /**
     * 针对特定消息类型
     *
     */
    public void test2() {
        // 针对文本消息回复
        WeixinMessageHandler textMessageHandler = new MessageHandlerAdapter<TextMessage>() {
            @Override
            protected WeixinResponse doHandle0(TextMessage message) {
                return new TextResponse("HelloWorld!");
            }
        };
        // 针对语音消息回复
        WeixinMessageHandler voiceMessageHandler = new MessageHandlerAdapter<VoiceMessage>() {
            @Override
            protected WeixinResponse doHandle0(VoiceMessage message) {
                return new TextResponse("HelloWorld!");
            }
        };
        // 当消息类型为文本(text)或者语音时回复「HelloWorld」, 否则回复调试消息
        new WeixinServerBootstrap(weixinId, token, aesKey)
                .addHandler(textMessageHandler, voiceMessageHandler, DebugMessageHandler.global).startup();
    }

    /**
     * 多种消息类型处理
     *
     */
    public void test3() {
        @SuppressWarnings("unchecked")
        MultipleMessageHandlerAdapter messageHandler = new MultipleMessageHandlerAdapter(ScanEventMessage.class,
                TextMessage.class) {
			@Override
			public WeixinResponse doHandle(WeixinRequest request,
					WeixinMessage message) {
				 return new TextResponse("处理了扫描和文字消息");
			}
        };
        new WeixinServerBootstrap(token).addHandler(messageHandler, DebugMessageHandler.global).startup();
    }

    /**
     * 扫描包添加handler
     *
     * @
     */
    public void test4() {
        // handler处理所在的包名(子包也会扫描)
        String packageToScan = "com.foxinmy.weixin4j.handler";
        // handler默认使用 Class.newInstance
        // 方式实例化,如果handler中含有service等类需要注入,可以声明一个BeanFactory,如SpringBeanFactory
        ApplicationContext applicationContext = null; // spring容器
        BeanFactory beanFactory = new SpringBeanFactory(applicationContext);
        new WeixinServerBootstrap(token).handlerPackagesToScan(packageToScan).openAlwaysResponse()
                .resolveBeanFactory(beanFactory).startup();
    }

    /**
     * 拦截器应用
     *
     * @
     */
    public void test5() {
        // 拦截所有请求
        WeixinMessageInterceptor interceptor = new WeixinMessageInterceptor() {
            @Override
            public boolean preHandle(ChannelHandlerContext context, WeixinRequest request, WeixinMessage message,
                    WeixinMessageHandler handler) {
                context.writeAndFlush(new TextResponse("所有消息被拦截了！"));
                return false;
            }

            @Override
            public void postHandle(ChannelHandlerContext context, WeixinRequest request, WeixinResponse response,
                    WeixinMessage message, WeixinMessageHandler handler) {
                System.err.println("preHandle返回为true,执行handler后");
            }

            @Override
            public void afterCompletion(ChannelHandlerContext context, WeixinRequest request, WeixinResponse response,
                    WeixinMessage message, WeixinMessageHandler handler, Exception exception) {
                System.err.println("请求处理完毕");
            }

            @Override
            public int weight() {
                return 0;
            }
        };
        new WeixinServerBootstrap(token).addInterceptor(interceptor).openAlwaysResponse().startup();
    }

    /**
     * main方法入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new MessageServerStartup().test1();
    }
}
