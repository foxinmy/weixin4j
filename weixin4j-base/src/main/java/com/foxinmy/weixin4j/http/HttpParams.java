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

	private final boolean allowUserInteraction;
	private final int connectTimeout;
	private final int socketTimeout;
	private final int readTimeout;
	private final int chunkSize;
	private final boolean followRedirects;
	/**
	 * 代理对象
	 */
	private final Proxy proxy;
	/**
	 * SSL对象
	 */
	private final SSLContext sslContext;
	/**
	 * hostname对象
	 */
	private final HostnameVerifier hostnameVerifier;

	HttpParams(boolean allowUserInteraction, int connectTimeout,
			int socketTimeout, int readTimeout, int chunkSize,
			boolean followRedirects, Proxy proxy, SSLContext sslContext,
			HostnameVerifier hostnameVerifier) {
		this.allowUserInteraction = allowUserInteraction;
		this.connectTimeout = connectTimeout;
		this.socketTimeout = socketTimeout;
		this.readTimeout = readTimeout;
		this.chunkSize = chunkSize;
		this.followRedirects = followRedirects;
		this.proxy = proxy;
		this.sslContext = sslContext;
		this.hostnameVerifier = hostnameVerifier;
	}

	public boolean isAllowUserInteraction() {
		return allowUserInteraction;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public SSLContext getSSLContext() {
		return sslContext;
	}

	public HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}

	public static HttpParams.Builder custom() {
		return new Builder();
	}

	public static HttpParams.Builder copy(final HttpParams params) {
		return new Builder()
				.setAllowUserInteraction(params.isAllowUserInteraction())
				.setConnectTimeout(params.getConnectTimeout())
				.setSocketTimeout(params.getSocketTimeout())
				.setReadTimeout(params.getReadTimeout())
				.setChunkSize(params.getChunkSize())
				.setFollowRedirects(params.isFollowRedirects());
	}

	public static class Builder {
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

		Builder() {
			this.allowUserInteraction = true;
			this.connectTimeout = 5000;
			this.socketTimeout = 5000;
			this.readTimeout = 5000;
			this.chunkSize = 4096;
			this.followRedirects = false;
		}

		public boolean isAllowUserInteraction() {
			return allowUserInteraction;
		}

		public Builder setAllowUserInteraction(boolean allowUserInteraction) {
			this.allowUserInteraction = allowUserInteraction;
			return this;
		}

		public int getConnectTimeout() {
			return connectTimeout;
		}

		public Builder setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}

		public int getSocketTimeout() {
			return socketTimeout;
		}

		public Builder setSocketTimeout(int socketTimeout) {
			this.socketTimeout = socketTimeout;
			return this;
		}

		public int getReadTimeout() {
			return readTimeout;
		}

		public Builder setReadTimeout(int readTimeout) {
			this.readTimeout = readTimeout;
			return this;
		}

		public int getChunkSize() {
			return chunkSize;
		}

		public Builder setChunkSize(int chunkSize) {
			this.chunkSize = chunkSize;
			return this;
		}

		public boolean isFollowRedirects() {
			return followRedirects;
		}

		public Builder setFollowRedirects(boolean followRedirects) {
			this.followRedirects = followRedirects;
			return this;
		}

		public Proxy getProxy() {
			return proxy;
		}

		public Builder setProxy(Proxy proxy) {
			this.proxy = proxy;
			return this;
		}

		public SSLContext getSslContext() {
			return sslContext;
		}

		public Builder setSslContext(SSLContext sslContext) {
			this.sslContext = sslContext;
			return this;
		}

		public HostnameVerifier getHostnameVerifier() {
			return hostnameVerifier;
		}

		public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
			this.hostnameVerifier = hostnameVerifier;
			return this;
		}

		public HttpParams build() {
			return new HttpParams(allowUserInteraction, connectTimeout,
					socketTimeout, readTimeout, chunkSize, followRedirects,
					proxy, sslContext, hostnameVerifier);
		}
	}
}
