package com.foxinmy.weixin4j.test.msg;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.api.MediaApi;
import com.foxinmy.weixin4j.api.NotifyApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.BaseResult;
import com.foxinmy.weixin4j.msg.notify.ArticleNotify;
import com.foxinmy.weixin4j.msg.notify.BaseNotify;
import com.foxinmy.weixin4j.msg.notify.ImageNotify;
import com.foxinmy.weixin4j.msg.notify.MusicNotify;
import com.foxinmy.weixin4j.msg.notify.TextNotify;
import com.foxinmy.weixin4j.msg.notify.VideoNotify;
import com.foxinmy.weixin4j.msg.notify.VoiceNotify;
import com.foxinmy.weixin4j.test.TokenTest;
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
		notifyApi = new NotifyApi(tokenApi);
		mediaApi = new MediaApi(tokenApi);
	}

	@Test
	public void text() {
		TextNotify notify = new TextNotify("123", "to");
		System.out.println(notify.toJson());
	}

	@Test
	public void image() {
		ImageNotify notify = new ImageNotify("to");
		notify.pushMediaId("123");
		System.out.println(notify.toJson());
	}

	@Test
	public void voice() {
		VoiceNotify notify = new VoiceNotify("to");
		notify.pushMediaId("123");
		System.out.println(notify.toJson());
	}

	@Test
	public void video() {
		VideoNotify notify = new VideoNotify("to");
		notify.pushVideo("123", "m123");
		System.out.println(notify.toJson());
	}

	@Test
	public void music() {
		MusicNotify notify = new MusicNotify("to");
		notify.pushMusic("url", "hqUrl", "mediaId");
		System.out.println(notify.toJson());
	}

	@Test
	public void article() {
		ArticleNotify notify = new ArticleNotify("to");
		notify.pushArticle("title1", "desc1", "picUrl1", "url1");
		notify.pushArticle("title2", "desc2", "picUrl2", "url2");
		System.out.println(notify.toJson());
	}

	@Test
	public void send1() throws IOException, WeixinException {
		BaseNotify notify = new TextNotify("this is a notify message!",
				"owGBft_vbBbOaQOmpEUE4xDLeRSU");
		BaseResult result = notifyApi.sendNotify(notify);
		Assert.assertEquals(0, result.getErrcode());
	}

	@Test
	public void send2() throws WeixinException, IOException {
		String mediaId = mediaApi.uploadMedia(new File(
				"/tmp/test.jpg"), MediaType.image);
		ImageNotify imageNotify = new ImageNotify(
				"owGBft_vbBbOaQOmpEUE4xDLeRSU");
		imageNotify.pushMediaId(mediaId);
		BaseResult result = notifyApi.sendNotify(imageNotify);
		Assert.assertEquals(0, result.getErrcode());
	}
}
