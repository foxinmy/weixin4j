package com.foxinmy.weixin4j.tuple;

/**
 * 文本卡片对象
 * <p>
 * <font color="red">企业微信的「通知消息」</font>
 * </p>
 * 
 * @className Text
 * @author caoyiheng(ebir@qq.com)
 * @date 2018年10月22日
 * @since JDK 1.8
 * @see
 */
public class TextCard implements NotifyTuple {

	private static final long serialVersionUID = 1L;

	private String title;
	private String description;
	private String url;
	private String btntxt;

	public TextCard() {
	}

	public TextCard(String title, String description, String url, String btntxt) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.btntxt = btntxt;
	}

	@Override
	public String getMessageType() {
		return "textcard";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBtntxt() {
		return btntxt;
	}

	public void setBtntxt(String btntxt) {
		this.btntxt = btntxt;
	}

	@Override
	public String toString() {
		return "TextCard {title=" + title + ", description=" + description + ", url=" + url + ", btntxt=" + btntxt
				+ "}";
	}

}
