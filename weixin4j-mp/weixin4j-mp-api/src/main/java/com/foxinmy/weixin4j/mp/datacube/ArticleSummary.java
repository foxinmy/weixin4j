package com.foxinmy.weixin4j.mp.datacube;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数据统计:图文群发每日数据
 * 
 * @className ArticleSummary
 * @author jy
 * @date 2015年1月30日
 * @since JDK 1.7
 * @see
 */
public class ArticleSummary extends ArticleDatacube1 {
	private static final long serialVersionUID = 4820605570501368550L;
	@JSONField(name = "ref_date")
	private String refDate; // 数据的日期
	@JSONField(name = "ref_hour")
	private int refHour; // 数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
	/**
	 * 这里的msgid实际上是由msgid（图文消息id）和index（消息次序索引）组成， 例如12003_3，
	 * 其中12003是msgid，即一次群发的id消息的； 3为index，假设该次群发的图文消息共5个文章（因为可能为多图文）， 3表示5个中的第3个
	 */
	private String msgid;
	private String title;// 图文消息的标题

	public String getRefDate() {
		return refDate;
	}

	public void setRefDate(String refDate) {
		this.refDate = refDate;
	}

	public int getRefHour() {
		return refHour;
	}

	public void setRefHour(int refHour) {
		this.refHour = refHour;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ArticleSummary [refDate=" + refDate + ", refHour=" + refHour
				+ ", msgid=" + msgid + ", title=" + title + ", "
				+ super.toString() + "]";
	}
}
