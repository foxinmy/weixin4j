package com.foxinmy.weixin4j.mp;

import java.io.File;
import java.util.Date;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.XmlResult;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.api.Pay2Api;
import com.foxinmy.weixin4j.mp.api.Pay3Api;
import com.foxinmy.weixin4j.mp.api.PayApi;
import com.foxinmy.weixin4j.mp.payment.v3.ApiResult;
import com.foxinmy.weixin4j.mp.type.BillType;
import com.foxinmy.weixin4j.mp.type.IdQuery;
import com.foxinmy.weixin4j.mp.type.IdType;
import com.foxinmy.weixin4j.mp.type.RefundType;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 微信支付接口实现
 * 
 * @className WeixinPayProxy
 * @author jy
 * @date 2015年1月3日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
 */
public class WeixinPayProxy {
	private final PayApi payApi;
	private final Pay2Api pay2Api;
	private final Pay3Api pay3Api;

	/**
	 * 默认采用文件存放Token信息
	 */
	public WeixinPayProxy() {
		this(new FileTokenHolder(AccountType.MP));
	}

	/**
	 * WeixinAccount对象
	 * 
	 * @param weixinAccount
	 *            微信账户
	 */
	public WeixinPayProxy(WeixinMpAccount weixinAccount) {
		this(new FileTokenHolder(weixinAccount));
	}

