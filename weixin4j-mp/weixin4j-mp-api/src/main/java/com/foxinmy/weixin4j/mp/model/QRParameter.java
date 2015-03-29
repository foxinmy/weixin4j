package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.foxinmy.weixin4j.mp.type.QRType;

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
 * @since JDK 1.7
 * 
 * @see com.foxinmy.weixin4j.mp.type.QRType
 */
public class QRParameter implements Serializable {

	private static final long serialVersionUID = 6611187606558274253L;

	/**
	 * 该二维码有效时间，以秒为单位。 最大不超过1800。
	 */
	private int expireSeconds;
	/**
	 * 二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
	 */
	private QRType qrType;
	/**
	 * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 */
	private int sceneId;

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public QRType getQrType() {
		return qrType;
	}

	public void setQrType(QRType qrType) {
		this.qrType = qrType;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public QRParameter(int sceneId, int expireSeconds) {
		this(sceneId, expireSeconds, QRType.TEMPORARY);
		if (expireSeconds <= 0) {
			this.qrType = QRType.PERMANENCE;
		}
	}

	public QRParameter(int sceneId, int expireSeconds, QRType qrType) {
		this.expireSeconds = expireSeconds;
		this.qrType = qrType;
		this.sceneId = sceneId;
	}

	public String toJson() {
		/*
		 * XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
		 * public HierarchicalStreamWriter createWriter(Writer writer) { return
		 * new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE); } });
		 * xstream.setMode(XStream.NO_REFERENCES);
		 * xstream.autodetectAnnotations(true); if (this.qrType ==
		 * QRType.QR_LIMIT_SCENE) { xstream.omitField(QRParameter.class,
		 * "expire_seconds"); } return xstream.toXML(this);
		 */
		StringBuilder jsonBuilder = new StringBuilder("{");
		jsonBuilder.append("\"action_name\":\"").append(qrType.getName())
				.append("\"");
		if (this.qrType == QRType.TEMPORARY) {
			jsonBuilder.append(",\"expire_seconds\":").append(expireSeconds);
		}
		jsonBuilder.append(",\"action_info\":").append(
				String.format("{\"scene\": {\"scene_id\": %d}}", sceneId));
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}

	@Override
	public String toString() {
		return "QRParameter [expireSeconds=" + expireSeconds + ", qrType="
				+ qrType + ", sceneId=" + sceneId + "]";
	}
}
