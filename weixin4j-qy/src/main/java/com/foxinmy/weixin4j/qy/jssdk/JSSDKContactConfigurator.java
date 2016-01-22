package com.foxinmy.weixin4j.qy.jssdk;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * JSSDK联系人筛选配置
 * 
 * @className JSSDKContactConfigurator
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.7
 * @see
 */
public class JSSDKContactConfigurator {
	private final TokenHolder ticketTokenHolder;
	private JSSDKContactParameter contactParameter;

	/**
	 * ticket保存类 可调用WeixinProxy#getTicketHolder获取
	 * 
	 * @param ticketTokenHolder
	 */
	public JSSDKContactConfigurator(TokenHolder ticketTokenHolder) {
		this.ticketTokenHolder = ticketTokenHolder;
		this.contactParameter = new JSSDKContactParameter();
	}

	/**
	 * 可选范围：部门ID列表(如果partyIds为0则表示显示管理组下所有部门)
	 * 
	 * @param departmentIds
	 * @return
	 */
	public JSSDKContactConfigurator partyIds(Integer... partyIds) {
		contactParameter.putPartyIds(partyIds);
		return this;
	}

	/**
	 * 可选范围：标签ID列表(如果tagIds为0则表示显示所有标签)
	 * 
	 * @param tagIds
	 * @return
	 */
	public JSSDKContactConfigurator tagIds(Integer... tagIds) {
		contactParameter.putTagIds(tagIds);
		return this;
	}

	/**
	 * 可选范围：用户ID列表
	 * 
	 * @param userIds
	 * @return
	 */
	public JSSDKContactConfigurator userIds(String... userIds) {
		contactParameter.putUserIds(userIds);
		return this;
	}

	/**
	 * 单选模式
	 * 
	 * @return
	 */
	public JSSDKContactConfigurator singleMode() {
		contactParameter.setMode("single");
		return this;
	}

	/**
	 * 多选模式
	 * 
	 * @return
	 */
	public JSSDKContactConfigurator multiMode() {
		contactParameter.setMode("multi");
		return this;
	}

	/**
	 * 限制部门
	 * 
	 * @return
	 */
	public JSSDKContactConfigurator limitDepartment() {
		contactParameter.putLimitType("department");
		return this;
	}

	/**
	 * 限制标签
	 * 
	 * @return
	 */
	public JSSDKContactConfigurator limitTag() {
		contactParameter.putLimitType("tag");
		return this;
	}

	/**
	 * 限制用户
	 * 
	 * @return
	 */
	public JSSDKContactConfigurator limitUser() {
		contactParameter.putLimitType("user");
		return this;
	}

	/**
	 * 已选部门ID
	 * 
	 * @param selectedDepartmentIds
	 * @return
	 */
	public JSSDKContactConfigurator selectedDepartmentIds(
			Integer... selectedDepartmentIds) {
		contactParameter.putSelectedDepartmentIds(selectedDepartmentIds);
		return this;
	}

	/**
	 * 已选标签ID
	 * 
	 * @param selectedTagIds
	 * @return
	 */
	public JSSDKContactConfigurator selectedTagIds(Integer... selectedTagIds) {
		contactParameter.putSelectedTagIds(selectedTagIds);
		return this;
	}

	/**
	 * 已选用户ID
	 * 
	 * @param selectedUserIds
	 * @return
	 */
	public JSSDKContactConfigurator selectedUserIds(String... selectedUserIds) {
		contactParameter.putSelectedUserIds(selectedUserIds);
		return this;
	}

	/**
	 * 生成config配置JSON串
	 * 
	 * @param url
	 *            当前网页的URL，不包含#及其后面部分
	 * @return
	 * @throws WeixinException
	 */
	public String toJSONConfig(String url) throws WeixinException {
		return toJSONConfig(url, contactParameter);
	}

	/**
	 * 生成config配置JSON串
	 * 
	 * @param url
	 *            当前网页的URL，不包含#及其后面部分
	 * @param parameter
	 *            自定义传入参数对象
	 * @return
	 * @throws WeixinException
	 */
	public String toJSONConfig(String url, JSSDKContactParameter parameter)
			throws WeixinException {
		Map<String, String> signMap = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(24);
		Token token = this.ticketTokenHolder.getToken();
		signMap.put("timestamp", timestamp);
		signMap.put("nonceStr", noncestr);
		signMap.put("group_ticket", token.getAccessToken());
		signMap.put("url", url);
		String sign = DigestUtil.SHA1(MapUtil
				.toJoinString(signMap, false, true));
		JSONObject config = new JSONObject();
		config.put("signature", sign);
		config.put("groupId", JSON.parseObject(token.getOriginalResult())
				.getString("group_id"));
		config.put("timestamp", timestamp);
		config.put("noncestr", noncestr);
		config.put("params", parameter);
		return config.toJSONString();
	}
}
