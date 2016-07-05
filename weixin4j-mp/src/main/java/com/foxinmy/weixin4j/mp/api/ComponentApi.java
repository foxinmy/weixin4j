package com.foxinmy.weixin4j.mp.api;

import com.foxinmy.weixin4j.mp.component.WeixinComponentPreCodeCreator;
import com.foxinmy.weixin4j.mp.component.WeixinComponentTokenCreator;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 第三方应用组件
 *
 * @className ComponentApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a href=
 *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=&lang=zh_CN">第三方应用组件概述</a>
 */
public class ComponentApi extends MpApi {
	/**
	 * 应用套件token
	 */
	private final TokenManager tokenManager;
	/**
	 * 应用套件ticket
	 */
	private final TicketManager ticketManager;
	/**
	 * 应用套件pre_code
	 */
	private final TokenManager preCodeManager;

	/**
	 *
	 * @param ticketManager
	 *            组件ticket存取
	 */
	public ComponentApi(TicketManager ticketManager) {
		this.ticketManager = ticketManager;
		this.tokenManager = new TokenManager(new WeixinComponentTokenCreator(ticketManager),
				ticketManager.getCacheStorager());
		this.preCodeManager = new TokenManager(new WeixinComponentPreCodeCreator(tokenManager, ticketManager.getId()),
				ticketManager.getCacheStorager());
	}

	/**
	 * 应用组件token
	 *
	 * @return
	 */
	public TokenManager getTokenManager() {
		return this.tokenManager;
	}

	/**
	 * 应用组件ticket
	 *
	 * @return
	 */
	public TicketManager getTicketManager() {
		return this.ticketManager;
	}

	/**
	 * 应用组件预授权码
	 *
	 * @return
	 */
	public TokenManager getPreCodeManager() {
		return this.preCodeManager;
	}
}
