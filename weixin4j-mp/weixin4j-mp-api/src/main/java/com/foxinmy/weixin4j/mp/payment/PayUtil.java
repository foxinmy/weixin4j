package com.foxinmy.weixin4j.mp.payment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.payment.v2.JsPayRequestV2;
import com.foxinmy.weixin4j.mp.payment.v2.NativePayResponseV2;
import com.foxinmy.weixin4j.mp.payment.v2.PayPackageV2;
import com.foxinmy.weixin4j.mp.payment.v3.PayPackageV3;
import com.foxinmy.weixin4j.mp.payment.v3.PayRequestV3;
import com.foxinmy.weixin4j.mp.payment.v3.PrePay;
import com.foxinmy.weixin4j.mp.type.SignType;
import com.foxinmy.weixin4j.mp.type.TradeType;
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

	private static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private static final String NATIVEURLV2 = "weixin://wxpay/bizpayurl?sign=%s&appid=%s&productid=%s&timestamp=%s&noncestr=%s";
	private static final String NATIVEURLV3 = "weixin://wxpay/bizpayurl?sign=%s&appid=%s&mch_id=%s&product_id=%s&time_stamp=%s&nonce_str=%s";

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
			WeixinAccount weixinAccount) throws PayException {
		if (payPackage instanceof PayPackageV2) {
			return createPayJsRequestJsonV2((PayPackageV2) payPackage,
					weixinAccount);
		} else if (payPackage instanceof PayPackageV3) {
			return createPayJsRequestJsonV3((PayPackageV3) payPackage,
					weixinAccount);
		}
		throw new PayException("-1", "unknown pay");
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
			WeixinAccount weixinAccount) {
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
			double orderFee, String ip, WeixinAccount weixinAccount) {
		PayPackageV2 payPackage = new PayPackageV2(body, orderNo, orderFee, ip);
		payPackage.setPartner(weixinAccount.getPartnerId());
		return createPayJsRequestJsonV2(payPackage, weixinAccount);
	}

	/**
	 * V2.x版本的PayRequest签名
	 * 
	 * @param jsPayRequestV2
	 *            支付请求
	 * @param paySignKey
	 *            支付API的密钥
	 * @return
	 */
	public static String paysignSha(Object obj, String paySignKey) {
		JSONObject extra = null;
		if (StringUtils.isNotBlank(paySignKey)) {
			extra = new JSONObject();
			extra.put("appKey", paySignKey);
		}
		return DigestUtils.sha1Hex(MapUtil
				.toJoinString(obj, false, true, extra));
	}

	/**
	 * V3.x版本的PayRequest签名
	 * 
	 * @param jsPayRequestV3
	 *            支付请求
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
			WeixinAccount weixinAccount) throws PayException {
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
			WeixinAccount weixinAccount) throws PayException {
		String paySignKey = weixinAccount.getPaySignKey();
		payPackage.setSign(paysignMd5(payPackage, paySignKey));
		PrePay prePay = createPrePay(payPackage);
		PayRequestV3 jsPayRequest = new PayRequestV3(prePay);
		jsPayRequest.setPaySign(paysignMd5(jsPayRequest, paySignKey));
		jsPayRequest.setSignType(SignType.MD5);
		return JSON.toJSONString(jsPayRequest);
	}

	public static PrePay createPrePay(PayPackageV3 payPackage) {
		String payJsRequestXml = XStream.to(payPackage).replaceAll("__", "_");
		HttpClient client = null;
		try {
			client = new DefaultHttpClient();
			HttpPost post = new HttpPost(UNIFIEDORDER);
			post.setEntity(new StringEntity(payJsRequestXml,
					StandardCharsets.UTF_8));
			HttpResponse response = client.execute(post);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				return new PrePay("-1", "网络异常[" + statusLine.getStatusCode()
						+ "," + statusLine.getReasonPhrase() + "]！");
			}
			String returnXml = EntityUtils.toString(response.getEntity(),
					StandardCharsets.UTF_8);
			return XStream.get(returnXml, PrePay.class);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return new PrePay("-1", "request fail");
	}

	/**
	 * <p>
	 * 生成编辑地址请求
	 * </p>
	 * 
	 * err_msg edit_address:ok获取编辑收货地址成功<br/>
	 * edit_address:fail获取编辑收货地址失败<br/>
	 * userName 收货人姓名<br/>
	 * telNumber 收货人电话<br/>
	 * addressPostalCode 邮编<br/>
	 * proviceFirstStageName 国标收货地址第一级地址<br/>
	 * addressCitySecondStageName 国标收货地址第二级地址<br/>
	 * addressCountiesThirdStageName 国标收货地址第三级地址<br/>
	 * addressDetailInfo 详细收货地址信息<br/>
	 * nationalCode 收货地址国家码<br/>
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
		Map<String, String> param = new HashMap<String, String>();
		param.put("appId", appId);
		param.put("url", url);
		param.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		param.put("nonceStr", RandomUtil.generateString(16));
		param.put("accessToken", accessToken);
		String sign = paysignSha(param, null);
		JSONObject obj = new JSONObject();
		obj.put("appId", appId);
		obj.put("scope", "jsapi_address");
		obj.put("signType", "sha1");
		obj.put("addrSign", sign);
		obj.put("timeStamp", param.get("timeStamp"));
		obj.put("nonceStr", param.get("nonceStr"));

		return obj.toJSONString();
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
	public String createNativePayRequestURLV2(WeixinAccount weixinAccount,
			String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getAppId());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("productid", productId);
		String sign = paysignSha(map, weixinAccount.getPaySignKey());
		return String.format(NATIVEURLV2, sign, weixinAccount.getAppId(),
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
	public String createNativePayRequestURLV3(WeixinAccount weixinAccount,
			String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getAppId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("time_stamp", timestamp);
		map.put("nonce_str", noncestr);
		map.put("product_id", productId);
		String sign = paysignMd5(map, weixinAccount.getPaySignKey());
		return String.format(NATIVEURLV3, sign, weixinAccount.getAppId(),
				weixinAccount.getMchId(), productId, timestamp, noncestr);
	}

	public static String createNativePayRequestV2(WeixinAccount weixinAccount,
			PayPackageV2 payPackage) {
		NativePayResponseV2 payRequest = new NativePayResponseV2(weixinAccount,
				payPackage);
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getAppId());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("package", payRequest.getPackageInfo());
		map.put("retcode", payRequest.getRetCode());
		map.put("reterrmsg", payRequest.getRetMsg());
		payRequest.setPaySign(paysignSha(map, weixinAccount.getPaySignKey()));
		return XStream.to(payRequest);
	}

	/**
	 * 测试js支付请求
	 * 
	 * @return
	 */
	private static void createTestPayJsRequestJson() {
		// V2.xAPI支付
		PayPackageV2 payPackage = new PayPackageV2("pay_test", "1220403701",
				"D123456", 0.01, "http://182.92.74.85:8082/pay/notify",
				"192.168.1.1");
		WeixinAccount weixinAccount = new WeixinAccount("wx0d1d598c0c03c999",
				"2270e6c67cf4ff48fe2c6d7cc5a42157",
				"GATFzDwbQdbbci3QEQxX2rUBvwTrsMiZ", "1221966601",
				"6b506ef5fefba3142653a9affd2648d8");
		System.out.println(PayUtil.createPayJsRequestJsonV2(payPackage,
				weixinAccount));
		// V3.xJSAPI支付
		try {
			weixinAccount = new WeixinAccount("wx0d1d598c0c03c999",
					"2270e6c67cf4ff48fe2c6d7cc5a42157",
					"6b506ef5fefba3142653a9affd2648d8", "10020674",
					"oyFLst1bqtuTcxK-ojF8hOGtLQao");
			System.out.println(PayUtil.createPayJsRequestJsonV3(
					"oyFLst1bqtuTcxK-ojF8hOGtLQao", "测试", "T001", 1d,
					"192.0.0.1", "http://182.92.74.85:8082/pay/notify",
					weixinAccount));
		} catch (PayException e) {
			e.printStackTrace();
		}
		// V2.xNative支付
		System.out.println(PayUtil.createNativePayRequestV2(weixinAccount,
				payPackage));
	}

	public static void main(String[] args) {
		createTestPayJsRequestJson();
	}
}
