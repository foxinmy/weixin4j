package com.foxinmy.weixin4j.tuple;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 图文对象
 * <p>
 * <font color="red">可用于「客服消息」</font>
 * </p>
 * 
 * @className News
 * @author jy
 * @date 2014年11月21日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("Articles")
public class News implements NotifyTuple {

	private static final long serialVersionUID = 3348756809039388415L;

	@Override
	public String getMessageType() {
		return "news";
	}

	private static final int MAX_ARTICLE_COUNT = 10;

	/**
	 * 图文列表
	 * 
	 * @see com.foxinmy.weixin4j.tuple.Article
	 */
	@JSONField(name = "articles")
	@XStreamAlias("Articles")
	private List<Article> articles;

	public News() {
		this.articles = new ArrayList<Article>();
	}

	public void pushArticle(String title, String desc, String picUrl, String url) {
		if ((articles.size() + 1) > MAX_ARTICLE_COUNT) {
			return;
		}
		articles.add(new Article(title, desc, picUrl, url));
	}

	public void pushFirstArticle(String title, String desc, String picUrl,
			String url) {
		articles.add(0, new Article(title, desc, picUrl, url));
	}

	public void pushLastArticle(String title, String desc, String picUrl,
			String url) {
		articles.add(articles.size(), new Article(title, desc, picUrl, url));
	}

	public Article removeLastArticle() {
		return articles.remove(articles.size() - 1);
	}

	public Article removeFirstArticle() {
		return articles.remove(0);
	}

	@JSONField(serialize = false)
	public boolean isMaxCount() {
		return this.articles.size() == MAX_ARTICLE_COUNT;
	}

	public void setArticles(List<Article> articles) {
		if (articles.size() > MAX_ARTICLE_COUNT) {
			articles = articles.subList(0, MAX_ARTICLE_COUNT);
		}
		this.articles = articles;
	}

	public List<Article> getArticles() {
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
