package com.foxinmy.weixin4j.payment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.api.CashApi;
import com.foxinmy.weixin4j.api.CouponApi;
import com.foxinmy.weixin4j.api.Pay3Api;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.exception.WeixinPayException;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.coupon.CouponDetail;
import com.foxinmy.weixin4j.payment.coupon.CouponResult;
import com.foxinmy.weixin4j.payment.coupon.CouponStock;
import com.foxinmy.weixin4j.payment.mch.ApiResult;
import com.foxinmy.weixin4j.payment.mch.AuthCodeOpenIdResult;
import com.foxinmy.weixin4j.payment.mch.MPPayment;
import com.foxinmy.weixin4j.payment.mch.MPPaymentRecord;
import com.foxinmy.weixin4j.payment.mch.MPPaymentResult;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.payment.mch.PrePay;
import com.foxinmy.weixin4j.payment.mch.Redpacket;
import com.foxinmy.weixin4j.payment.mch.RedpacketRecord;
import com.foxinmy.weixin4j.payment.mch.RedpacketSendResult;
import com.foxinmy.weixin4j.payment.mch.RefundRecord;
import com.foxinmy.weixin4j.type.BillType;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;
import com.foxinmy.weixin4j.util.Weixin4jConst;

/**
 * 微信支付接口实现
 * 
 * @className WeixinPayProxy
 * @author jy
 * @date 2015年1月3日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.api.Pay3Api
 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/index.html">商户平台支付API</a>
 */
public class WeixinPayProxy {

	private final Pay3Api pay3Api;
	private final CouponApi couponApi;
	private final CashApi cashApi;

	private final WeixinPayAccount weixinAccount;

