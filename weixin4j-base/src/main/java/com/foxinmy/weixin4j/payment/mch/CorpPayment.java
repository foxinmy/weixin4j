package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.mch.CorpPaymentCheckNameType;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 企业付款
 * 
 * @className CorpPayment
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年4月1日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CorpPayment extends MerchantResult {

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
	 * @see com.foxinmy.weixin4j.type.mch.CorpPaymentCheckNameType.type.MPPaymentCheckNameType
	 */
	@XmlElement(name = "check_name")
	@JSONField(name = "check_name")
	private CorpPaymentCheckNameType checkNameType;
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
	 * 付款金额 单位分
	 */
	private int amount;
	/**
	 * 调用接口的机器Ip地址
	 */
	@XmlElement(name = "spbill_create_ip")
	@JSONField(name = "spbill_create_ip")
	private String clientIp;

	protected CorpPayment() {
		// jaxb required
	}

	/**
	 * 企业付款
	 * 
	 * @param outTradeNo
	 *            商户的订单号
	 * @param openId
	 *            用户的openid
	 * @param checkNameType
	 *            校验用户姓名选项
	 * @param desc
	 *            描述
	 * @param amount
	 *            金额 单位元
	 * @param clientIp
	 *            调用接口IP
	 */
	public CorpPayment(String outTradeNo, String openId,
			CorpPaymentCheckNameType checkNameType, String desc, double amount,
			String clientIp) {
		this.outTradeNo = outTradeNo;
		this.openId = openId;
		this.checkNameType = checkNameType;
		this.desc = desc;
		this.amount = DateUtil.formatYuan2Fen(amount);
		this.clientIp = clientIp;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public CorpPaymentCheckNameType getCheckNameType() {
		return checkNameType;
	}

	public String getUserName() {
		return userName;
	}

	public String getDesc() {
		return desc;
	}

	public int getAmount() {
		return amount;
	}
	
	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatAmount() {
		return amount / 100d;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CorpPayment [outTradeNo=" + outTradeNo + ", openId=" + openId
				+ ", checkNameType=" + checkNameType + ", userName=" + userName
				+ ", desc=" + desc + ", amount=" + amount + ", clientIp="
				+ clientIp + ", " + super.toString() + "]";
	}
}