package com.foxinmy.weixin4j.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.mch.CorpPayment;
import com.foxinmy.weixin4j.payment.mch.CorpPaymentRecord;
import com.foxinmy.weixin4j.payment.mch.CorpPaymentResult;
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
 * @since JDK 1.6
 * @see <a
 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_1">现金红包</a>
 * @see <a
 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_1">企业付款</a>
 */
public class CashApi extends MchApi {

	public CashApi(WeixinPayAccount weixinAccount) {
		super(weixinAccount);
	}

	/**
	 * 发放红包 企业向微信用户个人发现金红包
	 * 
	 * @param certificate
	 *            后缀为*.p12的证书文件
	 * @param redpacket
	 *            红包信息
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.payment.mch.Redpacket
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketSendResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5">发放红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketSendResult sendRedpack(InputStream certificate,
			Redpacket redpacket) throws WeixinException {
		redpacket.setSign(weixinSignature.sign(redpacket));
		String param = XmlStream.map2xml((JSONObject) JSON.toJSON(redpacket));
		WeixinResponse response = null;
		try {
			response = createSSLRequestExecutor(certificate)
					.post(redpacket.getTotalNum() > 1 ? getRequestUri("groupredpack_send_uri")
							: getRequestUri("redpack_send_uri"), param);
		} finally {
			if (certificate != null) {
				try {
					certificate.close();
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
	 * @param certificate
	 *            后缀为*.p12的证书文件
	 * @param outTradeNo
	 *            商户发放红包的商户订单号
	 * @return 红包记录
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_6">查询红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketRecord queryRedpack(InputStream certificate,
			String outTradeNo) throws WeixinException {
		Map<String, String> para = createBaseRequestMap(null);
		para.put("bill_type", "MCHT");
		para.put("mch_billno", outTradeNo);
		para.put("sign", weixinSignature.sign(para));
		String param = XmlStream.map2xml(para);
		WeixinResponse response = null;
		try {
			response = createSSLRequestExecutor(certificate).post(
					getRequestUri("redpack_query_uri"), param);
		} finally {
			if (certificate != null) {
				try {
					certificate.close();
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
	 * @param certificate
	 *            后缀为*.p12的证书文件
	 * @param mpPayment
	 *            付款信息
	 * @return 付款结果
	 * @see com.foxinmy.weixin4j.payment.mch.CorpPayment
	 * @see com.foxinmy.weixin4j.payment.mch.CorpPaymentResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_1">企业付款</a>
	 * @throws WeixinException
	 */
	public CorpPaymentResult sendCorpPayment(InputStream certificate,
			CorpPayment payment) throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(payment);
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mchid", weixinAccount.getMchId());
		obj.put("sub_mch_id", weixinAccount.getSubMchId());
		obj.put("mch_appid", weixinAccount.getId());
		obj.put("device_info", weixinAccount.getDeviceInfo());
		obj.put("sign", weixinSignature.sign(obj));
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = null;
		try {
			response = createSSLRequestExecutor(certificate).post(
					getRequestUri("corppayment_send_uri"), param);
		} finally {
			if (certificate != null) {
				try {
					certificate.close();
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
		return XmlStream.fromXML(text, CorpPaymentResult.class);
	}

	/**
	 * 企业付款查询 用于商户的企业付款操作进行结果查询，返回付款操作详细结果
	 * 
	 * @param certificate
	 *            后缀为*.p12的证书文件
	 * @param outTradeNo
	 *            商户调用企业付款API时使用的商户订单号
	 * @return 付款记录
	 * @see com.foxinmy.weixin4j.payment.mch.CorpPaymentRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_3">企业付款查询</a>
	 * @throws WeixinException
	 */
	public CorpPaymentRecord queryCorpPayment(InputStream certificate,
			String outTradeNo) throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("appid", weixinAccount.getId());
		obj.put("partner_trade_no", outTradeNo);
		obj.put("sign", weixinSignature.sign(obj));
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = null;
		try {
			response = createSSLRequestExecutor(certificate).post(
					getRequestUri("corppayment_query_uri"), param);
		} finally {
			if (certificate != null) {
				try {
					certificate.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return response.getAsObject(new TypeReference<CorpPaymentRecord>() {
		});
	}
}
