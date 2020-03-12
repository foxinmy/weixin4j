package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 礼品卡货架
 *
 * @author kit(kit.li@qq.com)
 * @date 2018年10月30日
 */
public class GiftCardPage {
    @JSONField(name = "page_id")
    private String pageId;
    /**
     * 礼品卡货架名称
     */
    @JSONField(name = "page_title")
    private String title;
    /**
     * 是否支持一次购买多张及发送至群，填true或者false，若填true则支持，默认为false
     */
    @JSONField(name = "support_multi")
    private Boolean supportMulti;
    /**
     * 礼品卡货架是否支持买给自己，填true或者false，若填true则支持，默认为false
     */
    @JSONField(name = "support_buy_for_self")
    private Boolean supportBuyForSelf;
    /**
     * 礼品卡货架主题页顶部banner图片，须先将图片上传至CDN，建议尺寸为750px*630px
     */
    @JSONField(name = "banner_pic_url")
    private String bannerPicUrl;
    /**
     * 主题结构体
     */
    @JSONField(name = "theme_list")
    private JSONArray themeList;
    /**
     * 主题分类列表
     */
    @JSONField(name = "category_list")
    private JSONArray categoryList;
    /**
     * 商家地址
     */
    private String address;
    /**
     * 商家服务电话
     */
    @JSONField(name = "service_phone")
    private String servicePhone;
    /**
     * 商家使用说明，用于描述退款、发票等流程
     */
    @JSONField(name = "biz_description")
    private String description;
    /**
     * 该货架的订单是否支持开发票，填true或者false，若填true则需要使用API设置支付后开票功能，默认为false
     */
    @JSONField(name = "need_receipt")
    private Boolean needReceipt;
    /**
     * 商家自定义链接，用于承载退款、发票等流程
     */
    @JSONField(name = "cell_1")
    private JSONObject cell1;
    /**
     * 商家自定义链接，用于承载退款、发票等流程
     */
    @JSONField(name = "cell_2")
    private JSONObject cell2;

    public String getPageId() { return pageId; }

    public String getTitle() {
        return title;
    }

    public Boolean getSupportMulti() {
        return supportMulti;
    }

    public Boolean getSupportBuyForSelf() {
        return supportBuyForSelf;
    }

    public String getBannerPicUrl() {
        return bannerPicUrl;
    }

    public JSONArray getThemeList() {
        return themeList;
    }

    public JSONArray getCategoryList() {
        return categoryList;
    }

    public String getAddress() {
        return address;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getNeedReceipt() {
        return needReceipt;
    }

    public JSONObject getCell1() {
        return cell1;
    }

    public JSONObject getCell2() {
        return cell2;
    }

    public GiftCardPage(Builder builder){
        this.pageId = builder.pageId;
        this.address = builder.address;
        this.bannerPicUrl = builder.bannerPicUrl;
        this.categoryList = builder.categoryList;
        this.cell1 = builder.cell1;
        this.cell2 = builder.cell2;
        this.description = builder.description;
        this.needReceipt = builder.needReceipt;
        this.servicePhone = builder.servicePhone;
        this.supportBuyForSelf = builder.supportBuyForSelf;
        this.supportMulti = builder.supportMulti;
        this.themeList = builder.themeList;
        this.title = builder.title;
    }

    public static class Builder{
        /**
         * 货架ID，只在货架更新时用到
         */
        private String pageId;
        /**
         * 礼品卡货架名称
         */
        private String title;
        /**
         * 是否支持一次购买多张及发送至群，填true或者false，若填true则支持，默认为false
         */
        private Boolean supportMulti;
        /**
         * 礼品卡货架是否支持买给自己，填true或者false，若填true则支持，默认为false
         */
        private Boolean supportBuyForSelf;
        /**
         * 礼品卡货架主题页顶部banner图片，须先将图片上传至CDN，建议尺寸为750px*630px
         */
        private String bannerPicUrl;
        /**
         * 主题结构体
         */
        private JSONArray themeList;
        /**
         * 主题分类列表
         */
        private JSONArray categoryList;
        /**
         * 商家地址
         */
        private String address;
        /**
         * 商家服务电话
         */
        private String servicePhone;
        /**
         * 商家使用说明，用于描述退款、发票等流程
         */
        private String description;
        /**
         * 该货架的订单是否支持开发票，填true或者false，若填true则需要使用API设置支付后开票功能，默认为false
         */
        private Boolean needReceipt;
        /**
         * 商家自定义链接，用于承载退款、发票等流程
         */
        private JSONObject cell1;
        /**
         * 商家自定义链接，用于承载退款、发票等流程
         */
        private JSONObject cell2;

        public Builder(){
            this.themeList = new JSONArray();
            this.categoryList = null;
        }

        public Builder pageId(String pageId){
            this.pageId = pageId;
            return this;
        }

        /**
         * 设置礼品卡货架名称
         *
         * @param title
         * @return
         */
        public Builder title(String title){
            this.title = title;
            return this;
        }

        /**
         * 设置是否支持一次购买多张及发送至群
         *
         * @param supportMulti
         * @return
         */
        public Builder supportMulti(boolean supportMulti){
            this.supportMulti = Boolean.valueOf(supportMulti);
            return this;
        }

        /**
         * 设置礼品卡货架是否支持买给自己
         *
         * @param supportBuyForSelf
         * @return
         */
        public Builder supportBuyForSelf(boolean supportBuyForSelf){
            this.supportBuyForSelf = Boolean.valueOf(supportBuyForSelf);
            return this;
        }

        /**
         * 设置礼品卡货架主题页顶部banner图片
         *
         * @param url
         * @return
         */
        public Builder bannerPicUrl(String url){
            this.bannerPicUrl = url;
            return this;
        }

        /**
         * 添加一个主题
         *
         * @param theme
         * @return
         */
        public Builder themeList(PageTheme theme){
            this.themeList.add(theme);
            return this;
        }

        /**
         * 添加一个主题分类
         *
         * @param title
         * @return
         */
        public Builder categoryList(String title){
            if(this.categoryList==null){
                this.categoryList = new JSONArray();
            }
            JSONObject category = new JSONObject();
            category.put("title", title);
            this.categoryList.add(category);
            return this;
        }

        /**
         * 设置商家地址
         *
         * @param address
         * @return
         */
        public Builder address(String address){
            this.address = address;
            return this;
        }

        /**
         * 设置商家服务电话
         *
         * @param phoneNo
         * @return
         */
        public Builder servicePhone(String phoneNo){
            this.servicePhone = phoneNo;
            return this;
        }

        /**
         * 设置商家使用说明
         *
         * @param description
         * @return
         */
        public Builder description(String description){
            this.description = description;
            return this;
        }

        /**
         * 设置该货架的订单是否支持开发票
         *
         * @param needReceipt
         * @return
         */
        public Builder needReceipt(boolean needReceipt){
            this.needReceipt = Boolean.valueOf(needReceipt);
            return this;
        }

        /**
         * 设置商家自定义链接
         *
         * @param title
         * @param url
         * @return
         */
        public Builder cell1(String title, String url){
            JSONObject cell = new JSONObject();
            cell.put("title", title);
            cell.put("url", url);
            this.cell1 = cell;
            return this;
        }

        /**
         * 设置商家自定义链接
         *
         * @param title
         * @param url
         * @return
         */
        public Builder cell2(String title, String url){
            JSONObject cell = new JSONObject();
            cell.put("title", title);
            cell.put("url", url);
            this.cell2 = cell;
            return this;
        }
    }
}
