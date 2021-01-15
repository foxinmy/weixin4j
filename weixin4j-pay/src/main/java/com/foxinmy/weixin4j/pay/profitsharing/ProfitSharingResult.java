package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * 单次分帐返回结果
 *
 * @author kit
 * @date 2020年05月25日
 * @since weixin4j-pay 1.1.0
 */
public class ProfitSharingResult extends MerchantResult {
    /**
     * 微信支付订单号
     */
    @XmlElement(name = "transaction_id")
    @JSONField(name = "transaction_id")
    private String transactionId;
    /**
     * 商户订单号
     */
    @XmlElement(name = "out_trade_no")
    @JSONField(name = "out_trade_no")
    private String outOrderNo;
    /**
     * 微信分帐单号
     */
    @XmlElement(name = "order_id")
    @JSONField(name = "order_id")
    private String orderId;
    /**
     * 分账单状态（分帐查询）
     * ACCEPTED—受理成功
     * PROCESSING—处理中
     * FINISHED—处理完成
     * CLOSED—处理失败，已关单
     */
    private String status;
    /**
     * 关单原因（分帐查询），非必传，默认值：NO_AUTH----分账授权已解除
     */
    @XmlElement(name = "close_reason")
    @JSONField(name = "close_reason")
    private String closeReason;
    /**
     * 分账接收方列表（分帐查询）
     */
    @JSONField(serialize = false)
    private String receivers;
    /**
     * 分账金额（分帐查询）
     * 分账完结的分账金额，单位为分， 仅当查询分账完结的执行结果时，存在本字段
     */
    private Integer amount;
    /**
     * 分帐描述（分帐查询）
     * 分账完结的原因描述，仅当查询分账完结的执行结果时，存在本字段
     */
    private String description;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JSONField(name = "receivers")
    public List<ReceiverProfitResult> getProfitResult(){
        return JSON.parseArray(this.receivers, ReceiverProfitResult.class);
    }
}
