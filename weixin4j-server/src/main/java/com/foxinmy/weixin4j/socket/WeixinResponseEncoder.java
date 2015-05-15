package com.foxinmy.weixin4j.socket;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.bean.AesToken;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.response.BlankResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 微信回复编码类
 * 
 * @className WeixinResponseEncoder
 * @author jy
 * @date 2014年11月13日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 */
public class WeixinResponseEncoder extends
		MessageToMessageEncoder<WeixinResponse> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final AesToken aesToken;

	public WeixinResponseEncoder(AesToken aesToken) {
		this.aesToken = aesToken;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, WeixinResponse response,
			List<Object> out) throws WeixinException {
		EncryptType encryptType = ctx.channel().attr(Consts.ENCRYPTTYPE_KEY)
				.get();
		String userOpenId = ctx.channel().attr(Consts.USEROPENID_KEY).get();
		String accountOpenId = ctx.channel().attr(Consts.ACCOUNTOPENID_KEY)
				.get();
		if (StringUtil.isBlank(accountOpenId)) {
			accountOpenId = aesToken.getAppid();
		}
		StringBuilder content = new StringBuilder();
		if (response instanceof BlankResponse) {
			content.append(response.toContent());
		} else {
			content.append("<xml>");
			content.append(String.format(
					"<ToUserName><![CDATA[%s]]></ToUserName>", userOpenId));
			content.append(String.format(
					"<FromUserName><![CDATA[%s]]></FromUserName>",
					accountOpenId));
			content.append(String.format(
					"<CreateTime><![CDATA[%d]]></CreateTime>",
					System.currentTimeMillis() / 1000l));
			content.append(String.format("<MsgType><![CDATA[%s]]></MsgType>",
					response.getMsgType()));
			content.append(response.toContent());
			content.append("</xml>");
			if (encryptType == EncryptType.AES) {
				String nonce = RandomUtil.generateString(32);
				String timestamp = String
						.valueOf(System.currentTimeMillis() / 1000l);
				String encrtypt = MessageUtil.aesEncrypt(accountOpenId,
						aesToken.getAesKey(), content.toString());
				String msgSignature = MessageUtil.signature(
						aesToken.getToken(), nonce, timestamp, encrtypt);
				content.delete(0, content.length());
				content.append("<xml>");
				content.append(String.format("<Nonce><![CDATA[%s]]></Nonce>",
						nonce));
				content.append(String.format(
						"<TimeStamp><![CDATA[%s]]></TimeStamp>", timestamp));
				content.append(String.format(
						"<MsgSignature><![CDATA[%s]]></MsgSignature>",
						msgSignature));
				content.append(String.format(
						"<Encrypt><![CDATA[%s]]></Encrypt>", encrtypt));
				content.append("</xml>");
			}
		}
		ctx.writeAndFlush(HttpUtil.createHttpResponse(content.toString(), OK,
				Consts.CONTENTTYPE$APPLICATION_XML));
		log.info("\n=================response message=================");
		log.info("{}", content);
	}
}