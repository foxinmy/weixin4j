package com.foxinmy.weixin4j.mp.test.msg;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.mp.api.CustomApi;
import com.foxinmy.weixin4j.mp.model.CustomRecord;
import com.foxinmy.weixin4j.mp.model.KfAccount;
import com.foxinmy.weixin4j.mp.test.TokenTest;

/**
 * 客服消息测试
 * 
 * @className MessageNotifyTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 * @see
 */
public class CustomTest extends TokenTest {

	private CustomApi customApi;

	@Before
	public void init() {
		customApi = new CustomApi(tokenHolder);
	}

	@Test
	public void customRecord() throws WeixinException {
		String openId = "";
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		Date starttime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 21);
		Date endtime = calendar.getTime();
		int pagesize = 10;
		int pageindex = 1;
		List<CustomRecord> recordList = customApi.getCustomRecord(openId,
				starttime, endtime, pagesize, pageindex);
		System.out.println(recordList);
	}

	@Test
	public void kfList() throws WeixinException {
		List<KfAccount> kfList = customApi.getKfAccountList(false);
		System.out.println(kfList);
		kfList = customApi.getKfAccountList(true);
		System.out.println(kfList);
	}

	@Test
	public void addAccount() throws WeixinException {
		JsonResult result = customApi.addAccount("temp1@canyidianzhang", "temp",
				"123456");
		Assert.assertEquals(0, result.getCode());
	}
	
	@Test
	public void updateAccount() throws WeixinException {
		JsonResult result = customApi.updateAccount("temp1@canyidianzhang", "temp",
				"123456");
		Assert.assertEquals(0, result.getCode());
	}
	
	@Test
	public void uploadAccountHeadimg() throws WeixinException, IOException {
		JsonResult result = customApi.uploadAccountHeadimg("temp1@canyidianzhang", new File("/Users/jy/Music/简谱/风动草.jpg"));
		Assert.assertEquals(0, result.getCode());
	}
	
	@Test
	public void deleteAccount() throws WeixinException, IOException {
		JsonResult result = customApi.deleteAccount("temp@canyidianzhang");
		Assert.assertEquals(0, result.getCode());
	}
}
