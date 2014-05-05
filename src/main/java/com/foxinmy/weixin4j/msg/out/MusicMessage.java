package com.foxinmy.weixin4j.msg.out;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复音乐消息
 * @className MusicMessage
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E9.9F.B3.E4.B9.90.E6.B6.88.E6.81.AF">回复音乐消息</a>
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()   
 */
public class MusicMessage extends BaseMessage {

	private static final long serialVersionUID = 4384403772658796395L;

	public MusicMessage() {
		super(MessageType.music);
	}

	@XStreamAlias("Music")
	private Music music;

	public Music getMusic() {
		return this.music;
	}

	public void pushMusic(String title, String desc, String url, String hqUrl, String mediaId) {
		this.music = new Music();
		this.music.thumbMediaId = mediaId;
		this.music.desc = desc;
		this.music.hqMusicUrl = hqUrl;
		this.music.MusicURL = url;
		this.music.title = title;
	}

	private static class Music {
		@XStreamAlias("Title")
		private String title; // 音乐标题
		@XStreamAlias("Description")
		private String desc; // 音乐描述
		@XStreamAlias("MusicURL")
		private String MusicURL; // 音乐链接
		@XStreamAlias("HQMusicUrl")
		private String hqMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
		@XStreamAlias("ThumbMediaId")
		private String thumbMediaId; // 缩略图的媒体id，通过上传多媒体文件，得到的id
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(Music ,title=").append(title);
			sb.append(" ,description=").append(desc);
			sb.append(" ,musicURL=").append(MusicURL);
			sb.append(" ,hqMusicUrl=").append(hqMusicUrl);
			sb.append(" ,thumbMediaId=").append(thumbMediaId).append(")");
			return sb.toString();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[MusicMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,music=").append(getMusic().toString());
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
