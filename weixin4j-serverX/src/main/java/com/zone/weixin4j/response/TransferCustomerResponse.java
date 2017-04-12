package com.zone.weixin4j.response;

/**
 * 消息转移到客服
 * 
 * @className TransferCustomerResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/5/ae230189c9bd07a6b221f48619aeef35.html">转移消息到多客服</a>
 */
public class TransferCustomerResponse implements WeixinResponse {

	/**
	 * 指定会话接入的客服账号
	 */
	private String kfAccount;

	public TransferCustomerResponse(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	public String getKfAccount() {
		return kfAccount;
	}

	@Override
	public String toContent() {
		String content = "";
		if (kfAccount != null && !kfAccount.trim().isEmpty()) {
			content = String
					.format("<TransInfo><KfAccount><![CDATA[%s]]></KfAccount></TransInfo>",
							kfAccount);
		}
		return content;
	}

	@Override
	public String getMsgType() {
		return "transfer_customer_service";
	}
}
