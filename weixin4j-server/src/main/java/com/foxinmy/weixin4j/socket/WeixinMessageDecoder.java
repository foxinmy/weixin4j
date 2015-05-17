package com.foxinmy.weixin4j.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;
import java.util.Map;

import com.foxinmy.weixin4j.bean.AesToken;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.StringUtil;
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
 * @see com.foxinmy.weixin4j.request.WeixinRequest
 */
public class WeixinMessageDecoder extends
		MessageToMessageDecoder<FullHttpRequest> {
	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	private AesToken aesToken;

	public WeixinMessageDecoder(AesToken aesToken) {
		this.aesToken = aesToken;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest req,
			List<Object> out) throws WeixinException {
		String content = req.content().toString(Consts.UTF_8);
		QueryStringDecoder queryDecoder = new QueryStringDecoder(req.getUri(),
				true);
		logger.info("\n=================receive request=================");
		logger.info("{}", req.getMethod());
		logger.info("{}", req.getUri());
		logger.info("{}", content);
		Map<String, List<String>> parameters = queryDecoder.parameters();
		EncryptType encryptType = parameters.containsKey("encrypt_type") ? EncryptType
				.valueOf(parameters.get("encrypt_type").get(0).toUpperCase())
				: EncryptType.RAW;
		String echoStr = parameters.containsKey("echostr") ? parameters.get(
				"echostr").get(0) : "";
		String timeStamp = parameters.containsKey("timestamp") ? parameters
				.get("timestamp").get(0) : "";
		String nonce = parameters.containsKey("nonce") ? parameters
				.get("nonce").get(0) : "";
		String signature = parameters.containsKey("signature") ? parameters
				.get("signature").get(0) : "";
		String msgSignature = parameters.containsKey("msg_signature") ? parameters
				.get("msg_signature").get(0) : "";
		String originalContent = content;
		String encryptContent = null;
		if (!content.isEmpty()) {
			if (encryptType == EncryptType.AES) {
				if (StringUtil.isBlank(aesToken.getAesKey())
						|| StringUtil.isBlank(aesToken.getAppid())) {
					throw new WeixinException(
							"AESEncodingKey or AppId not be null in AES mode");
				}
				encryptContent = EncryptMessageHandler.parser(content);
				originalContent = MessageUtil.aesDecrypt(aesToken.getAppid(),
						aesToken.getAesKey(), encryptContent);
			}
		}
		out.add(new WeixinRequest(req.getMethod().name(), encryptType, echoStr,
				timeStamp, nonce, signature, msgSignature, originalContent,
				encryptContent));
	}
}
