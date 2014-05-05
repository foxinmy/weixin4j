package com.foxinmy.weixin4j.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 关注信息
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a href=
 *        "http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%85%B3%E6%B3%A8%E8%80%85%E5%88%97%E8%A1%A8"
 *        >关注信息</a>
 */
public class Following implements Serializable {

	private static final long serialVersionUID = 1917454368271027134L;

	private int total;
	private int count;
	@JSONField(name = "data")
	private JSONObject dataJson;
	@JSONField(name = "next_openid")
	private String nextOpenId;
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
