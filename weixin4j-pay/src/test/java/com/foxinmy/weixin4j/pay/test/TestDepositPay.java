package com.foxinmy.weixin4j.pay.test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.pay.WeixinPayProxy;
import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.payment.mch.MchPayRequest;
import org.junit.Test;

public class TestDepositPay {
    @Test
    public void test() throws WeixinException {
        String appid = "";
        String mchid = "";
        String paySignKey = "";
        String code = "";
        boolean isFacePay = false; //true - 人脸押金支付，false - 付款码押金支付

        WeixinPayAccount payAccount = new WeixinPayAccount(appid, paySignKey, mchid);
        payAccount.setSubMchId(mchid);
        WeixinPayProxy proxy = new WeixinPayProxy(payAccount);

        MchPayRequest payRequest = proxy.createDepositPayRequest(code, "测试押金支付", "TESTORDER20190921001", 0.01,
                "127.0.0.1", null, null, null, isFacePay);
        System.out.println(payRequest.toRequestString());
    }
}
