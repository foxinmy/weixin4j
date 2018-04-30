package com.foxinmy.weixin4j.wxa;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/signature.html#wxchecksessionobject">加密数据解密算法</a>
 */
public class WXBizDataCrypt {

	private final String appid;

	private final String sessionKey;

	public WXBizDataCrypt(String appid, String sessionKey) {
		this.appid = appid;
		this.sessionKey = sessionKey;
	}

	public JSONObject decryptData(final String encryptedData, final String iv) {
		final byte[] aesKey = Base64.decodeBase64(sessionKey);
		final byte[] aesCipher = Base64.decodeBase64(encryptedData);
		final byte[] aesIV = Base64.decodeBase64(iv);

		final byte[] resultByte;
		try {
			resultByte = AESUtils.decrypt(aesCipher, aesKey, aesIV);
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException(e);
		}

		final String decryptedText = new String(resultByte, Charset.forName("UTF-8"));
		final JSONObject decrypted = JSON.parseObject(decryptedText);

		final String appId = decrypted.getJSONObject("watermark").getString("appid");
		if (!appId.equals(this.appid)) {
			throw new IllegalArgumentException("Invalid Buffer");
		}

		return decrypted;
	}

}
