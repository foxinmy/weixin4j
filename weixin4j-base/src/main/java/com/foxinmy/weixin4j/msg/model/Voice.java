package com.foxinmy.weixin4j.msg.model;

import com.foxinmy.weixin4j.type.MediaType;

/**
 * 语音对象
 * <p>
 * <font color="red">可用于「被动消息」「客服消息」「群发消息」</font>
 * </p>
 * 
 * @className Voice
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Voice extends Image implements Responseable, Notifyable, Massable {

	private static final long serialVersionUID = 8853054484809101524L;

	public Voice(String mediaId) {
		super(MediaType.voice, mediaId);
	}
}
