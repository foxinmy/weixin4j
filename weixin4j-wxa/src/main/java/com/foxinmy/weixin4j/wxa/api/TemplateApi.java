package com.foxinmy.weixin4j.wxa.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.model.paging.Pagedata;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.wxa.model.template.Template;

/**
 * 模板消息管理。
 *
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/template-message.html">模板消息管理</a>
 * @since 1.8
 */
public class TemplateApi extends TokenManagerApi {

	public TemplateApi(TokenManager tokenManager) {
		this(tokenManager, null);
	}

	public TemplateApi(TokenManager tokenManager, Properties properties) {
		super(tokenManager, properties);
	}

	/**
	 * 获取小程序模板库标题列表
	 *
	 * @param pageable the pagination information.
	 * @return templates in library.
	 * @throws WeixinException indicates getting access token failed or getting templates failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/getTemplateLibraryList.html">获取小程序模板库标题列表</a>
	 */
	public Pagedata<Template> getTemplatesInLibrary(final Pageable pageable) throws WeixinException {
		final TemplateListResult r = post(
			"wxopen_template_library_list",
			toPageableRequestParams(pageable),
			TemplateListResult.TYPE_REFERENCE
		);
		return r.toPage(pageable);
	}

	/**
	 * 获取模板库某个模板标题下关键词库
	 *
	 * @param id 模板标题id，可通过接口获取，也可登录小程序后台查看获取
	 * @return the template in library with specified ID.
	 * @throws WeixinException indicates getting access token failed or getting template failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/getTemplateLibraryById.html">获取模板库某个模板标题下关键词库</a>
	 */
	public Template getTemplateInLibrary(String id) throws WeixinException {
		final Map<String, String> params = new HashMap<String, String>(1);
		params.put("id", id);
		final TemplateResult r = this.post(
			"wxopen_template_library_get",
			params,
			TemplateResult.TYPE_REFERENCE
		);
		return r.toTemplate();
	}

	/**
	 * 组合模板并添加至帐号下的个人模板库
	 *
	 * @param id 模板标题id，可通过接口获取，也可登录小程序后台查看获取
	 * @param keywordIds 开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如[3,5,4]或[4,5,3]），最多支持10个关键词组合
	 * @return the added template ID.
	 * @throws WeixinException indicates getting access token failed or adding template failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/addTemplate.html">组合模板并添加至帐号下的个人模板库</a>
	 */
	public String addTemplate(String id, int[] keywordIds) throws WeixinException {
		final AddTemplateParameter params = new AddTemplateParameter(id, keywordIds);
		final TemplateResult r = this.post(
			"wxopen_template_add",
			params,
			TemplateResult.TYPE_REFERENCE
		);
		return r.toTemplate().getId();
	}

	/**
	 * 获取帐号下已存在的模板列表
	 *
	 * @param pageable the pagination information.
	 * @return the templates.
	 * @throws WeixinException indicates getting access token failed or getting template failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/getTemplateList.html">获取帐号下已存在的模板列表</a>
	 */
	public List<Template> getTemplates(final Pageable pageable) throws WeixinException {
		final TemplateListResult r = post(
			"wxopen_template_list",
			toPageableRequestParams(pageable),
			TemplateListResult.TYPE_REFERENCE
		);
		return r.toList();
	}

	/**
	 * 删除帐号下的某个模板
	 *
	 * @param id 要删除的模板id
	 * @throws WeixinException indicates getting access token failed or deleting template failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/deleteTemplate.html">删除帐号下的某个模板</a>
	 */
	public void deleteTemplate(String id) throws WeixinException {
		final Map<String, String> params = new HashMap<String, String>(1);
		params.put("template_id", id);
		final WxaApiResult r = this.post(
			"wxopen_template_del",
			params,
			WxaApiResult.TYPE_REFERENCE
		);
		r.checkErrCode();
	}

	private Map<String, Integer> toPageableRequestParams(final Pageable pageable) {
		final Map<String, Integer> params = new HashMap<String, Integer>(2);
		params.put("offset", pageable.getOffset());
		params.put("count", pageable.getPageSize());
		return params;
	}

}
