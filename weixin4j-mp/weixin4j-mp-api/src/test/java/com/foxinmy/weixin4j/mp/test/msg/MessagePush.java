package com.foxinmy.weixin4j.mp.test.msg;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.foxinmy.weixin4j.exception.WeixinException;

public class MessagePush {

	private final String server = "http://localhost:8090";
	private final HttpClient httpClient;
	private final HttpPost httpPost;
	private final HttpGet httpGet;

	public MessagePush() {
		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost();
		httpPost.setURI(URI.create(server));

		httpGet = new HttpGet();
		httpGet.setURI(URI.create(server));
	}

	public String get(String para) throws WeixinException, IOException {
		httpGet.setURI(URI.create(server + para));
		HttpResponse httpResponse = httpClient.execute(httpGet);
		return entity(httpResponse);
	}

	public String push(String xml) throws WeixinException, IOException {
		return push("", xml);
	}

	public String push(String para, String xml) throws WeixinException,
			IOException {
		httpPost.setURI(URI.create(server + para));
		httpPost.setEntity(new StringEntity(xml, StandardCharsets.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		return entity(httpResponse);
	}

	private String entity(HttpResponse httpResponse) throws WeixinException,
			IOException {
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