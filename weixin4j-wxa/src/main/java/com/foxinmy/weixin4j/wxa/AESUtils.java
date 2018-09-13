package com.foxinmy.weixin4j.wxa;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @since 1.8
 */
final class AESUtils {

	private static boolean initialized = false;

	private AESUtils() {
	}

	/**
	 * AES解密
	 *
	 * @param content 密文
	 * @param keyByte key
	 * @param ivByte 初始向量
	 * @return 明文
	 */
	static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");

			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte)); // 初始化
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static synchronized void initialize() {
		if (initialized) {
			return;
		}

		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

	private static AlgorithmParameters generateIV(byte[] iv) throws Exception {
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}

}
