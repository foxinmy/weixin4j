package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 普通消息基类
 * <p>
 * <font color="red">回复图片等多媒体消息时需要预先上传多媒体文件到微信服务器,
 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试</font>
 * </p>
 * 
 * @className BaseMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 */
public class BaseMsg implements Serializable {

	private static final long serialVersionUID = 7761192742840031607L;

	@XStreamAlias("ToUserName")
	private String toUserName; // 开发者微信号
	@XStreamAlias("FromUserName")
	private String fromUserName; // 发送方帐号（一个OpenID）
	@XStreamAlias("CreateTime")
	private long createTime = System.currentTimeMillis(); // 消息创建时间 （整型）

	public BaseMsg() {

	}

	public BaseMsg(String toUserName, String fromUserName) {
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
