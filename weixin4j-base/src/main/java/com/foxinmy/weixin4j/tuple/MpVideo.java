package com.foxinmy.weixin4j.tuple;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 群发视频对象
 * <p>
 * <font color="red">可用于「群发消息」</font>
 * </p>
 * 
 * @className MpVideo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
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
	@XmlElement(name = "MediaId")
	private String mediaId;

	@JSONCreator
	public MpVideo(@JSONField(name = "mediaId") String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	@Override
	public String toString() {
		return "MpVideo [mediaId=" + mediaId + "]";
	}
}
