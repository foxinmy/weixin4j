package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;
import com.foxinmy.weixin4j.pay.type.profitsharing.ReturnAccountType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 分帐回退API请求内容
 *
 * @author kit (kit.li@qq.com)
 * @date 2020年05月25日
 * @since weixin4j-pay 1.1.0
 */
public class ProfitSharingReturnRequest extends MerchantResult {
    /**
     * 只支持HMAC-SHA256
     */
    @XmlElement(name = "sign_type")
    @JSONField(name = "sign_type")
    private final String signType = "HMAC-SHA256";
    /**
     * 微信分帐单号
     * 原发起分账请求时，微信返回的微信分账单号，与商户分账单号一一对应。微信分账单号与商户分账单号二选一填写
     */
    private String orderId;
    /**
     * 商户订单号
     * 原发起分账请求时使用的商户系统内部的分账单号。微信分账单号与商户分账单号二选一填写
     */
    @XmlElement(name = "out_trade_no")
    @JSONField(name = "out_trade_no")
    private String outOrderNo;
    /**
     * 商户回退单号
     */
    @XmlElement(name = "out_return_no")
    @JSONField(name = "out_return_no")
    private String outReturnNo;
    /**
     * 回退方类型
     */
    @XmlElement(name = "return_account_type")
    @JSONField(name = "return_account_type")
    private ReturnAccountType returnAccountType = ReturnAccountType.MERCHANT_ID;
    /**
     * 回退方账号
     * 当回退方类型为商户ID时，填写商户ID
     */
    @XmlElement(name = "return_account")
    @JSONField(name = "return_account")
    private String returnAccount;
    /**
     * 回退描述
     */
    private String description;

    /**
     * 用于回退查询的参数构造方法
     *
     * @param orderId
     *          微信分帐单号
     * @param outOrderNo
     *          商户分帐单号
     * @param outReturnNo
     *          商户回退单号
     */
    public ProfitSharingReturnRequest(String orderId, String outOrderNo, String outReturnNo) {
        this.orderId = orderId;
        this.outOrderNo = outOrderNo;
        this.outReturnNo = outReturnNo;
    }

    /**
     * 用于回退请求的参数构造方法
     *
     * @param orderId
     *          微信分帐单号
     * @param outOrderNo
     *          商户分帐单号
     * @param outReturnNo
     *          商户回退单号
     * @param returnAccountType
     *          回退方类型
     * @param returnAccount
     *          回退方帐号
     * @param description
     *          回退描述
     */
    public ProfitSharingReturnRequest(String orderId, String outOrderNo, String outReturnNo,
                                      ReturnAccountType returnAccountType, String returnAccount, String description) {
        this.orderId = orderId;
        this.outOrderNo = outOrderNo;
        this.outReturnNo = outReturnNo;
        this.returnAccountType = returnAccountType;
        this.returnAccount = returnAccount;
        this.description = description;
    }

    @Override
    public String getSignType() {
        return signType;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
