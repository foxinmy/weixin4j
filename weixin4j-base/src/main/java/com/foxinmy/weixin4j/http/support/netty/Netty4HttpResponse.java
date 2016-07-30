package com.foxinmy.weixin4j.http.support.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Map;

import com.foxinmy.weixin4j.http.AbstractHttpResponse;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.HttpVersion;

/**
 * Netty Response::Requires Netty 4.x or higher
 *
 * @className Netty4HttpResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月30日
 * @since JDK 1.6
 * @see
 */
public class Netty4HttpResponse extends AbstractHttpResponse {

	private final ChannelHandlerContext context;
	private final FullHttpResponse response;

	private HttpVersion protocol;
	private HttpStatus status;
	private HttpHeaders headers;

	public Netty4HttpResponse(ChannelHandlerContext context,
			FullHttpResponse response, byte[] content) {
		super(content);
		this.context = context;
		this.response = response;
		this.response.retain();
	}

	@Override
	public HttpHeaders getHeaders() {
		if (this.headers == null) {
			this.headers = new HttpHeaders();
			for (Map.Entry<String, String> entry : this.response.headers()) {
				headers.add(entry.getKey(), entry.getValue());
			}
		}
		return this.headers;
	}

	@Override
	public HttpVersion getProtocol() {
		if (protocol == null) {
			io.netty.handler.codec.http.HttpVersion version = response
					.getProtocolVersion();
			this.protocol = new HttpVersion(version.protocolName(),
					version.majorVersion(), version.majorVersion(),
					version.isKeepAliveDefault());
		}
		return protocol;
	}

	@Override
	public HttpStatus getStatus() {
		if (status == null) {
			HttpResponseStatus status = response.getStatus();
			this.status = new HttpStatus(status.code(), status.reasonPhrase());
		}
		return status;
	}

	@Override
	public void close() {
		this.response.release();
		this.context.close();
	}
}
