package com.foxinmy.weixin4j.pay.profitsharing;

import com.foxinmy.weixin4j.pay.type.profitsharing.ReceiverType;

/**
 * 请求分帐时使用的分帐方信息
 *
 * @author kit(kit.li@qq.com)
 * @date 2020年05月25日
 * @since weixin4j-pay 1.1.0
 */
public class ReceiverProfit extends Receiver {
    private int amount;
    private String description;

    /**
     * json deserialize need
     */
    public ReceiverProfit(){
        super();
    }

    public ReceiverProfit(ReceiverType type, String account, int amount, String description){
        super(type, account, null);
        this.amount = amount;
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
