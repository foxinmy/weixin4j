package com.foxinmy.weixin4j.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.message.ResponseMessage;
import com.foxinmy.weixin4j.model.AesToken;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * 微信消息编码类
 * 
 * @className WeixinMessageEncoder
 * @author jy
 * @date 2014年11月13日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 */
public class WeixinMessageEncoder extends
		MessageToMessageEncoder<ResponseMessage> {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final AesToken aesToken;

	public WeixinMessageEncoder(AesToken aesToken) {
		this.aesToken = aesToken;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseMessage response,
			List<Object> out) throws RuntimeException {
		if (aesToken.getAesKey() == null || aesToken.getAppid() == null) {
			throw new IllegalArgumentException(
					"AESEncodingKey or AppId not be null in AES mode");
		}
		String nonce = RandomUtil.generateString(32);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000l);
		String encrtypt = MessageUtil.aesEncrypt(aesToken.getAppid(),
				aesToken.getAesKey(), response.toXml());
		String msgSignature = MessageUtil.signature(aesToken.getToken(), nonce,
				timestamp, encrtypt);

		StringBuilder content = new StringBuilder();
		content.append("<xml>");
		content.append(String.format("<Nonce><![CDATA[%s]]></Nonce>", nonce));
		content.append(String.format("<TimeStamp><![CDATA[%s]]></TimeStamp>",
				timestamp));
		content.append(String.format(
				"<MsgSignature><![CDATA[%s]]></MsgSignature>", msgSignature));
		content.append(String.format("<Encrypt><![CDATA[%s]]></Encrypt>",
				encrtypt));
		content.append("</xml>");

		out.add(HttpUtil.createWeixinMessageResponse(content.toString(),
				Consts.CONTENTTYPE$APPLICATION_XML));

		log.info("\n=================aes encrtypt out=================");
		log.info("{}", content);
	}
}