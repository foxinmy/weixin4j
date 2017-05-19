package com.foxinmy.weixin4j.mp.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文章评论
 *
 * @className ArticleComment
 * @author jinyu
 * @date May 19, 2017
 * @since JDK 1.6
 * @see
 */
public class ArticleComment extends Comment {

	private static final long serialVersionUID = -8506024679132313314L;
	@JSONField(name = "user_comment_id")
	private String id;// 用户评论id
	private String openid;// openid
	@JSONField(name = "comment_type")
	private int type;// 是否精选评论，0为即非精选，1为true，即精选
	private Comment reply; // 评论回复

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getType() {
		return type;
	}

	@JSONField(serialize = false)
	public ArticleCommentType getFormatType() {
		if (type == 0) {
			return ArticleCommentType.GENERAL;
		} else if (type == 1) {
			return ArticleCommentType.MARKELECT;
		} else {
			return null;
		}
	}

	public void setType(int type) {
		this.type = type;
	}

	public Comment getReply() {
		return reply;
	}

	public void setReply(Comment reply) {
		this.reply = reply;
	}

	public enum ArticleCommentType {
		GENERAL, // 普通评论
		MARKELECT // 精选评论
	}
}
