package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.DatacuteCountIntervalType;

/**
 * 数据统计:消息发送分布数据
 * 
 * @className UpstreamMsgDist
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月30日
 * @since JDK 1.6
 * @see
 */
public class UpstreamMsgDist implements Serializable {

	private static final long serialVersionUID = -2605207523094962029L;
	/**
	 * 引用的日期
	 */
	@JSONField(name = "ref_date")
	private Date refDate;
	/**
	 * 上行发送了（向公众号发送了）消息的用户数
	 */
	@JSONField(name = "msg_user")
	private int msgUser;
	/**
	 * 当日发送消息量分布的区间，0代表 “0”，1代表“1-5”，2代表“6-10”，3代表“10次以上
	 */
	@JSONField(name = "count_interval")
	private int countInterval;

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public int getMsgUser() {
		return msgUser;
	}

	public void setMsgUser(int msgUser) {
		this.msgUser = msgUser;
	}

	public int getCountInterval() {
		return countInterval;
	}

	@JSONField(serialize = false)
	public DatacuteCountIntervalType getFormatCountInterval() {
		return DatacuteCountIntervalType.values()[countInterval];
	}

	public void setCountInterval(int countInterval) {
		this.countInterval = countInterval;
	}

	@Override
	public String toString() {
		return "UpstreamMsgDist [refDate=" + refDate + ", msgUser=" + msgUser
				+ ", countInterval=" + countInterval + "]";
	}
}
