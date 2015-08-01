package com.foxinmy.weixin4j.qy.chat;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.foxinmy.weixin4j.type.AgentType;

/**
 * 企业号聊天服务回调消息
 * 
 * @className WeixinChatMessage
 * @author jy
 * @date 2015年8月1日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeixinChatMessage implements Serializable {

	private static final long serialVersionUID = 6788124387186831643L;

	/**
	 * 企业号CorpID
	 */
	@XmlElement(name = "ToUserName")
	private String corpId;
	/**
	 * 应用类型
	 */
	@XmlElement(name = "AgentType")
	private String agentType;
	/**
	 * 消息数量
	 */
	@XmlElement(name = "ItemCount")
	private int itemCount;
	/**
	 * 会话事件或消息
	 */
	@XmlElement(name = "Item")
	public List<ChatItem> items;
	/**
	 * 回调包ID，uint64类型，企业内唯一
	 */
	@XmlElement(name = "PackageId")
	private String packageId;

	public String getCorpId() {
		return corpId;
	}

	public String getAgentType() {
		return agentType;
	}

	@Transient
	@XmlTransient
	public AgentType getFormatAgentType() {
		return AgentType.valueOf(agentType);
	}

	public int getItemCount() {
		return itemCount;
	}

	public List<ChatItem> getItems() {
		return items;
	}

	public String getPackageId() {
		return packageId;
	}

	@Override
	public String toString() {
		return "WeixinChatMessage [corpId=" + corpId + ", agentType="
				+ agentType + ", itemCount=" + itemCount + ", items=" + items
				+ ", packageId=" + packageId + "]";
	}
}
