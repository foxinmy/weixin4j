package com.foxinmy.weixin4j.wxa.model.custommessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图片消息。
 *
 * @since 1.8
 */
public class Image extends com.foxinmy.weixin4j.tuple.Image {

	private static final long serialVersionUID = 2018052901L;

	public Image(String mediaId) {
		super(mediaId);
	}

	@Override
	@JSONField(serialize = false)
	public String getMessageType() {
		return super.getMessageType();
	}

}
