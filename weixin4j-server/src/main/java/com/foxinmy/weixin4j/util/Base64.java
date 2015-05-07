package com.foxinmy.weixin4j.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public final class Base64 {

	public static byte[] decodeBase64(final String content) {
		byte[] data = StringUtil.getBytesUtf8(content);
		ByteBuf des = io.netty.handler.codec.base64.Base64.decode(Unpooled
				.copiedBuffer(data));
		if (des.hasArray()) {
			return des.array();
		} else {
			byte[] desArray = new byte[des.readableBytes()];
			des.readBytes(desArray);
			return desArray;
		}
	}

	public static byte[] encodeBase64(final byte[] bytes) {
		ByteBuf des = io.netty.handler.codec.base64.Base64.encode(Unpooled
				.copiedBuffer(bytes));
		if (des.hasArray()) {
			return des.array();
		} else {
			byte[] desArray = new byte[des.readableBytes()];
			des.readBytes(desArray);
			return desArray;
		}
	}

	public static String encodeBase64String(final byte[] bytes) {
		byte[] data = encodeBase64(bytes);
		return HexUtil.encodeHexString(data);
	}
}
