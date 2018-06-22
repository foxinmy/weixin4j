package com.foxinmy.weixin4j.wxa.api;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.wxa.model.template.Keyword;
import com.foxinmy.weixin4j.wxa.model.template.Template;

class TemplateResult extends WxaApiResult {

	private static final long serialVersionUID = 2018052601L;

	public static final TypeReference<TemplateResult> TYPE_REFERENCE
		= new TypeReference<TemplateResult>() {
		};

	private String id;
	private String title;
	private List<Keyword> keywords;

	public String getId() {
		return id;
	}

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

	@JSONField(name = "keyword_list")
	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public Template toTemplate() throws WeixinException {
		this.checkErrCode();

		return new Template(id, title, keywords);
	}

}
