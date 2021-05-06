package com.foxinmy.weixin4j.pay;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.pay.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.pay.payment.mch.SceneInfo;
import com.foxinmy.weixin4j.pay.payment.mch.SceneInfoApp;
import com.foxinmy.weixin4j.pay.payment.mch.SceneInfoStore;
import com.foxinmy.weixin4j.pay.type.TradeType;

import java.util.Date;

/**
 * MchPayPackage生成器
 *
 * 微信支付中很多新增的支付产品或功能不定期的在原来各种支付API中添加参数项，导致MchPayPackage类会不断更新
 * MchPayPackage构造方法参数会越来越多，不断增加的参数项也不好继续改构造方法。
 * PayApi中一些特定的支付api（如JSAPI、MACROPAY）等都是直接传入参数，然后在API内构造MchPayPackage，而不是传入MchPayPackage，
 * 一旦增加新参数就需要改API，影响正在使用SDK的工程，但如果改为直接使用MchPayPackag的createPayRequest方法，
 * MchPayPackage的构造方式又太难看，开发者需要对着微信文档然后看着构造函数里边一大堆的参数一一匹配
 * 所以最终有了PayPackageBuilder这个类，通过一些链式的API构造MchPayPackage，既提供最小参数的各种支付构造方法，又能让代码看上去直观一些
 *
 * @author kit (kit.li@qq.com)
 * @date 2020年06月02日
 */
public class PayPackageBuilder {
    private static final String Y = "Y";
    private MchPayPackage mchPayPackage;

    private PayPackageBuilder(){
    }

    /**
     * 使用MchPayPackage初始化
     *
     * @param payPackage
     * @return
     */
    public static PayPackageBuilder init(MchPayPackage payPackage){
        PayPackageBuilder instance = new PayPackageBuilder();
        instance.mchPayPackage = payPackage;
        return instance;
    }

    /**
     * 付款码支付/人脸支付
     *
     * @param body
     *          商品描述
     * @param outTradeNo
     *          商户订单号
     * @param totalFee
     *          支付金额
     * @param createIp
     *          终端IP
     * @param authCode
     *          用户付款码
     * @return
     */
    public static PayPackageBuilder microPay(String body, String outTradeNo, double totalFee, String createIp,
                                             String authCode){
        PayPackageBuilder instance = new PayPackageBuilder();
        instance.mchPayPackage = new MchPayPackage(body, outTradeNo, totalFee, null, createIp, TradeType.MICROPAY,
                null, authCode, null, null);
        return instance;
    }

    /**
     * JSAPI支付/小程序支付
     *
     * @param body
     *          商品描述
     * @param outTradeNo
     *          商户订单号
     * @param totalFee
     *          支付金额
     * @param createIp
     *          终端IP
     * @param notifyUrl
     *          回调通知地址
     * @param openid
     *          用户标识
     * @return
     */
    public static PayPackageBuilder jsapiPay(String body, String outTradeNo, double totalFee, String createIp,
                                             String notifyUrl, String openid){
        PayPackageBuilder instance = new PayPackageBuilder();
        instance.mchPayPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.JSAPI,
                openid, null, null, null);
        return instance;
    }

    /**
     * native支付
     *
     * @param body
     *          商品描述
     * @param outTradeNo
     *          商户订单号
     * @param totalFee
     *          支付金额
     * @param createIp
     *          终端IP
     * @param notifyUrl
     *          回调通知地址
     * @param productId
     *          产品ID
     * @return
     */
    public static PayPackageBuilder nativePay(String body, String outTradeNo, double totalFee, String createIp,
                                             String notifyUrl, String productId){
        PayPackageBuilder instance = new PayPackageBuilder();
        instance.mchPayPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.NATIVE,
                null, null, productId, null);
        return instance;
    }

    /**
     * APP支付
     *
     * @param body
     *          商品描述
     * @param outTradeNo
     *          商户订单号
     * @param totalFee
     *          支付金额
     * @param createIp
     *          终端IP
     * @param notifyUrl
     *          回调通知地址
     * @return
     */
    public static PayPackageBuilder appPay(String body, String outTradeNo, double totalFee, String createIp, String notifyUrl){
        PayPackageBuilder instance = new PayPackageBuilder();
        instance.mchPayPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.APP,
                null, null, null, null);
        return instance;
    }

    /**
     * H5支付
     *
     * @param body
     *          商品描述
     * @param outTradeNo
     *          商户订单号
     * @param totalFee
     *          支付金额
     * @param createIp
     *          终端IP
     * @param notifyUrl
     *          回调通知地址
     * @param wapUrl
     *          wap网站URL地址
     * @param wapName
     *          wap网站名
     * @return
     */
    public static PayPackageBuilder h5Pay(String body, String outTradeNo, double totalFee, String createIp,
                                          String notifyUrl, String wapUrl, String wapName){
        PayPackageBuilder instance = new PayPackageBuilder();
        instance.mchPayPackage = new MchPayPackage(body, outTradeNo, totalFee, notifyUrl, createIp, TradeType.MWEB,
                null, null, null, null);
        SceneInfoApp app = SceneInfoApp.createWapAPP(wapName, wapUrl);
        instance.mchPayPackage.setSceneInfo(String.format("{\"h5_info\":\"%s\"}", app.getSceneInfo()));
        return instance;
    }

    public PayPackageBuilder detail(String detail){
        this.mchPayPackage.setDetail(detail);
        return this;
    }

    public PayPackageBuilder attach(String attach){
        this.mchPayPackage.setAttach(attach);
        return this;
    }

    public PayPackageBuilder goodsTag(String goodsTag){
        this.mchPayPackage.setGoodsTag(goodsTag);
        return this;
    }

    public PayPackageBuilder limitPay(){
        this.mchPayPackage.setLimitPay("no_credit");
        return this;
    }

    public PayPackageBuilder timeStart(Date date){
        this.mchPayPackage.setTimeStart(date);
        return this;
    }

    public PayPackageBuilder timeStart(String date){
        this.mchPayPackage.setTimeStart(date);
        return this;
    }

    public PayPackageBuilder timeExpire(Date date){
        this.mchPayPackage.setTimeExpire(date);
        return this;
    }

    public PayPackageBuilder timeExpire(String date){
        this.mchPayPackage.setTimeExpire(date);
        return this;
    }

    public PayPackageBuilder receipt(){
        this.mchPayPackage.setReceipt(Y);
        return this;
    }

    public PayPackageBuilder sceneInfo(SceneInfo info){
        this.mchPayPackage.setSceneInfo(info.toJson());
        return this;
    }

    public PayPackageBuilder deposit(){
        this.mchPayPackage.setDeposit(Y);
        return this;
    }

    public PayPackageBuilder profitSharing(){
        this.mchPayPackage.setProfitSharing(Y);
        return this;
    }

    public PayPackageBuilder subOpenId(String subOpenId){
        this.mchPayPackage.setSubOpenId(subOpenId);
        return this;
    }

    public PayPackageBuilder totalFee(double totalFee){
        this.mchPayPackage.setTotalFee(totalFee);
        return this;
    }

    public PayPackageBuilder totalFee(int totalFee){
        this.mchPayPackage.setTotalFee(totalFee);
        return this;
    }

    public MchPayPackage build(){
        return this.mchPayPackage;
    }
}
