package com.foxinmy.weixin4j.qy.suite;

import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.qy.model.WeixinQyAccount;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenStorager;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信第三方套件配置相关
 * 
 * @className Weixin4jSuiteSettings
 * @author jy
 * @date 2016年1月28日
 * @since JDK 1.6
 * @see
 */
public class Weixin4jSuiteSettings {
	/**
	 * 微信企业号信息
	 */
	private final WeixinQyAccount weixinAccount;
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
	 * 默认使用weixin4j.properties配置的信息
	 */
	public Weixin4jSuiteSettings() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinQyAccount.class));
	}

	/**
	 * 
	 * @param providerCorpId
	 *            服务商的企业号ID <font color="red">使用服务商API时必填项</font>
	 * @param providerSecret
	 *            服务商secret <font color="red">使用服务商API时必填项</font>
	 * @param suites
	 *            套件信息 <font color="red">使用套件API时必填项</font>
	 */
	public Weixin4jSuiteSettings(String providerCorpId, String providerSecret,
			WeixinAccount... suites) {
		this.weixinAccount = new WeixinQyAccount(providerCorpId, null,
				Arrays.asList(suites), providerSecret, null);
	}

	private Weixin4jSuiteSettings(WeixinQyAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	public WeixinQyAccount getWeixinAccount() {
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

	public void setHttpParams(HttpParams httpParams) {
		this.httpParams = httpParams;
	}

	public void setTmpdir(String tmpdir) {
		this.tmpdir = tmpdir;
	}

	public void setTokenStorager(TokenStorager tokenStorager) {
		this.tokenStorager = tokenStorager;
	}

	@Override
	public String toString() {
		return "Weixin4jSuiteSettings [weixinAccount=" + weixinAccount
				+ ", httpParams=" + httpParams + ",tokenStorager="
				+ tokenStorager + ", tmpdir=" + tmpdir + "]";
	}
}
