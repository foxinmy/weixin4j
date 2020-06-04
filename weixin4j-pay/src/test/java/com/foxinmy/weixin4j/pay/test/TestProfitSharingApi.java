package com.foxinmy.weixin4j.pay.test;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.pay.WeixinPayProxy;
import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.profitsharing.*;
import com.foxinmy.weixin4j.pay.type.profitsharing.ReceiverType;
import com.foxinmy.weixin4j.pay.type.profitsharing.RelationType;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestProfitSharingApi {
    // 微信公众号appid
    private static String APPID = "";
    // 微信支付商户密钥
    private static String PAY_SIGN_KEY = "";
    // 商户号
    private static String MCHID = "";
    //
    private static String OPENID = "";

    /**
     * 测试添加分帐方
     */
    @Test
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
    @Test
    public void testRemoveReceiver() throws WeixinException {
        Receiver receiver = new Receiver(ReceiverType.PERSONAL_OPENID, OPENID, null);
        WeixinPayAccount account = new WeixinPayAccount(APPID, PAY_SIGN_KEY, MCHID);
        WeixinPayProxy proxy = new WeixinPayProxy(account);
        ReceiverResult result = proxy.removeProfitSharingReceiver(receiver);
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getReturnCode(), "SUCCESS");
    }

    /**
     * 单次分帐
     *
     * @throws WeixinException
     */
    @Test
    public void testProfitSharing() throws WeixinException {
        ReceiverProfit receiverProfit = new ReceiverProfit(ReceiverType.MERCHANT_ID, "", 1, "test");
        List<ReceiverProfit> list = new ArrayList<ReceiverProfit>();
        list.add(receiverProfit);
        WeixinPayAccount account = new WeixinPayAccount(APPID, PAY_SIGN_KEY, MCHID);
        WeixinPayProxy proxy = new WeixinPayProxy(account);
        ProfitSharingResult result = proxy.profitSharing("transactioId", "outOrderNo", list, false);
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getReturnCode(), "SUCCESS");
    }

}
