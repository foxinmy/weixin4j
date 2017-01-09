package com.foxinmy.weixin4j.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.payment.mch.CorpPayment;
import com.foxinmy.weixin4j.payment.mch.CorpPaymentRecord;
import com.foxinmy.weixin4j.payment.mch.CorpPaymentResult;
import com.foxinmy.weixin4j.payment.mch.Redpacket;
import com.foxinmy.weixin4j.payment.mch.RedpacketRecord;
import com.foxinmy.weixin4j.payment.mch.RedpacketSendResult;
import com.foxinmy.weixin4j.payment.mch.SettlementRecord;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 现金API
 *
 * @className CashApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月28日
 * @since JDK 1.6
 * @see <a href=
 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_1">
 *      现金红包</a>
 * @see <a href=
 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_1">
 *      企业付款</a>
 */
public class CashApi extends MchApi {

	public CashApi(WeixinPayAccount weixinAccount) {
		super(weixinAccount);
	}

	/**
	 * 发放红包 企业向微信用户个人发现金红包
	 *
	 * @param redpacket
	 *            红包信息
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.payment.mch.Redpacket
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketSendResult
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5">
	 *      发放现金红包接口</a>
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=16_5">
	 *      发放裂变红包接口</a>
	 * @throws WeixinException
	 */
	public RedpacketSendResult sendRedpack(Redpacket redpacket)
			throws WeixinException {
		String appId = redpacket.getAppId();
		super.declareMerchant(redpacket);
		final JSONObject obj = (JSONObject) JSON.toJSON(redpacket);
		if (StringUtil.isNotBlank(appId)) {
			obj.put("appid", appId);
		}
		obj.put("wxappid", obj.remove("appid"));
		final String redpack_uri = redpacket.getTotalNum() > 1 ? getRequestUri("groupredpack_send_uri")
				: getRequestUri("redpack_send_uri");
		obj.put("sign", weixinSignature.sign(obj));
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = getWeixinSSLExecutor().post(redpack_uri,
				param);
		String text = response.getAsString()
				.replaceFirst("<wxappid>", "<appid>")
				.replaceFirst("</wxappid>", "</appid>");
		return XmlStream.fromXML(text, RedpacketSendResult.class);
	}

	/**
	 * 批量发放红包 企业向微信用户个人发现金红包
	 *
	 * @param redpacket
	 *            多个红包信息
	 * @return 发放结果
	 * @see #sendRedpacks(Redpacket...)
	 * @throws WeixinException
	 */
	public List<Future<RedpacketSendResult>> sendRedpacks(
			Redpacket... redpackets) {
		ExecutorService sendExecutor = Executors.newFixedThreadPool(Math.max(1,
				redpackets.length / 10)); // 十分之一?
		CompletionService<RedpacketSendResult> completion = new ExecutorCompletionService<RedpacketSendResult>(
				sendExecutor);
		List<Future<RedpacketSendResult>> callSendList = new ArrayList<Future<RedpacketSendResult>>(
				redpackets.length);
		for (final Redpacket redpacket : redpackets) {
			Future<RedpacketSendResult> futureSend = completion
					.submit(new Callable<RedpacketSendResult>() {
						@Override
						public RedpacketSendResult call() throws Exception {
							return sendRedpack(redpacket);
						}
					});
			callSendList.add(futureSend);
		}
		// 关闭启动线程,不再接受新的任务
		sendExecutor.shutdown();
		return callSendList;
	}

	/**
	 * 查询红包记录
	 *
	 * @param outTradeNo
	 *            商户发放红包的商户订单号
	 * @return 红包记录
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketRecord
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_7&index=6">
	 *      查询现金红包接口</a>
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=16_6">
	 *      查询裂变红包接口</a>
	 * @throws WeixinException
	 */
	public RedpacketRecord queryRedpack(String outTradeNo)
			throws WeixinException {
		Map<String, String> para = createBaseRequestMap(null);
		para.put("bill_type", "MCHT");
		para.put("mch_billno", outTradeNo);
		para.put("sign", weixinSignature.sign(para));
		String param = XmlStream.map2xml(para);
		WeixinResponse response = getWeixinSSLExecutor().post(
				getRequestUri("redpack_query_uri"), param);
		return response.getAsObject(new TypeReference<RedpacketRecord>() {
		});
	}

