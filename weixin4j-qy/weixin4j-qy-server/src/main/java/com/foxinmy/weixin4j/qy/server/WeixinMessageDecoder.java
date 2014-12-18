package com.foxinmy.weixin4j.qy.server;

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

import com.foxinmy.weixin4j.model.WeixinQyAccount;
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
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%9B%9E%E8%B0%83%E6%A8%A1%E5%BC%8F">回调模式</a>
 */
public class WeixinMessageDecoder extends
		MessageToMessageDecoder<FullHttpRequest> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest req,
			List<Object> out) throws Exception {
		WeixinQyAccount qyAccount = ConfigUtil.getWeixinQyAccount();
		String xmlContent = req.content().toString(Consts.UTF_8);
		HttpWeixinMessage message = new HttpWeixinMessage();
		message.setXmlContent(xmlContent);
		if (StringUtils.isNotBlank(xmlContent)) {
			message = XmlStream.get(xmlContent, HttpWeixinMessage.class);
			message.setXmlContent(MessageUtil.aesDecrypt(qyAccount.getId(),
					qyAccount.getEncodingAesKey(), message.getEncryptContent()));
		}
		message.setMethod(req.getMethod().name());
		QueryStringDecoder queryDecoder = new QueryStringDecoder(req.getUri(),
				true);
		log.info("\n=================receive request=================");
		log.info("{}", req.getMethod());
		log.info("{}", req.getUri());
		log.info("{}", xmlContent);
		Map<String, List<String>> parameters = queryDecoder.parameters();
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

		message.setToken(qyAccount.getToken());
		message.setEncryptType(EncryptType.AES);
		out.add(message);
	}
}
