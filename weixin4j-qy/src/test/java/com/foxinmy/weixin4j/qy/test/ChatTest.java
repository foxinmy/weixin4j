package com.foxinmy.weixin4j.qy.test;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.qy.api.ChatApi;
import com.foxinmy.weixin4j.qy.message.ChatMessage;
import com.foxinmy.weixin4j.qy.model.ChatInfo;
import com.foxinmy.weixin4j.qy.type.ChatType;
import com.foxinmy.weixin4j.tuple.Text;

public class ChatTest extends TokenTest {

	private ChatApi chatApi;

	@Before
	public void init() {
		chatApi = new ChatApi(tokenHolder);
	}

	@Test
	public void createChat() throws WeixinException {
		ChatInfo chatInfo = new ChatInfo("test", "jinyu", "jinyu", "jiaolong",
				"keneng");
		String chatId = chatApi.createChat(chatInfo);
		System.err.println(chatId);
		// 55c87507d4c64543a62583f7
	}

	@Test
	public void getChat() throws WeixinException {
		ChatInfo chatInfo = chatApi.getChat("55c87507d4c64543a62583f7");
		System.err.println(chatInfo);
	}

	@Test
	public void updateChat() throws WeixinException {
		ChatInfo chatInfo = new ChatInfo("55c87507d4c64543a62583f7");
		chatInfo.setName("test1");
		chatApi.updateChat(chatInfo, "jinyu", Arrays.asList("keneng"), null);
	}

	@Test
	public void quitChat() throws WeixinException {
		chatApi.quitChat("55c87507d4c64543a62583f7", "keneng");
	}

	@Test
	public void clearChatNotify() throws WeixinException {
		chatApi.clearChatNotify("55c87507d4c64543a62583f7", "jinyu",
				ChatType.group);
	}

	@Test
	public void sendChatMessage() throws WeixinException {
		ChatMessage message = new ChatMessage("55c87507d4c64543a62583f7",
				ChatType.group, "keneng", new Text("test"));
		chatApi.sendChatMessage(message);
	}
}
