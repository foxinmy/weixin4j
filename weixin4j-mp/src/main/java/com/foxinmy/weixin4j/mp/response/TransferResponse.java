package com.foxinmy.weixin4j.mp.response;

import com.foxinmy.weixin4j.mp.type.ResponseType;
import com.foxinmy.weixin4j.msg.BaseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 转移消息到多客服端消息
 * 
 * @className TransferResponse
 * @author jy.hu
 * @date 2014年6月28日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%B0%86%E6%B6%88%E6%81%AF%E8%BD%AC%E5%8F%91%E5%88%B0%E5%A4%9A%E5%AE%A2%E6%9C%8D">多客服转移消息</a>
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse#toXml()
 */
@XStreamAlias("xml")
public class TransferResponse extends BaseResponse {

	private static final long serialVersionUID = -5479496746108594940L;

	public TransferResponse(BaseMessage inMessage) {
		super(ResponseType.transfer_customer_service, inMessage);
	}
}
