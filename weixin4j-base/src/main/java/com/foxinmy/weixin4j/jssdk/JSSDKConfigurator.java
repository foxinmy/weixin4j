package com.foxinmy.weixin4j.jssdk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * JSSDK配置类
 * 
 * @className JSSDKConfigurator
 * @author jy
 * @date 2015年12月23日
 * @since JDK 1.6
 * @see
 */
public class JSSDKConfigurator {
	private final TokenHolder ticketTokenHolder;
	private JSONObject config;
	private Set<JSSDKAPI> apis;

	/**
	 * ticket保存类 可调用WeixinProxy#getTicketHolder获取
	 * 
	 * @param ticketTokenHolder
	 */
	public JSSDKConfigurator(TokenHolder ticketTokenHolder) {
		this.ticketTokenHolder = ticketTokenHolder;
		this.config = new JSONObject();
		this.apis = new HashSet<JSSDKAPI>();
	}

	/**
	 * 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，
	 * 仅在pc端时才会打印。
	 * 
	 * @return
	 */
	public JSSDKConfigurator debugMode() {
		config.put("debug", true);
		return this;
	}

	/**
	 * 公众号的唯一标识 不填则获取weixin4j.properties#account中的id
	 * 
	 * @param appId
	 * @return
	 */
	public JSSDKConfigurator appId(String appId) {
		config.put("appId", appId);
		return this;
	}

	/**
	 * 需要使用的JS接口列表
	 * 
	 * @see JSSDKAPI
	 * @param apis
	 * @return
	 */
	public JSSDKConfigurator apis(JSSDKAPI... apis) {
		for (JSSDKAPI api : apis) {
			this.apis.add(api);
		}
		return this;
	}

	/**
	 * 需要使用的JS接口列表
	 * 
	 * @see JSSDKAPI
	 * @param apis
	 * @return
	 */
	public JSSDKConfigurator apis(JSSDKAPI[]... apis) {
		for (JSSDKAPI[] api : apis) {
			for (JSSDKAPI apii : api) {
				this.apis.add(apii);
			}
		}
		return this;
	}

	/**
	 * 生成config配置JSON串
	 * 
	 * @param url
	 *            当前网页的URL，不包含#及其后面部分
	 * @return
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.AD.A5.E9.AA.A4.E4.B8.89.EF.BC.9A.E9.80.9A.E8.BF.87config.E6.8E.A5.E5.8F.A3.E6.B3.A8.E5.85.A5.E6.9D.83.E9.99.90.E9.AA.8C.E8.AF.81.E9.85.8D.E7.BD.AE">通过config接口注入权限验证配置</a>
	 * @throws WeixinException
	 */
	public String toJSONConfig(String url) throws WeixinException {
		Map<String, String> signMap = new HashMap<String, String>();
		String timestamp = DateUtil.timestamp2string();
		String noncestr = RandomUtil.generateString(24);
		signMap.put("timestamp", timestamp);
		signMap.put("noncestr", noncestr);
		signMap.put("jsapi_ticket", this.ticketTokenHolder.getAccessToken());
		signMap.put("url", url);
		String sign = DigestUtil.SHA1(MapUtil.toJoinString(signMap, false,
				false));
		if (StringUtil.isBlank(config.getString("appId"))) {
			config.put("appId", Weixin4jConfigUtil.getWeixinAccount().getId());
		}
		if (StringUtil.isBlank(config.getString("debug"))) {
			config.put("debug", false);
		}
		if (apis.isEmpty()) {
			throw new WeixinException("jsapilist not be empty");
		}
		config.put("timestamp", timestamp);
		config.put("nonceStr", noncestr);
		config.put("signature", sign);
		config.put("jsApiList", apis.toArray());
		return config.toJSONString();
	}
}
