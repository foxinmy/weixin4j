package com.foxinmy.weixin4j.http;

import java.util.HashMap;
import java.util.Map;

public class HttpParams {
	private Map<String, String> params;

	public HttpParams() {
		this.params = new HashMap<String, String>();
	}

	public void setAllowUserInteraction(boolean allowUserInteraction) {
		params.put(HttpProtocolParams.ALLOW_USER_INTERACTION,
				Boolean.toString(allowUserInteraction));
	}

	public boolean getAllowUserInteraction() {
		String allowUserInteraction = params
				.get(HttpProtocolParams.ALLOW_USER_INTERACTION);
		return allowUserInteraction != null
				&& Boolean.parseBoolean(allowUserInteraction);
	}

	public void setConnectTimeout(int timeout) {
		if (timeout < 0) {
			timeout = 0;
		}
		params.put(HttpProtocolParams.CONNECT_TIMEOUT,
				Integer.toString(timeout));
	}

	public int getConnectTimeout() {
		String timeout = params.get(HttpProtocolParams.CONNECT_TIMEOUT);
		if (timeout == null) {
			return 5000;
		}
		return Integer.parseInt(timeout);
	}

	public void setReadTimeout(int timeout) {
		if (timeout < 0) {
			timeout = 5000;
		}
		params.put(HttpProtocolParams.READ_TIMEOUT, Integer.toString(timeout));
	}

	public int getReadTimeout() {
		String timeout = params.get(HttpProtocolParams.READ_TIMEOUT);
		if (timeout == null) {
			return 5000;
		}
		return Integer.parseInt(timeout);
	}

	public void setIfmodifiedsince(long ifmodifiedsince) {
		if (ifmodifiedsince < 0) {
			ifmodifiedsince = 5000;
		}
		params.put(HttpProtocolParams.IFMODIFIED_SINCE,
				Long.toString(ifmodifiedsince));
	}

	public long getIfmodifiedsince() {
		String ifmodifiedsince = params
				.get(HttpProtocolParams.IFMODIFIED_SINCE);
		if (ifmodifiedsince == null) {
			return 0l;
		}
		return Long.parseLong(ifmodifiedsince);
	}

	public void setFollowRedirects(boolean followRedirects) {
		params.put(HttpProtocolParams.FOLLOW_REDIRECTS,
				Boolean.toString(followRedirects));
	}

	public boolean getFollowRedirects() {
		String followRedirects = params
				.get(HttpProtocolParams.FOLLOW_REDIRECTS);
		return followRedirects != null && Boolean.parseBoolean(followRedirects);
	}
}
