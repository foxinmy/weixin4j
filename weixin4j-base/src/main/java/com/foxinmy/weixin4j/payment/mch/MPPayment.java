package com.foxinmy.weixin4j.payment.mch;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.MPPaymentCheckNameType;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 企业付款
 * 
 * @className MPPayment
 * @author jy
 * @date 2015年4月1日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MPPayment implements Serializable {
	
	private static final long serialVersionUID = 3734639674346425312L;
	/**
	 * 商户订单号
	 */
	@XmlElement(name = "partner_trade_no")
	@JSONField(name = "partner_trade_no")
	private String outTradeNo;
	/**
	 * 接收红包的用户的openid
	 */
	@JSONField(name = "openid")
	@XmlElement(name = "openid")
	private String openId;
	/**
	 * 校验用户姓名选项
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.MPPaymentCheckNameType
	 */
	@XmlElement(name = "check_name")
	@JSONField(name = "check_name")
	private MPPaymentCheckNameType checkNameType;
	/**
	 * 收款用户真实姓名。 如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名 可选
	 */
	@XmlElement(name = "re_user_name")
	@JSONField(name = "re_user_name")
	private String userName;
	/**
	 * 企业付款描述信息
	 */
	private String desc;
	/**
	 * 付款金额
	 */
	private String amount;
	/**
	 * 调用接口的机器Ip地址
	 */
	@XmlElement(name = "spbill_create_ip")
	@JSONField(name = "spbill_create_ip")
	private String clientIp;
	
	protected MPPayment() {
		// jaxb required
	}
	
	/**
	 * 企业付款
	 * @param outTradeNo 商户的订单号
	 * @param openId 用户的openid
	 * @param checkNameType 校验用户姓名选项
	 * @param desc 描述
	 * @param amount 金额
	 * @param clientIp 调用接口IP
	 */
	public MPPayment(String outTradeNo, String openId,
			MPPaymentCheckNameType checkNameType, String desc, double amount,
			String clientIp) {
		this.outTradeNo = outTradeNo;
		this.openId = openId;
		this.checkNameType = checkNameType;
		this.desc = desc;
		this.amount = DateUtil.formaFee2Fen(amount);
		this.clientIp = clientIp;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public MPPaymentCheckNameType getCheckNameType() {
		return checkNameType;
	}

	public String getUserName() {
		return userName;
	}

	public String getDesc() {
		return desc;
	}

	public String getAmount() {
		return amount;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "MPPayment [outTradeNo=" + outTradeNo + ", openId=" + openId
				+ ", checkNameType=" + checkNameType + ", userName=" + userName
				+ ", desc=" + desc + ", amount=" + amount + ", clientIp="
				+ clientIp + "]";
	}
}
