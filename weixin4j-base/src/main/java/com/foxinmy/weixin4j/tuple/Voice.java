package com.foxinmy.weixin4j.tuple;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 语音对象
 * <p>
 * <font color="red">可用于「客服消息」「群发消息」</font>
 * </p>
 * 
 * @className Voice
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see
 */
public class Voice extends Image implements NotifyTuple {

	private static final long serialVersionUID = 8853054484809101524L;

	@Override
	public String getMessageType() {
		return "voice";
	}

	@JSONCreator
	public Voice(@JSONField(name = "mediaId") String mediaId) {
		super(mediaId);
	}
}
