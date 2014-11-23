package com.foxinmy.weixin4j.mp.test.msg;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.mp.message.ResponseMessage;
import com.foxinmy.weixin4j.msg.TextMessage;
import com.foxinmy.weixin4j.msg.model.Image;
import com.foxinmy.weixin4j.msg.model.Music;
import com.foxinmy.weixin4j.msg.model.News;
import com.foxinmy.weixin4j.msg.model.Text;
import com.foxinmy.weixin4j.msg.model.Trans;
import com.foxinmy.weixin4j.msg.model.Video;
import com.foxinmy.weixin4j.msg.model.Voice;

/**
 * 发送消息格式测试
 * 
 * @author jy.hu
 * @date 2014年3月24日
 * @since JDK 1.7
 */
public class OutMsgTest {
	private BaseMsg inMessage;

	@Before
	public void init() {
		this.inMessage = new TextMessage();
		this.inMessage.setFromUserName("fromusername");
		this.inMessage.setToUserName("tousername");
	}

	@Test
	public void text() throws DocumentException {
		ResponseMessage message = new ResponseMessage(new Text("text"), inMessage);
		System.out.println(message.toXml());
	}

	@Test
	public void image() {
		ResponseMessage message = new ResponseMessage(new Image("image"), inMessage);
		System.out.println(message.toXml());
	}

	@Test
	public void voice() {
		ResponseMessage message = new ResponseMessage(new Voice("voice"), inMessage);
		System.out.println(message.toXml());
	}

	@Test
	public void video() {
		Video video = new Video("video");
		video.setDesc("desc");
		ResponseMessage message = new ResponseMessage(video, inMessage);
		System.out.println(message.toXml());
	}

	@Test
	public void music() {
		Music music = new Music("title", "desc", "musicUrl", "hqMusicUrl",
				"thumbMediaId");
		ResponseMessage message = new ResponseMessage(music, inMessage);
		System.out.println(message.toXml());
	}

	@Test
	public void article() {
		News news = new News();
		ResponseMessage message = new ResponseMessage(news, inMessage);
		news.pushArticle("title1", "desc1", "picUrl1", "url1");
		news.pushArticle("title2", "desc2", "picUrl2", "url2");
		System.out.println(message.toXml());
	}
	@Test
	public void trans(){
		ResponseMessage message = new ResponseMessage(new Trans(),inMessage);
		System.out.println(message.toXml());
		message = new ResponseMessage(new Trans("accountid"),inMessage);
		System.out.println(message.toXml());
	}
}
