package com.foxinmy.weixin4j.wxa.model.template;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 模板消息的模板。
 *
 * @since 1.8
 */
public class Template implements Serializable {

	private static final long serialVersionUID = 2018052601L;

	private String id;
	private String title;
	private List<Keyword> keywords;
	private String content;
	private String example;

	public Template() {
	}

	public Template(String id, String title, List<Keyword> keywords) {
		this.id = id;
		this.title = title;
		this.keywords = keywords;
	}

	public String getId() {
		return id;
	}

	@JSONField(name = "id", alternateNames = { "template_id" })
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

}
