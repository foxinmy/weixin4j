package com.foxinmy.weixin4j.mp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.response.HttpWeixinMessage;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.xml.XStream;

/**
 * 微信消息解码类
 * 
 * @className WeixinMessageDecoder
 * @author jy
 * @date 2014年11月13日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E5%85%A5%E6%8C%87%E5%BC%95">加密接入指引</a>
 */
public class WeixinMessageDecoder extends
		MessageToMessageDecoder<FullHttpRequest> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest req,
			List<Object> out) throws Exception {
		WeixinMpAccount mpAccount = ConfigUtil.getWeixinMpAccount();
		String xmlContent = req.content().toString(Consts.UTF_8);
		HttpWeixinMessage message = new HttpWeixinMessage();
		if (StringUtils.isNotBlank(xmlContent)) {
			message = XStream.get(xmlContent, HttpWeixinMessage.class);
		}
		message.setMethod(req.getMethod().name());
		QueryStringDecoder queryDecoder = new QueryStringDecoder(req.getUri(),
				true);
		log.info("\n=================receive request=================");
		log.info("{}", req.getMethod());
		log.info("{}", req.getUri());
		log.info("{}", xmlContent);
		Map<String, List<String>> parameters = queryDecoder.parameters();
		String encryptType = parameters.containsKey("encrypt_type") ? parameters
				.get("encrypt_type").get(0) : EncryptType.RAW.name();
		message.setEncryptType(EncryptType.valueOf(encryptType.toUpperCase()));
		String msgSignature = parameters.containsKey("msg_signature") ? parameters
				.get("msg_signature").get(0) : "";
		message.setMsgSignature(msgSignature);
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

		message.setXmlContent(xmlContent);
		if (message.getEncryptType() == EncryptType.AES) {
			message.setXmlContent(MessageUtil.aesDecrypt(mpAccount.getId(),
					mpAccount.getEncodingAesKey(), message.getEncryptContent()));
		}
		message.setToken(mpAccount.getToken());
		out.add(message);
	}
}
