package com.foxinmy.weixin4j.http.weixin;

import java.io.IOException;
import java.util.Map;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.HttpMultipartMode;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.FormUrlEntity;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.http.entity.StringEntity;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.message.ApiResult;
import com.foxinmy.weixin4j.http.message.XmlMessageConverter;
import com.foxinmy.weixin4j.http.message.XmlResult;
import com.foxinmy.weixin4j.logging.InternalLogger;
import com.foxinmy.weixin4j.logging.InternalLoggerFactory;
import com.foxinmy.weixin4j.model.Consts;

/**
 * 负责微信请求的执行
 *
 * @className WeixinRequestExecutor
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月15日
 * @since JDK 1.6
 * @see
 */
public class WeixinRequestExecutor {

	protected final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

	private static final String SUCCESS_CODE = ",0,success,";

	protected final HttpClient httpClient;
	protected final HttpParams httpParams;

	public WeixinRequestExecutor() {
		this(new HttpParams());
	}

	public WeixinRequestExecutor(HttpParams httpParams) {
		this.httpClient = HttpClientFactory.getInstance();
		this.httpParams = httpParams;
	}

	public WeixinResponse get(String url) throws WeixinException {
		HttpRequest request = new HttpRequest(HttpMethod.GET, url);
		return doRequest(request);
	}

	public WeixinResponse get(String url, Map<String, String> parameters) throws WeixinException {
		StringBuilder buf = new StringBuilder(url);
		if (parameters != null && !parameters.isEmpty()) {
			if (url.indexOf("?") < 0) {
				buf.append("?");
			} else {
				buf.append("&");
			}
			buf.append(FormUrlEntity.formatParameters(parameters));
		}
		return doRequest(new HttpRequest(HttpMethod.GET, buf.toString()));
	}

	public WeixinResponse post(String url) throws WeixinException {
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		return doRequest(request);
	}

	public WeixinResponse post(String url, String body) throws WeixinException {
		HttpEntity entity = new StringEntity(body);
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		request.setEntity(entity);
		return doRequest(request);
	}

	public WeixinResponse post(String url, FormBodyPart... bodyParts) throws WeixinException {
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Consts.UTF_8);
		for (FormBodyPart bodyPart : bodyParts) {
			entity.addPart(bodyPart);
		}
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		request.setEntity(entity);
		return doRequest(request);
	}

	protected WeixinResponse doRequest(HttpRequest request) throws WeixinException {
		request.setParams(httpParams);
		try {
			logger.info("weixin request >> " + request.getMethod() + " " + request.getURI().toString());
			HttpResponse httpResponse = httpClient.execute(request);
			WeixinResponse response = new WeixinResponse(httpResponse);
			logger.info("weixin response << " + httpResponse.getProtocol() + httpResponse.getStatus() + ":"
					+ response.getAsString());
			handlResponse(response);
			return response;
		} catch (HttpClientException e) {
			throw new WeixinException(e);
		}
	}

	protected void handlResponse(WeixinResponse response) throws WeixinException {
		ApiResult result = response.getAsResult();
		if (!SUCCESS_CODE.contains(String.format(",%s,", result.getReturnCode().toLowerCase()))) {
			throw new WeixinException(result.getReturnCode(), result.getReturnMsg());
		}
		if (XmlMessageConverter.GLOBAL.canConvert(XmlResult.class, response)) {
			try {
				XmlResult xmlResult = XmlMessageConverter.GLOBAL.convert(XmlResult.class, response);
				if (!SUCCESS_CODE.contains(String.format(",%s,", xmlResult.getResultCode().toLowerCase()))) {
					throw new WeixinException(xmlResult.getErrCode(), xmlResult.getErrCodeDes());
				}
			} catch (IOException e) {
				;
			}
		}
	}

	public HttpClient getExecuteClient() {
		return httpClient;
	}

	public HttpParams getExecuteParams() {
		return httpParams;
	}
}
