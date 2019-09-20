package com.foxinmy.weixin4j.pay.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.pay.WeixinPayProxy;
import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.payment.mch.MchPayRequest;
import org.junit.Test;

public class TestFacePay {

    @Test
    public void test() throws WeixinException {
        String appid = "";
        String mchid = "";
        String paySignKey = "";
        WeixinPayAccount payAccount = new WeixinPayAccount(appid, paySignKey, mchid);
        WeixinPayProxy proxy = new WeixinPayProxy(payAccount);

        String orderNo = "TESTORDER2019092001";
        String openId = "oguJRswolIOGg7Vd1VaqGJuDBFAE";
        String faceCode = "0f879a6c-5fff-421c-a233-5fac0f4aad12";

        MchPayRequest rsp = proxy.createFacePayRequest(faceCode, "测试的人脸支付",
                orderNo, 1,
                "127.0.0.1", openId, null);

        JSONObject obj = (JSONObject) JSON.toJSON(rsp);
    }
}
