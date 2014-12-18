package com.foxinmy.weixin4j.qy.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.model.WeixinQyAccount;
import com.foxinmy.weixin4j.qy.util.HttpUtil;
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
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%8A%A0%E8%A7%A3%E5%AF%86%E6%96%B9%E6%A1%88%E7%9A%84%E8%AF%A6%E7%BB%86%E8%AF%B4%E6%98%8E">加密接入指引</a>
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
		WeixinQyAccount qyAccount = ConfigUtil.getWeixinQyAccount();
		String xmlContent = response.toXml();
		String nonce = RandomUtil.generateString(32);
		String timestamp = DateUtil.timestamp2string();
		String encrtypt = MessageUtil.aesEncrypt(qyAccount.getId(),
				qyAccount.getEncodingAesKey(), xmlContent);
		String msgSignature = MessageUtil.signature(qyAccount.getToken(),
				nonce, timestamp, encrtypt);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Encrypt", encrtypt);
		map.put("MsgSignature", msgSignature);
		map.put("TimeStamp", timestamp);
		map.put("Nonce", nonce);

		String content = mapXstream.toXML(map);
		out.add(HttpUtil.createWeixinMessageResponse(
				content, null));

		log.info("\n=================aes encrtypt out=================");
		log.info("{}", content);
	}
}