package com.foxinmy.weixin4j.setting;

import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenStorager;

/**
 * 系统配置相关
 *
 * @className SystemSettings
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年1月28日
 * @since JDK 1.6
 * @see
 */
public abstract class SystemSettings<T> {
	/**
	 * 账号信息
	 */
	private T account;
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
	 * @param account
	 */
	public SystemSettings(T account) {
		this.account = account;
	}

	public T getAccount() {
		return account;
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

	public abstract String getTmpdir0();

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
		return "account=" + account + ", httpParams=" + httpParams
				+ ",tokenStorager=" + tokenStorager + ", tmpdir=" + tmpdir;
	}
}
