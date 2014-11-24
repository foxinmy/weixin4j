package com.foxinmy.weixin4j.qy.startup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.ResourceBundle;

import com.foxinmy.weixin4j.qy.server.WeixinServerInitializer;

/**
 * 微信服务netty启动程序
 * 
 * @className WeixinServerBootstrap
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.7
 * @see
 */
public final class WeixinQyServerBootstrap {

	private final static int port;
	private final static int workerThreads;
	static {
		ResourceBundle netty = ResourceBundle.getBundle("netty");
		port = Integer.parseInt(netty.getString("port"));
		workerThreads = Integer.parseInt(netty.getString("workerThreads"));
	}

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler())
					.childHandler(new WeixinServerInitializer());
			Channel ch = b.bind(port).sync().channel();
			System.err.println("weixin server startup OK:" + port);
			ch.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
