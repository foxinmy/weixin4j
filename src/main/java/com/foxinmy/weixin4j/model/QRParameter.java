package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 二维码参数对象
 * <p>目前有2种类型的二维码,分别是临时二维码和永久二维码,前者有过期时间,最大为1800秒,但能够生成较多数量,后者无过期时间,数量较少(目前参数只支持1--100000)</p>
 * @className QRParameter
 * @author jy.hu
 * @date 2014年4月8日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%94%9F%E6%88%90%E5%B8%A6%E5%8F%82%E6%95%B0%E7%9A%84%E4%BA%8C%E7%BB%B4%E7%A0%81">生成带参数的二维码</a>
 */
public class QRParameter implements Serializable {

	private static final long serialVersionUID = 6611187606558274253L;

	public enum QRType {
		QR_SCENE, // 临时
		QR_LIMIT_SCENE; // 永久
	}

	private int expire_seconds; // 该二维码有效时间，以秒为单位。 最大不超过1800。
	@XStreamAlias("action_name")
	private QRType qrType; // 二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
	@XStreamOmitField
	private int scene_id; // 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）

	public int getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(int expire_seconds) {
		this.expire_seconds = expire_seconds;
	}

	public QRType getQrType() {
		return qrType;
	}

	public void setQrType(QRType qrType) {
		this.qrType = qrType;
	}

	public int getScene_id() {
		return scene_id;
	}

	public void setScene_id(int scene_id) {
		this.scene_id = scene_id;
	}

	public QRParameter(int expire_seconds, QRType qrType, int scene_id) {
		this.expire_seconds = expire_seconds;
		this.qrType = qrType;
		this.scene_id = scene_id;
	}

	public QRParameter(QRType action_name, int scene_id) {
		this(0, action_name, scene_id);
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
		jsonBuilder.append("\"action_name\":\"").append(qrType.name()).append("\"");
		if (this.qrType == QRType.QR_SCENE) {
			jsonBuilder.append(",\"expire_seconds\":").append(expire_seconds);
		}
		jsonBuilder.append(",\"action_info\":").append(String.format("{\"scene\": {\"scene_id\": %d}}", scene_id));
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}

	@Override
	public String toString() {
		return String.format("[QRParameter action_name=%s, expire_seconds=%d, scene_id=%d]", getQrType().name(), getExpire_seconds(), getScene_id());
	}
}
