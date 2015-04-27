package com.foxinmy.weixin4j.tuple;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 图文对象(消息内容存储在微信后台)
 * <p>
 * <font color="red">可用于「群发消息(其中mediaId与articles请至少保持一个有值)」「企业号的客服消息」</font>
 * </p>
 * 
 * @className MpNews
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class MpNews implements MassTuple, NotifyTuple {

	private static final long serialVersionUID = 8853054484809101524L;

	@Override
	public String getMessageType() {
		return "mpnews";
	}

	/**
	 * 上传图文列表后微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XStreamAlias("MediaId")
	private String mediaId;
	/**
	 * 图文列表
	 */
	@JSONField(name = "articles")
	@XStreamOmitField
	private List<MpArticle> articles;

	public MpNews() {
		this(null);
	}

	public MpNews(String mediaId) {
		this.mediaId = mediaId;
		this.articles = new ArrayList<MpArticle>();
	}

	public void pushArticle(String thumbMediaId, String title, String content) {
		articles.add(new MpArticle(thumbMediaId, title, content));
	}

	public void pushFirstArticle(String thumbMediaId, String title,
			String content) {
		articles.add(0, new MpArticle(thumbMediaId, title, content));
	}

	public void pushLastArticle(String thumbMediaId, String title,
			String content) {
		articles.add(articles.size(), new MpArticle(thumbMediaId, title,
				content));
	}

	public MpArticle removeLastArticle() {
		return articles.remove(articles.size() - 1);
	}

	public MpArticle removeFirstArticle() {
		return articles.remove(0);
	}

	public void setArticles(List<MpArticle> articles) {
		this.articles = articles;
	}

	public List<MpArticle> getArticles() {
		return articles;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toString() {
		return "MpNews [articles=" + articles + ", mediaId=" + mediaId + "]";
	}
}
