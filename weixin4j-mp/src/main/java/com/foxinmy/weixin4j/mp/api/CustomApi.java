package com.foxinmy.weixin4j.mp.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.MimeType;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.InputStreamBody;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.KfAccount;
import com.foxinmy.weixin4j.mp.model.KfChatRecord;
import com.foxinmy.weixin4j.mp.model.KfOnlineAccount;
import com.foxinmy.weixin4j.mp.model.KfSession;
import com.foxinmy.weixin4j.mp.model.KfSession.KfSessionCounter;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.FileUtil;
import com.foxinmy.weixin4j.util.ObjectId;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 多客服API
 *
 * @className CustomApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月16日
 * @since JDK 1.6
 * @see <a href="http://dkf.qq.com">多客服说明</a>
 */
public class CustomApi extends MpApi {

	private final TokenManager tokenManager;

	public CustomApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 客服聊天记录
	 *
	 * @param startTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间 每次查询不能跨日查询
	 * @param number
	 *            最多10000条
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.KfChatRecord
	 * @see <a href="http://dkf.qq.com/document-1_1.html">查询客服聊天记录</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044854&token=&lang=zh_CN">
	 *      查询客服聊天记录</a>
	 */
	public List<KfChatRecord> getKfChatRecord(Date startTime, Date endTime,
			int number) throws WeixinException {
		List<KfChatRecord> records = new ArrayList<KfChatRecord>();
		String kf_chatrecord_uri = getRequestUri("kf_chatrecord_uri");
		Token token = tokenManager.getCache();
		JSONObject obj = new JSONObject();
		obj.put("starttime", startTime.getTime() / 1000);
		obj.put("endtime", endTime.getTime() / 1000);
		obj.put("msgid", "1");
		obj.put("number", Math.min(10000, number));
		JSONObject result = null;
		do {
			WeixinResponse response = weixinExecutor.post(
					String.format(kf_chatrecord_uri, token.getAccessToken()),
					obj.toJSONString());
			result = response.getAsJson();
			String text = result.getString("recordlist");
			if (StringUtil.isBlank(text) || "[]".equals(text)) {
				break;
			}
			records.addAll(JSON.parseArray(text, KfChatRecord.class));
			obj.put("msgid", result.getString("msgid"));
		} while (obj.getIntValue("number") == result.getIntValue("number"));
		return records;
	}

	/**
	 * 获取公众号中所设置的客服基本信息，包括客服工号、客服昵称、客服登录账号
	 *
	 * @return 多客服信息列表
	 * @see com.foxinmy.weixin4j.mp.model.KfAccount
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      获取客服基本信息</a>
	 * @throws WeixinException
	 */
	public List<KfAccount> listKfAccount() throws WeixinException {
		Token token = tokenManager.getCache();
		String kf_list_uri = getRequestUri("kf_list_uri");
		WeixinResponse response = weixinExecutor.get(String.format(kf_list_uri,
				token.getAccessToken()));
		String text = response.getAsJson().getString("kf_list");
		return JSON.parseArray(text, KfAccount.class);
	}

