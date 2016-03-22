package com.foxinmy.weixin4j.base.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.factory.HttpComponent3Factory;
import com.foxinmy.weixin4j.http.factory.HttpComponent4Factory;
import com.foxinmy.weixin4j.http.factory.Netty4HttpClientFactory;
import com.foxinmy.weixin4j.http.factory.SimpleHttpClientFactory;
import com.foxinmy.weixin4j.util.IOUtil;

public class HttpClientTest {

	static HttpRequest request = new HttpRequest(HttpMethod.GET,
			"http://www.iteye.com/");
	static {
		HttpParams params = new HttpParams();
		params.setProxy(new Proxy(Type.HTTP, new InetSocketAddress(
				"117.136.234.9", 80)));
		//request.setParams(params);
	}

	public static void test1() throws HttpClientException {
		HttpClientFactory.setDefaultFactory(new SimpleHttpClientFactory());
		HttpClient httpClient = HttpClientFactory.getInstance();
		HttpResponse response = httpClient.execute(request);
		print(response);
	}

	public static void test2() throws HttpClientException {
		HttpClientFactory.setDefaultFactory(new HttpComponent3Factory());
		HttpClient httpClient = HttpClientFactory.getInstance();
		HttpResponse response = httpClient.execute(request);
		print(response);
	}

	public static void test3() throws HttpClientException {
		HttpClientFactory.setDefaultFactory(new HttpComponent4Factory());
		HttpClient httpClient = HttpClientFactory.getInstance();
		HttpResponse response = httpClient.execute(request);
		print(response);
	}

	public static void test4() throws HttpClientException {
		HttpClientFactory.setDefaultFactory(new Netty4HttpClientFactory());
		HttpClient httpClient = HttpClientFactory.getInstance();
		HttpResponse response = httpClient.execute(request);
		print(response);
	}

	public static void print(HttpResponse response) throws HttpClientException {
		try {
			System.err.println(new String(
					IOUtil.toByteArray(response.getBody())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		for(int i=0;i<100000;i++){
		test1();
		}
		System.out.println("---------------------");
		test2();
		System.out.println("---------------------");
		test3();
		System.out.println("---------------------");
		test4();
	}
}
