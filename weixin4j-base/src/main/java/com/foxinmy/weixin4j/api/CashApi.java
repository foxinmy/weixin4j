package com.foxinmy.weixin4j.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.http.weixin.WeixinSSLRequestExecutor;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayURLConsts;
import com.foxinmy.weixin4j.payment.PayUtil;
import com.foxinmy.weixin4j.payment.mch.MPPayment;
import com.foxinmy.weixin4j.payment.mch.MPPaymentRecord;
import com.foxinmy.weixin4j.payment.mch.MPPaymentResult;
import com.foxinmy.weixin4j.payment.mch.Redpacket;
import com.foxinmy.weixin4j.payment.mch.RedpacketRecord;
import com.foxinmy.weixin4j.payment.mch.RedpacketSendResult;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 现金API
 * 
 * @className CashApi
 * @author jy
 * @date 2015年3月28日
 * @since JDK 1.7
 * @see <a
 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_1">现金红包</a>
 * @see <a
 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_1">企业付款</a>
 */
public class CashApi {

	private final WeixinPayAccount weixinAccount;

	public CashApi(WeixinPayAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	/**
	 * 发放红包 企业向微信用户个人发现金红包
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param redpacket
	 *            红包信息
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.payment.mch.Redpacket
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketSendResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5">发放红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketSendResult sendRedpack(InputStream ca, Redpacket redpacket)
			throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(redpacket);
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("sub_mch_id", weixinAccount.getSubMchId());
		obj.put("wxappid", weixinAccount.getId());
		String sign = PayUtil.paysignMd5(obj, weixinAccount.getPaySignKey());
		obj.put("sign", sign);
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = null;
		try {
			WeixinRequestExecutor weixinExecutor = new WeixinSSLRequestExecutor(
					weixinAccount.getMchId(), ca);
			response = weixinExecutor.post(PayURLConsts.MCH_REDPACKSEND_URL,
					param);
		} finally {
			if (ca != null) {
				try {
					ca.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return response.getAsObject(new TypeReference<RedpacketSendResult>() {
		});
	}

	/**
	 * 查询红包记录
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param outTradeNo
	 *            商户发放红包的商户订单号
	 * @return 红包记录
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_6">查询红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketRecord queryRedpack(InputStream ca, String outTradeNo)
			throws WeixinException {
		Map<String, String> para = new HashMap<String, String>();
		para.put("nonce_str", RandomUtil.generateString(16));
		para.put("mch_id", weixinAccount.getMchId());
		para.put("bill_type", "MCHT");
		para.put("appid", weixinAccount.getId());
		para.put("mch_billno", outTradeNo);
		String sign = PayUtil.paysignMd5(para, weixinAccount.getPaySignKey());
		para.put("sign", sign);
		String param = XmlStream.map2xml(para);
		WeixinResponse response = null;
		try {
			WeixinRequestExecutor weixinExecutor = new WeixinSSLRequestExecutor(
					weixinAccount.getMchId(), ca);
			response = weixinExecutor.post(PayURLConsts.MCH_REDPACKQUERY_URL,
					param);
		} finally {
			if (ca != null) {
				try {
					ca.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return response.getAsObject(new TypeReference<RedpacketRecord>() {
		});
	}

	/**
	 * 企业付款 实现企业向个人付款，针对部分有开发能力的商户， 提供通过API完成企业付款的功能。 比如目前的保险行业向客户退保、给付、理赔。
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param mpPayment
	 *            付款信息
	 * @return 付款结果
	 * @see com.foxinmy.weixin4j.payment.mch.MPPayment
	 * @see com.foxinmy.weixin4j.payment.mch.MPPaymentResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_1">企业付款</a>
	 * @throws WeixinException
	 */
	public MPPaymentResult mchPayment(InputStream ca, MPPayment mpPayment)
			throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(mpPayment);
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mchid", weixinAccount.getMchId());
		obj.put("sub_mch_id", weixinAccount.getSubMchId());
		obj.put("mch_appid", weixinAccount.getId());
		obj.put("device_info", weixinAccount.getDeviceInfo());
		String sign = PayUtil.paysignMd5(obj, weixinAccount.getPaySignKey());
		obj.put("sign", sign);
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = null;
		try {
			WeixinRequestExecutor weixinExecutor = new WeixinSSLRequestExecutor(
					weixinAccount.getMchId(), ca);
			response = weixinExecutor.post(PayURLConsts.MCH_ENPAYMENT_URL,
					param);
		} finally {
			if (ca != null) {
				try {
					ca.close();
				} catch (IOException e) {
					;
				}
			}
		}
		String text = response.getAsString()
				.replaceFirst("<mch_appid>", "<appid>")
				.replaceFirst("</mch_appid>", "</appid>")
				.replaceFirst("<mchid>", "<mch_id>")
				.replaceFirst("</mchid>", "</mch_id>");
		return XmlStream.fromXML(text, MPPaymentResult.class);
	}

	/**
	 * 企业付款查询 用于商户的企业付款操作进行结果查询，返回付款操作详细结果
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param outTradeNo
	 *            商户调用企业付款API时使用的商户订单号
	 * @return 付款记录
	 * @see com.foxinmy.weixin4j.payment.mch.MPPaymentRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_3">企业付款查询</a>
	 * @throws WeixinException
	 */
	public MPPaymentRecord mchPaymentQuery(InputStream ca, String outTradeNo)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("appid", weixinAccount.getId());
		obj.put("partner_trade_no", outTradeNo);
		String sign = PayUtil.paysignMd5(obj, weixinAccount.getPaySignKey());
		obj.put("sign", sign);
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = null;
		try {
			WeixinRequestExecutor weixinExecutor = new WeixinSSLRequestExecutor(
					weixinAccount.getMchId(), ca);
			response = weixinExecutor.post(PayURLConsts.MCH_ENPAYQUERY_URL,
					param);
		} finally {
			if (ca != null) {
				try {
					ca.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return response.getAsObject(new TypeReference<MPPaymentRecord>() {
		});
	}
}
