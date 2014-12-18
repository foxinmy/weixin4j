package com.foxinmy.weixin4j.util;

import java.io.InputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 消息工具类
 * 
 * @className MessageUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
 * @see
 */
public class MessageUtil {

	/**
	 * 验证微信签名
	 * 
	 * @param signature
	 *            微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
	 * @return 开发者通过检验signature对请求进行相关校验。若确认此次GET请求来自微信服务器
	 *         请原样返回echostr参数内容，则接入生效 成为开发者成功，否则接入失败
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">接入指南</a>
	 */
	public static String signature(String... para) {
		Arrays.sort(para);
		StringBuilder sb = new StringBuilder();
		for (String str : para) {
			sb.append(str);
		}
		return DigestUtils.sha1Hex(sb.toString());
	}

	/**
	 * 对xml消息加密
	 * 
	 * @param appId
	 * @param encodingAesKey
	 *            加密密钥
	 * @param xmlContent
	 *            原始消息体
	 * @return aes加密后的消息体
	 * @throws WeixinException
	 */
	public static String aesEncrypt(String appId, String encodingAesKey,
			String xmlContent) throws WeixinException {
		byte[] randomBytes = RandomUtil.generateString(16).getBytes(
				org.apache.http.Consts.UTF_8);
		byte[] xmlBytes = xmlContent.getBytes(org.apache.http.Consts.UTF_8);
		int xmlLength = xmlBytes.length;
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (xmlLength & 0xFF);
		orderBytes[2] = (byte) (xmlLength >> 8 & 0xFF);
		orderBytes[1] = (byte) (xmlLength >> 16 & 0xFF);
		orderBytes[0] = (byte) (xmlLength >> 24 & 0xFF);
		byte[] appidBytes = appId.getBytes(org.apache.http.Consts.UTF_8);
		int byteLength = randomBytes.length + xmlLength + orderBytes.length
				+ appidBytes.length;
		// ... + pad: 使用自定义的填充方式对明文进行补位填充
		byte[] padBytes = PKCS7Encoder.encode(byteLength);
		// random + endian + xml + appid + pad 获得最终的字节流
		byte[] unencrypted = new byte[byteLength + padBytes.length];
		byteLength = 0;
		// src:源数组;srcPos:源数组要复制的起始位置;dest:目的数组;destPos:目的数组放置的起始位置;length:复制的长度
		System.arraycopy(randomBytes, 0, unencrypted, byteLength,
				randomBytes.length);
		byteLength += randomBytes.length;
		System.arraycopy(orderBytes, 0, unencrypted, byteLength,
				orderBytes.length);
		byteLength += orderBytes.length;
		System.arraycopy(xmlBytes, 0, unencrypted, byteLength, xmlBytes.length);
		byteLength += xmlBytes.length;
		System.arraycopy(appidBytes, 0, unencrypted, byteLength,
				appidBytes.length);
		byteLength += appidBytes.length;
		System.arraycopy(padBytes, 0, unencrypted, byteLength, padBytes.length);
		try {
			byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, Consts.AES);
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);
			// 使用BASE64对加密后的字符串进行编码
			return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			throw new WeixinException("-40006", "AES加密失败");
		}
	}

	/**
	 * 对AES消息解密
	 * 
	 * @param appId
	 * @param encodingAesKey
	 *            aes加密的密钥
	 * @param encryptContent
	 *            加密的消息体
	 * @return 解密后的字符
	 * @throws WeixinException
	 */
	public static String aesDecrypt(String appId, String encodingAesKey,
			String encryptContent) throws WeixinException {
		byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");
		byte[] original;
		try {
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, Consts.AES);
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey,
					0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(encryptContent);
			// 解密
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			throw new WeixinException("-40007", "AES解密失败");
		}
		String xmlContent, fromAppId;
		try {
			// 去除补位字符
			byte[] bytes = PKCS7Encoder.decode(original);
			// 获取表示xml长度的字节数组
			byte[] lengthByte = Arrays.copyOfRange(bytes, 16, 20);
			// 获取xml消息主体的长度(byte[]2int)
			// http://my.oschina.net/u/169390/blog/97495
			int xmlLength = lengthByte[3] & 0xff | (lengthByte[2] & 0xff) << 8
					| (lengthByte[1] & 0xff) << 16
					| (lengthByte[0] & 0xff) << 24;
			xmlContent = new String(Arrays.copyOfRange(bytes, 20,
					20 + xmlLength), org.apache.http.Consts.UTF_8);
			fromAppId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength,
					bytes.length), org.apache.http.Consts.UTF_8);
		} catch (Exception e) {
			throw new WeixinException("-40008", "公众平台发送的xml不合法");
		}
		// 校验appId是否一致
		if (!fromAppId.trim().equals(appId)) {
			throw new WeixinException("-40005", "校验AppID失败");
		}
		return xmlContent;
	}

	/**
	 * xml消息转换为消息对象
	 * 
	 * @param xmlMsg
	 *            消息字符串
	 * @return 消息对象
	 * @throws DocumentException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/2ccadaef44fe1e4b0322355c2312bfa8.html">验证消息的合法性</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/79502792eef98d6e0c6e1739da387346.html">普通消息</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/2/5baf56ce4947d35003b86a9805634b1e.html">事件触发</a>
	 * @see com.foxinmy.weixin4j.type.MessageType
	 * @see com.feican.weixin.msg.BaeMessage
	 * @see com.foxinmy.weixin4j.msg.TextMessage
	 * @see com.foxinmy.weixin4j.msg.ImageMessage
	 * @see com.foxinmy.weixin4j.msg.VoiceMessage
	 * @see com.foxinmy.weixin4j.msg.VideoMessage
	 * @see com.foxinmy.weixin4j.msg.LocationMessage
	 * @see com.foxinmy.weixin4j.msg.LinkMessage
	 * @see com.foxinmy.weixin4j.msg.event.ScribeEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.ScanEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.LocationEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
	 */
	public static BaseMsg xml2msg(String xmlMsg) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlMsg);
		String type = doc.selectSingleNode("/xml/MsgType").getStringValue();
		if (StringUtils.isBlank(type)) {
			return null;
		}
		MessageType messageType = MessageType.valueOf(type.toLowerCase());
		Class<? extends BaseMsg> messageClass = messageType
				.getMessageClass();
		if (messageType == MessageType.event) {
			type = doc.selectSingleNode("/xml/Event").getStringValue();
			messageClass = EventType.valueOf(type.toLowerCase())
					.getEventClass();
		}
		return XmlStream.get(xmlMsg, messageClass);
	}

	/**
	 * xml消息转换为消息对象
	 * 
	 * @param inputStream
	 *            带消息字符串的输入流
	 * @return 消息对象
	 * @throws DocumentException
	 * @see {@link com.foxinmy.weixin4j.util.MessageUtil#xml2msg(String)}
	 */
	public static BaseMsg xml2msg(InputStream inputStream)
			throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inputStream);
		return xml2msg(doc.asXML());
	}
}
