package com.foxinmy.weixin4j.pay.payment;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.api.CashApi;
import com.foxinmy.weixin4j.api.CouponApi;
import com.foxinmy.weixin4j.api.CustomsApi;
import com.foxinmy.weixin4j.api.PayApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.pay.payment.coupon.CouponDetail;
import com.foxinmy.weixin4j.pay.payment.coupon.CouponResult;
import com.foxinmy.weixin4j.pay.payment.coupon.CouponStock;
import com.foxinmy.weixin4j.pay.payment.mch.*;
import com.foxinmy.weixin4j.sign.WeixinSignature;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.CustomsCity;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.TarType;
import com.foxinmy.weixin4j.type.mch.BillType;
import com.foxinmy.weixin4j.type.mch.RefundAccountType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 微信支付接口实现
 *
 * @className WeixinPayProxy
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月3日
 * @since JDK 1.6
 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/index.html">商户平台支付API</a>
 */
public class WeixinPayProxy {

	/**
	 * 微信支付API:js支付、扫码支付等接口
	 */
	private final PayApi payApi;
	/**
	 * 代金券API
	 */
	private final CouponApi couponApi;
	/**
	 * 现金API
	 */
	private final CashApi cashApi;
	/**
	 * 海关API
	 */
	private final CustomsApi customsApi;
	/**
	 * 商户信息
	 */
	private final WeixinPayAccount weixinPayAccount;

