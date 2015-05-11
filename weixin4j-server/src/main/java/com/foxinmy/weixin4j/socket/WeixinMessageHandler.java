package com.foxinmy.weixin4j.socket;

import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpMethod;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.bean.AesToken;
import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 微信被动消息处理类
 * 
 * @className WeixinMessageHandler
 * @author jy
 * @date 2014年11月16日
 * @since JDK 1.7
 * @see
 */
public class WeixinMessageHandler extends
		SimpleChannelInboundHandler<WeixinRequest> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final AesToken aesToken;
	private final WeixinMessageDispatcher messageDispatcher;
	private final JAXBContext jaxbContext;

	public WeixinMessageHandler(AesToken aesToken,
			WeixinMessageDispatcher messageDispatcher) throws WeixinException {
		this.aesToken = aesToken;
		this.messageDispatcher = messageDispatcher;
		try {
			jaxbContext = JAXBContext.newInstance(WeixinMessage.class);
		} catch (JAXBException e) {
			throw new WeixinException(e);
		}
	}

	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		log.error("catch the exception:{}", cause.getMessage());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WeixinRequest request)
			throws WeixinException {
		log.info("\n=================message request=================\n{}",
				request);
		if (request.getMethod().equals(HttpMethod.GET.name())) {
			if (MessageUtil.signature(aesToken.getToken(),
					request.getTimeStamp(), request.getNonce()).equals(
					request.getSignature())) {
				ctx.writeAndFlush(
						HttpUtil.createHttpResponse(request.getEchoStr(), OK,
								Consts.CONTENTTYPE$TEXT_PLAIN)).addListener(
						ChannelFutureListener.CLOSE);
				return;
			}
			ctx.writeAndFlush(
					HttpUtil.createHttpResponse(null, FORBIDDEN, null))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		} else if (request.getMethod().equals(HttpMethod.POST.name())) {
			if (!MessageUtil.signature(aesToken.getToken(),
					request.getTimeStamp(), request.getNonce()).equals(
					request.getSignature())) {
				ctx.writeAndFlush(
						HttpUtil.createHttpResponse(null, FORBIDDEN, null))
						.addListener(ChannelFutureListener.CLOSE);
				return;
			}
			if (request.getEncryptType() == EncryptType.AES) {
				if (!MessageUtil.signature(aesToken.getToken(),
						request.getTimeStamp(), request.getNonce(),
						request.getEncryptContent()).equals(
						request.getMsgSignature())) {
					ctx.writeAndFlush(
							HttpUtil.createHttpResponse(null, FORBIDDEN, null))
							.addListener(ChannelFutureListener.CLOSE);
					return;
				}
			}
		} else {
			ctx.writeAndFlush(
					HttpUtil.createHttpResponse(null, METHOD_NOT_ALLOWED, null))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		WeixinMessage weixinMessage = null;
		try {
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			weixinMessage = (WeixinMessage) jaxbUnmarshaller
					.unmarshal(new ByteArrayInputStream(request
							.getOriginalContent().getBytes(Consts.UTF_8)));
		} catch (JAXBException e) {
			throw new WeixinException(e);
		}
		ctx.channel().attr(Consts.ENCRYPTTYPE_KEY)
				.set(request.getEncryptType());
		ctx.channel().attr(Consts.USEROPENID_KEY)
				.set(weixinMessage.getFromUserName());
		if (StringUtil.isBlank(aesToken.getAppid())) {
			ctx.channel().attr(Consts.ACCOUNTOPENID_KEY)
					.set(weixinMessage.getToUserName());
		}
		final WeixinRequest cloneRequest = (WeixinRequest) ClassUtil
				.deepClone(request);
		final WeixinMessage cloneMessage = (WeixinMessage) ClassUtil
				.deepClone(weixinMessage);
		messageDispatcher.doDispatch(ctx, cloneRequest, cloneMessage);
	}
}
