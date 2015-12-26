package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 关注信息
 * 
 * @author jy.hu
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
	@JSONField(name = "data")
	private JSONObject dataJson;
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

	public JSONObject getDataJson() {
		return dataJson;
	}

	public void setDataJson(JSONObject dataJson) {
		this.dataJson = dataJson;
	}

	public String getNextOpenId() {
		return nextOpenId;
	}

	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Following total=").append(total);
		sb.append(", count=").append(count);
		if (userList != null && !userList.isEmpty()) {
			sb.append(", users={");
			for (User u : userList) {
				sb.append(u.toString());
			}
			sb.append("}");
		}
		sb.append(", nextOpenId=").append(nextOpenId).append("]");
		return sb.toString();
	}
}
