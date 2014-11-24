package com.foxinmy.weixin4j.msg.event.menu;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 弹出拍照或者相册发图的事件推送(pic_sysphoto|pic_photo_or_album|pic_weixin)
 * 
 * @className MenuPhotoEventMessage
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81#pic_sysphoto.EF.BC.9A.E5.BC.B9.E5.87.BA.E7.B3.BB.E7.BB.9F.E6.8B.8D.E7.85.A7.E5.8F.91.E5.9B.BE.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">订阅号、服务号的系统发图的事件推送</a>
 *      @see <a href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E5.BC.B9.E5.87.BA.E7.B3.BB.E7.BB.9F.E6.8B.8D.E7.85.A7.E5.8F.91.E5.9B.BE.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">企业号的系统发图的事件推送</a>
 */
public class MenuPhotoEventMessage extends MenuEventMessage {

	private static final long serialVersionUID = 3142350663022709730L;

	@XStreamAlias("SendPicsInfo")
	private PictureInfo pictureInfo;

	public PictureInfo getPictureInfo() {
		return pictureInfo;
	}

	public static class PictureInfo {
		@XStreamAlias("Count")
		private int count;
		@XStreamAlias("PicList")
		private List<PictureItem> items;

		public int getCount() {
			return count;
		}

		public List<PictureItem> getItems() {
			return items;
		}

		@Override
		public String toString() {
			return "PictureInfo [count=" + count + ", items=" + items + "]";
		}
	}

	@XStreamAlias("item")
	public static class PictureItem {
		@XStreamAlias("PicMd5Sum")
		private String md5;

		@Override
		public String toString() {
			return "PictureItem [md5=" + md5 + "]";
		}
	}

	@Override
	public String toString() {
		return "MenuPhotoEventMessage [pictureInfo=" + pictureInfo + ", "
				+ super.toString() + "]";
	}
}