	/**
	 * 使用weixin4j.properties配置的账号信息
	 */
	public WeixinPayProxy() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinPayAccount.class));
	}

	/**
	 *
	 * @param weixinAccount
	 *            支付相关的公众号账号信息
	 * 
	 */
	public WeixinPayProxy(WeixinPayAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
		this.pay3Api = new Pay3Api(weixinAccount);
		this.couponApi = new CouponApi(weixinAccount);
		this.cashApi = new CashApi(weixinAccount);
	}

	/**
	 * 获取微信商户支付信息
	 * 
	 * @return
	 */
	public WeixinPayAccount getWeixinAccount() {
		// clone ...
		String text = JSON.toJSONString(weixinAccount);
		return JSON.parseObject(text, WeixinPayAccount.class);
	}

	/**
	 * 统一下单接口</br>
	 * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI
	 * 、APP等不同场景生成交易串调起支付。
	 * 
	 * @param payPackage
	 *            包含订单信息的对象
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see com.foxinmy.weixin4j.payment.mch.MchPayPackage
	 * @see com.foxinmy.weixin4j.payment.mch.PrePay
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">统一下单接口</a>
	 * @return 预支付对象
	 */
	public PrePay createPrePay(MchPayPackage payPackage)
			throws WeixinPayException {
		return pay3Api.createPrePay(payPackage);
	}

	/**
	 * 生成JSAPI支付请求对象
	 * 
	 * @param openId
	 *            用户ID
	 * @param body
	 *            订单描述
	 * @param outTradeNo
	 *            订单号
	 * @param totalFee
	 *            订单总额 按实际金额传入即可(元) 构造函数会转换为分
	 * @param notifyUrl
	 *            支付通知地址
	 * @param createIp
	 *            ip地址
	 * @return MchPayRequest对象；<font
	 *         color="red">注意：如果要转换为JSON格式请使用fastjson中的JSON
	 *         #toJSONString方法或直接用MchPayRequest#asPayJsRequestJson方法</font>
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see com.foxinmy.weixin4j.payment.mch.MchPayRequest
	 * @throws WeixinPayException
	 */
	public MchPayRequest createPayJsRequest(String openId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp) throws WeixinPayException {
		return pay3Api.createPayJsRequest(openId, body, outTradeNo, totalFee,
				notifyUrl, createIp);
	}

	/**
	 * 生成JSAPI支付请求对象【完整参数】
	 * 
	 * @param openId
	 *            用户ID
	 * @param body
	 *            商品描述
	 * @param outTradeNo
	 *            商户内部唯一订单号
	 * @param totalFee
	 *            商品总额 单位元
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            订单生成的机器 IP
	 * @param attach
	 *            附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * @param timeStart
	 *            订单生成时间，格式为yyyyMMddHHmmss
	 * @param timeExpire
	 *            订单失效时间，格式为yyyyMMddHHmmss;注意：最短失效时间间隔必须大于5分钟
	 * @param goodsTag
	 *            商品标记，代金券或立减优惠功能的参数
	 * @param limitPay
	 *            指定支付方式:no_credit--指定不能使用信用卡支付
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see com.foxinmy.weixin4j.payment.mch.MchPayRequest
	 * @return MchPayRequest对象；<font
	 *         color="red">注意：如果要转换为JSON格式请使用fastjson中的JSON
	 *         #toJSONString方法或直接用MchPayRequest#asPayJsRequestJson方法</font>
	 * @throws WeixinPayException
	 */
	public MchPayRequest createPayJsRequest(String openId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp, String attach, Date timeStart, Date timeExpire,
			String goodsTag, String limitPay) throws WeixinPayException {
		return pay3Api.createPayJsRequest(openId, body, outTradeNo, totalFee,
				notifyUrl, createIp, attach, timeStart, timeExpire, goodsTag,
				limitPay);
	}

	/**
	 * 创建Native支付(扫码支付)链接【模式一】
	 * 
	 * @param productId
	 *            与订单ID等价
	 * @return 支付链接
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/native.php">扫码支付</a>
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4">模式一</a>
	 */
	public String createNativePayRequestURL(String productId) {
		return pay3Api.createNativePayRequestURL(productId);
	}

	/**
	 * 创建V3.x NativePay支付(扫码支付)链接【模式二】【必填参数】
	 * 
	 * @param productId
	 *            商品ID
	 * @param body
	 *            商品描述
	 * @param outTradeNo
	 *            商户内部唯一订单号
	 * @param totalFee
	 *            商品总额 单位元
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            订单生成的机器 IP
	 * @return 支付链接
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/native.php">扫码支付</a>
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5">模式二</a>
	 * @throws WeixinPayException
	 */
	public String createNativePayRequestURL(String productId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp) throws WeixinPayException {
		return pay3Api.createNativePayRequestURL(productId, body, outTradeNo,
				totalFee, notifyUrl, createIp);
	}

	/**
	 * 创建Native支付(扫码支付)链接【模式二】【完整参数】
	 * 
	 * @param productId
	 *            商品ID
	 * @param body
	 *            商品描述
	 * @param outTradeNo
	 *            商户内部唯一订单号
	 * @param totalFee
	 *            商品总额 单位元
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            订单生成的机器 IP
	 * @param attach
	 *            附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * @param timeStart
	 *            订单生成时间，格式为yyyyMMddHHmmss
	 * @param timeExpire
	 *            订单失效时间，格式为yyyyMMddHHmmss;注意：最短失效时间间隔必须大于5分钟
	 * @param goodsTag
	 *            商品标记，代金券或立减优惠功能的参数
	 * @param limitPay
	 *            指定支付方式:no_credit--指定不能使用信用卡支付
	 * @return 支付链接
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/native.php">扫码支付</a>
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5">模式二</a>
	 * @throws WeixinPayException
	 */
	public String createNativePayRequestURL(String productId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp, String attach, Date timeStart, Date timeExpire,
			String goodsTag, String limitPay) throws WeixinPayException {
		return pay3Api.createNativePayRequestURL(productId, body, outTradeNo,
				totalFee, notifyUrl, createIp, attach, timeStart, timeExpire,
				goodsTag, limitPay);
	}

	/**
	 * 提交被扫支付
	 * 
	 * @param authCode
	 *            扫码支付授权码 ,设备读取用户微信中的条码或者二维码信息
	 * @param body
	 *            商品描述
	 * @param orderNo
	 *            商户内部唯一订单号
	 * @param orderFee
	 *            商品总额 单位元
	 * @param createIp
	 *            订单生成的机器 IP
	 * @return 支付的订单信息
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see {@link #createMicroPay(MicroPayPackage)}
	 * @throws WeixinException
	 */
	public Order createMicroPay(String authCode, String body, String orderNo,
			double orderFee, String createIp) throws WeixinException {
		return pay3Api.createMicroPay(authCode, body, orderNo, orderFee,
				createIp);
	}

	/**
	 * 提交被扫支付:收银员使用扫码设备读取微信用户刷卡授权码以后，二维码或条码信息传送至商户收银台，由商户收银台或者商户后台调用该接口发起支付.
	 * 
	 * @param payPackage
	 *            订单信息
	 * @return 支付的订单信息
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see com.foxinmy.weixin4j.payment.MicroPayPackage
	 * @see com.foxinmy.weixin4j.payment.mch.Order
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10">提交被扫支付API</a>
	 */
	public Order createMicroPay(MicroPayPackage payPackage)
			throws WeixinException {
		return pay3Api.createMicroPay(payPackage);
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
	 * @since V3
	 * @see com.foxinmy.weixin4j.payment.mch.Order
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2">订单查询API</a>
	 * @return 订单详情
	 * @throws WeixinException
	 */
	public Order orderQuery(IdQuery idQuery) throws WeixinException {
		return pay3Api.orderQuery(idQuery);
	}

	/**
	 * 申请退款(请求需要双向证书)</br>
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
	 *            证书文件(后缀为*.p12)
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
	 * 
	 * @return 退款申请结果
	 * @see com.foxinmy.weixin4j.payment.mch.RefundResult
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4">申请退款API</a>
	 * @since V3
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.payment.mch.RefundResult refundApply(
			InputStream ca, IdQuery idQuery, String outRefundNo,
			double totalFee, double refundFee, CurrencyType refundFeeType,
			String opUserId) throws WeixinException {
		return pay3Api.refundApply(ca, idQuery, outRefundNo, totalFee,
				refundFee, refundFeeType, opUserId);
	}

	/**
	 * 退款申请(全额退款)采用properties中配置的ca文件
	 * 
	 * @throws IOException
	 * 
	 * @see {@link #refundApply(InputStream, IdQuery, String, double, double, String,CurrencyType)}
	 */
	public com.foxinmy.weixin4j.payment.mch.RefundResult refundApply(
			IdQuery idQuery, String outRefundNo, double totalFee)
			throws WeixinException, IOException {
		return pay3Api
				.refundApply(
						new FileInputStream(Weixin4jConfigUtil
								.getClassPathValue("certificate.file",
										Weixin4jConst.DEFAULT_CAFILE_PATH)),
						idQuery, outRefundNo, totalFee);
	}

	/**
	 * 退款查询
	 * <p>
	 * 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
	 * </p>
	 * 
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see com.foxinmy.weixin4j.payment.mch.RefundRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5">退款查询API</a>
	 * @since V3
	 * @throws WeixinException
	 */
	public RefundRecord refundQuery(IdQuery idQuery) throws WeixinException {
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
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单API</a>
	 * @throws WeixinException
	 */
	public File downloadBill(Date billDate, BillType billType)
			throws WeixinException {
		return pay3Api.downloadBill(billDate, billType);
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
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @since V3
	 * @throws WeixinException
	 */
	public ApiResult reverseOrder(InputStream ca, IdQuery idQuery)
			throws WeixinException {
		return pay3Api.reverseOrder(ca, idQuery);
	}

	/**
	 * 冲正撤销:默认采用properties中配置的ca文件
	 * 
	 * @param idQuery
	 *            transaction_id、out_trade_no 二选一
	 * @return 撤销结果
	 * @see {@link #reverseOrder(InputStream, IdQuery)}
	 * @throws WeixinException
	 * @throws IOException
	 */
	public ApiResult reverseOrder(IdQuery idQuery) throws WeixinException,
			IOException {
		return pay3Api
				.reverseOrder(
						new FileInputStream(Weixin4jConfigUtil
								.getClassPathValue("certificate.file",
										Weixin4jConst.DEFAULT_CAFILE_PATH)),
						idQuery);
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
	 * @return 执行结果
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @since V3
	 * @throws WeixinException
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3">关闭订单API</a>
	 */
	public ApiResult closeOrder(String outTradeNo) throws WeixinException {
		return pay3Api.closeOrder(outTradeNo);
	}

	/**
	 * native支付URL转短链接:用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)，减小二维码数据量
	 * ，提升扫描速度和精确度。
	 * 
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_9">转换短链接API</a>
	 * @since V3
	 * @throws WeixinException
	 */
	public String getPayShorturl(String url) throws WeixinException {
		return pay3Api.getShorturl(url);
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
	 * @see com.foxinmy.weixin4j.api.Pay3Api
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_8">接口测试上报API</a>
	 * @throws WeixinException
	 */
	public XmlResult interfaceReport(String interfaceUrl, int executeTime,
			String outTradeNo, String ip, Date time, XmlResult returnXml)
			throws WeixinException {
		return pay3Api.interfaceReport(interfaceUrl, executeTime, outTradeNo,
				ip, time, returnXml);
	}

	/**
	 * 发放代金券(需要证书)
	 * 
	 * @param ca
	 *            证书文件(后缀为*.p12)
	 * @param couponStockId
	 *            代金券批次id
	 * @param partnerTradeNo
	 *            商户发放凭据号（格式：商户id+日期+流水号），商户侧需保持唯一性
	 * @param openId
	 *            用户的openid
	 * @param opUserId
	 *            操作员帐号, 默认为商户号 可在商户平台配置操作员对应的api权限 可为空
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.api.CouponApi
	 * @see com.foxinmy.weixin4j.payment.coupon.CouponResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/sp_coupon.php?chapter=12_3">发放代金券接口</a>
	 * @throws WeixinException
	 */
	public CouponResult sendCoupon(InputStream ca, String couponStockId,
			String partnerTradeNo, String openId, String opUserId)
			throws WeixinException {
		return couponApi.sendCoupon(ca, couponStockId, partnerTradeNo, openId,
				opUserId);
	}

	/**
	 * 发放代金券采用properties中配置的ca文件
	 * 
	 * @see {@link com.foxinmy.weixin4j.payment.WeixinPayProxy#sendCoupon(InputStream, String, String, String, String)}
	 */
	public CouponResult sendCoupon(String couponStockId, String partnerTradeNo,
			String openId) throws WeixinException, IOException {
		return couponApi
				.sendCoupon(
						new FileInputStream(Weixin4jConfigUtil
								.getClassPathValue("certificate.file",
										Weixin4jConst.DEFAULT_CAFILE_PATH)),
						couponStockId, partnerTradeNo, openId, null);
	}

	/**
	 * 查询代金券批次
	 * 
	 * @param couponStockId
	 *            代金券批次ID
	 * @return 代金券批次信息
	 * @see com.foxinmy.weixin4j.api.CouponApi
	 * @see com.foxinmy.weixin4j.payment.coupon.CouponStock
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/sp_coupon.php?chapter=12_4">查询代金券信息</a>
	 * @throws WeixinException
	 */
	public CouponStock queryCouponStock(String couponStockId)
			throws WeixinException {
		return couponApi.queryCouponStock(couponStockId);
	}

	/**
	 * 查询代金券详细
	 * 
	 * @param couponId
	 *            代金券ID
	 * @return 代金券详细信息
	 * @see com.foxinmy.weixin4j.api.CouponApi
	 * @see com.foxinmy.weixin4j.payment.coupon.CouponDetail
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/sp_coupon.php?chapter=12_5">查询代金券详细信息</a>
	 * @throws WeixinException
	 */
	public CouponDetail queryCouponDetail(String couponId)
			throws WeixinException {
		return couponApi.queryCouponDetail(couponId);
	}

	/**
	 * 发放红包 企业向微信用户个人发现金红包
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param redpacket
	 *            红包信息
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.api.CashApi
	 * @see com.foxinmy.weixin4j.payment.mch.Redpacket
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketSendResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5">红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketSendResult sendRedpack(InputStream ca, Redpacket redpacket)
			throws WeixinException {
		return cashApi.sendRedpack(ca, redpacket);
	}

	/**
	 * 发放红包采用properties中配置的ca文件
	 * 
	 * @see {@link com.foxinmy.weixin4j.payment.WeixinPayProxy#sendRedpack(InputStream, Redpacket)}
	 */
	public RedpacketSendResult sendRedpack(Redpacket redpacket)
			throws WeixinException, IOException {
		return cashApi
				.sendRedpack(
						new FileInputStream(Weixin4jConfigUtil
								.getClassPathValue("certificate.file",
										Weixin4jConst.DEFAULT_CAFILE_PATH)),
						redpacket);
	}

	/**
	 * 查询红包记录
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param outTradeNo
	 *            商户发放红包的商户订单号
	 * @return 红包记录
	 * @see com.foxinmy.weixin4j.api.CashApi
	 * @see com.foxinmy.weixin4j.payment.mch.RedpacketRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_6">查询红包接口说明</a>
	 * @throws WeixinException
	 */
	public RedpacketRecord queryRedpack(InputStream ca, String outTradeNo)
			throws WeixinException {
		return cashApi.queryRedpack(ca, outTradeNo);
	}

	/**
	 * 查询红包采用properties中配置的ca文件
	 * 
	 * @see {@link com.foxinmy.weixin4j.payment.WeixinPayProxy#queryRedpack(InputStream,String)}
	 */
	public RedpacketRecord queryRedpack(String outTradeNo)
			throws WeixinException, IOException {
		return cashApi
				.queryRedpack(
						new FileInputStream(Weixin4jConfigUtil
								.getClassPathValue("certificate.file",
										Weixin4jConst.DEFAULT_CAFILE_PATH)),
						outTradeNo);
	}

	/**
	 * 企业付款 实现企业向个人付款，针对部分有开发能力的商户， 提供通过API完成企业付款的功能。 比如目前的保险行业向客户退保、给付、理赔。
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param mpPayment
	 *            付款信息
	 * @return 付款结果
	 * @see com.foxinmy.weixin4j.api.CashApi
	 * @see com.foxinmy.weixin4j.payment.mch.MPPayment
	 * @see com.foxinmy.weixin4j.payment.mch.MPPaymentResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_1">企业付款</a>
	 * @throws WeixinException
	 */
	public MPPaymentResult mpPayment(InputStream ca, MPPayment mpPayment)
			throws WeixinException {
		return cashApi.mchPayment(ca, mpPayment);
	}

	/**
	 * 企业付款采用properties中配置的ca文件
	 * 
	 * @see {@link com.foxinmy.weixin4j.payment.WeixinPayProxy#mpPayment(InputStream, MPPayment)}
	 */
	public MPPaymentResult mpPayment(MPPayment mpPayment)
			throws WeixinException, IOException {
		return cashApi
				.mchPayment(
						new FileInputStream(Weixin4jConfigUtil
								.getClassPathValue("certificate.file",
										Weixin4jConst.DEFAULT_CAFILE_PATH)),
						mpPayment);
	}

	/**
	 * 企业付款查询 用于商户的企业付款操作进行结果查询，返回付款操作详细结果
	 * 
	 * @param ca
	 *            证书文件(V3版本后缀为*.p12)
	 * @param outTradeNo
	 *            商户调用企业付款API时使用的商户订单号
	 * @return 付款记录
	 * @see com.foxinmy.weixin4j.api.CashApi
	 * @see com.foxinmy.weixin4j.payment.mch.MPPaymentRecord
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/mch_pay.php?chapter=14_3">企业付款查询</a>
	 * @throws WeixinException
	 */
	public MPPaymentRecord mpPaymentQuery(InputStream ca, String outTradeNo)
			throws WeixinException {
		return cashApi.mchPaymentQuery(ca, outTradeNo);
	}

	/**
	 * 企业付款查询采用properties中配置的ca文件
	 * 
	 * @see {@link com.foxinmy.weixin4j.payment.WeixinPayProxy#mpPaymentQuery(InputStream, String)}
	 */
	public MPPaymentRecord mpPaymentQuery(String outTradeNo)
			throws WeixinException, IOException {
		return cashApi
				.mchPaymentQuery(
						new FileInputStream(Weixin4jConfigUtil
								.getClassPathValue("certificate.file",
										Weixin4jConst.DEFAULT_CAFILE_PATH)),
						outTradeNo);
	}

	/**
	 * 授权码查询OPENID接口
	 * 
	 * @param authCode
	 *            扫码支付授权码，设备读取用户微信中的条码或者二维码信息
	 * @return 查询结果
	 * @see com.foxinmy.weixin4j.api.CashApi
	 * @see com.foxinmy.weixin4j.payment.mch.AuthCodeOpenIdResult
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_13&index=9">授权码查询OPENID</a>
	 * @throws WeixinException
	 */
	public AuthCodeOpenIdResult authCode2openId(String authCode)
			throws WeixinException {
		return pay3Api.authCode2openId(authCode);
	}

	public final static String VERSION = "1.6.5";
}
