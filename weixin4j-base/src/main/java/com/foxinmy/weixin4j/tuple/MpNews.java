package com.foxinmy.weixin4j.tuple;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图文对象(mpnews消息与news消息类似，不同的是图文消息内容存储在微信后台，并且支持保密选项。每个应用每天最多可以发送100次)
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

	/**
	 * 允许最多的图文列表数
	 */
	private static final int MAX_ARTICLE_COUNT = 10;

	@Override
	public String getMessageType() {
		return "mpnews";
	}

	/**
	 * 上传图文列表后微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XmlElement(name = "MediaId")
	private String mediaId;
	/**
	 * 图文列表
	 */
	@XmlTransient
	private LinkedList<MpArticle> articles;

	public MpNews() {
		this(null);
	}

	@JSONCreator
	public MpNews(@JSONField(name = "mediaId") String mediaId) {
		this.mediaId = mediaId;
		this.articles = new LinkedList<MpArticle>();
	}

	public MpNews addArticle(String thumbMediaId, String title, String content) {
		return addArticle(new MpArticle(thumbMediaId, title, content));
	}

	public MpNews addArticle(MpArticle... articles) {
		for (MpArticle article : articles) {
			this.articles.add(article);
		}
		return this;
	}

	public MpNews addFirstArticle(MpArticle article) {
		articles.addFirst(article);
		return this;
	}

	public MpNews addLastArticle(MpArticle article) {
		articles.addLast(article);
		return this;
	}

	public MpNews removeFirstArticle() {
		articles.removeFirst();
		return this;
	}

	public MpNews removeLastArticle() {
		articles.removeLast();
		return this;
	}

	@JSONField(serialize = false)
	@XmlTransient
	public boolean isMaxCount() {
		return articles.size() == MAX_ARTICLE_COUNT;
	}

	public List<MpArticle> getArticles() {
		if (articles.size() > MAX_ARTICLE_COUNT) {
			return articles.subList(0, MAX_ARTICLE_COUNT);
		} else {
			return articles;
		}
	}

	@JSONField(serialize = false)
	@XmlTransient
	public List<MpArticle> getFullArticles() {
		return articles;
	}

	public String getMediaId() {
		return mediaId;
	}

	@Override
	public String toString() {
		return "MpNews [articles=" + articles + ", mediaId=" + mediaId + "]";
	}
}
