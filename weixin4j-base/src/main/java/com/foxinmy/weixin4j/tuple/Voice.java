package com.foxinmy.weixin4j.tuple;


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
public class Voice extends Image implements ResponseTuple, NotifyTuple {

	private static final long serialVersionUID = 8853054484809101524L;

	@Override
	public String getMessageType() {
		return "voice";
	}

	public Voice(String mediaId) {
		super(mediaId);
	}
}
