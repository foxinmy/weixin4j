package com.foxinmy.weixin4j.tuple;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服消息图文
 *
 * @className Article
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see
 */
public class Article implements Serializable {

	private static final long serialVersionUID = -1231352700301456395L;

	/**
	 * 图文消息标题
	 */
	@XmlElement(name = "Title")
	private String title;
	/**
	 * 图文消息描述
	 */
	@JSONField(name = "description")
	@XmlElement(name = "Description")
	private String desc;
	/**
	 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 */
	@JSONField(name = "picurl")
	@XmlElement(name = "PicUrl")
	private String picUrl;
	/**
	 * 点击图文消息跳转链接
	 */
	@XmlElement(name = "Url")
	private String url;

	/**
	 *
	 * @param title
	 *            标题
	 * @param desc
	 *            描述
	 * @param picUrl
	 *            图片链接
	 * @param url
	 *            跳转URL
	 */
	@JSONCreator
	public Article(@JSONField(name = "title") String title,
			@JSONField(name = "desc") String desc,
			@JSONField(name = "picUrl") String picUrl,
			@JSONField(name = "url") String url) {
		this.title = title;
		this.desc = desc;
		this.picUrl = picUrl;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", desc=" + desc + ", picUrl="
				+ picUrl + ", url=" + url + "]";
	}
}