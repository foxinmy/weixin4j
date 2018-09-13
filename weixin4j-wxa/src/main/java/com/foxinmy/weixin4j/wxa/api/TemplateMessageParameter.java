package com.foxinmy.weixin4j.wxa.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

class TemplateMessageParameter implements Serializable {

	private static final long serialVersionUID = 2018052601L;

	private String toUser;
	private String templateId;
	private String page;
	private String formId;
	private Map<String, TemplateMessageData> data;
	private String emphasisKeyword;

	public TemplateMessageParameter() {
	}

	public TemplateMessageParameter(
		String toUser,
		String templateId,
		String page,
		String formId,
		Map<String, String> data,
		String emphasisKeyword
	) {
		this.toUser = toUser;
		this.templateId = templateId;
		this.page = page;
		this.formId = formId;
		if (data != null) {
			this.data = new HashMap<String, TemplateMessageData>(data.size());
			for (Map.Entry<String, String> entry : data.entrySet()) {
				this.data.put(entry.getKey(), new TemplateMessageData(entry.getValue()));
			}
		}
		this.emphasisKeyword = emphasisKeyword;
	}

	@JSONField(name = "touser")
	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	@JSONField(name = "template_id")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@JSONField(name = "form_id")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public Map<String, TemplateMessageData> getData() {
		return data;
	}

	public void setData(Map<String, TemplateMessageData> data) {
		this.data = data;
	}

	@JSONField(name = "emphasis_keyword")
	public String getEmphasisKeyword() {
		return emphasisKeyword;
	}

	public void setEmphasisKeyword(String emphasisKeyword) {
		this.emphasisKeyword = emphasisKeyword;
	}

	public static class TemplateMessageData implements Serializable {

		private static final long serialVersionUID = 2018052601L;

		private String value;

		public TemplateMessageData(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
