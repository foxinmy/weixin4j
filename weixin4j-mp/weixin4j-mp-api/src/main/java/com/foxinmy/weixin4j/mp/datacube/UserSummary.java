package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.UserSourceType;

/**
 * 数据统计:用户增减
 * 
 * @className UserSummary
 * @author jy
 * @date 2015年1月25日
 * @since JDK 1.7
 * @see
 */
public class UserSummary implements Serializable {

	private static final long serialVersionUID = 5303181828798568052L;
	@JSONField(name = "ref_date")
	private Date refDate; // 数据的日期
	@JSONField(name = "user_source")
	private int userSource; // 用户的渠道，数值代表的含义如下：0代表其他 30代表扫二维码 17代表名片分享
							// 35代表搜号码（即微信添加朋友页的搜索） 39代表查询微信公众帐号 43代表图文页右上角菜单
	@JSONField(name = "new_user")
	private int newUser; // 新增的用户数量
	@JSONField(name = "cancel_user")
	private int cancelUser; // 取消关注的用户数量，new_user减去cancel_user即为净增用户数量
	@JSONField(name = "cumulate_user")
	private int cumulateUser; // 总用户量

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public UserSourceType getUserSource() {
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
		return "UserSummary [refDate=" + refDate + ", userSource="
				+ getUserSource() + ", newUser=" + newUser + ", cancelUser="
				+ cancelUser + ", cumulateUser=" + cumulateUser + "]";
	}
}
