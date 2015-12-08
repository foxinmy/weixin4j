package com.foxinmy.weixin4j.server.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * 接收事件消息格式测试
 * 
 * @author jy.hu
 * @date 2014年3月24日
 * @since JDK 1.6
 */
public class EventMessageRequestTest extends MessagePush {

	private StringBuilder xmlSb = new StringBuilder();

	/***************** event message *********************/
	@Test
	public void scribe() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[subscribe]]></Event>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void scan() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[SCAN]]></Event>");
		xmlSb.append("<EventKey><![CDATA[SCENE_VALUE]]></EventKey>");
		xmlSb.append("<Ticket><![CDATA[TICKET]]></Ticket>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void scan_scribe() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml><ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[subscribe]]></Event>");
		xmlSb.append("<EventKey><![CDATA[qrscene_123123]]></EventKey>");
		xmlSb.append("<Ticket><![CDATA[TICKET]]></Ticket>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void location() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[fromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[LOCATION]]></Event>");
		xmlSb.append("<Latitude>23.137466</Latitude>");
		xmlSb.append("<Longitude>113.352425</Longitude>");
		xmlSb.append("<Precision>119.385040</Precision>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void menu_click() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[CLICK]]></Event>");
		xmlSb.append("<EventKey><![CDATA[EVENTKEY]]></EventKey>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void menu_link() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[VIEW]]></Event>");
		xmlSb.append("<EventKey><![CDATA[www.qq.com]]></EventKey>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void menu_scan() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[scancode_waitmsg]]></Event>");
		xmlSb.append("<EventKey><![CDATA[www.qq.com]]></EventKey>");
		xmlSb.append("<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>");
		xmlSb.append("<ScanResult><![CDATA[1]]></ScanResult>");
		xmlSb.append("</ScanCodeInfo>");

		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void menu_photo() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[pic_weixin]]></Event>");
		xmlSb.append("<EventKey><![CDATA[www.qq.com]]></EventKey>");
		xmlSb.append("<SendPicsInfo><Count>1</Count>");
		xmlSb.append("<PicList><item><PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>");
		xmlSb.append("</item></PicList></SendPicsInfo>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}

	@Test
	public void menu_location() throws IOException {
		String para = "?signature=d919cc8a6361597afa536e906156262cc9cd93df&timestamp=1433903433&nonce=518016546";
		xmlSb.delete(0, xmlSb.length());
		xmlSb.append("<xml>");
		xmlSb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		xmlSb.append("<FromUserName><![CDATA[FromUser]]></FromUserName>");
		xmlSb.append("<CreateTime>123456789</CreateTime>");
		xmlSb.append("<MsgType><![CDATA[event]]></MsgType>");
		xmlSb.append("<Event><![CDATA[location_select]]></Event>");
		xmlSb.append("<EventKey><![CDATA[www.qq.com]]></EventKey>");
		xmlSb.append("<SendLocationInfo><Location_X><![CDATA[23]]></Location_X>");
		xmlSb.append("<Location_Y><![CDATA[113]]></Location_Y>");
		xmlSb.append("<Scale><![CDATA[15]]></Scale>");
		xmlSb.append("<Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>");
		xmlSb.append("<Poiname><![CDATA[]]></Poiname></SendLocationInfo>");
		xmlSb.append("</xml>");
		String response = push(para, xmlSb.toString());
		Assert.assertNotNull(response);
		System.out.println(response);
	}
}
