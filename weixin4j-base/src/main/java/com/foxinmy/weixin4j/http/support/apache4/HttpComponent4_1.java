package com.foxinmy.weixin4j.http.support.apache4;

import java.io.IOException;

import org.apache.http.client.methods.HttpRequestBase;

import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;

/**
 * Requires Apache HttpComponents 4.2 or lower
 * 
 * @className HttpComponent4_1
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月18日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent4_1 extends HttpComponent4 {

	private final org.apache.http.client.HttpClient httpClient;

	public HttpComponent4_1(org.apache.http.client.HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			HttpRequestBase uriRequest = createRequest(request);
			org.apache.http.HttpResponse httpResponse = httpClient
					.execute(uriRequest);
			response = new HttpComponent4_1Response(httpResponse,
					getContent(httpResponse));
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
}
