package com.foxinmy.weixin4j.wxa;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 对微信小程序用户加密数据的解密。
 *
 * @since 1.8
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html">开放数据校验与解密</a>
 */
public class WXBizDataCrypt {

	private final String appid;

	private final String sessionKey;

	public WXBizDataCrypt(String appid, String sessionKey) {
		this.appid = appid;
		this.sessionKey = sessionKey;
	}

	/**
	 * 解密微信小程序用户加密数据.
	 *
	 * @param encryptedData 加密的用户数据.
	 * @param iv 与用户数据一同返回的初始向量.
	 * @return 解密后的原文.
	 */
	public JSONObject decryptData(final String encryptedData, final String iv) {
		final byte[] aesKey = Base64.decodeBase64(sessionKey);
		final byte[] aesCipher = Base64.decodeBase64(encryptedData);
		final byte[] aesIV = Base64.decodeBase64(iv);

		final byte[] decryptedBytes = AESUtils.decrypt(aesCipher, aesKey, aesIV);
		final String decryptedText = new String(decryptedBytes, Charset.forName("UTF-8"));
		final JSONObject decrypted = JSON.parseObject(decryptedText);

		final String appId = decrypted.getJSONObject("watermark").getString("appid");
		if (!appId.equals(this.appid)) {
			throw new IllegalArgumentException("Invalid Buffer");
		}

		return decrypted;
	}

}
