package com.foxinmy.weixin4j.pay.test;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.pay.WeixinPayProxy;
import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.profitsharing.Receiver;
import com.foxinmy.weixin4j.pay.profitsharing.ReceiverResult;
import com.foxinmy.weixin4j.pay.type.profitsharing.ReceiverType;
import com.foxinmy.weixin4j.pay.type.profitsharing.RelationType;
import org.junit.Assert;
import org.junit.Test;

public class TestProfitSharingApi {
    // 微信公众号appid
    private static String APPID = "wx8208082617097c70";
    // 微信支付商户密钥
    private static String PAY_SIGN_KEY = "musegogogomusegogogomusegogogo16";
    // 商户号
    private static String MCHID = "1319061501";
    //
    private static String OPENID = "oIkWcwtHXPp32ISw_Ept3FUQ1DM4";

    /**
     * 测试添加分帐方
     */
    // @Test
    public void testAddReceiver() throws WeixinException {
        Receiver receiver = new Receiver(ReceiverType.PERSONAL_OPENID, OPENID, RelationType.STAFF);
        WeixinPayAccount account = new WeixinPayAccount(APPID, PAY_SIGN_KEY, MCHID);
        WeixinPayProxy proxy = new WeixinPayProxy(account);
        ReceiverResult result = proxy.addProfitSharingReceiver(receiver);
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getReturnCode(), "SUCCESS");
    }

    /**
     * 测试删除分帐方
     */
    //@Test
    public void testRemoveReceiver() throws WeixinException {
        Receiver receiver = new Receiver(ReceiverType.PERSONAL_OPENID, OPENID);
        WeixinPayAccount account = new WeixinPayAccount(APPID, PAY_SIGN_KEY, MCHID);
        WeixinPayProxy proxy = new WeixinPayProxy(account);
        ReceiverResult result = proxy.removeProfitSharingReceiver(receiver);
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getReturnCode(), "SUCCESS");
    }

    public void testProfitPay() throws WeixinException {

    }
}
