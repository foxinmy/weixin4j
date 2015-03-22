package com.foxinmy.weixin4j.mp.test.msg;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.mp.api.NotifyApi;
import com.foxinmy.weixin4j.mp.message.NotifyMessage;
import com.foxinmy.weixin4j.mp.test.TokenTest;
import com.foxinmy.weixin4j.msg.model.Image;
import com.foxinmy.weixin4j.msg.model.Music;
import com.foxinmy.weixin4j.msg.model.News;
import com.foxinmy.weixin4j.msg.model.Text;
import com.foxinmy.weixin4j.msg.model.Video;
import com.foxinmy.weixin4j.msg.model.Voice;
import com.foxinmy.weixin4j.type.MediaType;

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
	private MediaApi mediaApi;

	@Before
	public void init() {
		notifyApi = new NotifyApi(tokenHolder);
		mediaApi = new MediaApi(tokenHolder);
	}

	@Test
	public void text() {
		NotifyMessage notify = new NotifyMessage("to", new Text("ttt"));
		System.out.println(notify.toJson());
	}

	@Test
	public void image() {
		NotifyMessage notify = new NotifyMessage("to", new Image("image"));
		System.out.println(notify.toJson());
	}

	@Test
	public void voice() {
		NotifyMessage notify = new NotifyMessage("to", new Voice("voice"));
		System.out.println(notify.toJson());
	}

	@Test
	public void video() {
		NotifyMessage notify = new NotifyMessage("to", new Video("video"));
		System.out.println(notify.toJson());
	}

	@Test
	public void music() {
		NotifyMessage notify = new NotifyMessage("to", new Music("music"));
		System.out.println(notify.toJson());
	}

	@Test
	public void news() {
		News news = new News();
		NotifyMessage notify = new NotifyMessage("to", news);
		news.pushArticle("title1", "desc1", "picUrl1", "url1");
		news.pushArticle("title2", "desc2", "picUrl2", "url2");
		System.out.println(notify.toJson());
	}

	@Test
	public void send1() throws IOException, WeixinException {
		NotifyMessage notify = new NotifyMessage(
				"owGBft_vbBbOaQOmpEUE4xDLeRSU", new Text(
						"this is a notify message!"));
		JsonResult result = notifyApi.sendNotify(notify);
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void send2() throws WeixinException, IOException {
		String mediaId = mediaApi.uploadMedia(new File("/tmp/test.jpg"),
				MediaType.image, false);
		NotifyMessage imageNotify = new NotifyMessage(
				"owGBft_vbBbOaQOmpEUE4xDLeRSU", new Image(mediaId));
		JsonResult result = notifyApi.sendNotify(imageNotify);
		Assert.assertEquals(0, result.getCode());
	}
}
