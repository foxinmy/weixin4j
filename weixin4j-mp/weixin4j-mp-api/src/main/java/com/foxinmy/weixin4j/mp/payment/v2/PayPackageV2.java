package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.payment.PayPackage;
import com.foxinmy.weixin4j.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * V2支付的订单详情
 * 
 * @className PayPackageV2
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see
 */
public class PayPackageV2 extends PayPackage {

	private static final long serialVersionUID = 5557542103637795834L;

	// 银行通道类型 固定为"WX" 非空
	@XStreamAlias("bank_type")
	@JSONField(name = "bank_type")
	private String bankType;
	// 商户号 注册时分配的财付通商户号 非空
	private String partner;
	// 支付币种 默认值是"1" 非空
	@XStreamAlias("fee_type")
	@JSONField(name = "fee_type")
	private String feeType;
	// 物流费用 可为空 如果有值,必须保 证 transport_fee + product_fee=total_fee【传进来的参数按照实际金额即可
	// 也就是元为单位】
	@XStreamAlias("transport_fee")
	@JSONField(name = "transport_fee")
	private String transportFee;
	// 商品费用 可为空 商品费用,单位为分。如果有值,必须保 证 transport_fee +
	// product_fee=total_fee;【传进来的参数按照实际金额即可 也就是元为单位】
	@XStreamAlias("product_fee")
	@JSONField(name = "product_fee")
	private String productFee;
	// 传入参数字符编码 取值范围:"GBK"、"UTF-8",默认:"GBK" 可为空
	@XStreamAlias("input_charset")
	@JSONField(name = "input_charset")
	private String inputCharset;

	public String getBankType() {
		return bankType;
	}

	public void setBank_type(String bankType) {
		this.bankType = bankType;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(double transportFee) {
		this.transportFee = DateUtil.formaFee2Fen(transportFee);
	}

	public String getProductFee() {
		return productFee;
	}

	public void setProductFee(double productFee) {
		this.productFee = DateUtil.formaFee2Fen(productFee);
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInput_charset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public PayPackageV2() {
		this.bankType = "WX";
		this.feeType = "1";
		this.inputCharset = "UTF-8";
	}

	public PayPackageV2(String outTradeNo, double totalFee,
			String spbillCreateIp) {
		this(null, null, null, outTradeNo, totalFee, null, spbillCreateIp,
				null, null, 0d, 0d, null);
	}

	public PayPackageV2(String body, String outTradeNo, double totalFee,
			String notifyUrl, String spbillCreateIp) {
		this(body, null, null, outTradeNo, totalFee, notifyUrl, spbillCreateIp,
				null, null, 0d, 0d, null);
	}

	public PayPackageV2(String body, String partner, String outTradeNo,
			double totalFee, String notifyUrl, String spbillCreateIp) {
		this(body, null, partner, outTradeNo, totalFee, notifyUrl,
				spbillCreateIp, null, null, 0d, 0d, null);
	}

	public PayPackageV2(String body, String attach, String partner,
			String outTradeNo, double totalFee, String notifyUrl,
			String spbillCreateIp, Date timeStart, Date timeExpire,
			double transportFee, double productFee, String goodsTag) {
		super(body, attach, outTradeNo, totalFee, spbillCreateIp, timeStart,
				timeExpire, goodsTag, notifyUrl);
		this.bankType = "WX";
		this.feeType = "1";
		this.inputCharset = "UTF-8";
		this.transportFee = transportFee > 0d ? DateUtil
				.formaFee2Fen(transportFee) : null;
		this.productFee = productFee > 0 ? DateUtil.formaFee2Fen(productFee)
				: null;
	}

	@Override
	public String toString() {
		return "PayPackageV2 [bankType=" + bankType + ", partner=" + partner
				+ ", feeType=" + feeType + ", transportFee=" + transportFee
				+ ", productFee=" + productFee + ", inputCharset="
				+ inputCharset + ", " + super.toString() + "]";
	}
}
