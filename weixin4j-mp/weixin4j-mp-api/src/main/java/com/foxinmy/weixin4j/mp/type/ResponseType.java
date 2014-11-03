package com.foxinmy.weixin4j.mp.type;

import com.foxinmy.weixin4j.mp.response.ArticleResponse;
import com.foxinmy.weixin4j.mp.response.BaseResponse;
import com.foxinmy.weixin4j.mp.response.ImageResponse;
import com.foxinmy.weixin4j.mp.response.MusicResponse;
import com.foxinmy.weixin4j.mp.response.TextResponse;
import com.foxinmy.weixin4j.mp.response.TransferResponse;
import com.foxinmy.weixin4j.mp.response.VideoResponse;
import com.foxinmy.weixin4j.mp.response.VoiceResponse;

/**
 * 
 * 响应类型
 * 
 * @author jy.hu
 * 
 */
public enum ResponseType {
	text(TextResponse.class), image(ImageResponse.class), voice(
			VoiceResponse.class), video(VideoResponse.class), music(
			MusicResponse.class), news(ArticleResponse.class), transfer_customer_service(
			TransferResponse.class);
	private Class<? extends BaseResponse> messageClass;

	ResponseType(Class<? extends BaseResponse> messageClass) {
		this.messageClass = messageClass;
	}

	public void setMessageClass(Class<? extends BaseResponse> messageClass) {
		this.messageClass = messageClass;
	}

	public Class<? extends BaseResponse> getMessageClass() {
		return messageClass;
	}
}
