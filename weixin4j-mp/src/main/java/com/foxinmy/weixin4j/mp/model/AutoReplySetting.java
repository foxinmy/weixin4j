package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.AutomatchMode;
import com.foxinmy.weixin4j.mp.type.AutoreplyMode;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 自动回复设置
 *
 * @className AutoReplySetting
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年4月15日
 * @since JDK 1.6
 * @see
 */
public class AutoReplySetting implements Serializable {

	private static final long serialVersionUID = 8164017927864497009L;

	/**
	 * 关注后自动回复是否开启
	 */
	@JSONField(name = "is_add_friend_reply_open")
	private boolean isAddFriendReplyOpen;
	/**
	 * 消息自动回复是否开启
	 */
	@JSONField(name = "is_autoreply_open")
	private boolean isAutoreplyOpen;

	/**
	 * 关注后自动回复的信息
	 *
	 * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting.Entry
	 */
	@JSONField(name = "add_friend_autoreply_info")
	private Entry addFriendReply;

	/**
	 * 默认自动回复的信息
	 *
	 * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting.Entry
	 */
	@JSONField(name = "message_default_autoreply_info")
	private Entry defaultReply;

	/**
	 * 关键词自动回复的信息
	 *
	 * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting.Rule
	 */
	private List<Rule> keywordReplyList;

	public boolean isAddFriendReplyOpen() {
		return isAddFriendReplyOpen;
	}

	public void setAddFriendReplyOpen(boolean isAddFriendReplyOpen) {
		this.isAddFriendReplyOpen = isAddFriendReplyOpen;
	}

	public boolean isAutoreplyOpen() {
		return isAutoreplyOpen;
	}

	public void setAutoreplyOpen(boolean isAutoreplyOpen) {
		this.isAutoreplyOpen = isAutoreplyOpen;
	}

	public Entry getAddFriendReply() {
		return addFriendReply;
	}

	public void setAddFriendReply(Entry addFriendReply) {
		this.addFriendReply = addFriendReply;
	}

	public Entry getDefaultReply() {
		return defaultReply;
	}

	public void setDefaultReply(Entry defaultReply) {
		this.defaultReply = defaultReply;
	}

	public List<Rule> getKeywordReplyList() {
		return keywordReplyList;
	}

	public void setKeywordReplyList(List<Rule> keywordReplyList) {
		this.keywordReplyList = keywordReplyList;
	}

	@Override
	public String toString() {
		return "AutoReplySetting [isAddFriendReplyOpen=" + isAddFriendReplyOpen
				+ ", isAutoreplyOpen=" + isAutoreplyOpen + ", addFriendReply="
				+ addFriendReply + ", defaultReply=" + defaultReply
				+ ", keywordReplyList=" + keywordReplyList + "]";
	}

	/**
	 * 关键字规则
	 *
	 * @className Rule
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年4月15日
	 * @since JDK 1.6
	 * @see
	 */
	public static class Rule implements Serializable {

		private static final long serialVersionUID = -7299903545861946025L;
		/**
		 * 规则名称
		 */
		@JSONField(name = "rule_name")
		private String ruleName;
		/**
		 * 创建时间
		 */
		@JSONField(name = "create_time")
		private Date createTime;
		/**
		 * 回复模式
		 *
		 * @see com.foxinmy.weixin4j.mp.type.AutoreplyMode
		 */
		@JSONField(name = "reply_mode")
		private AutoreplyMode replyMode;
		/**
		 * 匹配的关键词列表
		 *
		 * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting.Entry
		 */
		@JSONField(name = "keyword_list_info")
		private List<Entry> keywordList;
		/**
		 * 回复的信息列表
		 *
		 * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting.Entry
		 */
		@JSONField(name = "reply_list_info")
		private List<Entry> replyList;

		public String getRuleName() {
			return ruleName;
		}

		public void setRuleName(String ruleName) {
			this.ruleName = ruleName;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public AutoreplyMode getReplyMode() {
			return replyMode;
		}

		public void setReplyMode(AutoreplyMode replyMode) {
			this.replyMode = replyMode;
		}

		public List<Entry> getKeywordList() {
			return keywordList;
		}

		public void setKeywordList(List<Entry> keywordList) {
			this.keywordList = keywordList;
		}

		public List<Entry> getReplyList() {
			return replyList;
		}

		public void setReplyList(List<Entry> replyList) {
			this.replyList = replyList;
		}

		@Override
		public String toString() {
			return "Rule [ruleName=" + ruleName + ", createTime=" + createTime
					+ ", replyMode=" + replyMode + ", keywordList="
					+ keywordList + ", replyList=" + replyList + "]";
		}
	}

	/**
	 * 数据项
	 *
	 * @className Entry
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年4月15日
	 * @since JDK 1.6
	 * @see
	 */
	public static class Entry implements Serializable {

		private static final long serialVersionUID = -187922224547974025L;
		/**
		 * 自动回复的类型。关注后自动回复和消息自动回复的类型仅支持文本（text）、图片（img）、语音（voice）、视频（video），
		 * 关键词自动回复则还多了图文消息(news)
		 */
		private ButtonType type;
		/**
		 * 对于文本类型，content是文本内容，对于图片、语音、视频类型，content是mediaID,news是article
		 *
		 * @see com.foxinmy.weixin4j.tuple.MpArticle
		 */
		private Serializable content;
		/**
		 * 匹配模式(仅但关键字列表)
		 *
		 * @see com.foxinmy.weixin4j.mp.type.AutomatchMode
		 */
		@JSONField(name = "match_mode")
		private AutomatchMode matchMode;

		public ButtonType getType() {
			return type;
		}

		public void setType(ButtonType type) {
			this.type = type;
		}

		public Serializable getContent() {
			return content;
		}

		public void setContent(Serializable content) {
			this.content = content;
		}

		public AutomatchMode getMatchMode() {
			return matchMode;
		}

		public void setMatchMode(AutomatchMode matchMode) {
			this.matchMode = matchMode;
		}

		@Override
		public String toString() {
			return "Entry [type=" + type + ", content=" + content
					+ ", matchMode=" + matchMode + "]";
		}
	}
}
