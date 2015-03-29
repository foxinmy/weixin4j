package com.foxinmy.weixin4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 菜单按钮
 * <p>
 * 目前自定义菜单最多包括3个一级菜单,每个一级菜单最多包含5个二级菜单,一级菜单最多4个汉字,二级菜单最多7个汉字,多出来的部分将会以"..."代替
 * 请注意,创建自定义菜单后,由于微信客户端缓存,需要24小时微信客户端才会展现出来,建议测试时可以尝试取消关注公众账号后再次关注,则可以看到创建后的效果
 * </p>
 * 
 * @className Button
 * @author jy.hu
 * @date 2014年4月5日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.type.ButtonType
 */
public class Button implements Serializable {

	private static final long serialVersionUID = -6422234732203854866L;

	/**
	 * 菜单标题，不超过16个字节，子菜单不超过40个字节
	 */
	private String name;
	/**
	 * 菜单的响应动作类型
	 * 
	 * @see com.foxinmy.weixin4j.type.ButtonType
	 */
	private ButtonType type;
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;
	/**
	 * view类型必须 网页链接，用户点击菜单可打开链接，不超过256字节
	 */
	private String url;
	/**
	 * 二级菜单数组，个数应为1~5个
	 */
	@JSONField(name = "sub_button")
	private List<Button> subs;

	public Button() {
	}

	/**
	 * 创建一个菜单
	 * @param name 菜单显示的名称
	 * @param value 当buttonType为view时value设置为url,否则为key.
	 * @param buttonType 按钮类型
	 */
	public Button(String name, String value, ButtonType buttonType) {
		this.name = name;
		this.type = buttonType;
		if (buttonType == ButtonType.view) {
			this.url = value;
		} else {
			this.key = value;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ButtonType getType() {
		return type;
	}

	public void setType(ButtonType type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Button> getSubs() {
		return subs;
	}

	public void setSubs(List<Button> subs) {
		this.subs = subs;
	}

	public Button pushSub(Button btn) {
		if (this.subs == null) {
			this.subs = new ArrayList<Button>();
		}
		this.subs.add(btn);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Button name=").append(name);
		sb.append(" ,type=").append(type);
		sb.append(" ,key=").append(key);
		sb.append(" ,url=").append(url);
		if (subs != null && !subs.isEmpty()) {
			sb.append("{");
			for (Button sub : subs) {
				sb.append(sub.toString());
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
}
