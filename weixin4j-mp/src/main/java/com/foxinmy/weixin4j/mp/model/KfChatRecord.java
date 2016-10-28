package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.CustomRecordOperCode;

/**
 * 客服聊天记录
 * 
 * @className KfChatRecord
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年6月28日
 * @since JDK 1.6
 */
public class KfChatRecord implements Serializable {

	private static final long serialVersionUID = -4024147769411601325L;

	/**
	 * 客服账号
	 */
	private String worker;
	/**
	 * 用户的标识
	 */
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 操作ID（会话状态）
	 */
	@JSONField(name = "opercode")
	private int operCode;
	/**
	 * 操作时间
	 */
	private long time;
	/**
	 * 聊天记录
	 */
	private String text;

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getOperCode() {
		return operCode;
	}

	@JSONField(serialize = false)
	public CustomRecordOperCode getFormatOperCode() {
		return CustomRecordOperCode.getOper(operCode);
	}

	public void setOperCode(int operCode) {
		this.operCode = operCode;
	}

	public long getTime() {
		return time;
	}

	@JSONField(serialize = false)
	public Date getFormatTime() {
		return new Date(time * 1000l);
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[KfChatRecord worker=").append(worker);
		sb.append(" ,openId=").append(openId);
		sb.append(" ,operCode=").append(operCode);
		sb.append(" ,time=").append(time);
		sb.append(" ,text=").append(text);
		sb.append("]");
		return sb.toString();
	}
}
