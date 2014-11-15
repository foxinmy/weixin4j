package com.foxinmy.weixin4j.mp.test.msg;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.mp.response.ArticleResponse;
import com.foxinmy.weixin4j.mp.response.ImageResponse;
import com.foxinmy.weixin4j.mp.response.MusicResponse;
import com.foxinmy.weixin4j.mp.response.TextResponse;
import com.foxinmy.weixin4j.mp.response.VideoResponse;
import com.foxinmy.weixin4j.mp.response.VoiceResponse;
import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.TextMessage;

/**
 * 发送消息格式测试
 * 
 * @author jy.hu
 * @date 2014年3月24日
 * @since JDK 1.7
 */
public class OutMsgTest {
	private BaseMessage inMessage;

	@Before
	public void init() {
		this.inMessage = new TextMessage();
		this.inMessage.setFromUserName("fromusername");
		this.inMessage.setToUserName("tousername");
	}

	@Test
	public void text() throws DocumentException {
		TextResponse message = new TextResponse("text", inMessage);
		System.out.println(message.toXml());
	}

	@Test
	public void image() {
		ImageResponse message = new ImageResponse(inMessage);
		message.pushMediaId("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void voice() {
		VoiceResponse message = new VoiceResponse(inMessage);
		message.pushMediaId("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void video() {
		VideoResponse message = new VideoResponse(inMessage);
		message.pushVideo("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void music() {
		MusicResponse message = new MusicResponse(inMessage);
		message.pushMusic("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void article() {
		ArticleResponse message = new ArticleResponse(inMessage);
		message.pushArticle("title1", "desc1", "picUrl1", "url1");
		message.pushArticle("title2", "desc2", "picUrl2", "url2");
		System.out.println(message.toXml());
	}
}
