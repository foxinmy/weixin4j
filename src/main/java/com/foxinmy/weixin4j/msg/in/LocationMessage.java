package com.foxinmy.weixin4j.msg.in;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 地理位置消息
 * @className LocationMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E6.B6.88.E6.81.AF">地理位置消息</a>
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()
 */
public class LocationMessage extends BaseMessage {

	private static final long serialVersionUID = 2866021596599237334L;

	public LocationMessage() {
		super(MessageType.location);
	}

	@XStreamAlias("Location_X")
	private String location_X; // 地理位置维度
	@XStreamAlias("Location_Y")
	private String location_Y; // 地理位置经度
	@XStreamAlias("Scale")
	private String scale; // 地图缩放大小
	@XStreamAlias("Label")
	private String label; // 地理位置信息

	public String getLocation_X() {
		return location_X;
	}

	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}

	public String getLocation_Y() {
		return location_Y;
	}

	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[LocationMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,location_X=").append(location_X);
		sb.append(" ,location_Y=").append(location_Y);
		sb.append(" ,scale=").append(scale);
		sb.append(" ,label=").append(label);
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
