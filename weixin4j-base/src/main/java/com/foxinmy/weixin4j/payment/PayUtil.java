package com.foxinmy.weixin4j.payment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.exception.WeixinPayException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.payment.mch.PrePay;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 支付工具类(JSAPI,NATIVE,MicroPay)
 * 
 * @className PayUtil
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.6
 * @see
 */
public class PayUtil {

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
	 * @param outTradeNo
	 *            订单号
	 * @param totalFee
	 *            订单总额 按实际金额传入即可(元) 构造函数会转换为分
	 * @param notifyUrl
	 *            支付通知地址
	 * @param createIp
	 *            ip地址
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付json串
	 * @throws WeixinPayException
	 */
	public static String createPayJsRequestJson(String openId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp, WeixinPayAccount weixinAccount)
			throws WeixinPayException {
		return JSON.toJSONString(createPayJsRequest(weixinAccount, openId,
				body, outTradeNo, totalFee, notifyUrl, createIp, null, null,
				null, null, null));
	}

	/**
	 * 生成V3.x版本JSAPI支付对象【完整参数】
	 * 
	 * @param weixinAccount
	 *            支付配置信息
	 * @param openId
	 *            用户ID
	 * @param body
	 *            商品描述
	 * @param outTradeNo
	 *            商户内部唯一订单号
	 * @param totalFee
	 *            商品总额 单位元
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
	 * @param goodsTag
	 *            商品标记，代金券或立减优惠功能的参数
	 * @param limitPay
	 *            指定支付方式:no_credit--指定不能使用信用卡支付
	 * @see com.foxinmy.weixin4j.payment.mch.MchPayRequest
	 * @return MchPayRequest对象；<font
	 *         color="red">注意：如果要转换为JSON格式请使用fastjson包或者直接用MchPayRequest#
	 *         asPayJsRequestJson方法</font>
	 * @throws WeixinPayException
	 */
	public static MchPayRequest createPayJsRequest(
			WeixinPayAccount weixinAccount, String openId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp, String attach, Date timeStart, Date timeExpire,
			String goodsTag, String limitPay) throws WeixinPayException {
		MchPayPackage payPackage = new MchPayPackage(weixinAccount, openId,
				body, outTradeNo, totalFee, notifyUrl, createIp,
				TradeType.JSAPI);
		payPackage.setAttach(attach);
		payPackage.setTimeStart(timeStart);
		payPackage.setTimeExpire(timeExpire);
		payPackage.setGoodsTag(goodsTag);
		payPackage.setLimitPay(limitPay);
		String paySignKey = weixinAccount.getPaySignKey();
		payPackage.setSign(paysignMd5(payPackage, paySignKey));
		PrePay prePay = createPrePay(payPackage, paySignKey);
		MchPayRequest jsPayRequest = new MchPayRequest(prePay);
		jsPayRequest.setSignType(SignType.MD5);
		jsPayRequest.setPaySign(paysignMd5(jsPayRequest, paySignKey));
		return jsPayRequest;
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
	 * @see com.foxinmy.weixin4j.payment.mch.MchPayPackage
	 * @see com.foxinmy.weixin4j.payment.mch.PrePay
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">统一下单接口</a>
	 * @return 预支付对象
	 */
	private final static WeixinRequestExecutor httpClient = new WeixinRequestExecutor();

	public static PrePay createPrePay(MchPayPackage payPackage,
			String paySignKey) throws WeixinPayException {
		if (StringUtil.isBlank(payPackage.getSign())) {
			payPackage.setSign(paysignMd5(payPackage, paySignKey));
		}
		String payJsRequestXml = XmlStream.toXML(payPackage);
		try {
			WeixinResponse response = httpClient.post(
					PayURLConsts.MCH_UNIFIEDORDER_URL, payJsRequestXml);
			PrePay prePay = response.getAsObject(new TypeReference<PrePay>() {
			});
			if (!prePay.getReturnCode().equalsIgnoreCase(Consts.SUCCESS)) {
				throw new WeixinPayException(prePay.getReturnMsg(),
						prePay.getReturnCode());
			}
			if (!prePay.getResultCode().equalsIgnoreCase(Consts.SUCCESS)) {
				throw new WeixinPayException(prePay.getResultCode(),
						prePay.getErrCodeDes());
			}
			return prePay;
		} catch (WeixinException e) {
			throw new WeixinPayException(e.getErrorCode(), e.getErrorMsg());
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
		String sign = DigestUtil.SHA1(MapUtil.toJoinString(map, false, true,
				null));
		map.remove("url");
		map.remove("accessToken");
		map.put("scope", "jsapi_address");
		map.put("signType", SignType.SHA1.name().toLowerCase());
		map.put("addrSign", sign);

		return JSON.toJSONString(map);
	}

	/**
	 * 创建V3.x NativePay支付(扫码支付)链接【模式一】
	 * 
	 * @param weixinAccount
	 *            支付配置信息
	 * @param productId
	 *            与订单ID等价
	 * @return 支付链接
	 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/native.php">扫码支付</a>
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4">模式一</a>
	 */
	public static String createNativePayRequestURL(
			WeixinPayAccount weixinAccount, String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("time_stamp", timestamp);
		map.put("nonce_str", noncestr);
		map.put("product_id", productId);
		String sign = paysignMd5(map, weixinAccount.getPaySignKey());
		return String.format(PayURLConsts.MCH_NATIVE_URL, sign,
				weixinAccount.getId(), weixinAccount.getMchId(), productId,
				timestamp, noncestr);
	}

	/**
	 * 创建V3.x NativePay支付(扫码支付)链接【模式二】【必填参数】
	 * 
	 * @param weixinAccount
	 *            支付配置信息
	 * @param productId
	 *            商品ID
	 * @param body
	 *            商品描述
	 * @param outTradeNo
	 *            商户内部唯一订单号
	 * @param totalFee
	 *            商品总额 单位元
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            订单生成的机器 IP
	 * @return 支付链接
	 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/native.php">扫码支付</a>
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5">模式二</a>
	 * @throws WeixinPayException
	 */
	public static String createNativePayRequestURL(
			WeixinPayAccount weixinAccount, String productId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp) throws WeixinPayException {
		return createNativePayRequestURL(weixinAccount, productId, body,
				outTradeNo, totalFee, notifyUrl, createIp, null, null, null,
				null, null);
	}

	/**
	 * 创建V3.x NativePay支付(扫码支付)链接【模式二】【完整参数】
	 * 
	 * @param weixinAccount
	 *            支付配置信息
	 * @param productId
	 *            商品ID
	 * @param body
	 *            商品描述
	 * @param outTradeNo
	 *            商户内部唯一订单号
	 * @param totalFee
	 *            商品总额 单位元
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
	 * @param goodsTag
	 *            商品标记，代金券或立减优惠功能的参数
	 * @param limitPay
	 *            指定支付方式:no_credit--指定不能使用信用卡支付
	 * @return 支付链接
	 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/native.php">扫码支付</a>
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5">模式二</a>
	 * @throws WeixinPayException
	 */
	public static String createNativePayRequestURL(
			WeixinPayAccount weixinAccount, String productId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp, String attach, Date timeStart, Date timeExpire,
			String goodsTag, String limitPay) throws WeixinPayException {
		MchPayPackage payPackage = new MchPayPackage(weixinAccount, null, body,
				outTradeNo, totalFee, notifyUrl, createIp, TradeType.NATIVE);
		payPackage.setProductId(productId);
		payPackage.setAttach(attach);
		payPackage.setTimeStart(timeStart);
		payPackage.setTimeExpire(timeExpire);
		payPackage.setGoodsTag(goodsTag);
		payPackage.setLimitPay(limitPay);
		String paySignKey = weixinAccount.getPaySignKey();
		payPackage.setSign(paysignMd5(payPackage, paySignKey));
		PrePay prePay = createPrePay(payPackage, paySignKey);
		return prePay.getCodeUrl();
	}

	/**
	 * 提交被扫支付
	 * 
	 * @param authCode
	 *            扫码支付授权码 ,设备读取用户微信中的条码或者二维码信息
	 * @param body
	 *            商品描述
	 * @param orderNo
	 *            商户内部唯一订单号
	 * @param orderFee
	 *            商品总额 单位元
	 * @param createIp
	 *            订单生成的机器 IP
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付的订单信息
	 * @see {@link #createMicroPay(MicroPayPackage, WeixinPayAccount)}
	 * @throws WeixinException
	 */
	public static Order createMicroPay(String authCode, String body,
			String orderNo, double orderFee, String createIp,
			WeixinPayAccount weixinAccount) throws WeixinException {
		MicroPayPackage payPackage = new MicroPayPackage(weixinAccount,
				authCode, body, orderNo, orderFee, createIp);
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
	 * @see com.foxinmy.weixin4j.payment.mch.Order
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10">提交被扫支付API</a>
	 */
	public static Order createMicroPay(MicroPayPackage payPackage,
			WeixinPayAccount weixinAccount) throws WeixinException {
		String sign = paysignMd5(payPackage, weixinAccount.getPaySignKey());
		payPackage.setSign(sign);
		String para = XmlStream.toXML(payPackage);
		WeixinResponse response = httpClient.post(
				PayURLConsts.MCH_MICROPAY_URL, para);
		return response
				.getAsObject(new TypeReference<com.foxinmy.weixin4j.payment.mch.Order>() {
				});
	}

	private static String JSAPI() throws WeixinPayException {
		WeixinPayAccount weixinAccount = JSON.parseObject(
				Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
		return createPayJsRequestJson("oyFLst1bqtuTcxK-ojF8hOGtLQao", "支付测试",
				"JSAPI01", 0.01d, "http://127.0.0.1/jsapi/notify", "127.0.0.0",
				weixinAccount);
	}

	private static String NATIVE() {
		WeixinPayAccount weixinAccount = JSON.parseObject(
				Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
		return createNativePayRequestURL(weixinAccount, "P1");
	}

	public static void main(String[] args) throws WeixinPayException {
		// V3版本下的JS支付
		System.out.println(JSAPI());
		// V3版本下的原生支付
		System.out.println(NATIVE());
	}
}
