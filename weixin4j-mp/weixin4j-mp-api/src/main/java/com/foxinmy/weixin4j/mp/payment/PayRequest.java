package com.foxinmy.weixin4j.mp.payment;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PayRequest extends PayBaseInfo {

	private static final long serialVersionUID = -453746488398523883L;

	// 订单详情扩展 订单信息组成该字符串
	@XStreamAlias("Package")
	@JSONField(name = "package")
	private String packageInfo;

	public PayRequest() {
		this(null, null);
	}

	public PayRequest(String appId, String packageInfo) {
		super(appId, System.currentTimeMillis() / 1000 + "", RandomUtil
				.generateString(16));
		this.packageInfo = packageInfo;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}
}
