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
 * @see <a href="http://dkf.qq.com/document-4_1.html">将消息转发到多客服</a>
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse
 * @see com.foxinmy.weixin4j.mp.response.BaseResponse#toXml()
 */
@XStreamAlias("xml")
public class TransferResponse extends BaseResponse {

	private static final long serialVersionUID = -5479496746108594940L;

	public TransferResponse(BaseMessage inMessage) {
		super(ResponseType.transfer_customer_service, inMessage);
	}

	/**
	 * 需如果指定的客服没有接入能力(不在线、没有开启自动接入或者自动接入已满) 该用户会一直等待指定客服有接入能力后才会被接入 而不会被其他客服接待
	 * 
	 * @param inMessage
	 * @param kfAccount
	 *            转移客服的账号
	 */
	public TransferResponse(BaseMessage inMessage, String kfAccount) {
		super(ResponseType.transfer_customer_service, inMessage);
		this.transInfo = new TransInfo(kfAccount);
	}

	@XStreamAlias("TransInfo")
	private TransInfo transInfo;

	public String getTransAccount() {
		if (transInfo != null) {
			return transInfo.getKfAccount();
		}
		return null;
	}

	public void setTransAccount(String kfAccount) {
		this.transInfo = new TransInfo(kfAccount);
	}

	private static class TransInfo {
		// 指定会话接入的客服账号
		@XStreamAlias("KfAccount")
		private String kfAccount;

		public TransInfo(String kfAccount) {
			this.kfAccount = kfAccount;
		}

		public String getKfAccount() {
			return kfAccount;
		}

		@Override
		public String toString() {
			return "TransInfo [kfAccount=" + kfAccount + "]";
		}
	}
}