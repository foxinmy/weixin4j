package com.foxinmy.weixin4j.http.support.apache3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.TraceMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

import com.foxinmy.weixin4j.http.AbstractHttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * Requires commons-httpclient 3.0 or higher
 *
 * @className HttpComponent3
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月18日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent3 extends AbstractHttpClient {

	private final org.apache.commons.httpclient.HttpClient httpClient;

	public HttpComponent3(org.apache.commons.httpclient.HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			org.apache.commons.httpclient.HttpMethod httpMethod = createRequest(request);
			httpClient.executeMethod(httpMethod);
			response = new HttpComponent3Response(httpMethod);
			handleResponse(response);
		} catch (IOException e) {
			throw new HttpClientException("I/O error on "
					+ request.getMethod().name() + " request for \""
					+ request.getURI().toString(), e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return response;
	}

	/**
	 * Create HttpRequest
	 */
	protected org.apache.commons.httpclient.HttpMethod createRequest(
			HttpRequest request) throws HttpClientException, IOException {
		org.apache.commons.httpclient.HttpMethod httpMethod = createMethod(request);
		resolveHeaders(request, httpMethod);
		resolveContent(request, httpMethod);
		return httpMethod;
	}

	/**
	 * Create HttpMethod
	 */
	protected org.apache.commons.httpclient.HttpMethod createMethod(
			HttpRequest request) throws HttpClientException {
		HttpMethod method = request.getMethod();
		org.apache.commons.httpclient.HttpMethod httpMethod = null;
		try {
			URI uri = new URI(request.getURI().toString(), false,
					Consts.UTF_8.name());
			if (method == HttpMethod.GET) {
				httpMethod = new GetMethod();
			} else if (method == HttpMethod.HEAD) {
				httpMethod = new HeadMethod();
			} else if (method == HttpMethod.POST) {
				httpMethod = new PostMethod();
			} else if (method == HttpMethod.PUT) {
				return new PutMethod();
			} else if (method == HttpMethod.DELETE) {
				httpMethod = new DeleteMethod();
			} else if (method == HttpMethod.OPTIONS) {
				httpMethod = new OptionsMethod();
			} else if (method == HttpMethod.TRACE) {
				return new TraceMethod(uri.getEscapedURI());
			} else {
				throw new HttpClientException("unknown request method "
						+ method + " for " + uri);
			}
			httpMethod.setURI(uri);
		} catch (IOException e) {
			throw new HttpClientException("I/O error on " + method.name()
					+ " setURI for \"" + request.toString() + "\":"
					+ e.getMessage(), e);
		}
		return httpMethod;
	}

	/**
	 * Resolve Headers
	 */
	protected void resolveHeaders(HttpRequest request,
			org.apache.commons.httpclient.HttpMethod httpMethod) {
		com.foxinmy.weixin4j.http.HttpHeaders headers = request.getHeaders();
		if (headers == null) {
			headers = new HttpHeaders();
		}
		if (!headers.containsKey(HttpHeaders.HOST)) {
			headers.set(HttpHeaders.HOST, request.getURI().getHost());
		}
		// Add default accept headers
		if (!headers.containsKey(HttpHeaders.ACCEPT)) {
			headers.set(HttpHeaders.ACCEPT, "*/*");
		}
		// Add default user agent
		if (!headers.containsKey(HttpHeaders.USER_AGENT)) {
			headers.set(HttpHeaders.USER_AGENT, "apache/httpclient3");
		}
		for (Entry<String, List<String>> header : headers.entrySet()) {
			if (HttpHeaders.COOKIE.equalsIgnoreCase(header.getKey())) {
				httpMethod.setRequestHeader(header.getKey(),
						StringUtil.join(header.getValue(), ';'));
			} else {
				for (String headerValue : header.getValue()) {
					httpMethod.setRequestHeader(header.getKey(),
							headerValue != null ? headerValue : "");
				}
			}
		}
	}

	/**
	 * Resolve Content
	 */
	protected void resolveContent(HttpRequest request,
			org.apache.commons.httpclient.HttpMethod httpMethod)
			throws IOException {
		HttpEntity entity = request.getEntity();
		if (entity != null) {
			if (entity.getContentLength() > 0l) {
				httpMethod.addRequestHeader(HttpHeaders.CONTENT_LENGTH,
						Long.toString(entity.getContentLength()));
			}
			if (entity.getContentType() != null) {
				httpMethod.addRequestHeader(HttpHeaders.CONTENT_TYPE, entity
						.getContentType().toString());
			}
			RequestEntity requestEntity = null;
			if (entity instanceof MultipartEntity) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				entity.writeTo(os);
				os.flush();
				requestEntity = new ByteArrayRequestEntity(os.toByteArray(),
						entity.getContentType().toString());
				os.close();
			} else {
				requestEntity = new InputStreamRequestEntity(
						entity.getContent(), entity.getContentType().toString());
			}
			((EntityEnclosingMethod) httpMethod)
					.setRequestEntity(requestEntity);
		}
	}

	public static class SSLProtocolSocketFactory implements
			SecureProtocolSocketFactory {

		private final SSLContext sslContext;

		public SSLProtocolSocketFactory(SSLContext sslContext) {
			this.sslContext = sslContext;
		}

		@Override
		public Socket createSocket(String host, int port,
				InetAddress localAddress, int localPort) throws IOException,
				UnknownHostException {
			return sslContext.getSocketFactory().createSocket(host, port,
					localAddress, localPort);
		}

		@Override
		public Socket createSocket(String host, int port,
				InetAddress localAddress, int localPort,
				HttpConnectionParams params) throws IOException,
				UnknownHostException, ConnectTimeoutException {
			if (params == null) {
				throw new IllegalArgumentException("Parameters may not be null");
			}
			int timeout = params.getConnectionTimeout();
			if (timeout == 0) {
				return createSocket(host, port, localAddress, localPort);
			} else {
				return ControllerThreadSocketFactory.createSocket(this, host,
						port, localAddress, localPort, timeout);
			}
		}

		@Override
		public Socket createSocket(String host, int port) throws IOException,
				UnknownHostException {
			return sslContext.getSocketFactory().createSocket(host, port);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}
	}
}
