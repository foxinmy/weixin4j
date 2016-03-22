package com.foxinmy.weixin4j.tuple;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 群发消息图文(消息内容存储在微信后台)
 * 
 * @author jy.hu
 * @date 2014年4月26日
 * @since JDK 1.6
 */
public class MpArticle implements Serializable {

	private static final long serialVersionUID = 5583479943661639234L;

	/**
	 * 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得 非空
	 */
	@JSONField(name = "thumb_media_id")
	private String thumbMediaId;
	/**
	 * 图文消息的封面图片的地址，第三方开发者也可以使用这个URL下载图片到自己服务器中，然后显示在自己网站上
	 */
	@JSONField(name = "thumb_url")
	private String thumbUrl;
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
	private String sourceUrl;
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
	/**
	 * 正文的URL 可为空
	 */
	@JSONField(name = "content_url")
	private String contentUrl;
	/**
	 * 封面图片的URL 可为空
	 */
	@JSONField(name = "cover_url")
	private String coverUrl;

	public MpArticle(String thumbMediaId, String title, String content) {
		this.thumbMediaId = thumbMediaId;
		this.title = title;
		this.content = content;
	}

	@JSONCreator
	public MpArticle(@JSONField(name = "thumbMediaId") String thumbMediaId,
			@JSONField(name = "thumbUrl") String thumbUrl,
			@JSONField(name = "author") String author,
			@JSONField(name = "title") String title,
			@JSONField(name = "sourceUrl") String sourceUrl,
			@JSONField(name = "content") String content,
			@JSONField(name = "digest") String digest,
			@JSONField(name = "showCoverPic") String showCoverPic,
			@JSONField(name = "contentUrl") String contentUrl,
			@JSONField(name = "coverUrl") String coverUrl) {
		this.thumbMediaId = thumbMediaId;
		this.thumbUrl = thumbUrl;
		this.author = author;
		this.title = title;
		this.sourceUrl = sourceUrl;
		this.content = content;
		this.digest = digest;
		this.showCoverPic = showCoverPic;
		this.contentUrl = contentUrl;
		this.coverUrl = coverUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
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

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getContent() {
		return content;
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

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	@Override
	public String toString() {
		return "MpArticle [thumbMediaId=" + thumbMediaId + ",thumbUrl="
				+ thumbUrl + ", author=" + author + ", title=" + title
				+ ", sourceUrl=" + sourceUrl + ", content=" + content
				+ ", digest=" + digest + ", showCoverPic=" + showCoverPic
				+ ", contentUrl=" + contentUrl + ", coverUrl=" + coverUrl + "]";
	}
}
