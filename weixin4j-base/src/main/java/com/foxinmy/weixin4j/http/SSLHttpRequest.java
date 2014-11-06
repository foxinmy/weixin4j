package com.foxinmy.weixin4j.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.conn.scheme.Scheme;
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

	public SSLHttpRequest(String password, File file) throws IOException {
		this(password, new FileInputStream(file));
	}

	public SSLHttpRequest(String password, InputStream inputStream) {
		super();
		try {
			KeyStore trustStore = KeyStore.getInstance("PKCS12");
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
}
