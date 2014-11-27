package com.foxinmy.weixin4j.model;

public final class Consts {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";
	public static final String X509 = "X.509";
	public static final String AES = "AES";
	public static final String PROTOCOL_FILE = "file";
	public static final String PROTOCOL_JAR = "jar";

	public static final String OAUTH_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
	public static final String MP_ASSESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	public static final String QY_ASSESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

	public static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String MICROPAYURL = "https://api.mch.weixin.qq.com/pay/micropay";
	public static final String NATIVEURLV2 = "weixin://wxpay/bizpayurl?sign=%s&appid=%s&productid=%s&timestamp=%s&noncestr=%s";
	public static final String NATIVEURLV3 = "weixin://wxpay/bizpayurl?sign=%s&appid=%s&mch_id=%s&product_id=%s&time_stamp=%s&nonce_str=%s";
}
