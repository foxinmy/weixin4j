package com.foxinmy.weixin4j.http.support.apache4;

import org.apache.http.util.VersionInfo;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;

/**
 * 使用Apache的HttpClient4.x
 * 
 * @className HttpComponent4Factory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月12日
 * @since JDK 1.6
 */
public class HttpComponent4Factory extends HttpClientFactory {

	private static HttpClientFactory httpComponentFactory;
	static {
		VersionInfo version = VersionInfo.loadVersionInfo(
				"org.apache.http.client", HttpClient.class.getClassLoader());
		String release = (version != null) ? version.getRelease()
				: VersionInfo.UNAVAILABLE;
		if (release.startsWith("4.")) {
			if (release.startsWith("4.0") || release.startsWith("4.1")
					|| release.startsWith("4.2")) {
				httpComponentFactory = new HttpComponent4_1Factory();
			} else {
				httpComponentFactory = new HttpComponent4_2Factory();
			}
		} else {
			throw new RuntimeException("unknown the HttpComponent version:"
					+ release);
		}
	}

	@Override
	public HttpClient newInstance(HttpParams params) {
		return httpComponentFactory.newInstance(params);
	}
}
