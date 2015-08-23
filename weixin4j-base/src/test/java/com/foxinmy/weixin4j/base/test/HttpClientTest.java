package com.foxinmy.weixin4j.base.test;

import org.apache.commons.httpclient.methods.GetMethod;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.factory.HttpComponent3Factory;
import com.foxinmy.weixin4j.http.factory.HttpComponent4Factory;
import com.foxinmy.weixin4j.http.factory.SimpleHttpClientFactory;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

public class HttpClientTest {

	static HttpRequest request = new HttpRequest(HttpMethod.GET,
			"https://www.baidu.com");
	static {
		HttpParams params = new HttpParams();
		//params.setProxy(new Proxy(Type.HTTP, new InetSocketAddress(
			//	"218.92.227.170", 11095)));
		request.setParams(params);
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

	public static void print(HttpResponse response) throws HttpClientException {
		System.err.println(response.getStatus());
		System.err.println(response.getBody());
		System.err.println(response.getHeaders());
		System.err.println(response.getProtocol());
	}

	public static void test() {
		org.apache.commons.httpclient.HttpClient hc = new org.apache.commons.httpclient.HttpClient();
		hc.getHostConfiguration().setProxy("127.0.0.1", 1080);
		org.apache.commons.httpclient.HttpMethod hm = new GetMethod(
				"http://www.baidu.com");
		try {
			hc.executeMethod(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		//test1();
		System.out.println("---------------------");
		//test2();
		System.out.println("---------------------");
		//test3();
	}
}
