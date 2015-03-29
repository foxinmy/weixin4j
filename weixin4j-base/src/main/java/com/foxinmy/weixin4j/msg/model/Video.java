package com.foxinmy.weixin4j.msg.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.MediaType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 视频对象
 * <p>
 * <font color="red">可用于「被动消息」「客服消息」</font>
 * </p>
 * 
 * @className Video
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Video extends Base implements Responseable, Notifyable {

	private static final long serialVersionUID = 2167437425244069128L;

	/**
	 * 上传视频微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XStreamAlias("MediaId")
	private String mediaId;
	/**
	 * 缩略图的媒体ID(客服消息)
	 */
	@XStreamOmitField
	@JSONField(name = "thumb_media_id")
	private String thumbMediaId;
	/**
	 * 视频标题
	 */
	@XStreamAlias("Title")
	private String title;
	/**
	 * 视频描述
	 */
	@JSONField(name = "description")
	@XStreamAlias("Description")
	private String desc;

	public Video(String mediaId) {
		super(MediaType.video);
		this.mediaId = mediaId;
	}

	public Video(String mediaId, String thumbMediaId) {
		this(mediaId, thumbMediaId, null, null);
	}

	public Video(String mediaId, String title, String desc) {
		this(mediaId, null, title, desc);
	}

	public Video(String mediaId, String thumbMediaId, String title, String desc) {
		super(MediaType.video);
		this.mediaId = mediaId;
		this.thumbMediaId = thumbMediaId;
		this.title = title;
		this.desc = desc;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
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
				+ ", desc=" + desc + ", mediaId=" + mediaId
				+ ", getMediaType()=" + getMediaType() + "]";
	}
}
