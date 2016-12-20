package com.foxinmy.weixin4j.model.media;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.TypeUtils;
import com.foxinmy.weixin4j.tuple.MpArticle;

/**
 * 媒体素材信息
 * 
 * @className MediaItem
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see
 */
public class MediaItem implements Serializable {

	private static final long serialVersionUID = -2923028664954250134L;

	/**
	 * 媒体素材ID
	 */
	@JSONField(name = "media_id")
	private String mediaId;
	/**
	 * 媒体素材名称
	 */
	@JSONField(name = "name")
	private String name;
	/**
	 * 图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL
	 */
	@JSONField(name = "url")
	private String url;
	/**
	 * 媒体素材最后更新时间
	 */
	@JSONField(name = "update_time")
	private String updateTime;
	/**
	 * 图文素材列表
	 */
	@JSONField(name = "articles")
	private List<MpArticle> articles;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	@JSONField(serialize = false)
	public Date getForamtUpdateTime() {
		return updateTime != null ? TypeUtils.castToDate(updateTime) : null;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<MpArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<MpArticle> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "MediaItem [mediaId=" + mediaId + ", name=" + name + ",url="
				+ url + ", updateTime=" + updateTime + ", articles=" + articles
				+ "]";
	}
}
