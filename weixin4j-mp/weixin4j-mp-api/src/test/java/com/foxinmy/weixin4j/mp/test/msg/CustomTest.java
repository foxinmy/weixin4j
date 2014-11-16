package com.foxinmy.weixin4j.mp.test.msg;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
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
}
