package com.foxinmy.weixin4j.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.mch.APPPayRequest;
import com.foxinmy.weixin4j.payment.mch.JSAPIPayRequest;
import com.foxinmy.weixin4j.payment.mch.MICROPayRequest;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.payment.mch.MerchantResult;
import com.foxinmy.weixin4j.payment.mch.NATIVEPayRequest;
import com.foxinmy.weixin4j.payment.mch.NativePayResponse;
import com.foxinmy.weixin4j.payment.mch.OpenIdResult;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.payment.mch.PrePay;
import com.foxinmy.weixin4j.payment.mch.RefundRecord;
import com.foxinmy.weixin4j.payment.mch.RefundResult;
import com.foxinmy.weixin4j.payment.mch.WAPPayRequest;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.type.TarType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.type.mch.BillType;
import com.foxinmy.weixin4j.type.mch.RefundAccountType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 支付API
 *
 * @className PayApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月28日
 * @since JDK 1.6
 */
public class PayApi extends MchApi {

    public PayApi(WeixinPayAccount weixinAccount) {
        super(weixinAccount);
    }

    /**
     * 统一下单接口</br>
     * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI
     * 、APP等不同场景生成交易串调起支付。
     *
     * @param payPackage
     *            包含订单信息的对象
     * @see com.foxinmy.weixin4j.payment.mch.MchPayPackage
     * @see com.foxinmy.weixin4j.payment.mch.PrePay
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">统一下单接口
     *      </a>
     * @return 预支付对象
     */
    public PrePay createPrePay(MchPayPackage payPackage) throws WeixinException {
        super.declareMerchant(payPackage);
        payPackage.setSign(weixinSignature.sign(payPackage));
        String payJsRequestXml = XmlStream.toXML(payPackage);
        WeixinResponse response = weixinExecutor.post(getRequestUri("order_create_uri"), payJsRequestXml);
        return response.getAsObject(new TypeReference<PrePay>() {
        });
    }

    /**
     * 创建支付请求对象
     *
     * @param payPackage
     *            支付详情
     * @return 支付请求对象
     * @see com.foxinmy.weixin4j.payment.mch.JSAPIPayRequest JS支付
     * @see com.foxinmy.weixin4j.payment.mch.NATIVEPayRequest 扫码支付
     * @see com.foxinmy.weixin4j.payment.mch.MICROPayRequest 刷卡支付
     * @see com.foxinmy.weixin4j.payment.mch.APPPayRequest APP支付
     * @see com.foxinmy.weixin4j.payment.mch.WAPPayRequest WAP支付
     * @throws WeixinException
     */
    public MchPayRequest createPayRequest(MchPayPackage payPackage) throws WeixinException {
        if (StringUtil.isBlank(payPackage.getTradeType())) {
            throw new WeixinException("tradeType not be empty");
        }
        String tradeType = payPackage.getTradeType().toUpperCase();
        if (TradeType.MICROPAY.name().equals(tradeType)) {
            MchPayPackage _payPackage = new MchPayPackage(payPackage.getBody(), payPackage.getDetail(),
                    payPackage.getOutTradeNo(), DateUtil.formatFee2Yuan(payPackage.getTotalFee()), null, null,
                    payPackage.getCreateIp(), null, null, payPackage.getAuthCode(), null, payPackage.getAttach(), null,
                    null, payPackage.getGoodsTag(), payPackage.getLimitPay(), payPackage.getSubAppId());
            super.declareMerchant(_payPackage);
            _payPackage.setSign(weixinSignature.sign(_payPackage));
            String para = XmlStream.toXML(_payPackage);
            WeixinResponse response = weixinExecutor.post(getRequestUri("micropay_uri"), para);
            MICROPayRequest microPayRequest = response.getAsObject(new TypeReference<MICROPayRequest>() {
            });
            microPayRequest.setPaymentAccount(weixinAccount);
            return microPayRequest;
        }
        PrePay prePay = createPrePay(payPackage);
        if (TradeType.APP.name().equals(tradeType)) {
            return new APPPayRequest(prePay.getPrepayId(), weixinAccount);
        } else if (TradeType.JSAPI.name().equals(tradeType)) {
            return new JSAPIPayRequest(prePay.getPrepayId(), weixinAccount);
        } else if (TradeType.NATIVE.name().equals(tradeType)) {
            return new NATIVEPayRequest(prePay.getPrepayId(), prePay.getPayUrl(), weixinAccount);
        } else if (TradeType.MWEB.name().equals(tradeType)) {
            return new WAPPayRequest(prePay.getPrepayId(), prePay.getPayUrl(), weixinAccount);
        } else {
            throw new WeixinException("unknown tradeType:" + tradeType);
        }
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
     * @see com.foxinmy.weixin4j.payment.mch.JSAPIPayRequest
     * @return JSAPI支付对象
     * @throws WeixinException
     */
    public MchPayRequest createJSPayRequest(String openId, String body, String outTradeNo, double totalFee,
            String notifyUrl, String createIp, String attach) throws WeixinException {
        MchPayPackage payPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.JSAPI,
                openId, null, null, attach);
        return createPayRequest(payPackage);
    }

