package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.ShareSourceType;

/**
 * 数据统计:图文分享数据
 * 
 * @className ArticleDatacubeShare
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月30日
 * @since JDK 1.6
 * @see
 */
public class ArticleDatacubeShare implements Serializable {
	private static final long serialVersionUID = 3841239305410294553L;

	/**
	 * 数据的日期
	 */
	@JSONField(name = "ref_date")
	private Date refDate;
	/**
	 * 数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
	 */
	@JSONField(name = "ref_hour")
	private int refHour;
	/**
	 * 分享的人数
	 */
	@JSONField(name = "shareUser")
	private int shareUser;
	/**
	 * 分享的次数
	 */
	@JSONField(name = "shareCount")
	private int shareCount;
	/**
	 * 分享的场景
	 */
	@JSONField(name = "share_scene")
	private int shareScene;

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

	public int getShareUser() {
		return shareUser;
	}

	public void setShareUser(int shareUser) {
		this.shareUser = shareUser;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getShareScene() {
		return shareScene;
	}

	@JSONField(serialize = false)
	public ShareSourceType getFormatShareScene() {
		if (shareScene == 1) {
			return ShareSourceType.FRIENDFORWARD;
		} else if (shareScene == 2) {
			return ShareSourceType.FRIENDSCIRCLE;
		} else if (shareScene == 3) {
			return ShareSourceType.TENCENTWEIBO;
		} else {
			return ShareSourceType.OTHER;
		}
	}

	public void setShareScene(int shareScene) {
		this.shareScene = shareScene;
	}

	@Override
	public String toString() {
		return "ArticleDatacubeShare [refDate=" + refDate + ", refHour="
				+ refHour + ", shareUser=" + shareUser + ", shareCount="
				+ shareCount + ", shareScene=" + shareScene + "]";
	}
}
