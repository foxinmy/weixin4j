package com.foxinmy.weixin4j.setting;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信配置相关
 *
 * @className Weixin4jSettings
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年1月28日
 * @since JDK 1.6
 * @see
 */
public class Weixin4jSettings extends SystemSettings<WeixinAccount> {
	/**
	 * 微信支付账号信息
	 */
	private WeixinPayAccount weixinPayAccount;
	/**
	 * 支付接口需要的证书文件(*.p12)
	 */
	private String certificateFile;

	/**
	 * 默认使用weixin4j.properties配置的信息
	 */
	public Weixin4jSettings() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinPayAccount.class), null);
	}

	/**
	 * 支付代理接口
	 *
	 * @param weixinPayAccount
	 *            商户信息
	 * @param certificateFile
	 *            支付接口需要的证书文件(*.p12),比如退款接口
	 */
	public Weixin4jSettings(WeixinPayAccount weixinPayAccount,
			String certificateFile) {
		this(weixinPayAccount);
		this.certificateFile = certificateFile;
	}

	/**
	 * 支付代理接口
	 *
	 * @param weixinPayAccount
	 *            商户信息
	 */
	public Weixin4jSettings(WeixinPayAccount weixinPayAccount) {
		this(new WeixinAccount(weixinPayAccount.getId(),
				weixinPayAccount.getSecret()));
		this.weixinPayAccount = weixinPayAccount;
	}

	/**
	 * 账号信息
	 *
	 * @param account
	 */
	public Weixin4jSettings(WeixinAccount account) {
		super(account);
	}

	public WeixinPayAccount getPayAccount() {
		return weixinPayAccount;
	}

	@Override
	public String getTmpdir0() {
		if (StringUtil.isBlank(getTmpdir())) {
			return Weixin4jConfigUtil.getClassPathValue("weixin4j.tmpdir",
					System.getProperty("java.io.tmpdir"));
		}
		return getTmpdir();
	}

	public String getCertificateFile() {
		return certificateFile;
	}

	public String getCertificateFile0() {
		if (StringUtil.isBlank(certificateFile)) {
			return Weixin4jConfigUtil.getClassPathValue(
					"weixin4j.certificate.file", "classpath:ca.p12");
		}
		return certificateFile;
	}

	public void setCertificateFile(String certificateFile) {
		this.certificateFile = certificateFile;
	}

	@Override
	public String toString() {
		return "Weixin4jSettings [weixinPayAccount=" + weixinPayAccount
				+ ", certificateFile=" + certificateFile + ", "
				+ super.toString() + "]";
	}
}
