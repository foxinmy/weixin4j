package com.foxinmy.weixin4j.util;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.foxinmy.weixin4j.base64.Base64;

/**
 * 消息工具类
 *
 * @className MessageUtil
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月31日
 * @since JDK 1.6
 * @see
 */
public final class MessageUtil {
    /**
     * 验证微信签名
     *
     * @param signature
     *            微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @return 开发者通过检验signature对请求进行相关校验。若确认此次GET请求来自微信服务器
     *         请原样返回echostr参数内容，则接入生效 成为开发者成功，否则接入失败
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319&token=&lang=zh_CN">接入指南</a>
     */
    public static String signature(String... para) {
        Arrays.sort(para);
        StringBuffer sb = new StringBuffer();
        for (String str : para) {
            sb.append(str);
        }
        return ServerToolkits.digestSHA1(sb.toString());
    }

    /**
     * 对xml消息加密
     *
     * @param appId
     *            应用ID
     * @param encodingAesKey
     *            加密密钥
     * @param xmlContent
     *            原始消息体
     * @return aes加密后的消息体
     * @throws WeixinException
     */
    public static String aesEncrypt(String appId, String encodingAesKey, String xmlContent) {
        /**
         * 其中，msg_encrypt=Base64_Encode(AES_Encrypt [random(16B)+ msg_len(4B) +
         * msg + $AppId])
         *
         * random(16B)为16字节的随机字符串；msg_len为msg长度，占4个字节(网络字节序)，$AppId为公众账号的AppId
         */
        byte[] randomBytes = ServerToolkits.getBytesUtf8(ServerToolkits.generateRandomString(16));
        byte[] xmlBytes = ServerToolkits.getBytesUtf8(xmlContent);
        int xmlLength = xmlBytes.length;
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (xmlLength & 0xFF);
        orderBytes[2] = (byte) (xmlLength >> 8 & 0xFF);
        orderBytes[1] = (byte) (xmlLength >> 16 & 0xFF);
        orderBytes[0] = (byte) (xmlLength >> 24 & 0xFF);
        byte[] appidBytes = ServerToolkits.getBytesUtf8(appId);

        int byteLength = randomBytes.length + xmlLength + orderBytes.length + appidBytes.length;
        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteLength);
        // random + endian + xml + appid + pad 获得最终的字节流
        byte[] unencrypted = new byte[byteLength + padBytes.length];
        byteLength = 0;
        // src:源数组;srcPos:源数组要复制的起始位置;dest:目的数组;destPos:目的数组放置的起始位置;length:复制的长度
        System.arraycopy(randomBytes, 0, unencrypted, byteLength, randomBytes.length);
        byteLength += randomBytes.length;
        System.arraycopy(orderBytes, 0, unencrypted, byteLength, orderBytes.length);
        byteLength += orderBytes.length;
        System.arraycopy(xmlBytes, 0, unencrypted, byteLength, xmlBytes.length);
        byteLength += xmlBytes.length;
        System.arraycopy(appidBytes, 0, unencrypted, byteLength, appidBytes.length);
        byteLength += appidBytes.length;
        System.arraycopy(padBytes, 0, unencrypted, byteLength, padBytes.length);
        try {
            byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, ServerToolkits.AES);
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);
            // 使用BASE64对加密后的字符串进行编码
            // return Base64.encodeBase64String(encrypted);
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("-40006,AES加密失败:" + e.getMessage());
        }
    }

    /**
     * 对AES消息解密
     *
     * @param appId
     * @param encodingAesKey
     *            aes加密的密钥
     * @param encryptContent
     *            加密的消息体
     * @return 解密后的字符
     * @throws WeixinException
     */
    public static String aesDecrypt(String appId, String encodingAesKey, String encryptContent) {
        byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");
        byte[] original;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, ServerToolkits.AES);
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(encryptContent);
            // 解密
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("-40007,AES解密失败:" + e.getMessage());
        }
        String xmlContent, fromAppId;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            /**
             * AES加密的buf由16个字节的随机字符串、4个字节的msg_len(网络字节序)、msg和$AppId组成，
             * 其中msg_len为msg的长度，$AppId为公众帐号的AppId
             */
            // 获取表示xml长度的字节数组
            byte[] lengthByte = Arrays.copyOfRange(bytes, 16, 20);
            // 获取xml消息主体的长度(byte[]2int)
            // http://my.oschina.net/u/169390/blog/97495
            int xmlLength = lengthByte[3] & 0xff | (lengthByte[2] & 0xff) << 8 | (lengthByte[1] & 0xff) << 16
                    | (lengthByte[0] & 0xff) << 24;
            xmlContent = ServerToolkits.newStringUtf8(Arrays.copyOfRange(bytes, 20, 20 + xmlLength));
            fromAppId = ServerToolkits.newStringUtf8(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length));
        } catch (Exception e) {
            throw new RuntimeException("-40008,xml内容不合法:" + e.getMessage());
        }
        // 校验appId是否一致
        if (appId != null && !fromAppId.trim().equals(appId)) {
            throw new RuntimeException("-40005,校验AppID失败,expect " + appId + ",but actual is " + fromAppId);
        }
        return xmlContent;
    }
}
