package com.foxinmy.weixin4j.mp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.message.ResponseMessage;
import com.foxinmy.weixin4j.mp.util.HttpUtil;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.xml.Map2ObjectConverter;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.mapper.DefaultMapper;

/**
 * 微信消息编码类
 * 
 * @className WeixinMessageEncoder
 * @author jy
 * @date 2014年11月13日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E5%85%A5%E6%8C%87%E5%BC%95">加密接入指引</a>
 */
public class WeixinMessageEncoder extends MessageToMessageEncoder<ResponseMessage> {
	private final Logger log = LoggerFactory.getLogger(getClass());
	protected final static XStream mapXstream = XStream.get();
	static {
		mapXstream.alias("xml", Map.class);
		mapXstream.registerConverter(new Map2ObjectConverter(new DefaultMapper(
				new ClassLoaderReference(XStream.class.getClassLoader()))));
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseMessage response,
			List<Object> out) throws Exception {
		WeixinMpAccount mpAccount = ConfigUtil.getWeixinMpAccount();
		String xmlContent = response.toXml();
		String nonce = RandomUtil.generateString(32);
		String timestamp = DateUtil.timestamp2string();
		String encrtypt = MessageUtil.aesEncrypt(mpAccount.getId(),
				mpAccount.getEncodingAesKey(), xmlContent);
		String msgSignature = MessageUtil.signature(mpAccount.getToken(), nonce,
				timestamp, encrtypt);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Encrypt", encrtypt);
		map.put("MsgSignature", msgSignature);
		map.put("TimeStamp", timestamp);
		map.put("Nonce", nonce);

		String content = mapXstream.toXML(map);
		HttpResponse httpResponse = HttpUtil
				.createWeixinMessageResponse(content);
		out.add(httpResponse);

		log.info("\n=================aes encrtypt out=================");
		log.info("{}", map);
		log.info("{}", content);
	}
}