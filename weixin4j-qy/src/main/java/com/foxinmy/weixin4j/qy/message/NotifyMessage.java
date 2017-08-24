package com.foxinmy.weixin4j.qy.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.qy.model.IdParameter;
import com.foxinmy.weixin4j.tuple.NotifyTuple;

/**
 * 消息提醒对象
 *
 * @className NotifyMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月22日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.tuple.Text
 * @see com.foxinmy.weixin4j.tuple.Image
 * @see com.foxinmy.weixin4j.tuple.Voice
 * @see com.foxinmy.weixin4j.tuple.Video
 * @see com.foxinmy.weixin4j.tuple.File
 * @see com.foxinmy.weixin4j.tuple.News
 * @see com.foxinmy.weixin4j.tuple.MpNews
 */
public class NotifyMessage implements Serializable {

	private static final long serialVersionUID = 1219589414293000383L;

	/**
	 * 企业应用的id，整型。可在应用的设置页面查看
	 */
	@JSONField(name = "agentid")
	private int agentId;
	/**
	 * 表示是否是保密消息，0表示否，1表示是，默认0
	 */
	private int safe;
	/**
	 * 消息对象
	 */
	@JSONField(serialize = false)
	private NotifyTuple tuple;
	/**
	 * 发送对象
	 */
	@JSONField(serialize = false)
	private IdParameter target;

	public NotifyMessage(int agentid, NotifyTuple tuple) {
		this(agentid, tuple, IdParameter.get(), false);
	}

	public NotifyMessage(int agentId, NotifyTuple tuple, IdParameter target,
			boolean isSafe) {
		this.agentId = agentId;
		this.safe = isSafe ? 1 : 0;
		this.tuple = tuple;
		this.target = target;
	}

	public int getAgentId() {
		return agentId;
	}

	public NotifyTuple getTuple() {
		return tuple;
	}

	public IdParameter getTarget() {
		return target;
	}

	public void setTarget(IdParameter target) {
		this.target = target;
	}

	public int getSafe() {
		return safe;
	}

	public void setSafe(boolean isSafe) {
		this.safe = isSafe ? 1 : 0;
	}

	@Override
	public String toString() {
		return "NotifyMessage [agentId=" + agentId + ", safe=" + safe
				+ ", tuple=" + tuple + ", target=" + target + "]";
	}
}
