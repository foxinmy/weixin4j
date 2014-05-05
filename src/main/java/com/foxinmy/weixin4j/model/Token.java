package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * access_token是公众号的全局唯一票据,公众号调用各接口时都需使用access_token,正常情况下access_token有效期为7200秒,重复获取将导致上次获取的access_token失效
 * 
 * @className Token
 * @author jy.hu
 * @date 2014年4月5日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">获取token</a>
 */
@XStreamAlias("app-token")
public class Token implements Serializable {

	private static final long serialVersionUID = 1L;
	private String access_token;
	private int expires_in;
	private String openid;
	private long time;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Token) {
			return access_token.equals(((Token) obj).getAccess_token());
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Token access_token=").append(access_token);
		sb.append(", expires_in=").append(expires_in);
		sb.append(", openid=").append(openid);
		sb.append(", time=").append(time).append("]");
		return sb.toString();
	}
}