	/**
	 * TokenHolder对象
	 * 
	 * @param tokenHolder
	 */
	public WeixinPayProxy(TokenHolder tokenHolder) {
		this.pay2Api = new Pay2Api(tokenHolder);
		this.pay3Api = new Pay3Api(tokenHolder);
		int version = ((WeixinMpAccount) tokenHolder.getAccount()).getVersion();
		if (version == 2) {
			this.payApi = this.pay2Api;
		} else if (version == 3) {
			this.payApi = this.pay3Api;
		} else {
			this.payApi = this.pay3Api;
		}
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
	 * @since V2 & V3
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @throws WeixinException
	 */
	public JsonResult deliverNotify(String openId, String transid,
			String outTradeNo, boolean status, String statusMsg)
			throws WeixinException {
		return payApi.deliverNotify(openId, transid, outTradeNo, status,
				statusMsg);
	}

	/**
	 * 维权处理
	 * 
	 * @param openId
	 *            用户ID
	 * @param feedbackId
	 *            维权单号
	 * @return 调用结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @since V2 & V3
	 * @throws WeixinException
	 */
	public JsonResult updateFeedback(String openId, String feedbackId)
			throws WeixinException {
		return payApi.updateFeedback(openId, feedbackId);
	}

	/**
	 * V2订单查询
	 * 
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @since V2
	 * @see com.foxinmy.weixin4j.mp.payment.v2.Order
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
	 * @return 订单详情
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v2.Order orderQueryV2(
			String outTradeNo) throws WeixinException {
		return pay2Api.orderQuery(new IdQuery(outTradeNo, IdType.TRADENO));
	}

	/**
	 * V3订单查询
	 * 
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @since V3
	 * @see com.foxinmy.weixin4j.mp.payment.v3.Order
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
	 * @return 订单详情
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v3.Order orderQueryV3(IdQuery idQuery)
			throws WeixinException {
		return pay3Api.orderQuery(idQuery);
	}

	/**
	 * V2申请退款(请求需要双向证书)</br>
	 * <p style="color:red">
	 * 交易时间超过 1 年的订单无法提交退款; </br> 支持部分退款,部分退需要设置相同的订单号和不同的 out_refund_no。一笔退款失
	 * 败后重新提交,要采用原来的 out_refund_no。总退款金额不能超过用户实际支付金额。</br>
	 * </p>
	 * 
	 * @param caFile
	 *            证书文件(后缀为*.pfx)
	 * @param idQuery
	 *            ) 商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
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
	 *            操作员密码
	 * 
	 * @return 退款申请结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundResult
	 * @since V2
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v2.RefundResult refundV2(
			File caFile, IdQuery idQuery, String outRefundNo, double totalFee,
			double refundFee, String opUserId, String opUserPasswd)
			throws WeixinException {
		return pay2Api.refund(caFile, idQuery, outRefundNo, totalFee,
				refundFee, opUserId, opUserPasswd);
	}

	/**
	 * V2退款申请采用properties中配置的ca文件
	 * 
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinPayProxy#refundV2(File, IdQuery, String, double, double, String,String)}
	 */
	public com.foxinmy.weixin4j.mp.payment.v2.RefundResult refundV2(
			IdQuery idQuery, String outRefundNo, double totalFee,
			double refundFee, String opUserId, String opUserPasswd)
			throws WeixinException {
		File caFile = new File(ConfigUtil.getClassPathValue("ca_file"));
		return refundV2(caFile, idQuery, outRefundNo, totalFee, refundFee,
				opUserId, opUserPasswd);
	}

	/**
	 * V2退款申请
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
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundResult
	 * @return 退款结果
	 */
	public com.foxinmy.weixin4j.mp.payment.v2.RefundResult refundV2(
			File caFile, IdQuery idQuery, String outRefundNo, double totalFee,
			double refundFee, String opUserId, String opUserPasswd,
			String recvUserId, String reccvUserName, RefundType refundType)
			throws WeixinException {
		return pay2Api.refund(caFile, idQuery, outRefundNo, totalFee,
				refundFee, opUserId, opUserPasswd, recvUserId, reccvUserName,
				refundType);
	}

	/**
	 * V2退款查询</br> 退款有一定延时,用零钱支付的退款20分钟内到账,银行卡支付的退款 3 个工作日后重新查询退款状态
	 * 
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundRecord
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
	 * @since V2
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v2.RefundRecord refundQueryV2(
			IdQuery idQuery) throws WeixinException {
		return pay2Api.refundQuery(idQuery);
	}

	/**
	 * V3申请退款(请求需要双向证书)</br>
	 * <p style="color:red">
	 * 交易时间超过 1 年的订单无法提交退款; </br> 支持部分退款,部分退需要设置相同的订单号和不同的 out_refund_no。一笔退款失
	 * 败后重新提交,要采用原来的 out_refund_no。总退款金额不能超过用户实际支付金额。</br>
	 * </p>
	 * 
	 * @param caFile
	 *            证书文件(后缀为*.p12)
	 * @param idQuery
	 *            ) 商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
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
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
	 * @since V3
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v3.RefundResult refundV3(
			File caFile, IdQuery idQuery, String outRefundNo, double totalFee,
			double refundFee, String opUserId) throws WeixinException {
		return pay3Api.refund(caFile, idQuery, outRefundNo, totalFee,
				refundFee, opUserId);
	}

	/**
	 * V3退款申请采用properties中配置的ca文件
	 * 
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinPayProxy#refundV3(File, IdQuery, String, double, double, String)}
	 */
	public com.foxinmy.weixin4j.mp.payment.v3.RefundResult refundV3(
			IdQuery idQuery, String outRefundNo, double totalFee,
			double refundFee, String opUserId) throws WeixinException {
		File caFile = new File(ConfigUtil.getClassPathValue("ca_file"));
		return pay3Api.refund(caFile, idQuery, outRefundNo, totalFee,
				refundFee, opUserId);
	}

	/**
	 * V3退款查询</br> 退款有一定延时,用零钱支付的退款20分钟内到账,银行卡支付的退款 3 个工作日后重新查询退款状态
	 * 
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
	 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundRecord
	 * @since V3
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v3.RefundRecord refundQueryV3(
			IdQuery idQuery) throws WeixinException {
		return pay3Api.refundQuery(idQuery);
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
	 * @since V2 & V3
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @throws WeixinException
	 */
	public File downloadbill(Date billDate, BillType billType)
			throws WeixinException {
		return payApi.downloadbill(billDate, billType);
	}

	/**
	 * 冲正订单(需要证书)</br> 当支付返回失败,或收银系统超时需要取消交易,可以调用该接口</br> 接口逻辑:支
	 * 付失败的关单,支付成功的撤销支付</br> <font color="red">7天以内的单可撤销,其他正常支付的单
	 * 如需实现相同功能请调用退款接口</font></br> <font
	 * color="red">调用扣款接口后请勿立即调用撤销,需要等待5秒以上。先调用查单接口,如果没有确切的返回,再调用撤销</font></br>
	 * 
	 * @param ca
	 *            证书文件(V2版本后缀为*.pfx,V3版本后缀为*.p12)
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 撤销结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
	 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
	 * @since V3
	 * @throws WeixinException
	 */
	public ApiResult reverse(File caFile, IdQuery idQuery)
			throws WeixinException {
		return payApi.reverse(caFile, idQuery);
	}

	/**
	 * 冲正撤销:默认采用properties中配置的ca文件
	 * 
	 * @param idQuery
	 *            transaction_id、out_trade_no 二选一
	 * @return 撤销结果
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#reverse(File, IdQuery)}
	 * @throws WeixinException
	 */
	public ApiResult reverse(IdQuery idQuery) throws WeixinException {
		File caFile = new File(ConfigUtil.getClassPathValue("ca_file"));
		return payApi.reverse(caFile, idQuery);
	}

	/**
	 * 关闭订单</br> 当订单支付失败,调用关单接口后用新订单号重新发起支付,如果关单失败,返回已完
	 * 成支付请按正常支付处理。如果出现银行掉单,调用关单成功后,微信后台会主动发起退款。
	 * 
	 * @param outTradeNo
	 *            商户系统内部的订单号
	 * @return 执行结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
	 * @since V3
	 * @throws WeixinException
	 */
	public ApiResult closeOrder(String outTradeNo) throws WeixinException {
		return payApi.closeOrder(outTradeNo);
	}

	/**
	 * native支付URL转短链接
	 * 
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay2Api
	 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
	 * @since V2 & V3
	 * @throws WeixinException
	 */
	public String getPayShorturl(String url) throws WeixinException {
		return payApi.getShorturl(url);
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
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.api.Pay3Api
	 * @throws WeixinException
	 */
	public XmlResult interfaceReport(String interfaceUrl, int executeTime,
			String outTradeNo, String ip, Date time, XmlResult returnXml)
			throws WeixinException {
		return pay3Api.interfaceReport(interfaceUrl, executeTime, outTradeNo,
				ip, time, returnXml);
	}
}
