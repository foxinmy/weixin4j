package com.foxinmy.weixin4j.msg.notify;

import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 客服音乐消息
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E9.9F.B3.E4.B9.90.E6.B6.88.E6.81.AF">客服音乐消息</a>
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

	public void pushMusic(String title, String desc, String url, String hqUrl, String mediaId) {
		this.music = new $();
		this.music.thumbMediaId = mediaId;
		this.music.desc = desc;
		this.music.hqMusicUrl = hqUrl;
		this.music.musicUrl = url;
		this.music.title = title;
	}

	@XStreamAlias("music")
	private $ music;

	private static class $ {
		private String title;
		@XStreamAlias("description")
		private String desc;
		@XStreamAlias("musicurl")
		private String musicUrl;
		@XStreamAlias("hqmusicurl")
		private String hqMusicUrl;
		@XStreamAlias("thumb_media_id")
		private String thumbMediaId;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(music ,title=").append(title);
			sb.append(" ,description=").append(desc);
			sb.append(" ,musicURL=").append(musicUrl);
			sb.append(" ,hqMusicUrl=").append(hqMusicUrl);
			sb.append(" ,thumbMediaId=").append(thumbMediaId).append(")");
			return sb.toString();
		}

	}

	@Override
	public String toString() {
		return String.format("[VideoNotify touser=%s ,msgtype=%s ,music=%s]", super.getTouser(), super.getMsgtype().name(), this.music.toString());
	}
}
