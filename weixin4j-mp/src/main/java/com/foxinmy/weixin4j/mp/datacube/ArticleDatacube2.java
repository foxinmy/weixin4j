package com.foxinmy.weixin4j.mp.datacube;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图文数据
 * 
 * @className ArticleDatacube2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月29日
 * @since JDK 1.6
 * @see
 */
public class ArticleDatacube2 extends ArticleDatacube1 {
	private static final long serialVersionUID = -2924534868674264316L;

	/**
	 * 统计的日期
	 */
	@JSONField(name = "stat_date")
	private Date statDate;
	/**
	 * 送达人数，一般约等于总粉丝数（需排除黑名单或其他异常情况下无法收到消息的粉丝）
	 */
	@JSONField(name = "target_user")
	private int targetUser;

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public int getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(int targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public String toString() {
		return "statDate=" + statDate + ", targetUser=" + targetUser + ", "
				+ super.toString();
	}
}
