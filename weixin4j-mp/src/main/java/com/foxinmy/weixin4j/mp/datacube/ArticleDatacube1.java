package com.foxinmy.weixin4j.mp.datacube;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图文数据
 * 
 * @className ArticleDatacube1
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月29日
 * @since JDK 1.6
 * @see
 */
public class ArticleDatacube1 implements Serializable {

	private static final long serialVersionUID = 4140706754295502971L;

	/**
	 * 图文页（点击群发图文卡片进入的页面）的阅读人数
	 */
	@JSONField(name = "int_page_read_user")
	private int intPageReadUser;
	/**
	 * 图文页的阅读次数
	 */
	@JSONField(name = "int_page_read_count")
	private int intPageReadCount;
	/**
	 * 原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0
	 */
	@JSONField(name = "ori_page_read_user")
	private int oriPageReadUser;
	/**
	 * 原文页的阅读次数
	 */
	@JSONField(name = "ori_page_read_count")
	private int oriPageReadCount;
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
	 * 收藏的人数
	 */
	@JSONField(name = "add_to_fav_user")
	private int favUser;
	/**
	 * 收藏的次数
	 */
	@JSONField(name = "add_to_fav_count")
	private int favCount;

	public int getIntPageReadUser() {
		return intPageReadUser;
	}

	public void setIntPageReadUser(int intPageReadUser) {
		this.intPageReadUser = intPageReadUser;
	}

	public int getIntPageReadCount() {
		return intPageReadCount;
	}

	public void setIntPageReadCount(int intPageReadCount) {
		this.intPageReadCount = intPageReadCount;
	}

	public int getOriPageReadUser() {
		return oriPageReadUser;
	}

	public void setOriPageReadUser(int oriPageReadUser) {
		this.oriPageReadUser = oriPageReadUser;
	}

	public int getOriPageReadCount() {
		return oriPageReadCount;
	}

	public void setOriPageReadCount(int oriPageReadCount) {
		this.oriPageReadCount = oriPageReadCount;
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

	public int getFavUser() {
		return favUser;
	}

	public void setFavUser(int favUser) {
		this.favUser = favUser;
	}

	public int getFavCount() {
		return favCount;
	}

	public void setFavCount(int favCount) {
		this.favCount = favCount;
	}

	@Override
	public String toString() {
		return " intPageReadUser=" + intPageReadUser + ", intPageReadCount="
				+ intPageReadCount + ", oriPageReadUser=" + oriPageReadUser
				+ ", oriPageReadCount=" + oriPageReadCount + ", share_user="
				+ shareUser + ", share_count=" + shareCount + ", favUser="
				+ favUser + ", favCount=" + favCount;
	}
}
