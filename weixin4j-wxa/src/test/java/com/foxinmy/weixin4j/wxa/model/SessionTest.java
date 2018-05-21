package com.foxinmy.weixin4j.wxa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class SessionTest {

	@Test
	public void test() {
		Session session = JSON.parseObject("{\"openid\": \"OPENID\", \"session_key\": \"SESSIONKEY\"}", Session.class);
		assertEquals("OPENID", session.getOpenId());
		assertEquals("SESSIONKEY", session.getSessionKey());
		assertNull(session.getUnionId());

		session = JSON.parseObject("{\"openid\": \"OPENID\", \"session_key\": \"SESSIONKEY\", \"unionid\": \"UNIONID\"}", Session.class);
		assertEquals("OPENID", session.getOpenId());
		assertEquals("SESSIONKEY", session.getSessionKey());
		assertEquals("UNIONID", session.getUnionId());
	}

}
