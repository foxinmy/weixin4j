package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Arrays;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 二维码
 * 
 * @className QRResult
 * @author jy
 * @date 2015年7月29日
 * @since JDK 1.6
 * @see
 */
public class QRResult implements Serializable {

	private static final long serialVersionUID = 7730781702153258151L;

	private String ticket;
	private String url;
	@JSONField(name = "expire_seconds")
	private int expireSeconds;
	private byte[] content;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "QRResult [ticket=" + ticket + ", url=" + url
				+ ", expireSeconds=" + expireSeconds + ", content="
				+ Arrays.toString(content) + "]";
	}
}
