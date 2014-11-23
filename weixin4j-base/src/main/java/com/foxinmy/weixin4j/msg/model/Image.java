package com.foxinmy.weixin4j.msg.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.MediaType;
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
public class Image extends Base implements Responseable, Notifyable, Massable {

	private static final long serialVersionUID = 6928681900960656161L;

	@JSONField(name = "media_id")
	@XStreamAlias("MediaId")
	private String mediaId;

	public Image(String mediaId) {
		super(MediaType.image);
		this.mediaId = mediaId;
	}

	public Image(MediaType mediaType, String mediaId) {
		super(mediaType);
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
