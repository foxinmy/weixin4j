package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;
import com.foxinmy.weixin4j.type.card.FieldNameType;

/**
 * 会员卡
 * @auther: Feng Yapeng
 * @since: 2016/12/15 10:09
 */
public class MemberCard extends CardCoupon {

    /**
     * 会员卡背景图 [商家自定义会员卡背景图，须先调用上传图片接口将背景图上传至CDN，否则报错，
     * 卡面设计请遵循微信会员卡自定义背景设计规范  ,像素大小控制在1000像素*600像素以下]
     */
    @JSONField(name = "background_pic_url")
    private String backgroundPicUrl;

    /**
     * 会员卡特权说明。
     */
    @JSONField(name = "prerogative")
    private String  prerogative;
    /**
     * 用户领取会员卡后系统自动将其激活，无需调用激活接口，详情见自动激活。
     */
    @JSONField(name = "auto_activate")
    private boolean autoActivate;
    /**
     * 设置为true时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效。
     */
    @JSONField(name = "wx_activate")
    private boolean wxActivate;
    /**
     * 激活会员卡的url。
     */
    @JSONField(name = "activate_url")
    private String  activateUrl;

    /**
     * 显示积分 【true:积分相关字段均为必填】
     */
    @JSONField(name = "supply_bonus")
    private boolean supplyBonus;
    /**
     * 设置跳转外链查看积分详情。仅适用于积分无法通过激活接口同步的情况下使用该字段
     */
    @JSONField(name = "bonus_url")
    private String  bonusUrl;
    /**
     * 显示余额
     */
    @JSONField(name = "supply_balance")
    private boolean  supplyBalance;
    /**
     * 设置跳转外链查看余额详情。仅适用于余额无法通过激活接口同步的情况下使用该字段。
     */
    @JSONField(name = "balance_url")
    private String  balanceUrl;

    /**
     * 自定义会员信息类目
     */
    @JSONField(name = "custom_field1")
    private MemCardCustomField customField1;
    /**
     * 自定义会员信息类目
     */
    @JSONField(name = "custom_field2")
    private MemCardCustomField customField2;
    /**
     * 自定义会员信息类目
     */
    @JSONField(name = "custom_field3")
    private MemCardCustomField customField3;

    /**
     * 积分规则说明
     */
    @JSONField(name = "bonus_rules")
    private String             bonusRules;
    /**
     * 储值说明。
     */
    @JSONField(name = "balance_rules")
    private String             balanceRules;
    /**
     * 积分清零规则
     */
    @JSONField(name = "bonus_cleared")
    private String             bonusCleared;
    /**
     * 自定义会员信息类目，会员卡激活后显示
     */
    @JSONField(name = "custom_cell1")
    private MemCardCustomField customCell1;

    /**
     * 折扣【该会员卡享受的折扣优惠,填10就是九折。】
     */
    private int discount;

    @JSONField(name = "bonus_rule")
    private MemCardBonusRule bonusRule;


    /**
     * 卡券
     *
     * @param couponBaseInfo 基础信息
     */
    protected MemberCard(CouponBaseInfo couponBaseInfo, Builder builder) {
        super(couponBaseInfo);
        this.activateUrl = builder.activateUrl;
        this.backgroundPicUrl = builder.backgroundPicUrl;
        this.prerogative = builder.prerogative;
        this.autoActivate = builder.autoActivate;
        this.wxActivate = builder.wxActivate;
        this.activateUrl = builder.activateUrl;
        this.supplyBonus = builder.supplyBonus;
        this.bonusUrl = builder.bonusUrl;
        this.supplyBalance = builder.supplyBalance;
        this.balanceUrl = builder.balanceUrl;
        this.customField1 = builder.customField1;
        this.customField2 = builder.customField2;
        this.customField3 = builder.customField3;
        this.bonusRules = builder.bonusRules;
        this.balanceRules = builder.balanceRules;
        this.bonusCleared = builder.bonusCleared;
        this.customCell1 = builder.customCell1;
        this.discount = builder.discount;
        this.bonusRule = builder.bonusRule;
    }

