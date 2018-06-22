package com.foxinmy.weixin4j.wxa.api;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

class AddTemplateParameter implements Serializable {

	private static final long serialVersionUID = 2018052601L;

	private String id;
	private int[] keywordIds;

	public AddTemplateParameter() {
	}

	public AddTemplateParameter(String id, int[] keywordIds) {
		this.id = id;
		this.keywordIds = keywordIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JSONField(name = "keyword_id_list")
	public int[] getKeywordIds() {
		return keywordIds;
	}

	public void setKeywordIds(int[] keywordIds) {
		this.keywordIds = keywordIds;
	}

}
