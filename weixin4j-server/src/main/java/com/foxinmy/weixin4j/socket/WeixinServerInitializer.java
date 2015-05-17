package com.foxinmy.weixin4j.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import com.foxinmy.weixin4j.bean.AesToken;
import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.exception.WeixinException;

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

	private final AesToken aesToken;
	private final WeixinMessageDispatcher messageDispatcher;

	public WeixinServerInitializer(AesToken aesToken,
			WeixinMessageDispatcher messageDispatcher) throws WeixinException {
		if (aesToken == null) {
			throw new WeixinException("AesToken not be null.");
		}
		this.aesToken = aesToken;
		this.messageDispatcher = messageDispatcher;
	}

	@Override
	protected void initChannel(SocketChannel channel) throws WeixinException {
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new WeixinMessageDecoder(aesToken));
		pipeline.addLast(new WeixinResponseEncoder(aesToken));
		pipeline.addLast(new WeixinRequestHandler(aesToken, messageDispatcher));
	}
}
