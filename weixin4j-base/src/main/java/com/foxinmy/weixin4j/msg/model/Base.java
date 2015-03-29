package com.foxinmy.weixin4j.msg.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.MediaType;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 消息对象基类
 * 
 * @className Base
 * @author jy
 * @date 2015年3月21日
 * @since JDK 1.7
 * @see
 */
public class Base implements Serializable {

	private static final long serialVersionUID = 8487251213352068227L;

	/**
	 * 媒体类型
	 */
	@JSONField(serialize = false)
	@XStreamOmitField
	private MediaType mediaType;

	public Base(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public String toString() {
		return "Base [mediaType=" + mediaType + "]";
	}
}
