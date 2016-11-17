package com.foxinmy.weixin4j.mp.type;

/**
 * 多客服在线状态
 * 
 * @className KfOnlineStatus
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月16日
 * @since JDK 1.6
 * @see
 */
public enum KfOnlineStatus {
	PC(1, "PC在线"), MOBILE(2, "手机在线"), BOTH(3, "PC跟手机同时在线");
	private int code;
	private String desc;

	KfOnlineStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
