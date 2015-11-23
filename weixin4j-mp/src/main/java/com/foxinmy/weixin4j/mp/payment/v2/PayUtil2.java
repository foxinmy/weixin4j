package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinPayException;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * V2支付工具类(JSAPI,NATIVE)
 * 
 * @className PayUtil2
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class PayUtil2 {

	/**
	 * 生成V2.x版本JSAPI支付字符串
	 * 
	 * @param body
	 *            支付详情
	 * @param outTradeNo
	 *            订单号
	 * @param totalFee
	 *            订单总额 按实际金额传入即可(元) 构造函数会转换为分
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            订单生成的机器 IP
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付json串
	 */
	public static String createPayJsRequestJsonV2(String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp, WeixinPayAccount weixinAccount) {
		return createPayJsRequestJsonV2(weixinAccount, body, outTradeNo,
				totalFee, notifyUrl, createIp, null, null, null, 0d, 0d, null);
	}

	/**
	 * 生成V2.x版本JSAPI支付字符串
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param body
	 *            支付详情
	 * @param outTradeNo
	 *            订单号
	 * @param totalFee
	 *            订单总额 按实际金额传入即可(元) 构造函数会转换为分
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            订单生成的机器 IP
	 * @param attach
	 *            附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * @param timeStart
	 *            订单生成时间，格式为yyyyMMddHHmmss
	 * @param timeExpire
	 *            订单失效时间，格式为yyyyMMddHHmmss;注意：最短失效时间间隔必须大于5分钟
	 * @param transportFee
	 *            物流费用 如有值 必须保证 transportFee+productFee=totalFee
	 * @param transportFee
	 *            商品费用 如有值 必须保证 transportFee+productFee=totalFee
	 * @param goodsTag
	 *            商品标记，代金券或立减优惠功能的参数
	 * @return 支付json串
	 */
	public static String createPayJsRequestJsonV2(
			WeixinPayAccount weixinAccount, String body, String outTradeNo,
			double totalFee, String notifyUrl, String createIp, String attach,
			Date timeStart, Date timeExpire, double transportFee,
			double productFee, String goodsTag) {
		PayPackageV2 payPackage = new PayPackageV2(
				weixinAccount.getPartnerId(), body, outTradeNo, totalFee,
				notifyUrl, createIp);
		payPackage.setAttach(attach);
		payPackage.setTimeStart(timeStart);
		payPackage.setTimeExpire(timeExpire);
		payPackage.setTransportFee(transportFee);
		payPackage.setProductFee(productFee);
		payPackage.setGoodsTag(goodsTag);
		JsPayRequestV2 jsPayRequest = new JsPayRequestV2(weixinAccount,
				payPackage);
		jsPayRequest.setPaySign(paysignSha(jsPayRequest,
				weixinAccount.getPaySignKey()));
		jsPayRequest.setSignType(SignType.SHA1);
		return JSON.toJSONString(jsPayRequest);
	}

	/**
	 * sha签名(一般用于V2.x支付接口)
	 * 
	 * @param obj
	 *            签名对象
	 * @return
	 */
	public static String paysignSha(Object obj) {
		return DigestUtil.SHA1(MapUtil.toJoinString(obj, false, true, null));
	}

	/**
	 * sha签名(一般用于V2.x支付接口)
	 * 
	 * @param obj
	 *            签名对象
	 * @param paySignKey
	 *            支付API的密钥<font color="red">请注意排序放进去的是put("appKey",
	 *            paySignKey)</font>
	 * @return
	 */
	public static String paysignSha(Object obj, String paySignKey) {
		Map<String, String> extra = new HashMap<String, String>();
		extra.put("appKey", paySignKey);
		return DigestUtil.SHA1(MapUtil.toJoinString(obj, false, true, extra));
	}

	/**
	 * 创建V2.x NativePay支付链接
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param productId
	 *            与订单ID等价
	 * @return 支付链接
	 */
	public static String createNativePayRequestURLV2(
			WeixinPayAccount weixinAccount, String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("productid", productId);
		map.put("appkey", weixinAccount.getPaySignKey());
		String sign = paysignSha(map);
		return String
				.format("􏳈􏳈􏳈􏳈􏱗􏱗􏱗􏱗􏱕􏱕􏱕􏱕􏳉􏳉􏳉􏳉􏱕􏱕􏱕􏱕􏱩􏱩􏱩􏱩􏰛􏰛􏰛􏰛􏳊􏳊􏳊􏳊􏳊􏳊􏳊􏳊􏳈􏳈􏳈􏳈􏳉􏳉􏳉􏳉􏱶􏱶􏱶􏱶􏱓􏱓􏱓􏱓􏱭􏱭􏱭􏱭􏳊􏳊􏳊􏳊􏳋􏳋􏳋􏳋􏱕􏱕􏱕􏱕􏳌􏳌􏳌􏳌􏱶􏱶􏱶􏱶􏱓􏱓􏱓􏱓􏱭􏱭􏱭􏱭􏱰􏱰􏱰􏱰􏱨􏱨􏱨􏱨􏳍􏳍􏳍􏳍􏳎􏳎􏳎􏳎􏱱􏱱􏱱􏱱􏱕􏱕􏱕􏱕􏱦􏱦􏱦􏱦􏱩􏱩􏱩􏱩􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱓􏱓􏱓􏱓􏱶􏱶􏱶􏱶􏱶􏱶􏱶􏱶􏱕􏱕􏱕􏱕􏱪􏱪􏱪􏱪􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱶􏱶􏱶􏱶􏱨􏱨􏱨􏱨􏳟􏳟􏳟􏳟􏱪􏱪􏱪􏱪􏱰􏱰􏱰􏱰􏱷􏱷􏱷􏱷􏱔􏱔􏱔􏱔􏱕􏱕􏱕􏱕􏱪􏱪􏱪􏱪􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱔􏱔􏱔􏱔􏱕􏱕􏱕􏱕􏳈􏳈􏳈􏳈􏱗􏱗􏱗􏱗􏱕􏱕􏱕􏱕􏳉􏳉􏳉􏳉􏱕􏱕􏱕􏱕􏱩􏱩􏱩􏱩􏰛􏰛􏰛􏰛􏳊􏳊􏳊􏳊􏳊􏳊􏳊􏳊􏳈􏳈􏳈􏳈􏳉􏳉􏳉􏳉􏱶􏱶􏱶􏱶􏱓􏱓􏱓􏱓􏱭􏱭􏱭􏱭􏳊􏳊􏳊􏳊􏳋􏳋􏳋􏳋􏱕􏱕􏱕􏱕􏳌􏳌􏳌􏳌􏱶􏱶􏱶􏱶􏱓􏱓􏱓􏱓􏱭􏱭􏱭􏱭􏱰􏱰􏱰􏱰􏱨􏱨􏱨􏱨􏳍􏳍􏳍􏳍􏳎􏳎􏳎􏳎􏱱􏱱􏱱􏱱􏱕􏱕􏱕􏱕􏱦􏱦􏱦􏱦􏱩􏱩􏱩􏱩􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱓􏱓􏱓􏱓􏱶􏱶􏱶􏱶􏱶􏱶􏱶􏱶􏱕􏱕􏱕􏱕􏱪􏱪􏱪􏱪􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱶􏱶􏱶􏱶􏱨􏱨􏱨􏱨􏳟􏳟􏳟􏳟􏱪􏱪􏱪􏱪􏱰􏱰􏱰􏱰􏱷􏱷􏱷􏱷􏱔􏱔􏱔􏱔􏱕􏱕􏱕􏱕􏱪􏱪􏱪􏱪􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱔􏱔􏱔􏱔􏱕􏱕􏱕􏱕􏳠􏳠􏳠􏳠􏱗􏱗􏱗􏱗􏱱􏱱􏱱􏱱􏱔􏱔􏱔􏱔􏱓􏱓􏱓􏱓􏳠􏳠􏳠􏳠􏱶􏱶􏱶􏱶􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱩􏱩􏱩􏱩􏳟􏳟􏳟􏳟􏱩􏱩􏱩􏱩􏱷􏱷􏱷􏱷􏱗􏱗􏱗􏱗􏱱􏱱􏱱􏱱􏱔􏱔􏱔􏱔􏱨􏱨􏱨􏱨􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝weixin://wxpay/bizpayurl?sign=%s&appid=%s&productid=%s&timestamp=%s&nocestr=%s􏳠􏳠􏳠􏳠􏱗􏱗􏱗􏱗􏱱􏱱􏱱􏱱􏱔􏱔􏱔􏱔􏱓􏱓􏱓􏱓􏳠􏳠􏳠􏳠􏱶􏱶􏱶􏱶􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳞􏳞􏳞􏳞􏱩􏱩􏱩􏱩􏳟􏳟􏳟􏳟􏱩􏱩􏱩􏱩􏱷􏱷􏱷􏱷􏱗􏱗􏱗􏱗􏱱􏱱􏱱􏱱􏱔􏱔􏱔􏱔􏱨􏱨􏱨􏱨􏳜􏳜􏳜􏳜􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝􏳝",
						sign, weixinAccount.getId(), productId, timestamp,
						noncestr);
	}

	/**
	 * 创建V2.x NATIVE回调时的响应字符串
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param payPackage
	 *            订单信息
	 * @return
	 */
	public static String createNativePayResponseV2(
			WeixinPayAccount weixinAccount, PayPackageV2 payPackage) {
		NativePayResponseV2 payRequest = new NativePayResponseV2(weixinAccount,
				payPackage);
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("appkey", weixinAccount.getPaySignKey());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("package", payRequest.getPackageInfo());
		map.put("retcode", payRequest.getRetCode());
		map.put("reterrmsg", payRequest.getRetMsg());
		payRequest.setPaySign(paysignSha(map));
		return XmlStream.toXML(payRequest);
	}

	private static String JSAPIV2() {
		WeixinPayAccount weixinAccount = JSON.parseObject(
				Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
		return createPayJsRequestJsonV2("支付测试", "JSAPI01", 0.01d, "127.0.0.0",
				"http://127.0.0.1/jsapi/notify", weixinAccount);
	}

	private static String NATIVEV2() {
		WeixinPayAccount weixinAccount = JSON.parseObject(
				Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
		return createNativePayRequestURLV2(weixinAccount, "P1");
	}

	public static void main(String[] args) throws WeixinPayException {
		// V2版本下的JS支付
		System.out.println(JSAPIV2());
		// V2版本下的原生支付
		System.out.println(NATIVEV2());
	}
}
