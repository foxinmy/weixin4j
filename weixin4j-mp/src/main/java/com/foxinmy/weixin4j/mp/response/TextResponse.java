package com.foxinmy.weixin4j.mp.response;

import com.foxinmy.weixin4j.mp.type.ResponseType;
import com.foxinmy.weixin4j.msg.BaseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复文本消息
 * 
 * @className TextResponse
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#.E6.96.87.E6.9C.AC.E6.B6.88.E6.81.AF">接收文本消息</a>
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E6.96.87.E6.9C.AC.E6.B6.88.E6.81.AF">回复文本消息</a>
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse#toXml()
 */
@XStreamAlias("xml")
public class TextResponse extends BaseResponse {

	private static final long serialVersionUID = -7018053906644190260L;

	public TextResponse() {
		super(ResponseType.text);
	}

	public TextResponse(String content, BaseMessage inMessage) {
		super(ResponseType.text, inMessage);
		this.content = content;
	}

	@XStreamAlias("Content")
	private String content; // 消息内容

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[TextResponse ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,content=").append(content);
		sb.append(" ,createTime=").append(super.getCreateTime()).append("]");
		return sb.toString();
	}
}
