package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * access_token是公众号的全局唯一票据,公众号调用各接口时都需使用access_token,正常情况下access_token有效期为7200秒,
 * 重复获取将导致上次获取的access_token失效
 * 
 * @className Token
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月5日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN">微信公众平台获取token</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%BB%E5%8A%A8%E8%B0%83%E7%94%A8">微信企业号的主动模式</a>
 */
public class Token implements Serializable {

	private static final long serialVersionUID = -7564855472419104084L;

	/**
	 * 获取到的凭证
	 */
	@JSONField(name = "access_token")
	private String accessToken;
	/**
	 * 凭证有效时间，单位：秒
	 */
	@JSONField(name = "expires_in")
	private int expiresIn;
	/**
	 * token创建的时间,单位：毫秒
	 */
	@JSONField(name = "create_time")
	private long createTime;
	/**
	 * 请求返回的原始结果
	 */
	@JSONField(name = "original_result")
	private String originalResult;

	protected Token() {
		// jaxb required
	}

	public Token(String accessToken) {
		this.accessToken = accessToken;
		this.createTime = System.currentTimeMillis();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOriginalResult() {
		return originalResult;
	}

	public void setOriginalResult(String originalResult) {
		this.originalResult = originalResult;
	}

	@Override
	public String toString() {
		return "Token [accessToken=" + accessToken + ", expiresIn=" + expiresIn
				+ ", createTime=" + createTime + "]";
	}
}
