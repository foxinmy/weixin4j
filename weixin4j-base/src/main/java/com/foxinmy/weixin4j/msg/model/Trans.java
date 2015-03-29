package com.foxinmy.weixin4j.msg.model;

import com.foxinmy.weixin4j.type.MediaType;
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
public class Trans extends Base implements Responseable {

	private static final long serialVersionUID = -214711609286629729L;

	/**
	 * 指定会话接入的客服账号
	 */
	@XStreamAlias("KfAccount")
	private String kfAccount;

	public Trans() {
		this(null);
	}

	public Trans(String kfAccount) {
		super(MediaType.transfer_customer_service);
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
