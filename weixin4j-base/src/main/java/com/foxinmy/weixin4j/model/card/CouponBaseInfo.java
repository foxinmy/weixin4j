package com.foxinmy.weixin4j.model.card;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardCodeType;
import com.foxinmy.weixin4j.type.card.CardColor;

/**
 * 卡券基本信息
 *
 * @className CouponBaseInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年6月1日
 * @since JDK 1.6
 * @see Builder
 */
public class CouponBaseInfo implements Serializable {

	private static final long serialVersionUID = -5725424121330101716L;
	/**
	 * 卡券的商户logo，建议像素为300*300。
	 */
	@JSONField(name = "logo_url")
	private String logoUrl;
	/**
	 * 商户名字,字数上限为12个汉字，如：海底捞
	 */
	@JSONField(name = "brand_name")
	private String brandName;
	/**
	 * 卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)，如：双人套餐100元兑换券
	 */
	private String title;
	/**
	 * 码型
	 */
	@JSONField(name = "code_type")
	private CardCodeType codeType;
	/**
	 * 卡券颜色
	 */
	@JSONField(name = "color")
	private CardColor cardColor;
	/**
	 * 卡券使用提醒，字数上限为16个汉字，如：请出示二维码
	 */
	private String notice;
	/**
	 * 卡券使用说明，字数上限为1024个汉字，如：不可与其他优惠同享
	 */
	private String description;
	/**
	 * 卡券库存的数量，上限为100000000。
	 */
	private JSONObject sku;
	/**
	 * 使用日期，有效期的信息。
	 */
	@JSONField(name = "date_info")
	private JSONObject date;

	// 以下为选填字段
	/**
	 * 是否自定义Code码
	 */
	@JSONField(name = "use_custom_code")
	private Boolean useCustomCode;
	/**
	 * 指定特殊用户群体
	 */
	@JSONField(name = "bind_openid")
	private Boolean bindOpenId;
	/**
	 * 客服电话
	 */
	@JSONField(name = "service_phone")
	private String servicePhone;
	/**
	 * 门店位置poiid。具备线下门店
	 */
	@JSONField(name = "location_id_list")
	private List<String> locationIds;
	/**
	 * 设置本卡券支持全部门店，与locationIds互斥
	 */
	@JSONField(name = "use_all_locations")
	private boolean useAllLocation;
	/**
	 * 卡券顶部居中的按钮，仅在卡券状 态正常(可以核销)时显示，如：立即使用
	 */
	@JSONField(name = "center_title")
	private String centerTitle;
	/**
	 * 顶部居中的url ，仅在卡券状态正常(可以核销)时显示，如：www.qq.com
	 */
	@JSONField(name = "center_url")
	private String centerUrl;
	/**
	 * 显示在入口下方的提示语 ，仅在卡券状态正常(可以核销)时显示，如：立即享受优惠
	 */
	@JSONField(name = "center_sub_title")
	private String centerSubTitle;
	/**
	 * 自定义跳转外链的入口名字，如：立即使用
	 */
	@JSONField(name = "custom_url_name")
	private String customTitle;
	/**
	 * 自定义跳转的URL，如：www.qq.com
	 */
	@JSONField(name = "custom_url")
	private String customUrl;
	/**
	 * 显示在入口右侧的提示语，如：更多惊喜
	 */
	@JSONField(name = "custom_url_sub_title")
	private String customSubTitle;
	/**
	 * 营销场景的自定义入口名称，如：产品介绍
	 */
	@JSONField(name = "promotion_url_name")
	private String promotionTitle;
	/**
	 * 入口跳转外链的地址链接，如：www.qq.com
	 */
	@JSONField(name = "promotion_url")
	private String promotionUrl;
	/**
	 * 显示在营销入口右侧的提示语，如：卖场大优惠
	 */
	@JSONField(name = "promotion_url_sub_title")
	private String promotionSubTitle;
	/**
	 * 第三方来源名，例如同程旅游、大众点评。
	 */
	private String source;
	/**
	 * 每人可领券的数量限制,不填写默认为50。
	 */
	@JSONField(name = "get_limit")
	private int limitNum;
	/**
	 * 卡券领取页面是否可分享
	 */
	@JSONField(name = "can_share")
	private boolean canShare;
	/**
	 * 卡券是否可转赠
	 */
	@JSONField(name = "can_give_friend")
	private boolean canGiveFriend;

