package com.foxinmy.weixin4j.mp.payment;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PayRequest extends PayBaseInfo {

	private static final long serialVersionUID = -453746488398523883L;

	/**
	 * 订单详情扩展 订单信息组成该字符串
	 */
	@XStreamAlias("Package")
	@JSONField(name = "package")
	private String packageInfo;

	public PayRequest() {
		super(null, DateUtil.timestamp2string(), RandomUtil.generateString(16));
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

	@Override
	public String toString() {
		return "package" + packageInfo + ", " + super.toString();
	}
}
