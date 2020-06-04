package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;

/**
 * 微信支付-服务商分帐接口-分帐接收方API返回结果
 *
 * @author kit(kit_21cn@21cn.com)
 * @className ProfitSharingReceiverResult
 * @date 2020年05月20日
 * @since weixin4j-pay 1.1.0
 */
public class ReceiverResult extends MerchantResult {
    /**
     * 分账接收方对象，json格式字符串
     */
    @JSONField(serialize = false)
    private String receiver;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 返回接收方java对象
     */
    @JSONField(name = "receiver")
    public Receiver getReceiverObject(){
        try {
            return JSON.parseObject(receiver, Receiver.class);
        } catch (Exception e) {
            return null;
        }
    }
}
