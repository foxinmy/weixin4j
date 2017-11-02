package com.foxinmy.weixin4j.socket;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;

import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.SingleResponse;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.AesToken;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.ServerToolkits;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 微信请求处理类
 *
 * @className WeixinRequestHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月16日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher
 */
public class WeixinRequestHandler extends SimpleChannelInboundHandler<WeixinRequest> {
    private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

    private final WeixinMessageDispatcher messageDispatcher;

    public WeixinRequestHandler(WeixinMessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        logger.error(cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WeixinRequest request) {
        AesToken aesToken = request.getAesToken();
        // 消息字段不完整返回400
        if (aesToken == null || (ServerToolkits.isBlank(request.getSignature())
                && ServerToolkits.isBlank(request.getMsgSignature()))) {
            ctx.writeAndFlush(resolveResponse(BAD_REQUEST, request)).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        /**
         * 公众平台:无论Get,Post都带signature参数,当开启aes模式时带msg_signature参数
         * 企业号:无论Get,Post都带msg_signature参数
         * 一般来说：signature验证url上的参数签名，msg_signature验证消息体签名
         **/
        if (request.getMethod() == HttpMethod.GET) {
            // URL参数签名验证
            if (!ServerToolkits.isBlank(request.getSignature())
                    && MessageUtil.signature(aesToken.getToken(), request.getTimeStamp(), request.getNonce())
                            .equals(request.getSignature())) {
                ctx.writeAndFlush(new SingleResponse(request.getEchoStr()));
                return;
            }
            // XML消息签名验证
            if (!ServerToolkits.isBlank(request.getMsgSignature()) && MessageUtil
                    .signature(aesToken.getToken(), request.getTimeStamp(), request.getNonce(), request.getEchoStr())
                    .equals(request.getMsgSignature())) {
                ctx.writeAndFlush(
                        new SingleResponse(MessageUtil.aesDecrypt(null, aesToken.getAesKey(), request.getEchoStr())));
                return;
            }
            ctx.writeAndFlush(resolveResponse(FORBIDDEN, request)).addListener(ChannelFutureListener.CLOSE);
            return;
        } else if (request.getMethod() == HttpMethod.POST) {
            // URL参数签名验证
            if (!ServerToolkits.isBlank(request.getSignature())
                    && !MessageUtil.signature(aesToken.getToken(), request.getTimeStamp(), request.getNonce())
                            .equals(request.getSignature())) {
                ctx.writeAndFlush(resolveResponse(FORBIDDEN, request)).addListener(ChannelFutureListener.CLOSE);
                return;
            }
            // XML消息签名验证
            if (request.getEncryptType() == EncryptType.AES
                    && !MessageUtil.signature(aesToken.getToken(), request.getTimeStamp(), request.getNonce(),
                            request.getEncryptContent()).equals(request.getMsgSignature())) {
                ctx.writeAndFlush(resolveResponse(FORBIDDEN, request)).addListener(ChannelFutureListener.CLOSE);
                return;
            }
        } else {
            // 访问其它URL
            ctx.writeAndFlush(resolveResponse(METHOD_NOT_ALLOWED, request)).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        messageDispatcher.doDispatch(ctx, request);
    }

    private FullHttpResponse resolveResponse(HttpResponseStatus responseStatus, WeixinRequest request) {
        FullHttpResponse response = new DefaultFullHttpResponse(request.getProtocolVersion(), responseStatus);
        HttpUtil.resolveHeaders(response);
        return response;
    }
}
