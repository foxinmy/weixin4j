package com.foxinmy.weixin4j.tuple;

import java.beans.Transient;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 消息元件
 * 
 * @className Tuple
 * @author jy
 * @date 2015年4月19日
 * @since JDK 1.7
 * @see
 */
public interface Tuple extends Serializable {

	/**
	 * 消息类型
	 * 
	 * @return
	 */
	@Transient
	@JSONField(deserialize = false, serialize = false)
	public String getMessageType();
}
