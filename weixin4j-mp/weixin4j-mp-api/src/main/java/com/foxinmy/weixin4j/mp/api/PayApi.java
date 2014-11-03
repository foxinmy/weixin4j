package com.foxinmy.weixin4j.mp.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.http.XmlResult;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.payment.BillType;
import com.foxinmy.weixin4j.mp.payment.IdQuery;
import com.foxinmy.weixin4j.mp.payment.IdType;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.mp.payment.RefundConverter;
import com.foxinmy.weixin4j.mp.payment.v2.Order;
import com.foxinmy.weixin4j.mp.payment.v3.Refund;
import com.foxinmy.weixin4j.mp.util.ExcelUtil;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * 支付API
 * 
 * @className PayApi
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class PayApi extends BaseApi {
	private final TokenHolder tokenHolder;

	public PayApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 发货通知
	 * 
	 * @param weixinAccount
	 * @param transid
	 *            交易单号
	 * @param orderNo
	 *            订单号
	 * @param status
	 *            成功|失败
	 * @param statusMsg
	 *            status为失败时携带的信息
	 * @return
	 * @throws WeixinException
	 */
	public JsonResult deliverNotify(WeixinAccount weixinAccount,
			String transid, String orderNo, boolean status, String statusMsg)
			throws WeixinException {
		String delivernotify_uri = getRequestUri("delivernotify_uri");
		Token token = tokenHolder.getToken();

		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", weixinAccount.getAppId());
		param.put("appkey", weixinAccount.getPaySignKey());
		// 用户购买的openId
		param.put("openid", weixinAccount.getOpenId());
		param.put("transid", transid);
		param.put("out_trade_no", orderNo);
		param.put("deliver_timestamp", System.currentTimeMillis() / 1000 + "");
		param.put("deliver_status", status ? "1" : "0");
		param.put("deliver_msg", statusMsg);
		param.put("app_signature", DigestUtils.sha1Hex(MapUtil.toJoinString(
				param, false, true, null)));
		param.put("sign_method", "sha1");

		Response response = request.post(
				String.format(delivernotify_uri, token.getAccessToken()),
				JSON.toJSONString(param));

		return response.getAsJsonResult();
	}

	/**
	 * 订单查询
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param orderNo
	 *            订单号
	 * @return
	 * @throws WeixinException
	 */
	public Order orderQueryV2(WeixinAccount weixinAccount, IdQuery idQuery)
			throws WeixinException {
		String orderquery_uri = getRequestUri("orderquery_uri");
		Token token = tokenHolder.getToken();

		StringBuilder sb = new StringBuilder();
		sb.append(idQuery.getType().getName()).append(idQuery.getId());
		sb.append("&partner=").append(weixinAccount.getPartnerId());
		String part = sb.toString();
		sb.append("&key=").append(weixinAccount.getPartnerKey());
		String sign = DigestUtils.md5Hex(sb.toString()).toUpperCase();
		sb.delete(0, sb.length());
		sb.append(part).append("&sign=").append(sign);

		String timestamp = System.currentTimeMillis() / 1000 + "";
		JSONObject obj = new JSONObject();
		obj.put("appid", weixinAccount.getAppId());
		obj.put("appkey", weixinAccount.getPaySignKey());
		obj.put("package", sb.toString());
		obj.put("timestamp", timestamp);
		String signature = DigestUtils.sha1Hex(MapUtil.toJoinString(obj, false,
				true, null));

		obj = new JSONObject();
		obj.put("appid", weixinAccount.getAppId());
		obj.put("package", sb.toString());
		obj.put("timestamp", timestamp);
		obj.put("app_signature", signature);
		obj.put("sign_method", "sha1");

		Response response = request.post(
				String.format(orderquery_uri, token.getAccessToken()),
				obj.toJSONString());

		String order_info = response.getAsJson().getString("order_info");
		Order order = JSON.parseObject(order_info, Order.class,
				Feature.IgnoreNotMatch);
		order.setMapData(JSON.parseObject(order_info,
				new TypeReference<Map<String, String>>() {
				}));
		return order;
	}

	/**
	 * 维权处理
	 * 
	 * @param openId
	 *            用户ID
	 * @param feedbackId
	 *            维权单号
	 * @return
	 * @throws WeixinException
	 */
	public JsonResult updateFeedback(String openId, String feedbackId)
			throws WeixinException {
		String payfeedback_update_uri = ConfigUtil
				.getValue("payfeedback_update_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(payfeedback_update_uri,
				token.getAccessToken(), openId, feedbackId));
		return response.getAsJsonResult();
	}

	/**
	 * V3订单查询
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v3.Order orderQueryV3(
			WeixinAccount weixinAccount, IdQuery idQuery)
			throws WeixinException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getAppId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("nonce_str", RandomUtil.generateString(16));
		map.put(idQuery.getType().getName(), idQuery.getId());
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String orderquery_uri = getRequestUri("orderquery_v3_uri");
		Response response = request.post(orderquery_uri, param);
		return response
				.getAsObject(new TypeReference<com.foxinmy.weixin4j.mp.payment.v3.Order>() {
				});
	}

	/**
	 * native支付URL转短链接
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @throws WeixinException
	 */
	public String getShorturl(WeixinAccount weixinAccount, String url)
			throws WeixinException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getAppId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("long_url", url);
		map.put("nonce_str", RandomUtil.generateString(16));
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		try {
			map.put("long_url", URLEncoder.encode(url, utf8.name()));
		} catch (UnsupportedEncodingException e) {
			;
		}
		String param = map2xml(map);
		String shorturl_uri = getRequestUri("p_shorturl_uri");
		Response response = request.post(shorturl_uri, param);
		map = xml2map(response.getAsString());
		return map.get("short_url");
	}

	/**
	 * 关闭订单<br/>
	 * 当订单支付失败,调用关单接口后用新订单号重新发起支付,如果关单失败,返回已完
	 * 成支付请按正常支付处理。如果出现银行掉单,调用关单成功后,微信后台会主动发起退款。
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param idQuery
	 *            商户系统内部的订单号
	 * @return
	 * @throws WeixinException
	 */
	public XmlResult closeOrder(WeixinAccount weixinAccount, IdQuery idQuery)
			throws WeixinException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getAppId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("nonce_str", RandomUtil.generateString(16));
		map.put(idQuery.getType().getName(), idQuery.getId());
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String closeorder_uri = getRequestUri("closeorder_uri");
		Response response = request.post(closeorder_uri, param);
		return response.getAsXmlResult();
	}

	/**
	 * 下载对账单<br>
	 * 1.微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账 单中,跟原支付单订单号一致,bill_type 为
	 * REVOKED;<br>
	 * 2.微信在次日 9 点启动生成前一天的对账单,建议商户 9 点半后再获取;<br>
	 * 3.对账单中涉及金额的字段单位为“元”。<br>
	 * 
	 * @param weixinAccount
	 *            商户配置
	 * @param billDate
	 *            下载对账单的日期
	 * @param billType
	 *            下载对账单的类型 ALL,返回当日所有订单信息, 默认值 SUCCESS,返回当日成功支付的订单
	 *            REFUND,返回当日退款订单
	 * @return excel表格
	 * @throws WeixinException
	 * @throws IOException
	 */
	public File downloadbill(WeixinAccount weixinAccount, Date billDate,
			BillType billType) throws WeixinException, IOException {

		if (billDate == null) {
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, -10);
			billDate = now.getTime();
		}
		if (billType == null) {
			billType = BillType.ALL;
		}
		String _billDate = DateUtil.fortmatYYYYMMDD(billDate);
		String bill_path = ConfigUtil.getValue("bill_path");
		String fileName = String.format("%s_%s_%s.xls", _billDate, billType
				.name().toLowerCase(), weixinAccount.getAppId());
		File file = new File(String.format("%s/%s", bill_path, fileName));
		if (file.exists()) {
			return file;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getAppId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("nonce_str", RandomUtil.generateString(16));
		map.put("device_info", weixinAccount.getDeviceInfo());
		map.put("bill_date", _billDate);
		map.put("bill_type", billType.name());
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String downloadbill_uri = getRequestUri("downloadbill_uri");
		Response response = request.post(downloadbill_uri, param);

		BufferedReader reader = new BufferedReader(new StringReader(
				response.getAsString()));
		String line = null;
		List<String[]> bills = new LinkedList<String[]>();
		while ((line = reader.readLine()) != null) {
			bills.add(line.replaceAll("`", "").split(","));
		}
		reader.close();
		List<String> headers = Arrays.asList(bills.remove(0));
		List<String> totalDatas = Arrays.asList(bills.remove(bills.size() - 1));
		List<String> totalHeaders = Arrays
				.asList(bills.remove(bills.size() - 1));
		HSSFWorkbook wb = new HSSFWorkbook();
		wb.createSheet(_billDate + "对账单");
		ExcelUtil.list2excel(wb, headers, bills);
		ExcelUtil.list2excel(wb, totalHeaders, totalDatas);
		wb.write(new FileOutputStream(file));
		return file;
	}

	/**
	 * 退款查询<br/>
	 * 退款有一定延时,用零钱支付的退款20分钟内到账,银行卡支付的退款 3 个工作日后重新查询退款状态
	 * 
	 * @param weixinAccount
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @throws WeixinException
	 */
	public Refund refundQuery(WeixinAccount weixinAccount, IdQuery idQuery)
			throws WeixinException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getAppId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("nonce_str", RandomUtil.generateString(16));
		map.put("device_info", weixinAccount.getDeviceInfo());
		map.put(idQuery.getType().getName(), idQuery.getId());
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String refundquery_uri = getRequestUri("refundquery_uri");
		Response response = request.post(refundquery_uri, param);
		return new RefundConverter().fromXML(response.getAsString());
	}

	public static void main(String[] args) throws Exception {
		WeixinAccount weixinAccount = new WeixinAccount("wx0d1d598c0c03c999",
				null, "GATFzDwbQdbbci3QEQxX2rUBvwTrsMiZ", "10020674");
		PayApi payApi = new PayApi(null);

		System.out.println(payApi.refundQuery(weixinAccount, new IdQuery(
				"T0002", IdType.ORDERNO)));
	}
}
