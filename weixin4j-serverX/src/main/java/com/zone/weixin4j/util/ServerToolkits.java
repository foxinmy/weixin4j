package com.zone.weixin4j.util;

import com.zone.weixin4j.socket.WeixinMessageTransfer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 工具包
 * 
 * @className ServerToolkits
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月26日
 * @since JDK 1.7
 * @see
 */
public final class ServerToolkits {
	private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final String AES = "AES";
	public static final String SHA1 = "SHA-1";
	public static final String PROTOCOL_FILE = "file";
	public static final String PROTOCOL_JAR = "jar";
	public static final String CONTENTTYPE$APPLICATION_XML = "application/xml";
	public static final String CONTENTTYPE$TEXT_PLAIN = "text/plain";

	/**
	 * 返回一个定长的随机字符串(包含数字和大小写字母)
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 构造器设置为可见
	 * 
	 * @param ctor
	 */
	public static void makeConstructorAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor
				.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}

	/**
	 * SHA1签名
	 * 
	 * @param content
	 *            待签名字符串
	 * @return 签名后的字符串
	 */
	public static String digestSHA1(String content) {
		byte[] data = ServerToolkits.getBytesUtf8(content);
		try {
			return HexUtil.encodeHexString(MessageDigest.getInstance(SHA1)
					.digest(data));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String newString(final byte[] bytes, final Charset charset) {
		return bytes == null ? null : new String(bytes, charset);
	}

	public static byte[] getBytesUtf8(final String content) {
		return content != null ? content.getBytes(UTF_8) : null;
	}

	public static String newStringUtf8(final byte[] bytes) {
		return newString(bytes, UTF_8);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
