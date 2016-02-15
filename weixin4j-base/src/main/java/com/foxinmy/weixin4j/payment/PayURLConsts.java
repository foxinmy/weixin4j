package com.foxinmy.weixin4j.payment;


/**
 * 支付URL常量类
 * 
 * @className PayURLConsts
 * @author jy
 * @date 2014年12月3日
 * @since JDK 1.6
 * @see
 */
public final class PayURLConsts {

	private static final String MCH_BASE_URL = "https://api.mch.weixin.qq.com";

	/**
	 * 商户平台下统一订单生成的url
	 */
	public static final String MCH_UNIFIEDORDER_URL = MCH_BASE_URL
			+ "/pay/unifiedorder";
	/**
	 * 订单查询(商户平台)
	 */
	public static final String MCH_ORDERQUERY_URL = MCH_BASE_URL
			+ "/pay/orderquery";
	/**
	 * 关闭订单(商户平台)
	 */
	public static final String MCH_CLOSEORDER_URL = MCH_BASE_URL
			+ "/pay/closeorder";
	/**
	 * 对账单下载(商户平台)
	 */
	public static final String MCH_DOWNLOADBILL_URL = MCH_BASE_URL
			+ "/pay/downloadbill";
	/**
	 * 退款查询(商户平台)
	 */
	public static final String MCH_REFUNDQUERY_URL = MCH_BASE_URL
			+ "/pay/refundquery";
	/**
	 * 退款申请(商户平台)
	 */
	public static final String MCH_REFUNDAPPLY_URL = MCH_BASE_URL
			+ "/secapi/pay/refund";
	/**
	 * 冲正撤销(商户平台)
	 */
	public static final String MCH_ORDERREVERSE_URL = MCH_BASE_URL
			+ "/secapi/pay/reverse";
	/**
	 * 被扫支付&刷卡支付(商户平台)
	 */
	public static final String MCH_MICROPAY_URL = MCH_BASE_URL
			+ "/pay/micropay";
	/**
	 * 接口上报(商户平台)
	 */
	public static final String MCH_PAYREPORT_URL = MCH_BASE_URL
			+ "/payitil/report";
	/**
	 * 发送现金红包-普通红包(商户平台)
	 */
	public static final String MCH_REDPACKSEND_URL = MCH_BASE_URL
			+ "/mmpaymkttransfers/sendredpack";
	/**
	 * 发送现金红包-裂变红包(商户平台)
	 */
	public static final String MCH_REDPACK_GROUPSEND_URL = MCH_BASE_URL
			+ "/mmpaymkttransfers/sendgroupredpack";
	/**
	 * 查询现金红包(商户平台)
	 */
	public static final String MCH_REDPACKQUERY_URL = MCH_BASE_URL
			+ "/mmpaymkttransfers/gethbinfo";
	/**
	 * 企业向个人付款(商户平台)
	 */
	public static final String MCH_ENPAYMENT_URL = MCH_BASE_URL
			+ "/mmpaymkttransfers/promotion/transfers";
	/**
	 * 企业付款查询(商户平台)
	 */
	public static final String MCH_ENPAYQUERY_URL = MCH_BASE_URL
			+ "/mmpaymkttransfers/gettransferinfo";
	/**
	 * 发放代金券(商户平台)
	 */
	public static final String MCH_COUPONSEND_URL = MCH_BASE_URL
			+ "/mmpaymkttransfers/send_coupon";
	/**
	 * 查询代金券批次信息(商户平台)
	 */
	public static final String MCH_COUPONSTOCKQUERY_URL = MCH_BASE_URL
			+ "/mmpaymkttransfers/query_coupon_stock";
	/**
	 * 查询代金券详细信息(商户平台)
	 */
	public static final String MCH_COUPONDETAILQUERY_URL = MCH_BASE_URL
			+ "/promotion/query_coupon";
	/**
	 * 长链接转换(商户平台)
	 */
	public static final String MCH_SHORTURL_URL = MCH_BASE_URL
			+ "/tools/shorturl";
	/**
	 * 商户平台下native支付的url(模式1)
	 */
	public static final String MCH_NATIVE_URL = "weixin://wxpay/bizpayurl?sign=%s&appid=%s&mch_id=%s&product_id=%s&time_stamp=%s&nonce_str=%s";

	/**
	 * WAP支付
	 * 
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/wap.php?chapter=15_1">WAP支付说明</a>
	 */
	public static final String MCH_WAP_URL = "weixin://wap/pay?%s";
	/**
	 * 授权码查询OPENID接口
	 */
	public static final String MCH_AUTHCODE_OPENID_URL = MCH_BASE_URL
			+ "/tools/authcodetoopenid";
}
