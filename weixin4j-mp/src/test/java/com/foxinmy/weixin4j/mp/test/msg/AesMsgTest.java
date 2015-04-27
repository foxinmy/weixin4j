package com.foxinmy.weixin4j.mp.test.msg;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;

public class AesMsgTest extends MessagePush {
	StringBuilder xmlSb = new StringBuilder();

	@Test
	public void testValidate() throws WeixinException, IOException {
		String para = "?signature=0d2366aedb4f3531cfa4297c1e4ea7eece2311d9&echostr=2143641595566077626&timestamp=1415951914&nonce=165976363";
		xmlSb.delete(0, xmlSb.length());
		String response = get(para);
		Assert.assertEquals("2143641595566077626", response);
	}

	@Test
	public void testType1() throws WeixinException, IOException {
		String para = "?signature=6dd806a20a314723e78bc58742a1b98a7adfd151&timestamp=1415979366&nonce=1865915590";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[gh_248c6f91d64f]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[oyFLst1bqtuTcxK-ojF8hOGtLQao]]></FromUserName>");
		xmlSb.append("<CreateTime>1415979365</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[CLICK]]></Event>");
		xmlSb.append("<EventKey><![CDATA[CHECKIN]]></EventKey>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
	}

	@Test
	public void testType2() throws WeixinException, IOException {
		String para = "?signature=ad05f836772d1cbba1ff2edb7be4b9c9fb3a43d5&timestamp=1415980001&nonce=1803216865&encrypt_type=raw&msg_signature=c0d38e9ca00548f7142627ec2908a4fe8481025e";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[gh_248c6f91d64f]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[oyFLst1bqtuTcxK-ojF8hOGtLQao]]></FromUserName>");
		xmlSb.append("<CreateTime>1415980001</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[CLICK]]></Event>");
		xmlSb.append("<EventKey><![CDATA[CHECKIN]]></EventKey>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
	}

	@Test
	public void testType3() throws WeixinException, IOException {
		String para = "?signature=ad05f836772d1cbba1ff2edb7be4b9c9fb3a43d5&timestamp=1415980001&nonce=1803216865&encrypt_type=aes&msg_signature=c0d38e9ca00548f7142627ec2908a4fe8481025e";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[gh_248c6f91d64f]]></ToUserName>");
		xmlSb.append("<Encrypt><![CDATA[R6VQIWDR14XgSRLm25zc7V/WJYqK15gsUiMh0u/5GTMZME6jGtHkyfVN079ZPL065b+ZDq3TnoFKKtjtZlzcodY6Fm8+EujvtbTdVMMFSwdo8AwqVViAn09+DDfqPaNvbHUSiYsL3qlxArs1MH6APRUHFo7MU/piY1x2stJc8+kv28xtF+K8Aou0RuPO7PeQ18Zu/GkLnYNiI1E7UG31UYfOgVKcRjeE0PXa18iF5LBS8G/ce/l+/pH/DJWUBw5uXaqSOlo21tctlgLXu3bYUUkIu8tT49QwhHvRZILtO9icoyCNuTA7iTcHIdlAe1bD1S0ncmopIQCGmoU2/AXC2CCi6trONf3EPNKKKfDeQYHadnVZOg6kTX2cnYmHZLviYeLzjCKFSqSNkimoSKQ/Dcutpsq1D82NCwiExUZW4oo=]]></Encrypt>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
	}
}
