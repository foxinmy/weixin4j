package com.foxinmy.weixin4j.wxa.model;

import java.io.Serializable;

import com.foxinmy.weixin4j.wxa.WXBizDataCrypt;

/**
 * 登录会话。
 *
 * @since 1.8
 */
public class Session implements Serializable {

	private static final long serialVersionUID = 2018051801L;

	private String openId;
	private String sessionKey;
	private String unionId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 返回会话密钥
	 * <p>注意：会话密钥 session_key 是对用户数据进行
	 * <a href="https://developers.weixin.qq.com/miniprogram/dev/api/signature.html#wxchecksessionobject">加密签名</a>
	 * 的密钥。
	 * 为了应用自身的数据安全，开发者服务器不应该把会话密钥下发到小程序，
	 * 也不应该对外提供这个密钥。</p>
	 *
	 * @return 会话密钥
	 * @see WXBizDataCrypt
	 */
	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * 返回UnionID
	 * <p>
	 * UnionID 只在满足一定条件的情况下返回。
	 * 具体参看 <a href="https://developers.weixin.qq.com/miniprogram/dev/api/unionID.html">UnionID机制说明</a>。
	 * </p>
	 *
	 * @return UnionID
	 */
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

}
