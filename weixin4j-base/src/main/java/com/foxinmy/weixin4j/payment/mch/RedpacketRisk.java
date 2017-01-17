package com.foxinmy.weixin4j.payment.mch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.MapUtil;

/**
 * 发送红包的活动信息
 * 
 * @className RedpacketRisk
 * @author jinyu(foxinmy@gmail.com)
 * @date 2017年1月4日
 * @since JDK 1.6
 * @see
 */
public class RedpacketRisk {
	private Map<String, String> risk;

	public RedpacketRisk() {
		this.risk = new HashMap<String, String>();
	}

	/**
	 * 用户操作的时间戳
	 * 
	 * @return
	 */
	public RedpacketRisk postTimestamp() {
		risk.put("posttime", DateUtil.timestamp2string());
		return this;
	}

	/**
	 * 业务系统账号的手机号，国家代码-手机号。不需要+号
	 * 
	 * @param mobile
	 * @return
	 */
	public RedpacketRisk mobile(String mobile) {
		risk.put("mobile", mobile);
		return this;
	}

	/**
	 * 用户操作的客户端版本
	 * 
	 * @param clientVersion
	 * @return
	 */
	public RedpacketRisk clientVersion(String clientVersion) {
		risk.put("clientversion", clientVersion);
		return this;
	}

	/**
	 * mac 地址或者设备唯一标识
	 * 
	 * @param deviceid
	 * @return
	 */
	public RedpacketRisk deviceid(String deviceid) {
		risk.put("deviceid", deviceid);
		return this;
	}

	public Map<String, String> getRisk() {
		return risk;
	}

	public String toContent() {
		if (risk.isEmpty())
			return null;
		try {
			return URLEncoder.encode(MapUtil.toJoinString(risk, false, false),
					Consts.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
