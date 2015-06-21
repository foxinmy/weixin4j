package com.foxinmy.weixin4j.mp.type;


/**
 * URL常量类
 * 
 * @className URLConsts
 * @author jy
 * @date 2014年12月3日
 * @since JDK 1.7
 * @see
 */
public final class URLConsts {
	/**
	 * 公众平台获取token的url
	 */
	public static final String ASSESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	/**
	 * 公众平台jssdk获取token的url
	 */
	public static final String JS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	/**
	 * 商户平台下统一订单生成的url
	 */
	public static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 商户平台下刷卡支付的url
	 */
	public static final String MICROPAYURL = "https://api.mch.weixin.qq.com/pay/micropay";
	/**
	 * V2支付下natvie支付的url
	 */
	public static final String NATIVEURLV2 = "weixin://wxpay/bizpayurl?sign=%s&appid=%s&productid=%s&timestamp=%s&noncestr=%s";
	/**
	 * 商户平台(V3)下native支付的url
	 */
	public static final String NATIVEURLV3 = "weixin://wxpay/bizpayurl?sign=%s&appid=%s&mch_id=%s&product_id=%s&time_stamp=%s&nonce_str=%s";
}
