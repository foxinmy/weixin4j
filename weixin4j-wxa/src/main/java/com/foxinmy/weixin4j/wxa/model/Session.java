package com.foxinmy.weixin4j.wxa.model;

import java.io.Serializable;

/**
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

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

}
