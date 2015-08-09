package com.foxinmy.weixin4j.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.Map;

import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.util.AesToken;

/**
 * 微信消息服务器初始化
 * 
 * @className WeixinServerInitializer
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinServerInitializer extends ChannelInitializer<SocketChannel> {

	private final Map<String, AesToken> aesTokenMap;
	private final WeixinMessageDispatcher messageDispatcher;

	public WeixinServerInitializer(Map<String, AesToken> aesTokenMap,
			WeixinMessageDispatcher messageDispatcher) throws WeixinException {
		this.aesTokenMap = aesTokenMap;
		this.messageDispatcher = messageDispatcher;
	}

	@Override
	protected void initChannel(SocketChannel channel) {
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new WeixinMessageDecoder(aesTokenMap));
		pipeline.addLast(new WeixinResponseEncoder());
		pipeline.addLast(new SingleResponseEncoder());
		pipeline.addLast(new WeixinRequestHandler(messageDispatcher));
	}
}
