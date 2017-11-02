package com.zone.weixin4j.message;

import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.type.MessageType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 图片消息
 * 
 * @className ImageMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453&token=&lang=zh_CN">订阅号、服务号的图片消息</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#image.E6.B6.88.E6.81.AF">企业号的图片消息</a>
 */
public class ImageMessage extends WeixinMessage {

	private static final long serialVersionUID = 8430800898756567016L;

	public ImageMessage() {
		super(MessageType.image.name());
	}

	/**
	 * 图片链接
	 */
	@XmlElement(name = "PicUrl")
	private String picUrl;
	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	@XmlElement(name = "MediaId")
	private String mediaId;

	public String getPicUrl() {
		return picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	@Override
	public String toString() {
		return "ImageMessage [picUrl=" + picUrl + ", mediaId=" + mediaId + ", "
				+ super.toString() + "]";
	}
}
