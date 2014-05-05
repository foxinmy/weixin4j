package com.foxinmy.weixin4j.test;

import org.junit.Test;

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
public class MessageOutTest {
	@Test
	public void text() {
		TextMessage message = new TextMessage();
		message.setFromUserName("fromUser");
		message.setToUserName("toUser");
		message.setContent("text message");
		System.out.println(message.toXml());
	}

	@Test
	public void image() {
		ImageMessage message = new ImageMessage();
		message.setFromUserName("fromUser");
		message.setToUserName("toUser");
		message.pushMediaId("mediaId");
		System.out.println(message.toXml());
	}

	@Test
	public void voice() {
		VoiceMessage message = new VoiceMessage();
		message.setFromUserName("fromUser");
		message.setToUserName("toUser");
		message.pushMediaId("mediaId");
		System.out.println(message.toXml());
	}
	
	@Test
	public void video() {
		VideoMessage message = new VideoMessage();
		message.setFromUserName("fromUser");
		message.setToUserName("toUser");
		message.pushVideo("mediaId", "title", "title");
		System.out.println(message.toXml());
	}
	
	@Test
	public void music() {
		MusicMessage message = new MusicMessage();
		message.setFromUserName("fromUser");
		message.setToUserName("toUser");
		message.pushMusic("title", "desc", "url", "hqurl", "mediaId");
		System.out.println(message.toXml());
	}
	
	@Test
	public void article() {
		ArticleMessage message = new ArticleMessage();
		message.setFromUserName("fromUser");
		message.setToUserName("toUser");
		message.pushArticle("title1", "desc1", "picUrl1", "url1");
		message.pushArticle("title2", "desc2", "picUrl2", "url2");
		System.out.println(message.toXml());
	}
}
