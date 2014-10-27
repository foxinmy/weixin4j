package com.foxinmy.weixin4j.type;

/**
 * 上传的媒体类型<br/>
 * 图片(image): 128K,支持JPG格式<br/>
 * 语音(voice):256K,播放长度不超过60s,支持AMR\MP3格式<br/>
 * 视频(video):1MB,支持MP4格式<br/>
 * 缩略图(thumb):64KB,支持JPG格式<br/>
 * <p>
 * <font color='red'>媒体文件在后台保存时间为3天,即3天后media_id失效</font>
 * <p/>
 * 
 * @author jy.hu
 * @date 2014年4月2日
 * @since JDK 1.7
 */
public enum MediaType {
	image("image/jpeg", "jpg"), voice("binary/octet-stream", "amr"), vedio(
			"binary/octet-stream", "mp4"), thumb("image/jpeg", "jpg"), text(
			"application/json", ""), music("binary/octet-stream", "mp3"), news(
			"application/json", ""), mpnews("application/json", ""), mpvideo(
			"binary/octet-stream", "");

	MediaType(String contentType, String formatType) {
		this.contentType = contentType;
		this.formatType = formatType;
	}

	private String contentType;

	public String getContentType() {
		return contentType;
	}

	private String formatType;

	public String getFormatType() {
		return formatType;
	}
}
