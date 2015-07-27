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
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
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

	@JSONCreator
	public Video(@JSONField(name = "media_id") String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * 公众平台
	 * 
	 * @param mediaId
	 * @param thumbMediaId
	 */
	public Video(String mediaId, String thumbMediaId) {
		this(mediaId, thumbMediaId, null, null);
	}

	/**
	 * 企业号
	 * 
	 * @param mediaId
	 * @param title
	 * @param desc
	 */
	public Video(String mediaId, String title, String desc) {
		this(mediaId, null, title, desc);
	}

	@JSONCreator
	public Video(@JSONField(name = "media_id") String mediaId,
			@JSONField(name = "thumb_media_id") String thumbMediaId,
			@JSONField(name = "title") String title,
			@JSONField(name = "description") String desc) {
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

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Video [thumbMediaId=" + thumbMediaId + ", title=" + title
				+ ", desc=" + desc + ", mediaId=" + mediaId + "]";
	}
}
