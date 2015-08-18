package com.foxinmy.weixin4j.qy.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.message.ChatMessage;
import com.foxinmy.weixin4j.qy.model.ChatInfo;
import com.foxinmy.weixin4j.qy.model.ChatMute;
import com.foxinmy.weixin4j.qy.type.ChatType;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.ChatTuple;
import com.foxinmy.weixin4j.util.ObjectId;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 会话服务接口
 * 
 * @className ChatApi
 * @author jy
 * @date 2015年7月31日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%9C%8D%E5%8A%A1">企业号消息服务</a>
 */
public class ChatApi extends QyApi {
	private final TokenHolder tokenHolder;

	public ChatApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 创建会话 <font color="red">如果会话id为空,程序会自动生成一个唯一ID</font>
	 * 
	 * @param chatInfo
	 *            会话信息
	 * @return 会话ID
	 * @see com.foxinmy.weixin4j.qy.model.ChatInfo
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E5.88.9B.E5.BB.BA.E4.BC.9A.E8.AF.9D">创建会话</a>
	 * @throws WeixinException
	 */
	public String createChat(ChatInfo chatInfo) throws WeixinException {
		String chatId = chatInfo.getChatId();
		JSONObject obj = (JSONObject) JSON.toJSON(chatInfo);
		if (StringUtil.isBlank(chatId)) {
			chatId = ObjectId.get().toHexString();
			obj.put("chatid", chatId);
		}
		String message_chat_create_uri = getRequestUri("message_chat_create_uri");
		Token token = tokenHolder.getToken();
		weixinExecutor.post(
				String.format(message_chat_create_uri, token.getAccessToken()),
				obj.toJSONString());
		return chatId;
	}

	/**
	 * 获取会话
	 * 
	 * @param chatId
	 *            会话ID
	 * @return 会话信息
	 * @see com.foxinmy.weixin4j.qy.model.ChatInfo
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.8E.B7.E5.8F.96.E4.BC.9A.E8.AF.9D">获取会话</a>
	 * @throws WeixinException
	 */
	public ChatInfo getChat(String chatId) throws WeixinException {
		String message_chat_get_uri = getRequestUri("message_chat_get_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				message_chat_get_uri, token.getAccessToken(), chatId));
		return response.getAsJson().getObject("chat_info", ChatInfo.class);
	}

	/**
	 * 更新会话
	 * 
	 * @param chatInfo
	 *            会话信息 至少保持会话ID不能为空
	 * @param operator
	 *            操作人userid
	 * @param addUsers
	 *            会话新增成员列表
	 * @param deleteUsers
	 *            会话退出成员列表
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.qy.model.ChatInfo
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E4.BF.AE.E6.94.B9.E4.BC.9A.E8.AF.9D.E4.BF.A1.E6.81.AF">修改会话信息</a>
	 * @throws WeixinException
	 */
	public JsonResult updateChat(ChatInfo chatInfo, String operator,
			List<String> addUsers, List<String> deleteUsers)
			throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(chatInfo);
		obj.remove("userlist");
		obj.put("op_user", operator);
		obj.put("add_user_list", addUsers);
		obj.put("del_user_list", deleteUsers);
		String message_chat_update_uri = getRequestUri("message_chat_update_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_chat_update_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJsonResult();
	}

	/**
	 * 退出会话
	 * 
	 * @param chatId
	 *            会话ID
	 * @param operator
	 *            操作人userid
	 * @return 处理结果
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E9.80.80.E5.87.BA.E4.BC.9A.E8.AF.9D">退出会话</a>
	 * @throws WeixinException
	 */
	public JsonResult quitChat(String chatId, String operator)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("chatid", chatId);
		obj.put("op_user", operator);
		String message_chat_quit_uri = getRequestUri("message_chat_quit_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_chat_quit_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJsonResult();
	}

	/**
	 * 清除会话未读状态
	 * 
	 * @param targetId
	 *            会话值，为userid|chatid，分别表示：成员id|会话id
	 * @param owner
	 *            会话所有者的userid
	 * @param chatType
	 *            会话类型：single|group，分别表示：群聊|单聊
	 * @return 处理结果
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E6.B8.85.E9.99.A4.E4.BC.9A.E8.AF.9D.E6.9C.AA.E8.AF.BB.E7.8A.B6.E6.80.81">清除会话未读状态</a>
	 * @throws WeixinException
	 */
	public JsonResult clearChatNotify(String targetId, String owner,
			ChatType chatType) throws WeixinException {
		JSONObject chat = new JSONObject();
		chat.put("type", chatType.name());
		chat.put("id", targetId);
		String message_chat_clearnotify_uri = getRequestUri("message_chat_clearnotify_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_chat_clearnotify_uri,
						token.getAccessToken()),
				String.format("{\"op_user\": \"%s\",\"chat\":%s", owner,
						chat.toJSONString()));
		return response.getAsJsonResult();
	}

	/**
	 * 设置成员接收到的消息是否提醒。主要场景是用于对接企业im的在线状态，如成员处于在线状态时，可以设置该成员的消息免打扰。当成员离线时，关闭免打扰状态
	 * ，对微信端进行提醒。
	 * 
	 * @param chatMutes
	 *            提醒参数
	 * @see com.foxinmy.weixin4j.qy.model.ChatMute
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.AE.BE.E7.BD.AE.E6.88.90.E5.91.98.E6.96.B0.E6.B6.88.E6.81.AF.E5.85.8D.E6.89.93.E6.89.B0"
	 *      >设置成员新消息免打扰</a>
	 * @return 列表中不存在的成员，剩余合法成员会继续执行。
	 * @throws WeixinException
	 */
	public List<String> setChatMute(List<ChatMute> chatMutes)
			throws WeixinException {
		JSONObject mute = new JSONObject();
		mute.put("user_mute_list", chatMutes);
		String message_chat_setmute_uri = getRequestUri("message_chat_setmute_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor
				.post(String.format(message_chat_setmute_uri,
						token.getAccessToken()), mute.toJSONString());
		return JSON.parseArray(response.getAsJson().getString("invaliduser"),
				String.class);
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 *            消息对象
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.qy.message.ChatMessage
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E5.8F.91.E6.B6.88.E6.81.AF">发送消息</a>
	 * @throws WeixinException
	 */
	public JsonResult sendChatMessage(ChatMessage message)
			throws WeixinException {
		ChatTuple tuple = message.getChatTuple();
		String msgtype = tuple.getMessageType();
		JSONObject msg = new JSONObject();
		JSONObject receiver = new JSONObject();
		receiver.put("id", message.getTargetId());
		receiver.put("type", message.getChatType().name());
		msg.put("receiver", receiver);
		msg.put("sender", message.getSenderId());
		msg.put("msgtype", msgtype);
		msg.put(msgtype, tuple);
		String message_chat_send_uri = getRequestUri("message_chat_send_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_chat_send_uri, token.getAccessToken()),
				msg.toJSONString());
		return response.getAsJsonResult();
	}
}