	@JSONField(name = "need_push_on_view")
	private Boolean needPushOnView;

	private CouponBaseInfo(Builder builder) {
		this.logoUrl = builder.logoUrl;
		this.brandName = builder.brandName;
		this.title = builder.title;
		this.codeType = builder.codeType;
		this.cardColor = builder.cardColor;
		this.notice = builder.notice;
		this.description = builder.description;
		this.sku = builder.sku;
		this.date = builder.date;
		this.useCustomCode = builder.useCustomCode;
		this.bindOpenId = builder.bindOpenId;
		this.servicePhone = builder.servicePhone;
		this.locationIds = builder.locationIds;
		this.useAllLocation = builder.useAllLocation;
		this.centerTitle = builder.centerTitle;
		this.centerUrl = builder.centerUrl;
		this.centerSubTitle = builder.centerSubTitle;
		this.customTitle = builder.customTitle;
		this.customUrl = builder.customUrl;
		this.customSubTitle = builder.customSubTitle;
		this.promotionTitle = builder.promotionTitle;
		this.promotionUrl = builder.promotionUrl;
		this.promotionSubTitle = builder.promotionSubTitle;
		this.source = builder.source;
		this.limitNum = builder.limitNum;
		this.canShare = builder.canShare;
		this.canGiveFriend = builder.canGiveFriend;
		this.needPushOnView = builder.needPushOnView;
	};

