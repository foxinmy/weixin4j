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
 * 单一回复编码类
 * 
 * @className SingleResponseEncoder
 * @author jy
 * @date 2015年08月02日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.response.SingleResponse
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
				Consts.CONTENTTYPE$TEXT_PLAIN));
		logger.info("encode single response:{}", content);
	}
}