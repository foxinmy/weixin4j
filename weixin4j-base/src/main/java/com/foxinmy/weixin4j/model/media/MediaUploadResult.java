package com.foxinmy.weixin4j.model.media;

import java.io.Serializable;
import java.util.Date;

import com.foxinmy.weixin4j.type.MediaType;

/**
 * 媒体文件上传结果
 *
 * @className MediaUploadResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年7月25日
 * @since JDK 1.6
 * @see
 */
public class MediaUploadResult implements Serializable {

	private static final long serialVersionUID = -620630472640999536L;
	private String mediaId;
	private MediaType mediaType;
	private Date createdAt;
	/**
	 * 新增的图片素材的图片URL
	 */
	private String url;

	public MediaUploadResult(String mediaId, MediaType mediaType,
			Date createdAt, String url) {
		this.mediaId = mediaId;
		this.mediaType = mediaType;
		this.createdAt = createdAt;
		this.url = url;
	}

	public String getMediaId() {
		return mediaId;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "MediaUploadResult [mediaId=" + mediaId + ", mediaType="
				+ mediaType + ", createdAt=" + createdAt + ", url=" + url + "]";
	}
}