	/**
	 * 获取在线客服在线状态（手机在线、PC客户端在线、手机和PC客户端全都在线）、客服自动接入最大值、 客服当前接待客户数
	 *
	 * @return 多客服在线信息列表
	 * @see com.foxinmy.weixin4j.mp.model.KfOnlineAccount
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      获取客服在线信息</a>
	 * @throws WeixinException
	 */
	public List<KfOnlineAccount> listOnlineKfAccount() throws WeixinException {
		Token token = tokenManager.getCache();
		String kf_onlinelist_uri = getRequestUri("kf_onlinelist_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				kf_onlinelist_uri, token.getAccessToken()));
		String text = response.getAsJson().getString("kf_online_list");
		return JSON.parseArray(text, KfOnlineAccount.class);
	}

	/**
	 * 新增客服账号
	 *
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，
	 *            请前往微信公众平台设置。
	 * @param name
	 *            客服昵称，最长6个汉字或12个英文字符
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      新增客服账号</a>
	 */
	public ApiResult createKfAccount(String id, String name)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("kf_account", id);
		obj.put("nickname", name);
		String kf_create_uri = getRequestUri("kf_create_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(kf_create_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 更新客服账号
	 *
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，
	 *            请前往微信公众平台设置。
	 * @param name
	 *            客服昵称，最长6个汉字或12个英文字符
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      新增客服账号</a>
	 */
	public ApiResult updateKfAccount(String id, String name)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("kf_account", id);
		obj.put("nickname", name);
		String kf_update_uri = getRequestUri("kf_update_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(kf_update_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 邀请绑定客服帐号
	 * 新添加的客服帐号是不能直接使用的，只有客服人员用微信号绑定了客服账号后，方可登录Web客服进行操作。此接口发起一个绑定邀请到客服人员微信号
	 * ，客服人员需要在微信客户端上用该微信号确认后帐号才可用。尚未绑定微信号的帐号可以进行绑定邀请操作，邀请未失效时不能对该帐号进行再次绑定微信号邀请。
	 *
	 * @param kfAccount
	 *            完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param inviteAccount
	 *            接收绑定邀请的客服微信号
	 * @return 处理结果
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
	 *      >邀请绑定客服帐号<a/>
	 * @throws WeixinException
	 */
	public ApiResult inviteKfAccount(String kfAccount, String inviteAccount)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("kf_account", kfAccount);
		obj.put("invite_wx", inviteAccount);
		String kf_invite_uri = getRequestUri("kf_invite_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(kf_invite_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 上传客服头像
	 *
	 * @param accountId
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @param is
	 *            头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
	 * @param fileName
	 *            文件名 为空时将自动生成
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      上传客服头像</a>
	 */
	public ApiResult uploadKfAvatar(String accountId, InputStream is,
			String fileName) throws WeixinException {
		if (StringUtil.isBlank(fileName)) {
			fileName = ObjectId.get().toHexString();
		}
		if (StringUtil.isBlank(FileUtil.getFileExtension(fileName))) {
			fileName = String.format("%s.jpg", fileName);
		}
		MimeType mimeType = new MimeType("image",
				FileUtil.getFileExtension(fileName));
		Token token = tokenManager.getCache();
		String kf_avatar_uri = getRequestUri("kf_avatar_uri");
		WeixinResponse response = weixinExecutor
				.post(String.format(kf_avatar_uri, token.getAccessToken(),
						accountId), new FormBodyPart("media",
						new InputStreamBody(is, mimeType.toString(), fileName)));

		return response.getAsResult();
	}

	/**
	 * 删除客服账号
	 *
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      删除客服账号</a>
	 */
	public ApiResult deleteKfAccount(String id) throws WeixinException {
		Token token = tokenManager.getCache();
		String kf_delete_uri = getRequestUri("kf_delete_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				kf_delete_uri, token.getAccessToken(), id));

		return response.getAsResult();
	}

	/**
	 * 创建会话
	 * <p>
	 * 开发者可以使用本接口，为多客服的客服工号创建会话，将某个客户直接指定给客服工号接待，需要注意此接口不会受客服自动接入数以及自动接入开关限制。
	 * 只能为在线的客服（PC客户端在线，或者已绑定多客服助手）创建会话。
	 * </p>
	 *
	 * @param userOpenId
	 *            用户的userOpenId
	 * @param kfAccount
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @param text
	 *            附加信息，文本会展示在客服人员的多客服客户端
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      创建会话</a>
	 */
	public ApiResult createKfSession(String userOpenId, String kfAccount,
			String text) throws WeixinException {
		Token token = tokenManager.getCache();
		String kfsession_create_uri = getRequestUri("kfsession_create_uri");
		JSONObject obj = new JSONObject();
		obj.put("openid", userOpenId);
		obj.put("kf_account", kfAccount);
		obj.put("text", text);
		WeixinResponse response = weixinExecutor.post(
				String.format(kfsession_create_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsResult();
	}

	/**
	 * 关闭会话
	 *
	 * @param userOpenId
	 *            用户的userOpenId
	 * @param kfAccount
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @param text
	 *            附加信息，文本会展示在客服人员的多客服客户端
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      关闭会话</a>
	 */
	public ApiResult closeKfSession(String userOpenId, String kfAccount,
			String text) throws WeixinException {
		Token token = tokenManager.getCache();
		String kfsession_close_uri = getRequestUri("kfsession_close_uri");
		JSONObject obj = new JSONObject();
		obj.put("openid", userOpenId);
		obj.put("kf_account", kfAccount);
		obj.put("text", text);
		WeixinResponse response = weixinExecutor.post(
				String.format(kfsession_close_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsResult();
	}

	/**
	 * 获取客户的会话状态:获取客户当前的会话状态。
	 *
	 * @param userOpenId
	 *            用户的openid
	 * @return 会话对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.KfSession
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      获取会话状态</a>
	 */
	public KfSession getKfSession(String userOpenId) throws WeixinException {
		Token token = tokenManager.getCache();
		String kfsession_get_uri = getRequestUri("kfsession_get_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				kfsession_get_uri, token.getAccessToken(), userOpenId));

		KfSession session = response
				.getAsObject(new TypeReference<KfSession>() {
				});
		session.setUserOpenId(userOpenId);
		return session;
	}

	/**
	 * 获取客服的会话列表:获取某个客服正在接待的会话列表。
	 *
	 * @param kfAccount
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。
	 * @return 会话列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.KfSession
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      获取客服的会话列表</a>
	 */
	public List<KfSession> listKfSession(String kfAccount)
			throws WeixinException {
		Token token = tokenManager.getCache();
		String kfsession_list_uri = getRequestUri("kfsession_list_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				kfsession_list_uri, token.getAccessToken(), kfAccount));

		List<KfSession> sessionList = JSON.parseArray(response.getAsJson()
				.getString("sessionlist"), KfSession.class);
		return sessionList;
	}

	/**
	 * 获取未接入会话列表:获取当前正在等待队列中的会话列表，此接口最多返回最早进入队列的100个未接入会话。</br> <font
	 * color="red">缺陷：没有count字段</font>
	 *
	 * @return 会话列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.KfSession
	 * @see com.foxinmy.weixin4j.mp.model.KfSession.KfSessionCounter
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      获取客服的会话列表</a>
	 */
	public KfSessionCounter listKfWaitSession() throws WeixinException {
		Token token = tokenManager.getCache();
		String kfsession_wait_uri = getRequestUri("kfsession_wait_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				kfsession_wait_uri, token.getAccessToken()));

		return response.getAsObject(new TypeReference<KfSessionCounter>() {
		});
	}
}