package com.foxinmy.weixin4j.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.http.weixin.WeixinSSLRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayURLConsts;
import com.foxinmy.weixin4j.payment.PayUtil;
import com.foxinmy.weixin4j.payment.mch.ApiResult;
import com.foxinmy.weixin4j.payment.mch.AuthCodeOpenIdResult;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.payment.mch.RefundRecord;
import com.foxinmy.weixin4j.payment.mch.RefundResult;
import com.foxinmy.weixin4j.type.BillType;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * (商户平台版)支付API
 * 
 * @className Pay3Api
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/index.html">商户平台API</a>
 */
public class Pay3Api {

	private final WeixinRequestExecutor weixinExecutor;

	private final WeixinPayAccount weixinAccount;

	public Pay3Api(WeixinPayAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
		this.weixinExecutor = new WeixinRequestExecutor();
	}

	/**
	 * 订单查询
	 * <p>
	 * 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；</br> 调用支付接口后，返回系统错误或未知交易状态情况；</br>
	 * 调用被扫支付API，返回USERPAYING的状态；</br> 调用关单或撤销接口API之前，需确认支付状态；
	 * </P>
	 * 
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 订单信息
	 * @see com.foxinmy.weixin4j.payment.mch.Order
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2">订单查询API</a>
	 * @since V3
	 * @throws WeixinException
	 */
	public Order orderQuery(IdQuery idQuery) throws WeixinException {
		Map<String, String> map = baseMap(idQuery);
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				PayURLConsts.MCH_ORDERQUERY_URL, param);
		return ListsuffixResultDeserializer.deserialize(response.getAsString(),
				Order.class);
	}

	/**
	 * 申请退款(请求需要双向证书)
	 * <p>
	 * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，微信支付将在收到退款请求并且验证成功之后，
	 * 按照退款规则将支付款按原路退到买家帐号上。
	 * </p>
	 * <p style="color:red">
	 * 1.交易时间超过半年的订单无法提交退款；
	 * 2.微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。一笔退款失败后重新提交
	 * ，要采用原来的退款单号。总退款金额不能超过用户实际支付金额。
	 * </p>
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
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
	 * 
	 * @return 退款申请结果
	 * @see com.foxinmy.weixin4j.payment.mch.RefundResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4">申请退款API</a>
	 * @since V3
	 * @throws WeixinException
	 */
	protected RefundResult refundApply(InputStream ca, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, Map<String, String> mopara) throws WeixinException {
		WeixinResponse response = null;
		try {
			Map<String, String> map = baseMap(idQuery);
			map.put("out_refund_no", outRefundNo);
			map.put("total_fee", DateUtil.formaFee2Fen(totalFee));
			map.put("refund_fee", DateUtil.formaFee2Fen(refundFee));
			if (StringUtil.isBlank(opUserId)) {
				opUserId = weixinAccount.getMchId();
			}
			map.put("op_user_id", opUserId);
			if (mopara != null && !mopara.isEmpty()) {
				map.putAll(mopara);
			}
			String sign = PayUtil
					.paysignMd5(map, weixinAccount.getPaySignKey());
			map.put("sign", sign);
			String param = XmlStream.map2xml(map);
			WeixinRequestExecutor weixinExecutor = new WeixinSSLRequestExecutor(
					weixinAccount.getMchId(), ca);
			response = weixinExecutor.post(PayURLConsts.MCH_REFUNDAPPLY_URL,
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
		return response.getAsObject(new TypeReference<RefundResult>() {
		});
	}

	/**
	 * 退款申请
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @param outRefundNo
	 *            商户系统内部的退款单号,商 户系统内部唯一,同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额,单位为元
	 * @param refundFee
	 *            退款总金额,单位为元,可以做部分退款
	 * @param refundFeeType
	 *            货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 * @param opUserId
	 *            操作员帐号, 默认为商户号
	 * @see {@link #refundApply(InputStream, IdQuery, String, double, double, String, Map)}
	 */
	public RefundResult refundApply(InputStream ca, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			CurrencyType refundFeeType, String opUserId) throws WeixinException {
		Map<String, String> mopara = new HashMap<String, String>();
		if (refundFeeType == null) {
			refundFeeType = CurrencyType.CNY;
		}
		mopara.put("refund_fee_type", refundFeeType.name());
		return refundApply(ca, idQuery, outRefundNo, totalFee, refundFee,
				opUserId, mopara);
	}

	/**
	 * 冲正订单(需要证书)</br> 当支付返回失败,或收银系统超时需要取消交易,可以调用该接口</br> 接口逻辑:支
	 * 付失败的关单,支付成功的撤销支付</br> <font color="red">7天以内的单可撤销,其他正常支付的单
	 * 如需实现相同功能请调用退款接口</font></br> <font
	 * color="red">调用扣款接口后请勿立即调用撤销,需要等待5秒以上。先调用查单接口,如果没有确切的返回,再调用撤销</font></br>
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 撤销结果
	 * @since V3
	 * @throws WeixinException
	 */
	public ApiResult reverseOrder(InputStream ca, IdQuery idQuery)
			throws WeixinException {
		try {
			WeixinRequestExecutor weixinExecutor = new WeixinSSLRequestExecutor(
					weixinAccount.getMchId(), ca);
			Map<String, String> map = baseMap(idQuery);
			String sign = PayUtil
					.paysignMd5(map, weixinAccount.getPaySignKey());
			map.put("sign", sign);
			String param = XmlStream.map2xml(map);
			WeixinResponse response = weixinExecutor.post(
					PayURLConsts.MCH_ORDERREVERSE_URL, param);
			return response.getAsObject(new TypeReference<ApiResult>() {
			});
		} finally {
			if (ca != null) {
				try {
					ca.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}

	/**
	 * native支付URL转短链接：用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)，减小二维码数据量
	 * ，提升扫描速度和精确度。
	 * 
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @throws WeixinException
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_9">转换短链接API</a>
	 */
	public String getShorturl(String url) throws WeixinException {
		Map<String, String> map = baseMap(null);
		try {
			map.put("long_url", URLEncoder.encode(url, Consts.UTF_8.name()));
		} catch (UnsupportedEncodingException ignore) {
			;
		}
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				PayURLConsts.MCH_SHORTURL_URL, param);
		map = XmlStream.xml2map(response.getAsString());
		return map.get("short_url");
	}

	/**
	 * 关闭订单
	 * <p>
	 * 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续
	 * ，请调用关单接口,如果关单失败,返回已完 成支付请按正常支付处理。如果出现银行掉单,调用关单成功后,微信后台会主动发起退款。
	 * </p>
	 * 
	 * @param outTradeNo
	 *            商户系统内部的订单号
	 * @return 处理结果
	 * @since V3
	 * @throws WeixinException
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3">关闭订单API</a>
	 */
	public ApiResult closeOrder(String outTradeNo) throws WeixinException {
		Map<String, String> map = baseMap(new IdQuery(outTradeNo,
				IdType.TRADENO));
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				PayURLConsts.MCH_CLOSEORDER_URL, param);
		return response.getAsObject(new TypeReference<ApiResult>() {
		});
	}

	/**
	 * 下载对账单<br>
	 * 1.微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账 单中,跟原支付单订单号一致,bill_type 为
	 * REVOKED;<br>
	 * 2.微信在次日 9 点启动生成前一天的对账单,建议商户 9 点半后再获取;<br>
	 * 3.对账单中涉及金额的字段单位为“元”。<br>
	 * 
	 * @param billDate
	 *            下载对账单的日期
	 * @param billType
	 *            下载对账单的类型 ALL,返回当日所有订单信息, 默认值 SUCCESS,返回当日成功支付的订单
	 *            REFUND,返回当日退款订单
	 * @return excel表格
	 * @since V3
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单API</a>
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
		Map<String, String> map = baseMap(null);
		map.put("bill_date", formatBillDate);
		map.put("bill_type", billType.name());
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				PayURLConsts.MCH_DOWNLOADBILL_URL, param);

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
	 * 退款查询
	 * 
	 * <p>
	 * 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
	 * </p>
	 * 
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @see com.foxinmy.weixin4j.payment.mch.RefundRecord
	 * @see com.foxinmy.weixin4j.payment.mch.RefundDetail
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5">退款查询API</a>
	 * @since V3
	 * @throws WeixinException
	 */
	public RefundRecord refundQuery(IdQuery idQuery) throws WeixinException {
		Map<String, String> map = baseMap(idQuery);
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				PayURLConsts.MCH_REFUNDQUERY_URL, param);
		return ListsuffixResultDeserializer.deserialize(response.getAsString(),
				RefundRecord.class);
	}

	/**
	 * 接口上报
	 * 
	 * @param interfaceUrl
	 *            上报对应的接口的完整 URL, 类似: https://api.mch.weixin.q
	 *            q.com/pay/unifiedorder
	 * @param executeTime
	 *            接口耗时情况,单位为毫秒
	 * @param outTradeNo
	 *            商户系统内部的订单号,商 户可以在上报时提供相关商户订单号方便微信支付更好 的提高服务质量。
	 * @param ip
	 *            发起接口调用时的机器 IP
	 * @param time
	 *            ￼商户调用该接口时商户自己 系统的时间
	 * @param returnXml
	 *            调用接口返回的基本数据
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_8">接口测试上报API</a>
	 */
	@SuppressWarnings("unchecked")
	public XmlResult interfaceReport(String interfaceUrl, int executeTime,
			String outTradeNo, String ip, Date time, XmlResult returnXml)
			throws WeixinException {
		Map<String, String> map = baseMap(null);
		map.put("interface_url", interfaceUrl);
		map.put("execute_time_", Integer.toString(executeTime));
		map.put("out_trade_no", outTradeNo);
		map.put("user_ip", ip);
		map.put("time", DateUtil.fortmat2yyyyMMddHHmmss(time));
		map.putAll((Map<String, String>) JSON.toJSON(returnXml));
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				PayURLConsts.MCH_PAYREPORT_URL, param);
		return response.getAsXmlResult();
	}

	/**
	 * 授权码查询OPENID接口
	 * 
	 * @param authCode
	 *            扫码支付授权码，设备读取用户微信中的条码或者二维码信息
	 * @return 查询结果
	 * @see com.foxinmy.weixin4j.payment.mch.AuthCodeOpenIdResult
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_13&index=9">授权码查询OPENID</a>
	 * @throws WeixinException
	 */
	public AuthCodeOpenIdResult authCode2openId(String authCode)
			throws WeixinException {
		Map<String, String> map = baseMap(null);
		map.put("auth_code", authCode);
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				PayURLConsts.MCH_AUTHCODE_OPENID_URL, param);
		return response.getAsObject(new TypeReference<AuthCodeOpenIdResult>() {
		});
	}

	/**
	 * V3接口请求基本数据
	 * 
	 * @return
	 */
	private Map<String, String> baseMap(IdQuery idQuery) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("nonce_str", RandomUtil.generateString(16));
		if (StringUtil.isNotBlank(weixinAccount.getDeviceInfo())) {
			map.put("device_info", weixinAccount.getDeviceInfo());
		}
		if (idQuery != null) {
			map.put(idQuery.getType().getName(), idQuery.getId());
		}
		return map;
	}
}