    @JSONField(serialize = false)
    @Override
    public CardType getCardType() {
        return CardType.MEMBER_CARD;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public String getPrerogative() {
        return prerogative;
    }

    public boolean isAutoActivate() {
        return autoActivate;
    }

    public boolean isWxActivate() {
        return wxActivate;
    }

    public String getActivateUrl() {
        return activateUrl;
    }

    public boolean isSupplyBonus() {
        return supplyBonus;
    }

    public String getBonusUrl() {
        return bonusUrl;
    }

    public boolean getSupplyBalance() {
        return supplyBalance;
    }

    public String getBalanceUrl() {
        return balanceUrl;
    }

    public MemCardCustomField getCustomField1() {
        return customField1;
    }

    public MemCardCustomField getCustomField2() {
        return customField2;
    }

    public MemCardCustomField getCustomField3() {
        return customField3;
    }

    public String getBonusRules() {
        return bonusRules;
    }

    public String getBalanceRules() {
        return balanceRules;
    }

    public String getBonusCleared() {
        return bonusCleared;
    }

    public MemCardCustomField getCustomCell1() {
        return customCell1;
    }

    public int getDiscount() {
        return discount;
    }

    public MemCardBonusRule getBonusRule() {
        return bonusRule;
    }


    public static final class Builder {

        /**
         * 会员卡背景图 [商家自定义会员卡背景图，须先调用上传图片接口将背景图上传至CDN，否则报错，
         * 卡面设计请遵循微信会员卡自定义背景设计规范  ,像素大小控制在1000像素*600像素以下]
         */
        private String backgroundPicUrl;

        /**
         * 会员卡特权说明。
         */
        private String  prerogative;
        /**
         * 用户领取会员卡后系统自动将其激活，无需调用激活接口，详情见自动激活。
         */
        private boolean autoActivate;
        /**
         * 设置为true时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效。
         */
        private boolean wxActivate;
        /**
         * 激活会员卡的url。
         */
        private String  activateUrl;

        /**
         * 显示积分 【true:积分相关字段均为必填】
         */
        private boolean            supplyBonus;
        /**
         * 设置跳转外链查看积分详情。仅适用于积分无法通过激活接口同步的情况下使用该字段
         */
        private String             bonusUrl;
        /**
         * 显示余额
         */
        private boolean             supplyBalance;
        /**
         * 设置跳转外链查看余额详情。仅适用于余额无法通过激活接口同步的情况下使用该字段。
         */
        private String             balanceUrl;
        /**
         * 自定义会员信息类目
         */
        private MemCardCustomField customField1;
        /**
         * 自定义会员信息类目
         */
        private MemCardCustomField customField2;
        /**
         * 自定义会员信息类目
         */
        private MemCardCustomField customField3;
        /**
         * 积分规则说明
         */
        private String             bonusRules;
        /**
         * 储值说明。
         */
        private String             balanceRules;
        /**
         * 积分清零规则
         */
        private String             bonusCleared;
        /**
         * 自定义会员信息类目，会员卡激活后显示
         */
        private MemCardCustomField customCell1;
        /**
         * 折扣【该会员卡享受的折扣优惠,填10就是九折。】
         */
        private int                discount;
        /**
         * 积分规则
         */
        private MemCardBonusRule   bonusRule;


        public Builder backgroundPicUrl(String backgroundPicUrl) {
            this.backgroundPicUrl = backgroundPicUrl;
            return this;
        }

        public Builder prerogative(String prerogative) {
            this.prerogative = prerogative;
            return this;
        }

        public Builder activateWithAuto(boolean autoActivate) {
            this.autoActivate = autoActivate;
            this.activateUrl = null;
            this.wxActivate = false;
            return this;
        }

        public Builder activateWithWx(boolean wxActivate) {
            this.wxActivate = wxActivate;
            this.autoActivate = false;
            this.activateUrl = null;
            return this;
        }

        public Builder activateUrl(String activateUrl) {
            this.activateUrl = activateUrl;
            this.autoActivate  = false;
            this.wxActivate = false;
            return this;
        }

        public Builder supplyBonus(boolean supplyBonus) {
            this.supplyBonus = supplyBonus;
            return this;
        }

        public Builder bonusUrl(String bonusUrl) {
            this.bonusUrl = bonusUrl;
            return this;
        }

        public Builder supplyBalance(boolean supplyBalance) {
            this.supplyBalance = supplyBalance;
            return this;
        }

        public Builder balanceUrl(String balanceUrl) {
            this.balanceUrl = balanceUrl;
            return this;
        }

        public Builder customField1(FieldNameType type, String name, String url) {
            this.customField1 = new MemCardCustomField(type, name, url);
            return this;
        }

        public Builder customField2(FieldNameType type, String name, String url) {
            this.customField2 = new MemCardCustomField(type, name, url);
            return this;
        }

        public Builder customField3(FieldNameType type, String name, String url) {
            this.customField3 = new MemCardCustomField(type, name, url);
            return this;
        }

        public Builder bonusRules(String bonusRules) {
            this.bonusRules = bonusRules;
            return this;
        }

        public Builder balanceRules(String balanceRules) {
            this.balanceRules = balanceRules;
            return this;
        }

        public Builder bonusCleared(String bonusCleared) {
            this.bonusCleared = bonusCleared;
            return this;
        }

        public Builder customCell1(String name, String url, String tips) {
            this.customCell1 = new MemCardCustomField(name, url, tips);
            return this;
        }

        public Builder discount(int discount) {
            this.discount = discount;
            return this;
        }

        public Builder bonusRule(MemCardBonusRule bonusRule) {
            this.bonusRule = bonusRule;
            return this;
        }
    }
}