	/**
	 * 微信支付接口实现(使用weixin4j.properties配置的account商户信息)
	 */
	public WeixinPayProxy() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinPayAccount.class));
	}

	/**
	 * 微信支付接口实现
	 *
	 * @param weixinPayAccount
	 *            微信商户信息
	 */
	public WeixinPayProxy(WeixinPayAccount weixinPayAccount) {
		if (weixinPayAccount == null) {
			throw new IllegalArgumentException(
					"weixinPayAccount must not be empty");
		}
		this.weixinPayAccount = weixinPayAccount;
		this.payApi = new PayApi(weixinPayAccount);
		this.couponApi = new CouponApi(weixinPayAccount);
		this.cashApi = new CashApi(weixinPayAccount);
		this.customsApi = new CustomsApi(weixinPayAccount);
	}

	/**
	 * 获取微信商户账号信息
	 *
	 * @return
	 */
	public WeixinPayAccount getWeixinPayAccount() {
		return weixinPayAccount;
	}

	/**
	 * 获取微信签名类
	 *
	 * @return
	 */
	public WeixinSignature getWeixinSignature() {
		return payApi.getWeixinSignature();
	}

	/**
	 * 统一下单接口</br>
	 * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI
	 * 、APP等不同场景生成交易串调起支付。
	 *
	 * @param payPackage
	 *            包含订单信息的对象
	 * @see PayApi
	 * @see MchPayPackage
	 * @see PrePay
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">统一下单接口
	 *      </a>
	 * @return 预支付对象
	 */
	public PrePay createPrePay(MchPayPackage payPackage) throws WeixinException {
		return payApi.createPrePay(payPackage);
	}

	/**
	 * 创建支付请求对象
	 *
	 * @param payPackage
	 *            支付详情
	 * @return 支付请求对象
	 * @see PayApi
	 * @see JSAPIPayRequest JS支付
	 * @see NATIVEPayRequest 扫码支付
	 * @see MICROPayRequest 刷卡支付
	 * @see APPPayRequest APP支付
	 * @see WAPPayRequest WAP支付
	 * @see MchPayRequest#toRequestString()
	 * @throws WeixinException
	 */
	public MchPayRequest createPayRequest(MchPayPackage payPackage)
			throws WeixinException {
		return payApi.createPayRequest(payPackage);
	}

	/**
	 * 创建JSAPI支付请求对象
	 *
	 * @param openId
	 *            用户ID
	 * @param body
	 *            订单描述
	 * @param outTradeNo
	 *            订单号
	 * @param totalFee
	 *            订单总额(元)
	 * @param notifyUrl
	 *            支付通知地址
	 * @param createIp
	 *            ip地址
	 * @param attach
	 *            附加数据 非必填
	 * @see PayApi
	 * @see JSAPIPayRequest
	 * @see MchPayRequest#toRequestString()
	 * @return JSAPI支付对象
	 * @throws WeixinException
	 */
	public MchPayRequest createJSPayRequest(String openId, String body,
                                            String outTradeNo, double totalFee, String notifyUrl,
                                            String createIp, String attach) throws WeixinException {
		return payApi.createJSPayRequest(openId, body, outTradeNo, totalFee,
				notifyUrl, createIp, attach);
	}

	/**
	 * <p>
	 * 生成编辑地址请求
	 * </p>
	 *
	 * err_msg edit_address:ok获取编辑收货地址成功</br> edit_address:fail获取编辑收货地址失败</br>
	 * userName 收货人姓名</br> telNumber 收货人电话</br> addressPostalCode 邮编</br>
	 * proviceFirstStageName 国标收货地址第一级地址</br> addressCitySecondStageName
	 * 国标收货地址第二级地址</br> addressCountiesThirdStageName 国标收货地址第三级地址</br>
	 * addressDetailInfo 详细收货地址信息</br> nationalCode 收货地址国家码</br>
	 *
	 * @param url
	 *            当前访问页的URL
	 * @param oauthToken
	 *            oauth授权时产生的token
	 * @see PayApi
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_8&index=7">
	 *      收货地址共享</a>
	 * @return 编辑地址请求JSON串
	 */
	public String createAddressRequestJSON(String url, String oauthToken) {
		return payApi.createAddressRequestJSON(url, oauthToken);
	}

	/**
	 * 创建Native支付(扫码支付)链接【模式一】
	 *
	 * @param productId
	 *            与订单ID等价
	 * @return 支付链接
	 * @see PayApi
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1">扫码支付
	 *      </a>
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4">模式一
	 *      </a>
	 */
	public String createNativePayRequest(String productId) {
		return payApi.createNativePayRequest(productId);
	}

	/**
	 * 创建Native支付(扫码支付)回调对象【模式一】
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
	 *            附加数据 非必填
	 * @return Native回调对象
	 * @see PayApi
	 * @see NativePayResponse
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1">扫码支付
	 *      </a>
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4">模式一
	 *      </a>
	 * @throws WeixinException
	 */
	public NativePayResponse createNativePayResponse(String productId,
                                                     String body, String outTradeNo, double totalFee, String notifyUrl,
                                                     String createIp, String attach) throws WeixinException {
		return payApi.createNativePayResponse(productId, body, outTradeNo,
				totalFee, notifyUrl, createIp, attach);
	}

	/**
	 * 创建Native支付(扫码支付)链接【模式二】
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
	 *            附加数据 非必填
	 * @return Native支付对象
	 * @see PayApi
	 * @see NATIVEPayRequest
	 * @see MchPayRequest#toRequestString()
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1">扫码支付
	 *      </a>
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5">模式二
	 *      </a>
	 * @throws WeixinException
	 */
	public MchPayRequest createNativePayRequest(String productId, String body,
                                                String outTradeNo, double totalFee, String notifyUrl,
                                                String createIp, String attach) throws WeixinException {
		return payApi.createNativePayRequest(productId, body, outTradeNo,
				totalFee, notifyUrl, createIp, attach);
	}

	/**
	 * 创建APP支付请求对象
	 *
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
	 *            附加数据 非必填
	 * @param store
	 *            门店信息 非必填
	 * @return APP支付对象
	 * @see PayApi
	 * @see SceneInfoStore
	 * @see APPPayRequest
	 * @see MchPayRequest#toRequestString()
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1">
	 *      APP支付</a>
	 * @throws WeixinException
	 */
	public MchPayRequest createAppPayRequest(String body, String outTradeNo,
                                             double totalFee, String notifyUrl, String createIp, String attach,
                                             SceneInfoStore store) throws WeixinException {
		return payApi.createAppPayRequest(body, outTradeNo, totalFee,
				notifyUrl, createIp, attach, store);
	}

	/**
	 * 创建WAP支付请求对象
	 *
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
	 *            附加数据 非必填
	 * @param app
	 *            应用信息
	 * @return WAP支付对象
	 * @see PayApi
	 * @see SceneInfoApp
	 * @see WAPPayRequest
	 * @see MchPayRequest#toRequestString()
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/wap.php?chapter=15_1">WAP支付
	 *      </a>
	 * @throws WeixinException
	 */
	public MchPayRequest createWapPayRequest(String body, String outTradeNo,
                                             double totalFee, String notifyUrl, String createIp, String attach,
                                             SceneInfoApp app) throws WeixinException {
		return payApi.createWapPayRequest(body, outTradeNo, totalFee,
				notifyUrl, createIp, attach, app);
	}

	/**
	 * 提交被扫支付
	 *
	 * @param authCode
	 *            扫码支付授权码 ,设备读取用户微信中的条码或者二维码信息
	 * @param body
	 *            商品描述
	 * @param outTradeNo
	 *            商户内部唯一订单号
	 * @param totalFee
	 *            商品总额 单位元
	 * @param createIp
	 *            订单生成的机器 IP
	 * @param attach
	 *            附加数据 非必填
	 * @param store
	 *            门店信息 非必填
	 * @return 支付的订单信息
	 * @see PayApi
	 * @see Order
	 * @see SceneInfoStore
	 * @see MICROPayRequest
	 * @see MchPayRequest#toRequestString()
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10">
	 *      提交被扫支付API</a>
	 * @throws WeixinException
	 */
	public MchPayRequest createMicroPayRequest(String authCode, String body,
                                               String outTradeNo, double totalFee, String createIp, String attach,
                                               SceneInfoStore store) throws WeixinException {
		return payApi.createMicroPayRequest(authCode, body, outTradeNo,
				totalFee, createIp, attach, store);
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
	 * @see Order
	 * @see PayApi
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2">
	 *      订单查询API</a>
	 * @return 订单详情
	 * @throws WeixinException
	 */
	public Order queryOrder(IdQuery idQuery) throws WeixinException {
		return payApi.queryOrder(idQuery);
	}

	/**
	 * 申请退款
	 *
	 * @see PayApi#applyRefund(IdQuery, String, double, double, CurrencyType, String, String, RefundAccountType)
	 */
	public RefundResult applyRefund(IdQuery idQuery, String outRefundNo,
                                    double totalFee, double refundFee, CurrencyType refundFeeType,
                                    String opUserId, String refundDesc,
                                    RefundAccountType refundAccountType) throws WeixinException {
		return payApi.applyRefund(idQuery, outRefundNo, totalFee, refundFee,
				refundFeeType, opUserId, refundDesc, refundAccountType);
	}

	/**
	 * 申请退款
	 *
	 * @see PayApi#applyRefund(IdQuery, String, double)
	 */
	public RefundResult applyRefund(IdQuery idQuery, String outRefundNo,
                                    double totalFee) throws WeixinException {
		return payApi.applyRefund(idQuery, outRefundNo, totalFee);
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
	 * @see PayApi
	 * @see RefundRecord
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5">
	 *      退款查询API</a>
	 * @since V3
	 * @throws WeixinException
	 */
	public RefundRecord queryRefund(IdQuery idQuery) throws WeixinException {
		return payApi.queryRefund(idQuery);
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
	 * @para outputStream 输出流
	 * @param tarType
	 *            非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
	 * @since V2 & V3
	 * @see PayApi
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">
	 *      下载对账单API</a>
	 * @throws WeixinException
	 */
	public void downloadBill(Date billDate, BillType billType,
			OutputStream outputStream, TarType tarType) throws WeixinException {
		payApi.downloadBill(billDate, billType, outputStream, tarType);
	}

	/**
	 * 冲正订单(需要证书)</br> 当支付返回失败,或收银系统超时需要取消交易,可以调用该接口</br> 接口逻辑:支
	 * 付失败的关单,支付成功的撤销支付</br> <font color="red">7天以内的单可撤销,其他正常支付的单
	 * 如需实现相同功能请调用退款接口</font></br> <font
	 * color="red">调用扣款接口后请勿立即调用撤销,需要等待5秒以上。先调用查单接口,如果没有确切的返回,再调用撤销</font> </br>
	 *
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 撤销结果
	 * @see PayApi
	 * @since V3
	 * @throws WeixinException
	 */
	public MerchantResult reverseOrder(IdQuery idQuery) throws WeixinException {
		return payApi.reverseOrder(idQuery);
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
	 * @see PayApi
	 * @since V3
	 * @throws WeixinException
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3">
	 *      关闭订单API</a>
	 */
	public MerchantResult closeOrder(String outTradeNo) throws WeixinException {
		return payApi.closeOrder(outTradeNo);
	}

	/**
	 * native支付URL转短链接:用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)，减小二维码数据量
	 * ，提升扫描速度和精确度。
	 *
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @see PayApi
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_9">
	 *      转换短链接API</a>
	 * @since V3
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
	 * @see PayApi
	 * @see <a href=
	 *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_8">
	 *      接口测试上报API</a>
	 * @throws WeixinException
	 */
	public XmlResult reportInterface(String interfaceUrl, int executeTime,
			String outTradeNo, String ip, Date time, XmlResult returnXml)
			throws WeixinException {
		return payApi.reportInterface(interfaceUrl, executeTime, outTradeNo,
				ip, time, returnXml);
	}

	/**
	 * 发放代金券(需要证书)
	 *
	 * @param couponStockId
	 *            代金券批次id
	 * @param partnerTradeNo
	 *            商户发放凭据号（格式：商户id+日期+流水号），商户侧需保持唯一性
	 * @param openId
	 *            用户的openid
	 * @param opUserId
	 *            操作员帐号, 默认为商户号 可在商户平台配置操作员对应的api权限 可为空
	 * @return 发放结果
	 * @see CouponApi
	 * @see CouponResult
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_3">
	 *      发放代金券接口</a>
	 * @throws WeixinException
	 */
	public CouponResult sendCoupon(String couponStockId, String partnerTradeNo,
                                   String openId, String opUserId) throws WeixinException {
		return couponApi.sendCoupon(couponStockId, partnerTradeNo, openId,
				opUserId);
	}

	/**
	 * 查询代金券批次
	 *
	 * @param couponStockId
	 *            代金券批次ID
	 * @return 代金券批次信息
	 * @see CouponApi
	 * @see CouponStock
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_4">
	 *      查询代金券批次信息接口</a>
	 * @throws WeixinException
	 */
	public CouponStock queryCouponStock(String couponStockId)
			throws WeixinException {
		return couponApi.queryCouponStock(couponStockId);
	}

	/**
	 * 查询代金券详细
	 *
	 * @param openId
	 *            用户ID
	 * @param couponId
	 *            代金券ID
	 * @param stockId
	 *            代金劵对应的批次号
	 * @return 代金券详细信息
	 * @see CouponApi
	 * @see CouponDetail
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_5">
	 *      查询代金券详细信息接口</a>
	 * @throws WeixinException
	 */
	public CouponDetail queryCouponDetail(String openId, String couponId,
                                          String stockId) throws WeixinException {
		return couponApi.queryCouponDetail(openId, couponId, stockId);
	}

	/**
	 * 发放红包 企业向微信用户个人发现金红包
	 *
	 * @param redpacket
	 *            红包信息
	 * @return 发放结果
	 * @see CashApi
	 * @see Redpacket
	 * @see RedpacketSendResult
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
		return cashApi.sendRedpack(redpacket);
	}

	/**
	 * 批量发放红包 企业向微信用户个人发现金红包
	 *
	 * @param redpacket
	 *            多个红包信息
	 * @return 发放结果
	 * @see CashApi
	 * @see #sendRedpacks(Redpacket...)
	 * @throws WeixinException
	 */
	public List<Future<RedpacketSendResult>> sendRedpacks(
			Redpacket... redpackets) {
		return cashApi.sendRedpacks(redpackets);
	}

	/**
	 * 查询红包记录
	 *
	 * @param outTradeNo
	 *            商户发放红包的商户订单号
	 * @return 红包记录
	 * @see CashApi
	 * @see RedpacketRecord
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
		return cashApi.queryRedpack(outTradeNo);
	}

	/**
	 * 企业付款
	 *
	 * @see CashApi#sendCorpPayment(CorpPayment)
	 */
	public CorpPaymentResult sendCorpPayment(CorpPayment payment)
			throws WeixinException {
		return cashApi.sendCorpPayment(payment);
	}

	/**
	 * 企业付款查询 用于商户的企业付款操作进行结果查询，返回付款操作详细结果
	 *
	 * @param outTradeNo
	 *            商户调用企业付款API时使用的商户订单号
	 * @return 付款记录
	 * @see CashApi
	 * @see CorpPaymentRecord
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3">
	 *      企业付款查询接口</a>
	 * @throws WeixinException
	 */
	public CorpPaymentRecord queryCorpPayment(String outTradeNo)
			throws WeixinException {
		return cashApi.queryCorpPayment(outTradeNo);
	}

	/**
	 * 授权码查询OPENID
	 *
	 * @param authCode
	 *            扫码支付授权码，设备读取用户微信中的条码或者二维码信息
	 * @return 查询结果
	 * @see CashApi
	 * @see OpenIdResult
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_13&index=9">
	 *      授权码查询OPENID</a>
	 * @throws WeixinException
	 */
	public OpenIdResult authCode2openId(String authCode) throws WeixinException {
		return payApi.authCode2openId(authCode);
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
	 * @see CashApi
	 * @see SettlementRecord
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/external/micropay.php?chapter=9_14&index=7">
	 *      查询结算资金接口</a>
	 */
	public SettlementRecord querySettlement(boolean status, Pageable pageable,
                                            Date start, Date end) throws WeixinException {
		return cashApi.querySettlement(status, pageable, start, end);
	}

	/**
	 * 查询汇率
	 *
	 * @param currencyType
	 *            外币币种
	 * @param date
	 *            日期 不填则默认当天
	 * @return 汇率对象
	 * @throws WeixinException
	 * @see CashApi
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/external/micropay.php?chapter=9_15&index=8">
	 *      查询汇率接口</a>
	 */
	public double queryExchageRate(CurrencyType currencyType, Date date)
			throws WeixinException {
		return cashApi.queryExchageRate(currencyType, date);
	}

	/**
	 * 订单附加信息提交
	 *
	 * @param customsOrder
	 *            附加订单信息
	 * @return 报关结果
	 * @see CustomsApi
	 * @see CustomsOrder
	 * @see CustomsOrderResult
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/external/declarecustom.php?chapter=18_1">
	 *      附加订单信息提交接口</a>
	 * @throws WeixinException
	 */
	public CustomsOrderResult declareCustomsOrder(CustomsOrder customsOrder)
			throws WeixinException {
		return customsApi.declareCustomsOrder(customsOrder);
	}

	/**
	 * 订单附加信息查询
	 *
	 * @param idQuery
	 *            out_trade_no,transaction_id,sub_order_no,sub_order_id四选一
	 * @param customsCity
	 *            海关
	 * @return 报关记录
	 * @see CustomsOrderRecord
	 * @see CustomsApi
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/external/declarecustom.php?chapter=18_1">
	 *      附加订单信息查询接口</a>
	 * @throws WeixinException
	 */
	public CustomsOrderRecord queryCustomsOrder(IdQuery idQuery,
                                                CustomsCity customsCity) throws WeixinException {
		return customsApi.queryCustomsOrder(idQuery, customsCity);
	}

	public final static String VERSION = Consts.VERSION;
}