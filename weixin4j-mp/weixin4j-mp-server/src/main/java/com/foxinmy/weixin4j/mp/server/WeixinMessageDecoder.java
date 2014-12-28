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
import com.foxinmy.weixin4j.xml.XmlStream;

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

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest req,
			List<Object> out) throws Exception {
		WeixinMpAccount mpAccount = ConfigUtil.getWeixinMpAccount();
		String content = req.content().toString(Consts.UTF_8);
		HttpWeixinMessage message = new HttpWeixinMessage();
		if (StringUtils.isNotBlank(content)) {
			message = XmlStream.get(content, HttpWeixinMessage.class);
		}
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

		message.setOriginalContent(content);
		if (message.getEncryptType() == EncryptType.AES) {
			message.setOriginalContent(MessageUtil.aesDecrypt(
					mpAccount.getId(), mpAccount.getEncodingAesKey(),
					message.getEncryptContent()));
		}
		out.add(message);
	}
}
