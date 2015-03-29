package com.foxinmy.weixin4j.msg.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 群发消息图文(消息内容存储在微信后台)
 * 
 * @author jy.hu
 * @date 2014年4月26日
 * @since JDK 1.7
 */
public class MpArticle implements Serializable {

	private static final long serialVersionUID = 5583479943661639234L;

	/**
	 * 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得 非空
	 */
	@JSONField(name = "thumb_media_id")
	private String thumbMediaId;
	/**
	 * 图文消息的作者 可为空
	 */
	private String author;
	/**
	 * 图文消息的标题 非空
	 */
	private String title;
	/**
	 * 在图文消息页面点击“阅读原文”后的页面 可为空
	 */
	@JSONField(name = "content_source_url")
	private String url;
	/**
	 * 图文消息页面的内容，支持HTML标签 非空
	 */
	private String content;
	/**
	 * 图文消息的描述 可为空
	 */
	private String digest;
	/**
	 * 是否显示封面，1为显示，0为不显示 可为空
	 */
	@JSONField(name = "show_cover_pic")
	private String showCoverPic;

	public MpArticle(String thumbMediaId, String title, String content) {
		this.thumbMediaId = thumbMediaId;
		this.title = title;
		this.content = content;
	}

	public MpArticle() {

	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getShowCoverPic() {
		return showCoverPic;
	}

	public void setShowCoverPic(boolean showCoverPic) {
		this.showCoverPic = showCoverPic ? "1" : "0";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[MpArticle thumbMediaId=").append(thumbMediaId);
		sb.append(", author=").append(author);
		sb.append(", title=").append(title);
		sb.append(", url=").append(url);
		sb.append(", content=").append(content);
		sb.append(", digest=").append(digest);
		sb.append(", showCoverPic=").append(showCoverPic).append("]");
		return sb.toString();
	}
}
