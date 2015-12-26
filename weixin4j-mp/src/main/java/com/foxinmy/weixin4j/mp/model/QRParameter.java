package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.foxinmy.weixin4j.type.QRType;

/**
 * 二维码参数对象
 * <p>
 * 目前有2种类型的二维码,分别是临时二维码和永久二维码,前者有过期时间,最大为1800秒,但能够生成较多数量,后者无过期时间,数量较少(目前参数只支持1--
 * 100000)
 * </p>
 * 
 * @className QRParameter
 * @author jy.hu
 * @date 2014年4月8日
 * @since JDK 1.6
 * 
 */
public class QRParameter implements Serializable {

	private static final long serialVersionUID = 6611187606558274253L;

	/**
	 * 临时二维码的有效时间, 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒
	 */
	private int expireSeconds;
	/**
	 * 二维码类型
	 * 
	 * @see com.foxinmy.weixin4j.type.QRType
	 */
	private QRType qrType;
	/**
	 * 场景值I 根据qrType参数而定
	 */
	private String sceneValue;

	private QRParameter() {
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public QRType getQrType() {
		return qrType;
	}

	public String getSceneValue() {
		return sceneValue;
	}

	private String content;

	public String getContent() {
		return content;
	}

	/**
	 * 创建临时二维码
	 * 
	 * @param expireSeconds
	 *            有效时间
	 * @param sceneValue
	 *            二维码的场景值
	 * @return 二维码参数
	 */
	public static QRParameter createTemporary(int expireSeconds, int sceneValue) {
		QRParameter qr = new QRParameter();
		qr.qrType = QRType.QR_SCENE;
		qr.expireSeconds = expireSeconds;
		qr.sceneValue = Integer.toString(sceneValue);
		qr.content = String
				.format("{\"expire_seconds\": %s, \"action_name\": \"%s\", \"action_info\": {\"scene\": {\"scene_id\": %s}}}",
						expireSeconds, QRType.QR_SCENE.name(), sceneValue);
		return qr;
	}

	/**
	 * 创建永久二维码(场景值为int)
	 * 
	 * @param sceneValue
	 *            场景值
	 * @return 二维码参数
	 */
	public static QRParameter createPermanenceInt(int sceneValue) {
		QRParameter qr = new QRParameter();
		qr.qrType = QRType.QR_LIMIT_SCENE;
		qr.sceneValue = Integer.toString(sceneValue);
		qr.content = String
				.format("{\"action_name\": \"%s\", \"action_info\": {\"scene\": {\"scene_id\": %s}}}",
						QRType.QR_LIMIT_SCENE.name(), sceneValue);
		return qr;
	}

	/**
	 * 创建永久二维码(场景值为string)
	 * 
	 * @param sceneValue
	 *            场景值
	 * @return 二维码参数
	 */
	public static QRParameter createPermanenceStr(String sceneValue) {
		QRParameter qr = new QRParameter();
		qr.qrType = QRType.QR_LIMIT_STR_SCENE;
		qr.sceneValue = sceneValue;
		qr.content = String
				.format("{\"action_name\": \"%s\", \"action_info\": {\"scene\": {\"scene_str\": \"%s\"}}}",
						QRType.QR_LIMIT_STR_SCENE, sceneValue);
		return qr;
	}

	@Override
	public String toString() {
		return "QRParameter [expireSeconds=" + expireSeconds + ", qrType="
				+ qrType + ", sceneValue=" + sceneValue + "]";
	}
}
