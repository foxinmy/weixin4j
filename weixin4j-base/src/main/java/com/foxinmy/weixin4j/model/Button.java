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
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.type.ButtonType
 */
public class Button implements Serializable {

	private static final long serialVersionUID = -6422234732203854866L;

	/**
	 * 菜单标题，不超过16个字节，子菜单不超过40个字节
	 */
	private String name;
	/**
	 * 菜单类型 </br> <font color="red">
	 * 公众平台官网上能够设置的菜单类型有view、text、img、photo、video、voice </font>
	 * 
	 * @see com.foxinmy.weixin4j.type.ButtonType
	 */
	private ButtonType type;
	/**
	 * 菜单KEY值,根据type的类型而定,用于消息接口推送,不超过128字节.
	 * <p>
	 * 官网上设置的自定义菜单：</br> Text:保存文字到value； Img、voice：保存mediaID到value；
	 * Video：保存视频下载链接到value；</br> News：保存图文消息到news_info； View：保存链接到url。</br>
	 * <p>
	 * 使用API设置的自定义菜单：</br>
	 * click、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album
	 * 、</br>
	 * pic_weixin、location_select：保存为key；view：保存为url;media_id、view_limited
	 * ：保存为media_id
	 * </p>
	 * </p>
	 */
	private Serializable content;
	/**
	 * 二级菜单数组，个数应为1~5个
	 */
	@JSONField(name = "sub_button")
	private List<Button> subs;

	protected Button() {
		this.subs = new ArrayList<Button>();
	}

	/**
	 * 创建一个菜单
	 * 
	 * @param name
	 *            菜单显示的名称
	 * @param content
	 *            当buttonType为view时content设置为url,否则为key.
	 * @param type
	 *            按钮类型
	 */
	public Button(String name, String content, ButtonType type) {
		this.name = name;
		this.content = content;
		this.type = type;
		this.subs = new ArrayList<Button>();
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

	public Serializable getContent() {
		return content;
	}

	public void setContent(Serializable content) {
		this.content = content;
	}

	public List<Button> getSubs() {
		return subs;
	}

	public void setSubs(List<Button> subs) {
		this.subs = subs;
	}

	public Button pushSub(Button btn) {
		this.subs.add(btn);
		return this;
	}

	@Override
	public String toString() {
		return "Button [name=" + name + ", type=" + type + ", content="
				+ content + ", subs=" + subs + "]";
	}
}
