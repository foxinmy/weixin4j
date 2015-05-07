package com.foxinmy.weixin4j.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import com.foxinmy.weixin4j.model.AesToken;

public class WeixinServerInitializer extends ChannelInitializer<SocketChannel> {

	private final AesToken aesToken;

	public WeixinServerInitializer(AesToken aesToken) {
		if (aesToken == null) {
			throw new IllegalArgumentException("AesToken not be null.");
		}
		this.aesToken = aesToken;
	}

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new WeixinMessageDecoder(aesToken));
		pipeline.addLast(new WeixinMessageEncoder(aesToken));
		pipeline.addLast(new WeixinMessageHandler(aesToken));
	}
}
