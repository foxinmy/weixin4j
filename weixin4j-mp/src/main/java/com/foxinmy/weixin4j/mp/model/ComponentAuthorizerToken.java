package com.foxinmy.weixin4j.mp.model;

import java.util.List;

import com.foxinmy.weixin4j.model.Token;

/**
 * 授权码换取公众号的授权信息
 * 
 * @className ComponentAuthorizerToken
 * @author jinyu(foxinmy@gmail.com)
 * @date 2017年8月13日
 * @since JDK 1.7
 * @see
 */
public class ComponentAuthorizerToken extends Token {

	private static final long serialVersionUID = 1L;

	/**
	 * 授权方appid
	 */
	private String appId;

	/**
	 * 接口调用凭据刷新令牌（在授权的公众号具备API权限时，才有此返回值），刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，
	 * 只会在授权时刻提供，请妥善保存。 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
	 */
	private String refreshToken;
	/**
	 * 公众号授权给开发者的权限集列表，ID为1到15时分别代表： 消息管理权限 用户管理权限 帐号服务权限 网页服务权限 微信小店权限 微信多客服权限
	 * 群发与通知权限 微信卡券权限 微信扫一扫权限 微信连WIFI权限 素材管理权限 微信摇周边权限 微信门店权限 微信支付权限 自定义菜单权限
	 */
	private List<Integer> privileges;

	public ComponentAuthorizerToken(String accessToken, long expires) {
		super(accessToken, expires);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public List<Integer> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Integer> categoryIds) {
		this.privileges = categoryIds;
	}

	@Override
	public String toString() {
		return "ComponentAuthorizerToken [appId=" + appId + ", refreshToken="
				+ refreshToken + ", privileges=" + privileges + ", "
				+ super.toString() + "]";
	}
}
