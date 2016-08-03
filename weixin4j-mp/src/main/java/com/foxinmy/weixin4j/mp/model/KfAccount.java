package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.KfInviteStatus;

/**
 * 多客服账号信息
 * 
 * @className KfAccount
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月16日
 * @since JDK 1.6
 * @see <a href="http://dkf.qq.com/document-3_1.html">多客服账号信息</a>
 */
public class KfAccount implements Serializable {

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
	 * 客服昵称
	 */
	@JSONField(name = "kf_nick")
	private String nickName;
	/**
	 * 客服头像
	 */
	@JSONField(name = "kf_headimgurl")
	private String headimgurl;
	/**
	 * 客服微信
	 */
	@JSONField(name = "kf_wx")
	private String wx;
	/**
	 * 客服绑定邀请的微信号
	 */
	@JSONField(name = "invite_wx")
	private String inviteWx;
	/**
	 * 客服邀请的过期时间
	 */
	@JSONField(name = "invite_expire_time")
	private long inviteExpireTime;
	/**
	 * 客服邀请的状态
	 */
	@JSONField(name = "invite_status")
	private String inviteStatus;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getWx() {
		return wx;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}

	public String getInviteWx() {
		return inviteWx;
	}

	public void setInviteWx(String inviteWx) {
		this.inviteWx = inviteWx;
	}

	public long getInviteExpireTime() {
		return inviteExpireTime;
	}

	@JSONField(serialize = false)
	public Date getFormatInviteExpireTime() {
		return new Date(inviteExpireTime * 1000l);
	}

	public void setInviteExpireTime(long inviteExpireTime) {
		this.inviteExpireTime = inviteExpireTime;
	}

	public String getInviteStatus() {
		return inviteStatus;
	}

	@JSONField(serialize = false)
	public KfInviteStatus getFormatInviteStatus() {
		return inviteStatus != null ? KfInviteStatus.valueOf(inviteStatus
				.toUpperCase()) : null;
	}

	public void setInviteStatus(String inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	@Override
	public String toString() {
		return "KfAccount [id=" + id + ", account=" + account + ", nickName="
				+ nickName + ", headimgurl=" + headimgurl + ", wx=" + wx
				+ ", inviteWx=" + inviteWx + ", inviteExpireTime="
				+ inviteExpireTime + ", inviteStatus=" + inviteStatus + "]";
	}
}
