package com.foxinmy.weixin4j.mp.payment;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * JSAPI支付回调时的POST信息
 * 
 * @className JsPayNotify
 * @author jy
 * @date 2014年8月19日
 * @since JDK 1.7
 * @see
 */
public class JsPayNotify extends PayBaseInfo {

	private static final long serialVersionUID = -4659030958445259803L;

	/**
	 * 用户的openid
	 */
	@XStreamAlias("OpenId")
	private String openid;
	/**
	 * 是否关注公众号
	 */
	@XStreamAlias("IsSubscribe")
	private int issubscribe;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getIssubscribe() {
		return issubscribe;
	}

	public void setIssubscribe(int issubscribe) {
		this.issubscribe = issubscribe;
	}

	@JSONField(serialize = false, deserialize = false)
	public boolean getFormatIssubscribe() {
		return issubscribe == 1;
	}

	@Override
	public String toString() {
		return "openid=" + openid + ", issubscribe=" + getFormatIssubscribe()
				+ ", " + super.toString();
	}
}
