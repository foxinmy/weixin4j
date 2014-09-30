package com.foxinmy.weixin4j.msg.model;

import com.foxinmy.weixin4j.type.MediaType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 图片对象
 * 
 * @className Image
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Image extends BaseMsg {

	private static final long serialVersionUID = 1L;

	@XStreamAlias("media_id")
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
	public MediaType getMediaType() {
		return MediaType.image;
	}
}
