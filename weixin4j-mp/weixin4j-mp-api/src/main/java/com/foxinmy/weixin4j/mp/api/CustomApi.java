package com.foxinmy.weixin4j.mp.api;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.CustomRecord;
import com.foxinmy.weixin4j.mp.model.KfAccount;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 多客服API
 * 
 * @className CustomApi
 * @author jy
 * @date 2014年11月16日
 * @since JDK 1.7
 * @see <a href="http://dkf.qq.com">多客服说明</a>
 */
public class CustomApi extends BaseApi {

	private final TokenHolder tokenHolder;

	public CustomApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 客服聊天记录
	 * 
	 * @param openId
	 *            用户标识 可为空
	 * @param starttime
	 *            查询开始时间
	 * @param endtime
	 *            查询结束时间 每次查询不能跨日查询
	 * @param pagesize
	 *            每页大小 每页最多拉取1000条
	 * @param pageindex
	 *            查询第几页 从1开始
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.CustomRecord
	 * @see <a href="http://dkf.qq.com/document-1_1.html">查询客服聊天记录</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AE%A2%E6%9C%8D%E8%81%8A%E5%A4%A9%E8%AE%B0%E5%BD%95">查询客服聊天记录</a>
	 */
	public List<CustomRecord> getCustomRecord(String openId, Date starttime,
			Date endtime, int pagesize, int pageindex) throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("openId", openId == null ? "" : openId);
		obj.put("starttime", starttime.getTime() / 1000);
		obj.put("endtime", endtime.getTime() / 1000);
		obj.put("pagesize", pagesize > 1000 ? 1000 : pagesize);
		obj.put("pageindex", pageindex);
		String custom_record_uri = getRequestUri("custom_record_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(
				String.format(custom_record_uri, token.getAccessToken()),
				obj.toJSONString());

		String text = response.getAsJson().getString("recordlist");
		return JSON.parseArray(text, CustomRecord.class);
	}

	/**
	 * 获取公众号中所设置的客服基本信息，包括客服工号、客服昵称、客服登录账号
	 * 
	 * @param isOnline
	 *            是否在线 为ture时可以可以获取客服在线状态（手机在线、PC客户端在线、手机和PC客户端全都在线）、客服自动接入最大值、
	 *            客服当前接待客户数
	 * @return 多客服信息列表
	 * @see com.foxinmy.weixin4j.mp.model.KfAccount
	 * @see <a href="http://dkf.qq.com/document-3_1.html">获取客服基本信息</a>
	 * @see <a href="http://dkf.qq.com/document-3_2.html">获取在线客服接待信息</a>
	 * @throws WeixinException
	 */
	public List<KfAccount> getKfAccountList(boolean isOnline)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String text = "";
		if (isOnline) {
			String getonlinekflist_uri = getRequestUri("getonlinekflist_uri");
			Response response = request.post(String.format(getonlinekflist_uri,
					token.getAccessToken()));
			text = response.getAsJson().getString("kf_online_list");
		} else {
			String getkflist_uri = getRequestUri("getkflist_uri");
			Response response = request.post(String.format(getkflist_uri,
					token.getAccessToken()));
			text = response.getAsJson().getString("kf_list");
		}
		return JSON.parseArray(text, KfAccount.class);
	}
}