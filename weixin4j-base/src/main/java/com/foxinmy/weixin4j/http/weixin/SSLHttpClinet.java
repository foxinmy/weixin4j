package com.foxinmy.weixin4j.http.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * ssl请求
 * 
 * @className SSLHttpClinet
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
public class SSLHttpClinet extends WeixinHttpClient {

	private final SSLContext sslContext;

	public SSLHttpClinet(String password, InputStream inputStream)
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
			throw new WeixinException(e.getMessage());
		}
	}

	public SSLHttpClinet(SSLContext sslContext) {
		this.sslContext = sslContext;
	}

	@Override
	protected HttpURLConnection createHttpConnection(URI uri)
			throws IOException {
		URL url = uri.toURL();
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();
		connection.setSSLSocketFactory(sslContext.getSocketFactory());
		connection.setHostnameVerifier(super.createHostnameVerifier());
		return connection;
	}
}
