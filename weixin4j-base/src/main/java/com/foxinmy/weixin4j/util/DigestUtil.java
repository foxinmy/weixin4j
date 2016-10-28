package com.foxinmy.weixin4j.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具类
 * 
 * @className DigestUtil
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月6日
 * @since JDK 1.6
 * @see
 */
public final class DigestUtil {

	private static MessageDigest getDigest(final String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * SHA1签名
	 * 
	 * @param content
	 *            待签名字符串
	 * @return 签名后的字符串
	 */
	public static String SHA1(String content) {
		byte[] data = StringUtil.getBytesUtf8(content);
		return HexUtil.encodeHexString(getDigest(Consts.SHA1).digest(data));
	}

	/**
	 * SHA签名
	 * 
	 * @param content
	 *            待签名字符串
	 * @return 签名后的字符串
	 */
	public static String SHA(String content) {
		byte[] data = StringUtil.getBytesUtf8(content);
		return HexUtil.encodeHexString(getDigest(Consts.SHA).digest(data));
	}

	/**
	 * MD5签名
	 * 
	 * @param content
	 *            待签名字符串
	 * @return 签名后的字符串
	 */
	public static String MD5(String content) {
		byte[] data = StringUtil.getBytesUtf8(content);
		return HexUtil.encodeHexString(getDigest(Consts.MD5).digest(data));
	}
}
