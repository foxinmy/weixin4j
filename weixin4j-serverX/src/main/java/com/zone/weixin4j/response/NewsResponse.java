package com.zone.weixin4j.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 回复图文消息
 * 
 * @className NewsResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see
 */
public class NewsResponse implements WeixinResponse {

	/**
	 * 图文集合
	 */
	private List<Article> articleList;

	public NewsResponse(List<Article> articleList) {
		this.articleList = articleList;
	}

	public NewsResponse(Article article) {
		this.articleList = new ArrayList<Article>();
		this.articleList.add(article);
	}

	public void pushArticle(Article article) {
		articleList.add(article);
	}

	public void pushFirstArticle(Article article) {
		articleList.add(0, article);
	}

	public void pushLastArticle(Article article) {
		articleList.add(articleList.size(), article);
	}

	public Article removeLastArticle() {
		return articleList.remove(articleList.size() - 1);
	}

	public Article removeFirstArticle() {
		return articleList.remove(0);
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	@Override
	public String toContent() {
		StringBuilder content = new StringBuilder();
		content.append(String.format("<ArticleCount>%d</ArticleCount>",
				articleList.size()));
		content.append("<Articles>");
		for (Article article : articleList) {
			content.append("<item>");
			content.append(String.format("<Title><![CDATA[%s]]></Title>",
					article.getTitle() != null ? article.getTitle() : ""));
			content.append(String.format(
					"<Description><![CDATA[%s]]></Description>",
					article.getDesc() != null ? article.getDesc() : ""));
			content.append(String.format("<Url><![CDATA[%s]]></Url>",
					article.getUrl() != null ? article.getUrl() : ""));
			content.append(String.format("<PicUrl><![CDATA[%s]]></PicUrl>",
					article.getPicUrl() != null ? article.getPicUrl() : ""));
			content.append("</item>");
		}
		content.append("</Articles>");
		return content.toString();
	}

	@Override
	public String getMsgType() {
		return "news";
	}

	/**
	 * 图文消息对象
	 * 
	 * @className Article
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年5月5日
	 * @since JDK 1.6
	 * @see
	 */
	public static class Article {
		/**
		 * 图文消息标题
		 */
		private String title;
		/**
		 * 图文消息描述
		 */
		private String desc;
		/**
		 * 点击图文消息跳转链接
		 */
		private String url;
		/**
		 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
		 */
		private String picUrl;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public Article() {

		}

		public Article(String title, String desc, String url, String picUrl) {
			this.title = title;
			this.desc = desc;
			this.url = url;
			this.picUrl = picUrl;
		}

		@Override
		public String toString() {
			return "Article [title=" + title + ", desc=" + desc + ", url="
					+ url + ", picUrl=" + picUrl + "]";
		}
	}
}
