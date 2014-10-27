package com.foxinmy.weixin4j.msg.out;

import java.util.LinkedList;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.model.Article;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复图文消息
 * 
 * @className ArticleMessage
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF">回复图文消息</a>
 * @see com.foxinmy.weixin.msg.model.Article
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()
 */
public class ArticleMessage extends BaseMessage {
	private static final int MAX_ARTICLE_COUNT = 10;
	private static final long serialVersionUID = -7331603018352309317L;

	public ArticleMessage(BaseMessage inMessage) {
		super(MessageType.news, inMessage);
	}

	@XStreamAlias("ArticleCount")
	private int count; // 图文消息个数，限制为10条以内
	@XStreamAlias("Articles")
	private LinkedList<Article> articles;

	public void pushArticle(String title, String desc, String picUrl, String url) {
		if (this.articles == null) {
			this.articles = new LinkedList<Article>();
		}
		if ((articles.size() + 1) > MAX_ARTICLE_COUNT) {
			return;
		}
		this.articles.add(new Article(title, desc, picUrl, url));
	}

	public void pushFirstArticle(String title, String desc, String picUrl,
			String url) {
		if (this.articles == null) {
			this.articles = new LinkedList<Article>();
		}
		this.articles.addFirst(new Article(title, desc, picUrl, url));
	}

	public void pushLastArticle(String title, String desc, String picUrl,
			String url) {
		if (this.articles == null) {
			this.articles = new LinkedList<Article>();
		}
		this.articles.addLast(new Article(title, desc, picUrl, url));
	}

	public Article removeLastArticle() {
		Article article = null;
		if (this.articles != null) {
			article = this.articles.removeLast();
		}
		return article;
	}

	public Article removeFirstArticle() {
		if (this.articles != null) {
			return this.articles.removeFirst();
		}
		return null;
	}

	public boolean isMaxCount() {
		return this.articles.size() == MAX_ARTICLE_COUNT;
	}

	@Override
	public String toXml() {
		this.count = articles.size();
		XStream xstream = getXStream();
		xstream.alias("item", Article.class);
		xstream.aliasField("Title", Article.class, "title");
		xstream.aliasField("Description", Article.class, "desc");
		xstream.aliasField("PicUrl", Article.class, "picUrl");
		xstream.aliasField("Url", Article.class, "url");
		return xstream.toXML(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ArticleMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,articles=").append(this.articles.toString());
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
