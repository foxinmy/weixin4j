package com.foxinmy.weixin4j.test.msg;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.TextMessage;
import com.foxinmy.weixin4j.msg.out.ArticleMessage;
import com.foxinmy.weixin4j.msg.out.ImageMessage;
import com.foxinmy.weixin4j.msg.out.MusicMessage;
import com.foxinmy.weixin4j.msg.out.VideoMessage;
import com.foxinmy.weixin4j.msg.out.VoiceMessage;

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
		TextMessage message = new TextMessage("text", inMessage);
		System.out.println(message.toXml());
	}

	@Test
	public void image() {
		ImageMessage message = new ImageMessage(inMessage);
		message.pushMediaId("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void voice() {
		VoiceMessage message = new VoiceMessage(inMessage);
		message.pushMediaId("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void video() {
		VideoMessage message = new VideoMessage(inMessage);
		message.pushVideo("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void music() {
		MusicMessage message = new MusicMessage(inMessage);
		message.pushMusic("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void article() {
		ArticleMessage message = new ArticleMessage(inMessage);
		message.pushArticle("title1", "desc1", "picUrl1", "url1");
		message.pushArticle("title2", "desc2", "picUrl2", "url2");
		System.out.println(message.toXml());
	}
}
