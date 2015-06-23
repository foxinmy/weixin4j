package com.foxinmy.weixin4j.mp.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.SSLHttpClinet;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.mp.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.mp.payment.v3.MPPayment;
import com.foxinmy.weixin4j.mp.payment.v3.MPPaymentRecord;
import com.foxinmy.weixin4j.mp.payment.v3.MPPaymentResult;
import com.foxinmy.weixin4j.mp.payment.v3.Redpacket;
import com.foxinmy.weixin4j.mp.payment.v3.RedpacketRecord;
import com.foxinmy.weixin4j.mp.payment.v3.RedpacketSendResult;
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
public class CashApi extends MpApi {

	private final WeixinMpAccount weixinAccount;

	public CashApi(WeixinMpAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	/**
	 * 发放红包 企业向微信用户个人发现金红包
	 * 
	 * @param caFile
	 *            证书文件(V3版本后缀为*.p12)
	 * @param redpacket
	 *            红包信息
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.mp.payment.v3.Redpacket
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RedpacketSendResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5">发放红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketSendResult sendRedpack(File caFile, Redpacket redpacket)
			throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(redpacket);
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("sub_mch_id", weixinAccount.getSubMchId());
		obj.put("wxappid", weixinAccount.getId());
		String sign = PayUtil.paysignMd5(obj, weixinAccount.getPaySignKey());
		obj.put("sign", sign);
		String param = XmlStream.map2xml(obj);
		String redpack_send_uri = getRequestUri("redpack_send_uri");
		WeixinResponse response = null;
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);
			SSLHttpClinet request = new SSLHttpClinet(weixinAccount.getMchId(),
					ca);
			response = request.post(redpack_send_uri, param);
		} catch (WeixinException e) {
			throw e;
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
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
	 * @param caFile
	 *            证书文件(V3版本后缀为*.p12)
	 * @param outTradeNo
	 *            商户发放红包的商户订单号
	 * @return 红包记录
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RedpacketRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_6">查询红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketRecord queryRedpack(File caFile, String outTradeNo)
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
		String redpack_query_uri = getRequestUri("redpack_query_uri");
		WeixinResponse response = null;
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);
			SSLHttpClinet request = new SSLHttpClinet(weixinAccount.getMchId(),
					ca);
			response = request.post(redpack_query_uri, param);
		} catch (WeixinException e) {
			throw e;
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
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
	 * @param caFile
	 *            证书文件(V3版本后缀为*.p12)
	 * @param mpPayment
	 *            付款信息
	 * @return 付款结果
	 * @see com.foxinmy.weixin4j.mp.payment.v3.MPPayment
	 * @see com.foxinmy.weixin4j.mp.payment.v3.MPPaymentResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_1">企业付款</a>
	 * @throws WeixinException
	 */
	public MPPaymentResult mpPayment(File caFile, MPPayment mpPayment)
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
		String mp_payment_uri = getRequestUri("mp_payment_uri");
		WeixinResponse response = null;
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);
			SSLHttpClinet request = new SSLHttpClinet(weixinAccount.getMchId(),
					ca);
			response = request.post(mp_payment_uri, param);
		} catch (WeixinException e) {
			throw e;
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
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
	 * @param caFile
	 *            证书文件(V3版本后缀为*.p12)
	 * @param outTradeNo
	 *            商户调用企业付款API时使用的商户订单号
	 * @return 付款记录
	 * @see com.foxinmy.weixin4j.mp.payment.v3.MPPaymentRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_3">企业付款查询</a>
	 * @throws WeixinException
	 */
	public MPPaymentRecord mpPaymentQuery(File caFile, String outTradeNo)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("appid", weixinAccount.getId());
		obj.put("partner_trade_no", outTradeNo);
		String sign = PayUtil.paysignMd5(obj, weixinAccount.getPaySignKey());
		obj.put("sign", sign);
		String param = XmlStream.map2xml(obj);
		String mp_payquery_uri = getRequestUri("mp_payquery_uri");
		WeixinResponse response = null;
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);
			SSLHttpClinet request = new SSLHttpClinet(weixinAccount.getMchId(),
					ca);
			response = request.post(mp_payquery_uri, param);
		} catch (WeixinException e) {
			throw e;
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
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
