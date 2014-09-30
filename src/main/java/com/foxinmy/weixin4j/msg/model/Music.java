package com.foxinmy.weixin4j.msg.model;

import com.foxinmy.weixin4j.type.MediaType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 音乐对象
 * 
 * @className Music
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Music extends BaseMsg {

	private static final long serialVersionUID = 1L;

	private String title;
	@XStreamAlias("description")
	private String desc;
	@XStreamAlias("musicurl")
	private String musicUrl;
	@XStreamAlias("hqmusicurl")
	private String hqMusicUrl;
	@XStreamAlias("thumb_media_id")
	private String thumbMediaId;

	public Music(String thumbMediaId) {
		this(null, null, null, null, thumbMediaId);
	}

	public Music(String title, String desc, String musicUrl, String hqMusicUrl,
			String thumbMediaId) {
		this.title = title;
		this.desc = desc;
		this.musicUrl = musicUrl;
		this.hqMusicUrl = hqMusicUrl;
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

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toString() {
		return "Music [title=" + title + ", desc=" + desc + ", musicUrl="
				+ musicUrl + ", hqMusicUrl=" + hqMusicUrl + ", thumbMediaId="
				+ thumbMediaId + "]";
	}

	@Override
	public MediaType getMediaType() {
		return MediaType.music;
	}
}
