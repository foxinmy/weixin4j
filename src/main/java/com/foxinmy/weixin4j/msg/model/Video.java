package com.foxinmy.weixin4j.msg.model;

import com.foxinmy.weixin4j.type.MediaType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 视频对象
 * 
 * @className Video
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Video extends BaseMsg {

	private static final long serialVersionUID = 1L;

	@XStreamAlias("media_id")
	private String mediaId;
	@XStreamAlias("thumb_media_id")
	private String thumbMediaId;
	private String title;
	@XStreamAlias("description")
	private String desc;

	public Video(String mediaId) {
		this(mediaId, null, null, null);
	}

	public Video(String mediaId, String thumbMediaId) {
		this(mediaId, thumbMediaId, null, null);
	}

	public Video(String mediaId, String title, String desc) {
		this(mediaId, null, title, desc);
	}

	public Video(String mediaId, String thumbMediaId, String title, String desc) {
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
		return "Video [mediaId=" + mediaId + ", thumbMediaId=" + thumbMediaId
				+ ", title=" + title + ", desc=" + desc + "]";
	}

	@Override
	public MediaType getMediaType() {
		return MediaType.vedio;
	}
}
