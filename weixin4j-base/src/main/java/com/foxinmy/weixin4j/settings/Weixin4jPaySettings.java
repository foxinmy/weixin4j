package com.foxinmy.weixin4j.settings;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信支付配置相关
 * 
 * @className Weixin4jPaySettings
 * @author jy
 * @date 2016年1月28日
 * @since JDK 1.6
 * @see
 */
public class Weixin4jPaySettings {

	private final WeixinPayAccount payAccount;

	public Weixin4jPaySettings() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinPayAccount.class));
	}

	public Weixin4jPaySettings(WeixinPayAccount payAccount) {
		this.payAccount = payAccount;
	}

	/**
	 * 支付账号信息
	 * 
	 * @return
	 */
	public WeixinPayAccount getPayAccount() {
		return this.payAccount;
	}

	private String billPath;

	/**
	 * 对账单保存路径
	 * 
	 * @param billPath
	 *            硬盘目录
	 */
	public Weixin4jPaySettings billPath(String billPath) {
		this.billPath = billPath;
		return this;
	}

	/**
	 * 默认对账单保存路径
	 */
	public static final String DEFAULT_BILL_PATH = "/tmp/weixin4j/bill";

	/**
	 * 对账单保存路径,默认值为{@link #DEFAULT_BILL_PATH}
	 * 
	 * @return
	 */
	public String getBillPath() {
		if (StringUtil.isBlank(billPath)) {
			this.billPath = Weixin4jConfigUtil.getClassPathValue("bill.path",
					DEFAULT_BILL_PATH);
		}
		return this.billPath;
	}

	private String certificatePath;

	/**
	 * ca证书存放路径
	 * 
	 * @param certificatePath
	 *            硬盘目录
	 * @return
	 */
	public Weixin4jPaySettings certificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
		return this;
	}

	/**
	 * 默认ca证书存放路径
	 */
	public static final String DEFAULT_CAFILE_PATH = "classpath:ca.p12";

	/**
	 * ca证书存放路径,默认值为{@link #DEFAULT_CAFILE_PATH}
	 * 
	 * @return
	 */
	public String getCertificatePath() {
		if (StringUtil.isBlank(certificatePath)) {
			this.certificatePath = Weixin4jConfigUtil.getClassPathValue(
					"certificate.path", DEFAULT_CAFILE_PATH);
		}
		return this.certificatePath;
	}

	@Override
	public String toString() {
		return "Weixin4jPaySettings [payAccount=" + payAccount + ", billPath="
				+ getBillPath() + ", certificatePath=" + getCertificatePath()
				+ "]";
	}
}
