package com.zone.weixin4j.response;

/**
 * 回复语音消息
 * 
 * @className VoiceResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see
 */
public class VoiceResponse implements WeixinResponse {

	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String mediaId;

	public VoiceResponse(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toContent() {
		return String.format(
				"<Voice><MediaId><![CDATA[%s]]></MediaId></Voice>", mediaId);
	}

	public String getMediaId() {
		return mediaId;
	}

	@Override
	public String getMsgType() {
		return "voice";
	}
}
