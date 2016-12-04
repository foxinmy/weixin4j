package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.AgentAuthType;
import com.foxinmy.weixin4j.qy.type.LoginUserType;

/**
 * 企业号oauth授权登陆信息&第三方应用授权信息
 * 
 * @className OUserInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月12日
 * @since JDK 1.6
 * @see
 */
public class OUserInfo implements Serializable {

	private static final long serialVersionUID = -567063562050171293L;
	/**
	 * 登录用户的类型：1.企业号创建者 2.企业号内部系统管理员 3.企业号外部系统管理员 4.企业号分级管理员 5. 企业号成员
	 */
	@JSONField(name = "usertype")
	private int userType;
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
	private User adminInfo;
	/**
	 * 授权方企业信息
	 */
	@JSONField(name = "corp_info")
	private CorpInfo corpInfo;
	/**
	 * 该管理员在该提供商中能使用的应用列表
	 */
	@JSONField(name = "agent")
	private List<AgentItem> agentInfo;
	/**
	 * 该管理员拥有的通讯录权限
	 */
	@JSONField(name = "auth_info")
	private AuthInfo authInfo;
	/**
	 * 登录跳转的票据信息
	 */
	@JSONField(name = "redirect_login_info")
	private Token redirectLoginInfo;

	public boolean isSysAdmin() {
		return isSysAdmin;
	}

	public boolean isInnerAdmin() {
		return isInnerAdmin;
	}

	public User getAdminInfo() {
		return adminInfo;
	}

	public CorpInfo getCorpInfo() {
		return corpInfo;
	}

	public List<AgentItem> getAgentInfo() {
		return agentInfo;
	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public Token getRedirectLoginInfo() {
		return redirectLoginInfo;
	}

	// ---------- setter 应该全部去掉

	public int getUserType() {
		return userType;
	}

	@JSONField(serialize = false)
	public LoginUserType getFormatUserType() {
		return LoginUserType.values()[userType - 1];
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public void setSysAdmin(boolean isSysAdmin) {
		this.isSysAdmin = isSysAdmin;
	}

	public void setInnerAdmin(boolean isInnerAdmin) {
		this.isInnerAdmin = isInnerAdmin;
	}

	public void setAdminInfo(User adminInfo) {
		this.adminInfo = adminInfo;
	}

	public void setCorpInfo(CorpInfo corpInfo) {
		this.corpInfo = corpInfo;
	}

	public void setAgentInfo(List<AgentItem> agentInfo) {
		this.agentInfo = agentInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public void setRedirectLoginInfo(Token redirectLoginInfo) {
		this.redirectLoginInfo = redirectLoginInfo;
	}

	@Override
	public String toString() {
		return "OUserInfo [userType=" + userType + ", isSysAdmin=" + isSysAdmin
				+ ", isInnerAdmin=" + isInnerAdmin + ", adminInfo=" + adminInfo
				+ ", corpInfo=" + corpInfo + ", agentInfo=" + agentInfo
				+ ", authInfo=" + authInfo + ", redirectLoginInfo="
				+ redirectLoginInfo + "]";
	}

	/**
	 * 授权信息
	 * 
	 * @className AuthInfo
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年6月22日
	 * @since JDK 1.6
	 * @see
	 */
	public static class AuthInfo implements Serializable {

		private static final long serialVersionUID = -4290240764958942370L;
		/**
		 * 是否采用了新的授权，没有该字段或者该字段为false表示是旧授权，true表示是新授权
		 */
		@JSONField(name = "is_new_auth")
		private boolean isNewAuth;
		/**
		 * 授权的应用信息,新的权限体系其中通讯录权限以应用为节点
		 */
		@JSONField(name = "agent")
		private List<AgentItem> agentItems;
		/**
		 * 授权的通讯录部门,<font color="red">新的权限体系将废弃</font>
		 */
		@JSONField(name = "department")
		private List<DepartItem> departItems;

		public List<AgentItem> getAgentItems() {
			return agentItems;
		}

		public List<DepartItem> getDepartItems() {
			return departItems;
		}

		// ---------- setter 应该全部去掉

		public boolean isNewAuth() {
			return isNewAuth;
		}

		public void setNewAuth(boolean isNewAuth) {
			this.isNewAuth = isNewAuth;
		}

		public void setAgentItems(List<AgentItem> agentItems) {
			this.agentItems = agentItems;
		}

		public void setDepartItems(List<DepartItem> departItems) {
			this.departItems = departItems;
		}

		@Override
		public String toString() {
			return "AuthInfo [isNewAuth=" + isNewAuth + ", agentItems="
					+ agentItems + ", departItems=" + departItems + "]";
		}
	}

	/**
	 * 授权的应用信息
	 * 
	 * @className AgentItem
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年6月22日
	 * @since JDK 1.6
	 * @see
	 */
	public static class AgentItem extends AgentOverview {

		private static final long serialVersionUID = -1188968391623633559L;
		/**
		 * 管理员对应用的权限
		 */
		@JSONField(name = "auth_type")
		private int authType;
		/**
		 * 服务商套件中的对应应用id
		 */
		@JSONField(name = "appid")
		private int appId;
		/**
		 * 授权方应用敏感权限组，目前仅有get_location，表示是否有权限设置应用获取地理位置的开关, <font
		 * color="red">新的权限体系将废弃</font>
		 */
		@JSONField(name = "api_group")
		private List<String> apiGroup;
		/**
		 * 应用对应的权限,<font color="red">新的权限体系</font>
		 */
		@JSONField(name = "privilege")
		private PrivilegeInfo privilege;

		public int getAuthType() {
			return authType;
		}

		@JSONField(serialize = false)
		public AgentAuthType getFormatAuthType() {
			if (authType == 0) {
				return AgentAuthType.USE;
			} else if (authType == 1) {
				return AgentAuthType.MANAGE;
			}
			return null;
		}

		public int getAppId() {
			return appId;
		}

		public List<String> getApiGroup() {
			return apiGroup;
		}

		// ---------- setter 应该全部去掉

		public PrivilegeInfo getPrivilege() {
			return privilege;
		}

		public void setPrivilege(PrivilegeInfo privilege) {
			this.privilege = privilege;
		}

		public void setAuthType(int authType) {
			this.authType = authType;
		}

		public void setAppId(int appId) {
			this.appId = appId;
		}

		public void setApiGroup(List<String> apiGroup) {
			this.apiGroup = apiGroup;
		}

		@Override
		public String toString() {
			return "AgentItem [authType=" + authType + ", appId=" + appId
					+ ", apiGroup=" + apiGroup + ", privilege=" + privilege
					+ ", " + super.toString() + "]";
		}
	}

	/**
	 * 授权的通讯录部门
	 * 
	 * @className DepartItem
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年6月22日
	 * @since JDK 1.6
	 * @see
	 */
	public static class DepartItem extends Party {

		private static final long serialVersionUID = 556556672204642407L;

		/**
		 * 是否具有该部门的写权限
		 */
		private boolean writable;

		public boolean isWritable() {
			return writable;
		}

		// ---------- setter 应该全部去掉
		public void setWritable(boolean writable) {
			this.writable = writable;
		}

		@Override
		public String toString() {
			return "DepartItem [writable=" + writable + ", " + super.toString()
					+ "]";
		}
	}
}
