package com.foxinmy.weixin4j.http.factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.foxinmy.weixin4j.http.AbstractHttpClient;
import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * Requires Apache HttpComponents 4.3 or higher
 * 
 * @className HttpComponent4_2
 * @author jy
 * @date 2015年8月18日
 * @since JDK 1.7
 * @see
 */
public class HttpComponent4_2 extends AbstractHttpClient implements HttpClient {

	private CloseableHttpClient httpClient;
	private final Map<HttpMethod, HttpRequestBase> methodMap;

	public HttpComponent4_2() {
		this.httpClient = HttpClients.createDefault();
		this.methodMap = new HashMap<HttpMethod, HttpRequestBase>();
		methodMap.put(HttpMethod.GET, new HttpGet());
		methodMap.put(HttpMethod.HEAD, new HttpHead());
		methodMap.put(HttpMethod.POST, new HttpPost());
		methodMap.put(HttpMethod.PUT, new HttpPut());
		methodMap.put(HttpMethod.DELETE, new HttpDelete());
		methodMap.put(HttpMethod.OPTIONS, new HttpOptions());
		methodMap.put(HttpMethod.TRACE, new HttpTrace());
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			HttpRequestBase uriRequest = methodMap.get(request.getMethod());
			uriRequest.setURI(request.getURI());
			HttpParams params = request.getParams();
			if (params != null) {
				Builder requestConfig = RequestConfig.custom()
						.setSocketTimeout(params.getSocketTimeout())
						.setConnectTimeout(params.getConnectTimeout())
						.setConnectionRequestTimeout(params.getReadTimeout());
				if (params.getProxy() != null) {
					InetSocketAddress socketAddress = (InetSocketAddress) params
							.getProxy().address();
					HttpHost proxy = new HttpHost(socketAddress.getHostName(),
							socketAddress.getPort());
					requestConfig.setProxy(proxy);
				}
				if (params.getSSLContext() != null) {
					httpClient = HttpClients.custom()
							.setSslcontext(params.getSSLContext()).build();
				}
				uriRequest.setConfig(requestConfig.build());
			}
			HttpHeaders headers = request.getHeaders();
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
			HttpEntity entity = request.getEntity();
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
				((HttpEntityEnclosingRequestBase) uriRequest)
						.setEntity(httpEntity);
			}
			CloseableHttpResponse httpResponse = httpClient.execute(uriRequest);
			response = new HttpComponent4_2Response(httpResponse);
		} catch (IOException e) {
			throw new HttpClientException("I/O error on "
					+ request.getMethod().name() + " request for \""
					+ request.getURI().toString() + "\":" + e.getMessage(), e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return response;
	}
}
