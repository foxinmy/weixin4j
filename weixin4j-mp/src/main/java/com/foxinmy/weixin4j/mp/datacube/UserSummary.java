package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.UserSourceType;

/**
 * 数据统计:用户增减
 * 
 * @className UserSummary
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月25日
 * @since JDK 1.6
 * @see
 */
public class UserSummary implements Serializable {

	private static final long serialVersionUID = 5303181828798568052L;
	/**
	 * 数据的日期
	 */
	@JSONField(name = "ref_date")
	private Date refDate;
	/**
	 * 用户的渠道
	 */
	@JSONField(name = "user_source")
	private int userSource;
	/**
	 * 新增的用户数量
	 */
	@JSONField(name = "new_user")
	private int newUser;
	/**
	 * 取消关注的用户数量，new_user减去cancel_user即为净增用户数量
	 */
	@JSONField(name = "cancel_user")
	private int cancelUser;
	/**
	 * 总用户量
	 */
	@JSONField(name = "cumulate_user")
	private int cumulateUser;

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public int getUserSource() {
		return userSource;
	}

	@JSONField(serialize = false)
	public UserSourceType getFormatUserSource() {
		if (userSource == 30) {
			return UserSourceType.QRCODE;
		} else if (userSource == 17) {
			return UserSourceType.CARDSHARE;
		} else if (userSource == 35) {
			return UserSourceType.SONUMBER;
		} else if (userSource == 39) {
			return UserSourceType.SOMPACCOUNT;
		} else if (userSource == 43) {
			return UserSourceType.ARTICLEMENU;
		} else {
			return UserSourceType.OTHER;
		}
	}

	public void setUserSource(int userSource) {
		this.userSource = userSource;
	}

	public int getNewUser() {
		return newUser;
	}

	public void setNewUser(int newUser) {
		this.newUser = newUser;
	}

	public int getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(int cancelUser) {
		this.cancelUser = cancelUser;
	}

	public int getCumulateUser() {
		return cumulateUser;
	}

	public void setCumulateUser(int cumulateUser) {
		this.cumulateUser = cumulateUser;
	}

	@Override
	public String toString() {
		return "UserSummary [refDate=" + refDate + ", userSource=" + userSource
				+ ", newUser=" + newUser + ", cancelUser=" + cancelUser
				+ ", cumulateUser=" + cumulateUser + "]";
	}
}
