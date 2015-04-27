package com.foxinmy.weixin4j.tuple;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 群发视频对象
 * <p>
 * <font color="red">可用于「群发消息」</font>
 * </p>
 * 
 * @className MpVideo
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class MpVideo implements MassTuple {

	private static final long serialVersionUID = 2167437425244069128L;

	@Override
	public String getMessageType() {
		return "mpvideo";
	}

	/**
	 * 上传视频后微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XStreamAlias("MediaId")
	private String mediaId;

	public MpVideo(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toString() {
		return "MpVideo [mediaId=" + mediaId + "]";
	}
}
