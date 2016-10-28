package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数据统计:图文群发总数据
 * 
 * @className ArticleTotal
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月30日
 * @since JDK 1.6
 * @see
 */
public class ArticleTotal implements Serializable {
	private static final long serialVersionUID = -6820948857241500950L;

	/**
	 * 数据的日期
	 */
	@JSONField(name = "ref_date")
	private Date refDate;
	/**
	 * 这里的msgid实际上是由msgid（图文消息id）和index（消息次序索引）组成， 例如12003_3，
	 * 其中12003是msgid，即一次群发的id消息的； 3为index，假设该次群发的图文消息共5个文章（因为可能为多图文）， 3表示5个中的第3个
	 */
	@JSONField(name = "msgid")
	private String msgId;
	/**
	 * 图文消息的标题
	 */
	private String title;
	/**
	 * 详细信息
	 */
	private List<ArticleDatacube2> details;

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ArticleDatacube2> getDetails() {
		return details;
	}

	public void setDetails(List<ArticleDatacube2> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ArticleTotal [refDate=" + refDate + ", msgId=" + msgId
				+ ", title=" + title + ", details=" + details + "]";
	}
}
