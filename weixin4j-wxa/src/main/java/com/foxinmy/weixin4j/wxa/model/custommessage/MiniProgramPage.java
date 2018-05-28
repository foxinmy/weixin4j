package com.foxinmy.weixin4j.wxa.model.custommessage;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.tuple.NotifyTuple;

/**
 * 小程序卡片。
 *
 * @since 1.8
 */
public class MiniProgramPage implements NotifyTuple {

	private static final long serialVersionUID = 2018052901L;

	private String title;
	private String pagePath;
	private String thumbMediaId;

	public MiniProgramPage() {
	}

	public MiniProgramPage(
		String title,
		String pagePath,
		String thumbMediaId
	) {
		this.title = title;
		this.pagePath = pagePath;
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	@JSONField(serialize = false)
	public String getMessageType() {
		return "miniprogrampage";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JSONField(name = "pagepath")
	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	@JSONField(name = "thumb_media_id")
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

}