package com.foxinmy.weixin4j.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64Dialect;

/**
 * NettyBase64
 * 
 * @className NettyBase64
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see
 */
public final class NettyBase64 {

	private static byte[] byteBuf2Array(ByteBuf byteBuf) {
		if (byteBuf.hasArray()) {
			return byteBuf.array();
		} else {
			byte[] desArray = new byte[byteBuf.readableBytes()];
			byteBuf.readBytes(desArray);
			return desArray;
		}
	}

	public static byte[] decodeBase64(final String content) {
		byte[] data = ServerToolkits.getBytesUtf8(content);
		ByteBuf des = io.netty.handler.codec.base64.Base64.decode(
				Unpooled.copiedBuffer(data), Base64Dialect.STANDARD);
		return byteBuf2Array(des);
	}

	public static byte[] encodeBase64(final byte[] bytes) {
		ByteBuf des = io.netty.handler.codec.base64.Base64.encode(
				Unpooled.copiedBuffer(bytes), Base64Dialect.STANDARD);
		return byteBuf2Array(des);
	}

	public static String encodeBase64String(final byte[] bytes) {
		byte[] data = encodeBase64(bytes);
		return ServerToolkits.newStringUtf8(data);
	}
}
