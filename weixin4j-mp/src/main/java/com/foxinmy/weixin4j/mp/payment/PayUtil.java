package com.foxinmy.weixin4j.mp.payment;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinHttpClient;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.mp.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.v2.JsPayRequestV2;
import com.foxinmy.weixin4j.mp.payment.v2.NativePayResponseV2;
import com.foxinmy.weixin4j.mp.payment.v2.PayPackageV2;
import com.foxinmy.weixin4j.mp.payment.v3.PayPackageV3;
import com.foxinmy.weixin4j.mp.payment.v3.PayRequestV3;
import com.foxinmy.weixin4j.mp.payment.v3.PrePay;
import com.foxinmy.weixin4j.mp.type.SignType;
import com.foxinmy.weixin4j.mp.type.TradeType;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 支付工具类(JSAPI,NATIVE,MicroPay)
 * 
 * @className PayUtil
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class PayUtil {
	/**
	 * 生成JSAPI字符串
	 * 
	 * @param payPackage
	 *            订单信息
	 * @param WeixinMpAccount
	 *            商户信息
	 * @since V2 & V3
	 * @return 支付json串
	 * @throws PayException
	 */
	public static String createPayJsRequestJson(PayPackage payPackage,
			WeixinMpAccount weixinAccount) throws PayException {
		if (payPackage instanceof PayPackageV2) {
			return createPayJsRequestJsonV2((PayPackageV2) payPackage,
					weixinAccount);
		} else if (payPackage instanceof PayPackageV3) {
			return createPayJsRequestJsonV3((PayPackageV3) payPackage,
					weixinAccount);
		} else {
			throw new PayException("unknown pay");
		}
	}

	/**
	 * 生成V2.x版本JSAPI支付字符串
	 * 
	 * @param payPackage
	 *            订单信息
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付json串
	 */
	public static String createPayJsRequestJsonV2(PayPackageV2 payPackage,
			WeixinMpAccount weixinAccount) {
		if (StringUtil.isBlank(payPackage.getPartner())) {
			payPackage.setPartner(weixinAccount.getPartnerId());
		}
		JsPayRequestV2 jsPayRequest = new JsPayRequestV2(weixinAccount,
				payPackage);
		jsPayRequest.setPaySign(paysignSha(jsPayRequest,
				weixinAccount.getPaySignKey()));
		jsPayRequest.setSignType(SignType.SHA1);
		return JSON.toJSONString(jsPayRequest);
	}

	/**
	 * 生成V2.x版本JSAPI支付字符串
	 * 
	 * @param body
	 *            支付详情
	 * @param orderNo
	 *            订单号
	 * @param orderFee
	 *            订单总额 按实际金额传入即可(元) 构造函数会转换为分
	 * @param ip
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付json串
	 */
	public static String createPayJsRequestJsonV2(String body, String orderNo,
			double orderFee, String notify_url, String ip,
			WeixinMpAccount weixinAccount) {
		PayPackageV2 payPackage = new PayPackageV2(body, orderNo, orderFee,
				notify_url, ip);
		payPackage.setPartner(weixinAccount.getPartnerId());
		return createPayJsRequestJsonV2(payPackage, weixinAccount);
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
	 * md5签名(一般用于V3.x支付接口)
	 * 
	 * @param obj
	 *            签名对象
	 * @param paySignKey
	 *            支付API的密钥
	 * @return
	 */
	public static String paysignMd5(Object obj, String paySignKey) {
		StringBuilder sb = new StringBuilder();
		// a--->string1
		sb.append(MapUtil.toJoinString(obj, false, false, null));
		// b--->
		// 在 string1 最后拼接上 key=paternerKey 得到 stringSignTemp 字符串,并 对
		// stringSignTemp 进行 md5 运算
		// 再将得到的 字符串所有字符转换为大写 ,得到 sign 值 signValue。
		sb.append("&key=").append(paySignKey);
		return DigestUtil.MD5(sb.toString()).toUpperCase();
	}

	/**
	 * 生成V3.x版本JSAPI支付字符串
	 * 
	 * @param openId
	 *            用户ID
	 * @param body
	 *            订单描述
	 * @param orderNo
	 *            订单号
	 * @param orderFee
	 *            订单总额 按实际金额传入即可(元) 构造函数会转换为分
	 * @param notifyUrl
	 *            支付通知地址
	 * @param ip
	 *            ip地址
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付json串
	 * @throws PayException
	 */
	public static String createPayJsRequestJsonV3(String openId, String body,
			String orderNo, double orderFee, String notifyUrl, String ip,
			WeixinMpAccount weixinAccount) throws PayException {
		PayPackageV3 payPackage = new PayPackageV3(weixinAccount, openId, body,
				orderNo, orderFee, ip, TradeType.JSAPI);
		payPackage.setNotifyUrl(notifyUrl);
		return createPayJsRequestJsonV3(payPackage, weixinAccount);
	}

	/**
	 * 生成V3.x版本JSAPI支付字符串
	 * 
	 * @param payPackage
	 *            订单信息
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付json串
	 * @throws PayException
	 */
	public static String createPayJsRequestJsonV3(PayPackageV3 payPackage,
			WeixinMpAccount weixinAccount) throws PayException {
		String paySignKey = weixinAccount.getPaySignKey();
		payPackage.setSign(paysignMd5(payPackage, paySignKey));
		PrePay prePay = createPrePay(payPackage, paySignKey);
		PayRequestV3 jsPayRequest = new PayRequestV3(prePay);
		jsPayRequest.setSignType(SignType.MD5);
		jsPayRequest.setPaySign(paysignMd5(jsPayRequest, paySignKey));
		return JSON.toJSONString(jsPayRequest);
	}

	/**
	 * 统一下单接口</br>
	 * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI
	 * 、APP等不同场景生成交易串调起支付。
	 * 
	 * @param payPackage
	 *            包含订单信息的对象
	 * @param paySignKey
	 *            <font color="red">如果sign为空 则拿paysignkey进行签名</font>
	 * @see com.foxinmy.weixin4j.mp.payment.v3.PayPackageV3
	 * @see com.foxinmy.weixin4j.mp.payment.v3.PrePay
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">统一下单接口</a>
	 * @return 预支付对象
	 */
	private final static WeixinHttpClient httpClient = new WeixinHttpClient();

	public static PrePay createPrePay(PayPackageV3 payPackage, String paySignKey)
			throws PayException {
		if (StringUtil.isBlank(payPackage.getSign())) {
			payPackage.setSign(paysignMd5(payPackage, paySignKey));
		}
		String payJsRequestXml = XmlStream.toXML(payPackage);
		try {
			WeixinResponse response = httpClient.post(Consts.UNIFIEDORDER,
					payJsRequestXml);
			PrePay prePay = response.getAsObject(new TypeReference<PrePay>() {
			});
			if (!prePay.getReturnCode().equalsIgnoreCase(Consts.SUCCESS)) {
				throw new PayException(prePay.getReturnMsg(),
						prePay.getReturnCode());
			}
			if (!prePay.getResultCode().equalsIgnoreCase(Consts.SUCCESS)) {
				throw new PayException(prePay.getResultCode(),
						prePay.getErrCodeDes());
			}
			return prePay;
		} catch (WeixinException e) {
			throw new PayException(e.getErrorCode(), e.getErrorMsg());
		}
	}

	/**
	 * <p>
	 * 生成编辑地址请求
	 * </p>
	 * 
	 * err_msg edit_address:ok获取编辑收货地址成功</br> edit_address:fail获取编辑收货地址失败</br>
	 * userName 收货人姓名</br> telNumber 收货人电话</br> addressPostalCode 邮编</br>
	 * proviceFirstStageName 国标收货地址第一级地址</br> addressCitySecondStageName
	 * 国标收货地址第二级地址</br> addressCountiesThirdStageName 国标收货地址第三级地址</br>
	 * addressDetailInfo 详细收货地址信息</br> nationalCode 收货地址国家码</br>
	 * 
	 * @param appId
	 *            公众号的ID
	 * @param url
	 *            当前访问页的URL
	 * @param accessToken
	 *            snsapi_base授权时产生的token
	 * @return
	 */
	public static String createAddressRequestJson(String appId, String url,
			String accessToken) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appId", appId);
		map.put("timeStamp", DateUtil.timestamp2string());
		map.put("nonceStr", RandomUtil.generateString(16));
		map.put("url", url);
		map.put("accessToken", accessToken);
		String sign = paysignSha(map);
		map.remove("url");
		map.remove("accessToken");
		map.put("scope", "jsapi_address");
		map.put("signType", SignType.SHA1.name().toLowerCase());
		map.put("addrSign", sign);

		return JSON.toJSONString(map);
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
			WeixinMpAccount weixinAccount, String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("productid", productId);
		map.put("appkey", weixinAccount.getPaySignKey());
		String sign = paysignSha(map);
		return String.format(Consts.NATIVEURLV2, sign, weixinAccount.getId(),
				productId, timestamp, noncestr);
	}

	/**
	 * 创建V3.x NativePay支付(扫码支付)链接
	 * 
	 * @param weixinAccount
	 *            支付配置信息
	 * @param productId
	 *            与订单ID等价
	 * @return 支付链接
	 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/native.php">扫码支付</a>
	 */
	public static String createNativePayRequestURLV3(
			WeixinMpAccount weixinAccount, String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("time_stamp", timestamp);
		map.put("nonce_str", noncestr);
		map.put("product_id", productId);
		String sign = paysignMd5(map, weixinAccount.getPaySignKey());
		return String.format(Consts.NATIVEURLV3, sign, weixinAccount.getId(),
				weixinAccount.getMchId(), productId, timestamp, noncestr);
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
			WeixinMpAccount weixinAccount, PayPackageV2 payPackage) {
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

	/**
	 * 提交被扫支付
	 * 
	 * @param authCode
	 *            扫码支付授权码 ,设备读取用户微信中的条码或者二维码信息
	 * @param body
	 *            商品描述
	 * @param attach
	 *            附加数据
	 * @param orderNo
	 *            商户内部唯一订单号
	 * @param orderFee
	 *            商品总额 单位元
	 * @param ip
	 *            订单生成的机器 IP
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付的订单信息
	 * @see {@link com.foxinmy.weixin4j.mp.payment.PayUtil#createMicroPay(MicroPayPackage, WeixinMpAccount)}
	 * @throws WeixinException
	 */
	public static com.foxinmy.weixin4j.mp.payment.v3.Order createMicroPay(
			String authCode, String body, String attach, String orderNo,
			double orderFee, String ip, WeixinMpAccount weixinAccount)
			throws WeixinException {
		MicroPayPackage payPackage = new MicroPayPackage(weixinAccount, body,
				attach, orderNo, orderFee, ip, authCode);
		return createMicroPay(payPackage, weixinAccount);
	}

	/**
	 * 提交被扫支付:收银员使用扫码设备读取微信用户刷卡授权码以后，二维码或条码信息传送至商户收银台，由商户收银台或者商户后台调用该接口发起支付.
	 * 
	 * @param payPackage
	 *            订单信息
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付的订单信息
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.payment.v3.Order
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10">提交被扫支付API</a>
	 */
	public static com.foxinmy.weixin4j.mp.payment.v3.Order createMicroPay(
			MicroPayPackage payPackage, WeixinMpAccount weixinAccount)
			throws WeixinException {
		String sign = paysignMd5(payPackage, weixinAccount.getPaySignKey());
		payPackage.setSign(sign);
		String para = XmlStream.toXML(payPackage);
		WeixinResponse response = httpClient.post(Consts.MICROPAYURL, para);
		return response
				.getAsObject(new TypeReference<com.foxinmy.weixin4j.mp.payment.v3.Order>() {
				});
	}

	private static String JSAPIV2() {
		WeixinMpAccount weixinAccount = JSON.parseObject(
				ConfigUtil.getValue("account"), WeixinMpAccount.class);
		return createPayJsRequestJsonV2("支付测试", "JSAPI01", 0.01d, "127.0.0.0",
				"http://127.0.0.1/jsapi/notify", weixinAccount);
	}

	private static String NATIVEV2() {
		WeixinMpAccount weixinAccount = JSON.parseObject(
				ConfigUtil.getValue("account"), WeixinMpAccount.class);
		return createNativePayRequestURLV2(weixinAccount, "P1");
	}

	private static String JSAPIV3() throws PayException {
		WeixinMpAccount weixinAccount = JSON.parseObject(
				ConfigUtil.getValue("account"), WeixinMpAccount.class);
		return createPayJsRequestJsonV3("oyFLst1bqtuTcxK-ojF8hOGtLQao", "支付测试",
				"JSAPI01", 0.01d, "http://127.0.0.1/jsapi/notify", "127.0.0.0",
				weixinAccount);
	}

	private static String NATIVEV3() {
		WeixinMpAccount weixinAccount = JSON.parseObject(
				ConfigUtil.getValue("account"), WeixinMpAccount.class);
		return createNativePayRequestURLV3(weixinAccount, "P1");
	}

	public static void main(String[] args) throws PayException {
		// V2版本下的JS支付
		System.out.println(JSAPIV2());
		// V2版本下的原生支付
		System.out.println(NATIVEV2());
		// V3版本下的JS支付
		System.out.println(JSAPIV3());
		// V3版本下的原生支付
		System.out.println(NATIVEV3());
	}
}
