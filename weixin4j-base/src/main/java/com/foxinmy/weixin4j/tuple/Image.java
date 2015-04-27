package com.foxinmy.weixin4j.tuple;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 图片对象
 * <p>
 * <font color="red">可用于「被动消息」「客服消息」「群发消息」</font>
 * </p>
 * 
 * @className Image
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Image implements ResponseTuple, MassTuple, NotifyTuple {

	private static final long serialVersionUID = 6928681900960656161L;

	@Override
	public String getMessageType() {
		return "image";
	}

	/**
	 * 上传后的微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XStreamAlias("MediaId")
	private String mediaId;

	public Image(String mediaId) {
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
		return "Image [mediaId=" + mediaId + "]";
	}
}
