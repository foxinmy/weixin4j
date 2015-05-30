package com.foxinmy.weixin4j.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.model.Consts;

/**
 * HTTP 简单实现
 * 
 * @className SimpleHttpClient
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public class SimpleHttpClient implements HttpClient {

	protected HostnameVerifier createHostnameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
	}

	protected X509TrustManager createX509TrustManager() {
		return new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(
					X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkClientTrusted(
					X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}
		};
	}

	protected HttpURLConnection createHttpConnection(URI uri)
			throws IOException {
		URL url = uri.toURL();
		if (uri.getScheme().equals("https")) {
			HttpsURLConnection connection = (HttpsURLConnection) url
					.openConnection();
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null,
						new X509TrustManager[] { createX509TrustManager() },
						new java.security.SecureRandom());
			} catch (GeneralSecurityException e) {
				throw new IOException(e);
			}
			connection.setSSLSocketFactory(sslContext.getSocketFactory());
			connection.setHostnameVerifier(createHostnameVerifier());
			return connection;
		} else {
			return (HttpURLConnection) url.openConnection();
		}
	}

	protected Map<String, String> createDefualtHeader() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("User-Agent", "simple httpclient/java 1.5");
		params.put("Accept", "text/xml,text/javascript");
		params.put("Accept-Charset", Consts.UTF_8.name());
		params.put("Accept-Encoding", Consts.UTF_8.name());
		return params;
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws IOException {
		// create connection object
		HttpURLConnection connection = createHttpConnection(request.getURI());
		// set parameters
		HttpParams params = request.getParams();
		connection.setRequestMethod(request.getMethod().name());
		connection.setAllowUserInteraction(params.getAllowUserInteraction());
		connection.setConnectTimeout(params.getConnectTimeout());
		connection.setReadTimeout(params.getReadTimeout());
		connection.setIfModifiedSince(params.getIfmodifiedsince());
		connection.setInstanceFollowRedirects(params.getFollowRedirects());
		connection.setDoInput(true);
		connection.setDoOutput(true);
		// set headers
		Header[] headers = request.getAllHeaders();
		for (Header header : headers) {
			connection.setRequestProperty(header.getName(), header.getValue());
		}
		for (Iterator<Entry<String, String>> headerIterator = createDefualtHeader()
				.entrySet().iterator(); headerIterator.hasNext();) {
			Entry<String, String> header = headerIterator.next();
			connection.setRequestProperty(header.getKey(), header.getValue());
		}
		HttpEntity httpEntity = null;
		if (request instanceof HttpEntityRequest) {
			httpEntity = ((HttpEntityRequest) request).getEntity();
			connection.setUseCaches(false);
			connection.setFixedLengthStreamingMode(httpEntity
					.getContentLength());
			connection.setRequestProperty("Content-Type", httpEntity
					.getContentType().getMimeType());
		}
		connection.connect();
		// open stream
		if (httpEntity != null) {
			OutputStream output = connection.getOutputStream();
			httpEntity.writeTo(output);
			output.flush();
			output.close();
		}
		// building response object
		StatusLine statusLine = new StatusLine(connection.getResponseCode(),
				connection.getResponseMessage());
		byte[] content = null;
		if (statusLine.getStatusCode() < 300) {
			ByteArrayOutputStream os = null;
			InputStream is = null;
			try {
				is = connection.getInputStream();
				os = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int n = 0;
				while (-1 != (n = is.read(buffer))) {
					os.write(buffer, 0, n);
				}
				content = os.toByteArray();
			} catch (IOException e) {
				;
			} finally {
				if (os != null) {
					os.close();
				}
				is.close();
			}
		}
		HttpResponse response = new HttpResponse();
		String httpVersion = connection.getHeaderField(null);
		if (httpVersion != null) {
			if (httpVersion.contains(HttpVersion.HTTP_1_0_STRING)) {
				response.setHttpVersion(HttpVersion.HTTP_1_0);
			} else if (httpVersion.contains(HttpVersion.HTTP_1_1_STRING)) {
				response.setHttpVersion(HttpVersion.HTTP_1_1);
			} else {
				response.setHttpVersion(new HttpVersion(httpVersion, true));
			}
		}
		List<Header> responseHeaders = new ArrayList<Header>();
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		for (Iterator<Entry<String, List<String>>> headerIterator = headerFields
				.entrySet().iterator(); headerIterator.hasNext();) {
			Entry<String, List<String>> headerEntry = headerIterator.next();
			for (String value : headerEntry.getValue()) {
				responseHeaders.add(new Header(headerEntry.getKey(), value));
			}
		}
		response.setHeaders(responseHeaders.toArray(new Header[responseHeaders
				.size()]));
		response.setStatusLine(statusLine);
		response.setContent(content);
		return response;
	}

	@Override
	public <T> T execute(HttpRequest request,
			ResponseHandler<? extends T> handler) throws IOException {
		return handler.handleResponse(execute(request));
	}
}
