package com.foxinmy.weixin4j.socket;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.response.SingleResponse;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;

/**
 * 微信回复编码类
 * 
 * @className WeixinResponseEncoder
 * @author jy
 * @date 2014年11月13日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 * @see com.foxinmy.weixin4j.response.WeixinResponse
 */
public class SingleResponseEncoder extends
		MessageToMessageEncoder<SingleResponse> {

	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	@Override
	protected void encode(ChannelHandlerContext ctx, SingleResponse response,
			List<Object> out) throws WeixinException {
		String content = response.toContent();
		ctx.writeAndFlush(HttpUtil.createHttpResponse(content, OK,
				Consts.CONTENTTYPE$APPLICATION_XML));
		logger.info("encode single response:{}", content);
	}
}