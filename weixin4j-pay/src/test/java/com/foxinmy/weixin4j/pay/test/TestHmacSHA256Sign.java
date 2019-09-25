package com.foxinmy.weixin4j.pay.test;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.pay.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.pay.type.SignType;
import org.junit.Assert;
import org.junit.Test;

/**
 * HmacSHA256签名算法测试
 * @author kit (kit_21cn@21cn.com)
 */
public class TestHmacSHA256Sign {
    @Test
    public void test(){
        WeixinPaymentSignature signature = new WeixinPaymentSignature("muses");
        JSONObject json = new JSONObject();
        json.put("appid", "1");
        json.put("mch_id", "2");
        String sign = signature.sign(json, SignType.HMAC$SHA256);

        Assert.assertEquals("637CF27B23F731398B2BE0118F484191B3728749C25D1EEF7479B6E93033602C", sign);
    }
}
