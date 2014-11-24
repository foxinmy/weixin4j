package com.foxinmy.weixin4j.msg.event;

import com.foxinmy.weixin4j.type.EventType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 上报地理位置事件
 * 
 * @className LocationEventMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6">订阅号、服务号的上报地理位置事件</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6">企业号的上报地理位置事件</a>
 */
public class LocationEventMessage extends EventMessage {

	private static final long serialVersionUID = -2030716800669824861L;

	public LocationEventMessage() {
		super(EventType.location);
	}

	@XStreamAlias("Latitude")
	private String latitude;// 地理位置纬度
	@XStreamAlias("Longitude")
	private String longitude;// 地理位置经度
	@XStreamAlias("Precision")
	private String precision;// 地理位置精度

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getPrecision() {
		return precision;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[LocationEventMessage ,toUserName=").append(
				super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType());
		sb.append(" ,eventType=").append(super.getEventType().name());
		sb.append(" ,longitude=").append(longitude);
		sb.append(" ,latitude=").append(latitude);
		sb.append(" ,precision=").append(precision);
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
