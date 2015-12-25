package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foxinmy.weixin4j.util.StringUtil;

/**
 * id参数集
 * 
 * @className IdParameter
 * @author jy
 * @date 2015年3月30日
 * @since JDK 1.6
 * @see
 */
public class IdParameter implements Serializable {

	private static final long serialVersionUID = -2689758682205591133L;

	private static final char SEPARATOR = '|';

	private List<String> userIds;
	private List<Integer> departmentIds;
	private List<Integer> tagIds;

	public IdParameter() {
		this.userIds = new ArrayList<String>();
		this.departmentIds = new ArrayList<Integer>();
		this.tagIds = new ArrayList<Integer>();
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
	 * 设置成员ID列表，最多支持1000个
	 * 
	 * @param userIds
	 * @return
	 */
	public IdParameter setUserIds(List<String> userIds) {
		this.userIds = userIds;
		return this;
	}

	/**
	 * 新增部门ID列表，最多支持100个
	 * 
	 * @param departmentIds
	 * @return
	 */
	public IdParameter putDepartmentIds(Integer... departmentIds) {
		this.departmentIds.addAll(Arrays.asList(departmentIds));
		return this;
	}

	/**
	 * 设置部门ID列表，最多支持100个
	 * 
	 * @param departmentIds
	 * @return
	 */
	public IdParameter setDepartmentIds(List<Integer> departmentIds) {
		this.departmentIds = departmentIds;
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

	/**
	 * 设置标签ID列表
	 * 
	 * @param tagIds
	 * @return
	 */
	public IdParameter setTagIds(List<Integer> tagIds) {
		this.tagIds = tagIds;
		return this;
	}

	/**
	 * 目标参数
	 * 
	 * @return
	 */
	public Map<String, String> getParameter() {
		Map<String, String> parameterMap = new HashMap<String, String>();
		if (userIds != null && !userIds.isEmpty()) {
			parameterMap.put("touser", StringUtil.join(userIds, SEPARATOR));
		}
		if (departmentIds != null && !departmentIds.isEmpty()) {
			parameterMap.put("toparty", StringUtil.join(departmentIds, SEPARATOR));
		}
		if (tagIds != null && !tagIds.isEmpty()) {
			parameterMap.put("totag", StringUtil.join(tagIds, SEPARATOR));
		}
		return parameterMap;
	}

	@Override
	public String toString() {
		return "IdParameter [userIds=" + userIds + ", departmentIds="
				+ departmentIds + ", tagIds=" + tagIds + "]";
	}
}
