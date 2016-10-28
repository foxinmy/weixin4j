package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服会话信息
 * 
 * @className KfSession
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see
 */
public class KfSession implements Serializable {

	private static final long serialVersionUID = 7236468333492555458L;

	/**
	 * 客服账号
	 */
	@JSONField(name = "kf_account")
	private String kfAccount;
	/**
	 * 用户ID
	 */
	@JSONField(name = "openid")
	private String userOpenId;
	/**
	 * 创建时间
	 */
	@JSONField(name = "createtime")
	private Date createTime;
	/**
	 * 最后一条消息的时间,在`获取未接入会话列表`接口中有值
	 */
	@JSONField(name = "latest_time")
	private Date latestTime;

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

	public Date getLatestTime() {
		return latestTime;
	}

	public void setLatestTime(Date latestTime) {
		this.latestTime = latestTime;
	}

	/**
	 * 会话计数,如 未接入会话列表
	 * 
	 * @className: kfSessionCounter
	 * @description:
	 * @author jinyu
	 * @date 2016年4月15日
	 * @since JDK 1.6
	 * @see
	 */
	public static class KfSessionCounter implements Iterable<KfSession>, Serializable {

		private static final long serialVersionUID = -2200434961546270829L;

		@JSONField(name = "count")
		private int count;
		@JSONField(name = "waitcaselist")
		private List<KfSession> kfSessions;

		@Override
		public Iterator<KfSession> iterator() {
			return kfSessions.iterator();
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public List<KfSession> getKfSessions() {
			return kfSessions;
		}

		public void setKfSessions(List<KfSession> kfSessions) {
			this.kfSessions = kfSessions;
		}

		@Override
		public String toString() {
			return "kfSessionCounter [count=" + count + ", kfSessions=" + kfSessions + "]";
		}
	}

	@Override
	public String toString() {
		return "KfSession [kfAccount=" + kfAccount + ", userOpenId=" + userOpenId + ", createTime=" + createTime
				+ ", latestTime=" + latestTime + "]";
	}
}
