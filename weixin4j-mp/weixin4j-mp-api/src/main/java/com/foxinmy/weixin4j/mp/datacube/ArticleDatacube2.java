package com.foxinmy.weixin4j.mp.datacube;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ArticleDatacube2 extends ArticleDatacube1 {
	private static final long serialVersionUID = -2924534868674264316L;

	@JSONField(name = "stat_date")
	private Date statDate;
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
