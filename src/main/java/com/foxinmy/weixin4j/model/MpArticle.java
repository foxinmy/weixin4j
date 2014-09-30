package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 群发图文消息
 * 
 * @author jy.hu
 * @date 2014年4月26日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF.E7.B4.A0.E6.9D.90">群发消息描述</a>
 */
public class MpArticle implements Serializable {

	private static final long serialVersionUID = 5583479943661639234L;

	@JSONField(name = "thumb_media_id")
	private String thumbMediaId; // 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得 非空
	private String author;// 图文消息的作者 可为空
	private String title;// 图文消息的标题 非空
	@JSONField(name = "content_source_url")
	private String url;// 在图文消息页面点击“阅读原文”后的页面 可为空
	private String content;// 图文消息页面的内容，支持HTML标签 非空
	private String digest;// 图文消息的描述 可为空
	@JSONField(name = "show_cover_pic")
	private short showCoverPic; // 是否显示封面，1为显示，0为不显示 可为空
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
	public short getShowCoverPic() {
		return showCoverPic;
	}
	public void setShowCoverPic(short showCoverPic) {
		this.showCoverPic = showCoverPic;
	}
	public MpArticle(String thumbMediaId, String title, String content) {
		this.thumbMediaId = thumbMediaId;
		this.title = title;
		this.content = content;
	}

	public MpArticle() {

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
