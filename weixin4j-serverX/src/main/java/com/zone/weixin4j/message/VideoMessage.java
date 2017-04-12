package com.zone.weixin4j.message;

import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.type.MessageType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 视频消息
 * 
 * @className VideoMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453&token=&lang=zh_CN">订阅号、服务号的视频消息</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#video.E6.B6.88.E6.81.AF">企业号的视频消息</a>
 */
public class VideoMessage extends WeixinMessage {

	private static final long serialVersionUID = -1013075358679078381L;

	public VideoMessage() {
		super(MessageType.video.name());
	}

	/**
	 * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	@XmlElement(name = "MediaId")
	private String mediaId;
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	@XmlElement(name = "ThumbMediaId")
	private String thumbMediaId;

	public String getMediaId() {
		return mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	@Override
	public String toString() {
		return "VideoMessage [mediaId=" + mediaId + ", thumbMediaId="
				+ thumbMediaId + ", " + super.toString() + "]";
	}
}
