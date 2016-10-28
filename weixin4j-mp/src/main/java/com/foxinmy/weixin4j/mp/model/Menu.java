package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.List;

import com.foxinmy.weixin4j.model.Button;

/**
 * 底部菜单
 * 
 * @className Menu
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月18日
 * @since JDK 1.7
 * @see
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = -915139819768888593L;

	private String menuId;
	private List<Button> buttons;
	private MenuMatchRule matchRule;

	public Menu(String menuId, List<Button> buttons, MenuMatchRule matchRule) {
		this.menuId = menuId;
		this.buttons = buttons;
		this.matchRule = matchRule;
	}

	public String getMenuId() {
		return menuId;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public MenuMatchRule getMatchRule() {
		return matchRule;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", buttons=" + buttons
				+ ", matchRule=" + matchRule + "]";
	}
}
