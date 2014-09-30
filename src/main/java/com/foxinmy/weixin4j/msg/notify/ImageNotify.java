package com.foxinmy.weixin4j.msg.notify;

import com.foxinmy.weixin4j.msg.model.Image;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 客服图片消息
 * 
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E5.9B.BE.E7.89.87.E6.B6.88.E6.81.AF">客服图片消息</a>
 * @see com.foxinmy.weixin4j.msg.model.Image
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify#toJson()
 */
public class ImageNotify extends BaseNotify {

	private static final long serialVersionUID = -7698823863398518425L;

	public ImageNotify() {
		super(MessageType.image);
	}

	public ImageNotify(String touser) {
		super(touser, MessageType.image);
	}

	public ImageNotify(String mediaId, String touser) {
		super(touser, MessageType.image);
		this.pushMediaId(mediaId);
	}

	public void pushMediaId(String mediaId) {
		this.image = new Image(mediaId);
	}

	private Image image;

	@Override
	public String toString() {
		return String.format("[ImageNotify touser=%s ,msgtype=%s ,mediaId=%s]",
				super.getTouser(), super.getMsgtype().name(),
				this.image.getMediaId());
	}
}
