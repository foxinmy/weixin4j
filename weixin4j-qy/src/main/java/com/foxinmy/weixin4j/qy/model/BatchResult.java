package com.foxinmy.weixin4j.qy.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.qy.type.BatchStatus;
import com.foxinmy.weixin4j.qy.type.BatchType;

/**
 * 异步任务执行结果
 * 
 * @className BatchResult
 * @author jy
 * @date 2015年3月31日
 * @since JDK 1.6
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
	@JSONField(name = "percentage")
	private int percentAge;
	/**
	 * 预估剩余时间（单位：分钟），当任务完成时为0
	 */
	@JSONField(name = "remaintime")
	private int remainTime;
	/**
	 * 详细的处理结果 TODO
	 */
	private JSONArray result;

	public int getStatus() {
		return status;
	}

	@JSONField(serialize = false)
	public BatchStatus getFormatStatus() {
		return BatchStatus.values()[status - 1];
	}

	public String getType() {
		return type;
	}

	@JSONField(serialize = false)
	public BatchType getFormatType() {
		return type != null ? BatchType.valueOf(type.toUpperCase()) : null;
	}

	public int getTotal() {
		return total;
	}

	public int getPercentAge() {
		return percentAge;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public JSONArray getResult() {
		return result;
	}

	// ---------- setter 应该全部去掉

	public void setStatus(int status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setPercentAge(int percentAge) {
		this.percentAge = percentAge;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BatchResult [status=" + status + ", type=" + type + ", total="
				+ total + ", percentAge=" + percentAge + ", remainTime="
				+ remainTime + ", result=" + result + "]";
	}
}
