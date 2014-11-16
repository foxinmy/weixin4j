package com.foxinmy.weixin4j.mp.type;

/**
 * 客服消息记录中的回话状态
 * @className CustomRecordOperCode
 * @author jy
 * @date 2014年11月16日
 * @since JDK 1.7
 * @see
 */
public enum CustomRecordOperCode {
	MISS(1000, "创建未接入会话"), ONLINE(1001, "接入会话"), CALL(1002, "主动发起会话"), CLOSE(
			1004, "关闭会话"), RASE(1005, "抢接会话"), RECEIVE1(2001, "公众号收到消息"), SEND(
			2002, "客服发送消息"), RECEIVE2(2003, "客服收到消息");
	private int code;
	private String desc;

	CustomRecordOperCode(int code, String desc) {
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
