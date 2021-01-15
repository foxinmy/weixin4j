package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.type.profitsharing.ReceiverType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 分帐方添加删除API调用结果
 *
 * @author kit (kit.li@qq.com)
 * @date 2020年05月25日
 * @since weixin4j-pay 1.1.0
 */
public class ReceiverProfitResult extends ReceiverProfit {
    /**
     * 分帐结果：
     * PENDING:待分账
     * SUCCESS:分账成功
     * ADJUST:分账失败待调账
     * RETURNED:已转回分账方
     * CLOSED: 已关闭
     */
    private String result;
    /**
     * 分账完成时间
     */
    @XmlElement(name = "finish_time")
    @JSONField(name = "finish_time")
    private String finishTime;
    @XmlElement(name = "fail_reason")
    @JSONField(name = "fail_reason")
    private String failReason;

    public ReceiverProfitResult(ReceiverType type, String account, int amount, String description, String result){
        super(type, account, amount, description);
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
