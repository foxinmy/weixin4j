package com.zone.weixin4j.response;

/**
 * 回复视频消息
 * 
 * @className VideoResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see
 */
public class VideoResponse implements WeixinResponse {

	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String mediaId;
	/**
	 * 视频消息标题
	 */
	private String title;
	/**
	 * 视频消息描述
	 */
	private String desc;

	public VideoResponse(String mediaId) {
		this.mediaId = mediaId;
	}

	public VideoResponse(String mediaId, String title, String desc) {
		this.mediaId = mediaId;
		this.title = title;
		this.desc = desc;
	}

	@Override
	public String toContent() {
		StringBuilder content = new StringBuilder();
		content.append("<Video>");
		content.append(String.format("<MediaId><![CDATA[%s]]></MediaId>",
				mediaId));
		content.append(String.format("<Title><![CDATA[%s]]></Title>",
				title != null ? title : ""));
		content.append(String.format(
				"<Description><![CDATA[%s]]></Description>",
				desc != null ? desc : ""));
		content.append("</Video>");
		return content.toString();
	}

	public String getMediaId() {
		return mediaId;
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
		return "video";
	}
}
