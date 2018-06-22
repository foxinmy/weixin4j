package com.foxinmy.weixin4j.wxa.model.custommessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文本消息。
 *
 * @since 1.8
 */
public class Text extends com.foxinmy.weixin4j.tuple.Text {

	private static final long serialVersionUID = 2018052901L;

	public Text(String content) {
		super(content);
	}

	@Override
	@JSONField(serialize = false)
	public String getMessageType() {
		return super.getMessageType();
	}

}
