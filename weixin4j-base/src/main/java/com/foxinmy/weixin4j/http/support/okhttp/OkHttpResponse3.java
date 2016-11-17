package com.foxinmy.weixin4j.http.support.okhttp;

import okhttp3.Protocol;

import com.foxinmy.weixin4j.http.AbstractHttpResponse;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.HttpVersion;

/**
 * OkHttp Response:Requires OkHttp 3.x or higher
 * 
 * @className OkHttpResponse3
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月25日
 * @since JDK 1.6
 */
public class OkHttpResponse3 extends AbstractHttpResponse {
	private final okhttp3.Response response;
	private HttpHeaders headers;
	private HttpVersion protocol;
	private HttpStatus status;

	public OkHttpResponse3(okhttp3.Response response, byte[] content) {
		super(content);
		this.response = response;
	}

	@Override
	public HttpHeaders getHeaders() {
		if (this.headers == null) {
			this.headers = new HttpHeaders();
			okhttp3.Headers headers = this.response.headers();
			for (int i = 0; i < headers.size(); i++) {
				this.headers.add(headers.name(i), headers.value(i));
			}
		}
		return this.headers;
	}

	@Override
	public HttpVersion getProtocol() {
		if (protocol == null) {
			String protocol = this.response.protocol().toString().split("/")[0];
			boolean keepAlive = KEEP_ALIVE.equalsIgnoreCase(this.response
					.header("Connection"));
			if (this.response.protocol() == Protocol.HTTP_1_0) {
				return new HttpVersion(protocol, 1, 0, keepAlive);
			} else if (this.response.protocol() == Protocol.HTTP_1_1) {
				return new HttpVersion(protocol, 1, 1, keepAlive);
			} else if (this.response.protocol() == Protocol.HTTP_2) {
				return new HttpVersion(protocol, 2, 0, keepAlive);
			} else if (this.response.protocol() == Protocol.SPDY_3) {
				return new HttpVersion(protocol, 3, 0, keepAlive);
			} else {
				this.protocol = new HttpVersion(protocol, keepAlive);
			}
		}
		return protocol;
	}

	@Override
	public HttpStatus getStatus() {
		if (status == null) {
			status = new HttpStatus(this.response.code(),
					this.response.message());
		}
		return status;
	}

	@Override
	public void close() {
		response.close();
	}
}
