package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 关注信息
 * 
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月4日
 * @since JDK 1.6
 */
public class Following implements Serializable {

	private static final long serialVersionUID = 1917454368271027134L;

	/**
	 * 关注总数
	 */
	private int total;
	/**
	 * 拉取的OPENID个数，最大值为10000
	 */
	private int count;
	/**
	 * 列表数据，OPENID的列表
	 */
	@JSONField(deserialize = false)
	private List<String> openIds;
	/**
	 * 拉取列表的后一个用户的OPENID
	 */
	@JSONField(name = "next_openid")
	private String nextOpenId;
	/**
	 * 用户详情列表
	 * 
	 * @see com.foxinmy.weixin4j.mp.model.User
	 */
	@JSONField(deserialize = false)
	private List<User> userList;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<String> getOpenIds() {
		return openIds;
	}

	public void setOpenIds(List<String> openIds) {
		this.openIds = openIds;
	}

	public String getNextOpenId() {
		return nextOpenId;
	}

	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}

	public boolean hasContent() {
		return userList != null && !userList.isEmpty();
	}

	@Override
	public String toString() {
		return "Following [total=" + total + ", count=" + count + ", openIds=" + openIds + ", nextOpenId=" + nextOpenId
				+ ", userList=" + userList + "]";
	}
}
