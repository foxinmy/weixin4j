package com.foxinmy.weixin4j.settings;

import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenStorager;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信基础配置相关
 * 
 * @className Weixin4jSettings
 * @author jy
 * @date 2016年1月28日
 * @since JDK 1.6
 * @see
 */
public class Weixin4jSettings {

	private final WeixinAccount account;

	public Weixin4jSettings() {
		this(Weixin4jConfigUtil.getWeixinAccount());
	}

	/**
	 * 
	 * @param id
	 *            应用唯一标识 appid/corpid
	 * @param secret
	 *            应用接口密钥
	 */
	public Weixin4jSettings(String id, String secret) {
		this(new WeixinAccount(id, secret));
	}

	public Weixin4jSettings(WeixinAccount account) {
		this.account = account;
	}

	/**
	 * 微信账号信息
	 */
	public WeixinAccount getAccount() {
		return this.account;
	}

	private String tokenPath;

	/**
	 * 使用FileTokenStorager时token的存放路径
	 * 
	 * @param tokenPath
	 *            硬盘目录
	 * @return
	 */
	public Weixin4jSettings setTokenPath(String tokenPath) {
		this.tokenPath = tokenPath;
		return this;
	}

	/**
	 * 默认token的存放路径
	 */
	public static final String DEFAULT_TOKEN_PATH = "/tmp/weixin4j/token";

	/**
	 * 使用FileTokenStorager时token的存放路径,默认值为{@link #DEFAULT_TOKEN_PATH}
	 */
	public String getTokenPath() {
		if (StringUtil.isBlank(tokenPath)) {
			tokenPath = Weixin4jConfigUtil.getClassPathValue("token.path",
					DEFAULT_TOKEN_PATH);
		}
		return tokenPath;
	}

	private String qrcodePath;

	/**
	 * 二维码保存路径
	 * 
	 * @param qrcodePath
	 *            硬盘目录
	 */
	public Weixin4jSettings setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
		return this;
	}

	/**
	 * 默认二维码保存路径
	 */
	public static final String DEFAULT_QRCODE_PATH = "/tmp/weixin4j/qrcode";

	/**
	 * 二维码保存路径,默认值为{@link #DEFAULT_QRCODE_PATH}
	 */
	public String getQrcodePath() {
		if (StringUtil.isBlank(qrcodePath)) {
			this.qrcodePath = Weixin4jConfigUtil.getClassPathValue(
					"qrcode.path", DEFAULT_QRCODE_PATH);
		}
		return this.qrcodePath;
	}

	private String mediaPath;

	/**
	 * 媒体文件保存路径
	 * 
	 * @param mediaPath
	 *            硬盘目录
	 */
	public Weixin4jSettings setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
		return this;
	}

	/**
	 * 默认媒体文件保存路径
	 */
	public static final String DEFAULT_MEDIA_PATH = "/tmp/weixin4j/media";

	/**
	 * 媒体文件保存路径,默认值为{@link #DEFAULT_MEDIA_PATH}
	 */
	public String getMediaPath() {
		if (StringUtil.isBlank(mediaPath)) {
			this.mediaPath = Weixin4jConfigUtil.getClassPathValue("media.path",
					DEFAULT_MEDIA_PATH);
		}
		return this.mediaPath;
	}

	private TokenStorager tokenStorager;

	/**
	 * token存储
	 * 
	 * @param tokenStorager
	 * @return
	 */
	public Weixin4jSettings setTokenStorager(TokenStorager tokenStorager) {
		this.tokenStorager = tokenStorager;
		return this;
	}

	/**
	 * 获取token存储方式 默认为FileTokenStorager
	 * 
	 * @return
	 */
	public TokenStorager getTokenStorager() {
		if (tokenStorager == null) {
			this.tokenStorager = new FileTokenStorager(getTokenPath());
		}
		return this.tokenStorager;
	}

	@Override
	public String toString() {
		return "Weixin4jSettings [account=" + account + ", tokenPath="
				+ getTokenPath() + ", qrcodePath=" + getQrcodePath()
				+ ", mediaPath=" + getMediaPath() + ", tokenStorager="
				+ getTokenStorager() + "]";
	}
}
