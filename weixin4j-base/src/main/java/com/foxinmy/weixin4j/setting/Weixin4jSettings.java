package com.foxinmy.weixin4j.setting;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.model.Token;
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
public class Weixin4jSettings<T> {
	/**
	 * 账号信息
	 */
	private final T account;
	/**
	 * Http参数
	 */
	private HttpParams httpParams;
	/**
	 * 系统临时目录
	 */
	private String tmpdir;
	/**
	 * Token的存储方式 默认为FileCacheStorager
	 */
	private CacheStorager<Token> cacheStorager;
	/**
	 * 支付接口需要的证书文件(*.p12)
	 */
	private String certificateFile;

	public Weixin4jSettings(T account) {
		this.account = account;
	}

	public T getAccount() {
		return account;
	}

	public HttpParams getHttpParams() {
		return httpParams;
	}

	public String getTmpdir() {
		return tmpdir;
	}

	public void setHttpParams(HttpParams httpParams) {
		this.httpParams = httpParams;
	}

	public void setTmpdir(String tmpdir) {
		this.tmpdir = tmpdir;
	}

	public String getTmpdir0() {
		if (StringUtil.isBlank(tmpdir)) {
			return Weixin4jConfigUtil.getValue("weixin4j.tmpdir",
					System.getProperty("java.io.tmpdir"));
		}
		return tmpdir;
	}

	public CacheStorager<Token> getCacheStorager() {
		return cacheStorager;
	}

	public CacheStorager<Token> getCacheStorager0() {
		if (cacheStorager == null) {
			return new FileCacheStorager<Token>(getTmpdir0());
		}
		return cacheStorager;
	}

	public void setCacheStorager(CacheStorager<Token> cacheStorager) {
		this.cacheStorager = cacheStorager;
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
		return "Weixin4jSettings [account=" + account + ", httpParams="
				+ httpParams + ", tmpdir=" + tmpdir + ", cacheStorager="
				+ cacheStorager + ", certificateFile=" + certificateFile + "]";
	}
}