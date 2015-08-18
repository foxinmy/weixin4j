package com.foxinmy.weixin4j.http;

import java.net.Proxy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * Http 参数
 * 
 * @className HttpParams
 * @author jy
 * @date 2015年8月13日
 * @since JDK 1.7
 * @see
 */
public final class HttpParams {

	private boolean allowUserInteraction = true;
	private int connectTimeout = 5000;
	private int socketTimeout = 5000;
	private int readTimeout = 5000;
	private int chunkSize = 4096;
	private long ifModifiedSince = 0l;
	private boolean followRedirects = false;

	/**
	 * 代理对象
	 */
	private Proxy proxy;
	/**
	 * SSL对象
	 */
	private SSLContext sslContext;
	/**
	 * hostname对象
	 */
	private HostnameVerifier hostnameVerifier;

	public boolean isAllowUserInteraction() {
		return allowUserInteraction;
	}

	public void setAllowUserInteraction(boolean allowUserInteraction) {
		this.allowUserInteraction = allowUserInteraction;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	public long getIfModifiedSince() {
		return ifModifiedSince;
	}

	public void setIfModifiedSince(long ifModifiedSince) {
		this.ifModifiedSince = ifModifiedSince;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public SSLContext getSSLContext() {
		return sslContext;
	}

	public void setSSLContext(SSLContext sslContext) {
		this.sslContext = sslContext;
	}

	public HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}

	public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}
}
