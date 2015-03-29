package com.foxinmy.weixin4j.mp.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息
 * 
 * @className TemplateMessage
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html">模板消息</a>
 */
public class TemplateMessage implements Serializable {

	private static final long serialVersionUID = 7950608393821661436L;

	/**
	 * 用户的openid
	 */
	private String touser;
	/**
	 * 模板ID
	 */
	private String template_id;
	/**
	 * 点击消息跳转的url
	 */
	private String url;
	/**
	 * 顶部的颜色值
	 */
	private String topcolor = "#FF0000";
	/**
	 * 数据项
	 */
	private Map<String, Item> data;

	public void pushData(String key, String value) {
		this.data.put(key, new Item(value));
	}

	public TemplateMessage(String touser, String template_id, String title,
			String url) {
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
		this.data = new HashMap<String, Item>();
		pushData("first", title);
	}

	/**
	 * 模板消息的数据项
	 * 
	 * @className Item
	 * @author jy
	 * @date 2015年3月29日
	 * @since JDK 1.7
	 * @see
	 */
	private static class Item implements Serializable {
		private static final long serialVersionUID = 1L;
		/**
		 * 字段值
		 */
		private String value;
		/**
		 * 颜色值
		 */
		private String color;

		public Item(String value) {
			this(value, "#173177");
		}

		public Item(String value, String color) {
			this.value = value;
			this.color = color;
		}

		public String getValue() {
			return value;
		}

		public String getColor() {
			return color;
		}

		@Override
		public String toString() {
			return "$ [value=" + getValue() + ", color=" + getColor() + "]";
		}
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, Item> getData() {
		return data;
	}

	public void setData(Map<String, Item> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TemplateMessage [touser=" + touser + ", template_id="
				+ template_id + ", url=" + url + ", topcolor=" + topcolor
				+ ", data=" + data + "]";
	}
}
