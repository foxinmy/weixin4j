package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;

import javax.xml.bind.annotation.XmlElement;

/**
 * 分帐方添加及删除的API请求
 *
 * @author kit (kit.li@qq.com)
 * @date 2020年05月20日
 * @since weixin4j-pay 1.1.0
 */
public class ReceiverRequest extends MerchantResult {

    @XmlElement(name = "sign_type")
    @JSONField(name = "sign_type")
    private final String signType = "HMAC-SHA256";

    public ReceiverRequest(Receiver receiver){
        super();
        this.receiver = JSON.toJSONString(receiver);
    }

    private String receiver;

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String getSignType() {
        return signType;
    }
}
