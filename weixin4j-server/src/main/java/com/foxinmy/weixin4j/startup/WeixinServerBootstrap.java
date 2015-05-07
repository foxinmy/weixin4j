package com.foxinmy.weixin4j.startup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import com.foxinmy.weixin4j.model.AesToken;
import com.foxinmy.weixin4j.server.WeixinServerInitializer;

/**
 * 微信netty服务启动程序
 * 
 * @className WeixinServerBootstrap
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.7
 * @see
 */
public final class WeixinServerBootstrap {

	/**
	 * 默认boss线程数,一般设置为cpu的核数
	 */
	public final static int DEFAULT_BOSSTHREADS = 1;
	/**
	 * 默认worker线程数
	 */
	public final static int DEFAULT_WORKERTHREADS = 20;
	/**
	 * 默认服务启动端口
	 */
	public final static int DEFAULT_SERVERPORT = 30000;

	/**
	 * 明文模式
	 * 
	 * @param token
	 *            开发者填写的token
	 */
	public static void startup(String token) {
		startup(DEFAULT_BOSSTHREADS, DEFAULT_WORKERTHREADS, DEFAULT_SERVERPORT,
				new AesToken(token));
	}

	/**
	 * 兼容模式 & 密文模式
	 * 
	 * @param appid
	 *            公众号的唯一ID
	 * @param token
	 *            开发者填写的token
	 * @param aesKey
	 *            消息加密的密钥
	 */
	public static void startup(String appid, String token, String aesKey) {
		startup(DEFAULT_BOSSTHREADS, DEFAULT_WORKERTHREADS, DEFAULT_SERVERPORT,
				new AesToken(appid, token, aesKey));
	}

	public static void startup(int bossThreads, int workerThreads,
			int serverPort, AesToken aesToken) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(bossThreads);
		EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler())
					.childHandler(new WeixinServerInitializer(aesToken));
			Channel ch = b.bind(serverPort).sync().channel();
			System.err.println("weixin server startup OK:" + serverPort);
			ch.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
