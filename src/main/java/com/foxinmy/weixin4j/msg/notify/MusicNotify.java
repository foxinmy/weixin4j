package com.foxinmy.weixin4j.msg.notify;

import com.foxinmy.weixin4j.msg.model.Music;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 客服音乐消息
 * 
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E9.9F.B3.E4.B9.90.E6.B6.88.E6.81.AF">客服音乐消息</a>
 * @see com.foxinmy.weixin4j.msg.model.Music
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify#toJson()
 */
public class MusicNotify extends BaseNotify {

	private static final long serialVersionUID = -7698823863398518425L;

	public MusicNotify() {
		super(MessageType.music);
	}

	public MusicNotify(String touser) {
		super(touser, MessageType.music);
	}

	public void pushMusic(String musicurl, String hqUrl, String mediaId) {
		this.music = new Music(null, null, musicurl, hqUrl, mediaId);
	}

	private Music music;

	public void setMusic(Music music) {
		this.music = music;
	}

	@Override
	public String toString() {
		return String.format("[MusicNotify touser=%s ,msgtype=%s ,music=%s]",
				super.getTouser(), super.getMsgtype().name(),
				this.music.toString());
	}
}
