package com.foxinmy.weixin4j.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.RandomUtil;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayRequest extends PayBaseInfo {

	private static final long serialVersionUID = -453746488398523883L;

	/**
	 * 订单详情扩展 订单信息组成该字符串
	 */
	@XmlElement(name = "Package")
	@JSONField(name = "package")
	private String packageInfo;

	/**
	 * 冗余字段
	 */
	@XmlTransient
	@JSONField(serialize = false)
	private String prepayId;
	/**
	 * 冗余字段
	 */
	@XmlTransient
	@JSONField(serialize = false)
	private String partnerId;


	protected PayRequest() {
		// jaxb required
	}

	public PayRequest(String appId, String packageInfo) {
		super(appId, DateUtil.timestamp2string(), RandomUtil.generateString(16));
		this.packageInfo = packageInfo;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}


	@Override
	public String toString() {
		return "package" + packageInfo + ", prepayId=" + prepayId
				+ super.toString();
	}
}
