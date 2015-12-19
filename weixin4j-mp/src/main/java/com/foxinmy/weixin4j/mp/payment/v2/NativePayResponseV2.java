package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * V2 Native支付时的回调响应
 * 
 * @className NativePayResponseV2
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayResponseV2 extends JsPayRequestV2 {

	private static final long serialVersionUID = 6119895998783333012L;
	/**
	 * 返回码
	 */
	@JSONField(name = "RetCode")
	@XmlElement(name = "RetCode")
	private String retCode;
	/**
	 * 返回消息
	 */
	@JSONField(name = "RetErrMsg")
	@XmlElement(name = "RetErrMsg")
	private String retMsg;

	@XmlTransient
	@JSONField(serialize = false)
	private WeixinPayAccount weixinAccount;

	protected NativePayResponseV2() {
		// jaxb required
	}

	public NativePayResponseV2(WeixinPayAccount weixinAccount,
			PayPackageV2 payPackage) {
		super(weixinAccount, payPackage);
		this.retCode = "0";
		this.retMsg = "OK";
		this.weixinAccount = weixinAccount;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	/**
	 * 生成 回调字符串
	 * 
	 * @return native回调字符串
	 */
	@XmlTransient
	@JSONField(serialize = false)
	public String asReqeustXml() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getId());
		map.put("appkey", weixinAccount.getPaySignKey());
		map.put("timestamp", DateUtil.timestamp2string());
		map.put("noncestr", RandomUtil.generateString(16));
		map.put("package", getPackageInfo());
		map.put("retcode", getRetCode());
		map.put("reterrmsg", getRetMsg());
		this.setPaySign(DigestUtil.paysignSha(map, null));
		return XmlStream.toXML(this);
	}

	@Override
	public String toString() {
		return "NativePayResponseV2 [retCode=" + retCode + ", retMsg=" + retMsg
				+ ", " + super.toString() + "]";
	}
}
