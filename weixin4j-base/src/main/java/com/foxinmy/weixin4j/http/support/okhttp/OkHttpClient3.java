package com.foxinmy.weixin4j.http.support.okhttp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map.Entry;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.BufferedSink;

import com.foxinmy.weixin4j.http.AbstractHttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * OkHttp3
 * 
 * @className OkHttpClient3
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月25日
 * @since JDK 1.6
 */
public class OkHttpClient3 extends AbstractHttpClient {

	private final OkHttpClient okClient;

	public OkHttpClient3(OkHttpClient okClient) {
		this.okClient = okClient;
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			okhttp3.Request okRequest = createRequest(request);
			okhttp3.Response okResponse = okClient.newCall(okRequest).execute();
			response = new OkHttpResponse3(okResponse, okResponse.body()
					.bytes());
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
	 * create OkRequest
	 */
	protected okhttp3.Request createRequest(HttpRequest request)
			throws HttpClientException {
		okhttp3.Request.Builder requestBuilder = createBuilder(request);
		resolveHeaders(requestBuilder, request);
		resolveContent(requestBuilder, request);
		return requestBuilder.build();
	}

	/**
	 * create Request.Builder
	 * 
	 * @throws HttpClientException
	 */
	protected okhttp3.Request.Builder createBuilder(HttpRequest request)
			throws HttpClientException {
		okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
		try {
			requestBuilder.url(request.getURI().toURL());
		} catch (MalformedURLException e) {
			throw new HttpClientException("format URI error", e);
		}
		return requestBuilder;
	}

	/**
	 * resolve Request.Headers
	 * */
	protected void resolveHeaders(okhttp3.Request.Builder builder,
			HttpRequest request) {
		com.foxinmy.weixin4j.http.HttpHeaders headers = request.getHeaders();
		if (headers == null) {
			headers = new HttpHeaders();
		}
		// Add default accept headers
		if (!headers.containsKey(HttpHeaders.ACCEPT)) {
			headers.set(HttpHeaders.ACCEPT, "*/*");
		}
		// Add default user agent
		if (!headers.containsKey(HttpHeaders.USER_AGENT)) {
			headers.set(HttpHeaders.USER_AGENT, "square/okhttp3");
		}
		for (Entry<String, List<String>> header : headers.entrySet()) {
			if (HttpHeaders.COOKIE.equalsIgnoreCase(header.getKey())) {
				builder.header(header.getKey(),
						StringUtil.join(header.getValue(), ';'));
			} else {
				for (String headerValue : header.getValue()) {
					builder.header(header.getKey(),
							headerValue != null ? headerValue : "");
				}
			}
		}
	}

	/**
	 * resolve Request.Content
	 */
	protected void resolveContent(okhttp3.Request.Builder builder,
			HttpRequest request) throws HttpClientException {
		final HttpEntity entity = request.getEntity();
		okhttp3.RequestBody body = null;
		if (entity != null) {
			body = new RequestBody() {

				@Override
				public long contentLength() throws IOException {
					return entity.getContentLength();
				}

				@Override
				public void writeTo(BufferedSink sink) throws IOException {
					entity.writeTo(sink.outputStream());
				}

				@Override
				public MediaType contentType() {
					return MediaType.parse(entity.getContentType().toString());
				}
			};
		}
		builder.method(request.getMethod().name(), body);
	}
}