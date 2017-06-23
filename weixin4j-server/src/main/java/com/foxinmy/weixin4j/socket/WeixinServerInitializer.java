package com.foxinmy.weixin4j.socket;

import java.util.Map;

import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.util.AesToken;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 微信消息服务器初始化
 *
 * @className WeixinServerInitializer
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see
 */
public class WeixinServerInitializer extends ChannelInitializer<SocketChannel> {

    private final WeixinMessageDispatcher messageDispatcher;
    private final WeixinMessageDecoder messageDecoder;

    public WeixinServerInitializer(Map<String, AesToken> aesTokenMap, WeixinMessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
        this.messageDecoder = new WeixinMessageDecoder(aesTokenMap);
    }

    public void addAesToken(AesToken asetoken) {
        messageDecoder.addAesToken(asetoken);
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(messageDecoder);
        pipeline.addLast(new WeixinResponseEncoder());
        pipeline.addLast(new SingleResponseEncoder());
        pipeline.addLast(new WeixinRequestHandler(messageDispatcher));
    }
}
