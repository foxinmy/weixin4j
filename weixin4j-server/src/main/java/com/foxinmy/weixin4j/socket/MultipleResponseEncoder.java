package com.foxinmy.weixin4j.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.foxinmy.weixin4j.response.MultipleResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 微信多个回复编码类
 * 
 * @className WeixinResponseEncoder
 * @author jy
 * @date 2016年4月27日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 * @see com.foxinmy.weixin4j.response.MultipleResponse
 */
public class MultipleResponseEncoder extends
		MessageToMessageEncoder<MultipleResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MultipleResponse response,
			List<Object> out) throws Exception {
		for (WeixinResponse r : response.getResponses()) {
			ctx.writeAndFlush(r);
		}
	}
}
