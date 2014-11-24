package com.foxinmy.weixin4j.action.mapping;

import org.dom4j.DocumentException;

import com.foxinmy.weixin4j.action.WeixinAction;

/**
 * 可扩展的Mapping接口
 * 
 * @className ActionMapping
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public interface ActionMapping {
	public WeixinAction getAction(String xmlMsg) throws DocumentException;
}
