package com.foxinmy.weixin4j.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.message.HttpWeixinMessage;
import com.foxinmy.weixin4j.model.AesToken;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.xml.EncryptMessageHandler;

/**
 * 微信消息解码类
 * 
 * @className WeixinMessageDecoder
 * @author jy
 * @date 2014年11月13日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 */
public class WeixinMessageDecoder extends
		MessageToMessageDecoder<FullHttpRequest> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private AesToken aesToken;

	public WeixinMessageDecoder(AesToken aesToken) {
		this.aesToken = aesToken;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest req,
			List<Object> out) throws RuntimeException {
		String content = req.content().toString(Consts.UTF_8);
		HttpWeixinMessage message = new HttpWeixinMessage();
		message.setMethod(req.getMethod().name());
		QueryStringDecoder queryDecoder = new QueryStringDecoder(req.getUri(),
				true);
		log.info("\n=================receive request=================");
		log.info("{}", req.getMethod());
		log.info("{}", req.getUri());
		log.info("{}", content);
		Map<String, List<String>> parameters = queryDecoder.parameters();
		String encryptType = parameters.containsKey("encrypt_type") ? parameters
				.get("encrypt_type").get(0) : EncryptType.RAW.name();
		message.setEncryptType(EncryptType.valueOf(encryptType.toUpperCase()));
		String echoStr = parameters.containsKey("echostr") ? parameters.get(
				"echostr").get(0) : "";
		message.setEchoStr(echoStr);
		String timeStamp = parameters.containsKey("timestamp") ? parameters
				.get("timestamp").get(0) : "";
		message.setTimeStamp(timeStamp);
		String nonce = parameters.containsKey("nonce") ? parameters
				.get("nonce").get(0) : "";
		message.setNonce(nonce);
		String signature = parameters.containsKey("signature") ? parameters
				.get("signature").get(0) : "";
		message.setSignature(signature);
		String msgSignature = parameters.containsKey("msg_signature") ? parameters
				.get("msg_signature").get(0) : "";
		message.setMsgSignature(msgSignature);

		if (!content.isEmpty()) {
			if (message.getEncryptType() == EncryptType.AES) {
				if (aesToken.getAesKey() == null || aesToken.getAppid() == null) {
					throw new IllegalArgumentException(
							"AESEncodingKey or AppId not be null in AES mode");
				}
				String encryptContent = EncryptMessageHandler.parser(content);
				message.setEncryptContent(encryptContent);
				message.setOriginalContent(MessageUtil.aesDecrypt(
						aesToken.getAppid(), aesToken.getAesKey(),
						encryptContent));
			} else {
				message.setOriginalContent(content);
			}
		}
		out.add(message);
	}
}
