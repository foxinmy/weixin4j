package com.foxinmy.weixin4j.http.weixin;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * 微信ssl请求
 * 
 * @className WeixinSSLRequestExecutor
 * @author jy
 * @date 2015年8月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinSSLRequestExecutor extends WeixinRequestExecutor {

	private final SSLContext sslContext;

	public WeixinSSLRequestExecutor(String password, InputStream inputStream)
			throws WeixinException {
		try {
			KeyStore keyStore = KeyStore
					.getInstance(com.foxinmy.weixin4j.model.Consts.PKCS12);
			keyStore.load(inputStream, password.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(com.foxinmy.weixin4j.model.Consts.SunX509);
			kmf.init(keyStore, password.toCharArray());
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), null,
					new java.security.SecureRandom());
		} catch (Exception e) {
			throw new WeixinException("Key load error", e);
		}
		params.setSSLContext(sslContext);
	}

	public WeixinSSLRequestExecutor(SSLContext sslContext) {
		this.sslContext = sslContext;
		params.setSSLContext(sslContext);
	}

	public SSLContext getSSLContext() {
		return sslContext;
	}
}
