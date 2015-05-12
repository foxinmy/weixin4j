package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.util.StringUtil;

/**
 * id参数集
 * 
 * @className IdParameter
 * @author jy
 * @date 2015年3月30日
 * @since JDK 1.7
 * @see
 */
public class IdParameter implements Serializable {

	private static final long serialVersionUID = -2689758682205591133L;

	private static final char SEPARATOR = '|';
	
	private Map<String, String> parameterMap;

	public IdParameter() {
		this.parameterMap = new HashMap<String, String>();
	}

	/**
	 * 成员ID列表，最多支持1000个
	 * 
	 * @param userIds
	 * @return
	 */
	public IdParameter putUseIds(String... userIds) {
		parameterMap.put("touser", StringUtil.join(userIds, SEPARATOR));
		return this;
	}

	/**
	 * 部门ID列表，最多支持100个
	 * 
	 * @param partyIds
	 * @return
	 */
	public IdParameter putUseIds(int... partyIds) {
		parameterMap.put("toparty", StringUtil.join(partyIds, SEPARATOR));
		return this;
	}

	/**
	 * 标签ID列表
	 * 
	 * @param tagIds
	 * @return
	 */
	public IdParameter putTagIds(int... tagIds) {
		parameterMap.put("totag", StringUtil.join(tagIds, SEPARATOR));
		return this;
	}

	public Map<String, String> getParameter() {
		return parameterMap;
	}
}
