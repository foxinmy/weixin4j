package com.foxinmy.weixin4j.wxa.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class SubscribeMessageParameter {

	private static final long serialVersionUID = 2018052601L;

	private String toUser;
	private String templateId;
	private String page;
	private Map<String, SubscribeMessageData> data;

	public SubscribeMessageParameter() {

	}

	public SubscribeMessageParameter(
		String toUser,
		String templateId,
		String page,
		Map<String, String> data
	) {
		this.toUser = toUser;
		this.templateId = templateId;
		this.page = page;
		if (data != null) {
			this.data = new HashMap<String, SubscribeMessageData>(data.size());
			for (Map.Entry<String, String> entry : data.entrySet()) {
				this.data.put(entry.getKey(), new SubscribeMessageData(entry.getValue()));
			}
		}
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

	public Map<String, SubscribeMessageData> getData() {
		return data;
	}

	public void setData(Map<String, SubscribeMessageData> data) {
		this.data = data;
	}


	/**
	 * SubscribeMessageData
	 */
	public static class SubscribeMessageData implements Serializable {

		private static final long serialVersionUID = 2018052601L;

		private String value;

		public SubscribeMessageData() {

		}

		public SubscribeMessageData(String value) {
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