    /**
     * <p>
     * 生成编辑地址请求
     * </p>
     *
     * err_msg edit_address:ok获取编辑收货地址成功</br>
     * edit_address:fail获取编辑收货地址失败</br>
     * userName 收货人姓名</br>
     * telNumber 收货人电话</br>
     * addressPostalCode 邮编</br>
     * proviceFirstStageName 国标收货地址第一级地址</br>
     * addressCitySecondStageName 国标收货地址第二级地址</br>
     * addressCountiesThirdStageName 国标收货地址第三级地址</br>
     * addressDetailInfo 详细收货地址信息</br>
     * nationalCode 收货地址国家码</br>
     *
     * @param url
     *            当前访问页的URL
     * @param oauthToken
     *            oauth授权时产生的token
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_8&index=7">
     *      收货地址共享</a>
     * @return 编辑地址请求JSON串
     */
    public String createAddressRequestJSON(String url, String oauthToken) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", weixinAccount.getId());
        map.put("timeStamp", DateUtil.timestamp2string());
        map.put("nonceStr", RandomUtil.generateString(16));
        map.put("url", url);
        map.put("accessToken", oauthToken);
        String sign = DigestUtil.SHA1(MapUtil.toJoinString(map, false, true));
        map.remove("url");
        map.remove("accessToken");
        map.put("scope", "jsapi_address");
        map.put("signType", SignType.SHA1.name().toLowerCase());
        map.put("addrSign", sign);
        return JSON.toJSONString(map);
    }

    /**
     * 创建Native支付(扫码支付)链接【模式一】
     *
     * @param productId
     *            与订单ID等价
     * @return 支付链接
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1">扫码支付
     *      </a>
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4">模式一
     *      </a>
     */
    public String createNativePayRequest(String productId) {
        Map<String, String> map = new HashMap<String, String>();
        String timestamp = DateUtil.timestamp2string();
        String noncestr = RandomUtil.generateString(16);
        map.put("appid", weixinAccount.getId());
        map.put("mch_id", weixinAccount.getMchId());
        map.put("time_stamp", timestamp);
        map.put("nonce_str", noncestr);
        map.put("product_id", productId);
        String sign = weixinSignature.sign(map);
        return String.format(getRequestUri("native_pay_uri"), sign, weixinAccount.getId(), weixinAccount.getMchId(),
                productId, timestamp, noncestr);
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
     * @see com.foxinmy.weixin4j.payment.mch.NativePayResponse
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1">扫码支付
     *      </a>
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4">模式一
     *      </a>
     * @throws WeixinException
     */
    public NativePayResponse createNativePayResponse(String productId, String body, String outTradeNo, double totalFee,
            String notifyUrl, String createIp, String attach) throws WeixinException {
        MchPayPackage payPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.NATIVE,
                null, null, productId, attach);
        PrePay prePay = createPrePay(payPackage);
        return new NativePayResponse(weixinAccount, prePay.getPrepayId());
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
     * @see com.foxinmy.weixin4j.payment.mch.NATIVEPayRequest
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1">扫码支付
     *      </a>
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5">模式二
     *      </a>
     * @throws WeixinException
     */
    public MchPayRequest createNativePayRequest(String productId, String body, String outTradeNo, double totalFee,
            String notifyUrl, String createIp, String attach) throws WeixinException {
        MchPayPackage payPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.NATIVE,
                null, null, productId, attach);
        return createPayRequest(payPackage);
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
     * @return APP支付对象
     * @see com.foxinmy.weixin4j.payment.mch.APPPayRequest
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1">
     *      APP支付</a>
     * @throws WeixinException
     */
    public MchPayRequest createAppPayRequest(String body, String outTradeNo, double totalFee, String notifyUrl,
            String createIp, String attach) throws WeixinException {
        MchPayPackage payPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.APP,
                null, null, null, attach);
        return createPayRequest(payPackage);
    }

    /**
     * 创建WAP支付请求对象：正常流程用户支付完成后会返回至发起支付的页面，如需返回至指定页面，
     * 则可以在MWEB_URL后拼接上redirect_url参数，来指定回调页面
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
     * @return WAP支付对象
     * @see com.foxinmy.weixin4j.payment.mch.WAPPayRequest
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/wap.php?chapter=15_1">WAP支付
     *      </a>
     * @throws WeixinException
     */
    public MchPayRequest createWapPayRequest(String body, String outTradeNo, double totalFee, String notifyUrl,
            String createIp, String attach) throws WeixinException {
        MchPayPackage payPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.MWEB,
                null, null, null, attach);
        return createPayRequest(payPackage);
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
     * @return 支付的订单信息
     * @see com.foxinmy.weixin4j.payment.mch.MICROPayRequest
     * @see com.foxinmy.weixin4j.payment.mch.Order
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10">
     *      提交被扫支付API</a>
     * @throws WeixinException
     */
    public MchPayRequest createMicroPayRequest(String authCode, String body, String outTradeNo, double totalFee,
            String createIp, String attach) throws WeixinException {
        MchPayPackage payPackage = new MchPayPackage(body, outTradeNo, totalFee, null, createIp, TradeType.MICROPAY,
                null, authCode, null, attach);
        return createPayRequest(payPackage);
    }

    /**
     * 订单查询
     * <p>
     * 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；</br>
     * 调用支付接口后，返回系统错误或未知交易状态情况；</br>
     * 调用被扫支付API，返回USERPAYING的状态；</br>
     * 调用关单或撤销接口API之前，需确认支付状态；
     * </P>
     *
     * @param idQuery
     *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
     *            transaction_id> out_trade_no
     * @return 订单信息
     * @see com.foxinmy.weixin4j.payment.mch.Order
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2">
     *      订单查询API</a>
     * @since V3
     * @throws WeixinException
     */
    public Order queryOrder(IdQuery idQuery) throws WeixinException {
        Map<String, String> map = createBaseRequestMap(idQuery);
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = weixinExecutor.post(getRequestUri("order_query_uri"), param);
        return ListsuffixResultDeserializer.deserialize(response.getAsString(), Order.class);
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
     * @param refundDesc
     *            退款原因，若商户传入，会在下发给用户的退款消息中体现退款原因
     * @param refundAccountType
     *            退款资金来源,默认使用未结算资金退款：REFUND_SOURCE_UNSETTLED_FUNDS
     * @return 退款申请结果
     * @see com.foxinmy.weixin4j.payment.mch.RefundResult
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4">
     *      申请退款API</a>
     * @since V3
     * @throws WeixinException
     */
    public RefundResult applyRefund(IdQuery idQuery, String outRefundNo, double totalFee, double refundFee,
            CurrencyType refundFeeType, String opUserId, String refundDesc, RefundAccountType refundAccountType)
            throws WeixinException {
        Map<String, String> map = createBaseRequestMap(idQuery);
        map.put("out_refund_no", outRefundNo);
        map.put("total_fee", Integer.toString(DateUtil.formatYuan2Fen(totalFee)));
        map.put("refund_fee", Integer.toString(DateUtil.formatYuan2Fen(refundFee)));
        if (StringUtil.isBlank(opUserId)) {
            opUserId = weixinAccount.getMchId();
        }
        map.put("op_user_id", opUserId);
        if (refundFeeType == null) {
            refundFeeType = CurrencyType.CNY;
        }
        if (refundAccountType == null) {
            refundAccountType = RefundAccountType.REFUND_SOURCE_UNSETTLED_FUNDS;
        }
        if (StringUtil.isNotBlank(refundDesc)) {
            map.put("refund_desc", refundDesc);
        }
        map.put("refund_fee_type", refundFeeType.name());
        map.put("refund_account", refundAccountType.name());
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = getWeixinSSLExecutor().post(getRequestUri("refund_apply_uri"), param);
        return response.getAsObject(new TypeReference<RefundResult>() {
        });
    }

    /**
     * 退款申请(全额退款)
     *
     * @param idQuery
     *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
     *            transaction_id> out_trade_no
     * @param outRefundNo
     *            商户系统内部的退款单号,商 户系统内部唯一,同一退款单号多次请求只退一笔
     * @param totalFee
     *            订单总金额,单位为元
     * @see {@link #applyRefund(IdQuery, String, double, double, CurrencyType, String, String, RefundAccountType)}
     */
    public RefundResult applyRefund(IdQuery idQuery, String outRefundNo, double totalFee) throws WeixinException {
        return applyRefund(idQuery, outRefundNo, totalFee, totalFee, null, null, null, null);
    }

    /**
     * 冲正订单(需要证书)</br>
     * 当支付返回失败,或收银系统超时需要取消交易,可以调用该接口</br>
     * 接口逻辑:支 付失败的关单,支付成功的撤销支付</br>
     * <font color="red">7天以内的单可撤销,其他正常支付的单 如需实现相同功能请调用退款接口</font></br>
     * <font color="red">调用扣款接口后请勿立即调用撤销,需要等待5秒以上。先调用查单接口,如果没有确切的返回,再调用撤销</font>
     * </br>
     *
     * @param idQuery
     *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
     *            transaction_id> out_trade_no
     * @return 撤销结果
     * @since V3
     * @throws WeixinException
     */
    public MerchantResult reverseOrder(IdQuery idQuery) throws WeixinException {
        Map<String, String> map = createBaseRequestMap(idQuery);
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = getWeixinSSLExecutor().post(getRequestUri("order_reverse_uri"), param);
        return response.getAsObject(new TypeReference<MerchantResult>() {
        });
    }

    /**
     * native支付URL转短链接：用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)，减小二维码数据量
     * ，提升扫描速度和精确度。
     *
     * @param url
     *            具有native标识的支付URL
     * @return 转换后的短链接
     * @throws WeixinException
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_9">
     *      转换短链接API</a>
     */
    public String getShorturl(String url) throws WeixinException {
        Map<String, String> map = createBaseRequestMap(null);
        try {
            map.put("long_url", URLEncoder.encode(url, Consts.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            ;
        }
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = weixinExecutor.post(getRequestUri("longurl_convert_uri"), param);
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
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3">
     *      关闭订单API</a>
     */
    public MerchantResult closeOrder(String outTradeNo) throws WeixinException {
        Map<String, String> map = createBaseRequestMap(new IdQuery(outTradeNo, IdType.TRADENO));
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = weixinExecutor.post(getRequestUri("order_close_uri"), param);
        return response.getAsObject(new TypeReference<MerchantResult>() {
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
     * @param outputStream
     *            输出流
     * @param tarType
     *            非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * @since V3
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">
     *      下载对账单API</a>
     * @throws WeixinException
     */
    public void downloadBill(Date billDate, BillType billType, OutputStream outputStream, TarType tarType)
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
        Map<String, String> map = createBaseRequestMap(null);
        map.put("bill_date", formatBillDate);
        map.put("bill_type", billType.name());
        if (tarType != null) {
            map.put("tar_type", tarType.name());
        }
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = weixinExecutor.post(getRequestUri("downloadbill_uri"), param);

        if (TarType.GZIP == tarType) {
            try {
                IOUtil.copy(response.getBody(), outputStream);
            } catch (IOException e) {
                ;
            }
        } else {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(outputStream, Consts.UTF_8));
                reader = new BufferedReader(new InputStreamReader(response.getBody(), Consts.UTF_8));
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
        }
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
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5">
     *      退款查询API</a>
     * @since V3
     * @throws WeixinException
     */
    public RefundRecord queryRefund(IdQuery idQuery) throws WeixinException {
        Map<String, String> map = createBaseRequestMap(idQuery);
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = weixinExecutor.post(getRequestUri("refund_query_uri"), param);
        return ListsuffixResultDeserializer.deserialize(response.getAsString(), RefundRecord.class);
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
     * @see <a href=
     *      "http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_8">
     *      交易保障</a>
     */
    @SuppressWarnings("unchecked")
    public XmlResult reportInterface(String interfaceUrl, int executeTime, String outTradeNo, String ip, Date time,
            XmlResult returnXml) throws WeixinException {
        Map<String, String> map = createBaseRequestMap(null);
        map.put("interface_url", interfaceUrl);
        map.put("execute_time_", Integer.toString(executeTime));
        map.put("out_trade_no", outTradeNo);
        map.put("user_ip", ip);
        map.put("time", DateUtil.fortmat2yyyyMMddHHmmss(time));
        map.putAll((Map<String, String>) JSON.toJSON(returnXml));
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = weixinExecutor.post(getRequestUri("interface_report_uri"), param);
        return response.getAsXml();
    }

    /**
     * 授权码查询OPENID接口
     *
     * @param authCode
     *            扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     * @return 查询结果
     * @see com.foxinmy.weixin4j.payment.mch.OpenIdResult
     * @see <a href=
     *      "https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_13&index=9">
     *      授权码查询OPENID</a>
     * @throws WeixinException
     */
    public OpenIdResult authCode2openId(String authCode) throws WeixinException {
        Map<String, String> map = createBaseRequestMap(null);
        map.put("auth_code", authCode);
        map.put("sign", weixinSignature.sign(map));
        String param = XmlStream.map2xml(map);
        WeixinResponse response = weixinExecutor.post(getRequestUri("authcode_openid_uri"), param);
        return response.getAsObject(new TypeReference<OpenIdResult>() {
        });
    }
}
