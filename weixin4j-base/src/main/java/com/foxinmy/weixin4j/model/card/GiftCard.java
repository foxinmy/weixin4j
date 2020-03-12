package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;
import com.foxinmy.weixin4j.type.card.SubCardType;

/**
 * 储值类礼品卡
 *
 * @author kit(kit.li@qq.com)
 * @date 2018年10月26日
 */
public class GiftCard extends CardCoupon {

    /**
     * 子卡券类型
     */
    @JSONField(name = "sub_card_type")
    private final String subCardType;
    /**
     * 礼品卡背景图片,非必需
     */
    @JSONField(name = "background_pic_url")
    private String backgroundPicUrl;
    /**
     * 是否支持积分，填写true或者false。默认为false
     */
    @JSONField(name = "supply_bonus")
    private boolean supplyBonus;
    /**
     * 是否支持余额，填写true或者false。默认为false
     */
    @JSONField(name = "supply_balance")
    private boolean supplyBalance;
    /**
     * 自定义会员信息类目，会员卡激活后显示,包含name和url字段
     */
    @JSONField(name = "custom_field1")
    private MemCardCustomField customField1;
    /**
     * 自定义会员信息类目，会员卡激活后显示,包含name和url字段
     */
    @JSONField(name = "custom_field2")
    private MemCardCustomField customField2;
    /**
     * 自定义会员信息类目，会员卡激活后显示,包含name和url字段
     */
    @JSONField(name = "custom_field3")
    private MemCardCustomField customField3;
    /**
     * 是否自动激活，若开发者不需要额外激活流程则填写true。
     */
    @JSONField(name = "auto_activate")
    private boolean autoActivate;
    /**
     * 初始余额，用户购买礼品卡后卡面上显示的初始余额
     */
    @JSONField(name = "init_balance")
    private Integer initBalance;

    public GiftCard(CouponBaseInfo baseInfo, GiftCard.Builder builder){
        super(baseInfo);
        this.subCardType = SubCardType.VOUCHER.name();
        this.autoActivate = builder.isAutoActivate();
        this.backgroundPicUrl = builder.getBackgroundPicUrl();
        this.customField1 = builder.getCustomField1();
        this.customField2 = builder.getCustomField2();
        this.customField3 = builder.getCustomField3();
        this.supplyBalance = builder.isSupplyBalance();
        this.supplyBonus = builder.isSupplyBonus();
        this.initBalance = builder.getInitBalance();
    }

    @JSONField(serialize = false)
    @Override
    public CardType getCardType() {
        return CardType.GENERAL_COUPON;
    }

    public static final class Builder {
        private String backgroundPicUrl;
        private boolean supplyBonus;
        private boolean supplyBalance;
        private boolean autoActivate;
        private Integer initBalance;
        private MemCardCustomField customField1;
        private MemCardCustomField customField2;
        private MemCardCustomField customField3;

        public Builder(){
            this.autoActivate = true;
            this.supplyBalance = false;
            this.supplyBonus = false;
        }

        public String getBackgroundPicUrl() {
            return backgroundPicUrl;
        }

        public boolean isSupplyBonus() {
            return supplyBonus;
        }

        public boolean isSupplyBalance() {
            return supplyBalance;
        }

        public boolean isAutoActivate() {
            return autoActivate;
        }

        public Integer getInitBalance() {
            return initBalance;
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

        /**
         * 设置礼品卡背景图片
         * @param url
         * @return
         */
        public Builder backgroundPicUrl(String url){
            this.backgroundPicUrl = url;
            return this;
        }

        /**
         * 设置是否支持积分（目前未清楚单品类礼品卡是否支持积分）
         * @param supplyBonus
         * @return
         */
        public Builder supplyBonus(boolean supplyBonus){
            this.supplyBonus = supplyBonus;
            return this;
        }

        /**
         * 设置是否支持余额（需礼品卡类型为GIFT_CARD，单品类礼品卡VOUCHER并非用于储值）
         * 注意事项：开发者仅能在supply_balance和custom_field1、custom_field2、custom_field3中选择最多3个填写，否则报错。
         * @param supplyBalance
         * @return
         */
        public Builder supplyBalance(boolean supplyBalance){
            this.supplyBalance = supplyBalance;
            return this;
        }

        /**
         * 设置礼品卡是否自动激活，若开发者不需要额外激活流程则填写true。
         * @param autoActivate
         * @return
         */
        public Builder autoActivate(boolean autoActivate){
            this.autoActivate = autoActivate;
            return this;
        }

        /**
         * 设置初始化的余额（需礼品卡类型为GIFT_CARD，单品类礼品卡VOUCHER并非用于储值）
         * @param initBalance
         * @return
         */
        public Builder initBalance(Integer initBalance){
            this.initBalance = initBalance;
            return this;
        }

        /**
         * 设置自定义会员信息类目
         * 注意事项：开发者仅能在supply_balance和custom_field1、custom_field2、custom_field3中选择最多3个填写，否则报错。
         * @param name 自定义信息类目名称
         * @param url 自定义信息类目跳转url
         * @return
         */
        public Builder customField1(String name, String url){
            MemCardCustomField field = new MemCardCustomField(name, url, null);
            this.customField1 = field;
            return this;
        }

        /**
         * 设置自定义会员信息类目
         * 注意事项：开发者仅能在supply_balance和custom_field1、custom_field2、custom_field3中选择最多3个填写，否则报错。
         * @param name 自定义信息类目名称
         * @param url 自定义信息类目跳转url
         * @return
         */
        public Builder customField2(String name, String url){
            MemCardCustomField field = new MemCardCustomField(name, url, null);
            this.customField2 = field;
            return this;
        }

        /**
         * 设置自定义会员信息类目
         * 注意事项：开发者仅能在supply_balance和custom_field1、custom_field2、custom_field3中选择最多3个填写，否则报错。
         * @param name 自定义信息类目名称
         * @param url 自定义信息类目跳转url
         * @return
         */
        public Builder customField3(String name, String url){
            MemCardCustomField field = new MemCardCustomField(name, url, null);
            this.customField3 = field;
            return this;
        }
    }

}
