package com.foxinmy.weixin4j.mp.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.http.weixin.ApiResult;

/**
 * openid转结果
 * 
 * @className ChangeOpenidResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2023年12月3日
 * @since JDK 1.8
 * @see <a href=
 *      "https://kf.qq.com/faq/1901177NrqMr190117nqYJze.html">openid转换</a>
 */
public class ChangeOpenidResult extends ApiResult {

	private static final long serialVersionUID = 9051214458161068387L;
	/**
	 * 旧openid
	 */
	@JSONField(name = "ori_openid")
	private String oldOpenid;
	/**
	 * 新openid，可能为空
	 */
	@JSONField(name = "new_openid")
	private String newOpenid;
	/**
	 * 返回消息
	 */
	@JSONField(name = "err_msg")
	private String message;

	public String getOldOpenid() {
		return oldOpenid;
	}

	public void setOldOpenid(String oldOpenid) {
		this.oldOpenid = oldOpenid;
	}

	public String getNewOpenid() {
		return newOpenid;
	}

	public void setNewOpenid(String newOpenid) {
		this.newOpenid = newOpenid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isChanged(){
		return "ok".equalsIgnoreCase(message);
	}

	@Override
	public String toString() {
		return "ChangeOpenidResult [oldOpenid=" + oldOpenid + ", newOpenid=" + newOpenid + ", message=" + message + ", " + super.toString() + "]";
	}
}
