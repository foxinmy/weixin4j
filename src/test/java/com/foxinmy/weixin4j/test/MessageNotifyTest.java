package com.foxinmy.weixin4j.test;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.msg.notify.ArticleNotify;
import com.foxinmy.weixin4j.msg.notify.BaseNotify;
import com.foxinmy.weixin4j.msg.notify.ImageNotify;
import com.foxinmy.weixin4j.msg.notify.MusicNotify;
import com.foxinmy.weixin4j.msg.notify.TextNotify;
import com.foxinmy.weixin4j.msg.notify.VideoNotify;
import com.foxinmy.weixin4j.msg.notify.VoiceNotify;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 客服消息测试
 * @className MessageNotifyTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 * @see
 */
public class MessageNotifyTest {

	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		weixinProxy = new WeixinProxy();
	}

	@Test
	public void text() {
		TextNotify notify = new TextNotify("123", "to");
		System.out.println(notify.toJson());
	}

	@Test
	public void image() {
		ImageNotify notify = new ImageNotify("123", "to");
		System.out.println(notify.toJson());
	}

	@Test
	public void voice() {
		VoiceNotify notify = new VoiceNotify("123", "to");
		System.out.println(notify.toJson());
	}

	@Test
	public void video() {
		VideoNotify notify = new VideoNotify("to");
		notify.pushVideo("123", "title", "desc");
		System.out.println(notify.toJson());
	}

	@Test
	public void music() {
		MusicNotify notify = new MusicNotify("to");
		notify.pushMusic("title", "desc", "url", "hqUrl", "mediaId");
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
	public void send() {
		BaseNotify t = new TextNotify("哈哈哈哈", "owGBft_vbBbOaQOmpEUE4xDLeRSU");
		try {
			weixinProxy.sendNotify(t);
			String mediaId = weixinProxy.uploadMedia(new File("D:\\test.jpg"), MediaType.image);
			t = new ImageNotify(mediaId, "owGBft_vbBbOaQOmpEUE4xDLeRSU");
			weixinProxy.sendNotify(t);
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
}
