package com.foxinmy.weixin4j.mp.oldpayment;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.foxinmy.weixin4j.sign.AbstractWeixinSignature;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;

/**
 * 老版本支付签名
 * 
 * @className WeixinOldPaymentSignature
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月26日
 * @since JDK 1.6
 * @see
 */
public class WeixinOldPaymentSignature extends AbstractWeixinSignature {

	@Override
	public boolean lowerCase() {
		return true;
	}

	@Override
	public String sign(Object obj) {
		return DigestUtil.SHA1(join(obj).toString());
	}

	public String sign(Object obj, String paySignKey) {
		if (obj instanceof Map) {
			JSONPath.set(obj, "appKey", paySignKey);
		} else {
			((JSONObject) JSON.toJSON(obj)).put("appKey", paySignKey);
		}
		return DigestUtil.SHA1(join(obj).toString());
	}

	/**
	 * package拼接签名
	 * 
	 * @param packageInfo
	 *            package对象
	 * @param partnerKey
	 *            签名key
	 * @return
	 */
	public String sign(PayPackageV2 packageInfo, String partnerKey) {
		StringBuilder sb = new StringBuilder();
		// a.对所有传入参数按照字段名的 ASCII 码从小到大排序(字典序) 后,
		// 使用 URL 键值 对的格式(即 key1=value1&key2=value2...)拼接成字符串 string1
		// 注意:值为空的参数不参与签名
		sb.append(MapUtil.toJoinString(packageInfo, false, false));
		// b--->
		// 在 string1 最后拼接上 key=signKey 得到 stringSignTemp 字符串,并 对
		// stringSignTemp 进行 md5 运算
		// 再将得到的 字符串所有字符转换为大写 ,得到 sign 值 signValue。
		sb.append("&key=").append(partnerKey);
		// c---> & d---->
		String sign = DigestUtil.MD5(sb.toString()).toUpperCase();
		sb.delete(0, sb.length());
		// c.对传入参数中所有键值对的 value 进行 urlencode 转码后重新拼接成字符串 string2
		sb.append(MapUtil.toJoinString(packageInfo, true, false))
				.append("&sign=").append(sign);
		return sb.toString();
	}
}
