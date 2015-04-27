package com.foxinmy.weixin4j.tuple;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 转移到客服
 * <p>
 * <font color="red">可用于「被动消息」</font>
 * </p>
 * 
 * @className Trans
 * @author jy
 * @date 2014年11月21日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("TransInfo")
public class Trans implements ResponseTuple {

	private static final long serialVersionUID = -214711609286629729L;

	@Override
	public String getMessageType() {
		return "transfer_customer_service";
	}

	/**
	 * 指定会话接入的客服账号
	 */
	@XStreamAlias("KfAccount")
	private String kfAccount;

	public Trans() {
		this(null);
	}

	public Trans(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	public String getKfAccount() {
		return kfAccount;
	}

	@Override
	public String toString() {
		return "Trans [kfAccount=" + kfAccount + "]";
	}

}
