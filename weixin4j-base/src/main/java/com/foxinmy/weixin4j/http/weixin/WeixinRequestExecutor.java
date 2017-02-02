package com.foxinmy.weixin4j.http.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.MimeType;
import com.foxinmy.weixin4j.http.URLParameter;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.HttpMultipartMode;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.FormUrlEntity;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.http.entity.StringEntity;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.message.XmlMessageConverter;
import com.foxinmy.weixin4j.logging.InternalLogLevel;
import com.foxinmy.weixin4j.logging.InternalLogger;
import com.foxinmy.weixin4j.logging.InternalLoggerFactory;
import com.foxinmy.weixin4j.util.Consts;

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

	protected final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	private static final String SUCCESS_CODE = ",0,success,";

	private final HttpClient httpClient;

	public WeixinRequestExecutor() {
		this.httpClient = HttpClientFactory.getInstance();
	}

	public WeixinRequestExecutor(HttpParams params) {
		this.httpClient = HttpClientFactory.getInstance(params);
	}

	/**
	 * Post方法执行微信请求
	 * 
	 * @param url
	 *            请求URL
	 * @param body
	 *            参数内容
	 * @return 微信响应
	 * @throws WeixinException
	 */
	public WeixinResponse post(String url, String body) throws WeixinException {
		HttpEntity entity = new StringEntity(body);
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		request.setEntity(entity);
		return doRequest(request);
	}

	/**
	 * Post方法执行微信请求，用于文件上传
	 * 
	 * @param url
	 *            请求URL
	 * @param bodyParts
	 *            文件内容
	 * @return 微信响应
	 * @throws WeixinException
	 */
	public WeixinResponse post(String url, FormBodyPart... bodyParts)
			throws WeixinException {
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE, null, Consts.UTF_8);
		for (FormBodyPart bodyPart : bodyParts) {
			entity.addPart(bodyPart);
		}
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		request.setEntity(entity);
		return doRequest(request);
	}

	/**
	 * Get方法执行微信请求
	 * 
	 * @param url
	 *            请求URL，如：https://api.weixin.qq.com/cgi-bin/token
	 * @param parameters
	 *            url上的参数，如:new URLParameter("appid",xxxxx)
	 * @return 微信响应
	 * @throws WeixinException
	 */
	public WeixinResponse get(String url, URLParameter... parameters)
			throws WeixinException {
		// always contain the question mark
		StringBuilder buf = new StringBuilder(url);
		if (parameters != null && parameters.length > 0) {
			buf.append("&").append(
					FormUrlEntity.formatParameters(Arrays.asList(parameters)));
		}
		HttpRequest request = new HttpRequest(HttpMethod.GET, buf.toString());
		return doRequest(request);
	}

	/**
	 * 执行微信请求
	 * 
	 * @param request
	 *            微信请求
	 * @return 微信响应
	 * @throws WeixinException
	 */
	public WeixinResponse doRequest(HttpRequest request) throws WeixinException {
		try {
			if (logger.isEnabled(InternalLogLevel.DEBUG)) {
				logger.debug("weixin request >> "
						+ request.getMethod()
						+ " "
						+ request.getURI().toString()
						+ (request.getEntity() instanceof StringEntity ? " >> "
								+ ((StringEntity) request.getEntity())
										.getContentString() : ""));
			}
			HttpResponse httpResponse = httpClient.execute(request);
			WeixinResponse response = new WeixinResponse(httpResponse);
			handleResponse(response);
			return response;
		} catch (HttpClientException e) {
			throw new WeixinException(e);
		}
	}

	/**
	 * 响应内容是否为流
	 * 
	 * @param response
	 *            微信响应
	 * @return true/false
	 */
	private boolean hasStreamMimeType(WeixinResponse response) {
		MimeType responseMimeType = MimeType.valueOf(response.getHeaders()
				.getContentType());
		for (MimeType streamMimeType : MimeType.STREAM_MIMETYPES) {
			if (streamMimeType.includes(responseMimeType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * handle the weixin response
	 * 
	 * @param response
	 *            微信请求响应
	 * @throws WeixinException
	 */
	protected void handleResponse(WeixinResponse response)
			throws WeixinException {
		boolean hasStreamMimeType = hasStreamMimeType(response);
		if (logger.isEnabled(InternalLogLevel.DEBUG)) {
			logger.debug("weixin response << "
					+ response.getProtocol()
					+ response.getStatus()
					+ " << "
					+ (hasStreamMimeType ? response.getHeaders()
							.getContentType() : response.getAsString()));
		}
		if (hasStreamMimeType) {
			return;
		}
		ApiResult result = response.getAsResult();
		if (!SUCCESS_CODE.contains(String.format(",%s,", result.getReturnCode()
				.toLowerCase()))) {
			throw new WeixinException(result.getReturnCode(),
					result.getReturnMsg());
		}
		if (XmlMessageConverter.GLOBAL.canConvert(XmlResult.class, response)) {
			try {
				XmlResult xmlResult = XmlMessageConverter.GLOBAL.convert(
						XmlResult.class, response);
				if (!SUCCESS_CODE.contains(String.format(",%s,", xmlResult
						.getResultCode().toLowerCase()))) {
					throw new WeixinException(xmlResult.getErrCode(),
							xmlResult.getErrCodeDes());
				}
			} catch (IOException e) {
				;
			}
		}
	}

	public HttpClient getExecuteClient() {
		return httpClient;
	}

	/**
	 * 创建 SSL微信请求对象
	 * 
	 * @param password
	 *            加载密钥
	 * @param inputStream
	 *            密钥内容
	 * @return 微信请求
	 * @throws WeixinException
	 */
	public WeixinRequestExecutor createSSLRequestExecutor(String password,
			InputStream inputStream) throws WeixinException {
		try {
			KeyStore keyStore = KeyStore.getInstance(Consts.PKCS12);
			keyStore.load(inputStream, password.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(Consts.SunX509);
			kmf.init(keyStore, password.toCharArray());
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), null,
					new java.security.SecureRandom());
			return createSSLRequestExecutor(sslContext);
		} catch (Exception e) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignore) {
				}
			}
			throw new WeixinException("Key load error", e);
		}
	}

	public WeixinRequestExecutor createSSLRequestExecutor(SSLContext sslContext) {
		if (sslContext == null) {
			throw new IllegalArgumentException("sslContext must not be empty");
		}
		HttpParams params = new HttpParams();
		params.setSSLContext(sslContext);
		return new WeixinRequestExecutor(params);
	}
}
