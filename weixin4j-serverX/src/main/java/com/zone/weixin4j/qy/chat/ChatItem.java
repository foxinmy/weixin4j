package com.zone.weixin4j.qy.chat;

import com.zone.weixin4j.type.MessageType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 会话事件或消息
 * 
 * @className ChatItem
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月1日
 * @since JDK 1.6
 * @see
 */
public class ChatItem implements Serializable {

	private static final long serialVersionUID = -5921235260175596270L;

	private final String LIST_SEPARATOR = "\\|";

	/**
	 * 操作成员UserID
	 */
	@XmlElement(name = "FromUserName")
	private String operatorId;
	/**
	 * 消息创建时间（整型）
	 */
	@XmlElement(name = "CreateTime")
	private long createTime;
	/**
	 * 消息类型
	 * 
	 */
	@XmlElement(name = "MsgType")
	private String msgType;
	/**
	 * 事件类型
	 */
	@XmlElement(name = "Event")
	private String eventType;
	/**
	 * 会话id
	 */
	@XmlElement(name = "ChatId")
	private String chatId;
	/**
	 * 会话标题
	 */
	@XmlElement(name = "Name")
	private String chatName;
	/**
	 * 管理员userid
	 */
	@XmlElement(name = "Owner")
	private String ownerId;
	/**
	 * 会话成员列表
	 */
	@XmlElement(name = "UserList")
	private String members;
	/**
	 * 会话新增成员列表
	 */
	@XmlElement(name = "AddUserList")
	private String addMembers;
	/**
	 * 会话删除成员列表
	 */
	@XmlElement(name = "DelUserList")
	private String deleteMembers;
	/**
	 * 消息ID 64位整型
	 */
	@XmlElement(name = "MsgId")
	private long msgId;
	/**
	 * 接收人
	 */
	@XmlElement(name = "Receiver")
	private ChatReceiver receiver;
	/**
	 * 文本消息内容
	 */
	@XmlElement(name = "Content")
	private String content;
	/**
	 * 图片消息链接
	 */
	@XmlElement(name = "PicUrl")
	private String picUrl;
	/**
	 * 链接消息标题
	 */
	@XmlElement(name = "Title")
	private String title;
	/**
	 * 链接消息描述
	 */
	@XmlElement(name = "Description")
	private String description;
	/**
	 * 链接消息链接
	 */
	@XmlElement(name = "Url")
	private String url;
	/**
	 * 图片、语音、文件消息的媒体id，可以调用获取媒体文件接口拉取数据
	 */
	@XmlElement(name = "MediaId")
	private String mediaId;

	public String getOperatorId() {
		return operatorId;
	}

	public long getCreateTime() {
		return createTime;
	}

	@XmlTransient
	public Date getFormatCreateTime() {
		return createTime > 0l ? new Date(createTime * 1000l) : null;
	}

	public String getMsgType() {
		return msgType;
	}

	@XmlTransient
	public MessageType getFormatMsgType() {
		return msgType != null ? MessageType.valueOf(msgType) : null;
	}

	public String getEventType() {
		return eventType;
	}

	@XmlTransient
	public ChatEventType getFormatEventType() {
		return eventType != null ? ChatEventType.valueOf(eventType) : null;
	}

	public String getChatId() {
		return chatId;
	}

	public String getChatName() {
		return chatName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getMembers() {
		return members;
	}

	@XmlTransient
	public List<String> getFormatMembers() {
		return members != null ? Arrays.asList(members.split(LIST_SEPARATOR))
				: null;
	}

	public String getAddMembers() {
		return addMembers;
	}

	@XmlTransient
	public List<String> getFormatAddMembers() {
		return addMembers != null ? Arrays.asList(addMembers
				.split(LIST_SEPARATOR)) : null;
	}

	public String getDeleteMembers() {
		return deleteMembers;
	}

	@XmlTransient
	public List<String> getFormatDeleteMembers() {
		return deleteMembers != null ? Arrays.asList(deleteMembers
				.split(LIST_SEPARATOR)) : null;
	}

	public long getMsgId() {
		return msgId;
	}

	public ChatReceiver getReceiver() {
		return receiver;
	}

	public String getContent() {
		return content;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "ChatItem [operatorId=" + operatorId + ", createTime="
				+ createTime + ", msgType=" + msgType + ", eventType="
				+ eventType + ", chatId=" + chatId + ", chatName=" + chatName
				+ ", ownerId=" + ownerId + ", members=" + members
				+ ", addMembers=" + addMembers + ", deleteMembers="
				+ deleteMembers + ", msgId=" + msgId + ", receiver=" + receiver
				+ ", content=" + content + ", picUrl=" + picUrl + ", title="
				+ title + ", description=" + description + ", url=" + url
				+ ", mediaId=" + mediaId + "]";
	}
}
