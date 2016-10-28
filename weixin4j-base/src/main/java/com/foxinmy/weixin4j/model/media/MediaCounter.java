package com.foxinmy.weixin4j.model.media;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 媒体素材总数
 * 
 * @className MediaCounter
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see
 */
public class MediaCounter implements Serializable {

	private static final long serialVersionUID = -1752502821323552783L;

	/**
	 * 应用素材总数目(企业号独有)
	 */
	@JSONField(name = "total_count")
	private int totalCount;
	/**
	 * 文件素材总数目(企业号独有)
	 */
	@JSONField(name = "file_count")
	private int fileCount;
	/**
	 * 语音总数量
	 */
	@JSONField(name = "voice_count")
	private long voiceCount;
	/**
	 * 视频总数量
	 */
	@JSONField(name = "video_count")
	private long videoCount;
	/**
	 * 图片总数量
	 */
	@JSONField(name = "image_count")
	private long imageCount;
	/**
	 * 图文总数量
	 */
	@JSONField(name = "news_count")
	private long newsCount;

	public long getVoiceCount() {
		return voiceCount;
	}

	public void setVoiceCount(long voiceCount) {
		this.voiceCount = voiceCount;
	}

	public long getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(long videoCount) {
		this.videoCount = videoCount;
	}

	public long getImageCount() {
		return imageCount;
	}

	public void setImageCount(long imageCount) {
		this.imageCount = imageCount;
	}

	public long getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(long newsCount) {
		this.newsCount = newsCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	@Override
	public String toString() {
		return "MediaCounter [totalCount=" + totalCount + ", fileCount="
				+ fileCount + ", voiceCount=" + voiceCount + ", videoCount="
				+ videoCount + ", imageCount=" + imageCount + ", newsCount="
				+ newsCount + "]";
	}
}
