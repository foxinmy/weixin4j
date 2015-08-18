package com.foxinmy.weixin4j.http;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Locale;

import com.foxinmy.weixin4j.model.Consts;

/**
 * reference of apache pivot
 * 
 * @className ContentType
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public final class ContentType implements Serializable {

	private static final long serialVersionUID = 1544245878894784980L;

	public static final ContentType APPLICATION_ATOM_XML = create(
			"application/atom+xml", Consts.UTF_8);
	public static final ContentType APPLICATION_FORM_URLENCODED = create(
			"application/x-www-form-urlencoded", Consts.UTF_8);
	public static final ContentType APPLICATION_JSON = create(
			"application/json", Consts.UTF_8);
	public static final ContentType APPLICATION_OCTET_STREAM = create(
			"application/octet-stream", (Charset) null);
	public static final ContentType APPLICATION_SVG_XML = create(
			"application/svg+xml", Consts.UTF_8);
	public static final ContentType APPLICATION_XHTML_XML = create(
			"application/xhtml+xml", Consts.UTF_8);
	public static final ContentType APPLICATION_XML = create("application/xml",
			Consts.UTF_8);
	public static final ContentType MULTIPART_FORM_DATA = create(
			"multipart/form-data", Consts.UTF_8);
	public static final ContentType TEXT_HTML = create("text/html",
			Consts.UTF_8);
	public static final ContentType TEXT_PLAIN = create("text/plain",
			Consts.UTF_8);
	public static final ContentType IMAGE_JPG = create("image/jpg",
			Consts.UTF_8);
	public static final ContentType AUDIO_MP3 = create("audio/mp3",
			Consts.UTF_8);
	public static final ContentType VIDEO_MPEG4 = create("video/mpeg4",
			Consts.UTF_8);
	public static final ContentType TEXT_XML = create("text/xml", Consts.UTF_8);
	public static final ContentType WILDCARD = create("*/*", (Charset) null);

	// defaults
	public static final ContentType DEFAULT_TEXT = TEXT_PLAIN;
	public static final ContentType DEFAULT_BINARY = APPLICATION_OCTET_STREAM;

	private final String mimeType;
	private final Charset charset;

	ContentType(final String mimeType, final Charset charset) {
		this.mimeType = mimeType;
		this.charset = charset;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public Charset getCharset() {
		return this.charset;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(this.mimeType);
		if (this.charset != null) {
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
		return new ContentType(type, charset);
	}

	public static ContentType create(final String mimeType) {
		return new ContentType(mimeType, (Charset) null);
	}

	public static ContentType create(final String mimeType, final String charset)
			throws UnsupportedCharsetException {
		return create(
				mimeType,
				(charset != null && charset.length() > 0) ? Charset
						.forName(charset) : null);
	}
}