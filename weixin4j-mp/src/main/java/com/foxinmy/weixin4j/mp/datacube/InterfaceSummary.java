package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数据统计:接口分析数据
 * 
 * @className InterfaceSummary
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月30日
 * @since JDK 1.6
 * @see
 */
public class InterfaceSummary implements Serializable {

	private static final long serialVersionUID = -8812979112580350988L;

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
	 * 通过服务器配置地址获得消息后，被动回复用户消息的次数
	 */
	@JSONField(name = "callback_count")
	private int callbackCount;
	/**
	 * 上述动作的失败次数
	 */
	@JSONField(name = "fail_count")
	private int failCount;
	/**
	 * 总耗时，除以callback_count即为平均耗时
	 */
	@JSONField(name = "total_time_cost")
	private int totalTimeCost;
	/**
	 * 最大耗时
	 */
	@JSONField(name = "max_time_cost")
	private int maxTimeCost;

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

	public int getCallbackCount() {
		return callbackCount;
	}

	public void setCallbackCount(int callbackCount) {
		this.callbackCount = callbackCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public int getTotalTimeCost() {
		return totalTimeCost;
	}

	public void setTotalTimeCost(int totalTimeCost) {
		this.totalTimeCost = totalTimeCost;
	}

	public int getMaxTimeCost() {
		return maxTimeCost;
	}

	public void setMaxTimeCost(int maxTimeCost) {
		this.maxTimeCost = maxTimeCost;
	}

	@Override
	public String toString() {
		return "InterfaceSummary [refDate=" + refDate + ", refHour=" + refHour
				+ ", callbackCount=" + callbackCount + ", failCount="
				+ failCount + ", totalTimeCost=" + totalTimeCost
				+ ", maxTimeCost=" + maxTimeCost + "]";
	}
}
