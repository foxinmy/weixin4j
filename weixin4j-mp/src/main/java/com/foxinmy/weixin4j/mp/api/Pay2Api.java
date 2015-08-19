package com.foxinmy.weixin4j.mp.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.http.weixin.WeixinSSLRequestExecutor;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.mp.payment.v2.OrderV2;
import com.foxinmy.weixin4j.mp.payment.v2.PayUtil2;
import com.foxinmy.weixin4j.mp.payment.v2.RefundRecordV2;
import com.foxinmy.weixin4j.mp.payment.v2.RefundResultV2;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.payment.PayUtil;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.TokenStorager;
import com.foxinmy.weixin4j.type.BillType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.RefundType;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;

/**
 * V2支付API
 * 
 * @className Pay2Api
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class Pay2Api extends MpApi {

	private final WeixinPayAccount weixinAccount;
	private final TokenHolder tokenHolder;

	public Pay2Api() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinPayAccount.class));
	}

	public Pay2Api(WeixinPayAccount weixinAccount) {
		this(weixinAccount, DEFAULT_TOKEN_STORAGER);
	}

	public Pay2Api(WeixinPayAccount weixinAccount, TokenStorager tokenStorager) {
		this.weixinAccount = weixinAccount;
		this.tokenHolder = new TokenHolder(new WeixinTokenCreator(
				weixinAccount.getId(), weixinAccount.getSecret()),
				tokenStorager);
	}

	/**
	 * 订单查询
	 * 
	 * @param idQuery
	 *            订单号
	 * @return 订单信息
	 * @see com.foxinmy.weixin4j.mp.payment.v2.OrderV2
	 * @since V2
	 * @throws WeixinException
	 */
	public OrderV2 orderQuery(IdQuery idQuery) throws WeixinException {
		String orderquery_uri = getRequestUri("orderquery_v2_uri");
		Token token = tokenHolder.getToken();
		StringBuilder sb = new StringBuilder();
		sb.append(idQuery.getType().getName()).append("=")
				.append(idQuery.getId());
		sb.append("&partner=").append(weixinAccount.getPartnerId());
		String part = sb.toString();
		sb.append("&key=").append(weixinAccount.getPartnerKey());
		String sign = DigestUtil.MD5(sb.toString()).toUpperCase();
		sb.delete(0, sb.length());
		sb.append(part).append("&sign=").append(sign);

		String timestamp = DateUtil.timestamp2string();
		JSONObject obj = new JSONObject();
		obj.put("appid", weixinAccount.getId());
		obj.put("appkey", weixinAccount.getPaySignKey());
		obj.put("package", sb.toString());
		obj.put("timestamp", timestamp);
		String signature = PayUtil2.paysignSha(obj);

		obj.clear();
		obj.put("appid", weixinAccount.getId());
		obj.put("package", sb.toString());
		obj.put("timestamp", timestamp);
		obj.put("app_signature", signature);
		obj.put("sign_method", SignType.SHA1.name().toLowerCase());

		WeixinResponse response = weixinExecutor.post(
				String.format(orderquery_uri, token.getAccessToken()),
				obj.toJSONString());

		String order_info = response.getAsJson().getString("order_info");
		OrderV2 order = JSON.parseObject(order_info, OrderV2.class,
				Feature.IgnoreNotMatch);
		if (order.getRetCode() != 0) {
			throw new WeixinException(Integer.toString(order.getRetCode()),
					order.getRetMsg());
		}
		return order;
	}

	/**
	 * 申请退款(需要证书)</br>
	 * <p style="color:red">
	 * 交易时间超过 1 年的订单无法提交退款; </br> 支持部分退款,部分退需要设置相同的订单号和不同的 out_refund_no。一笔退款失
	 * 败后重新提交,要采用原来的 out_refund_no。总退款金额不能超过用户实际支付金额。</br>
	 * </p>
	 * 
	 * @param caFile
	 *            证书文件(V2版本后缀为*.pfx)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @param outRefundNo
	 *            商户系统内部的退款单号,商 户系统内部唯一,同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额,单位为元
	 * @param refundFee
	 *            退款总金额,单位为元,可以做部分退款
	 * @param opUserId
	 *            操作员帐号, 默认为商户号
	 * @param mopara
	 *            如 opUserPasswd
	 * 
	 * @return 退款申请结果
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundResultV2
	 * @since V2
	 * @throws WeixinException
	 */
	protected RefundResultV2 refundApply(File caFile, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, Map<String, String> mopara) throws WeixinException {
		String refund_uri = getRequestUri("refundapply_v2_uri");
		WeixinResponse response = null;
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);
			Map<String, String> map = new HashMap<String, String>();
			map.put("input_charset", Consts.UTF_8.name());
			// 版本号
			// 填写为 1.0 时,操作员密码为明文
			// 填写为 1.1 时,操作员密码为 MD5(密码)值
			map.put("service_version", "1.1");
			map.put("partner", weixinAccount.getPartnerId());
			map.put("out_refund_no", outRefundNo);
			map.put("total_fee", DateUtil.formaFee2Fen(totalFee));
			map.put("refund_fee", DateUtil.formaFee2Fen(refundFee));
			map.put(idQuery.getType().getName(), idQuery.getId());
			if (StringUtil.isBlank(opUserId)) {
				opUserId = weixinAccount.getPartnerId();
			}
			map.put("op_user_id", opUserId);
			if (mopara != null && !mopara.isEmpty()) {
				map.putAll(mopara);
			}
			String sign = PayUtil
					.paysignMd5(map, weixinAccount.getPartnerKey());
			map.put("sign", sign.toUpperCase());

			SSLContext ctx = null;
			KeyStore ks = null;
			String jksPwd = "";
			File jksFile = new File(String.format("%s/tenpay_cacert.jks",
					caFile.getParent()));
			// create jks ca
			if (!jksFile.exists()) {
				CertificateFactory cf = CertificateFactory
						.getInstance(com.foxinmy.weixin4j.model.Consts.X509);
				java.security.cert.Certificate cert = cf
						.generateCertificate(Pay2Api.class
								.getResourceAsStream("cacert.pem"));
				ks = KeyStore
						.getInstance(com.foxinmy.weixin4j.model.Consts.JKS);
				ks.load(null, null);
				ks.setCertificateEntry("tenpay", cert);
				FileOutputStream os = new FileOutputStream(jksFile);
				ks.store(os, jksPwd.toCharArray());
				os.close();
			}
			// load jks ca
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(com.foxinmy.weixin4j.model.Consts.SunX509);
			ks = KeyStore.getInstance(com.foxinmy.weixin4j.model.Consts.JKS);
			FileInputStream is = new FileInputStream(jksFile);
			ks.load(is, jksPwd.toCharArray());
			tmf.init(ks);
			is.close();
			// load pfx ca
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(com.foxinmy.weixin4j.model.Consts.SunX509);
			ks = KeyStore.getInstance(com.foxinmy.weixin4j.model.Consts.PKCS12);
			ks.load(ca, weixinAccount.getPartnerId().toCharArray());
			kmf.init(ks, weixinAccount.getPartnerId().toCharArray());

			ctx = SSLContext.getInstance(com.foxinmy.weixin4j.model.Consts.TLS);
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
					new SecureRandom());

			WeixinRequestExecutor weixinExecutor = new WeixinSSLRequestExecutor(
					ctx);
			response = weixinExecutor.get(refund_uri, map);
		} catch (WeixinException e) {
			throw e;
		} catch (Exception e) {
			throw new WeixinException(e);
		} finally {
			if (ca != null) {
				try {
					ca.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return response.getAsObject(new TypeReference<RefundResultV2>() {
		});
	}

	/**
	 * 退款申请
	 * 
	 * @param caFile
	 *            证书文件(V2版本后缀为*.pfx)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @param outRefundNo
	 *            商户系统内部的退款单号,商 户系统内部唯一,同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额,单位为元
	 * @param refundFee
	 *            退款总金额,单位为元,可以做部分退款
	 * @param opUserId
	 *            操作员帐号, 默认为商户号
	 * @param opUserPasswd
	 *            操作员密码,默认为商户后台登录密码
	 * @see {@link #refundApply(File, IdQuery, String, double, double, String, Map)}
	 */
	public RefundResultV2 refundApply(File caFile, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, String opUserPasswd) throws WeixinException {
		Map<String, String> mopara = new HashMap<String, String>();
		mopara.put("op_user_passwd", DigestUtil.MD5(opUserPasswd));
		return refundApply(caFile, idQuery, outRefundNo, totalFee, refundFee,
				opUserId, mopara);
	}

	/**
	 * 退款申请
	 * 
	 * @param caFile
	 *            证书文件(V2版本后缀为*.pfx)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @param outRefundNo
	 *            商户系统内部的退款单号,商 户系统内部唯一,同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额,单位为元
	 * @param refundFee
	 *            退款总金额,单位为元,可以做部分退款
	 * @param opUserId
	 *            操作员帐号, 默认为商户号
	 * @param opUserPasswd
	 *            操作员密码,默认为商户后台登录密码
	 * @param recvUserId
	 *            转账退款接收退款的财付通帐号。 一般无需填写,只有退银行失败,资金转入商 户号现金账号时(即状态为转入代发,查询返 回的
	 *            refund_status 是 7 或 11),填写原退款 单号并填写此字段,资金才会退到指定财付通
	 *            账号。其他情况此字段忽略
	 * @param reccvUserName
	 *            转账退款接收退款的姓名(需与接收退款的财 付通帐号绑定的姓名一致)
	 * @param refundType
	 *            为空或者填 1:商户号余额退款;2:现金帐号 退款;3:优先商户号退款,若商户号余额不足, 再做现金帐号退款。使用 2 或
	 *            3 时,需联系财 付通开通此功能
	 * @see {@link #refundApply(File, IdQuery, String, double, double, String, Map)}
	 * @return 退款结果
	 */
	public RefundResultV2 refundApply(File caFile, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, String opUserPasswd, String recvUserId,
			String reccvUserName, RefundType refundType) throws WeixinException {
		Map<String, String> mopara = new HashMap<String, String>();
		mopara.put("op_user_passwd", DigestUtil.MD5(opUserPasswd));
		if (StringUtil.isNotBlank(recvUserId)) {
			mopara.put("recv_user_id", recvUserId);
		}
		if (StringUtil.isNotBlank(reccvUserName)) {
			mopara.put("reccv_user_name", reccvUserName);
		}
		if (refundType != null) {
			mopara.put("refund_type", Integer.toString(refundType.getVal()));
		}
		return refundApply(caFile, idQuery, outRefundNo, totalFee, refundFee,
				opUserId, mopara);
	}

	/**
	 * 下载对账单<br>
	 * 1.微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账 单中,跟原支付单订单号一致,bill_type 为
	 * REVOKED;<br>
	 * 2.微信在次日 9 点启动生成前一天的对账单,建议商户 9 点半后再获取;<br>
	 * 3.对账单中涉及金额的字段单位为“元”。<br>
	 * 
	 * @param billDate
	 *            下载对账单的日期 为空则取前一天
	 * @param billType
	 *            下载对账单的类型 ALL,返回当日所有订单信息, 默认值 SUCCESS,返回当日成功支付的订单
	 *            REFUND,返回当日退款订单
	 * @return excel表格
	 * @since V2
	 * @throws WeixinException
	 */
	public File downloadbill(Date billDate, BillType billType)
			throws WeixinException {
		if (billDate == null) {
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, -1);
			billDate = now.getTime();
		}
		if (billType == null) {
			billType = BillType.ALL;
		}
		String formatBillDate = DateUtil.fortmat2yyyyMMdd(billDate);
		String bill_path = Weixin4jConfigUtil.getValue("bill_path");
		String fileName = String.format("%s_%s_%s.txt", formatBillDate,
				billType.name().toLowerCase(), weixinAccount.getId());
		File file = new File(String.format("%s/%s", bill_path, fileName));
		if (file.exists()) {
			return file;
		}
		String downloadbill_uri = getRequestUri("downloadbill_v2_uri");

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("spid", weixinAccount.getPartnerId());
		map.put("trans_time", DateUtil.fortmat2yyyy_MM_dd(billDate));
		map.put("stamp", DateUtil.timestamp2string());
		map.put("cft_signtype", "0");
		map.put("mchtype", Integer.toString(billType.getVal()));
		map.put("key", weixinAccount.getPartnerKey());
		String sign = DigestUtil.MD5(MapUtil.toJoinString(map, false, false));
		map.put("sign", sign.toLowerCase());
		WeixinResponse response = weixinExecutor.get(downloadbill_uri, map);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), Consts.GBK));
			reader = new BufferedReader(new InputStreamReader(
					response.getBody(), com.foxinmy.weixin4j.model.Consts.GBK));
			String line = null;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			throw new WeixinException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException ignore) {
				;
			}
		}
		return file;
	}

	/**
	 * 退款查询</br> 退款有一定延时,用零钱支付的退款20分钟内到账,银行卡支付的退款 3 个工作日后重新查询退款状态
	 * 
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundRecordV2
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundDetailV2
	 * @since V2
	 * @throws WeixinException
	 */
	public RefundRecordV2 refundQuery(IdQuery idQuery) throws WeixinException {
		String refundquery_uri = getRequestUri("refundquery_v2_uri");
		Map<String, String> map = new HashMap<String, String>();
		map.put("input_charset", Consts.UTF_8.name());
		map.put("partner", weixinAccount.getPartnerId());
		map.put(idQuery.getType().getName(), idQuery.getId());
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPartnerKey());
		map.put("sign", sign.toLowerCase());
		WeixinResponse response = weixinExecutor.get(refundquery_uri, map);
		return ListsuffixResultDeserializer.deserialize(response.getAsString(),
				RefundRecordV2.class);
	}

	/**
	 * 发货通知
	 * 
	 * @param openId
	 *            用户ID
	 * @param transid
	 *            交易单号
	 * @param outTradeNo
	 *            订单号
	 * @param status
	 *            成功|失败
	 * @param statusMsg
	 *            status为失败时携带的信息
	 * @return 发货处理结果
	 * @throws WeixinException
	 */
	public JsonResult deliverNotify(String openId, String transid,
			String outTradeNo, boolean status, String statusMsg)
			throws WeixinException {
		String delivernotify_uri = getRequestUri("delivernotify_uri");
		Token token = tokenHolder.getToken();

		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getId());
		map.put("appkey", weixinAccount.getPaySignKey());
		map.put("openid", openId);
		map.put("transid", transid);
		map.put("out_trade_no", outTradeNo);
		map.put("deliver_timestamp", DateUtil.timestamp2string());
		map.put("deliver_status", status ? "1" : "0");
		map.put("deliver_msg", statusMsg);
		map.put("app_signature", PayUtil2.paysignSha(map));
		map.put("sign_method", SignType.SHA1.name().toLowerCase());

		WeixinResponse response = weixinExecutor.post(
				String.format(delivernotify_uri, token.getAccessToken()),
				JSON.toJSONString(map));
		return response.getAsJsonResult();
	}

	/**
	 * 维权处理
	 * 
	 * @param openId
	 *            用户ID
	 * @param feedbackId
	 *            维权单号
	 * @return 维权处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateFeedback(String openId, String feedbackId)
			throws WeixinException {
		String payfeedback_update_uri = getRequestUri("payfeedback_update_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				payfeedback_update_uri, token.getAccessToken(), openId,
				feedbackId));
		return response.getAsJsonResult();
	}
}
