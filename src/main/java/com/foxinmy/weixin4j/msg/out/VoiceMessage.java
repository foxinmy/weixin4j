package com.foxinmy.weixin4j.msg.out;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.model.Voice;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 语音消息
 * 
 * @className VoiceMessage
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E8.AF.AD.E9.9F.B3.E6.B6.88.E6.81.AF">回复语音消息</a>
 * @see com.foxinmy.weixin4j.msg.model.Voice
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()
 */
public class VoiceMessage extends BaseMessage {

	private static final long serialVersionUID = -7944926238652243793L;

	public VoiceMessage(BaseMessage inMessage) {
		this(null, inMessage);
	}

	public VoiceMessage(String mediaId, BaseMessage inMessage) {
		super(MessageType.voice, inMessage);
		super.getMsgType().setMessageClass(VoiceMessage.class);
		this.pushMediaId(mediaId);
	}

	@XStreamAlias("Voice")
	private Voice voice;

	public void pushMediaId(String mediaId) {
		this.voice = new Voice(mediaId);
	}

	@Override
	public String toXml() {
		XStream xstream = getXStream();
		xstream.aliasField("MediaId", Voice.class, "mediaId");
		return xstream.toXML(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[VoiceMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,mediaId=").append(voice.getMediaId());
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
