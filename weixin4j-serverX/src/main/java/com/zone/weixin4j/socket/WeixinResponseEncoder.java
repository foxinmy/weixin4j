package com.zone.weixin4j.socket;

import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.response.WeixinResponse;
import com.zone.weixin4j.service.context.WeiXin4jContextAwareImpl;
import com.zone.weixin4j.type.EncryptType;
import com.zone.weixin4j.util.AesToken;
import com.zone.weixin4j.util.MessageUtil;
import com.zone.weixin4j.util.ServerToolkits;
import org.springframework.stereotype.Component;

/**
 * 微信回复编码类
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className WeixinResponseEncoder
 * @date 2014年11月13日
 * @see <a
 * href="http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 * @see com.zone.weixin4j.response.WeixinResponse
 * @since JDK 1.6
 */

@Component
public class WeixinResponseEncoder {

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

    public String encode(WeixinResponse response) throws WeixinException {
        WeixinMessageTransfer messageTransfer = WeiXin4jContextAwareImpl.getWeixinMessageTransfer().get();
        EncryptType encryptType = messageTransfer.getEncryptType();
        StringBuilder content = new StringBuilder();
        content.append(XML_START);
        content.append(String.format(ELEMENT_TOUSERNAME,
                messageTransfer.getFromUserName()));
        content.append(String.format(ELEMENT_FROMUSERNAME,
                messageTransfer.getToUserName()));
        content.append(String.format(ELEMENT_CREATETIME,
                System.currentTimeMillis() / 1000l));
        content.append(String.format(ELEMENT_MSGTYPE, response.getMsgType()));
        content.append(response.toContent());
        content.append(XML_END);
        if (encryptType == EncryptType.AES) {
            AesToken aesToken = messageTransfer.getAesToken();
            String nonce = ServerToolkits.generateRandomString(32);
            String timestamp = Long
                    .toString(System.currentTimeMillis() / 1000l);
            String encrtypt = MessageUtil.aesEncrypt(aesToken.getWeixinId(),
                    aesToken.getAesKey(), content.toString());
            String msgSignature = MessageUtil.signature(aesToken.getToken(),
                    nonce, timestamp, encrtypt);
            content.delete(0, content.length());
            content.append(XML_START);
            content.append(String.format(ELEMENT_NONCE, nonce));
            content.append(String.format(ELEMENT_TIMESTAMP, timestamp));
            content.append(String.format(ELEMENT_MSGSIGNATURE, msgSignature));
            content.append(String.format(ELEMENT_ENCRYPT, encrtypt));
            content.append(XML_END);
        }
        return content.toString();
    }
}