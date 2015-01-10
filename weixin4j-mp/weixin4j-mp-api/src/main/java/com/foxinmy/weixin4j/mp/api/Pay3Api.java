package com.foxinmy.weixin4j.mp.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.http.SSLHttpRequest;
import com.foxinmy.weixin4j.http.XmlResult;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.mp.payment.RefundConverter;
import com.foxinmy.weixin4j.mp.payment.v3.ApiResult;
import com.foxinmy.weixin4j.mp.payment.v3.Order;
import com.foxinmy.weixin4j.mp.payment.v3.RefundRecord;
import com.foxinmy.weixin4j.mp.payment.v3.RefundResult;
import com.foxinmy.weixin4j.mp.type.BillType;
import com.foxinmy.weixin4j.mp.type.IdQuery;
import com.foxinmy.weixin4j.mp.type.IdType;
import com.foxinmy.weixin4j.mp.util.ExcelUtil;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * V3支付API
 * 
 * @className PayApi
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class Pay3Api extends PayApi {

	public Pay3Api(WeixinMpAccount weixinAccount) {
		super(weixinAccount);
	}

	/**
	 * 订单查询
	 * 
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 订单信息
	 * @see com.foxinmy.weixin4j.mp.payment.v3.Order
	 * @since V3
	 * @throws WeixinException
	 */
	public Order orderQuery(IdQuery idQuery) throws WeixinException {
		Map<String, String> map = baseMap(idQuery);
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String orderquery_uri = getRequestUri("orderquery_v3_uri");
		Response response = request.post(orderquery_uri, param);
		return response.getAsObject(new TypeReference<Order>() {
		});
	}

	/**
	 * 申请退款(请求需要双向证书)</br>
	 * <p style="color:red">
	 * 交易时间超过 1 年的订单无法提交退款; </br> 支持部分退款,部分退需要设置相同的订单号和不同的 out_refund_no。一笔退款失
	 * 败后重新提交,要采用原来的 out_refund_no。总退款金额不能超过用户实际支付金额。</br>
	 * </p>
	 * 
	 * @param caFile
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
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundResult
	 * @since V3
	 * @throws WeixinException
	 */
	protected RefundResult refund(File caFile, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, Map<String, String> mopara) throws WeixinException {
		String refund_uri = getRequestUri("refund_v3_uri");
		Response response = null;
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);

			Map<String, String> map = baseMap(idQuery);
			map.put("out_refund_no", outRefundNo);
			map.put("total_fee", DateUtil.formaFee2Fen(totalFee));
			map.put("refund_fee", DateUtil.formaFee2Fen(refundFee));
			if (StringUtils.isBlank(opUserId)) {
				opUserId = weixinAccount.getMchId();
			}
			map.put("op_user_id", opUserId);
			if (mopara != null && !mopara.isEmpty()) {
				map.putAll(mopara);
			}
			String sign = PayUtil
					.paysignMd5(map, weixinAccount.getPaySignKey());
			map.put("sign", sign);
			String param = map2xml(map);
			SSLHttpRequest request = new SSLHttpRequest(
					weixinAccount.getMchId(), ca);
			response = request.post(refund_uri, param);
		} catch (WeixinException e) {
			throw e;
		} catch (Exception e) {
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
		return response.getAsObject(new TypeReference<RefundResult>() {
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
	 * @see {@link com.foxinmy.weixin4j.mp.api.Pay3Api#refund(File, IdQuery, String, double, double, String, Map)}
	 */
	public RefundResult refund(File caFile, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId) throws WeixinException {
		return refund(caFile, idQuery, outRefundNo, totalFee, refundFee,
				opUserId, null);
	}

	/**
	 * 冲正订单(需要证书)</br> 当支付返回失败,或收银系统超时需要取消交易,可以调用该接口</br> 接口逻辑:支
	 * 付失败的关单,支付成功的撤销支付</br> <font color="red">7天以内的单可撤销,其他正常支付的单
	 * 如需实现相同功能请调用退款接口</font></br> <font
	 * color="red">调用扣款接口后请勿立即调用撤销,需要等待5秒以上。先调用查单接口,如果没有确切的返回,再调用撤销</font></br>
	 * 
	 * @param caFile
	 *            证书文件(V3版本后缀为*.p12)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 撤销结果
	 * @since V3
	 * @throws WeixinException
	 */
	public ApiResult reverse(File caFile, IdQuery idQuery)
			throws WeixinException {
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);
			SSLHttpRequest request = new SSLHttpRequest(
					weixinAccount.getMchId(), ca);
			String reverse_uri = getRequestUri("reverse_uri");
			Map<String, String> map = baseMap(idQuery);
			String sign = PayUtil
					.paysignMd5(map, weixinAccount.getPaySignKey());
			map.put("sign", sign);
			String param = map2xml(map);
			Response response = request.post(reverse_uri, param);
			return response.getAsObject(new TypeReference<ApiResult>() {
			});
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
	}

	/**
	 * native支付URL转短链接
	 * 
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @throws WeixinException
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
		String param = map2xml(map);
		String shorturl_uri = getRequestUri("p_shorturl_uri");
		Response response = request.post(shorturl_uri, param);
		map = xml2map(response.getAsString());
		return map.get("short_url");
	}

	/**
	 * 关闭订单</br> 当订单支付失败,调用关单接口后用新订单号重新发起支付,如果关单失败,返回已完
	 * 成支付请按正常支付处理。如果出现银行掉单,调用关单成功后,微信后台会主动发起退款。
	 * 
	 * @param outTradeNo
	 *            商户系统内部的订单号
	 * @return 处理结果
	 * @since V3
	 * @throws WeixinException
	 */
	public ApiResult closeOrder(String outTradeNo) throws WeixinException {
		Map<String, String> map = baseMap(new IdQuery(outTradeNo,
				IdType.TRADENO));
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String closeorder_uri = getRequestUri("closeorder_uri");
		Response response = request.post(closeorder_uri, param);
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
		String bill_path = ConfigUtil.getValue("bill_path");
		String fileName = String.format("%s_%s_%s.xls", formatBillDate,
				billType.name().toLowerCase(), weixinAccount.getId());
		File file = new File(String.format("%s/%s", bill_path, fileName));
		if (file.exists()) {
			return file;
		}
		String downloadbill_uri = getRequestUri("downloadbill_v3_uri");
		Map<String, String> map = baseMap(null);
		map.put("bill_date", formatBillDate);
		map.put("bill_type", billType.name());
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		Response response = request.post(downloadbill_uri, param);

		BufferedReader reader = null;
		OutputStream os = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					response.getStream(), Consts.UTF_8));
			String line = null;
			List<String[]> bills = new LinkedList<String[]>();
			while ((line = reader.readLine()) != null) {
				bills.add(line.replaceAll("`", "").split(","));
			}

			List<String> headers = Arrays.asList(bills.remove(0));
			List<String> totalDatas = Arrays
					.asList(bills.remove(bills.size() - 1));
			List<String> totalHeaders = Arrays
					.asList(bills.remove(bills.size() - 1));
			HSSFWorkbook wb = new HSSFWorkbook();
			wb.createSheet(formatBillDate + "对账单");
			ExcelUtil.list2excel(wb, headers, bills);
			ExcelUtil.list2excel(wb, totalHeaders, totalDatas);
			os = new FileOutputStream(file);
			wb.write(os);
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (os != null) {
					os.close();
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
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundRecord
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundDetail
	 * @since V3
	 * @throws WeixinException
	 */
	public RefundRecord refundQuery(IdQuery idQuery) throws WeixinException {
		String refundquery_uri = getRequestUri("refundquery_v3_uri");
		Map<String, String> map = baseMap(idQuery);
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		Response response = request.post(refundquery_uri, param);
		return RefundConverter.fromXML(response.getAsString(),
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
	 */
	@SuppressWarnings("unchecked")
	public XmlResult interfaceReport(String interfaceUrl, int executeTime,
			String outTradeNo, String ip, Date time, XmlResult returnXml)
			throws WeixinException {
		String pay_report_uri = getRequestUri("pay_report_uri");
		Map<String, String> map = baseMap(null);
		map.put("interface_url", interfaceUrl);
		map.put("execute_time_", Integer.toString(executeTime));
		map.put("out_trade_no", outTradeNo);
		map.put("user_ip", ip);
		map.put("time", DateUtil.fortmat2yyyyMMddHHmmss(time));
		map.putAll((Map<String, String>) JSON.toJSON(returnXml));
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		Response response = request.post(pay_report_uri, param);
		return response.getAsXmlResult();
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
		if (StringUtils.isNotBlank(weixinAccount.getDeviceInfo())) {
			map.put("device_info", weixinAccount.getDeviceInfo());
		}
		if (idQuery != null) {
			map.put(idQuery.getType().getName(), idQuery.getId());
		}
		return map;
	}
}
