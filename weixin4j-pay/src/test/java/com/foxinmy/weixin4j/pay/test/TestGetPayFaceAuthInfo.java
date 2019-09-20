package com.foxinmy.weixin4j.pay.test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.pay.WeixinPayProxy;
import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.payment.face.PayfaceAuthinfo;
import org.junit.Assert;
import org.junit.Test;

public class TestGetPayFaceAuthInfo {
    @Test
    public void test() throws WeixinException {
        String appid = "";
        String mchid = "";
        String paySignKey = "";
        String rawData = "7rfwfRXEDBBbs5MjHW67ritEHtQ7AchIcd0E1zfBxmxyIEXA3aiPTdtrZ1I1fXn6Mpyln11al546YOfNK/HXe81b589a9EyAHuoziZmoe+qJFf2ulkwBWOFffnSN05Qy/ykboG4PciO5yvIMlKEEdmqEjj4ck9oBmAKCqXCM2bRDqNEQOuckZGqGsJsM4xyKEZmlL4cLk+/l9xtvOseJccBeDJfkg5fAdwG1z7lX1DThdyb8uRuE3UCvVghQ21KslyIrF9G6rhh3bP83sD//QklJsdf/dapidxkCACfZkGyNl7wFDnjj5bUgwj9pYW3gQBUff606pI9Eh7VoLwFb768DGfSGHVTwQOPeq+Ldu0bAFyuTNFZUBETYnOCZx0ue7ehDmfmFSxyqVfLRhmlMTPHC/AHRVp6wwg1EnQUTRpUltHdn4O3w5B0PRDVlXogdri0WaTDyrYo18GaZvUdCWld09NZboEPknWEgfQfwaF4vow6R4negvKCVna5kNbjDlDRWNaN+AKtHIznnPKWipc6UunBKeMw/kNTced2f73dUEDALGLPE41nnxA7y1uePWMXVJyNGnWxk461/nz6g/NfyJAgIOILrO1wThEhBd6tFAJGQwu366fnN/5eU9XfBzApHE+OIrtYyRLYJHVaAsAeXOy+PvVqMUFkEWJ3iSLJUZhLJYUbnqVEZaIeAvMY0NJ5+E26WvXXhIGr91gPq35aEjuPu4LKbGTw1jgM=";

        WeixinPayAccount payAccount = new WeixinPayAccount(appid, paySignKey, mchid);
        WeixinPayProxy proxy = new WeixinPayProxy(payAccount);
        PayfaceAuthinfo info = proxy.getWxPayfaceAuthinfo("TEST01", "甘坑客家小镇", "TESTDEVICE01", rawData);
        System.out.print(info);
        Assert.assertEquals("SUCCESS", info.getReturnCode());
    }
}
