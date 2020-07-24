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
 */
public final class HttpParams {
	/**
	 * 连接超时时间(单位毫秒)
	 */
	private final int connectTimeout;
	/**
	 * 读取超时时间(单位毫秒)
	 */
	private final int readTimeout;
	/**
	 * 最大连接数
	 */
	private final int maxConnections;
	/**
	 * 每个host最大连接数
	 */
	private final int maxConnectionsPerHost;
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

	/**
	 * connectTimeout = 15000,readTimeout=20000,maxConnection=100,maxConnectionPerHost=32
	 */
	public HttpParams() {
		this(5000, 15000,100,32);
	}

	public HttpParams(int connectTimeout, int readTimeout,int maxConnections,int maxConnectionsPerHost) {
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.maxConnections = maxConnections;
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
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
		return new HttpParams(params.getConnectTimeout(),
				params.getReadTimeout(),params.getMaxConnections(),params.getMaxConnectionsPerHost()).setProxy(params.getProxy())
				.setHostnameVerifier(params.getHostnameVerifier())
				.setSSLContext(params.getSSLContext());
	}

	@Override
	public String toString() {
		return "HttpParams [connectTimeout=" + connectTimeout
				+ ", readTimeout=" + readTimeout + ", proxy=" + proxy
				+ ", sslContext=" + sslContext + ", hostnameVerifier="
				+ hostnameVerifier + "]";
	}
}
