package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数据统计:消息发送概况数据
 * 
 * @className UpstreamMsg
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月30日
 * @since JDK 1.6
 * @see
 */
public class UpstreamMsg implements Serializable {

	private static final long serialVersionUID = -2605207523094962029L;

	/**
	 * 引用的日期
	 */
	@JSONField(name = "ref_date")
	private Date refDate;
	/**
	 * 数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
	 */
	@JSONField(name = "ref_hour")
	private int refHour;
	/**
	 * 消息类型
	 */
	@JSONField(name = "msg_type")
	private int msgType;
	/**
	 * 上行发送了（向公众号发送了）消息的用户数
	 */
	@JSONField(name = "msg_user")
	private int msgUser;
	/**
	 * 上行发送了消息的消息总数
	 */
	@JSONField(name = "msg_count")
	private int msgCount;

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public int getRefHour() {
		return refHour;
	}

	public void setRefHour(int refHour) {
		this.refHour = refHour;
	}

	public int getMsgType() {
		return msgType;
	}

	/**
	 * 1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
	public String getFormatMsgType() {
		switch (msgType) {
		case 1:
			return "text";
		case 2:
			return "image";
		case 3:
			return "voice";
		case 4:
			return "video";
		case 6:
			return "link";
		default:
			return null;
		}
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getMsgUser() {
		return msgUser;
	}

	public void setMsgUser(int msgUser) {
		this.msgUser = msgUser;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	@Override
	public String toString() {
		return "UpstreamMsg [refDate=" + refDate + ", refHour=" + refHour
				+ ", msgType=" + msgType + ", msgUser=" + msgUser
				+ ", msgCount=" + msgCount + "]";
	}
}
