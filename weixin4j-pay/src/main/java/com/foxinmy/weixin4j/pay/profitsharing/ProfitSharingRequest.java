package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * 单次分帐的请求内容
 *
 * @author kit(kit.li@qq.com)
 * @date 2020年5月25日
 * @since weixin4j-pay 1.1.0
 */
public class ProfitSharingRequest extends MerchantResult {
    /**
     * 只支持HMAC-SHA256
     */
    @XmlElement(name = "sign_type")
    @JSONField(name = "sign_type")
    private final String signType = "HMAC-SHA256";
    /**
     * 微信支付订单号
     */
    @XmlElement(name = "transaction_id")
    @JSONField(name = "transaction_id")
    private String transactionId;
    /**
     * 商户订单号
     */
    @XmlElement(name = "out_order_no")
    @JSONField(name = "out_order_no")
    private String outOrderNo;
    /**
     * 分账接收方列表，不超过50个
     */
    private String receivers;
    /**
     * 分账完结描述
     */
    private String description;

    public ProfitSharingRequest(String transactionId, String outOrderNo, List<ReceiverProfit> receivers){
        this.transactionId = transactionId;
        this.outOrderNo = outOrderNo;
        this.receivers = receivers!=null && receivers.size()>0 ? JSON.toJSONString(receivers) : null;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getSignType() {
        return signType;
    }
}
