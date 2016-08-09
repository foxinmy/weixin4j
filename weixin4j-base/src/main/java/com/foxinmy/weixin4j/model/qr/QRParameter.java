package com.foxinmy.weixin4j.model.qr;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.card.CardQR;
import com.foxinmy.weixin4j.type.QRType;
import com.foxinmy.weixin4j.util.Consts;

/**
 * 二维码参数对象
 * 
 * @className QRParameter
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月8日
 * @since JDK 1.6
 * @see #createTemporaryQR(int, long) 创建整型临时二维码
 * @see #createPermanenceQR(int) 创建整型永久二维码
 * @see #createPermanenceQR(String) 创建字符串型永久二维码
 * @see #createCardCouponQR(Integer, CardQR...) 创建卡券二维码
 */
public class QRParameter implements Serializable {

	private static final long serialVersionUID = 6611187606558274253L;

	/**
	 * 二维码的类型
	 * 
	 * @see com.foxinmy.weixin4j.type.QRType
	 */
	@JSONField(name = "action_name")
	private QRType qrType;
	/**
	 * 二维码的有效时间
	 */
	@JSONField(name = "expire_seconds")
	private Integer expireSeconds;
	/**
	 * 二维码的场景值
	 */
	@JSONField(serialize = false)
	private String sceneValue;
	/**
	 * 二维码的内容
	 */
	@JSONField(name = "action_info")
	private JSONObject sceneContent;

	private QRParameter(QRType qrType, Integer expireSeconds,
			String sceneValue, JSONObject sceneContent) {
		this.qrType = qrType;
		this.expireSeconds = expireSeconds;
		this.sceneValue = sceneValue;
		this.sceneContent = sceneContent;
	}

	public Integer getExpireSeconds() {
		return expireSeconds;
	}

	public QRType getQrType() {
		return qrType;
	}

	public String getSceneValue() {
		return sceneValue;
	}

	public JSONObject getSceneContent() {
		return sceneContent;
	}

	/**
	 * 创建临时二维码
	 * 
	 * @param expireSeconds
	 *            二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
	 * @param sceneValue
	 *            二维码的场景值 <font color="red">临时二维码最大值为无符号32位非0整型</font>
	 * @return 二维码参数
	 */
	public static QRParameter createTemporaryQR(int expireSeconds,
			long sceneValue) {
		JSONObject sceneContent = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_id", sceneValue);
		sceneContent.put("scene", scene);
		return new QRParameter(QRType.QR_SCENE, expireSeconds,
				Long.toString(sceneValue), sceneContent);
	}

	/**
	 * 创建永久二维码(场景值为int)
	 * 
	 * @param sceneValue
	 *            场景值 最大值为100000 （目前参数只支持1--100000)
	 */
	public static QRParameter createPermanenceQR(int sceneValue) {
		JSONObject sceneContent = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_id", sceneValue);
		sceneContent.put("scene", scene);
		return new QRParameter(QRType.QR_LIMIT_SCENE, null,
				Integer.toString(sceneValue), sceneContent);
	}

	/**
	 * 创建永久二维码(场景值为string)
	 * 
	 * @param sceneValue
	 *            场景值
	 */
	public static QRParameter createPermanenceQR(String sceneValue) {
		JSONObject sceneContent = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_str", sceneValue);
		sceneContent.put("scene", scene);
		return new QRParameter(QRType.QR_LIMIT_STR_SCENE, null, sceneValue,
				sceneContent);
	}

	/**
	 * 创建卡券二维码
	 * 
	 * @param expireSeconds
	 *            指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为365天有效
	 * @param cardQRs
	 *            二维码参数:二维码领取单张卡券/多张卡券
	 */
	public static QRParameter createCardCouponQR(Integer expireSeconds,
			CardQR... cardQRs) {
		QRType qrType = QRType.QR_CARD;
		JSONObject sceneContent = new JSONObject();
		StringBuilder sceneValue = new StringBuilder();
		sceneValue.append(cardQRs[0].getSceneValue());
		if (cardQRs.length > 1) {
			qrType = QRType.QR_MULTIPLE_CARD;
			JSONObject multipleCard = new JSONObject();
			multipleCard.put("card_list", cardQRs);
			sceneContent.put("multiple_card", multipleCard);
			for (int i = 1; i < cardQRs.length; i++) {
				sceneValue.append(Consts.SEPARATOR).append(
						cardQRs[i].getSceneValue());
			}
		} else {
			sceneContent.put("card", cardQRs[0]);
		}
		return new QRParameter(qrType, expireSeconds, sceneValue.toString(),
				sceneContent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expireSeconds == null) ? 0 : expireSeconds.hashCode());
		result = prime * result + ((qrType == null) ? 0 : qrType.hashCode());
		result = prime * result
				+ ((sceneContent == null) ? 0 : sceneContent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QRParameter other = (QRParameter) obj;
		if (expireSeconds == null) {
			if (other.expireSeconds != null)
				return false;
		} else if (!expireSeconds.equals(other.expireSeconds))
			return false;
		if (qrType != other.qrType)
			return false;
		if (sceneContent == null) {
			if (other.sceneContent != null)
				return false;
		} else if (!sceneContent.equals(other.sceneContent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QRParameter [qrType=" + qrType + ", expireSeconds="
				+ expireSeconds + ", sceneContent=" + sceneContent + "]";
	}
}
