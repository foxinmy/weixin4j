package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 会员卡积分规则
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/15 11:43
 */
public class MemCardBonusRule {

    /**
     * 消费金额。以分为单位。
     */
    @JSONField(name = "cost_money_unit")
    private int costMoneyUnit;
    /**
     * 对应增加的积分
     */
    @JSONField(name = "increase_bonus")
    private int increaseBonus;
    /**
     * 用户单次可获取的积分上限。
     */
    @JSONField(name = "max_increase_bonus ")
    private int maxIncreaseBonus;
    /**
     * 初始设置积分【可以理解为开卡积分】
     */
    @JSONField(name = "init_increase_bonus")
    private int initIncreaseBonus;
    /**
     *每使用N 积分
     */
    @JSONField(name = "cost_bonus_unit")
    private int costBonusUnit;
    /**
     * 抵扣多少分
     */
    @JSONField(name = "reduce_money")
    private int reduceMoney;
    /**
     * 抵扣条件，满xx分（这里以分为单位）可用
     */
    @JSONField(name = "least_money_to_use_bonus")
    private int leastMoneyToUseBonus;
    /**
     *
     */
    @JSONField(name = "max_reduce_bonus")
    private int  maxReduceBonus;


    public int getCostMoneyUnit() {
        return costMoneyUnit;
    }

    public void setCostMoneyUnit(int costMoneyUnit) {
        this.costMoneyUnit = costMoneyUnit;
    }

    public int getIncreaseBonus() {
        return increaseBonus;
    }

    public void setIncreaseBonus(int increaseBonus) {
        this.increaseBonus = increaseBonus;
    }

    public int getMaxIncreaseBonus() {
        return maxIncreaseBonus;
    }

    public void setMaxIncreaseBonus(int maxIncreaseBonus) {
        this.maxIncreaseBonus = maxIncreaseBonus;
    }

    public int getInitIncreaseBonus() {
        return initIncreaseBonus;
    }

    public void setInitIncreaseBonus(int initIncreaseBonus) {
        this.initIncreaseBonus = initIncreaseBonus;
    }

    public int getCostBonusUnit() {
        return costBonusUnit;
    }

    public void setCostBonusUnit(int costBonusUnit) {
        this.costBonusUnit = costBonusUnit;
    }

    public int getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(int reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public int getLeastMoneyToUseBonus() {
        return leastMoneyToUseBonus;
    }

    public void setLeastMoneyToUseBonus(int leastMoneyToUseBonus) {
        this.leastMoneyToUseBonus = leastMoneyToUseBonus;
    }

    public int getMaxReduceBonus() {
        return maxReduceBonus;
    }

    public void setMaxReduceBonus(int maxReduceBonus) {
        this.maxReduceBonus = maxReduceBonus;
    }
}
