package com.foxinmy.weixin4j.mp.api;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.mp.payment.v3.ApiResult;
import com.foxinmy.weixin4j.mp.type.BillType;
import com.foxinmy.weixin4j.mp.type.IdQuery;
import com.foxinmy.weixin4j.mp.type.SignType;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 支付API
 * 
 * @className PayApi
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
 */
public abstract class PayApi extends MpApi {
	protected final TokenHolder tokenHolder;
	protected final WeixinMpAccount weixinAccount;

	public PayApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
		this.weixinAccount = (WeixinMpAccount) tokenHolder.getAccount();
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
		map.put("app_signature", PayUtil.paysignSha(map));
		map.put("sign_method", SignType.SHA1.name().toLowerCase());

		Response response = request.post(
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
		Response response = request.get(String.format(payfeedback_update_uri,
				token.getAccessToken(), openId, feedbackId));
		return response.getAsJsonResult();
	}

	/**
	 * 订单查询
	 * 
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 订单信息
	 * @see com.foxinmy.weixin4j.mp.payment.v2.Order
	 * @see com.foxinmy.weixin4j.mp.payment.v3.Order
	 * @throws WeixinException
	 */
	public abstract Object orderQuery(IdQuery idQuery) throws WeixinException;

	/**
	 * 申请退款(请求需要双向证书)</br>
	 * <p style="color:red">
	 * 交易时间超过 1 年的订单无法提交退款; </br> 支持部分退款,部分退需要设置相同的订单号和不同的 out_refund_no。一笔退款失
	 * 败后重新提交,要采用原来的 out_refund_no。总退款金额不能超过用户实际支付金额。</br>
	 * </p>
	 * 
	 * @param caFile
	 *            证书文件(V2版本后缀为*.pfx,V3版本后缀为*.p12)
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
	 *            更多参数 如V2版本的opUserPasswd
	 * 
	 * @return 退款申请结果
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundResult
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundResult
	 * @throws WeixinException
	 */
	protected abstract Object refund(File caFile, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, Map<String, String> mopara) throws WeixinException;

	/**
	 * 退款查询</br> 退款有一定延时,用零钱支付的退款20分钟内到账,银行卡支付的退款 3 个工作日后重新查询退款状态
	 * 
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundRecord
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundRecord
	 * @throws WeixinException
	 */
	public abstract Object refundQuery(IdQuery idQuery) throws WeixinException;

	/**
	 * 冲正订单(需要证书)
	 * 
	 * @param caFile
	 *            证书文件 (V2版本后缀为*.pfx,V3版本后缀为*.p12)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 撤销结果
	 * @throws WeixinException
	 */
	public abstract ApiResult reverse(File caFile, IdQuery idQuery)
			throws WeixinException;

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
	 * @throws WeixinException
	 */
	public abstract File downloadbill(Date billDate, BillType billType)
			throws WeixinException;

	/**
	 * 关闭订单</br> 当订单支付失败,调用关单接口后用新订单号重新发起支付,如果关单失败,返回已完
	 * 成支付请按正常支付处理。如果出现银行掉单,调用关单成功后,微信后台会主动发起退款。
	 * 
	 * @param outTradeNo
	 *            商户系统内部的订单号
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public abstract ApiResult closeOrder(String outTradeNo)
			throws WeixinException;

	/**
	 * native支付URL转短链接
	 * 
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @throws WeixinException
	 */
	public abstract String getShorturl(String url) throws WeixinException;
}
