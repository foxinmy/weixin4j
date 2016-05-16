package com.foxinmy.weixin4j.tuple;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文件对象
 * <p>
 * <font color="red">可用于企业号的「客服消息」及「聊天消息」</font>
 * </p>
 * 
 * @className File
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月21日
 * @since JDK 1.6
 * @see
 */
public class File implements NotifyTuple, ChatTuple {

	private static final long serialVersionUID = -8149837316289636110L;

	@Override
	public String getMessageType() {
		return "file";
	}

	/**
	 * 上传后的微信返回的媒体ID
	 */
	@JSONField(name = "media_id")
	@XmlElement(name = "MediaId")
	private String mediaId;

	@JSONCreator
	public File(@JSONField(name = "mediaId") String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	@Override
	public String toString() {
		return "File [mediaId=" + mediaId + "]";
	}
}
