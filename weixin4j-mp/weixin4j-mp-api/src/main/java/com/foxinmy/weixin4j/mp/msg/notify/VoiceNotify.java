package com.foxinmy.weixin4j.mp.msg.notify;

import com.foxinmy.weixin4j.mp.msg.model.Voice;
import com.foxinmy.weixin4j.mp.type.ResponseType;

/**
 * 客服语音消息
 * 
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E8.AF.AD.E9.9F.B3.E6.B6.88.E6.81.AF">客服语音消息</a>
 * @see com.foxinmy.weixin4j.mp.msg.model.Voice
 * @see com.foxinmy.weixin4j.mp.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.mp.msg.notify.BaseNotify#toJson()
 */
public class VoiceNotify extends BaseNotify {

	private static final long serialVersionUID = -7698823863398518425L;

	public VoiceNotify() {
		this(null, null);
	}

	public VoiceNotify(String touser) {
		this(null, touser);
	}

	public VoiceNotify(String mediaId, String touser) {
		super(touser, ResponseType.voice);
		this.pushMediaId(mediaId);
	}

	public void pushMediaId(String mediaId) {
		this.voice = new Voice(mediaId);
	}

	private Voice voice;

	@Override
	public String toString() {
		return String.format("[VoiceNotify touser=%s ,msgtype=%s ,mediaId=%s]",
				super.getTouser(), super.getMsgtype().name(),
				this.voice.getMediaId());
	}
}
