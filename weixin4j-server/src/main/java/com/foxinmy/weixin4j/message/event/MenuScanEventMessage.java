package com.foxinmy.weixin4j.message.event;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * 扫码推事件(scancode_push|scancode_waitmsg)
 * 
 * @className MenuScanEventMessage
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/9/981d772286d10d153a3dc4286c1ee5b5.html#scancode_push.EF.BC.9A.E6.89.AB.E7.A0.81.E6.8E.A8.E4.BA.8B.E4.BB.B6.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">订阅号、服务号的扫码推事件</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E6.89.AB.E7.A0.81.E6.8E.A8.E4.BA.8B.E4.BB.B6.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">企业号的的扫码推事件</a>
 */
public class MenuScanEventMessage extends MenuEventMessage {

	private static final long serialVersionUID = 3142350663022709730L;

	/**
	 * 扫描信息
	 */
	@XmlElement(name = "ScanCodeInfo")
	private ScanInfo scanInfo;

	public ScanInfo getScanInfo() {
		return scanInfo;
	}

	/**
	 * 扫描信息
	 * 
	 * @className ScanInfo
	 * @author jy
	 * @date 2015年3月29日
	 * @since JDK 1.7
	 * @see
	 */
	public static class ScanInfo implements Serializable {

		private static final long serialVersionUID = 2237570238164900421L;
		/**
		 * 扫描类型，一般是qrcode
		 */
		@XmlElement(name = "ScanType")
		private String type;
		/**
		 * 扫描结果，即二维码对应的字符串信息
		 */
		@XmlElement(name = "ScanResult")
		private String result;

		public String getType() {
			return type;
		}

		public String getResult() {
			return result;
		}

		@Override
		public String toString() {
			return "ScanInfo [type=" + type + ", result=" + result + "]";
		}
	}

	@Override
	public String toString() {
		return "MenuScanEventMessage [scanInfo=" + scanInfo + ", "
				+ super.toString() + "]";
	}
}
