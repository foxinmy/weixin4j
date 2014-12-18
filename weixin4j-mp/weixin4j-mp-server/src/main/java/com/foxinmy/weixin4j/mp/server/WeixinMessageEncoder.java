package com.foxinmy.weixin4j.mp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.util.HttpUtil;
import com.foxinmy.weixin4j.response.ResponseMessage;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.xml.Map2ObjectConverter;
import com.foxinmy.weixin4j.xml.XmlStream;
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
 *      href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 */
public class WeixinMessageEncoder extends
		MessageToMessageEncoder<ResponseMessage> {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final static XmlStream mapXstream = XmlStream.get();
	static {
		mapXstream.alias("xml", Map.class);
		mapXstream.registerConverter(new Map2ObjectConverter(new DefaultMapper(
				new ClassLoaderReference(XmlStream.class.getClassLoader()))));
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
		String msgSignature = MessageUtil.signature(mpAccount.getToken(),
				nonce, timestamp, encrtypt);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Encrypt", encrtypt);
		map.put("MsgSignature", msgSignature);
		map.put("TimeStamp", timestamp);
		map.put("Nonce", nonce);

		String content = mapXstream.toXML(map);
		out.add(HttpUtil.createWeixinMessageResponse(content, null));

		log.info("\n=================aes encrtypt out=================");
		log.info("{}", content);
	}
}