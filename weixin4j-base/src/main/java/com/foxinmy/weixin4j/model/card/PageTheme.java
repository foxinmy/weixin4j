package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 礼品卡货架主题
 *
 * @author kit(kit.li@qq.com)
 * @date 2018年10月30日
 */
public class PageTheme {
    /**
     * 主题的封面图片，须先将图片上传至CDN 大小控制在1000px*600px
     */
    @JSONField(name = "theme_pic_url")
    private String cover;
    /**
     * 主题名称，如“圣诞”“感恩家人”
     */
    private String title;
    /**
     * 主题title的颜色，直接传入色值
     */
    @JSONField(name = "title_color")
    private String titleColor;
    /**
     * 礼品卡列表，表示主题可选择的礼品卡，由cardid及标题文字组成
     */
    @JSONField(name = "item_list")
    private List<CardItem> itemList;
    /**
     * 礼品卡可选择的封面图
     */
    @JSONField(name = "pic_item_list")
    private List<PicItem> picItemList;
    /**
     * 当前主题所属主题分类的索引，对应主题分类列表category_list内的title字段， 若填写了category_list则每个主题必填该序号
     */
    @JSONField(name = "category_index")
    private Integer categoryIndex;
    /**
     * 该主题购买页是否突出商品名显示
     */
    @JSONField(name = "show_sku_title_first")
    private Boolean showSkuTitleFirst;
    /**
     * 是否将当前主题设置为banner主题（主推荐）
     */
    @JSONField(name = "is_banner")
    private Boolean bannerTheme;

    public PageTheme(Builder builder){
        this.cover = builder.cover;
        this.title = builder.title;
        this.titleColor = builder.titleColor;
        this.itemList = builder.itemList;
        this.picItemList = builder.picItemList;
        this.categoryIndex = builder.categoryIndex;
        this.showSkuTitleFirst = builder.showSkuTitleFirst;
        this.bannerTheme = builder.bannerTheme;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public List<CardItem> getItemList() {
        return itemList;
    }

    public List<PicItem> getPicItemList() {
        return picItemList;
    }

    public Integer getCategoryIndex() {
        return categoryIndex;
    }

    public Boolean getShowSkuTitleFirst() {
        return showSkuTitleFirst;
    }

    public Boolean getBannerTheme() {
        return bannerTheme;
    }

    public static class Builder{
        /**
         * 主题的封面图片，须先将图片上传至CDN 大小控制在1000px*600px
         */
        private String cover;
        /**
         * 主题名称，如“圣诞”“感恩家人”
         */
        private String title;
        /**
         * 主题title的颜色，直接传入色值
         */
        private String titleColor;
        /**
         * 礼品卡列表，表示主题可选择的礼品卡，由cardid及标题文字组成
         */
        private List<CardItem> itemList;
        /**
         * 礼品卡可选择的封面图
         */
        private List<PicItem> picItemList;
        /**
         * 主题标号，对应category_list内的title字段， 若填写了category_list则每个主题必填该序号
         */
        private Integer categoryIndex;
        /**
         * 该主题购买页是否突出商品名显示
         */
        private Boolean showSkuTitleFirst;
        /**
         * 是否将当前主题设置为banner主题（主推荐）
         */
        private Boolean bannerTheme;

        public Builder(){
            this.itemList = new ArrayList<CardItem>();
            this.picItemList = new ArrayList<PicItem>();
        }

        /**
         * 设置主题的封面图片
         *
         * @param cover
         * @return
         */
        public Builder cover(String cover){
            this.cover = cover;
            return this;
        }

        /**
         * 设置主题名称
         *
         * @param title
         * @return
         */
        public Builder title(String title){
            this.title = title;
            return this;
        }

        /**
         * 设置主题title的颜色
         *
         * @param titleColor
         *          直接设置色值，如：#FB966E
         * @return
         */
        public Builder titleColor(String titleColor){
            this.titleColor = titleColor;
            return this;
        }

        /**
         * 添加一个或多个礼品卡内容
         *
         * @param items
         * @return
         */
        public Builder cardItems(CardItem... items){
            this.itemList = Arrays.asList(items);
            return this;
        }

        /**
         * 添加一个礼品卡内容
         *
         * @param item
         * @return
         */
        public Builder addCardItem(CardItem item){
            this.itemList.add(item);
            return this;
        }

        /**
         * 添加一个或多个礼品卡封面图
         *
         * @param items
         * @return
         */
        public Builder picItems(PicItem... items){
            this.picItemList = Arrays.asList(items);
            return this;
        }

        /**
         * 添加一个礼品卡封面图
         *
         * @param item
         * @return
         */
        public Builder addPicItem(PicItem item){
            this.picItemList.add(item);
            return this;
        }

        /**
         * 设置所属主题分类的索引号
         *
         * @param index
         * @return
         */
        public Builder categoryIndex(Integer index){
            this.categoryIndex = index;
            return this;
        }

        /**
         * 设置该主题购买页是否突出商品名显示
         *
         * @param isShow
         * @return
         */
        public Builder showSkuTitleFirst(Boolean isShow){
            this.showSkuTitleFirst = isShow;
            return this;
        }

        /**
         * 设置是否将当前主题设置为banner主题（主推荐）
         *
         * @param isBanner
         * @return
         */
        public Builder bannerTheme(Boolean isBanner){
            this.bannerTheme = isBanner;
            return this;
        }
    }
}
