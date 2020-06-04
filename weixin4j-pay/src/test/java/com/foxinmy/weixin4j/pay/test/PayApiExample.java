package com.foxinmy.weixin4j.pay.test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.pay.PayPackageBuilder;
import com.foxinmy.weixin4j.pay.WeixinPayProxy;
import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.payment.mch.MICROPayRequest;
import com.foxinmy.weixin4j.pay.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.pay.payment.mch.MchPayRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 一个支付例子
 *
 * @author kit (kit.li@qq.com)
 */
public class PayApiExample {

    /**
     * 这是一个付款码支付的例子
     *
     * @throws WeixinException
     */
    @Test
    public void testPay() throws WeixinException {
        WeixinPayAccount payAccount = new WeixinPayAccount("你的appid", "支付密钥", "商户号");
        MchPayPackage payPackage = PayPackageBuilder.microPay("body内容", "商户订单号", 1.00D, "127.0.0.1",
                "你的付款码")
                .attach("这是一个测试")
                .limitPay()
                .build();
        WeixinPayProxy proxy = new WeixinPayProxy(payAccount);
        MICROPayRequest r = (MICROPayRequest) proxy.createPayRequest(payPackage);
        Assert.assertEquals(r.getResultCode(), "SUCCESS");
    }
}
