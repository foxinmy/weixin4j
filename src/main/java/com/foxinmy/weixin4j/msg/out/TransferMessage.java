package com.foxinmy.weixin4j.msg.out;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 转移消息到多客服端消息
 * 
 * @className TransferMessage
 * @author jy.hu
 * @date 2014年6月28日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%B0%86%E6%B6%88%E6%81%AF%E8%BD%AC%E5%8F%91%E5%88%B0%E5%A4%9A%E5%AE%A2%E6%9C%8D">多客服转移消息</a>
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()
 */
public class TransferMessage extends BaseMessage {

	private static final long serialVersionUID = -5479496746108594940L;

	public TransferMessage(BaseMessage inMessage) {
		super(MessageType.transfer_customer_service,inMessage);
	}
}
