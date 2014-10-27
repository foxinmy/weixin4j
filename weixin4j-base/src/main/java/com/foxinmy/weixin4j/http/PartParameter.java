package com.foxinmy.weixin4j.http;

import org.apache.http.entity.mime.content.ContentBody;

public class PartParameter {
	private String name;
	private ContentBody contentBody;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContentBody getContentBody() {
		return contentBody;
	}

	public void setContentBody(ContentBody contentBody) {
		this.contentBody = contentBody;
	}

	public PartParameter(String name, ContentBody contentBody) {
		super();
		this.name = name;
		this.contentBody = contentBody;
	}

	@Override
	public String toString() {
		return "PartParameter [name=" + name + ", contentBody=" + contentBody + "]";
	}
}
