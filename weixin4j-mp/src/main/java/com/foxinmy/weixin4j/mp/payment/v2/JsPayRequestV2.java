package com.foxinmy.weixin4j.mp.payment.v2;

import java.beans.Transient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;

/**
 * V2微信JS支付:get_brand_wcpay_request</br>
 * <font color="red">所列参数均为非空字符串</font>
 * <p>
 * get_brand_wcpay_request:ok 支付成功<br>
 * get_brand_wcpay_request:cancel 支付过程中用户取消<br>
 * get_brand_wcpay_request:fail 支付失败
 * </p>
 * 
 * @className JsPayRequestV2
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JsPayRequestV2 extends PayRequest {

	private static final long serialVersionUID = -5972173459255255197L;

	protected JsPayRequestV2(){
		// jaxb required
	}
	
	public JsPayRequestV2(WeixinPayAccount weixinAccount, PayPackageV2 payPackage) {
		this.setAppId(weixinAccount.getId());
		this.setPackageInfo(package2string(payPackage,
				weixinAccount.getPartnerKey()));
	}

	@Transient
	@JSONField(serialize = false)
	private String package2string(PayPackageV2 payPackage, String partnerKey) {
		StringBuilder sb = new StringBuilder();
		// a.对所有传入参数按照字段名的 ASCII 码从小到大排序(字典序) 后,
		// 使用 URL 键值 对的格式(即 key1=value1&key2=value2...)拼接成字符串 string1
		// 注意:值为空的参数不参与签名
		sb.append(MapUtil.toJoinString(payPackage, false, false, null));
		// b--->
		// 在 string1 最后拼接上 key=paternerKey 得到 stringSignTemp 字符串,并 对
		// stringSignTemp 进行 md5 运算
		// 再将得到的 字符串所有字符转换为大写 ,得到 sign 值 signValue。
		sb.append("&key=").append(partnerKey);
		// c---> & d---->
		String sign = DigestUtil.MD5(sb.toString()).toUpperCase();
		sb.delete(0, sb.length());
		// c.对传入参数中所有键值对的 value 进行 urlencode 转码后重新拼接成字符串 string2
		sb.append(MapUtil.toJoinString(payPackage, true, false, null))
				.append("&sign=").append(sign);

		return sb.toString();
	}

	@Override
	public String toString() {
		return "JsPayRequestV2 [" + super.toString() + "]";
	}
}