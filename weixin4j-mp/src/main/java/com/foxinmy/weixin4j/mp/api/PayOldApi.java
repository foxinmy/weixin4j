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
import com.foxinmy.weixin4j.mp.oldpayment.OrderV2;
import com.foxinmy.weixin4j.mp.oldpayment.PayPackageV2;
import com.foxinmy.weixin4j.mp.oldpayment.RefundRecordV2;
import com.foxinmy.weixin4j.mp.oldpayment.RefundResultV2;
import com.foxinmy.weixin4j.mp.oldpayment.WeixinOldPayAccount;
import com.foxinmy.weixin4j.mp.oldpayment.WeixinOldPaymentSignature;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.setting.Weixin4jSettings;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.sign.WeixinSignature;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.type.BillType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.RefundType;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;

/**
 * V2老支付API
 *
 * @className PayOldApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月28日
 * @since JDK 1.6
 * @see
 */
public class PayOldApi extends MpApi {

	private final Weixin4jSettings<WeixinOldPayAccount> settings;
	private final WeixinOldPayAccount weixinAccount;
	private final TokenManager tokenManager;
	private final WeixinSignature weixinMD5Signature;
	private final WeixinOldPaymentSignature weixinOldSignature;

	/**
	 * 默认使用weixin4j.properties配置信息
	 */
	public PayOldApi() {
		this(new Weixin4jSettings<WeixinOldPayAccount>(JSON.parseObject(
				Weixin4jConfigUtil.getValue("account"),
				WeixinOldPayAccount.class)));
	}

	/**
	 *
	 * @param settings
	 *            微信配置信息
	 */
	public PayOldApi(Weixin4jSettings<WeixinOldPayAccount> settings) {
		this.settings = settings;
		this.weixinAccount = settings.getAccount();
		this.tokenManager = new TokenManager(new WeixinTokenCreator(
				weixinAccount.getId(), weixinAccount.getSecret()),
				settings.getCacheStorager0());
		this.weixinMD5Signature = new WeixinPaymentSignature(
				weixinAccount.getPartnerKey());
		this.weixinOldSignature = new WeixinOldPaymentSignature(
				weixinAccount.getPaySignKey(), weixinAccount.getPartnerKey());
	}

	public WeixinOldPayAccount getWeixinPayAccount() {
		return this.weixinAccount;
	}

	public WeixinOldPaymentSignature getWeixinPaymentSignature() {
		return this.weixinOldSignature;
	}

	/**
	 * 生成V2.x版本JSAPI支付字符串
	 *
	 * @param body
	 *            支付详情
	 * @param outTradeNo
	 *            订单号
	 * @param totalFee
	 *            订单总额(元)
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            订单生成的机器 IP
	 * @return 支付json串
	 */
	public String createPayJsRequestJson(String body, String outTradeNo,
			double totalFee, String notifyUrl, String createIp) {
		PayPackageV2 payPackage = new PayPackageV2(
				weixinAccount.getPartnerId(), body, outTradeNo, totalFee,
				notifyUrl, createIp);
		return createPayJsRequestJson(payPackage);
	}

	/**
	 * 生成V2.x版本JSAPI支付字符串
	 *
	 * @param payPackage
	 *            支付信息
	 * @return 支付json串
	 */
	public String createPayJsRequestJson(PayPackageV2 payPackage) {
		PayRequest payRequest = new PayRequest(weixinAccount.getId(),
				weixinOldSignature.sign(payPackage));
		payRequest.setPaySign(weixinOldSignature.sign(payRequest));
		payRequest.setSignType(SignType.SHA1);
		return JSON.toJSONString(payRequest);
	}

	/**
	 * 创建V2.x NativePay支付链接
	 *
	 * @param productId
	 *            与订单ID等价
	 * @return 支付链接
	 */
	public String createNativePayRequestURL(String productId) {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(16);
		map.put("appid", weixinAccount.getId());
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("productid", productId);
		map.put("appkey", weixinAccount.getPaySignKey());
		String sign = weixinOldSignature.sign(map);
		String nativepay_uri = getRequestUri("nativepay_old_uri");
		return String.format(nativepay_uri, sign, weixinAccount.getId(),
				productId, timestamp, noncestr);
	}

