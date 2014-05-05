package com.foxinmy.weixin4j.msg.notify;

import java.util.LinkedList;
import java.util.List;

import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 客服图文消息
 * @className ArticleNotify
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF">客服图文消息</a>
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

	private static class News {
		@XStreamAlias("articles")
		private List<$> articles;

		public News() {
			this.articles = new LinkedList<$>();
		}

		public void pushArticle(String title, String desc, String picUrl, String url) {
			this.articles.add(new $(title, desc, picUrl, url));
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for ($ item : articles) {
				sb.append("{title=").append(item.title);
				sb.append(" ,description=").append(item.desc);
				sb.append(" ,picUrl=").append(item.picUrl);
				sb.append(" ,url=").append(item.url).append("}");
			}
			return sb.toString();
		}
	}

	private static class $ {
		private String title; // 图文消息标题
		@XStreamAlias("description")
		private String desc; // 图文消息描述
		@XStreamAlias("picurl")
		private String picUrl; // 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
		@XStreamAlias("url")
		private String url; // 点击图文消息跳转链接

		public $(String title, String desc, String picUrl, String url) {
			this.title = title;
			this.desc = desc;
			this.picUrl = picUrl;
			this.url = url;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(article ,title=").append(title);
			sb.append(" ,description=").append(desc);
			sb.append(" ,picUrl=").append(picUrl);
			sb.append(" ,url=").append(url).append(")");
			return sb.toString();
		}
	}

	@Override
	public String toString() {
		return String.format("[ArticleNotify touser=%s ,msgtype=%s ,articles=%s]", super.getTouser(), super.getMsgtype().name(), this.news.toString());
	}
}
