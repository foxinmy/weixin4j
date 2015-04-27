package com.foxinmy.weixin4j.tuple;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 客服消息图文
 * 
 * @className Article
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Article implements Serializable {

	private static final long serialVersionUID = -1231352700301456395L;

	/**
	 * 图文消息标题
	 */
	@XStreamAlias("Title")
	private String title;
	/**
	 * 图文消息描述
	 */
	@JSONField(name = "description")
	@XStreamAlias("Description")
	private String desc;
	/**
	 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 */
	@JSONField(name = "picurl")
	@XStreamAlias("PicUrl")
	private String picUrl;
	/**
	 *  点击图文消息跳转链接
	 */
	@XStreamAlias("Url")
	private String url;

	public Article(String title, String desc, String picUrl, String url) {
		this.title = title;
		this.desc = desc;
		this.picUrl = picUrl;
		this.url = url;
	}

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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", desc=" + desc + ", picUrl="
				+ picUrl + ", url=" + url + "]";
	}
}