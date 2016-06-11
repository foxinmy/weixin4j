package com.foxinmy.weixin4j.http;

import java.net.Proxy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * Http请求参数
 *
 * @className HttpParams
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月13日
 * @since JDK 1.6
 * @see
 */
public final class HttpParams {

	private boolean allowUserInteraction;
	private int connectTimeout;
	private int socketTimeout;
	private int readTimeout;
	private int chunkSize;
	private boolean followRedirects;
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

	public HttpParams() {
		this(5000, 5000, 5000);
	}

	public HttpParams(int connectTimeout, int socketTimeout, int readTimeout) {
		this.allowUserInteraction = true;
		this.connectTimeout = connectTimeout;
		this.socketTimeout = socketTimeout;
		this.readTimeout = readTimeout;
		this.chunkSize = 4096;
		this.followRedirects = false;
	}

	public boolean isAllowUserInteraction() {
		return allowUserInteraction;
	}

	public HttpParams setAllowUserInteraction(boolean allowUserInteraction) {
		this.allowUserInteraction = allowUserInteraction;
		return this;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public HttpParams setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public HttpParams setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public HttpParams setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public HttpParams setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
		return this;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public HttpParams setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
		return this;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public HttpParams setProxy(Proxy proxy) {
		this.proxy = proxy;
		return this;
	}

	public SSLContext getSSLContext() {
		return sslContext;
	}

	public HttpParams setSSLContext(SSLContext sslContext) {
		this.sslContext = sslContext;
		return this;
	}

	public HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}

	public HttpParams setHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
		return this;
	}

	public static HttpParams copy(final HttpParams params) {
		return new HttpParams()
				.setAllowUserInteraction(params.isAllowUserInteraction())
				.setConnectTimeout(params.getConnectTimeout())
				.setSocketTimeout(params.getSocketTimeout())
				.setReadTimeout(params.getReadTimeout())
				.setChunkSize(params.getChunkSize())
				.setFollowRedirects(params.isFollowRedirects());
	}

	@Override
	public String toString() {
		return "HttpParams [allowUserInteraction=" + allowUserInteraction
				+ ", connectTimeout=" + connectTimeout + ", socketTimeout="
				+ socketTimeout + ", readTimeout=" + readTimeout
				+ ", chunkSize=" + chunkSize + ", followRedirects="
				+ followRedirects + ", proxy=" + proxy + ", sslContext="
				+ sslContext + ", hostnameVerifier=" + hostnameVerifier + "]";
	}
}
