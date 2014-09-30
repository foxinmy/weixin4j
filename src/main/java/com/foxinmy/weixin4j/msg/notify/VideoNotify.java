package com.foxinmy.weixin4j.msg.notify;

import com.foxinmy.weixin4j.msg.model.Video;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 客服视频消息
 * 
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E8.A7.86.E9.A2.91.E6.B6.88.E6.81.AF">客服视频消息</a>
 * @see com.foxinmy.weixin4j.msg.model.Video
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify#toJson()
 */
public class VideoNotify extends BaseNotify {

	private static final long serialVersionUID = -7698823863398518425L;

	public VideoNotify() {
		super(MessageType.video);
	}

	public VideoNotify(String touser) {
		super(touser, MessageType.video);
	}
	
	public void pushVideo(String mediaId, String thumbMediaId) {
		this.video = new Video(mediaId, thumbMediaId);
	}

	private Video video;

	public void setVideo(Video video) {
		this.video = video;
	}

	@Override
	public String toString() {
		return String.format("[VideoNotify touser=%s ,msgtype=%s ,video=%s]",
				super.getTouser(), super.getMsgtype().name(),
				this.video.toString());
	}
}