	/**
	 * 企业付款 实现企业向个人付款，针对部分有开发能力的商户， 提供通过API完成企业付款的功能。 比如目前的保险行业向客户退保、给付、理赔。
	 * <p>
	 * 接口调用规则：
	 * <p>
	 * <li>给同一个实名用户付款，单笔单日限额2W/2W
	 * <li>给同一个非实名用户付款，单笔单日限额2000/2000
	 * <li>一个商户同一日付款总额限额100W
	 * <li>单笔最小金额默认为1元
	 * <li>每个用户每天最多可付款10次，可以在商户平台--API安全进行设置
	 * <li>给同一个用户付款时间间隔不得低于15秒
	 *
	 * @param payment
	 *            付款信息
	 * @return 付款结果
	 * @see com.foxinmy.weixin4j.payment.mch.CorpPayment
	 * @see com.foxinmy.weixin4j.payment.mch.CorpPaymentResult
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2">
	 *      企业付款接口</a>
	 * @throws WeixinException
	 */
	public CorpPaymentResult sendCorpPayment(CorpPayment payment)
			throws WeixinException {
		super.declareMerchant(payment);
		JSONObject obj = (JSONObject) JSON.toJSON(payment);
		obj.put("mchid", obj.remove("mch_id"));
		obj.put("mch_appid", obj.remove("appid"));
		obj.put("sign", weixinSignature.sign(obj));
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = getWeixinSSLExecutor().post(
				getRequestUri("corppayment_send_uri"), param);
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
	 * @param outTradeNo
	 *            商户调用企业付款API时使用的商户订单号
	 * @return 付款记录
	 * @see com.foxinmy.weixin4j.payment.mch.CorpPaymentRecord
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3">
	 *      企业付款查询接口</a>
	 * @throws WeixinException
	 */
	public CorpPaymentRecord queryCorpPayment(String outTradeNo)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("appid", weixinAccount.getId());
		obj.put("partner_trade_no", outTradeNo);
		obj.put("sign", weixinSignature.sign(obj));
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = getWeixinSSLExecutor().post(
				getRequestUri("corppayment_query_uri"), param);
		return response.getAsObject(new TypeReference<CorpPaymentRecord>() {
		});
	}

	/**
	 * 查询结算资金
	 *
	 * @param status
	 *            是否结算
	 * @param pageable
	 *            分页数据
	 * @param start
	 *            开始日期 查询未结算记录时，该字段可不传
	 * @param end
	 *            结束日期 查询未结算记录时，该字段可不传
	 * @return 结算金额记录
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.payment.mch.SettlementRecord
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/external/micropay.php?chapter=9_14&index=7">
	 *      查询结算资金接口</a>
	 */
	public SettlementRecord querySettlement(boolean status, Pageable pageable,
			Date start, Date end) throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("nonce_str", RandomUtil.generateString(16));
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("appid", weixinAccount.getId());
		obj.put("usetag", status ? 1 : 2);
		obj.put("offset", pageable.getOffset());
		obj.put("limit", pageable.getPageSize());
		if (start != null) {
			obj.put("date_start", DateUtil.fortmat2yyyyMMdd(start));
		}
		if (end != null) {
			obj.put("date_end", DateUtil.fortmat2yyyyMMdd(end));
		}
		obj.put("sign", weixinSignature.sign(obj));
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = weixinExecutor.post(
				getRequestUri("settlement_query_uri"), param);
		return response.getAsObject(new TypeReference<SettlementRecord>() {
		});
	}

	/**
	 * 查询汇率
	 *
	 * @param currencyType
	 *            外币币种
	 * @param date
	 *            日期 不填则默认当天
	 * @return 汇率 例如美元兑换人民币的比例为6.5
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/external/micropay.php?chapter=9_15&index=8">
	 *      查询汇率接口</a>
	 */
	public double queryExchageRate(CurrencyType currencyType, Date date)
			throws WeixinException {
		if (date == null) {
			date = new Date();
		}
		JSONObject obj = new JSONObject();
		obj.put("mch_id", weixinAccount.getMchId());
		obj.put("appid", weixinAccount.getId());
		obj.put("sub_mch_id", weixinAccount.getSubMchId());
		obj.put("fee_type", currencyType.name());
		obj.put("date", DateUtil.fortmat2yyyyMMdd(date));
		obj.put("sign", weixinSignature.sign(obj));
		String param = XmlStream.map2xml(obj);
		WeixinResponse response = weixinExecutor.post(
				getRequestUri("exchagerate_query_uri"), param);
		BigDecimal rate = new BigDecimal(XmlStream.xml2map(
				response.getAsString()).get("rate"));
		return rate.divide(new BigDecimal(100000000d)).doubleValue();
	}
}
