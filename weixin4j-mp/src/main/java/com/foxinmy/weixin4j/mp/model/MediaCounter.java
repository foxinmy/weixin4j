package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 媒体素材总数
 * 
 * @className MediaCounter
 * @author jy
 * @date 2015年3月22日
 * @since JDK 1.7
 * @see
 */
public class MediaCounter implements Serializable {

	private static final long serialVersionUID = -1752502821323552783L;

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

	@Override
	public String toString() {
		return "MediaCounter [voiceCount=" + voiceCount + ", videoCount="
				+ videoCount + ", imageCount=" + imageCount + ", newsCount="
				+ newsCount + "]";
	}
}
