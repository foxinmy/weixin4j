package com.foxinmy.weixin4j.socket;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.AesToken;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.ServerToolkits;
import com.foxinmy.weixin4j.xml.EncryptMessageHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 微信消息解码类
 *
 * @className WeixinMessageDecoder
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月13日
 * @since JDK 1.6
 * @see <a href=
 *      "http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html">加密接入指引</a>
 * @see com.foxinmy.weixin4j.request.WeixinRequest
 */
@ChannelHandler.Sharable
public class WeixinMessageDecoder extends MessageToMessageDecoder<FullHttpRequest> {
    private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

    private Map<String, AesToken> aesTokenMap = new ConcurrentHashMap<String, AesToken>();

    public WeixinMessageDecoder(final Map<String, AesToken> aesTokenMap) {
        for (Entry<String, AesToken> entry : aesTokenMap.entrySet()) {
            this.aesTokenMap.put(entry.getKey() == null ? "" : entry.getKey(), entry.getValue());
        }
    }

    public void addAesToken(final AesToken asetoken) {
        aesTokenMap.put(asetoken.getWeixinId(), asetoken);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest req, List<Object> out) {
        String messageContent = req.content().toString(ServerToolkits.UTF_8);
        QueryStringDecoder queryDecoder = new QueryStringDecoder(req.uri(), true);
        HttpMethod method = req.method();
        logger.info("decode request:{} use {} method invoking", req.uri(), method);
        Map<String, List<String>> parameters = queryDecoder.parameters();
        EncryptType encryptType = parameters.containsKey("encrypt_type")
                ? EncryptType.valueOf(parameters.get("encrypt_type").get(0).toUpperCase()) : EncryptType.RAW;
        String echoStr = parameters.containsKey("echostr") ? parameters.get("echostr").get(0) : "";
        String timeStamp = parameters.containsKey("timestamp") ? parameters.get("timestamp").get(0) : "";
        String nonce = parameters.containsKey("nonce") ? parameters.get("nonce").get(0) : "";
        String signature = parameters.containsKey("signature") ? parameters.get("signature").get(0) : "";
        String msgSignature = parameters.containsKey("msg_signature") ? parameters.get("msg_signature").get(0) : "";
        String weixinId = parameters.containsKey("weixin_id") ? parameters.get("weixin_id").get(0) : "";
        AesToken aesToken = aesTokenMap.get(weixinId);
        String encryptContent = null;
        if (!ServerToolkits.isBlank(messageContent) && encryptType == EncryptType.AES) {
            if (ServerToolkits.isBlank(aesToken.getAesKey())) {
                throw new RuntimeException("EncodingAESKey not be empty in safety(AES) mode");
            }
            EncryptMessageHandler encryptHandler = EncryptMessageHandler.parser(messageContent);
            encryptContent = encryptHandler.getEncryptContent();
            /**
             * 企业号第三方套件 ╮（╯_╰）╭
             */
            if (aesToken.getWeixinId().startsWith("tj")) {
                aesToken = new AesToken(encryptHandler.getToUserName(), aesToken.getToken(), aesToken.getAesKey());
            }
            messageContent = MessageUtil.aesDecrypt(aesToken.getWeixinId(), aesToken.getAesKey(), encryptContent);
        }
        logger.info("read original message {}", messageContent);
        WeixinRequest request = new WeixinRequest(req.headers(), method, req.uri(), encryptType, echoStr, timeStamp,
                nonce, signature, msgSignature, messageContent, encryptContent, aesToken);
        request.setDecoderResult(req.decoderResult());
        request.setProtocolVersion(req.protocolVersion());
        out.add(request);
    }
}
