/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.zone.weixin4j.util;

import java.util.Arrays;

/**
 * 提供基于PKCS7算法的加解密接口</br>
 * 提供接收和推送给公众平台消息的加解密接口(UTF8编码的字符串).
 * <ol>
 * <li>第三方回复加密消息给公众平台</li>
 * <li>第三方收到公众平台发送的消息，验证消息的安全性，并对消息进行解密。</li>
 * </ol>
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * <li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 * http://www.oracle.com/technetwork/java/javase
 * /downloads/jce-7-download-432124.html</li>
 * <li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * <li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * <li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
public class PKCS7Encoder {
	private final static int BLOCK_SIZE = 32;

	/**
	 * 获得对明文进行补位填充的字节.
	 * 
	 * @param count
	 *            需要进行填充补位操作的明文字节个数
	 * @return 补齐用的字节数组
	 */
	public static byte[] encode(int count) {
		// 计算需要填充的位数
		int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
		if (amountToPad == 0) {
			amountToPad = BLOCK_SIZE;
		}
		// 获得补位所用的字符
		byte target = (byte) (amountToPad & 0xFF);
		char padChr = (char) target;
		StringBuilder tmp = new StringBuilder();
		for (int index = 0; index < amountToPad; index++) {
			tmp.append(padChr);
		}
		return tmp.toString().getBytes(ServerToolkits.UTF_8);
	}

	/**
	 * 删除解密后明文的补位字符
	 * 
	 * @param decrypted
	 *            解密后的明文
	 * @return 删除补位字符后的明文
	 */
	public static byte[] decode(byte[] decrypted) {
		int pad = (int) decrypted[decrypted.length - 1];
		if (pad < 1 || pad > 32) {
			pad = 0;
		}
		return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
	}
}