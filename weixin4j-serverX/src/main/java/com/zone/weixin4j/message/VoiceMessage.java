package com.zone.weixin4j.message;

import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.type.MessageType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 语音消息
 * <p>
 * 开通语音识别功能,用户每次发送语音给公众号时,微信会在推送的语音消息XML数据包中,赋值到Recongnition字段.
 * </p>
 * 
 * @className VoiceMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453&token=&lang=zh_CN">订阅号、服务号的语音消息</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#voice.E6.B6.88.E6.81.AF">企业号的语音消息</a>
 */
public class VoiceMessage extends WeixinMessage {

	private static final long serialVersionUID = -7988380977182214003L;

	public VoiceMessage() {
		super(MessageType.voice.name());
	}

	/**
	 * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	@XmlElement(name = "MediaId")
	private String mediaId;
	/**
	 * 语音格式，如amr，speex等
	 */
	@XmlElement(name = "Format")
	private String format;
	/**
	 * 语音识别结果，UTF8编码
	 */
	@XmlElement(name = "Recognition")
	private String recognition;

	public String getRecognition() {
		return recognition;
	}

	public String getMediaId() {
		return mediaId;
	}

	public String getFormat() {
		return format;
	}

	@Override
	public String toString() {
		return "VoiceMessage [mediaId=" + mediaId + ", format=" + format
				+ ", recognition=" + recognition + ", " + super.toString()
				+ "]";
	}
}