	public String getLogoUrl() {
		return logoUrl;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getTitle() {
		return title;
	}

	public CardCodeType getCodeType() {
		return codeType;
	}

	public CardColor getCardColor() {
		return cardColor;
	}

	public String getNotice() {
		return notice;
	}

	public String getDescription() {
		return description;
	}

	public JSONObject getSku() {
		return sku;
	}

	public JSONObject getDate() {
		return date;
	}

	public Boolean isUseCustomCode() {
		return useCustomCode;
	}

	public Boolean isBindOpenId() {
		return bindOpenId;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public List<String> getLocationIds() {
		return locationIds;
	}

	public boolean isUseAllLocation() {
		return useAllLocation;
	}

	public String getCenterTitle() {
		return centerTitle;
	}

	public String getCenterUrl() {
		return centerUrl;
	}

	public String getCenterSubTitle() {
		return centerSubTitle;
	}

	public String getCustomTitle() {
		return customTitle;
	}

	public String getCustomUrl() {
		return customUrl;
	}

	public String getCustomSubTitle() {
		return customSubTitle;
	}

	public String getPromotionTitle() {
		return promotionTitle;
	}

	public String getPromotionUrl() {
		return promotionUrl;
	}

	public String getPromotionSubTitle() {
		return promotionSubTitle;
	}

	public String getSource() {
		return source;
	}

	public int getLimitNum() {
		return limitNum;
	}

	public boolean isCanShare() {
		return canShare;
	}

	public boolean isCanGiveFriend() {
		return canGiveFriend;
	}

	public Boolean getNeedPushOnView() {
		return needPushOnView;
	}

	@Override
	public String toString() {
		return "logoUrl=" + logoUrl + ", brandName=" + brandName + ", title="
				+ title + ", codeType=" + codeType + ", cardColor=" + cardColor
				+ ", notice=" + notice + ", description=" + description
				+ ", sku=" + sku + ", date=" + date + ", useCustomCode="
				+ useCustomCode + ", bindOpenId=" + bindOpenId
				+ ", servicePhone=" + servicePhone + ", locationIds="
				+ locationIds + ", useAllLocation=" + useAllLocation
				+ ", centerTitle=" + centerTitle + ", centerUrl=" + centerUrl
				+ ", centerSubTitle=" + centerSubTitle + ", customTitle="
				+ customTitle + ", customUrl=" + customUrl
				+ ", customSubTitle=" + customSubTitle + ", promotionTitle="
				+ promotionTitle + ", promotionUrl=" + promotionUrl
				+ ", promotionSubTitle=" + promotionSubTitle + ", source="
				+ source + ", limitNum=" + limitNum + ", canShare=" + canShare
				+ ", canGiveFriend=" + canGiveFriend;
	}

	public void cleanCantUpdateField() {
		this.brandName = null;
//		this.title = null;
		this.sku = null;
		this.bindOpenId = null;
		this.useCustomCode = null;
	}

	/**
	 * 卡券基础信息构造器
	 * 
	 * @className Builder
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2016年8月4日
	 * @since JDK 1.6
	 */
	public static final class Builder {
		/**
		 * 卡券的商户logo，建议像素为300*300
		 */
		private String logoUrl;
		/**
		 * 商户名字,字数上限为12个汉字，如：海底捞
		 */
		private String brandName;
		/**
		 * 卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)，如：双人套餐100元兑换券
		 */
		private String title;
		/**
		 * 码型
		 */
		private CardCodeType codeType;
		/**
		 * 卡券颜色
		 */
		private CardColor cardColor;
		/**
		 * 卡券使用提醒，字数上限为16个汉字，如：请出示二维码
		 */
		private String notice;
		/**
		 * 卡券使用说明，字数上限为1024个汉字，如：不可与其他优惠同享
		 */
		private String description;
		/**
		 * 卡券库存的数量，上限为100000000。
		 */
		private JSONObject sku;
		/**
		 * 使用日期，有效期的信息。
		 */
		private JSONObject date;

		// 以下为选填字段
		/**
		 * 是否自定义Code码
		 */
		private boolean useCustomCode;
		/**
		 * 指定特殊用户群体
		 */
		private boolean bindOpenId;
		/**
		 * 客服电话
		 */
		private String servicePhone;
		/**
		 * 门店位置poiid。具备线下门店
		 */
		private List<String> locationIds;
		/**
		 * 设置本卡券支持全部门店，与locationIds互斥
		 */
		private boolean useAllLocation;
		/**
		 * 卡券顶部居中的按钮，仅在卡券状 态正常(可以核销)时显示，如：立即使用
		 */
		private String centerTitle;
		/**
		 * 顶部居中的url ，仅在卡券状态正常(可以核销)时显示，如：www.qq.com
		 */
		private String centerUrl;
		/**
		 * 显示在入口下方的提示语 ，仅在卡券状态正常(可以核销)时显示，如：立即享受优惠
		 */
		private String centerSubTitle;
		/**
		 * 自定义跳转外链的入口名字，如：立即使用
		 */
		private String customTitle;
		/**
		 * 自定义跳转的URL，如：www.qq.com
		 */
		private String customUrl;
		/**
		 * 显示在入口右侧的提示语，如：更多惊喜
		 */
		private String customSubTitle;
		/**
		 * 营销场景的自定义入口名称，如：产品介绍
		 */
		private String promotionTitle;
		/**
		 * 入口跳转外链的地址链接，如：www.qq.com
		 */
		private String promotionUrl;
		/**
		 * 显示在营销入口右侧的提示语，如：卖场大优惠
		 */
		private String promotionSubTitle;
		/**
		 * 第三方来源名，例如同程旅游、大众点评。
		 */
		private String source;
		/**
		 * 每人可领券的数量限制,不填写默认为50。
		 */
		private int limitNum;
		/**
		 * 卡券领取页面是否可分享,不填写默认true
		 */
		private boolean canShare;
		/**
		 * 卡券是否可转赠,不填写默认true
		 */
		private boolean canGiveFriend;

		/**
		 * 用户点击进入卡券时推送事件
		 */
		private boolean needPushOnView;

		/**
		 * 默认永久有效
		 */
		public Builder() {
			this.sku = new JSONObject();
			this.date = new JSONObject();
			this.date.put("type",CardActiveType.DATE_TYPE_PERMANENT);
			this.useAllLocation = true;
			this.canShare = true;
			this.canGiveFriend = true;
			this.limitNum = 50;
		}

		/**
		 * 设置商户logo
		 * 
		 * @param logoUrl
		 *            卡券的商户logo，建议像素为300*300。
		 * @return
		 */
		public Builder logoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
			return this;
		}

		/**
		 * 设置商户名字
		 * 
		 * @param brandName
		 *            商户名字,字数上限为12个汉字
		 * @return
		 */
		public Builder brandName(String brandName) {
			this.brandName = brandName;
			return this;
		}

		/**
		 * 设置卡券名
		 * 
		 * @param title
		 *            卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)。
		 * @return
		 */
		public Builder title(String title) {
			this.title = title;
			return this;
		}

		/**
		 * 设置码型
		 * 
		 * @param codeType
		 *            码型
		 * @return
		 */
		public Builder codeType(CardCodeType codeType) {
			this.codeType = codeType;
			return this;
		}

		/**
		 * 设置卡券颜色
		 * 
		 * @param cardColor
		 *            卡券颜色
		 * @return
		 */
		public Builder cardColor(CardColor cardColor) {
			this.cardColor = cardColor;
			return this;
		}

		/**
		 * 设置卡券使用提醒
		 * 
		 * @param notice
		 *            卡券使用提醒，字数上限为16个汉字。
		 * @return
		 */
		public Builder notice(String notice) {
			this.notice = notice;
			return this;
		}

		/**
		 * 设置卡券使用说明
		 * 
		 * @param description
		 *            卡券使用说明，字数上限为1024个汉字。
		 * @return
		 */
		public Builder description(String description) {
			this.description = description;
			return this;
		}

		/**
		 * 设置卡券库存的数量
		 * 
		 * @param quantity
		 *            卡券库存的数量，上限为100000000。
		 * 
		 * @return
		 */
		public Builder quantity(int quantity) {
			quantity = quantity > 100000000 ? 100000000 : quantity;
			this.sku.put("quantity", quantity);
			return this;
		}

		/**
		 * 设置卡券在某个时间范围有效
		 * 
		 * @param beginTime
		 *            表示起用时间
		 * @param endTime
		 *            表示结束时间，建议设置为截止日期的23:59:59过期
		 * @return
		 */
		public Builder activeAt(Date beginTime, Date endTime) {
			this.date.clear();
			this.date.put("type", CardActiveType.DATE_TYPE_FIX_TIME_RANGE);
			this.date.put("begin_timestamp", beginTime.getTime() / 1000l);
			this.date.put("end_timestamp", endTime.getTime() / 1000l);
			return this;
		}

		/**
		 * 设置卡券在领取多少天后有效
		 * 
		 * @param days
		 *            表示自领取后多少天内有效，不支持填写0。
		 * @param beginDays
		 *            表示自领取后多少天开始生效，领取后当天生效填写0。（单位为天）
		 * @param endTime
		 *            表示卡券统一过期时间，建议设置为截止日期的23:59:59过期。（东八区时间，单位为秒），设置了days卡券，
		 *            当时间达到end_timestamp时卡券统一过期
		 * @return
		 */
		public Builder activeAt(int days, int beginDays, Date endTime) {
			this.date.clear();
			this.date.put("type", CardActiveType.DATE_TYPE_FIX_TERM);
			this.date.put("fixed_term", days);
			this.date.put("fixed_begin_term", beginDays);
			this.date.put("end_timestamp", endTime.getTime() / 1000l);
			return this;
		}

		/**
		 * 设置卡券在领取多少天后有效
		 *
		 * @param days
		 *            表示自领取后多少天内有效，不支持填写0。
		 * @param beginDays
		 *            表示自领取后多少天开始生效，领取后当天生效填写0。（单位为天）
		 * @return
		 */
		public Builder activeAt(int days, int beginDays) {
			this.date.clear();
			this.date.put("type", CardActiveType.DATE_TYPE_FIX_TERM);
			this.date.put("fixed_term", days);
			this.date.put("fixed_begin_term", beginDays);
			return this;
		}

		/**
		 * 设置是否自定义Code码
		 * 
		 * @param useCustomCode
		 *            填写true或false，默认为false。
		 * @return
		 */
		public Builder useCustomCode(boolean useCustomCode) {
			this.useCustomCode = useCustomCode;
			return this;
		}

		/**
		 * 设置是否指定用户领取，填写true或false
		 * 
		 * @param bindOpenId
		 *            默认为false。通常指定特殊用户群体 投放卡券或防止刷券时选择指定用户领取。
		 * @return
		 */
		public Builder bindOpenId(boolean bindOpenId) {
			this.bindOpenId = bindOpenId;
			return this;
		}

		/**
		 * 设置客服电话
		 * 
		 * @param servicePhone
		 *            客服电话
		 * @return
		 */
		public Builder servicePhone(String servicePhone) {
			this.servicePhone = servicePhone;
			return this;
		}

		/**
		 * 设置门店位置poiid。具备线下门店 的商户为必填
		 * 
		 * @param locationIds
		 *            门店列表
		 * @return
		 */
		public Builder locationIds(String... locationIds) {
			this.locationIds.addAll(Arrays.asList(locationIds));
			this.useAllLocation = false;
			return this;
		}

		/**
		 * 设置卡券顶部居中的按钮，仅在卡券状态正常(可以核销)时显示
		 * 
		 * @param centerTitle
		 *            按钮文字
		 * @return
		 */
		public Builder centerTitle(String centerTitle) {
			this.centerTitle = centerTitle;
			return this;
		}

		/**
		 * 设置顶部居中的url ，仅在卡券状态正常(可以核销)时显示
		 * 
		 * @param centerUrl
		 *            按钮链接
		 * @return
		 */
		public Builder centerUrl(String centerUrl) {
			this.centerUrl = centerUrl;
			return this;
		}

		/**
		 * 设置显示在入口下方的提示语 ，仅在卡券状态正常(可以核销)时显示。
		 * 
		 * @param centerSubTitle
		 *            入口下方的提示语
		 * @return
		 */
		public Builder centerSubTitle(String centerSubTitle) {
			this.centerSubTitle = centerSubTitle;
			return this;
		}

		/**
		 * 设置自定义跳转外链的入口名字
		 * 
		 * @param customTitle
		 *            自定义外链入口文字
		 * @return
		 */
		public Builder customTitle(String customTitle) {
			this.customTitle = customTitle;
			return this;
		}

		/**
		 * 设置自定义跳转的URL
		 * 
		 * @param customUrl
		 *            跳转链接
		 * @return
		 */
		public Builder customUrl(String customUrl) {
			this.customUrl = customUrl;
			return this;
		}

		/**
		 * 设置显示在入口右侧的提示语
		 * 
		 * @param customSubTitle
		 *            入口右侧的提示语
		 * @return
		 */
		public Builder customSubTitle(String customSubTitle) {
			this.customSubTitle = customSubTitle;
			return this;
		}

		/**
		 * 设置营销场景的自定义入口名称
		 * 
		 * @param promotionTitle
		 *            自定义入口文字
		 * @return
		 */
		public Builder promotionTitle(String promotionTitle) {
			this.promotionTitle = promotionTitle;
			return this;
		}

		/**
		 * 设置入口跳转外链的地址链接。
		 * 
		 * @param promotionUrl
		 *            入口跳转链接
		 * @return
		 */
		public Builder promotionUrl(String promotionUrl) {
			this.promotionUrl = promotionUrl;
			return this;
		}

		/**
		 * 设置显示在营销入口右侧的提示语
		 * 
		 * @param promotionSubTitle
		 *            入口右侧的提示语
		 * @return
		 */
		public Builder promotionSubTitle(String promotionSubTitle) {
			this.promotionSubTitle = promotionSubTitle;
			return this;
		}

		/**
		 * 设置第三方来源名，例如同程旅游、大众点评。
		 * 
		 * @param source
		 *            来源
		 * @return
		 */
		public Builder source(String source) {
			this.source = source;
			return this;
		}

		/**
		 * 设置每人可领券的数量限制,不填写默认为50。
		 * 
		 * @param limitNum
		 *            限制数量
		 * @return
		 */
		public Builder limitNum(int limitNum) {
			this.limitNum = limitNum;
			return this;
		}

		/**
		 * 设置卡券领取页面是否可分享。
		 * 
		 * @param canShare
		 *            是否可以分享
		 * @return
		 */
		public Builder canShare(boolean canShare) {
			this.canShare = canShare;
			return this;
		}

		/**
		 * 设置卡券是否可转赠
		 * 
		 * @param canGiveFriend
		 *            是否可以转赠
		 * @return
		 */
		public Builder canGiveFriend(boolean canGiveFriend) {
			this.canGiveFriend = canGiveFriend;
			return this;
		}

		public Builder needPushOnView(boolean needPushOnView){
			this.needPushOnView = needPushOnView;
			return this;
		}

		/**
		 * 返回卡券的基础信息
		 * 
		 * @return 卡券基础信息
		 */
		public CouponBaseInfo build() {
			return new CouponBaseInfo(this);
		}

		/**
		 * 卡券使用时间类型
		 * 
		 * @className CardActiveType
		 * @author jinyu(foxinmy@gmail.com)
		 * @date 2016年8月5日
		 * @since JDK 1.7
		 * @see
		 */
		public enum CardActiveType {
			/**
			 * 表示固定日期区间
			 */
			DATE_TYPE_FIX_TIME_RANGE,
			/**
			 * 表示固定时长 （自领取后按天算。
			 */
			DATE_TYPE_FIX_TERM, /**
			 * 永久有效
			 */
			DATE_TYPE_PERMANENT;
		}
	}
}
