package com.foxinmy.weixin4j.tuple;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图片对象
 * <p>
 * <font color="red">可用于「客服消息」「群发消息」及企业号的「聊天消息」</font>
 * </p>
 * 
 * @className Image
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see
 */
public class Image implements MassTuple, NotifyTuple, ChatTuple {

	private static final long serialVersionUID = 6928681900960656161L;

	@Override
	public String getMessageType() {
		return "image";
	}

	/**
	 * 上传后的微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XmlElement(name = "MediaId")
	private String mediaId;

	@JSONCreator
	public Image(@JSONField(name = "mediaId") String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	@Override
	public String toString() {
		return "Image [mediaId=" + mediaId + "]";
	}
}
