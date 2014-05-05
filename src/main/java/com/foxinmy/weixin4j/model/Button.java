package com.foxinmy.weixin4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 菜单按钮
 * <p>目前自定义菜单最多包括3个一级菜单,每个一级菜单最多包含5个二级菜单,一级菜单最多4个汉字,二级菜单最多7个汉字,多出来的部分将会以"..."代替
 * 请注意,创建自定义菜单后,由于微信客户端缓存,需要24小时微信客户端才会展现出来,建议测试时可以尝试取消关注公众账号后再次关注,则可以看到创建后的效果</p>
 * @className Button
 * @author jy.hu
 * @date 2014年4月5日
 * @since JDK 1.7
 */
public class Button implements Serializable {

	private static final long serialVersionUID = -6422234732203854866L;

	private String name;
	private BtnType type;
	private String key;
	private String url;

	@JSONField(name = "sub_button")
	private List<Button> subs;

	public Button() {
	}

	public Button(String name) {
		this.name = name;
	}

	public Button(String name, String url) {
		this.name = name;
		this.url = url;
		this.type = BtnType.view;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BtnType getType() {
		return type;
	}

	public void setType(BtnType type) {
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

	public void pushSub(Button btn) {
		if (this.subs == null) {
			this.subs = new ArrayList<Button>();
		}
		this.subs.add(btn);
	}

	/**
	 * 按钮类型
	 * <p>click：
	 * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event	的结构给开发者(参考消息接口指南),并且带上按钮中开发者填写的key值,开发者可以通过自定义的key值与用户进行交互；</p>
	 * <p>view：
	 * 用户点击view类型按钮后,微信客户端将会打开开发者在按钮中填写的url值(即网页链接),达到打开网页的目的,建议与网页授权获取用户基本信息接口结合,获得用户的登入个人信息</p>
	 * @className BtnType
	 * @author jy.hu
	 * @date 2014年4月8日
	 * @since JDK 1.7
	 */
	public enum BtnType {
		click, view
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
