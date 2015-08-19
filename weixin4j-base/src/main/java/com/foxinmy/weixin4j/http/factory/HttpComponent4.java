package com.foxinmy.weixin4j.http.factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.InputStreamEntity;

import com.foxinmy.weixin4j.http.AbstractHttpClient;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * Apache HttpComponents 4.x
 * 
 * @className HttpComponent4
 * @author jy
 * @date 2015年8月18日
 * @since JDK 1.7
 * @see
 */
public abstract class HttpComponent4 extends AbstractHttpClient {
	protected static final Map<HttpMethod, HttpRequestBase> methodMap;
	static {
		methodMap = new HashMap<HttpMethod, HttpRequestBase>();
		methodMap.put(HttpMethod.GET, new HttpGet());
		methodMap.put(HttpMethod.HEAD, new HttpHead());
		methodMap.put(HttpMethod.POST, new HttpPost());
		methodMap.put(HttpMethod.PUT, new HttpPut());
		methodMap.put(HttpMethod.DELETE, new HttpDelete());
		methodMap.put(HttpMethod.OPTIONS, new HttpOptions());
		methodMap.put(HttpMethod.TRACE, new HttpTrace());
	}

	protected void addHeaders(HttpHeaders headers, HttpRequestBase uriRequest) {
		if (headers != null) {
			for (Iterator<Entry<String, List<String>>> headerIterator = headers
					.entrySet().iterator(); headerIterator.hasNext();) {
				Entry<String, List<String>> header = headerIterator.next();
				if (HttpHeaders.COOKIE.equalsIgnoreCase(header.getKey())) {
					uriRequest.addHeader(header.getKey(),
							StringUtil.join(header.getValue(), ';'));
				} else {
					for (String headerValue : header.getValue()) {
						uriRequest.addHeader(header.getKey(),
								headerValue != null ? headerValue : "");
					}
				}
			}
		}
	}

	protected void addEntity(HttpEntity entity, HttpRequestBase uriRequest)
			throws IOException {
		if (entity != null) {
			AbstractHttpEntity httpEntity = null;
			if (entity instanceof MultipartEntity) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				entity.writeTo(os);
				os.flush();
				httpEntity = new org.apache.http.entity.ByteArrayEntity(
						os.toByteArray());
				os.close();
			} else {
				httpEntity = new InputStreamEntity(entity.getContent(),
						entity.getContentLength());
			}
			httpEntity.setContentType(entity.getContentType().toString());
			((HttpEntityEnclosingRequestBase) uriRequest).setEntity(httpEntity);
		}
	}

	protected static class CustomHostnameVerifier implements
			X509HostnameVerifier {

		private final HostnameVerifier hostnameVerifier;

		public CustomHostnameVerifier(HostnameVerifier hostnameVerifier) {
			this.hostnameVerifier = hostnameVerifier;
		}

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return hostnameVerifier.verify(hostname, session);
		}

		@Override
		public void verify(String host, SSLSocket ssl) throws IOException {
		}

		@Override
		public void verify(String host, X509Certificate cert)
				throws SSLException {
		}

		@Override
		public void verify(String host, String[] cns, String[] subjectAlts)
				throws SSLException {
		}
	}
}
