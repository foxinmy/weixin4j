package com.foxinmy.weixin4j.http.weixin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.Header;
import com.foxinmy.weixin4j.http.HttpGet;
import com.foxinmy.weixin4j.http.HttpPost;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.SimpleHttpClient;
import com.foxinmy.weixin4j.http.StatusLine;
import com.foxinmy.weixin4j.http.UrlEncodeParameter;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.HttpHeaders;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.ByteArrayEntity;
import com.foxinmy.weixin4j.http.entity.FileEntity;
import com.foxinmy.weixin4j.http.entity.FormUrlEntity;
import com.foxinmy.weixin4j.http.entity.StringEntity;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.util.ErrorUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.XmlStream;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;

public class WeixinHttpClient extends SimpleHttpClient {

	@Override
	protected Map<String, String> createDefualtHeader() {
		Map<String, String> params = super.createDefualtHeader();
		params.put(HttpHeaders.USER_AGENT, "weixin4j client/1.5");
		return params;
	}

	public WeixinResponse get(String url) throws WeixinException {
		return get(url, (UrlEncodeParameter[]) null);
	}

	public WeixinResponse get(String url, Map<String, String> para)
			throws WeixinException {
		return get(
				String.format("%s?%s", url,
						MapUtil.toJoinString(para, false, false, null)),
				(UrlEncodeParameter[]) null);
	}

	public WeixinResponse get(String url, UrlEncodeParameter... parameters)
			throws WeixinException {
		StringBuilder sb = new StringBuilder(url);
		if (parameters != null && parameters.length > 0) {
			if (url.indexOf("?") < 0) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(parameters[0].encodingParameter());
			if (parameters.length > 1) {
				for (int i = 1; i < parameters.length; i++) {
					sb.append(parameters[i].encodingParameter());
				}
			}
		}
		return doRequest(new HttpGet(sb.toString()));
	}

	public WeixinResponse post(String url) throws WeixinException {
		return post(url, (UrlEncodeParameter[]) null);
	}

	public WeixinResponse post(String url, UrlEncodeParameter... parameters)
			throws WeixinException {
		HttpPost method = new HttpPost(url);
		if (parameters != null && parameters.length > 0) {
			method.setEntity(new FormUrlEntity(Arrays.asList(parameters)));
		}
		return doRequest(method);
	}

	public WeixinResponse post(String url, String body) throws WeixinException {
		HttpPost method = new HttpPost(url);
		method.setEntity(new StringEntity(body));
		return doRequest(method);
	}

	public WeixinResponse post(String url, byte[] bytes) throws WeixinException {
		HttpPost method = new HttpPost(url);
		method.setEntity(new ByteArrayEntity(bytes,
				ContentType.MULTIPART_FORM_DATA));
		return doRequest(method);
	}

	public WeixinResponse post(String url, File file) throws WeixinException {
		HttpPost method = new HttpPost(url);
		method.setEntity(new FileEntity(file));
		return doRequest(method);
	}

	public WeixinResponse post(String url, FormBodyPart... paramters)
			throws WeixinException {
		HttpPost method = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity();
		for (FormBodyPart paramter : paramters) {
			entity.addPart(paramter);
		}
		method.setEntity(entity);

		return doRequest(method);
	}

	protected WeixinResponse doRequest(HttpRequest request)
			throws WeixinException {
		WeixinResponse response = null;
		try {
			HttpResponse httpResponse = execute(request);
			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new WeixinException(String.format("request fail : %d-%s",
						statusLine.getStatusCode(), statusLine.getStatusText()));
			}
			response = new WeixinResponse();
			response.setContent(httpResponse.getContent());
			response.setHeaders(httpResponse.getAllHeaders());
			response.setHttpVersion(httpResponse.getHttpVersion());
			response.setStatusLine(httpResponse.getStatusLine());
			Header contentType = httpResponse
					.getFirstHeader(HttpHeaders.CONTENT_TYPE);
			Header disposition = httpResponse
					.getFirstHeader("Content-disposition");
			// json
			if (contentType.getValue().contains(
					ContentType.APPLICATION_JSON.getMimeType())
					|| (disposition != null && disposition.getValue().indexOf(
							".json") > 0)) {
				checkJson(response);
			} else if (contentType.getValue().contains(
					ContentType.TEXT_XML.getMimeType())) {
				checkXml(response);
			} else if (contentType.getValue().contains(
					ContentType.TEXT_PLAIN.getMimeType())
					|| contentType.getValue().contains(
							ContentType.TEXT_HTML.getMimeType())) {
				try {
					checkJson(response);
					return response;
				} catch (JSONException e) {
					;
				}
				try {
					checkXml(response);
					return response;
				} catch (CannotResolveClassException ex) {
					;
				}
				throw new WeixinException(response.getAsString());
			}
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		} finally {
			// request.releaseConnection();
		}
		return response;
	}

	private void checkJson(WeixinResponse response) throws WeixinException {
		JsonResult jsonResult = response.getAsJsonResult();
		response.setJsonResult(true);
		if (jsonResult.getCode() != 0) {
			if (StringUtil.isBlank(jsonResult.getDesc())) {
				jsonResult.setDesc(ErrorUtil.getText(Integer
						.toString(jsonResult.getCode())));
			}
			throw new WeixinException(Integer.toString(jsonResult.getCode()),
					jsonResult.getDesc());
		}
	}

	private void checkXml(WeixinResponse response) throws WeixinException {
		XmlResult xmlResult = null;
		try {
			xmlResult = response.getAsXmlResult();
		} catch (CannotResolveClassException ex) {
			// <?xml><root><data..../data></root>
			String newXml = response.getAsString()
					.replaceFirst("<root>", "<xml>")
					.replaceFirst("<retcode>", "<return_code>")
					.replaceFirst("</retcode>", "</return_code>")
					.replaceFirst("<retmsg>", "<return_msg>")
					.replaceFirst("</retmsg>", "</return_msg>")
					.replaceFirst("</root>", "</xml>");
			xmlResult = XmlStream.get(newXml, XmlResult.class);
			response.setContent(newXml.getBytes(Consts.UTF_8));
		}
		response.setXmlResult(true);
		if (xmlResult.getReturnCode().equals("0")) {
			return;
		}
		if (!xmlResult.getReturnCode().equalsIgnoreCase(
				com.foxinmy.weixin4j.model.Consts.SUCCESS)) {
			throw new WeixinException(xmlResult.getReturnCode(),
					xmlResult.getReturnMsg());
		}
		if (!xmlResult.getResultCode().equalsIgnoreCase(
				com.foxinmy.weixin4j.model.Consts.SUCCESS)) {
			throw new WeixinException(xmlResult.getErrCode(),
					xmlResult.getErrCodeDes());
		}
	}
}
