package com.foxinmy.weixin4j.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.foxinmy.weixin4j.util.StringUtil;

/**
 * MIME type
 * 
 * @className MimeType
 * @author jinyu
 * @date Jul 20, 2016
 * @since JDK 1.6
 */
public class MimeType implements Serializable {

	private static final long serialVersionUID = 4430596628682058362L;

	private static final String WILDCARD_TYPE = "*";

	private final String type;
	private final String subType;

	public static final MimeType APPLICATION_FORM_URLENCODED;
	public static final MimeType APPLICATION_JSON;
	public static final MimeType APPLICATION_OCTET_STREAM;
	public static final MimeType APPLICATION_XML;
	public static final MimeType MULTIPART_FORM_DATA;
	public static final MimeType TEXT_HTML;
	public static final MimeType TEXT_PLAIN;
	public static final MimeType IMAGE_JPG;
	public static final MimeType AUDIO_MP3;
	public static final MimeType VIDEO_MPEG4;
	public static final MimeType TEXT_XML;
	public static final MimeType TEXT_JSON;

	public static final List<MimeType> STREAM_MIMETYPES;

	static {
		APPLICATION_FORM_URLENCODED = valueOf("application/x-www-form-urlencoded");
		APPLICATION_JSON = valueOf("application/json");
		APPLICATION_OCTET_STREAM = valueOf("application/octet-stream");
		APPLICATION_XML = valueOf("application/xml");
		MULTIPART_FORM_DATA = valueOf("multipart/form-data");
		TEXT_HTML = valueOf("text/html");
		TEXT_PLAIN = valueOf("text/plain");
		IMAGE_JPG = valueOf("image/jpg");
		AUDIO_MP3 = valueOf("audio/mp3");
		VIDEO_MPEG4 = valueOf("video/mpeg4");
		TEXT_XML = valueOf("text/xml");
		TEXT_JSON = valueOf("text/json");

		STREAM_MIMETYPES = new ArrayList<MimeType>(4);
		STREAM_MIMETYPES.add(APPLICATION_OCTET_STREAM);
		STREAM_MIMETYPES.add(valueOf("image/*"));
		STREAM_MIMETYPES.add(valueOf("audio/*"));
		STREAM_MIMETYPES.add(valueOf("video/*"));
		STREAM_MIMETYPES.add(valueOf("application/zip"));
		STREAM_MIMETYPES.add(valueOf("application/x-gzip"));
	}

	public MimeType(String type) {
		this(type, WILDCARD_TYPE);
	}

	public MimeType(String type, String subType) {
		this.type = type.toLowerCase(Locale.ENGLISH);
		this.subType = subType.toLowerCase(Locale.ENGLISH);
	}

	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}

	public boolean isWildcardType() {
		return WILDCARD_TYPE.equals(getType());
	}

	public boolean isWildcardSubtype() {
		return WILDCARD_TYPE.equals(getSubType())
				|| getSubType().startsWith("*+");
	}

	public static MimeType valueOf(String value) {
		if (StringUtil.isBlank(value)) {
			return null;
		}
		String mimeType = StringUtil.tokenizeToStringArray(value, ";")[0]
				.trim().toLowerCase(Locale.ENGLISH);
		if (WILDCARD_TYPE.equals(mimeType)) {
			mimeType = "*/*";
		}
		int subIndex = mimeType.indexOf('/');
		if (subIndex == -1) {
			throw new IllegalArgumentException(mimeType
					+ ":does not contain '/'");
		}
		if (subIndex == mimeType.length() - 1) {
			throw new IllegalArgumentException(mimeType
					+ ":does not contain subtype after '/'");
		}
		String type = mimeType.substring(0, subIndex);
		String subType = mimeType.substring(subIndex + 1, mimeType.length());
		if (WILDCARD_TYPE.equals(type) && !WILDCARD_TYPE.equals(subType)) {
			throw new IllegalArgumentException(mimeType
					+ ":wildcard type is legal only in '*/*' (all mime types)");
		}
		return new MimeType(type, subType);
	}

	/**
	 * reference of Spring Web
	 */
	public boolean includes(MimeType other) {
		if (other == null) {
			return false;
		}
		if (this.isWildcardType()) {
			// */* includes anything
			return true;
		} else if (getType().equals(other.getType())) {
			if (getSubType().equals(other.getSubType())) {
				return true;
			}
			if (this.isWildcardSubtype()) {
				// wildcard with suffix, e.g. application/*+xml
				int thisPlusIdx = getSubType().indexOf('+');
				if (thisPlusIdx == -1) {
					return true;
				} else {
					// application/*+xml includes application/soap+xml
					int otherPlusIdx = other.getSubType().indexOf('+');
					if (otherPlusIdx != -1) {
						String thisSubtypeNoSuffix = getSubType().substring(0,
								thisPlusIdx);
						String thisSubtypeSuffix = getSubType().substring(
								thisPlusIdx + 1);
						String otherSubtypeSuffix = other.getSubType()
								.substring(otherPlusIdx + 1);
						if (thisSubtypeSuffix.equals(otherSubtypeSuffix)
								&& WILDCARD_TYPE.equals(thisSubtypeNoSuffix)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s/%s", this.type, this.subType);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MimeType)) {
			return false;
		}
		MimeType otherType = (MimeType) other;
		return this.type.equalsIgnoreCase(otherType.type)
				&& this.subType.equalsIgnoreCase(otherType.subType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subType == null) ? 0 : subType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
}
