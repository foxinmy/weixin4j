package com.foxinmy.weixin4j.tuple;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 视频对象
 * <p>
 * <font color="red">可用于「客服消息」</font>
 * </p>
 *
 * @className Video
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see
 */
public class Video implements NotifyTuple {

	private static final long serialVersionUID = 2167437425244069128L;

	@Override
	public String getMessageType() {
		return "video";
	}

	/**
	 * 上传视频微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XmlElement(name = "MediaId")
	private String mediaId;
	/**
	 * 缩略图的媒体ID(客服消息)
	 */
	@JSONField(name = "thumb_media_id")
	@XmlTransient
	private String thumbMediaId;
	/**
	 * 视频标题
	 */
	@XmlElement(name = "Title")
	private String title;
	/**
	 * 视频描述
	 */
	@JSONField(name = "description")
	@XmlElement(name = "Description")
	private String desc;

	/**
	 * 企业号的视频消息不需要缩略图
	 *
	 * @param mediaId
	 *            视频媒体文件id，可以调用上传临时素材或者永久素材接口获取
	 * @param title
	 *            视频标题
	 * @param desc
	 *            视频描述
	 */
	public Video(String mediaId, String title, String desc) {
		this(mediaId, null, title, desc);
	}

	/**
	 * 公众平台发送视频消息
	 *
	 * @param mediaId
	 *            视频媒体文件id，可以调用上传临时素材或者永久素材接口获取
	 * @param thumbMediaId
	 *            视频缩略图
	 * @param title
	 *            视频标题
	 * @param desc
	 *            视频描述
	 */
	@JSONCreator
	public Video(@JSONField(name = "mediaId") String mediaId,
			@JSONField(name = "thumbMediaId") String thumbMediaId,
			@JSONField(name = "title") String title,
			@JSONField(name = "desc") String desc) {
		this.mediaId = mediaId;
		this.thumbMediaId = thumbMediaId;
		this.title = title;
		this.desc = desc;
	}

	public String getMediaId() {
		return mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public String getTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return "Video [thumbMediaId=" + thumbMediaId + ", title=" + title
				+ ", desc=" + desc + ", mediaId=" + mediaId + "]";
	}
}
