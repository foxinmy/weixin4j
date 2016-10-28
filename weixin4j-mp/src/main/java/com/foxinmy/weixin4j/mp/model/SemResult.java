package com.foxinmy.weixin4j.mp.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.http.weixin.ApiResult;

/**
 * 语义理解结果
 * 
 * @className SemResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月7日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141241&token=&lang=zh_CN">语义理解</a>
 */
public class SemResult extends ApiResult {

	private static final long serialVersionUID = 9051214458161068387L;
	/**
	 * 用户的输入字符串
	 */
	private String query;
	/**
	 * 服务的全局类型id，详见协议文档中垂直服务协议定义
	 */
	private String type;
	/**
	 * 语义理解后的结构化标识，各服务不同
	 */
	private JSONObject semantic;
	/**
	 * 部分类别的结果
	 */
	private JSONArray result;
	/**
	 * 部分类别的结果html5展示，目前不支持
	 */
	private String answer;
	/**
	 * 特殊回复说明
	 */
	private String text;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject getSemantic() {
		return semantic;
	}

	public void setSemantic(JSONObject semantic) {
		this.semantic = semantic;
	}

	public JSONArray getResult() {
		return result;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "SemResult [" + super.toString() + ", query=" + query + ", type=" + type + ", semantic=" + semantic
				+ ", result=" + result + ", answer=" + answer + ", " + super.toString() + "]";
	}
}
