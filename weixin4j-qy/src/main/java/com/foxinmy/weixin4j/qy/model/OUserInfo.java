package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.qy.type.AgentAuthType;

/**
 * oauth授权登陆信息
 * 
 * @className OUserInfo
 * @author jy
 * @date 2015年6月12日
 * @since JDK 1.7
 * @see
 */
public class OUserInfo implements Serializable {

	private static final long serialVersionUID = -567063562050171293L;
	/**
	 * 是否系统管理员
	 */
	@JSONField(name = "is_sys")
	private boolean isSysAdmin;
	/**
	 * 是否内部管理员
	 */
	@JSONField(name = "is_inner")
	private boolean isInnerAdmin;
	/**
	 * 登陆管理员信息
	 */
	@JSONField(name = "user_info")
	private User userInfo;
	/**
	 * 授权方企业信息
	 */
	@JSONField(name = "corp_info")
	private Corpinfo corpinfo;
	/**
	 * 该管理员在该提供商中能使用的应用列表
	 */
	@JSONField(name = "agent")
	private List<AgentItem> agentInfo;
	/**
	 * 该管理员拥有的通讯录权限
	 */
	@JSONField(name = "auth_info")
	private JSONObject authInfo;

	public boolean isSysAdmin() {
		return isSysAdmin;
	}

	public void setSysAdmin(boolean isSysAdmin) {
		this.isSysAdmin = isSysAdmin;
	}

	public boolean isInnerAdmin() {
		return isInnerAdmin;
	}

	public void setInnerAdmin(boolean isInnerAdmin) {
		this.isInnerAdmin = isInnerAdmin;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public Corpinfo getCorpinfo() {
		return corpinfo;
	}

	public void setCorpinfo(Corpinfo corpinfo) {
		this.corpinfo = corpinfo;
	}

	public List<AgentItem> getAgentInfo() {
		return agentInfo;
	}

	public void setAgentInfo(List<AgentItem> agentInfo) {
		this.agentInfo = agentInfo;
	}

	public JSONObject getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(JSONObject authInfo) {
		this.authInfo = authInfo;
	}

	@Override
	public String toString() {
		return "OUserInfo [isSysAdmin=" + isSysAdmin + ", isInnerAdmin="
				+ isInnerAdmin + ", userInfo=" + userInfo + ", corpinfo="
				+ corpinfo + ", agentInfo=" + agentInfo + ", authInfo="
				+ authInfo + "]";
	}

	public static class AgentItem {
		/**
		 * 应用id
		 */
		private int agentid;
		/**
		 * 管理员对应用的权限
		 */
		@JSONField(name = "auth_type")
		private AgentAuthType authType;

		public int getAgentid() {
			return agentid;
		}

		public void setAgentid(int agentid) {
			this.agentid = agentid;
		}

		public AgentAuthType getAuthType() {
			return authType;
		}

		public void setAuthType(int authType) {
			if (authType == 0) {
				this.authType = AgentAuthType.USE;
			} else if (authType == 1) {
				this.authType = AgentAuthType.MANAGE;
			}
			this.authType = null;
		}

		@Override
		public String toString() {
			return "AgentItem [agentid=" + agentid + ", authType=" + authType
					+ "]";
		}
	}
}
