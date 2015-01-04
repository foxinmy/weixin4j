package com.foxinmy.weixin4j.http;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * ssl请求
 * 
 * @className SSLHttpRequest
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
public class SSLHttpRequest extends HttpRequest {

	public SSLHttpRequest(String password, InputStream inputStream) {
		super();
		try {
			KeyStore trustStore = KeyStore
					.getInstance(com.foxinmy.weixin4j.model.Consts.PKCS12);
			trustStore.load(inputStream, password.toCharArray());
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore,
					password);
			client.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ignore) {
				;
			}
		}
	}

	public SSLHttpRequest(SSLContext sslContext) {
		super();
		SchemeSocketFactory socketFactory = new SSLSocketFactory(sslContext);
		client.getConnectionManager().getSchemeRegistry()
				.register(new Scheme("https", 443, socketFactory));
	}
}
