package com.foxinmy.weixin4j.socket;

import java.util.List;

import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.AesToken;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.ServerToolkits;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 微信回复编码类
 *
 * @className WeixinResponseEncoder
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月13日
 * @since JDK 1.6
 * @see <a href=
 *      "http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 * @see com.foxinmy.weixin4j.response.WeixinResponse
 */
@ChannelHandler.Sharable
public class WeixinResponseEncoder extends MessageToMessageEncoder<WeixinResponse> {

    protected final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

    private final String XML_START = "<xml>";
    // ---------------明文节点
    private final String ELEMENT_TOUSERNAME = "<ToUserName><![CDATA[%s]]></ToUserName>";
    private final String ELEMENT_FROMUSERNAME = "<FromUserName><![CDATA[%s]]></FromUserName>";
    private final String ELEMENT_CREATETIME = "<CreateTime><![CDATA[%d]]></CreateTime>";
    private final String ELEMENT_MSGTYPE = "<MsgType><![CDATA[%s]]></MsgType>";
    // ---------------密文节点
    private final String ELEMENT_MSGSIGNATURE = "<MsgSignature><![CDATA[%s]]></MsgSignature>";
    private final String ELEMENT_ENCRYPT = "<Encrypt><![CDATA[%s]]></Encrypt>";
    private final String ELEMENT_TIMESTAMP = "<TimeStamp><![CDATA[%s]]></TimeStamp>";
    private final String ELEMENT_NONCE = "<Nonce><![CDATA[%s]]></Nonce>";
    private final String XML_END = "</xml>";

    @Override
    protected void encode(ChannelHandlerContext ctx, WeixinResponse response, List<Object> out) {
        WeixinMessageTransfer messageTransfer = ctx.channel().attr(ServerToolkits.MESSAGE_TRANSFER_KEY).get();
        EncryptType encryptType = messageTransfer.getEncryptType();
        StringBuilder content = new StringBuilder();
        content.append(XML_START);
        content.append(String.format(ELEMENT_TOUSERNAME, messageTransfer.getFromUserName()));
        content.append(String.format(ELEMENT_FROMUSERNAME, messageTransfer.getToUserName()));
        content.append(String.format(ELEMENT_CREATETIME, System.currentTimeMillis() / 1000l));
        content.append(String.format(ELEMENT_MSGTYPE, response.getMsgType()));
        content.append(response.toContent());
        content.append(XML_END);
        if (encryptType == EncryptType.AES) {
            AesToken aesToken = messageTransfer.getAesToken();
            String nonce = ServerToolkits.generateRandomString(32);
            String timestamp = Long.toString(System.currentTimeMillis() / 1000l);
            String encrtypt = MessageUtil.aesEncrypt(aesToken.getWeixinId(), aesToken.getAesKey(), content.toString());
            String msgSignature = MessageUtil.signature(aesToken.getToken(), nonce, timestamp, encrtypt);
            content.delete(0, content.length());
            content.append(XML_START);
            content.append(String.format(ELEMENT_NONCE, nonce));
            content.append(String.format(ELEMENT_TIMESTAMP, timestamp));
            content.append(String.format(ELEMENT_MSGSIGNATURE, msgSignature));
            content.append(String.format(ELEMENT_ENCRYPT, encrtypt));
            content.append(XML_END);
        }
        ctx.writeAndFlush(HttpUtil.createHttpResponse(content.toString(), ServerToolkits.CONTENTTYPE$APPLICATION_XML));
        logger.info("{} encode weixin response:{}", encryptType, content);
    }
}