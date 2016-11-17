package com.foxinmy.weixin4j.mp.type;

/**
 * 多客服邀请状态
 * 
 * @className KfInviteStatus
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月16日
 * @since JDK 1.6
 * @see
 */
public enum KfInviteStatus {
	WAITING("等待确认"), REJECTED("被拒绝"), EXPIRED("已过期");
	private String desc;

	KfInviteStatus(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
