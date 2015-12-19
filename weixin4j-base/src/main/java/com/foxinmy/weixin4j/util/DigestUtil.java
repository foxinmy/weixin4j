package com.foxinmy.weixin4j.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.model.Consts;

/**
 * 签名工具类
 * 
 * @className DigestUtil
 * @author jy
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

	/**
	 * md5签名(一般用于V3.x支付接口)
	 * 
	 * @param obj
	 *            签名对象
	 * @param paySignKey
	 *            支付API的密钥
	 * @return
	 */
	public static String paysignMd5(Object obj, String paySignKey) {
		StringBuilder sb = new StringBuilder();
		// a--->string1
		sb.append(MapUtil.toJoinString(obj, false, false, null));
		// b--->
		// 在 string1 最后拼接上 key=paternerKey 得到 stringSignTemp 字符串,并 对
		// stringSignTemp 进行 md5 运算
		// 再将得到的 字符串所有字符转换为大写 ,得到 sign 值 signValue。
		sb.append("&key=").append(paySignKey);
		return MD5(sb.toString()).toUpperCase();
	}

	/**
	 * sha签名(一般用于V2.x支付接口)
	 * 
	 * @param obj
	 *            签名对象
	 * @param paySignKey
	 *            支付API的密钥<font color="red">请注意排序放进去的是put("appKey",
	 *            paySignKey)</font>
	 * @return
	 */
	public static String paysignSha(Object obj, String paySignKey) {
		Map<String, String> extra = new HashMap<String, String>();
		if (StringUtil.isNotBlank(paySignKey)) {
			extra.put("appKey", paySignKey);
		}
		return SHA1(MapUtil.toJoinString(obj, false, true, extra));
	}
}
