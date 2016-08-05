package com.foxinmy.weixin4j.tuple;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图文对象
 * <p>
 * <font color="red">可用于「客服消息」</font>
 * </p>
 *
 * @className News
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月21日
 * @since JDK 1.6
 * @see
 */
public class News implements NotifyTuple {

	private static final long serialVersionUID = 3348756809039388415L;

	@Override
	public String getMessageType() {
		return "news";
	}

	/**
	 * 允许最多的图文列表数
	 */
	private static final int MAX_ARTICLE_COUNT = 8;

	/**
	 * 图文列表
	 *
	 * @see com.foxinmy.weixin4j.tuple.Article
	 */
	@JSONField(name = "articles")
	@XmlElement(name = "Articles")
	private LinkedList<Article> articles;

	public News() {
		this.articles = new LinkedList<Article>();
	}

	/**
	 *
	 * @param title
	 *            标题
	 * @param desc
	 *            描述
	 * @param picUrl
	 *            图片链接
	 * @param url
	 *            跳转URL
	 */
	public News addArticle(String title, String desc, String picUrl, String url) {
		return addArticle(new Article(title, desc, picUrl, url));
	}

	public News addArticle(Article... articles) {
		for (Article article : articles) {
			this.articles.add(article);
		}
		return this;
	}

	public News addFirstArticle(Article article) {
		articles.addFirst(article);
		return this;
	}

	public void addLastArticle(Article article) {
		articles.addLast(article);
	}

	public News removeFirstArticle() {
		articles.removeFirst();
		return this;
	}

	public News removeLastArticle() {
		articles.removeLast();
		return this;
	}

	@JSONField(serialize = false)
	@XmlTransient
	public boolean isMaxCount() {
		return articles.size() == MAX_ARTICLE_COUNT;
	}

	public List<Article> getArticles() {
		if (articles.size() > MAX_ARTICLE_COUNT) {
			return articles.subList(0, MAX_ARTICLE_COUNT);
		} else {
			return articles;
		}
	}

	@JSONField(serialize = false)
	@XmlTransient
	public List<Article> getFullArticles() {
		return articles;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Article article : articles) {
			sb.append("{title=").append(article.getTitle());
			sb.append(" ,description=").append(article.getDesc());
			sb.append(" ,picUrl=").append(article.getPicUrl());
			sb.append(" ,url=").append(article.getUrl()).append("}");
		}
		return sb.toString();
	}
}
