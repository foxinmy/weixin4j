package com.foxinmy.weixin4j.mp.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.NameValue;

/**
 * 模板消息
 * 
 * @className TemplateMessage
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html">模板消息</a>
 */
public class TemplateMessage implements Serializable {

	private static final long serialVersionUID = 7950608393821661436L;

	/**
	 * 用户的openid
	 */
	@JSONField(name = "touser")
	private String toUser;
	/**
	 * 模板ID
	 */
	@JSONField(name = "template_id")
	private String templateId;
	/**
	 * 点击消息跳转的url
	 */
	private String url;
	/**
	 * 头部信息(first第一行)
	 */
	@JSONField(serialize = false)
	private NameValue head;
	/**
	 * 尾部信息(remark最后行)
	 */
	@JSONField(serialize = false)
	private NameValue tail;
	/**
	 * 数据项
	 */
	@JSONField(name = "data")
	private Map<String, NameValue> content;

	private final String HEAD_KEY = "first";
	private final String TAIL_KEY = "remark";

	@JSONCreator
	public TemplateMessage(@JSONField(name = "toUser") String toUser,
			@JSONField(name = "templateId") String templateId,
			@JSONField(name = "title") String title,
			@JSONField(name = "url") String url) {
		this.toUser = toUser;
		this.templateId = templateId;
		this.url = url;
		this.content = new HashMap<String, NameValue>();
	}

	public String getToUser() {
		return toUser;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getUrl() {
		return url;
	}

	public NameValue getHead() {
		return head == null ? content.get(HEAD_KEY) : head;
	}

	public NameValue getTail() {
		return tail == null ? content.get(TAIL_KEY) : tail;
	}

	public Map<String, NameValue> getContent() {
		return content;
	}

	/**
	 * 新增头部字段(默认颜色为#FF0000)
	 * 
	 * @param text
	 *            字段文本
	 * @return
	 */
	public TemplateMessage pushHead(String text) {
		return pushHead("#FF0000", text);
	}

	/**
	 * 新增头部字段
	 * 
	 * @param color
	 *            文字颜色
	 * @param text
	 *            字段文本
	 * @return
	 */
	public TemplateMessage pushHead(String color, String text) {
		head = new NameValue(color, text);
		content.put(HEAD_KEY, head);
		return this;
	}

	/**
	 * 新增尾部字段(默认颜色为#173177)
	 * 
	 * @param text
	 *            字段文本
	 * @return
	 */
	public TemplateMessage pushTail(String text) {
		return pushTail("#173177", text);
	}

	/**
	 * 新增尾部字段
	 * 
	 * @param color
	 *            文字颜色
	 * @param text
	 *            字段文本
	 * @return
	 */
	public TemplateMessage pushTail(String color, String text) {
		tail = new NameValue(color, text);
		content.put(TAIL_KEY, tail);
		return this;
	}

	/**
	 * 新增字段项(默认颜色为#173177)
	 * 
	 * @param key
	 *            预留的字段名
	 * @param text
	 *            字段文本
	 * @return
	 */
	public TemplateMessage pushItem(String key, String text) {
		return pushItem(key, "#173177", text);
	}

	/**
	 * 新增字段项
	 * 
	 * @param key
	 *            预留的字段名
	 * @param color
	 *            文字颜色
	 * @param text
	 *            字段文本
	 * @return
	 */
	public TemplateMessage pushItem(String key, String color, String text) {
		content.put(key, new NameValue(color, text));
		return this;
	}

	@Override
	public String toString() {
		return "TemplateMessage [toUser=" + toUser + ", templateId="
				+ templateId + ", url=" + url + ", head=" + getHead()
				+ ", tail=" + getTail() + ", content=" + content + "]";
	}
}
