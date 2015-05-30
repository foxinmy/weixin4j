package com.foxinmy.weixin4j.qy.model;

import com.alibaba.fastjson.JSONArray;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.qy.type.BatchStatus;
import com.foxinmy.weixin4j.qy.type.BatchType;

/**
 * 异步任务执行结果
 * 
 * @className BatchResult
 * @author jy
 * @date 2015年3月31日
 * @since JDK 1.7
 * @see
 */
public class BatchResult extends JsonResult {

	private static final long serialVersionUID = 4985338631992208903L;
	/**
	 * 任务状态
	 */
	private int status;
	/**
	 * 任务类型
	 */
	private String type;
	/**
	 * 任务运行总条数
	 */
	private int total;
	/**
	 * 目前运行百分比，当任务完成时为100
	 */
	private int percentage;
	/**
	 * 预估剩余时间（单位：分钟），当任务完成时为0
	 */
	private int remaintime;
	/**
	 * 详细的处理结果 TODO
	 */
	private JSONArray result;

	public BatchStatus getStatus() {
		return BatchStatus.values()[status - 1];
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BatchType getType() {
		return BatchType.valueOf(type.toUpperCase());
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public int getRemaintime() {
		return remaintime;
	}

	public void setRemaintime(int remaintime) {
		this.remaintime = remaintime;
	}

	public JSONArray getResult() {
		return result;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BatchResult [status=" + getStatus() + ", type=" + getType() + ", total="
				+ total + ", percentage=" + percentage + ", remaintime="
				+ remaintime + ", result=" + result + "]";
	}
}
