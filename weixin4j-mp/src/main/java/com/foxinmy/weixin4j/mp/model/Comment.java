package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @className Comment
 * @author jinyu(foxinmy@gmail.com)
 * @date 2017年5月19日
 * @since JDK 1.6
 * @see
 */
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	private String content;// 评论内容
	@JSONField(name = "create_time")
	private long createTime;// 评论时间

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreateTime() {
		return createTime;
	}

	@JSONField(serialize = false)
	public Date getFormatCreateTime() {
		return new Date(createTime * 1000l);
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "content=" + content + ", createTime=" + createTime;
	}
}