	/**
	 * 订单查询
	 *
	 * @param idQuery
	 *            订单号
	 * @return 订单信息
	 * @see com.foxinmy.weixin4j.mp.oldpayment.OrderV2
	 * @since V2
	 * @throws WeixinException
	 */
	public OrderV2 queryOrder(IdQuery idQuery) throws WeixinException {
		String orderquery_uri = getRequestUri("orderquery_old_uri");
		Token token = tokenManager.getCache();
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
		String signature = weixinOldSignature.sign(obj);

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
	 * @param certificate
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
	 * @see com.foxinmy.weixin4j.mp.oldpayment.RefundResultV2
	 * @since V2
	 * @throws WeixinException
	 */
	protected RefundResultV2 applyRefund(InputStream certificate,
			IdQuery idQuery, String outRefundNo, double totalFee,
			double refundFee, String opUserId, Map<String, String> mopara)
			throws WeixinException {
		String refund_uri = getRequestUri("refundapply_old_uri");
		WeixinResponse response = null;
		try {
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
			String sign = weixinMD5Signature.sign(map);
			map.put("sign", sign.toUpperCase());

			SSLContext ctx = null;
			KeyStore ks = null;
			String jksPwd = "";
			File jksFile = new File(String.format("%s%stenpay_cacert.jks",
					settings.getTmpdir0(), File.separator));
			// create jks ca
			if (!jksFile.exists()) {
				CertificateFactory cf = CertificateFactory
						.getInstance(Consts.X509);
				java.security.cert.Certificate cert = cf
						.generateCertificate(PayOldApi.class
								.getResourceAsStream("cacert.pem"));
				ks = KeyStore.getInstance(Consts.JKS);
				ks.load(null, null);
				ks.setCertificateEntry("tenpay", cert);
				FileOutputStream os = new FileOutputStream(jksFile);
				ks.store(os, jksPwd.toCharArray());
				os.close();
			}
			// load jks ca
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(Consts.SunX509);
			ks = KeyStore.getInstance(Consts.JKS);
			FileInputStream is = new FileInputStream(jksFile);
			ks.load(is, jksPwd.toCharArray());
			tmf.init(ks);
			is.close();
			// load pfx ca
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(Consts.SunX509);
			ks = KeyStore.getInstance(Consts.PKCS12);
			ks.load(certificate, weixinAccount.getPartnerId().toCharArray());
			kmf.init(ks, weixinAccount.getPartnerId().toCharArray());

			ctx = SSLContext.getInstance(Consts.TLS);
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
			if (certificate != null) {
				try {
					certificate.close();
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
	 * @param certificate
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
	 * @see {@link #applyRefund(InputStream, IdQuery, String, double, double, String, Map)}
	 */
	public RefundResultV2 applyRefund(InputStream certificate, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, String opUserPasswd) throws WeixinException {
		Map<String, String> mopara = new HashMap<String, String>();
		mopara.put("op_user_passwd", DigestUtil.MD5(opUserPasswd));
		return applyRefund(certificate, idQuery, outRefundNo, totalFee,
				refundFee, opUserId, mopara);
	}

	/**
	 * 退款申请
	 *
	 * @param certificate
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
	 * @see {@link #applyRefund(InputStream, IdQuery, String, double, double, String, Map)}
	 * @return 退款结果
	 */
	public RefundResultV2 applyRefund(InputStream certificate, IdQuery idQuery,
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
		return applyRefund(certificate, idQuery, outRefundNo, totalFee,
				refundFee, opUserId, mopara);
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
	 * @param billPath
	 *            对账单保存路径
	 * @return excel表格
	 * @since V2
	 * @throws WeixinException
	 */
	public File downloadBill(Date billDate, BillType billType, String billPath)
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
		String fileName = String.format("weixin4j_bill_%s_%s_%s.txt",
				formatBillDate, billType.name().toLowerCase(),
				weixinAccount.getId());
		File file = new File(String.format("%s%s%s", billPath, File.separator,
				fileName));
		if (file.exists()) {
			return file;
		}
		String downloadbill_uri = getRequestUri("downloadbill_old_uri");

		Map<String, String> map = new HashMap<String, String>();
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
	 * @see com.foxinmy.weixin4j.mp.oldpayment.RefundRecordV2
	 * @see com.foxinmy.weixin4j.mp.oldpayment.RefundDetailV2
	 * @since V2
	 * @throws WeixinException
	 */
	public RefundRecordV2 queryRefund(IdQuery idQuery) throws WeixinException {
		String refundquery_uri = getRequestUri("refundquery_old_uri");
		Map<String, String> map = new HashMap<String, String>();
		map.put("input_charset", Consts.UTF_8.name());
		map.put("partner", weixinAccount.getPartnerId());
		map.put(idQuery.getType().getName(), idQuery.getId());
		String sign = weixinMD5Signature.sign(map);
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
		String delivernotify_uri = getRequestUri("delivernotify_old_uri");
		Token token = tokenManager.getCache();

		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getId());
		map.put("appkey", weixinAccount.getPaySignKey());
		map.put("openid", openId);
		map.put("transid", transid);
		map.put("out_trade_no", outTradeNo);
		map.put("deliver_timestamp", DateUtil.timestamp2string());
		map.put("deliver_status", status ? "1" : "0");
		map.put("deliver_msg", statusMsg);
		map.put("app_signature", weixinOldSignature.sign(map));
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
		String payfeedback_uri = getRequestUri("payfeedback_old_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				payfeedback_uri, token.getAccessToken(), openId, feedbackId));
		return response.getAsJsonResult();
	}
}
