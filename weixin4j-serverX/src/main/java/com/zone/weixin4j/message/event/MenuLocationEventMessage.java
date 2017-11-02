package com.zone.weixin4j.message.event;

import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 弹出地理位置选择器的事件推送
 * 
 * @className MenuLocationEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月30日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454&token=&lang=zh_CN">订阅号、服务号的弹出地理位置选择事件推送</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E5.BC.B9.E5.87.BA.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E9.80.89.E6.8B.A9.E5.99.A8.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">企业号的弹出地理位置选择事件推送</a>
 */
public class MenuLocationEventMessage extends MenuEventMessage {

	private static final long serialVersionUID = 145223888272819563L;

	public MenuLocationEventMessage() {
		super(EventType.location_select);
	}

	/**
	 * 发送的位置消息
	 */
	@XmlElement(name = "SendLocationInfo")
	private LocationInfo locationInfo;

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	/**
	 * 地理位置信息
	 * 
	 * @className LocationInfo
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年3月29日
	 * @since JDK 1.6
	 * @see
	 */
	public static class LocationInfo implements Serializable {

		private static final long serialVersionUID = 4904181780216819965L;

		/**
		 * 地理位置维度
		 */
		@XmlElement(name = "Location_X")
		private double x;
		/**
		 * 地理位置经度
		 */
		@XmlElement(name = "Location_Y")
		private double y;
		/**
		 * 地图缩放大小
		 */
		@XmlElement(name = "Scale")
		private double scale;
		/**
		 * 地理位置信息
		 */
		@XmlElement(name = "Label")
		private String label;
		/**
		 * 朋友圈POI的名字，可能为空
		 */
		@XmlElement(name = "Poiname")
		private String poiname;

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getScale() {
			return scale;
		}

		public String getLabel() {
			return label;
		}

		public String getPoiname() {
			return poiname;
		}

		@Override
		public String toString() {
			return "LocationInfo [x=" + x + ", y=" + y + ", scale=" + scale
					+ ", label=" + label + ", poiname=" + poiname + "]";
		}
	}

	@Override
	public String toString() {
		return "MenuLocationEventMessage [locationInfo=" + locationInfo + ", "
				+ super.toString() + "]";
	}
}
