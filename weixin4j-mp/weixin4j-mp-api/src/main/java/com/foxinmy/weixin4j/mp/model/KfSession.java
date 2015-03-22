package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服会话信息
 * 
 * @className KfSession
 * @author jy
 * @date 2015年3月22日
 * @since JDK 1.7
 * @see
 */
public class KfSession implements Serializable {

	private static final long serialVersionUID = 7236468333492555458L;

	@JSONField(name = "kf_account")
	private String kfAccount; // 客服账号
	@JSONField(name = "openid")
	private String userOpenId; // 用户ID
	@JSONField(name = "createtime")
	private Date createTime; // 创建时间

	public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "KfSession [kfAccount=" + kfAccount + ", userOpenId="
				+ userOpenId + ", createTime=" + createTime + "]";
	}
}
