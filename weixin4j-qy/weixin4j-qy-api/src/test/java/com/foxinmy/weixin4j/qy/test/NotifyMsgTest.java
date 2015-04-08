package com.foxinmy.weixin4j.qy.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.msg.model.File;
import com.foxinmy.weixin4j.msg.model.Image;
import com.foxinmy.weixin4j.msg.model.MpNews;
import com.foxinmy.weixin4j.msg.model.News;
import com.foxinmy.weixin4j.msg.model.Text;
import com.foxinmy.weixin4j.msg.model.Video;
import com.foxinmy.weixin4j.msg.model.Voice;
import com.foxinmy.weixin4j.qy.api.NotifyApi;
import com.foxinmy.weixin4j.qy.message.NotifyMessage;

/**
 * 客服消息测试
 * 
 * @className MessageNotifyTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 * @see
 */
public class NotifyMsgTest extends TokenTest {

	private NotifyApi notifyApi;

	@Before
	public void init() {
		notifyApi = new NotifyApi(tokenHolder);
	}

	@Test
	public void text() throws WeixinException {
		NotifyMessage notify = new NotifyMessage(new Text("content"), 0);
		System.out.println(notifyApi.sendNotify(notify));
	}

	@Test
	public void image() throws WeixinException {
		NotifyMessage notify = new NotifyMessage(new Image("123"), 0);
		System.out.println(notifyApi.sendNotify(notify));
	}

	@Test
	public void voice() throws WeixinException {
		NotifyMessage notify = new NotifyMessage(new Voice("123"), 0);
		System.out.println(notifyApi.sendNotify(notify));
	}

	@Test
	public void video() throws WeixinException {
		NotifyMessage notify = new NotifyMessage(new Video("123"), 0);
		System.out.println(notifyApi.sendNotify(notify));
	}

	@Test
	public void file() throws WeixinException {
		File file = new File("file");
		NotifyMessage notify = new NotifyMessage(file, 0);
		System.out.println(notifyApi.sendNotify(notify));
	}

	@Test
	public void news() throws WeixinException {
		News news = new News();
		NotifyMessage notify = new NotifyMessage(news, 0);
		news.pushArticle("title1", "desc1", "picUrl1", "url1");
		news.pushArticle("title2", "desc2", "picUrl2", "url2");
		System.out.println(notifyApi.sendNotify(notify));
	}

	@Test
	public void mpnews() throws WeixinException {
		MpNews news = new MpNews();
		NotifyMessage notify = new NotifyMessage(news, 0);
		news.pushArticle("thumbMediaId1", "title1", "content1");
		news.pushArticle("thumbMediaId2", "title1", "content2");
		System.out.println(notifyApi.sendNotify(notify));
	}

	@Test
	public void send1() throws WeixinException {
		Text text = new Text("this is a text");
		JSONObject result = notifyApi.sendNotify(new NotifyMessage(text, 1));
		Assert.assertEquals(0, result.getIntValue("errcode"));
	}
}
