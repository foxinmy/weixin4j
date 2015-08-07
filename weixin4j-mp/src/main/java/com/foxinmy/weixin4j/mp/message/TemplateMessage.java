package com.foxinmy.weixin4j.mp.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

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
	 * 顶部的颜色值
	 */
	@JSONField(name = "topcolor")
	private String topColor = "#FF0000";
	/**
	 * 数据项
	 */
	private Map<String, Item> data;

	@JSONCreator
	public TemplateMessage(@JSONField(name = "toUser") String toUser,
			@JSONField(name = "templateId") String templateId,
			@JSONField(name = "title") String title,
			@JSONField(name = "url") String url) {
		this.toUser = toUser;
		this.templateId = templateId;
		this.url = url;
		this.topColor = "#FF0000";
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

	public String getToUser() {
		return toUser;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getUrl() {
		return url;
	}

	public String getTopColor() {
		return topColor;
	}

	public void setTopColor(String topColor) {
		this.topColor = topColor;
	}

	public Map<String, Item> getData() {
		return data;
	}

	public void setData(Map<String, Item> data) {
		this.data = data;
	}

	public void pushData(String key, String value) {
		this.data.put(key, new Item(value));
	}

	@Override
	public String toString() {
		return "TemplateMessage [toUser=" + toUser + ", templateId="
				+ templateId + ", url=" + url + ", topColor=" + topColor
				+ ", data=" + data + "]";
	}
}
