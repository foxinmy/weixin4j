package com.foxinmy.weixin4j.msg.notify;

import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 客服语音消息
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E8.AF.AD.E9.9F.B3.E6.B6.88.E6.81.AF">客服语音消息</a>
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify#toJson()
 */
public class VoiceNotify extends BaseNotify {

	private static final long serialVersionUID = -7698823863398518425L;

	public VoiceNotify() {
		super(MessageType.voice);
	}

	public VoiceNotify(String mediaId) {
		super(MessageType.voice);
		this.voice = new $(mediaId);
	}

	public VoiceNotify(String mediaId, String touser) {
		super(touser, MessageType.voice);
		this.voice = new $(mediaId);
	}

	@XStreamAlias("voice")
	private $ voice;

	private static class $ {
		@XStreamAlias("media_id")
		private String mediaId;

		public $(String mediaId) {
			this.mediaId = mediaId;
		}
	}

	@Override
	public String toString() {
		return String.format("[VoiceNotify touser=%s ,msgtype=%s ,mediaId=%s]", super.getTouser(), super.getMsgtype().name(), this.voice.mediaId);
	}
}
