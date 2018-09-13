package com.foxinmy.weixin4j.wxa.api;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.model.paging.Pagedata;
import com.foxinmy.weixin4j.wxa.model.template.Template;

class TemplateListResult extends WxaApiResult {

	private static final long serialVersionUID = 2018052602L;

	public static final TypeReference<TemplateListResult> TYPE_REFERENCE
		= new TypeReference<TemplateListResult>() {
		};

	private List<Template> list;
	private Long totalCount;

	public List<Template> getList() {
		return list;
	}

	public void setList(List<Template> list) {
		this.list = list;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Pagedata<Template> toPage(Pageable pageable) throws WeixinException {
		this.checkErrCode();

		final Pagedata<Template> page = new Pagedata<Template>(
			pageable,
			this.getTotalCount().intValue(),
			this.getList()
		);
		return page;
	}

	public List<Template> toList() throws WeixinException {
		this.checkErrCode();

		return this.getList();
	}

}
