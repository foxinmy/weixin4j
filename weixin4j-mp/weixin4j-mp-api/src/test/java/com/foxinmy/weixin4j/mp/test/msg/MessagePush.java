package com.foxinmy.weixin4j.mp.test.msg;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.foxinmy.weixin4j.exception.WeixinException;

public class MessagePush {

	private HttpClient httpClient;
	private final HttpPost httpPost;

	public MessagePush() {
		this.httpClient = new DefaultHttpClient();
		ResourceBundle config = ResourceBundle.getBundle("netty");
		httpPost = new HttpPost();
		httpPost.setURI(URI.create(String.format("http://localhost:%s",
				Integer.parseInt(config.getString("port")))));
	}

	public String push(String xml) throws WeixinException, IOException {
		httpPost.setEntity(new StringEntity(xml, StandardCharsets.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		StatusLine statusLine = httpResponse.getStatusLine();

		int status = statusLine.getStatusCode();
		if (status != HttpStatus.SC_OK) {
			throw new WeixinException(Integer.toString(status), "request fail");
		}
		if (status == HttpStatus.SC_MOVED_PERMANENTLY
				|| status == HttpStatus.SC_MOVED_TEMPORARILY) {
			throw new WeixinException(Integer.toString(status), "uri moved");
		}
		return EntityUtils.toString(httpResponse.getEntity(),
				StandardCharsets.UTF_8);
	}
}
