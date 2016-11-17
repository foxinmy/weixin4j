package com.foxinmy.weixin4j.model.card;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 卡券二维码参数
 * 
 * @className CardQR
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月9日
 * @since JDK 1.6
 * @see Builder
 */
public class CardQR implements Serializable {
	private static final long serialVersionUID = -2810577326677518511L;

	/**
	 * 卡券ID
	 */
	@JSONField(name = "card_id")
	private String cardId;
	/**
	 * 卡券Code码,use_custom_code字段为true的卡券必须填写，非自定义code和导入code模式的卡券不必填写。
	 */
	@JSONField(name = "code")
	private String cardCode;
	/**
	 * 指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，非指定openid不必填写。
	 */
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 领取场景值，用于领取渠道的数据统计，默认值为0，字段类型为整型，长度限制为60位数字。用户领取卡券后触发的事件推送中会带上此自定义场景值。
	 */
	@JSONField(name = "outer_str")
	private String sceneValue;
	/**
	 * 指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。填写true或false。默认false，注意填写该字段时，
	 * 卡券须通过审核且库存不为0。
	 */
	@JSONField(name = "is_unique_code")
	private boolean isUniqueCode;

	private CardQR(Builder builder) {
		this.cardId = builder.cardId;
		this.cardCode = builder.cardCode;
		this.isUniqueCode = builder.isUniqueCode;
		this.openId = builder.openId;
		this.sceneValue = builder.sceneValue;
	}

	public String getCardId() {
		return cardId;
	}

	public String getCardCode() {
		return cardCode;
	}

	public String getOpenId() {
		return openId;
	}

	public String getSceneValue() {
		return sceneValue;
	}

	public boolean isUniqueCode() {
		return isUniqueCode;
	}

	@Override
	public String toString() {
		return "CardQR [cardId=" + cardId + ", cardCode=" + cardCode
				+ ", openId=" + openId + ", sceneValue=" + sceneValue
				+ ", isUniqueCode=" + isUniqueCode + "]";
	}

	public static class Builder {
		/**
		 * 卡券ID
		 */
		private String cardId;
		/**
		 * 卡券Code码,use_custom_code字段为true的卡券必须填写，非自定义code和导入code模式的卡券不必填写。
		 */
		private String cardCode;
		/**
		 * 指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，非指定openid不必填写。
		 */
		private String openId;
		/**
		 * 用户首次领卡时，会通过领取事件推送给商户；
		 * 对于会员卡的二维码，用户每次扫码打开会员卡后点击任何url，会将该值拼入url中，方便开发者定位扫码来源
		 */
		private String sceneValue;
		/**
		 * 指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。填写true或false。默认false，注意填写该字段时，
		 * 卡券须通过审核且库存不为0。
		 */
		private boolean isUniqueCode;

		public Builder(String cardId) {
			this.cardId = cardId;
		}

		/**
		 * 卡券Code码,use_custom_code字段为true的卡券必须填写，非自定义code和导入code模式的卡券不必填写。
		 * 
		 * @param cardCode
		 *            卡券code码
		 */
		public Builder cardCode(String cardCode) {
			this.cardCode = cardCode;
			return this;
		}

		/**
		 * 指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，非指定openid不必填写。
		 * 
		 * @param openId
		 *            用户openid
		 */
		public Builder openId(String openId) {
			this.openId = openId;
			return this;
		}

		/**
		 * 用户首次领卡时，会通过领取事件推送给商户；
		 * 对于会员卡的二维码，用户每次扫码打开会员卡后点击任何url，会将该值拼入url中，方便开发者定位扫码来源
		 * 
		 * @param sceneValue
		 *            场景值
		 */
		public Builder sceneValuer(String sceneValue) {
			this.sceneValue = sceneValue;
			return this;
		}

		/**
		 * 指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。填写true或false。默认false，注意填写该字段时，
		 * 卡券须通过审核且库存不为0。
		 */
		public Builder openUniqueCode() {
			this.isUniqueCode = true;
			return this;
		}

		public CardQR build() {
			return new CardQR(this);
		}
	}
}
