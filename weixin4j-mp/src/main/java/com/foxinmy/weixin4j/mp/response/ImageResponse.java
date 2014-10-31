package com.foxinmy.weixin4j.mp.response;

import com.foxinmy.weixin4j.mp.msg.model.Image;
import com.foxinmy.weixin4j.mp.type.ResponseType;
import com.foxinmy.weixin4j.msg.BaseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复图片消息
 * 
 * @className ImageResponse
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E5.9B.BE.E7.89.87.E6.B6.88.E6.81.AF">回复图片消息</a>
 * @see com.foxinmy.weixin4j.mp.msg.model.Image
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse#toXml()
 */
@XStreamAlias("xml")
public class ImageResponse extends BaseResponse {
	private static final long serialVersionUID = 6998255203997554731L;

	public ImageResponse(BaseMessage inMessage) {
		this(null, inMessage);
	}

	public ImageResponse(String mediaId, BaseMessage inMessage) {
		super(ResponseType.image, inMessage);
		super.getMsgType().setMessageClass(ImageResponse.class);
		this.pushMediaId(mediaId);
	}

	@XStreamAlias("Image")
	private Image image;

	public void pushMediaId(String mediaId) {
		this.image = new Image(mediaId);
	}

	@Override
	public String toXml() {
		xmlStream.aliasField("MediaId", Image.class, "mediaId");
		return xmlStream.toXML(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ImageResponse ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,mediaId=").append(this.image.getMediaId());
		sb.append(" ,createTime=").append(super.getCreateTime()).append("]");
		return sb.toString();
	}
}
