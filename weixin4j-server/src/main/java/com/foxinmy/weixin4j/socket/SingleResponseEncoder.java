package com.foxinmy.weixin4j.socket;

import java.util.List;

import com.foxinmy.weixin4j.response.SingleResponse;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.ServerToolkits;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 单一回复编码类
 *
 * @className SingleResponseEncoder
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年08月02日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.response.SingleResponse
 */
@ChannelHandler.Sharable
public class SingleResponseEncoder extends MessageToMessageEncoder<SingleResponse> {

    private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

    @Override
    protected void encode(ChannelHandlerContext ctx, SingleResponse response, List<Object> out) {
        String content = response.toContent();
        ctx.writeAndFlush(HttpUtil.createHttpResponse(content, ServerToolkits.CONTENTTYPE$TEXT_PLAIN));
        logger.info("encode single response:{}", content);
    }
}