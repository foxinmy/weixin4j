package com.foxinmy.weixin4j.mp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WeixinServerInitializer extends ChannelInitializer<SocketChannel> {

	private final WeixinActionMapping weixinActionMapping;

	public WeixinServerInitializer() {
		this.weixinActionMapping = new WeixinActionMapping();
	}

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new WeixinServerHandler(weixinActionMapping));
	}
}
