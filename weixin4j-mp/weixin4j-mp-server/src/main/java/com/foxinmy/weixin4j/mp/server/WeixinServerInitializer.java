package com.foxinmy.weixin4j.mp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import com.foxinmy.weixin4j.action.mapping.ActionMapping;
import com.foxinmy.weixin4j.action.mapping.AnnotationActionMapping;
import com.foxinmy.weixin4j.mp.action.ImageAction;

public class WeixinServerInitializer extends ChannelInitializer<SocketChannel> {

	private final ActionMapping actionMapping;

	public WeixinServerInitializer() {
		this.actionMapping = new AnnotationActionMapping(
				ImageAction.class.getPackage());
	}

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new WeixinMessageDecoder());
		pipeline.addLast(new WeixinMessageEncoder());
		pipeline.addLast(new WeixinServerHandler(actionMapping));
	}
}
