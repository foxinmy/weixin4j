package com.foxinmy.weixin4j.msg.out;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 模板消息
 * 
 * @className TemplateMessage
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%A8%A1%E6%9D%BF%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3">模板消息</a>
 */
public class TemplateMessage implements Serializable {

	private static final long serialVersionUID = 7950608393821661436L;

	private String touser;
	private String template_id;
	private String url;
	private String topcolor = "#FF0000";
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

	private static class Item implements Serializable {
		private static final long serialVersionUID = 1L;
		private String value;
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

	@JSONField(serialize = false)
	public String toJson() {
		return JSON.toJSONString(this);
	}
}
