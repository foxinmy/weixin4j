package com.foxinmy.weixin4j.mp.action;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.http.XmlResult;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.JsPayNotify;
import com.foxinmy.weixin4j.mp.payment.PayPackage;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.mp.payment.v2.NativePayNotifyV2;
import com.foxinmy.weixin4j.mp.payment.v2.NativePayResponseV2;
import com.foxinmy.weixin4j.mp.payment.v2.PayFeedback;
import com.foxinmy.weixin4j.mp.payment.v2.PayPackageV2;
import com.foxinmy.weixin4j.mp.payment.v2.PayWarn;
import com.foxinmy.weixin4j.mp.payment.v3.NativePayNotifyV3;
import com.foxinmy.weixin4j.mp.payment.v3.NativePayResponseV3;
import com.foxinmy.weixin4j.mp.payment.v3.PayPackageV3;
import com.foxinmy.weixin4j.mp.type.TradeType;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 支付示例
 * 
 * @className PayAction
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class PayAction {

	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * JSAPI支付
	 * 
	 * @return
	 */
	public JSONObject jsPay() {
		JSONObject obj = new JSONObject();
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		// V3 支付
		PayPackage payPackage = new PayPackageV3(weixinAccount, "用户openid",
				"商品描述", "系统内部订单号", 1d, "IP地址", TradeType.JSAPI);
		// V2 支付
		payPackage = new PayPackageV2("商品描述", weixinAccount.getPartnerId(),
				"系统内部订单号", 1d, "回调地址", "IP地址");
		payPackage.setAttach("ID");
		String jspay = null;
		try {
			jspay = PayUtil.createPayJsRequestJson(payPackage, weixinAccount);
		} catch (PayException e) {
			log.error("create jspay error,{}", weixinAccount, e);
		}
		if (StringUtils.isBlank(jspay)) {
			obj.put("code", "-2");
			obj.put("msg", "创建支付链接失败！");
			return obj;
		}
		obj.put("code", "0");
		obj.put("jspay", jspay);

		/*
		 * 编辑收货地址 SnsToken token = (SnsToken) getSession("AccessToken");
		 * obj.put("editaddress", PayUtil.createAddressRequestJson(
		 * wx.getAppId(), getFullLoction(), token.getAccess_token()));
		 */
		log.info("js pay....{}", obj);
		return obj;
	}

	/**
	 * JSAPI(V2)支付成功(前端)时的回调通知<br>
	 * &ltxml&gt</br> &ltOpenId&gt&lt![CDATA[111222]]&gt&lt/OpenId&gt</br>
	 * &ltAppId&gt&lt![CDATA[wwwwb4f85f3a797777]]&gt&lt/AppId&gt</br>
	 * &ltIsSubscribe&gt1&lt/IsSubscribe&gt</br>
	 * &ltTimeStamp&gt1369743511&lt/TimeStamp&gt</br>
	 * &ltNonceStr&gt&lt![CDATA[jALldRTHAFd5Tgs5]]&gt&lt/NonceStr&gt</br>
	 * &ltAppSignature>&lt![CDATA[bafe07f060f22dcda0bfdb4b5ff756f973aecffa]]&gt
	 * &lt/AppSignature&gt</br>
	 * &ltSignMethod>&lt![CDATA[sha1]]&gt&lt/SignMethod&gt</br> &lt/xml&gt</br>
	 * 参与签名的字段为: appid、appkey、timestamp、noncestr、openid、issubscribe
	 * 
	 * @param 订单信息
	 * @param inputStream
	 *            用户信息
	 * 
	 * @see com.foxinmy.weixin4j.mp.payment.JsPayNotify
	 * @return success或其他
	 */
	public String jsNotifyV2(InputStream inputStream) {
		Map<String, String> objMap = new HashMap<String, String>();
		/*
		 * 收集url中携带的参数 /pay/notify/back?attach=8&bank_billno=201410293351060&
		 * bank_type=2032&discount=0&fee_type=1&input_charset=UTF-8&
		 * notify_id=9fKbVf_qg6y-
		 * wSjtSMV0PLXeEn2oGfTM1s9dWSvR2B9U6iFQRTzmjrMWKUxvh9mpBLvnh8aqFbC_OFk1pTvFnFUO00Lln4fh
		 * & out_trade_no=D14102900031&partner=1221928801&product_fee=1&
		 * sign=B9D6E772C271C9B86B8436FC9F5DFC1A&
		 * sign_type=MD5&time_end=20141029183707&
		 * total_fee=1&trade_mode=1&trade_state=0&
		 * transaction_id=1221928801201410296039230054&transport_fee=0
		 */
		log.info("jspay_notify_orderinfo,{}", objMap);
		JsPayNotify payNotify = XmlStream.get(inputStream, JsPayNotify.class);
		log.info("jspay_notify_userinfo,{}", payNotify);
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		// 验证财付通签名
		String sign = objMap.get("sign");
		objMap.remove("sign");
		String _sign = PayUtil
				.paysignMd5(objMap, weixinAccount.getPartnerKey());
		log.info("财付通签名----->sign={},vaild_sign={}", sign, _sign);
		if (!sign.equals(_sign)) {
			return "fail";
		}
		objMap.clear();
		// 验证微信签名
		sign = payNotify.getPaySign();
		payNotify.setPaySign(null);
		payNotify.setSignType(null);
		String vaild_sign = PayUtil.paysignSha(payNotify,
				weixinAccount.getPaySignKey());
		log.info("微信签名----->sign={},vaild_sign={}", sign, vaild_sign);
		if (!sign.equals(vaild_sign)) {
			return "fail";
		}
		// 处理业务逻辑
		return "success";
	}

	/**
	 * JSAPI(V3)支付成功(前端)时的回调通知
	 * 
	 * 
	 * @param inputStream
	 *            订单回调
	 * @return &ltxml&gt<br>
	 *         &ltreturn_code&gtSUCCESS/FAIL&lt/return_code&gt<br>
	 *         &ltreturn_msg&gt如非空,为错误 原因签名失败参数格式校验错误&lt/return_msg&gt<br>
	 *         &lt/xml&gt
	 */
	public String jsNotifyV3(InputStream inputStream) {
		com.foxinmy.weixin4j.mp.payment.v3.Order order = XmlStream.get(
				inputStream, com.foxinmy.weixin4j.mp.payment.v3.Order.class);
		log.info("jaapi_notify_order_info:", order);
		String sign = order.getSign();
		order.setSign(null);
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		String valid_sign = PayUtil.paysignMd5(order,
				weixinAccount.getPaySignKey());
		log.info("微信签名----->sign={},vaild_sign={}", sign, valid_sign);
		if (!sign.equals(valid_sign)) {
			return XmlStream.to(new XmlResult(Consts.FAIL, "签名错误"));
		}
		return XmlStream.to(new XmlResult(Consts.SUCCESS, ""));
	}

	/**
	 * V2.x版本Native支付时POST数据<br>
	 * &ltxml&gt</br> &ltOpenId&gt&lt![CDATA[111222]]&gt&lt/OpenId&gt</br>
	 * &ltAppId&gt&lt![CDATA[wwwwb4f85f3a797777]]&gt&lt/AppId&gt</br>
	 * &ltIsSubscribe&gt1&lt/IsSubscribe&gt</br>
	 * &ltProductId&gt[CDATA[000000]]&lt/ProductId&gt</br>
	 * &ltTimeStamp&gt1369743511&lt/TimeStamp&gt</br>
	 * &ltNonceStr&gt&lt![CDATA[jALldRTHAFd5Tgs5]]&gt&lt/NonceStr&gt</br>
	 * &ltAppSignature>&lt![CDATA[bafe07f060f22dcda0bfdb4b5ff756f973aecffa]]&gt
	 * &lt/AppSignature&gt</br>
	 * &ltSignMethod>&lt![CDATA[sha1]]&gt&lt/SignMethod&gt</br> &lt/xml&gt</br>
	 * 参与签名的字段为: appid、appkey、timestamp、noncestr、openid、issubscribe、productId
	 * 
	 * @param inputStream
	 * 
	 * @return 必须返回一个带有Package信息的xml字符串
	 */
	public String nativeNotifyV2(InputStream inputStream) {
		// V2.x版本
		NativePayNotifyV2 payNotify = XmlStream.get(inputStream,
				NativePayNotifyV2.class);
		log.info("native_pay_notify,{}", payNotify);
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		String sign = payNotify.getPaySign();
		payNotify.setPaySign(null);
		payNotify.setSignType(null);
		// 验证微信签名
		String vaild_sign = PayUtil.paysignSha(payNotify,
				weixinAccount.getPaySignKey());
		log.info("微信签名----->sign={},vaild_sign={}", sign, vaild_sign);
		if (!sign.equals(vaild_sign)) {
			return "fail";
		}
		// 构造订单信息
		PayPackageV2 payPackage = new PayPackageV2("商品描述",
				weixinAccount.getPartnerId(), "系统内部订单号", 1d, "回调地址", "IP地址");
		NativePayResponseV2 payResponse = new NativePayResponseV2(
				weixinAccount, payPackage);
		return XmlStream.to(payResponse);
	}

	/**
	 * V3.x版本native回调<br>
	 * &ltxml&gt</br> &ltopenid&gt&lt![CDATA[111222]]&gt&lt/openid&gt</br>
	 * &ltappid&gt&lt![CDATA[wwwwb4f85f3a797777]]&gt&lt/appid&gt</br>
	 * &ltmch_id&gt&lt![CDATA[1100022]]&gt&lt/mch_id&gt</br>
	 * &ltis_subscribe&gt1&lt/is_subscribe&gt</br>
	 * &ltproduct_id&gt[CDATA[000000]]&lt/product_id&gt</br>
	 * &ltnonce_str&gt&lt![CDATA[jALldRTHAFd5Tgs5]]&gt&lt/nonce_str&gt</br>
	 * &ltsign>&lt![CDATA[bafe07f060f22dcda0bfdb4b5ff756f973aecffa]]&gt
	 * &lt/sign&gt</br> &lt/xml&gt</br>
	 * 
	 * @return
	 * @throws PayException
	 */
	public String nativeNotifyV3(InputStream inputStream) throws PayException {
		NativePayNotifyV3 payNotify = XmlStream.get(inputStream,
				NativePayNotifyV3.class);
		String sign = payNotify.getSign();
		payNotify.setSign(null);
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		String valid_sign = PayUtil.paysignMd5(payNotify,
				weixinAccount.getPaySignKey());
		log.info("微信签名----->sign={},vaild_sign={}", sign, valid_sign);
		// 生成Package
		PayPackageV3 payPackage = new PayPackageV3(weixinAccount, "用户openid",
				"商品描述", "系统内部订单号", 1d, "IP地址", TradeType.NATIVE);
		payPackage.setProduct_id(payNotify.getProductId());
		if (!sign.equals(valid_sign)) {
			// 校验失败
			NativePayResponseV3 payReponse = new NativePayResponseV3("签名失败",
					null);
			payReponse.setSign(PayUtil.paysignMd5(payReponse,
					weixinAccount.getPaySignKey()));
			return XmlStream.to(payReponse);

		}
		// 成功返回
		NativePayResponseV3 payReponse = new NativePayResponseV3(payPackage,
				weixinAccount.getPaySignKey());
		payReponse.setSign(PayUtil.paysignMd5(payReponse,
				weixinAccount.getPaySignKey()));
		return XmlStream.to(payReponse);
	}

	/**
	 * 告警通知 需要成功返回 success </br> &ltxml&gt</br>
	 * &ltAppId&gt&lt![CDATA[wxf8b4f85f3a794e77]]&gt&lt/AppId&gt</br>
	 * &ltErrorType&gt1001&lt/ErrorType&gt</br>
	 * &ltDescription&gt&lt![CDATA[错误描述]]>&lt/Description&gt</br>
	 * &ltAlarmContent&gt&lt![CDATA[错误详情]]>&lt/AlarmContent&gt</br>
	 * &ltTimeStamp&gt1393860740&lt/TimeStamp&gt</br>
	 * &ltAppSignature&gt&lt![CDATA[签名方式跟JsPayRequest中的paySign一样]]&gt&lt/
	 * AppSignature&gt</br>
	 * &ltSignMethod&gt&lt![CDATA[sha1]]&gt&lt/SignMethod&gt</br>
	 * &lt/xml&gt</br>
	 * 参与签名字段:alarmcontent、appid、appkey、description、errortype、timestamp
	 * 
	 * @param inputStream
	 *            xml数据
	 * @see com.foxinmy.weixin4j.mp.payment.v2.PayWarn
	 * @return
	 */
	public String warning(InputStream inputStream) {
		PayWarn payWarn = XmlStream.get(inputStream, PayWarn.class);
		log.info("pay_warning,{}", payWarn);
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		String sign = payWarn.getPaySign();
		payWarn.setPaySign(null);
		payWarn.setSignType(null);
		// 验证微信签名
		String vaild_sign = PayUtil.paysignSha(payWarn,
				weixinAccount.getPaySignKey());
		log.info("微信签名----->sign={},vaild_sign={}", sign, vaild_sign);
		return "success";
	}

	/**
	 * 用户维权
	 * 
	 * @param inputStream
	 * @see com.foxinmy.weixin4j.mp.payment.v2.PayFeedback
	 * @return
	 */
	public String feedback(InputStream inputStream) {
		PayFeedback feedback = XmlStream.get(inputStream, PayFeedback.class);
		log.info("pay_feedback_info:{}", feedback);
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		// 验证微信签名
		Map<String, String> obj = new HashMap<String, String>();
		obj.put("openid", feedback.getOpenId());
		obj.put("appid", feedback.getAppId());
		obj.put("timestamp", feedback.getTimeStamp());
		obj.put("appkey", weixinAccount.getPaySignKey());
		String sign = PayUtil.paysignSha(obj);
		log.info("微信签名----->sign={},vaild_sign={}", sign, feedback.getPaySign());
		return "success";
	}
}
