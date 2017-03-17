package com.foxinmy.weixin4j.http.support.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;

/**
 * 使用Netty4
 * 
 * @className Netty4HttpClientFactory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月30日
 * @since JDK 1.6
 */
public class Netty4HttpClientFactory extends HttpClientFactory {
	private volatile Bootstrap bootstrap;
	private EventLoopGroup eventLoopGroup;
	private Map<ChannelOption<?>, ?> options;

	public Netty4HttpClientFactory() {
		this(new NioEventLoopGroup(
				Runtime.getRuntime().availableProcessors() * 4));
	}

	public Netty4HttpClientFactory(EventLoopGroup eventLoopGroup) {
		this.eventLoopGroup = eventLoopGroup;
	}

	public Netty4HttpClientFactory setOptions(Map<ChannelOption<?>, ?> options) {
		if (options == null) {
			throw new IllegalArgumentException("'options' must not be empty");
		}
		this.options = options;
		return this;
	}

	private Bootstrap getBootstrap(final HttpParams params) {
		if (bootstrap == null) {
			bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel channel)
								throws Exception {
							ChannelPipeline pipeline = channel.pipeline();
							if (params != null) {
								channel.config().setConnectTimeoutMillis(
										params.getConnectTimeout());
								if (options != null) {
									channel.config().setOptions(options);
								}
								pipeline.addLast(new ReadTimeoutHandler(params
										.getReadTimeout(),
										TimeUnit.MILLISECONDS));
							}
							pipeline.addLast(new HttpClientCodec());
							pipeline.addLast(new HttpContentDecompressor());
							pipeline.addLast(new ChunkedWriteHandler());
							pipeline.addLast(new HttpResponseDecoder());
							pipeline.addLast(new HttpObjectAggregator(
									Integer.MAX_VALUE));
						}
					});
		}
		return bootstrap;
	}

	@Override
	public HttpClient newInstance(HttpParams params) {
		return new Netty4HttpClient(getBootstrap(params), params);
	}
}
