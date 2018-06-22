package com.foxinmy.weixin4j.wxa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class WXBizDataCryptTest {

	@Test
	public void testDecryptData() {
		String appId = "wx4f4bc4dec97d474b";
		String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";

		WXBizDataCrypt biz = new WXBizDataCrypt(appId, sessionKey);

		String encryptedData
			= "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"
			+ "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"
			+ "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"
			+ "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"
			+ "NCuabNPGBzlooOmB231qMM85d2/fV6Ch"
			+ "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"
			+ "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"
			+ "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"
			+ "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"
			+ "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"
			+ "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"
			+ "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"
			+ "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"
			+ "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"
			+ "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"
			+ "20f0a04COwfneQAGGwd5oa+T8yO5hzuy"
			+ "Db/XcxxmK01EpqOyuxINew==";
		String iv = "r7BXXKkLb8qrSNn05n0qiA==";

		JSONObject data = biz.decryptData(encryptedData, iv);

		assertEquals("CN", data.getString("country"));
		assertEquals("ocMvos6NjeKLIBqg5Mr9QjxrP1FA", data.getString("unionId"));
		assertEquals(1, data.getIntValue("gender"));
		assertEquals("Guangdong", data.getString("province"));
		assertEquals("Guangzhou", data.getString("city"));
		assertEquals("http://wx.qlogo.cn/mmopen/vi_32/aSKcBBPpibyKNicHNTMM0qJVh8Kjgiak2AHWr8MHM4WgMEm7GFhsf8OYrySdbvAMvTsw3mo8ibKicsnfN5pRjl1p8HQ/0", data.getString("avatarUrl"));
		assertEquals("oGZUI0egBJY1zhBYw2KhdUfwVJJE", data.getString("openId"));
		assertEquals("Band", data.getString("nickName"));
		assertEquals("zh_CN", data.getString("language"));

		JSONObject watermark = data.getJSONObject("watermark");
		assertEquals("wx4f4bc4dec97d474b", watermark.getString("appid"));
		assertEquals(1477314187L, watermark.getLongValue("timestamp"));
	}

}
