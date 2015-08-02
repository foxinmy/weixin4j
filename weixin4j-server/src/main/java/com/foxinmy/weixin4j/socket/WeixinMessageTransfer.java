package com.foxinmy.weixin4j.socket;

import java.io.Serializable;

import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.AesToken;

/**
 * 消息传递
 * 
 * @className WeixinMessageTransfer
 * @author jy
 * @date 2015年6月23日
 * @since JDK 1.7
 * @see
 */
public class WeixinMessageTransfer implements Serializable {

	private static final long serialVersionUID = 7779948135156353261L;

	/**
	 * aes & token
	 */
	private AesToken aesToken;
	/**
	 * 加密类型
	 */
	private EncryptType encryptType;

	/**
	 * 消息接收方
	 */
	private String toUserName;
	/**
	 * 消息发送方
	 */
	private String fromUserName;

	public WeixinMessageTransfer(AesToken aesToken, EncryptType encryptType,
			String toUserName, String fromUserName) {
		this.aesToken = aesToken;
		this.encryptType = encryptType;
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
	}

	public AesToken getAesToken() {
		return aesToken;
	}

	public EncryptType getEncryptType() {
		return encryptType;
	}

	public String getToUserName() {
		return toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	@Override
	public String toString() {
		return "WeixinMessageTransfer [aesToken=" + aesToken + ", encryptType="
				+ encryptType + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + "]";
	}
}
