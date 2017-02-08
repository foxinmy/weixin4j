package com.foxinmy.weixin4j.server.test;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.foxinmy.weixin4j.util.ServerToolkits;

/**
 * 发送消息请求到服务器
 * 
 * @className MessagePush
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月8日
 * @since JDK 1.6
 * @see
 */
public class MessagePush {

	private final String server = "https://localhost:30000";
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

	public String get(String para) throws IOException {
		httpGet.setURI(URI.create(server + para));
		HttpResponse httpResponse = httpClient.execute(httpGet);
		return entity(httpResponse);
	}

	public String push(String xml) throws IOException {
		return push("", xml);
	}

	public String push(String para, String xml) throws IOException {
		httpPost.setURI(URI.create(server + para));
		httpPost.setEntity(new StringEntity(xml, Consts.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		return entity(httpResponse);
	}

	private String entity(HttpResponse httpResponse) throws IOException {
		StatusLine statusLine = httpResponse.getStatusLine();

		int status = statusLine.getStatusCode();
		if (status != HttpStatus.SC_OK) {
			throw new IOException(Integer.toString(status) + "request fail");
		}
		if (status == HttpStatus.SC_MOVED_PERMANENTLY
				|| status == HttpStatus.SC_MOVED_TEMPORARILY) {
			throw new IOException(Integer.toString(status) + "uri moved");
		}
		return EntityUtils.toString(httpResponse.getEntity(),
				ServerToolkits.UTF_8);
	}
}