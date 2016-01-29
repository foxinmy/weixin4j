package com.foxinmy.weixin4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.payment.mch.NativePayNotify;
import com.foxinmy.weixin4j.xml.XmlStream;

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

	/**
	 * package拼接签名(一般用于V2.x支付接口)
	 * 
	 * @param signObj
	 *            签名对象 如 PayPackageV2
	 * @param signKey
	 *            签名key
	 * @return
	 */
	public static String packageSign(Object signObj, String signKey) {
		StringBuilder sb = new StringBuilder();
		// a.对所有传入参数按照字段名的 ASCII 码从小到大排序(字典序) 后,
		// 使用 URL 键值 对的格式(即 key1=value1&key2=value2...)拼接成字符串 string1
		// 注意:值为空的参数不参与签名
		sb.append(MapUtil.toJoinString(signObj, false, false, null));
		// b--->
		// 在 string1 最后拼接上 key=signKey 得到 stringSignTemp 字符串,并 对
		// stringSignTemp 进行 md5 运算
		// 再将得到的 字符串所有字符转换为大写 ,得到 sign 值 signValue。
		sb.append("&key=").append(signKey);
		// c---> & d---->
		String sign = DigestUtil.MD5(sb.toString()).toUpperCase();
		sb.delete(0, sb.length());
		// c.对传入参数中所有键值对的 value 进行 urlencode 转码后重新拼接成字符串 string2
		sb.append(MapUtil.toJoinString(signObj, true, false, null))
				.append("&sign=").append(sign);

		return sb.toString();
	}

	public static void main(String[] args) throws FileNotFoundException {
		NativePayNotify notify = XmlStream.fromXML(new FileInputStream(
				new File("/Users/jy/Downloads/weixin4j.xml")),
				NativePayNotify.class);
		notify.setSign(null);
		System.err.println(paysignMd5(notify, "GATFzDwbQdbbci3QEQxX2rUBvwTrsMiZ"));
	}
}
