package com.foxinmy.weixin4j.model.media;

import java.io.Serializable;

import com.foxinmy.weixin4j.http.ContentType;

/**
 * 媒体文件下载结果
 * 
 * @className MediaDownloadResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年7月25日
 * @since JDK 1.6
 * @see
 */
public class MediaDownloadResult implements Serializable {

	private static final long serialVersionUID = -7090523911701729058L;
	/**
	 * 内容
	 */
	private byte[] content;
	/**
	 * 类型
	 */
	private ContentType contentType;
	/**
	 * 文件名
	 */
	private String fileName;

	public byte[] getContent() {
		return content;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public MediaDownloadResult(byte[] content, ContentType contentType,
			String fileName) {
		this.content = content;
		this.contentType = contentType;
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "MediaDownloadResult [content=..., contentType=" + contentType
				+ ", fileName=" + fileName + "]";
	}
}
