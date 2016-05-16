package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.IndustryType;

/**
 * 模板消息的内容
 * 
 * @className TemplateMessageInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年1月20日
 * @since JDK 1.6
 * @see
 */
public class TemplateMessageInfo implements Serializable {

	private static final long serialVersionUID = 2130414666704670905L;

	/**
	 * 模板ID
	 */
	@JSONField(name = "template_id")
	private String templateId;
	/**
	 * 模板标题
	 */
	private String title;
	/**
	 * 模板所属行业的一级行业
	 */
	@JSONField(name = "primary_industry")
	private String primaryIndustry;
	/**
	 * 模板所属行业的二级行业
	 */
	@JSONField(name = "deputy_industry")
	private String secondaryIndustry;
	/**
	 * 模板内容
	 */
	private String content;
	/**
	 * 模板示例
	 */
	private String example;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public String getSecondaryIndustry() {
		return secondaryIndustry;
	}

	public void setSecondaryIndustry(String secondaryIndustry) {
		this.secondaryIndustry = secondaryIndustry;
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

	public IndustryType getIndustryType() {
		return IndustryType.getIndustry(primaryIndustry, secondaryIndustry);
	}

	@Override
	public String toString() {
		return "TemplateMessageInfo [templateId=" + templateId + ", title="
				+ title + ", primaryIndustry=" + primaryIndustry
				+ ", secondaryIndustry=" + secondaryIndustry + ", content="
				+ content + ", example=" + example + "]";
	}
}
