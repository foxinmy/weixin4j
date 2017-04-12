package com.zone.weixin4j.message;

import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.type.MessageType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 地理位置消息
 * 
 * @className LocationMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453&token=&lang=zh_CN">订阅号、服务号的地理位置消息</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#location.E6.B6.88.E6.81.AF">企业号的地理位置消息</a>
 */
public class LocationMessage extends WeixinMessage {

	private static final long serialVersionUID = 2866021596599237334L;

	public LocationMessage() {
		super(MessageType.location.name());
	}

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

	@Override
	public String toString() {
		return "LocationMessage [x=" + x + ", y=" + y + ", scale=" + scale
				+ ", label=" + label + ", " + super.toString() + "]";
	}
}
