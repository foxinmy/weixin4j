package com.foxinmy.weixin4j.http;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.foxinmy.weixin4j.util.CharArrayBuffer;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.NameValue;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * reference of apache pivot
 * 
 * @className ContentType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月29日
 * @since JDK 1.6
 * @see
 */
public final class ContentType implements Serializable {

	private static final long serialVersionUID = 1544245878894784980L;

	private final MimeType mimeType;
	private final Charset charset;
	private final NameValue[] params;
	private static final Charset DEFAULT_CHARSET = Consts.UTF_8;

	public static final ContentType APPLICATION_JSON;
	public static final ContentType APPLICATION_FORM_URLENCODED;
	public static final ContentType MULTIPART_FORM_DATA;
	public static final ContentType DEFAULT_BINARY;
	public static final ContentType DEFAULT_TEXT;

	static {
		APPLICATION_JSON = new ContentType(MimeType.APPLICATION_JSON);
		APPLICATION_FORM_URLENCODED = new ContentType(
				MimeType.APPLICATION_FORM_URLENCODED);
		MULTIPART_FORM_DATA = new ContentType(MimeType.MULTIPART_FORM_DATA);
		DEFAULT_BINARY = new ContentType(MimeType.APPLICATION_OCTET_STREAM);
		DEFAULT_TEXT = new ContentType(MimeType.TEXT_PLAIN);
	}

	ContentType(final MimeType mimeType) {
		this(mimeType, DEFAULT_CHARSET);
	}

	ContentType(final MimeType mimeType, final Charset charset) {
		this(mimeType, charset, null);
	}

	ContentType(final MimeType mimeType, final Charset charset,
			final NameValue[] params) {
		this.mimeType = mimeType;
		this.charset = charset;
		this.params = params;
	}

	public MimeType getMimeType() {
		return this.mimeType;
	}

	public Charset getCharset() {
		return this.charset;
	}

	/**
	 * @since 4.3
	 */
	public String getParameter(final String name) {
		if (this.params == null) {
			return null;
		}
		for (final NameValue param : this.params) {
			if (param.getName().equalsIgnoreCase(name)) {
				return param.getValue();
			}
		}
		return null;
	}

	/**
	 * Generates textual representation of this content type which can be used
	 * as the value of a {@code Content-Type} header.
	 */
	@Override
	public String toString() {
		final CharArrayBuffer buf = new CharArrayBuffer(64);
		buf.append(this.mimeType);
		if (this.params != null) {
			buf.append("; ");
			HeaderValueFormatter.INSTANCE.formatParameters(buf,
					this.params, false);
		} else if (this.charset != null) {
			buf.append("; charset=");
			buf.append(this.charset.name());
		}
		return buf.toString();
	}

	public static String toString(List<ContentType> contentTypes) {
		if (contentTypes == null || contentTypes.isEmpty()) {
			return null;
		}
		StringBuilder buf = new StringBuilder();
		for (ContentType contentType : contentTypes) {
			buf.append(contentType.toString()).append(",");
		}
		return buf.delete(buf.length() - 1, buf.length()).toString();
	}

	private static boolean valid(final String s) {
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == '"' || ch == ',' || ch == ';') {
				return false;
			}
		}
		return true;
	}

	public static ContentType create(final MimeType mimeType,
			final Charset charset) {
		if (mimeType == null) {
			throw new IllegalArgumentException("MIME type may not be null");
		}
		return new ContentType(mimeType, charset);
	}

	public static ContentType create(final String mimeType) {
		return create(MimeType.valueOf(mimeType), (Charset) null);
	}

	public static ContentType create(final String mimeType, final String charset)
			throws UnsupportedCharsetException {
		return create(
				mimeType,
				(charset != null && charset.length() > 0) ? Charset
						.forName(charset) : null);
	}

	public static ContentType create(final String mimeType,
			final Charset charset) {
		if (mimeType == null) {
			throw new IllegalArgumentException("MIME type may not be null");
		}
		String type = mimeType.trim().toLowerCase(Locale.US);
		if (type.length() == 0) {
			throw new IllegalArgumentException("MIME type may not be empty");
		}
		if (!valid(type)) {
			throw new IllegalArgumentException(
					"MIME type may not contain reserved characters");
		}
		return new ContentType(MimeType.valueOf(type), charset);
	}

	private static ContentType create(final MimeType mimeType,
			final NameValue[] params, final boolean strict) {
		Charset charset = null;
		for (final NameValue param : params) {
			if (param.getName().equalsIgnoreCase("charset")) {
				final String s = param.getValue();
				if (StringUtil.isNotBlank(s)) {
					try {
						charset = Charset.forName(s);
					} catch (final UnsupportedCharsetException ex) {
						if (strict) {
							throw ex;
						}
					}
				}
				break;
			}
		}
		return new ContentType(mimeType, charset, params != null
				&& params.length > 0 ? params : null);
	}

/**
	     * Creates a new instance of {@link ContentType} with the given parameters.
	     *
	     * @param mimeType MIME type. It may not be {@code null} or empty. It may not contain
	     *        characters {@code <">, <;>, <,>} reserved by the HTTP specification.
	     * @param params parameters.
	     * @return content type
	     *
	     * @since 4.4
	     */
	public static ContentType create(final String mimeType,
			final NameValue... params) throws UnsupportedCharsetException {
		final String type = mimeType.toLowerCase(Locale.ROOT);
		if (!valid(type)) {
			throw new IllegalArgumentException(
					"MIME type may not contain reserved characters");
		}
		return create(MimeType.valueOf(mimeType), params, true);
	}

	/**
	 * Creates a new instance with this MIME type and the given parameters.
	 *
	 * @param params
	 * @return a new instance with this MIME type and the given parameters.
	 * @since 4.4
	 */
	public ContentType withParameters(final NameValue... params)
			throws UnsupportedCharsetException {
		if (params.length == 0) {
			return this;
		}
		final Map<String, String> paramMap = new LinkedHashMap<String, String>();
		if (this.params != null) {
			for (final NameValue param : this.params) {
				paramMap.put(param.getName(), param.getValue());
			}
		}
		for (final NameValue param : params) {
			paramMap.put(param.getName(), param.getValue());
		}
		final List<NameValue> newParams = new ArrayList<NameValue>(
				paramMap.size() + 1);
		if (this.charset != null && !paramMap.containsKey("charset")) {
			newParams.add(new NameValue("charset", this.charset.name()));
		}
		for (final Map.Entry<String, String> entry : paramMap.entrySet()) {
			newParams.add(new NameValue(entry.getKey(), entry.getValue()));
		}
		return create(this.getMimeType(),
				newParams.toArray(new NameValue[newParams.size()]), true);
	}
}