package com.foxinmy.weixin4j.server.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * 消息验证测试
 * 
 * @className EncryptMessageTest
 * @author jy
 * @date 2015年5月8日
 * @since JDK 1.6
 * @see
 */
public class EncryptMessageTest extends MessagePush {
	StringBuilder xmlSb = new StringBuilder();

	/**
	 * 验证是否服务器配置是否正常
	 * 
	 * @throws IOException
	 */
	@Test
	public void testValidate() throws IOException {
		String echostr = "9104940990866104523";
		String para = "?signature=6ea29ec4c087868f960a20f28b7ac3edef7e444e&echostr="
				+ echostr + "&timestamp=1433903307&nonce=1961697383";
		xmlSb.delete(0, xmlSb.length());
		String response = get(para);
		Assert.assertEquals(echostr, response);
	}

	/**
	 * 验证明文模式
	 * 
	 * @throws IOException
	 */
	@Test
	public void testType1() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[gh_22b350df957b]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[owGBft_vbBbOaQOmpEUE4xDLeRSU]]></FromUserName>");
		xmlSb.append("<CreateTime>1433903433</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[text]]></MsgType>");
		xmlSb.append("<Content><![CDATA[text]]></Content>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.err.println(response);
	}

	/**
	 * 验证兼容模式
	 * 
	 * @throws IOException
	 */
	@Test
	public void testType2() throws IOException {
		String para = "/?signature=b4343f727d6e9b1072f6f72d28b0d0cf38986dce&timestamp=1430926116&nonce=1801492986&encrypt_type=aes&msg_signature=af1868ffe3058db89643c6c546e49cd40d717ac9";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[gh_248c6f91d64f]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[oyFLst1bqtuTcxK-ojF8hOGtLQao]]></FromUserName>");
		xmlSb.append("<CreateTime>1415980001</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[CLICK]]></Event>");
		xmlSb.append("<EventKey><![CDATA[CHECKIN]]></EventKey>");
		xmlSb.append("<Encrypt><![CDATA[TDlZ8S/dhpElqvh4Nd4esnSk6Z6jIF65S3OIXiDWdwRMw+B7uzRcoJDZcETYw3b5ZE1eB9zyt4qHyJdjiBazUeN/ZkG6PVPHaprE0q0yBs2YCGLesAsbQ8rFyMtDazsOzug7pu/PM3RI886PnJ1QAcnPDT7xj4lRmKasWziZ3Ta45FH4vNkaozKNluzpUpq43PGzlOfa1ITvx22ScBcWmdaKnEBFAH/sGwpJ6IaKzHo5DDGyD0gmQ/QNiSh/swMzZewKG6VU0rRWms1rMXOXgP5ttxH0d7wIYMeqbXwwqRh4lh93CxVs2pJGrxTpI48ZnytWSSBBWfMSgchdxvHGd4auBnYWPizIoeQjb7/zJkXnnfru8/TiSQHXujLQuETV78I1jbvNeDJ7dMVsYbpANzfNa5VtJSZCorucCHgK15k=]]></Encrypt>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.err.println(response);
	}

	/**
	 * 验证密文模式
	 * 
	 * @throws IOException
	 */
	@Test
	public void testType3() throws IOException {
		String para = "?signature=ad05f836772d1cbba1ff2edb7be4b9c9fb3a43d5&timestamp=1415980001&nonce=1803216865&encrypt_type=aes&msg_signature=c0d38e9ca00548f7142627ec2908a4fe8481025e";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[gh_248c6f91d64f]]></ToUserName>");
		xmlSb.append("<Encrypt><![CDATA[R6VQIWDR14XgSRLm25zc7V/WJYqK15gsUiMh0u/5GTMZME6jGtHkyfVN079ZPL065b+ZDq3TnoFKKtjtZlzcodY6Fm8+EujvtbTdVMMFSwdo8AwqVViAn09+DDfqPaNvbHUSiYsL3qlxArs1MH6APRUHFo7MU/piY1x2stJc8+kv28xtF+K8Aou0RuPO7PeQ18Zu/GkLnYNiI1E7UG31UYfOgVKcRjeE0PXa18iF5LBS8G/ce/l+/pH/DJWUBw5uXaqSOlo21tctlgLXu3bYUUkIu8tT49QwhHvRZILtO9icoyCNuTA7iTcHIdlAe1bD1S0ncmopIQCGmoU2/AXC2CCi6trONf3EPNKKKfDeQYHadnVZOg6kTX2cnYmHZLviYeLzjCKFSqSNkimoSKQ/Dcutpsq1D82NCwiExUZW4oo=]]></Encrypt>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.err.println(response);
	}
}
