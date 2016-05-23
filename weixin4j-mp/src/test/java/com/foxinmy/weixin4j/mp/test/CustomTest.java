package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.model.Pageable;
import com.foxinmy.weixin4j.mp.api.CustomApi;
import com.foxinmy.weixin4j.mp.model.KfAccount;
import com.foxinmy.weixin4j.mp.model.KfChatRecord;
import com.foxinmy.weixin4j.mp.model.KfSession;
import com.foxinmy.weixin4j.mp.model.KfSession.KfSessionCounter;

/**
 * 客服消息测试
 * 
 * @className MessageNotifyTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 * @see
 */
public class CustomTest extends TokenTest {

	private CustomApi customApi;

	@Before
	public void init() {
		customApi = new CustomApi(tokenHolder);
	}

	@Test
	public void kfChatRecord() throws WeixinException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		Date starttime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 21);
		Date endtime = calendar.getTime();
		List<KfChatRecord> recordList = customApi.getKfChatRecord(starttime,
				endtime, new Pageable(1, 70));
		System.out.println(recordList);
	}

	@Test
	public void kfList() throws WeixinException {
		List<KfAccount> kfList = customApi.listKfAccount(false);
		System.out.println(kfList);
		kfList = customApi.listKfAccount(true);
		System.out.println(kfList);
	}

	@Test
	public void createKfAccount() throws WeixinException {
		JsonResult result = customApi.createKfAccount("test@test", "test",
				"123456");
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void updateKfAccount() throws WeixinException {
		JsonResult result = customApi.updateKfAccount("temp1@canyidianzhang",
				"temp", "123456");
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void uploadKfAvatar() throws WeixinException, IOException {
		JsonResult result = customApi.uploadKfAvatar(
				"temp1@canyidianzhang", new FileInputStream(new File(
						"/Users/jy/Music/简谱/风动草.jpg")), "风动草.jpg");
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void deleteKfAccount() throws WeixinException, IOException {
		JsonResult result = customApi.deleteKfAccount("temp@canyidianzhang");
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void createSession() throws WeixinException {
		JsonResult result = customApi.createKfSession(
				"opKwyt6IhrqPmTTZshyqH5W9gIVo", "kfAccount", "text");
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void closeSession() throws WeixinException {
		JsonResult result = customApi.closeKfSession(
				"opKwyt6IhrqPmTTZshyqH5W9gIVo", "kfAccount", "text");
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void getSession() throws WeixinException {
		KfSession session = customApi
				.getKfSession("oz5axuNnJim8yTYs_jzE1bWFj9eA");
		System.err.println(session);
	}

	@Test
	public void getSessionList() throws WeixinException {
		List<KfSession> sessionList = customApi.listKfSession("kfAccount");
		System.err.println(sessionList);
	}

	@Test
	public void getSessionWaitList() throws WeixinException {
		KfSessionCounter sessionCounter = customApi.listKfWaitSession();
		System.err.println(sessionCounter);
	}
}
