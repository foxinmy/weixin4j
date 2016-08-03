package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.KfOnlineStatus;

/**
 * 多客服在线信息
 * 
 * @className KfOnlineAccount
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月16日
 * @since JDK 1.6
 * @see <a href="http://dkf.qq.com/document-3_1.html">多客服账号信息</a>
 */
public class KfOnlineAccount implements Serializable {

	private static final long serialVersionUID = -4565570894727129245L;
	/**
	 * 客服工号
	 */
	@JSONField(name = "kf_id")
	private String id;
	/**
	 * 客服账号@微信别名 微信别名如有修改，旧账号返回旧的微信别名，新增的账号返回新的微信别名
	 */
	@JSONField(name = "kf_account")
	private String account;
	/**
	 * 客服在线状态 1：pc在线，2：手机在线 若pc和手机同时在线则为 1+2=3
	 */
	private int status;
	/**
	 * 客服设置的最大自动接入数
	 */
	@JSONField(name = "auto_accept")
	private int autoAccept;
	/**
	 * 客服当前正在接待的会话数
	 */
	@JSONField(name = "accepted_case")
	private int acceptedCase;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	@JSONField(serialize = false)
	public KfOnlineStatus getFormatStatus() {
		if (status == 1) {
			return KfOnlineStatus.PC;
		} else if (status == 2) {
			return KfOnlineStatus.MOBILE;
		} else {
			return KfOnlineStatus.BOTH;
		}
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAutoAccept() {
		return autoAccept;
	}

	public void setAutoAccept(int autoAccept) {
		this.autoAccept = autoAccept;
	}

	public int getAcceptedCase() {
		return acceptedCase;
	}

	public void setAcceptedCase(int acceptedCase) {
		this.acceptedCase = acceptedCase;
	}

	@Override
	public String toString() {
		return "KfAccount [account=" + account + ", id=" + id + ", status="
				+ status + ", autoAccept=" + autoAccept + ", acceptedCase="
				+ acceptedCase + "]";
	}
}
