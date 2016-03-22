/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.foxinmy.weixin4j.http;

import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.foxinmy.weixin4j.util.LinkedCaseInsensitiveMap;
import com.foxinmy.weixin4j.util.MultiValueMap;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * Represents HTTP request and response headers, mapping string header names to
 * list of string values.
 *
 * <p>
 * In addition to the normal methods defined by {@link Map}, this class offers
 * the following convenience methods:
 * <ul>
 * <li>{@link #getFirst(String)} returns the first value associated with a given
 * header name</li>
 * <li>{@link #add(String, String)} adds a header value to the list of values
 * for a header name</li>
 * <li>{@link #set(String, String)} sets the header value to a single string
 * value</li>
 * </ul>
 *
 * <p>
 *
 * @author Arjen Poutsma
 * @author Sebastien Deleuze
 * @since 3.0
 */
public class HttpHeaders implements MultiValueMap<String, String>, Serializable {

	private static final long serialVersionUID = -8578554704772377436L;

	/**
	 * The HTTP {@code Accept} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.3.2">Section
	 *      5.3.2 of RFC 7231</a>
	 */
	public static final String ACCEPT = "Accept";
	/**
	 * The HTTP {@code Accept-Charset} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.3.3">Section
	 *      5.3.3 of RFC 7231</a>
	 */
	public static final String ACCEPT_CHARSET = "Accept-Charset";
	/**
	 * The HTTP {@code Accept-Encoding} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.3.4">Section
	 *      5.3.4 of RFC 7231</a>
	 */
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	/**
	 * The HTTP {@code Accept-Language} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.3.5">Section
	 *      5.3.5 of RFC 7231</a>
	 */
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	/**
	 * The HTTP {@code Accept-Ranges} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7233#section-2.3">Section
	 *      5.3.5 of RFC 7233</a>
	 */
	public static final String ACCEPT_RANGES = "Accept-Ranges";
	/**
	 * The CORS {@code Access-Control-Allow-Credentials} response header field
	 * name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	/**
	 * The CORS {@code Access-Control-Allow-Headers} response header field name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	/**
	 * The CORS {@code Access-Control-Allow-Methods} response header field name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	/**
	 * The CORS {@code Access-Control-Allow-Origin} response header field name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	/**
	 * The CORS {@code Access-Control-Expose-Headers} response header field
	 * name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	/**
	 * The CORS {@code Access-Control-Max-Age} response header field name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	/**
	 * The CORS {@code Access-Control-Request-Headers} request header field
	 * name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	/**
	 * The CORS {@code Access-Control-Request-Method} request header field name.
	 * 
	 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommandation</a>
	 */
	public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
	/**
	 * The HTTP {@code Age} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7234#section-5.1">Section 5.1
	 *      of RFC 7234</a>
	 */
	public static final String AGE = "Age";
	/**
	 * The HTTP {@code Allow} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-7.4.1">Section
	 *      7.4.1 of RFC 7231</a>
	 */
	public static final String ALLOW = "Allow";
	/**
	 * The HTTP {@code Authorization} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7235#section-4.2">Section 4.2
	 *      of RFC 7235</a>
	 */
	public static final String AUTHORIZATION = "Authorization";
	/**
	 * The HTTP {@code Cache-Control} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7234#section-5.2">Section 5.2
	 *      of RFC 7234</a>
	 */
	public static final String CACHE_CONTROL = "Cache-Control";
	/**
	 * The HTTP {@code Connection} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-6.1">Section 6.1
	 *      of RFC 7230</a>
	 */
	public static final String CONNECTION = "Connection";
	/**
	 * The HTTP {@code Content-Encoding} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-3.1.2.2">Section
	 *      3.1.2.2 of RFC 7231</a>
	 */
	public static final String CONTENT_ENCODING = "Content-Encoding";
	/**
	 * The HTTP {@code Content-Disposition} header field name
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc6266">RFC 6266</a>
	 */
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	/**
	 * The HTTP {@code Content-Language} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-3.1.3.2">Section
	 *      3.1.3.2 of RFC 7231</a>
	 */
	public static final String CONTENT_LANGUAGE = "Content-Language";
	/**
	 * The HTTP {@code Content-Length} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-3.3.2">Section
	 *      3.3.2 of RFC 7230</a>
	 */
	public static final String CONTENT_LENGTH = "Content-Length";
	/**
	 * The HTTP {@code Content-Location} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-3.1.4.2">Section
	 *      3.1.4.2 of RFC 7231</a>
	 */
	public static final String CONTENT_LOCATION = "Content-Location";
	/**
	 * The HTTP {@code Content-Range} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7233#section-4.2">Section 4.2
	 *      of RFC 7233</a>
	 */
	public static final String CONTENT_RANGE = "Content-Range";
	/**
	 * The HTTP {@code Content-Type} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-3.1.1.5">Section
	 *      3.1.1.5 of RFC 7231</a>
	 */
	public static final String CONTENT_TYPE = "Content-Type";
	/**
	 * The HTTP {@code Cookie} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc2109#section-4.3.4">Section
	 *      4.3.4 of RFC 2109</a>
	 */
	public static final String COOKIE = "Cookie";
	/**
	 * The HTTP {@code Date} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-7.1.1.2">Section
	 *      7.1.1.2 of RFC 7231</a>
	 */
	public static final String DATE = "Date";
	/**
	 * The HTTP {@code ETag} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7232#section-2.3">Section 2.3
	 *      of RFC 7232</a>
	 */
	public static final String ETAG = "ETag";
	/**
	 * The HTTP {@code Expect} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.1.1">Section
	 *      5.1.1 of RFC 7231</a>
	 */
	public static final String EXPECT = "Expect";
	/**
	 * The HTTP {@code Expires} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7234#section-5.3">Section 5.3
	 *      of RFC 7234</a>
	 */
	public static final String EXPIRES = "Expires";
	/**
	 * The HTTP {@code From} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.5.1">Section
	 *      5.5.1 of RFC 7231</a>
	 */
	public static final String FROM = "From";
	/**
	 * The HTTP {@code Host} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-5.4">Section 5.4
	 *      of RFC 7230</a>
	 */
	public static final String HOST = "Host";
	/**
	 * The HTTP {@code If-Match} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7232#section-3.1">Section 3.1
	 *      of RFC 7232</a>
	 */
	public static final String IF_MATCH = "If-Match";
	/**
	 * The HTTP {@code If-Modified-Since} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7232#section-3.3">Section 3.3
	 *      of RFC 7232</a>
	 */
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
	/**
	 * The HTTP {@code If-None-Match} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7232#section-3.2">Section 3.2
	 *      of RFC 7232</a>
	 */
	public static final String IF_NONE_MATCH = "If-None-Match";
	/**
	 * The HTTP {@code If-Range} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7233#section-3.2">Section 3.2
	 *      of RFC 7233</a>
	 */
	public static final String IF_RANGE = "If-Range";
	/**
	 * The HTTP {@code If-Unmodified-Since} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7232#section-3.4">Section 3.4
	 *      of RFC 7232</a>
	 */
	public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
	/**
	 * The HTTP {@code Last-Modified} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7232#section-2.2">Section 2.2
	 *      of RFC 7232</a>
	 */
	public static final String LAST_MODIFIED = "Last-Modified";
	/**
	 * The HTTP {@code Link} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc5988">RFC 5988</a>
	 */
	public static final String LINK = "Link";
	/**
	 * The HTTP {@code Location} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-7.1.2">Section
	 *      7.1.2 of RFC 7231</a>
	 */
	public static final String LOCATION = "Location";
	/**
	 * The HTTP {@code Max-Forwards} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.1.2">Section
	 *      5.1.2 of RFC 7231</a>
	 */
	public static final String MAX_FORWARDS = "Max-Forwards";
	/**
	 * The HTTP {@code Origin} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc6454">RFC 6454</a>
	 */
	public static final String ORIGIN = "Origin";
	/**
	 * The HTTP {@code Pragma} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7234#section-5.4">Section 5.4
	 *      of RFC 7234</a>
	 */
	public static final String PRAGMA = "Pragma";
	/**
	 * The HTTP {@code Proxy-Authenticate} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7235#section-4.3">Section 4.3
	 *      of RFC 7235</a>
	 */
	public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
	/**
	 * The HTTP {@code Proxy-Authorization} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7235#section-4.4">Section 4.4
	 *      of RFC 7235</a>
	 */
	public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
	/**
	 * The HTTP {@code Range} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7233#section-3.1">Section 3.1
	 *      of RFC 7233</a>
	 */
	public static final String RANGE = "Range";
	/**
	 * The HTTP {@code Referer} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.5.2">Section
	 *      5.5.2 of RFC 7231</a>
	 */
	public static final String REFERER = "Referer";
	/**
	 * The HTTP {@code Retry-After} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-7.1.3">Section
	 *      7.1.3 of RFC 7231</a>
	 */
	public static final String RETRY_AFTER = "Retry-After";
	/**
	 * The HTTP {@code Server} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-7.4.2">Section
	 *      7.4.2 of RFC 7231</a>
	 */
	public static final String SERVER = "Server";
	/**
	 * The HTTP {@code Set-Cookie} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc2109#section-4.2.2">Section
	 *      4.2.2 of RFC 2109</a>
	 */
	public static final String SET_COOKIE = "Set-Cookie";
	/**
	 * The HTTP {@code Set-Cookie2} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc2965">RFC 2965</a>
	 */
	public static final String SET_COOKIE2 = "Set-Cookie2";
	/**
	 * The HTTP {@code TE} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-4.3">Section 4.3
	 *      of RFC 7230</a>
	 */
	public static final String TE = "TE";
	/**
	 * The HTTP {@code Trailer} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-4.4">Section 4.4
	 *      of RFC 7230</a>
	 */
	public static final String TRAILER = "Trailer";
	/**
	 * The HTTP {@code Transfer-Encoding} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-3.3.1">Section
	 *      3.3.1 of RFC 7230</a>
	 */
	public static final String TRANSFER_ENCODING = "Transfer-Encoding";
	/**
	 * The HTTP {@code Upgrade} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-6.7">Section 6.7
	 *      of RFC 7230</a>
	 */
	public static final String UPGRADE = "Upgrade";
	/**
	 * The HTTP {@code User-Agent} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-5.5.3">Section
	 *      5.5.3 of RFC 7231</a>
	 */
	public static final String USER_AGENT = "User-Agent";
	/**
	 * The HTTP {@code Vary} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7231#section-7.1.4">Section
	 *      7.1.4 of RFC 7231</a>
	 */
	public static final String VARY = "Vary";
	/**
	 * The HTTP {@code Via} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7230#section-5.7.1">Section
	 *      5.7.1 of RFC 7230</a>
	 */
	public static final String VIA = "Via";
	/**
	 * The HTTP {@code Warning} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7234#section-5.5">Section 5.5
	 *      of RFC 7234</a>
	 */
	public static final String WARNING = "Warning";
	/**
	 * The HTTP {@code WWW-Authenticate} header field name.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc7235#section-4.1">Section 4.1
	 *      of RFC 7235</a>
	 */
	public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

	/**
	 * Date formats as specified in the HTTP RFC
	 * 
	 * @see <a
	 *      href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section
	 *      7.1.1.1 of RFC 7231</a>
	 */
	private static final String[] DATE_FORMATS = new String[] {
			"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz",
			"EEE MMM dd HH:mm:ss yyyy" };

	private static TimeZone GMT = TimeZone.getTimeZone("GMT");

	private final Map<String, List<String>> headers;

	/**
	 * Constructs a new, empty instance of the {@code HttpHeaders} object.
	 */
	public HttpHeaders() {
		this(new LinkedCaseInsensitiveMap<List<String>>(8, Locale.ENGLISH),
				false);
	}

	/**
	 * Private constructor that can create read-only {@code HttpHeader}
	 * instances.
	 */
	private HttpHeaders(Map<String, List<String>> headers, boolean readOnly) {
		if (readOnly) {
			Map<String, List<String>> map = new LinkedCaseInsensitiveMap<List<String>>(
					headers.size(), Locale.ENGLISH);
			for (Entry<String, List<String>> entry : headers.entrySet()) {
				List<String> values = Collections.unmodifiableList(entry
						.getValue());
				map.put(entry.getKey(), values);
			}
			this.headers = Collections.unmodifiableMap(map);
		} else {
			this.headers = headers;
		}
	}

	/**
	 * Set the list of acceptable {@linkplain MediaType media types}, as
	 * specified by the {@code Accept} header.
	 */
	public void setAccept(List<ContentType> acceptableMediaTypes) {
		set(ACCEPT, ContentType.toString(acceptableMediaTypes));
	}

	/**
	 * Return the list of acceptable {@linkplain MediaType media types}, as
	 * specified by the {@code Accept} header.
	 * <p>
	 * Returns an empty list when the acceptable media types are unspecified.
	 */
	public String getAccept() {
		return getFirst(ACCEPT);
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Allow-Credentials}
	 * response header.
	 */
	public void setAccessControlAllowCredentials(boolean allowCredentials) {
		set(ACCESS_CONTROL_ALLOW_CREDENTIALS,
				Boolean.toString(allowCredentials));
	}

	/**
	 * Returns the value of the {@code Access-Control-Allow-Credentials}
	 * response header.
	 */
	public boolean getAccessControlAllowCredentials() {
		return Boolean.valueOf(getFirst(ACCESS_CONTROL_ALLOW_CREDENTIALS));
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Allow-Headers} response
	 * header.
	 */
	public void setAccessControlAllowHeaders(List<String> allowedHeaders) {
		set(ACCESS_CONTROL_ALLOW_HEADERS,
				toCommaDelimitedString(allowedHeaders));
	}

	/**
	 * Returns the value of the {@code Access-Control-Allow-Headers} response
	 * header.
	 */
	public List<String> getAccessControlAllowHeaders() {
		return getFirstValueAsList(ACCESS_CONTROL_ALLOW_HEADERS);
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Allow-Methods} response
	 * header.
	 */
	public void setAccessControlAllowMethods(List<HttpMethod> allowedMethods) {
		set(ACCESS_CONTROL_ALLOW_METHODS, StringUtil.join(allowedMethods, ','));
	}

	/**
	 * Returns the value of the {@code Access-Control-Allow-Methods} response
	 * header.
	 */
	public List<HttpMethod> getAccessControlAllowMethods() {
		List<HttpMethod> result = new ArrayList<HttpMethod>();
		String value = getFirst(ACCESS_CONTROL_ALLOW_METHODS);
		if (value != null) {
			String[] tokens = value.split(",\\s*");
			for (String token : tokens) {
				result.add(HttpMethod.valueOf(token));
			}
		}
		return result;
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Allow-Origin} response
	 * header.
	 */
	public void setAccessControlAllowOrigin(String allowedOrigin) {
		set(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigin);
	}

	/**
	 * Returns the value of the {@code Access-Control-Allow-Origin} response
	 * header.
	 */
	public String getAccessControlAllowOrigin() {
		return getFirst(ACCESS_CONTROL_ALLOW_ORIGIN);
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Expose-Headers} response
	 * header.
	 */
	public void setAccessControlExposeHeaders(List<String> exposedHeaders) {
		set(ACCESS_CONTROL_EXPOSE_HEADERS,
				toCommaDelimitedString(exposedHeaders));
	}

	/**
	 * Returns the value of the {@code Access-Control-Expose-Headers} response
	 * header.
	 */
	public List<String> getAccessControlExposeHeaders() {
		return getFirstValueAsList(ACCESS_CONTROL_EXPOSE_HEADERS);
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Max-Age} response
	 * header.
	 */
	public void setAccessControlMaxAge(long maxAge) {
		set(ACCESS_CONTROL_MAX_AGE, Long.toString(maxAge));
	}

	/**
	 * Returns the value of the {@code Access-Control-Max-Age} response header.
	 * <p>
	 * Returns -1 when the max age is unknown.
	 */
	public long getAccessControlMaxAge() {
		String value = getFirst(ACCESS_CONTROL_MAX_AGE);
		return (value != null ? Long.parseLong(value) : -1);
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Request-Headers} request
	 * header.
	 */
	public void setAccessControlRequestHeaders(List<String> requestHeaders) {
		set(ACCESS_CONTROL_REQUEST_HEADERS,
				toCommaDelimitedString(requestHeaders));
	}

	/**
	 * Returns the value of the {@code Access-Control-Request-Headers} request
	 * header.
	 */
	public List<String> getAccessControlRequestHeaders() {
		return getFirstValueAsList(ACCESS_CONTROL_REQUEST_HEADERS);
	}

	/**
	 * Set the (new) value of the {@code Access-Control-Request-Method} request
	 * header.
	 */
	public void setAccessControlRequestMethod(HttpMethod requestedMethod) {
		set(ACCESS_CONTROL_REQUEST_METHOD, requestedMethod.name());
	}

	/**
	 * Returns the value of the {@code Access-Control-Request-Method} request
	 * header.
	 */
	public HttpMethod getAccessControlRequestMethod() {
		String value = getFirst(ACCESS_CONTROL_REQUEST_METHOD);
		return (value != null ? HttpMethod.valueOf(value) : null);
	}

	/**
	 * Set the list of acceptable {@linkplain Charset charsets}, as specified by
	 * the {@code Accept-Charset} header.
	 */
	public void setAcceptCharset(List<Charset> acceptableCharsets) {
		StringBuilder builder = new StringBuilder();
		for (Iterator<Charset> iterator = acceptableCharsets.iterator(); iterator
				.hasNext();) {
			Charset charset = iterator.next();
			builder.append(charset.name().toLowerCase(Locale.ENGLISH));
			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}
		set(ACCEPT_CHARSET, builder.toString());
	}

	/**
	 * Return the list of acceptable {@linkplain Charset charsets}, as specified
	 * by the {@code Accept-Charset} header.
	 */
	public List<Charset> getAcceptCharset() {
		List<Charset> result = new ArrayList<Charset>();
		String value = getFirst(ACCEPT_CHARSET);
		if (value != null) {
			String[] tokens = value.split(",\\s*");
			for (String token : tokens) {
				int paramIdx = token.indexOf(';');
				String charsetName;
				if (paramIdx == -1) {
					charsetName = token;
				} else {
					charsetName = token.substring(0, paramIdx);
				}
				if (!charsetName.equals("*")) {
					result.add(Charset.forName(charsetName));
				}
			}
		}
		return result;
	}

	/**
	 * Set the set of allowed {@link HttpMethod HTTP methods}, as specified by
	 * the {@code Allow} header.
	 */
	public void setAllow(Set<HttpMethod> allowedMethods) {
		set(ALLOW, StringUtil.join(allowedMethods, ','));
	}

	/**
	 * Return the set of allowed {@link HttpMethod HTTP methods}, as specified
	 * by the {@code Allow} header.
	 * <p>
	 * Returns an empty set when the allowed methods are unspecified.
	 */
	public Set<HttpMethod> getAllow() {
		String value = getFirst(ALLOW);
		if (StringUtil.isNotBlank(value)) {
			List<HttpMethod> allowedMethod = new ArrayList<HttpMethod>(5);
			String[] tokens = value.split(",\\s*");
			for (String token : tokens) {
				allowedMethod.add(HttpMethod.valueOf(token));
			}
			return EnumSet.copyOf(allowedMethod);
		} else {
			return EnumSet.noneOf(HttpMethod.class);
		}
	}

	/**
	 * Set the (new) value of the {@code Cache-Control} header.
	 */
	public void setCacheControl(String cacheControl) {
		set(CACHE_CONTROL, cacheControl);
	}

	/**
	 * Returns the value of the {@code Cache-Control} header.
	 */
	public String getCacheControl() {
		return getFirst(CACHE_CONTROL);
	}

	/**
	 * Set the (new) value of the {@code Connection} header.
	 */
	public void setConnection(String connection) {
		set(CONNECTION, connection);
	}

	/**
	 * Set the (new) value of the {@code Connection} header.
	 */
	public void setConnection(List<String> connection) {
		set(CONNECTION, toCommaDelimitedString(connection));
	}

	/**
	 * Returns the value of the {@code Connection} header.
	 */
	public List<String> getConnection() {
		return getFirstValueAsList(CONNECTION);
	}

	/**
	 * Set the (new) value of the {@code Content-Disposition} header for
	 * {@code form-data}.
	 * 
	 * @param name
	 *            the control name
	 * @param filename
	 *            the filename (may be {@code null})
	 */
	public void setContentDispositionFormData(String name, String filename) {
		StringBuilder builder = new StringBuilder("form-data; name=\"");
		builder.append(name).append('\"');
		if (filename != null) {
			builder.append("; filename=\"");
			builder.append(filename).append('\"');
		}
		set(CONTENT_DISPOSITION, builder.toString());
	}

	/**
	 * Set the length of the body in bytes, as specified by the
	 * {@code Content-Length} header.
	 */
	public void setContentLength(long contentLength) {
		set(CONTENT_LENGTH, Long.toString(contentLength));
	}

	/**
	 * Return the length of the body in bytes, as specified by the
	 * {@code Content-Length} header.
	 * <p>
	 * Returns -1 when the content-length is unknown.
	 */
	public long getContentLength() {
		String value = getFirst(CONTENT_LENGTH);
		return (value != null ? Long.parseLong(value) : -1);
	}

	/**
	 * Set the {@linkplain ContentType media type} of the body, as specified by
	 * the {@code Content-Type} header.
	 */
	public void setContentType(ContentType contentType) {
		set(CONTENT_TYPE, contentType.toString());
	}

	/**
	 * Return the {@linkplain MediaType media type} of the body, as specified by
	 * the {@code Content-Type} header.
	 * <p>
	 * Returns {@code null} when the content-type is unknown.
	 */
	public String getContentType() {
		return getFirst(CONTENT_TYPE);
	}

	/**
	 * Set the date and time at which the message was created, as specified by
	 * the {@code Date} header.
	 * <p>
	 * The date should be specified as the number of milliseconds since January
	 * 1, 1970 GMT.
	 */
	public void setDate(long date) {
		setDate(DATE, date);
	}

	/**
	 * Return the date and time at which the message was created, as specified
	 * by the {@code Date} header.
	 * <p>
	 * The date is returned as the number of milliseconds since January 1, 1970
	 * GMT. Returns -1 when the date is unknown.
	 * 
	 * @throws IllegalArgumentException
	 *             if the value can't be converted to a date
	 */
	public long getDate() {
		return getFirstDate(DATE);
	}

	/**
	 * Set the (new) entity tag of the body, as specified by the {@code ETag}
	 * header.
	 */
	public void setETag(String eTag) {
		set(ETAG, eTag);
	}

	/**
	 * Return the entity tag of the body, as specified by the {@code ETag}
	 * header.
	 */
	public String getETag() {
		return getFirst(ETAG);
	}

	/**
	 * Set the date and time at which the message is no longer valid, as
	 * specified by the {@code Expires} header.
	 * <p>
	 * The date should be specified as the number of milliseconds since January
	 * 1, 1970 GMT.
	 */
	public void setExpires(long expires) {
		setDate(EXPIRES, expires);
	}

	/**
	 * Return the date and time at which the message is no longer valid, as
	 * specified by the {@code Expires} header.
	 * <p>
	 * The date is returned as the number of milliseconds since January 1, 1970
	 * GMT. Returns -1 when the date is unknown.
	 */
	public long getExpires() {
		try {
			return getFirstDate(EXPIRES);
		} catch (IllegalArgumentException ex) {
			return -1;
		}
	}

	/**
	 * Set the (new) value of the {@code If-Modified-Since} header.
	 * <p>
	 * The date should be specified as the number of milliseconds since January
	 * 1, 1970 GMT.
	 */
	public void setIfModifiedSince(long ifModifiedSince) {
		setDate(IF_MODIFIED_SINCE, ifModifiedSince);
	}

	/**
	 * Return the value of the {@code If-Modified-Since} header.
	 * <p>
	 * The date is returned as the number of milliseconds since January 1, 1970
	 * GMT. Returns -1 when the date is unknown.
	 */
	public long getIfModifiedSince() {
		return getFirstDate(IF_MODIFIED_SINCE);
	}

	/**
	 * Set the (new) value of the {@code If-None-Match} header.
	 */
	public void setIfNoneMatch(String ifNoneMatch) {
		set(IF_NONE_MATCH, ifNoneMatch);
	}

	/**
	 * Set the (new) values of the {@code If-None-Match} header.
	 */
	public void setIfNoneMatch(List<String> ifNoneMatchList) {
		set(IF_NONE_MATCH, toCommaDelimitedString(ifNoneMatchList));
	}

	protected String toCommaDelimitedString(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String ifNoneMatch = iterator.next();
			builder.append(ifNoneMatch);
			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}
		return builder.toString();
	}

	/**
	 * Return the value of the {@code If-None-Match} header.
	 */
	public List<String> getIfNoneMatch() {
		return getFirstValueAsList(IF_NONE_MATCH);
	}

	protected List<String> getFirstValueAsList(String header) {
		List<String> result = new ArrayList<String>();
		String value = getFirst(header);
		if (value != null) {
			String[] tokens = value.split(",\\s*");
			for (String token : tokens) {
				result.add(token);
			}
		}
		return result;
	}

	/**
	 * Set the time the resource was last changed, as specified by the
	 * {@code Last-Modified} header.
	 * <p>
	 * The date should be specified as the number of milliseconds since January
	 * 1, 1970 GMT.
	 */
	public void setLastModified(long lastModified) {
		setDate(LAST_MODIFIED, lastModified);
	}

	/**
	 * Return the time the resource was last changed, as specified by the
	 * {@code Last-Modified} header.
	 * <p>
	 * The date is returned as the number of milliseconds since January 1, 1970
	 * GMT. Returns -1 when the date is unknown.
	 */
	public long getLastModified() {
		return getFirstDate(LAST_MODIFIED);
	}

	/**
	 * Set the (new) location of a resource, as specified by the
	 * {@code Location} header.
	 */
	public void setLocation(URI location) {
		set(LOCATION, location.toASCIIString());
	}

	/**
	 * Return the (new) location of a resource as specified by the
	 * {@code Location} header.
	 * <p>
	 * Returns {@code null} when the location is unknown.
	 */
	public URI getLocation() {
		String value = getFirst(LOCATION);
		return (value != null ? URI.create(value) : null);
	}

	/**
	 * Set the (new) value of the {@code Origin} header.
	 */
	public void setOrigin(String origin) {
		set(ORIGIN, origin);
	}

	/**
	 * Return the value of the {@code Origin} header.
	 */
	public String getOrigin() {
		return getFirst(ORIGIN);
	}

	/**
	 * Set the (new) value of the {@code Pragma} header.
	 */
	public void setPragma(String pragma) {
		set(PRAGMA, pragma);
	}

	/**
	 * Return the value of the {@code Pragma} header.
	 */
	public String getPragma() {
		return getFirst(PRAGMA);
	}

	/**
	 * Sets the (new) value of the {@code Range} header.
	 */
	public void setRange(String range) {
		set(RANGE, range);
	}

	/**
	 * Returns the value of the {@code Range} header.
	 * <p>
	 * Returns an empty list when the range is unknown.
	 */
	public String getRange() {
		return getFirst(RANGE);
	}

	/**
	 * Set the (new) value of the {@code Upgrade} header.
	 */
	public void setUpgrade(String upgrade) {
		set(UPGRADE, upgrade);
	}

	/**
	 * Returns the value of the {@code Upgrade} header.
	 */
	public String getUpgrade() {
		return getFirst(UPGRADE);
	}

	/**
	 * Parse the first header value for the given header name as a date, return
	 * -1 if there is no value, or raise {@link IllegalArgumentException} if the
	 * value cannot be parsed as a date.
	 */
	public long getFirstDate(String headerName) {
		String headerValue = getFirst(headerName);
		if (headerValue == null) {
			return -1;
		}
		for (String dateFormat : DATE_FORMATS) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					dateFormat, Locale.US);
			simpleDateFormat.setTimeZone(GMT);
			try {
				return simpleDateFormat.parse(headerValue).getTime();
			} catch (ParseException ex) {
				// ignore
			}
		}
		throw new IllegalArgumentException("Cannot parse date value \""
				+ headerValue + "\" for \"" + headerName + "\" header");
	}

	/**
	 * Set the given date under the given header name after formatting it as a
	 * string using the pattern {@code "EEE, dd MMM yyyy HH:mm:ss zzz"}. The
	 * equivalent of {@link #set(String, String)} but for date headers.
	 */
	public void setDate(String headerName, long date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATS[0],
				Locale.US);
		dateFormat.setTimeZone(GMT);
		set(headerName, dateFormat.format(new Date(date)));
	}

	/**
	 * Return the first header value for the given header name, if any.
	 * 
	 * @param headerName
	 *            the header name
	 * @return the first header value, or {@code null} if none
	 */
	@Override
	public String getFirst(String headerName) {
		List<String> headerValues = this.headers.get(headerName);
		return (headerValues != null ? headerValues.get(0) : null);
	}

	/**
	 * Add the given, single header value under the given name.
	 * 
	 * @param headerName
	 *            the header name
	 * @param headerValue
	 *            the header value
	 * @throws UnsupportedOperationException
	 *             if adding headers is not supported
	 * @see #put(String, List)
	 * @see #set(String, String)
	 */
	@Override
	public void add(String headerName, String headerValue) {
		List<String> headerValues = this.headers.get(headerName);
		if (headerValues == null) {
			headerValues = new LinkedList<String>();
			this.headers.put(headerName, headerValues);
		}
		headerValues.add(headerValue);
	}

	/**
	 * Set the given, single header value under the given name.
	 * 
	 * @param headerName
	 *            the header name
	 * @param headerValue
	 *            the header value
	 * @throws UnsupportedOperationException
	 *             if adding headers is not supported
	 * @see #put(String, List)
	 * @see #add(String, String)
	 */
	@Override
	public void set(String headerName, String headerValue) {
		List<String> headerValues = new LinkedList<String>();
		headerValues.add(headerValue);
		this.headers.put(headerName, headerValues);
	}

	@Override
	public void setAll(Map<String, String> values) {
		for (Entry<String, String> entry : values.entrySet()) {
			set(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Map<String, String> toSingleValueMap() {
		LinkedHashMap<String, String> singleValueMap = new LinkedHashMap<String, String>(
				this.headers.size());
		for (Entry<String, List<String>> entry : this.headers.entrySet()) {
			singleValueMap.put(entry.getKey(), entry.getValue().get(0));
		}
		return singleValueMap;
	}

	// Map implementation

	@Override
	public int size() {
		return this.headers.size();
	}

	@Override
	public boolean isEmpty() {
		return this.headers.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.headers.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.headers.containsValue(value);
	}

	@Override
	public List<String> get(Object key) {
		return this.headers.get(key);
	}

	@Override
	public List<String> put(String key, List<String> value) {
		return this.headers.put(key, value);
	}

	@Override
	public List<String> remove(Object key) {
		return this.headers.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends List<String>> map) {
		this.headers.putAll(map);
	}

	@Override
	public void clear() {
		this.headers.clear();
	}

	@Override
	public Set<String> keySet() {
		return this.headers.keySet();
	}

	@Override
	public Collection<List<String>> values() {
		return this.headers.values();
	}

	@Override
	public Set<Entry<String, List<String>>> entrySet() {
		return this.headers.entrySet();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HttpHeaders)) {
			return false;
		}
		HttpHeaders otherHeaders = (HttpHeaders) other;
		return this.headers.equals(otherHeaders.headers);
	}

	@Override
	public int hashCode() {
		return this.headers.hashCode();
	}

	@Override
	public String toString() {
		return this.headers.toString();
	}

	/**
	 * Return a {@code HttpHeaders} object that can only be read, not written
	 * to.
	 */
	public static HttpHeaders readOnlyHttpHeaders(HttpHeaders headers) {
		return new HttpHeaders(headers, true);
	}
}
