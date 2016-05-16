package com.foxinmy.weixin4j.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * reference of apache pivot
 * 
 * @className HttpVersion
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月29日
 * @since JDK 1.6
 * @see
 */
public class HttpVersion implements Comparable<HttpVersion> {

	private static final Pattern VERSION_PATTERN = Pattern
			.compile("(\\S+)/(\\d+)\\.(\\d+)");

	public static final String HTTP_1_0_STRING = "HTTP/1.0";
	public static final String HTTP_1_1_STRING = "HTTP/1.1";

	/**
	 * HTTP/1.0
	 */
	public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0,
			false);

	/**
	 * HTTP/1.1
	 */
	public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1,
			true);

	private final String protocol;
	private final int major;
	private final int minor;
	private final boolean keepAlive;
	private final String text;

	public HttpVersion(String text, boolean keepAlive) {
		if (text == null) {
			throw new NullPointerException("text");
		}

		text = text.trim().toUpperCase();
		if (text.isEmpty()) {
			throw new IllegalArgumentException("empty text");
		}
		Matcher m = VERSION_PATTERN.matcher(text);
		if (!m.matches()) {
			throw new IllegalArgumentException("invalid version format: "
					+ text);
		}
		this.protocol = m.group(1);
		this.major = Integer.parseInt(m.group(2));
		this.minor = Integer.parseInt(m.group(3));
		this.keepAlive = keepAlive;
		this.text = protocol + '/' + major + '.' + minor;
	}

	public HttpVersion(String protocol, int major, int minor, boolean keepAlive) {
		this.protocol = protocol;
		this.major = major;
		this.minor = minor;
		this.keepAlive = keepAlive;
		this.text = protocol + '/' + major + '.' + minor;
	}

	public String getProtocol() {
		return protocol;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return getText();
	}

	@Override
	public int hashCode() {
		return (protocol.hashCode() * 31 + major) * 31 + minor;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HttpVersion)) {
			return false;
		}
		HttpVersion that = (HttpVersion) o;
		return minor == that.minor && major == that.major
				&& protocol.equals(that.protocol);
	}

	@Override
	public int compareTo(HttpVersion o) {
		int v = protocol.compareTo(o.protocol);
		if (v != 0) {
			return v;
		}
		v = major - o.major;
		if (v != 0) {
			return v;
		}
		return minor - o.minor;
	}
}
