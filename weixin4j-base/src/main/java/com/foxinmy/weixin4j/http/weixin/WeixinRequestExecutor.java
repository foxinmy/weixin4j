package com.foxinmy.weixin4j.http.weixin;

import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.HttpMultipartMode;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.FormUrlEntity;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.http.entity.StringEntity;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.WeixinErrorUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 负责微信请求的执行
 * 
 * @className WeixinRequestExecutor
 * @author jy
 * @date 2015年8月15日
 * @since JDK 1.7
 * @see
 */
public class WeixinRequestExecutor {

	protected final HttpClient httpClient;
	protected final HttpParams params;

	public WeixinRequestExecutor() {
		this(new HttpParams());
	}

	public WeixinRequestExecutor(HttpParams params) {
		this.httpClient = HttpClientFactory.getInstance();
		this.params = params;
	}

	public WeixinResponse get(String url) throws WeixinException {
		HttpRequest request = new HttpRequest(HttpMethod.GET, url);
		return doRequest(request);
	}

	public WeixinResponse get(String url, Map<String, String> parameters)
			throws WeixinException {
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

	public WeixinResponse post(String url, FormBodyPart... bodyParts)
			throws WeixinException {
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT,
				null, Consts.UTF_8);
		for (FormBodyPart bodyPart : bodyParts) {
			entity.addPart(bodyPart);
		}
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		request.setEntity(entity);
		return doRequest(request);
	}

	public WeixinResponse doRequest(HttpRequest request) throws WeixinException {
		request.setParams(params);
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			HttpStatus status = httpResponse.getStatus();
			HttpHeaders headers = httpResponse.getHeaders();
			if (status.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
				throw new WeixinException(String.format("request fail : %d-%s",
						status.getStatusCode(), status.getStatusText()));
			}
			WeixinResponse response = new WeixinResponse(headers, status,
					httpResponse.getBody());
			String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);
			String disposition = headers
					.getFirst(HttpHeaders.CONTENT_DISPOSITION);
			// json
			if (contentType
					.contains(ContentType.APPLICATION_JSON.getMimeType())
					|| (disposition != null && disposition.indexOf(".json") > 0)) {
				checkJson(response);
			} else if (contentType.contains(ContentType.TEXT_XML.getMimeType())) {
				checkXml(response);
			} else if (contentType.contains(ContentType.TEXT_PLAIN
					.getMimeType())
					|| contentType
							.contains(ContentType.TEXT_HTML.getMimeType())) {
				try {
					checkJson(response);
					return response;
				} catch (JSONException e) {
					;
				}
				try {
					checkXml(response);
					return response;
				} catch (IllegalArgumentException ex) {
					;
				}
				throw new WeixinException(response.getAsString());
			}
			return response;
		} catch (HttpClientException e) {
			throw new WeixinException(e);
		}
	}

	protected void checkJson(WeixinResponse response) throws WeixinException {
		JsonResult jsonResult = response.getAsJsonResult();
		response.setJsonResult(true);
		if (jsonResult.getCode() != 0) {
			if (StringUtil.isBlank(jsonResult.getDesc())) {
				jsonResult.setDesc(WeixinErrorUtil.getText(Integer
						.toString(jsonResult.getCode())));
			}
			throw new WeixinException(Integer.toString(jsonResult.getCode()),
					jsonResult.getDesc());
		}
	}

	protected void checkXml(WeixinResponse response) throws WeixinException {
		String xmlContent = response.getAsString();
		if (xmlContent.length() != xmlContent.replaceFirst("<retcode>",
				"<return_code>").length()) {
			// <?xml><root><data..../data></root>
			xmlContent = xmlContent.replaceFirst("<root>", "<xml>")
					.replaceFirst("<retcode>", "<return_code>")
					.replaceFirst("</retcode>", "</return_code>")
					.replaceFirst("<retmsg>", "<return_msg>")
					.replaceFirst("</retmsg>", "</return_msg>")
					.replaceFirst("</root>", "</xml>");
		}
		XmlResult xmlResult = XmlStream.fromXML(xmlContent, XmlResult.class);
		response.setText(xmlContent);
		response.setXmlResult(true);
		if ("0".equals(xmlResult.getReturnCode())) {
			return;
		}
		if (!com.foxinmy.weixin4j.model.Consts.SUCCESS
				.equalsIgnoreCase(xmlResult.getReturnCode())) {
			throw new WeixinException(xmlResult.getReturnCode(),
					xmlResult.getReturnMsg());
		}
		if (!com.foxinmy.weixin4j.model.Consts.SUCCESS
				.equalsIgnoreCase(xmlResult.getResultCode())) {
			throw new WeixinException(xmlResult.getErrCode(),
					xmlResult.getErrCodeDes());
		}
	}

	public HttpClient getExecuteClient() {
		return httpClient;
	}

	public HttpParams getExecuteParams() {
		return params;
	}
}
