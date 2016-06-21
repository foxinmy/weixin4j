package com.foxinmy.weixin4j.tuple;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 音乐对象
 * <p>
 * <font color="red">可用于「客服消息」</font>
 * </p>
 *
 * @className Music
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see
 */
public class Music implements NotifyTuple {

	private static final long serialVersionUID = -5952134916367253297L;

	@Override
	public String getMessageType() {
		return "music";
	}

	/**
	 * 音乐标题
	 */
	@XmlElement(name = "Title")
	private String title;
	/**
	 * 音乐描述
	 */
	@JSONField(name = "description")
	@XmlElement(name = "Description")
	private String desc;
	/**
	 * 音乐链接
	 */
	@JSONField(name = "musicurl")
	@XmlElement(name = "MusicUrl")
	private String musicUrl;
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	@JSONField(name = "hqmusicurl")
	@XmlElement(name = "HQMusicUrl")
	private String hqMusicUrl;
	/**
	 * 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	@JSONField(name = "thumb_media_id")
	@XmlElement(name = "ThumbMediaId")
	private String thumbMediaId;

	/**
	 *
	 * @param musicUrl
	 *            音乐链接
	 * @param hqMusicUrl
	 *            高品质音乐链接
	 * @param thumbMediaId
	 *            缩略图
	 */
	public Music(String musicUrl, String hqMusicUrl, String thumbMediaId) {
		this(null, null, musicUrl, hqMusicUrl, thumbMediaId);
	}

	/**
	 *
	 * @param title
	 *            标题
	 * @param desc
	 *            描述
	 * @param musicUrl
	 *            音乐链接
	 * @param hqMusicUrl
	 *            高品质音乐链接
	 * @param thumbMediaId
	 *            缩略图
	 */
	public Music(@JSONField(name = "title") String title,
			@JSONField(name = "desc") String desc,
			@JSONField(name = "musicUrl") String musicUrl,
			@JSONField(name = "hqMusicUrl") String hqMusicUrl,
			@JSONField(name = "thumbMediaId") String thumbMediaId) {
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

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	@Override
	public String toString() {
		return "Music [title=" + title + ", desc=" + desc + ", musicUrl="
				+ musicUrl + ", hqMusicUrl=" + hqMusicUrl + ", thumbMediaId="
				+ thumbMediaId + "]";
	}
}
