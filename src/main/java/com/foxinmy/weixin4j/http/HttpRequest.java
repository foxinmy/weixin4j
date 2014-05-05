package com.foxinmy.weixin4j.http;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;

public class HttpRequest {
	private final String CONTENT_CHARSET = "UTF-8";
	private final String ERROR_CODE_KEY = "errcode";
	private final String ERROR_MSG_KEY = "errmsg";
	private HttpClient client;

	public HttpRequest() {
		this(150, 30000, 30000, 1024 * 1024);
	}

	public HttpRequest(int maxConPerHost, int conTimeOutMs, int soTimeOutMs, int maxSize) {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(maxConPerHost);
		params.setConnectionTimeout(conTimeOutMs);
		params.setSoTimeout(soTimeOutMs);

		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		client = new HttpClient(clientParams, connectionManager);
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET);
	}

	public Response get(String url) throws WeixinException {
		Parameter[] empty = null;
		return get(url, empty);
	}

	public Response get(String url, Parameter... parameters) throws WeixinException {
		StringBuilder sb = new StringBuilder(url);
		if (parameters != null && parameters.length > 0) {
			if (url.indexOf("?") < 0) {
				sb.append(String.format("?%s=%s", parameters[0].getName(), parameters[0].getValue()));
			}
			for (int i = 0; i < parameters.length; i++) {
				sb.append(parameters[i].toGetPara());
			}
		}
		return doRequest(new GetMethod(sb.toString()));
	}

	public Response post(String url) throws WeixinException {
		Parameter[] empty = null;
		return post(url, empty);
	}

	public Response post(String url, Parameter... parameters) throws WeixinException {
		PostMethod method = new PostMethod(url);
		for (Parameter parameter : parameters) {
			method.addParameter(parameter.toPostPara());
		}

		return doRequest(method);
	}

	public Response post(String url, RequestEntity entity) throws WeixinException {
		PostMethod method = new PostMethod(url);
		method.setRequestEntity(entity);
		return doRequest(method);
	}

	public Response post(String url, Part... parts) throws WeixinException {
		PostMethod method = new PostMethod(url);
		method.getParams().setContentCharset(CONTENT_CHARSET);
		MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
		method.setRequestEntity(entity);
		return doRequest(method);
	}

	protected Response doRequest(HttpMethod method) throws WeixinException {

		try {

			method.getParams().setContentCharset(CONTENT_CHARSET);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
			int status = client.executeMethod(method);
			if (status != HttpStatus.SC_OK) {
				throw new WeixinException(status, getCause(method.getStatusCode()));
			}
			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			// 301或者302
			if (status == HttpStatus.SC_MOVED_PERMANENTLY || status == HttpStatus.SC_MOVED_TEMPORARILY) {
				throw new WeixinException(status, String.format("the page was redirected to %s", method.getResponseHeader("location")));
			}
			Response response = new Response(method.getResponseBodyAsString());
			response.setBody(method.getResponseBody());
			response.setStatusCode(status);
			response.setStatusText(method.getStatusText());
			response.setStream(method.getResponseBodyAsStream());

			Header contentType = method.getResponseHeader("Content-Type");
			if (contentType.getValue().indexOf("application/json") >= 0 || contentType.getValue().indexOf("text/plain") >= 0) {
				JSONObject jsonObj = response.getAsJson();
				if (jsonObj.containsKey(ERROR_CODE_KEY) && jsonObj.getIntValue(ERROR_CODE_KEY) != 0) {
					throw new WeixinException(jsonObj.getIntValue(ERROR_CODE_KEY), jsonObj.getString(ERROR_MSG_KEY));
				}
			}
			return response;
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	private String getCause(int statusCode) {
		return "error---------------" + statusCode;
	}
}
