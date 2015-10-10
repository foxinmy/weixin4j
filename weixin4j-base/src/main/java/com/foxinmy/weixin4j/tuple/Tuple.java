package com.foxinmy.weixin4j.tuple;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

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
	@XmlTransient
	public String getMessageType();
}
