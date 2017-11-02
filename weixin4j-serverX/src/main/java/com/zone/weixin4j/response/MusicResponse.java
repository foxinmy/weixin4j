package com.zone.weixin4j.response;

/**
 * 回复音乐消息
 * 
 * @className MusicResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see
 */
public class MusicResponse implements WeixinResponse {

	/**
	 * 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	private String thumbMediaId;
	/**
	 * 音乐标题
	 */
	private String title;
	/**
	 * 音乐描述
	 */
	private String desc;
	/**
	 * 音乐链接
	 */
	private String musicUrl;
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	private String hqMusicUrl;

	public MusicResponse(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toContent() {
		StringBuilder content = new StringBuilder();
		content.append("<Music>");
		content.append(String.format(
				"<ThumbMediaId><![CDATA[%s]]></ThumbMediaId>", thumbMediaId));
		content.append(String.format("<Title><![CDATA[%s]]></Title>",
				title != null ? title : ""));
		content.append(String.format(
				"<Description><![CDATA[%s]]></Description>",
				desc != null ? desc : ""));
		content.append(String.format("<MusicUrl><![CDATA[%s]]></MusicUrl>",
				musicUrl != null ? musicUrl : ""));
		content.append(String.format("<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>",
				hqMusicUrl != null ? hqMusicUrl : ""));
		content.append("</Music>");
		return content.toString();
	}

	public String getThumbMediaId() {
		return thumbMediaId;
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
	public String getMsgType() {
		return "music";
	}
}
