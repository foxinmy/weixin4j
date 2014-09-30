package com.foxinmy.weixin4j.msg.notify;

import java.util.LinkedList;
import java.util.List;

import com.foxinmy.weixin4j.msg.model.Article;
import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 客服图文消息
 * 
 * @className ArticleNotify
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF">客服图文消息</a>
 * @see com.foxinmy.weixin.msg.model.Article
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify#toJson()
 */
public class ArticleNotify extends BaseNotify {
	private static final int MAX_ARTICLE_COUNT = 10;
	private static final long serialVersionUID = 1740696901128709998L;

	public ArticleNotify() {
		super(MessageType.news);
	}

	public ArticleNotify(String touser) {
		super(touser, MessageType.news);
	}

	@XStreamAlias("news")
	private News news;

	@XStreamOmitField
	private int count; // 图文消息个数，限制为10条以内

	public void pushArticle(String title, String desc, String picUrl, String url) {
		if ((count + 1) > MAX_ARTICLE_COUNT) {
			return;
		}
		if (this.news == null) {
			this.news = new News();
		}
		this.news.pushArticle(title, desc, picUrl, url);
		count++;
	}

	public void pushAll(List<Article> articles) {
		count = articles.size();
		if (articles.size() > MAX_ARTICLE_COUNT) {
			count = MAX_ARTICLE_COUNT;
			articles = articles.subList(0, count);
		}
		if (this.news == null) {
			this.news = new News();
		}
		this.news.setArticles(articles);
	}

	private static class News {
		@XStreamAlias("articles")
		private List<Article> articles;

		public News() {
			this.articles = new LinkedList<Article>();
		}

		public void pushArticle(String title, String desc, String picUrl,
				String url) {
			this.articles.add(new Article(title, desc, picUrl, url));
		}

		public void setArticles(List<Article> articles) {
			this.articles = articles;
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

	@Override
	public String toString() {
		return String.format(
				"[ArticleNotify touser=%s ,msgtype=%s ,articles=%s]",
				super.getTouser(), super.getMsgtype().name(),
				this.news.toString());
	}
}
