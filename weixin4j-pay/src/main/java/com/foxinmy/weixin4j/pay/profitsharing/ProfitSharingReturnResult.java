package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;
import com.foxinmy.weixin4j.pay.type.profitsharing.ReturnAccountType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 分帐回退/回退查询返回内容
 *
 * @author kit（kit.li@qq.com）
 * @date 2020年05年25日
 * @since weixin4j-pay 1.1.0
 */
public class ProfitSharingReturnResult extends MerchantResult {
    /**
     * 微信分账单号
     */
    @XmlElement(name = "order_id")
    @JSONField(name = "order_id")
    private String orderId;
    /**
     * 商户分账单号
     */
    @XmlElement(name = "out_order_no")
    @JSONField(name = "out_order_no")
    private String outOrderNo;
    /**
     * 商户回退单号
     */
    @XmlElement(name = "out_return_no")
    @JSONField(name = "out_return_no")
    private String outReturnNo;
    /**
     * 微信回退单号
     */
    @XmlElement(name = "return_no")
    @JSONField(name = "return_no")
    private String returnNo;
    /**
     * 回退方类型
     */
    @XmlElement(name = "return_account_type")
    @JSONField(name = "return_account_type")
    private ReturnAccountType returnAccountType;
    /**
     * 回退方账号
     */
    @XmlElement(name = "return_account")
    @JSONField(name = "return_account")
    private String returnAccount;
    /**
     * 回退金额
     */
    @XmlElement(name = "return_amount")
    @JSONField(name = "return_amount")
    private Integer returnAmount;
    /**
     * 回退描述
     */
    private String description;
    /**
     * 回退结果
     */
    private String result;
    /**
     * 失败原因
     */
    @XmlElement(name = "fail_reason")
    @JSONField(name = "fail_reason")
    private String failReason;
    /**
     * 完成时间
     */
    @XmlElement(name = "finish_time")
    @JSONField(name = "finish_time")
    private String finishTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getOutReturnNo() {
        return outReturnNo;
    }

    public void setOutReturnNo(String outReturnNo) {
        this.outReturnNo = outReturnNo;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public ReturnAccountType getReturnAccountType() {
        return returnAccountType;
    }

    public void setReturnAccountType(ReturnAccountType returnAccountType) {
        this.returnAccountType = returnAccountType;
    }

    public String getReturnAccount() {
        return returnAccount;
    }

    public void setReturnAccount(String returnAccount) {
        this.returnAccount = returnAccount;
    }

    public Integer getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Integer returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
