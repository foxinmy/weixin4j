package com.foxinmy.weixin4j.mp.message;

import java.io.Serializable;

import com.foxinmy.weixin4j.tuple.NotifyTuple;

/**
 * 客服消息(48小时内不限制发送次数)
 * 
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.tuple.Text
 * @see com.foxinmy.weixin4j.tuple.Image
 * @see com.foxinmy.weixin4j.tuple.Voice
 * @see com.foxinmy.weixin4j.tuple.Video
 * @see com.foxinmy.weixin4j.tuple.Music
 * @see com.foxinmy.weixin4j.tuple.News
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF">发送客服消息</a>
 */
public class NotifyMessage implements Serializable {

	private static final long serialVersionUID = 7190233634431087729L;

	/**
	 * 用户的openid
	 */
	private String touser;
	/**
	 * 消息对象
	 */
	private NotifyTuple tuple;

	public NotifyMessage(String touser, NotifyTuple tuple) {
		this.touser = touser;
		this.tuple = tuple;
	}

	public String getTouser() {
		return touser;
	}

	public NotifyTuple getTuple() {
		return tuple;
	}

	@Override
	public String toString() {
		return "NotifyMessage [touser=" + touser + ", tuple=" + tuple + "]";
	}
}