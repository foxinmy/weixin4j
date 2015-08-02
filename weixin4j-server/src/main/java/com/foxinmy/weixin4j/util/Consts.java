package com.foxinmy.weixin4j.util;

import io.netty.util.AttributeKey;

import java.nio.charset.Charset;

import com.foxinmy.weixin4j.socket.WeixinMessageTransfer;

/**
 * 常量类
 * 
 * @className Consts
 * @author jy
 * @date 2015年4月19日
 * @since JDK 1.7
 * @see
 */
public final class Consts {

	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final Charset GBK = Charset.forName("GBK");
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";
	public static final String X509 = "X.509";
	public static final String AES = "AES";
	public static final String MD5 = "MD5";
	public static final String SHA = "SHA";
	public static final String SHA1 = "SHA-1";
	public static final String PROTOCOL_FILE = "file";
	public static final String PROTOCOL_JAR = "jar";
	public static final String CONTENTTYPE$APPLICATION_XML = "application/xml";
	public static final String CONTENTTYPE$TEXT_PLAIN = "text/plain";

	public static final AttributeKey<WeixinMessageTransfer> MESSAGE_TRANSFER_KEY = AttributeKey
			.valueOf("$_MESSAGETRANSFER");
}
