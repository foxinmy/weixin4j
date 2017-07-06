package com.foxinmy.weixin4j.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.foxinmy.weixin4j.dispatcher.BeanFactory;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;
import com.foxinmy.weixin4j.util.AesToken;

/**
 * 监听器方式新线程启动微信服务(适用于与web集成
 *
 * @className AbstractWeixinServerStartupListener
 * @author jinyu
 * @date Jun 28, 2017
 * @since JDK 1.6
 * @see
 */
public abstract class AbstractWeixinServerStartupListener implements ServletContextListener {
    /**
     * 服务监听端口，目前微信只支持80端口,可以考虑用nginx做转发到此端口
     *
     * @return
     */
    protected int getPort() {
        return 30000;
    }

    /**
     * 明文模式:String aesToken = ""; 密文模式:AesToken aesToken = new
     * AesToken("公众号appid", "公众号token","公众号加密/解密消息的密钥");
     */
    protected AesToken getToken() {
        return new AesToken("weixin4j");
    }

    /**
     * 处理微信消息的全限包名(也可通过addHandler方式一个一个添加)
     */
    abstract String[] getHandlerToScan();

    /**
     * 拦截微信消息的全限包名(也可通过addInterceptor方式一个一个添加)
     *
     * @return
     */
    public String[] getInterceptorToScan() {
        return null;
    }

    /**
     * bean容器
     *
     * @param sc
     *            servlet上下文
     * @return
     */
    abstract BeanFactory getBeanFactory(ServletContext sc);

    /**
     * 当没有匹配到消息处理时输出空白回复(公众号不会出现「该公众号无法提供服务的提示」)
     *
     * @return
     */
    protected boolean getOpenAlwaysResponse() {
        return true;
    }

    /**
     * 打开调试回复
     *
     * @return
     */
    protected boolean getOpenDebugResponse() {
        return true;
    }

    /**
     * 服务启动
     */
    private WeixinServerBootstrap bootstrap;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final int port = getPort();
        final AesToken aesToken = getToken();
        final String[] handlerPackage = getHandlerToScan();
        final String[] interceptorPackage = getInterceptorToScan();
        final BeanFactory beanFactory = getBeanFactory(sce.getServletContext());
        final boolean openAlwaysResponse = getOpenAlwaysResponse();
        final boolean openDebugResponse = getOpenDebugResponse();
        /**
         * 线程启动服务
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                bootstrap = new WeixinServerBootstrap(aesToken) // 指定开发者token信息
                        .handlerPackagesToScan(handlerPackage); // 扫描处理消息的包
                if (interceptorPackage != null) // 扫描拦截消息的包
                    bootstrap.interceptorPackagesToScan(interceptorPackage);
                if (beanFactory != null)
                    bootstrap.resolveBeanFactory(beanFactory); // 声明处理消息类由Bean容器去实例化
                if (openDebugResponse)
                    bootstrap.addHandler(DebugMessageHandler.global); // 当没有匹配到消息处理时输出调试信息，开发环境打开
                if (openAlwaysResponse)
                    bootstrap.openAlwaysResponse(); // 是否总是返回响应，正式环境打开
                bootstrap.startup(port); // 绑定服务的端口号，即对外暴露(微信服务器URL地址)的服务端口
            }
        }).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /**
         * 关闭服务
         */
        bootstrap.shutdown(true);
    }
}
