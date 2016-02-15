package com.foxinmy.weixin4j.util;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenStorager;

/**
 * 微信配置相关
 * 
 * @className Weixin4jSettings
 * @author jy
 * @date 2016年1月28日
 * @since JDK 1.6
 * @see
 */
public class Weixin4jSettings {
	/**
	 * 微信支付账号信息
	 */
	private WeixinPayAccount weixinPayAccount;
	/**
	 * 微信账号信息
	 */
	private WeixinAccount weixinAccount;
	/**
	 * Http参数
	 */
	private HttpParams httpParams;
	/**
	 * token存储方式 默认为FileTokenStorager
	 */
	private TokenStorager tokenStorager;
	/**
	 * 系统临时目录
	 */
	private String tmpdir;
	/**
	 * 支付接口需要的证书文件(*.p12)
	 */
	private String certificateFile;

	/**
	 * 默认使用weixin4j.properties配置的信息
	 */
	public Weixin4jSettings() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinPayAccount.class), "classpath:ca.p12");
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
		this.weixinPayAccount = weixinPayAccount;
		this.certificateFile = certificateFile;
		this.weixinAccount = new WeixinAccount(weixinPayAccount.getId(),
				weixinPayAccount.getSecret());
	}

	/**
	 * 普通代理接口
	 * 
	 * @param weixinAccount
	 */
	public Weixin4jSettings(WeixinAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	public WeixinPayAccount getWeixinPayAccount() {
		return weixinPayAccount;
	}

	public WeixinAccount getWeixinAccount() {
		return weixinAccount;
	}

	public HttpParams getHttpParams() {
		return httpParams;
	}

	public HttpParams getHttpParams0() {
		if (httpParams == null) {
			return new HttpParams();
		}
		return httpParams;
	}

	public String getTmpdir() {
		return tmpdir;
	}

	public String getTmpdir0() {
		if (StringUtil.isBlank(tmpdir)) {
			return Weixin4jConfigUtil.getClassPathValue("weixin4j.tmpdir",
					System.getProperty("java.io.tmpdir"));
		}
		return tmpdir;
	}

	public TokenStorager getTokenStorager() {
		return tokenStorager;
	}

	public TokenStorager getTokenStorager0() {
		if (tokenStorager == null) {
			return new FileTokenStorager(getTmpdir0());
		}
		return tokenStorager;
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

	public void setHttpParams(HttpParams httpParams) {
		this.httpParams = httpParams;
	}

	public void setTmpdir(String tmpdir) {
		this.tmpdir = tmpdir;
	}

	public void setTokenStorager(TokenStorager tokenStorager) {
		this.tokenStorager = tokenStorager;
	}

	public void setCertificateFile(String certificateFile) {
		this.certificateFile = certificateFile;
	}

	@Override
	public String toString() {
		return "Weixin4jSettings [weixinAccount=" + weixinAccount
				+ ", httpParams=" + httpParams + ",tokenStorager="
				+ tokenStorager + ", tmpdir=" + tmpdir + ", certificateFile= "
				+ certificateFile + "]";
	}
}
