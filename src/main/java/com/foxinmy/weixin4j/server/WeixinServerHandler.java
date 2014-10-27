package com.foxinmy.weixin4j.server;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.action.WeixinAction;
import com.foxinmy.weixin4j.util.MessageUtil;

public class WeixinServerHandler extends ChannelInboundHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final WeixinActionMapping weixinActionMapping;

	public WeixinServerHandler(WeixinActionMapping weixinActionMapping) {
		this.weixinActionMapping = weixinActionMapping;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws DocumentException {
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest req = (FullHttpRequest) msg;
			if (HttpHeaders.is100ContinueExpected(req)) {
				ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
				return;
			}
			String xmlMsg = req.content().toString(StandardCharsets.UTF_8);
			log.info("\n=================message in=================\n{}",
					xmlMsg);
			String key = MessageUtil.getMappingKey(xmlMsg);
			WeixinAction action = weixinActionMapping.getAction(key);
			if (action == null) {
				ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
						HttpResponseStatus.NOT_FOUND));
				return;
			}
			String content = action.execute(xmlMsg);
			log.info("\n=================message out=================\n{}",
					content);
			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
					OK, Unpooled.copiedBuffer(content, StandardCharsets.UTF_8));
			response.headers().set(CONTENT_TYPE, "text/plain;charset=utf-8");
			response.headers().set(CONTENT_LENGTH,
					response.content().readableBytes());
			if (!HttpHeaders.isKeepAlive(req)) {
				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
			} else {
				response.headers().set(CONNECTION, Values.KEEP_ALIVE);
				ctx.write(response);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
