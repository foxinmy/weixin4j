package com.foxinmy.weixin4j.http.factory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import com.foxinmy.weixin4j.http.AbstractHttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.util.SettableFuture;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * Netty 4.x
 * 
 * @className Netty4HttpClient
 * @author jy
 * @date 2015年8月30日
 * @since JDK 1.6
 * @see
 */
public class Netty4HttpClient extends AbstractHttpClient {

	private final Bootstrap bootstrap;

	public Netty4HttpClient(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			final URI uri = request.getURI();
			final HttpParams params = request.getParams();
			if (params != null) {
				bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
						params.getConnectTimeout());
			}
			final boolean useProxy = params != null
					&& params.getProxy() != null;
			final boolean useSSL = "https".equals(uri.getScheme()) && !useProxy;
			final DefaultHttpRequest uriRequest = createRequest(request);
			final SettableFuture<HttpResponse> future = new SettableFuture<HttpResponse>();
			ChannelFutureListener listener = new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture channelFuture)
						throws Exception {
					if (channelFuture.isSuccess()) {
						Channel channel = channelFuture.channel();
						// ssl
						SSLContext sslContext = null;
						if (useSSL) {
							if (params != null
									&& params.getSSLContext() != null) {
								sslContext = params.getSSLContext();
							} else {
								sslContext = HttpClientFactory
										.allowSSLContext();
							}
							SSLEngine sslEngine = sslContext.createSSLEngine();
							sslEngine.setUseClientMode(true);
							channel.pipeline().addFirst(
									new SslHandler(sslEngine));
							if (params != null && params.getReadTimeout() > 0) {
								channel.pipeline().addFirst(
										new ReadTimeoutHandler(params
												.getReadTimeout(),
												TimeUnit.MILLISECONDS));
							}
						}
						channel.pipeline().addLast(new RequestHandler(future));
						channel.writeAndFlush(uriRequest);
					} else {
						future.setException(channelFuture.cause());
					}
				}
			};
			InetSocketAddress address = new InetSocketAddress(
					InetAddress.getByName(uri.getHost()), getPort(uri));
			if (useProxy) {
				address = (InetSocketAddress) params.getProxy().address();
			}
			bootstrap.connect(address).syncUninterruptibly()
					.addListener(listener);
			response = future.get();
			handleResponse(response);
		} catch (IOException e) {
			throw new HttpClientException("I/O error on "
					+ request.getMethod().name() + " request for \""
					+ request.getURI().toString() + "\":" + e.getMessage(), e);
		} catch (InterruptedException e) {
			throw new HttpClientException("Execute error on "
					+ request.getMethod().name() + " request for \""
					+ request.getURI().toString() + "\":" + e.getMessage(), e);
		} catch (ExecutionException e) {
			throw new HttpClientException("Execute error on "
					+ request.getMethod().name() + " request for \""
					+ request.getURI().toString() + "\":" + e.getMessage(), e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return response;
	}

	private DefaultHttpRequest createRequest(HttpRequest request)
			throws IOException {
		HttpMethod method = HttpMethod.valueOf(request.getMethod().name());
		URI uri = request.getURI();
		String url = StringUtil.isBlank(uri.getRawPath()) ? "/" : uri
				.getRawPath();
		if (StringUtil.isNotBlank(uri.getRawQuery())) {
			url += "?" + uri.getRawQuery();
		}
		DefaultHttpRequest uriRequest = new DefaultHttpRequest(
				HttpVersion.HTTP_1_1, method, url);
		// entity
		HttpEntity entity = request.getEntity();
		if (entity != null) {
			ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
			ByteBufOutputStream out = new ByteBufOutputStream(byteBuf);
			entity.writeTo(out);
			out.flush();
			out.close();
			uriRequest = new DefaultFullHttpRequest(
					uriRequest.getProtocolVersion(), uriRequest.getMethod(),
					uriRequest.getUri(), byteBuf);
			if (entity.getContentType() != null) {
				uriRequest.headers().add(HttpHeaders.CONTENT_TYPE,
						entity.getContentType().toString());
			}
			if (entity.getContentLength() < 0) {
				uriRequest.headers().add(HttpHeaders.TRANSFER_ENCODING,
						io.netty.handler.codec.http.HttpHeaders.Values.CHUNKED);
			} else {
				uriRequest.headers().add(HttpHeaders.CONTENT_LENGTH,
						entity.getContentLength());
			}
		}
		// header
		HttpHeaders headers = request.getHeaders();
		if (headers == null) {
			headers = new HttpHeaders();
		}
		if (!headers.containsKey(HttpHeaders.HOST)) {
			headers.set(HttpHeaders.HOST, uri.getHost());
		}
		// Add default accept headers
		if (!headers.containsKey(HttpHeaders.ACCEPT)) {
			headers.set(HttpHeaders.ACCEPT, "*/*");
		}
		// Add default user agent
		if (!headers.containsKey(HttpHeaders.USER_AGENT)) {
			headers.set(HttpHeaders.USER_AGENT, "netty/httpclient");
		}
		for (Iterator<Entry<String, List<String>>> headerIterator = headers
				.entrySet().iterator(); headerIterator.hasNext();) {
			Entry<String, List<String>> header = headerIterator.next();
			uriRequest.headers().set(header.getKey(), header.getValue());
		}
		uriRequest.headers().set(HttpHeaders.CONNECTION,
				io.netty.handler.codec.http.HttpHeaders.Values.CLOSE);
		return uriRequest;
	}

	private int getPort(URI uri) {
		int port = uri.getPort();
		if (port == -1) {
			if ("http".equalsIgnoreCase(uri.getScheme())) {
				port = 80;
			} else if ("https".equalsIgnoreCase(uri.getScheme())) {
				port = 443;
			}
		}
		return port;
	}

	private static class RequestHandler extends
			SimpleChannelInboundHandler<FullHttpResponse> {

		private final SettableFuture<HttpResponse> future;

		public RequestHandler(SettableFuture<HttpResponse> future) {
			this.future = future;
		}

		@Override
		protected void channelRead0(ChannelHandlerContext context,
				FullHttpResponse response) throws Exception {
			byte[] content = null;
			ByteBuf byteBuf = response.content();
			if (byteBuf.hasArray()) {
				content = byteBuf.array();
			} else {
				content = new byte[byteBuf.readableBytes()];
				byteBuf.readBytes(content);
			}
			future.set(new Netty4HttpResponse(context, response, content));
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext context,
				Throwable cause) throws Exception {
			future.setException(cause);
		}
	}
}
