package com.foxinmy.weixin4j.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.BitSet;

/**
 * 
 * @className URLEncodingUtil
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月15日
 * @since JDK 1.6
 * @see
 */
public final class URLEncodingUtil {
	/**
	 * Unreserved characters, i.e. alphanumeric, plus: {@code _ - ! . ~ ' ( ) *}
	 * <p>
	 * This list is the same as the {@code unreserved} list in <a
	 * href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
	 */
	private static final BitSet UNRESERVED = new BitSet(256);
	/**
	 * Punctuation characters: , ; : $ & + =
	 * <p>
	 * These are the additional characters allowed by userinfo.
	 */
	private static final BitSet PUNCT = new BitSet(256);
	/**
	 * Characters which are safe to use in userinfo, i.e. {@link #UNRESERVED}
	 * plus {@link #PUNCT}uation
	 */
	private static final BitSet USERINFO = new BitSet(256);
	/**
	 * Characters which are safe to use in a path, i.e. {@link #UNRESERVED} plus
	 * {@link #PUNCT}uation plus / @
	 */
	private static final BitSet PATHSAFE = new BitSet(256);
	/**
	 * Characters which are safe to use in a fragment, i.e. {@link #RESERVED}
	 * plus {@link #UNRESERVED}
	 */
	private static final BitSet FRAGMENT = new BitSet(256);

	/**
	 * Reserved characters, i.e. {@code ;/?:@&=+$,[]}
	 * <p>
	 * This list is the same as the {@code reserved} list in <a
	 * href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a> as augmented by
	 * <a href="http://www.ietf.org/rfc/rfc2732.txt">RFC 2732</a>
	 */
	private static final BitSet RESERVED = new BitSet(256);

	/**
	 * Safe characters for x-www-form-urlencoded data, as per
	 * java.net.URLEncoder and browser behaviour, i.e. alphanumeric plus
	 * {@code "-", "_", ".", "*"}
	 */
	private static final BitSet URLENCODER = new BitSet(256);

	static {
		// unreserved chars
		// alpha characters
		for (int i = 'a'; i <= 'z'; i++) {
			UNRESERVED.set(i);
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			UNRESERVED.set(i);
		}
		// numeric characters
		for (int i = '0'; i <= '9'; i++) {
			UNRESERVED.set(i);
		}
		UNRESERVED.set('_'); // these are the charactes of the "mark" list
		UNRESERVED.set('-');
		UNRESERVED.set('.');
		UNRESERVED.set('*');
		URLENCODER.or(UNRESERVED); // skip remaining unreserved characters
		UNRESERVED.set('!');
		UNRESERVED.set('~');
		UNRESERVED.set('\'');
		UNRESERVED.set('(');
		UNRESERVED.set(')');
		// punct chars
		PUNCT.set(',');
		PUNCT.set(';');
		PUNCT.set(':');
		PUNCT.set('$');
		PUNCT.set('&');
		PUNCT.set('+');
		PUNCT.set('=');
		// Safe for userinfo
		USERINFO.or(UNRESERVED);
		USERINFO.or(PUNCT);

		// URL path safe
		PATHSAFE.or(UNRESERVED);
		PATHSAFE.set('/'); // segment separator
		PATHSAFE.set(';'); // param separator
		PATHSAFE.set(':'); // rest as per list in 2396, i.e. : @ & = + $ ,
		PATHSAFE.set('@');
		PATHSAFE.set('&');
		PATHSAFE.set('=');
		PATHSAFE.set('+');
		PATHSAFE.set('$');
		PATHSAFE.set(',');

		RESERVED.set(';');
		RESERVED.set('/');
		RESERVED.set('?');
		RESERVED.set(':');
		RESERVED.set('@');
		RESERVED.set('&');
		RESERVED.set('=');
		RESERVED.set('+');
		RESERVED.set('$');
		RESERVED.set(',');
		RESERVED.set('['); // added by RFC 2732
		RESERVED.set(']'); // added by RFC 2732

		FRAGMENT.or(RESERVED);
		FRAGMENT.or(UNRESERVED);
	}

	private static final int RADIX = 16;

	public static String encoding(final String content, final Charset charset,
			final boolean blankAsPlus) {
		if (content == null) {
			return null;
		}
		StringBuilder buf = new StringBuilder();
		ByteBuffer bb = charset.encode(content);
		while (bb.hasRemaining()) {
			int b = bb.get() & 0xff;
			if (URLENCODER.get(b)) {
				buf.append((char) b);
			} else if (blankAsPlus && b == ' ') {
				buf.append('+');
			} else {
				buf.append("%");
				char hex1 = Character.toUpperCase(Character.forDigit(
						(b >> 4) & 0xF, RADIX));
				char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF,
						RADIX));
				buf.append(hex1);
				buf.append(hex2);
			}
		}
		return buf.toString();
	}
}
