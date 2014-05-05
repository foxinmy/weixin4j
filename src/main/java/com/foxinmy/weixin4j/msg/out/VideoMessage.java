package com.foxinmy.weixin4j.msg.out;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复视频消息
 * @className VideoMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E8.A7.86.E9.A2.91.E6.B6.88.E6.81.AF">回复视频消息</a>
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()  
 */
public class VideoMessage extends BaseMessage {

	private static final long serialVersionUID = -1013075358679078381L;

	public VideoMessage() {
		super(MessageType.video);
		super.getMsgType().setMessageClass(VideoMessage.class);
	}

	@XStreamAlias("Video")
	private Video video;

	public Video getVideo() {
		return this.video;
	}

	public void pushVideo(String mediaId, String title, String desc) {
		this.video = new Video();
		this.video.desc = desc;
		this.video.mediaId = mediaId;
		this.video.title = title;
	}

	private static class Video {
		@XStreamAlias("MediaId")
		private String mediaId;
		@XStreamAlias("Title")
		private String title;
		@XStreamAlias("Description")
		private String desc;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(Video mediaId=").append(mediaId);
			sb.append(" ,title=").append(title);
			sb.append(" ,desc=").append(desc).append(")");
			return super.toString();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[VideoMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,video=").append(getVideo().toString());
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
