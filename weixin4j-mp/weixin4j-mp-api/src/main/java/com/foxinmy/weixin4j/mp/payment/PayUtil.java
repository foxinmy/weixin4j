package com.foxinmy.weixin4j.mp.payment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.v2.JsPayRequestV2;
import com.foxinmy.weixin4j.mp.payment.v2.NativePayResponseV2;
import com.foxinmy.weixin4j.mp.payment.v2.PayPackageV2;
import com.foxinmy.weixin4j.mp.payment.v3.PayPackageV3;
import com.foxinmy.weixin4j.mp.payment.v3.PayRequestV3;
import com.foxinmy.weixin4j.mp.payment.v3.PrePay;
import com.foxinmy.weixin4j.mp.type.SignType;
import com.foxinmy.weixin4j.mp.type.TradeType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.xml.XStream;

/**
 * 支付工具类
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
	 * @param weixinConfig
	 *            appid等信息
	 * @return
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
		}
		throw new PayException("unknown pay");
	}

	/**
	 * 生成V2.x版本JSAPI支付字符串
	 * 
	 * @param payPackage
	 *            订单信息
	 * @param weixinConfig
	 *            appid等信息
	 * @return
	 */
	public static String createPayJsRequestJsonV2(PayPackageV2 payPackage,
			WeixinMpAccount weixinAccount) {
		if (StringUtils.isBlank(payPackage.getPartner())) {
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
	 *            订单总额
	 * @param ip
	 * @param weixinConfig
	 *            appid等信息
	 * @return
	 */
	public static String createPayJsRequestJsonV2(String body, String orderNo,
			double orderFee, String ip, WeixinMpAccount weixinAccount) {
		PayPackageV2 payPackage = new PayPackageV2(body, orderNo, orderFee, ip);
		payPackage.setPartner(weixinAccount.getPartnerId());
		return createPayJsRequestJsonV2(payPackage, weixinAccount);
	}

	/**
	 * sha签名(一般用于V2.x支付接口)
	 * 
	 * @param obj
	 *            签名对象
	 * @param paySignKey
	 *            支付API的密钥
	 * @return
	 */
	public static String paysignSha(Object obj, String paySignKey) {
		Map<String, String> extra = null;
		if (StringUtils.isNotBlank(paySignKey)) {
			extra = new HashMap<String, String>();
			extra.put("appKey", paySignKey);
		}
		return DigestUtils.sha1Hex(MapUtil
				.toJoinString(obj, false, true, extra));
	}

	/**
	 * md5签名(一般用于V3.x支付接口)
	 * 
	 * @param obj
	 *           签名对象
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
		return DigestUtils.md5Hex(sb.toString()).toUpperCase();
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
	 *            订单总额
	 * @param ip
	 * @param notifyUrl
	 *            支付通知地址
	 * @param weixinAccount
	 *            商户信息
	 * @return 支付json串
	 * @throws PayException
	 */
	public static String createPayJsRequestJsonV3(String openId, String body,
			String orderNo, double orderFee, String ip, String notifyUrl,
			WeixinMpAccount weixinAccount) throws PayException {
		PayPackageV3 payPackage = new PayPackageV3(weixinAccount, openId, body,
				orderNo, orderFee, ip, TradeType.JSAPI);
		payPackage.setNotify_url(notifyUrl);
		return createPayJsRequestJsonV3(payPackage, weixinAccount);
	}

	/**
	 * 生成V3.x版本JSAPI支付字符串
	 * 
	 * @param payPackage
	 *            订单信息
	 * @param weixinConfig
	 *            appid等信息
	 * @return
	 * @throws PayException
	 */
	public static String createPayJsRequestJsonV3(PayPackageV3 payPackage,
			WeixinMpAccount weixinAccount) throws PayException {
		String paySignKey = weixinAccount.getPaySignKey();
		payPackage.setSign(paysignMd5(payPackage, paySignKey));
		PrePay prePay = createPrePay(payPackage);
		PayRequestV3 jsPayRequest = new PayRequestV3(prePay);
		jsPayRequest.setPaySign(paysignMd5(jsPayRequest, paySignKey));
		jsPayRequest.setSignType(SignType.MD5);
		return JSON.toJSONString(jsPayRequest);
	}

	/**
	 * 创建预支付对象
	 * 
	 * @param payPackage
	 *            包含订单信息的对象
	 * @see com.foxinmy.weixin4j.mp.payment.v3.PayPackageV3
	 * @see com.foxinmy.weixin4j.mp.payment.v3.PrePay
	 * @return 预支付对象
	 */
	public static PrePay createPrePay(PayPackageV3 payPackage) {
		PrePay prePay = null;
		String payJsRequestXml = XStream.to(payPackage).replaceAll("__", "_");
		HttpRequest request = new HttpRequest();
		try {
			Response response = request.post(Consts.UNIFIEDORDER,
					payJsRequestXml);
			prePay = response.getAsObject(new TypeReference<PrePay>() {
			});
		} catch (WeixinException e) {
			prePay = new PrePay(e.getErrorCode(), e.getErrorMsg());
		}
		return prePay;
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
		Map<String, String> obj = new HashMap<String, String>();
		obj.put("appId", appId);
		obj.put("timeStamp", DateUtil.timestamp2string());
		obj.put("nonceStr", RandomUtil.generateString(16));
		obj.put("url", url);
		obj.put("accessToken", accessToken);
		String sign = paysignSha(obj, null);
		obj.remove("url");
		obj.remove("accessToken");
		obj.put("scope", "jsapi_address");
		obj.put("signType", SignType.SHA1.name().toLowerCase());
		obj.put("addrSign", sign);

		return JSON.toJSONString(obj);
	}

	/**
	 * 创建V2.x NativePay支付链接
	 * 
	 * @param weixinConfig
	 *            支付配置信息
	 * @param productId
	 *            与订单ID等价
	 * @return
	 */
	public String createNativePayRequestURLV2(WeixinMpAccount weixinAccount,
			String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("productid", productId);
		String sign = paysignSha(map, weixinAccount.getPaySignKey());
		return String.format(Consts.NATIVEURLV2, sign, weixinAccount.getId(),
				productId, timestamp, noncestr);
	}

	/**
	 * 创建V3.x NativePay支付链接
	 * 
	 * @param weixinConfig
	 *            支付配置信息
	 * @param productId
	 *            与订单ID等价
	 * @return
	 */
	public String createNativePayRequestURLV3(WeixinMpAccount weixinAccount,
			String productId) {
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

	public static String createNativePayRequestV2(
			WeixinMpAccount weixinAccount, PayPackageV2 payPackage) {
		NativePayResponseV2 payRequest = new NativePayResponseV2(weixinAccount,
				payPackage);
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("package", payRequest.getPackageInfo());
		map.put("retcode", payRequest.getRetCode());
		map.put("reterrmsg", payRequest.getRetMsg());
		payRequest.setPaySign(paysignSha(map, weixinAccount.getPaySignKey()));
		return XStream.to(payRequest);
	}

	/**
	 * 提交被扫支付
	 * 
	 * @param authCode
	 *            扫码支付授权码 ,设备读取用 户微信中的条码或者二维码 信息
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
	 * @return 返回数据
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
	 * 提交被扫支付
	 * 
	 * @param payPackage
	 *            订单信息
	 * @param weixinAccount
	 *            商户信息
	 * @return 返回数据
	 * @throws WeixinException
	 */
	public static com.foxinmy.weixin4j.mp.payment.v3.Order createMicroPay(
			MicroPayPackage payPackage, WeixinMpAccount weixinAccount)
			throws WeixinException {
		String sign = paysignMd5(payPackage, weixinAccount.getPaySignKey());
		payPackage.setSign(sign);
		String para = XStream.to(payPackage).replaceAll("__", "_");
		HttpRequest request = new HttpRequest();
		Response response = request.post(Consts.MICROPAYURL, para);
		return response
				.getAsObject(new TypeReference<com.foxinmy.weixin4j.mp.payment.v3.Order>() {
				});
	}
}
