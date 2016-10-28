package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.List;

import com.foxinmy.weixin4j.model.Button;

/**
 * 自定义菜单配置
 *
 * @className MenuSetting
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年4月14日
 * @since JDK 1.6
 * @see
 */
public class MenuSetting implements Serializable {

	private static final long serialVersionUID = 2461505572495855830L;

	/**
	 * 菜单是否开启
	 */
	private boolean isMenuOpen;
	/**
	 * 菜单列表
	 */
	private List<Button> buttons;

	public MenuSetting(boolean isMenuOpen, List<Button> buttons) {
		this.isMenuOpen = isMenuOpen;
		this.buttons = buttons;
	}

	public boolean isMenuOpen() {
		return isMenuOpen;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	@Override
	public String toString() {
		return "MenuSetting [isMenuOpen=" + isMenuOpen + ", buttons=" + buttons
				+ "]";
	}
}
