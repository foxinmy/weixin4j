package com.foxinmy.weixin4j.msg.out;

import java.util.LinkedList;
import java.util.List;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复图文消息
 * @className ArticleMessage
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF">回复图文消息</a>
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml() 
 */
public class ArticleMessage extends BaseMessage {
	private static final int MAX_ARTICLE_COUNT = 10;
	private static final long serialVersionUID = -7331603018352309317L;

	public ArticleMessage() {
		super(MessageType.news);
	}

	public ArticleMessage(String toUserName) {
		super(MessageType.news);
		super.setToUserName(toUserName);
	}

	@XStreamAlias("ArticleCount")
	private int count; // 图文消息个数，限制为10条以内
	@XStreamAlias("Articles")
	private List<Article> articles;

	public List<Article> getArticles() {
		return this.articles;
	}

	public void pushArticle(String title, String desc, String picUrl, String url) {
		if ((count + 1) > MAX_ARTICLE_COUNT) {
			return;
		}
		if (this.articles == null) {
			this.articles = new LinkedList<ArticleMessage.Article>();
		}
		this.articles.add(new Article(title, desc, picUrl, url));
		count ++;
	}

	@XStreamAlias("item")
	private static class Article {
		@XStreamAlias("Title")
		private String title; // 图文消息标题
		@XStreamAlias("Description")
		private String desc; // 图文消息描述
		@XStreamAlias("PicUrl")
		private String picUrl; // 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
		@XStreamAlias("Url")
		private String url; // 点击图文消息跳转链接

		public Article(String title, String desc, String picUrl, String url) {
			this.title = title;
			this.desc = desc;
			this.picUrl = picUrl;
			this.url = url;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(Article ,title=").append(title);
			sb.append(" ,description=").append(desc);
			sb.append(" ,picUrl=").append(picUrl);
			sb.append(" ,url=").append(url).append(")");
			return sb.toString();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ArticleMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,articles=").append(getArticles().toString());
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
