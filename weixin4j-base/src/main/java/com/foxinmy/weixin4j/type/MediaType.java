package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.http.MimeType;

/**
 * 上传的媒体类型</br>
 * <p>
 * 公众平台上传限制:</br>
 * 图片(image): 2MB,支持bmp/png/jpeg/jpg/gif格式</br>
 * 语音(voice):2MB,播放长度不超过60s,支持mp3/wma/wav/amr格式</br>
 * 视频(video):10MB,支持rm/rmvb/wmv/avi/mpg/mpeg/mp4格式</br>
 * 缩略图(thumb):64KB,支持JPG格式</br>
 * </p>
 * <p>
 * 企业号上传限制:</br>
 * 图片（image）:1MB，支持bmp/png/jpeg/jpg/gif格式</br>
 * 语音（voice）：2MB，播放长度不超过60s，支持mp3/wma/wav/amr格式</br>
 * 视频（video）：10MB，支持rm/rmvb/wmv/avi/mpg/mpeg/mp4格式</br>
 * 普通文件（file）：20MB</br>
 * </p>
 * <p>
 * <font color='red'>临时媒体文件在后台保存时间为3天,即3天后media_id失效</font>
 * </p>
 * 
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月2日
 * @since JDK 1.6
 */
public enum MediaType {
	image(MimeType.IMAGE_JPG), voice(MimeType.AUDIO_MP3), video(MimeType.VIDEO_MPEG4), thumb(MimeType.IMAGE_JPG), file(
			MimeType.MULTIPART_FORM_DATA), news(MimeType.MULTIPART_FORM_DATA);

	MediaType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	private MimeType mimeType;

	public MimeType getMimeType() {
		return mimeType;
	}
}
