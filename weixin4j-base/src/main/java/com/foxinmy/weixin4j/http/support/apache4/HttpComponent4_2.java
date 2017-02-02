package com.foxinmy.weixin4j.http.support.apache4;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;

/**
 * Requires Apache HttpComponents 4.3 or higher
 * 
 * @className HttpComponent4_2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月18日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent4_2 extends HttpComponent4 {

	private final CloseableHttpClient httpClient;

	public HttpComponent4_2(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			HttpRequestBase uriRequest = createRequest(request);
			CloseableHttpResponse httpResponse = httpClient.execute(uriRequest);
			response = new HttpComponent4_2Response(httpResponse,
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
