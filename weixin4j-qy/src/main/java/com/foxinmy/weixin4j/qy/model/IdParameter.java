package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * id参数集
 *
 * @className IdParameter
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月30日
 * @since JDK 1.6
 * @see
 */
public class IdParameter implements Serializable {

	private static final long serialVersionUID = -2689758682205591133L;

	public static final char SEPARATOR = '|';

	public static final String SEPARATORS = String.format("\\%s",
			String.valueOf(SEPARATOR));

	@JSONField(name = "user")
	private List<String> userIds;
	@JSONField(name = "party")
	private List<Integer> partyIds;
	@JSONField(name = "tag")
	private List<Integer> tagIds;

	protected IdParameter() {
		this.userIds = new ArrayList<String>();
		this.partyIds = new ArrayList<Integer>();
		this.tagIds = new ArrayList<Integer>();
	}

	public static IdParameter get(){
	    return new IdParameter();
	}

	/**
	 * 增加成员ID列表，最多支持1000个
	 *
	 * @param userIds
	 * @return
	 */
	public IdParameter putUserIds(String... userIds) {
		this.userIds.addAll(Arrays.asList(userIds));
		return this;
	}

	/**
	 * 新增部门ID列表，最多支持100个
	 *
	 * @param partyIds
	 * @return
	 */
	public IdParameter putPartyIds(Integer... partyIds) {
		this.partyIds.addAll(Arrays.asList(partyIds));
		return this;
	}

	/**
	 * 新增标签ID列表
	 *
	 * @param tagIds
	 * @return
	 */
	public IdParameter putTagIds(Integer... tagIds) {
		this.tagIds.addAll(Arrays.asList(tagIds));
		return this;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public List<Integer> getPartyIds() {
		return partyIds;
	}

	public List<Integer> getTagIds() {
		return tagIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public void setPartyIds(List<Integer> partyIds) {
		this.partyIds = partyIds;
	}

	public void setTagIds(List<Integer> tagIds) {
		this.tagIds = tagIds;
	}

	/**
	 * 生成某些接口需要的目标参数 如发送客服消息接口
	 *
	 * @return
	 */
	public Map<String, String> getParameter() {
		Map<String, String> parameterMap = new HashMap<String, String>();
		if (userIds != null && !userIds.isEmpty()) {
			parameterMap.put("touser", StringUtil.join(userIds, SEPARATOR));
		}
		if (partyIds != null && !partyIds.isEmpty()) {
			parameterMap.put("toparty", StringUtil.join(partyIds, SEPARATOR));
		}
		if (tagIds != null && !tagIds.isEmpty()) {
			parameterMap.put("totag", StringUtil.join(tagIds, SEPARATOR));
		}
		return parameterMap;
	}

	@Override
	public String toString() {
		return "IdParameter [userIds=" + userIds + ", partyIds=" + partyIds
				+ ", tagIds=" + tagIds + "]";
	}
}
