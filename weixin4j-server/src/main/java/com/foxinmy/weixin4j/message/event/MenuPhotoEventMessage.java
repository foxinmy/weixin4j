package com.foxinmy.weixin4j.message.event;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 弹出拍照或者相册发图的事件推送(pic_sysphoto|pic_photo_or_album|pic_weixin)
 * 
 * @className MenuPhotoEventMessage
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/9/981d772286d10d153a3dc4286c1ee5b5.html#pic_sysphoto.EF.BC.9A.E5.BC.B9.E5.87.BA.E7.B3.BB.E7.BB.9F.E6.8B.8D.E7.85.A7.E5.8F.91.E5.9B.BE.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">订阅号、服务号的系统发图的事件推送</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E5.BC.B9.E5.87.BA.E7.B3.BB.E7.BB.9F.E6.8B.8D.E7.85.A7.E5.8F.91.E5.9B.BE.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">企业号的系统发图的事件推送</a>
 */
public class MenuPhotoEventMessage extends MenuEventMessage {

	private static final long serialVersionUID = 3142350663022709730L;

	/**
	 * 发送的图片信息
	 */
	@XmlElement(name = "SendPicsInfo")
	private PictureInfo pictureInfo;

	public PictureInfo getPictureInfo() {
		return pictureInfo;
	}

	/**
	 * 图片信息
	 * 
	 * @className PictureInfo
	 * @author jy
	 * @date 2015年3月29日
	 * @since JDK 1.7
	 * @see
	 */
	public static class PictureInfo implements Serializable {

		private static final long serialVersionUID = -3361375879168233258L;

		/**
		 * 发送的图片数量
		 */
		@XmlElement(name = "Count")
		private int count;
		/**
		 * 图片列表
		 */
		@XmlElementWrapper(name = "PicList")
		@XmlElement(name = "item")
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

	/**
	 * 图片
	 * 
	 * @className PictureItem
	 * @author jy
	 * @date 2015年3月29日
	 * @since JDK 1.7
	 * @see
	 */
	public static class PictureItem implements Serializable {

		private static final long serialVersionUID = -7636697449096645590L;

		/**
		 * 图片的MD5值，开发者若需要，可用于验证接收到图片
		 */
		@XmlElement(name = "PicMd5Sum")
		private String md5;

		public String getMd5() {
			return md5;
		}

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
