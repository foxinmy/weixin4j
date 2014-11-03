package com.foxinmy.weixin4j.mp.mapping;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.foxinmy.weixin4j.type.MessageType;

/**
 * 获取默认的Mapping Key 如text,event_click
 * 
 * @className AbstractActionMapping
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public abstract class AbstractActionMapping implements ActionMapping {

	protected String getMappingKey(String xmlMsg) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlMsg);
		String msgType = doc.selectSingleNode("/xml/MsgType").getStringValue();
		if (msgType.equalsIgnoreCase(MessageType.event.name())) {
			msgType += "_"
					+ doc.selectSingleNode("/xml/Event").getStringValue();
		}
		return msgType.toLowerCase();
	}
}
