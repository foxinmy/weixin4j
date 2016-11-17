package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.CredentialType;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.CustomsCity;

/**
 * 报关对象
 * 
 * @className CustomsOrder
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月27日
 * @since JDK 1.6
 * @see
 */
public class CustomsOrder extends MerchantResult {

	private static final long serialVersionUID = 799510373861612386L;
	/**
	 * 微信支付订单号
	 */
	@XmlElement(name = "transaction_id")
	@JSONField(name = "transaction_id")
	private String transactionId;
	/**
	 * 商户订单号
	 */
	@XmlElement(name = "out_trade_no")
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	/**
	 * 商户子订单号，如有拆单则必传
	 */
	@XmlElement(name = "sub_order_no")
	@JSONField(name = "sub_order_no")
	private String subOrderNo;
	/**
	 * 货币类型,符合 ISO 4217 标准的三位字母代码,默认人民币:CNY
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "fee_type")
	@JSONField(name = "fee_type")
	private CurrencyType feeType;
	/**
	 * 子订单金额，以分为单位，不能超过原订单金额，order_fee=transport_fee+product_fee（应付金额=物流费+商品价格），
	 * 如有拆单则必传。
	 */
	@XmlElement(name = "order_fee")
	@JSONField(name = "order_fee")
	private String orderFee;
	/**
	 * 物流费用，以分为单位，如有拆单则必传。
	 */
	@XmlElement(name = "transport_fee")
	@JSONField(name = "transport_fee")
	private String transportFee;
	/**
	 * 商品费用，以分为单位，如有拆单则必传。
	 */
	@XmlElement(name = "product_fee")
	@JSONField(name = "product_fee")
	private String productFee;
	/**
	 * 关税，以分为单位
	 */
	@XmlElement(name = "duty")
	@JSONField(name = "duty")
	private String dutyFee;
	/**
	 * 海关
	 */
	@XmlElement(name = "customs")
	@JSONField(name = "customs")
	private CustomsCity customsCity;
	/**
	 * 商户在海关登记的备案号，customsCity非NO，此参数必填
	 */
	@XmlElement(name = "mch_customs_no")
	@JSONField(name = "mch_customs_no")
	private String customsNo;
	/**
	 * 证件类型：暂只支持身份证，该参数是指用户信息，商户若有用户信息，可上送，系统将以商户上传的数据为准，进行海关通关报备
	 */
	@XmlElement(name = "cert_type")
	@JSONField(name = "cert_type")
	private CredentialType credentialType;
	/**
	 * 证件号码：身份证号，该参数是指用户信息，商户若有用户信息，可上送，系统将以商户上传的数据为准，进行海关通关报备；
	 */
	@XmlElement(name = "cert_id")
	@JSONField(name = "cert_id")
	private String credentialId;
	/**
	 * 用户姓名，该参数是指用户信息，商户若有用户信息，可上送，系统将以商户上传的数据为准，进行海关通关报备；
	 */
	@XmlElement(name = "name")
	@JSONField(name = "name")
	private String uname;

	public CustomsOrder(String transactionId, String outTradeNo) {
		this.transactionId = transactionId;
		this.outTradeNo = outTradeNo;
		this.customsCity = CustomsCity.NO;
	}

	public String getSubOrderNo() {
		return subOrderNo;
	}

	public void setSubOrderNo(String subOrderNo) {
		this.subOrderNo = subOrderNo;
	}

	public CurrencyType getFeeType() {
		return feeType;
	}

	public void setFeeType(CurrencyType feeType) {
		this.feeType = feeType;
	}

	public String getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}

	public String getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(String transportFee) {
		this.transportFee = transportFee;
	}

	public String getProductFee() {
		return productFee;
	}

	public void setProductFee(String productFee) {
		this.productFee = productFee;
	}

	public String getDutyFee() {
		return dutyFee;
	}

	public void setDutyFee(String dutyFee) {
		this.dutyFee = dutyFee;
	}

	public CustomsCity getCustomsCity() {
		return customsCity;
	}

	public void setCustomsCity(CustomsCity customsCity) {
		this.customsCity = customsCity;
	}

	public String getCustomsNo() {
		return customsNo;
	}

	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
	}

	public CredentialType getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(CredentialType credentialType) {
		this.credentialType = credentialType;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	@Override
	public String toString() {
		return "CustomsOrder [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", subOrderNo=" + subOrderNo + ", feeType="
				+ feeType + ", orderFee=" + orderFee + ", transportFee="
				+ transportFee + ", productFee=" + productFee + ", dutyFee="
				+ dutyFee + ", customsCity=" + customsCity + ", customsNo="
				+ customsNo + ", credentialType=" + credentialType
				+ ", credentialId=" + credentialId + ", uname=" + uname + ", "
				+ super.toString() + "]";
	}
}
