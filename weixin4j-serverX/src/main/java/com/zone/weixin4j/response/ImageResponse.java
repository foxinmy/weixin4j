package com.zone.weixin4j.response;

/**
 * 回复图片消息
 * 
 * @className ImageResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see
 */
public class ImageResponse implements WeixinResponse {

	/**
	 * 通过上传多媒体文件，得到的id。
	 */
	private String mediaId;

	public ImageResponse(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toContent() {
		return String.format(
				"<Image><MediaId><![CDATA[%s]]></MediaId></Image>", mediaId);
	}

	public String getMediaId() {
		return mediaId;
	}

	@Override
	public String getMsgType() {
		return "image";
	}
}
