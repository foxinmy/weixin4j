package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.mp.payment.PayPackage;
import com.foxinmy.weixin4j.util.DateUtil;

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
	private String bank_type;
	// 商户号 注册时分配的财付通商户号 非空
	private String partner;
	// 支付币种 默认值是"1" 非空
	private String fee_type;
	// 物流费用 可为空 如果有值,必须保 证 transport_fee + product_fee=total_fee【传进来的参数按照实际金额即可
	// 也就是元为单位】
	private String transport_fee;
	// 商品费用 可为空 商品费用,单位为分。如果有值,必须保 证 transport_fee +
	// product_fee=total_fee;【传进来的参数按照实际金额即可 也就是元为单位】
	private String product_fee;
	// 传入参数字符编码 取值范围:"GBK"、"UTF-8",默认:"GBK" 可为空
	private String input_charset;

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public void setBody(String body) {
		super.setBody(StringUtils.isBlank(body) ? "服务费用" : body);
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public void setNotify_url(String notify_url) {
		super.setNotify_url(notify_url);
	}

	public String getTransport_fee() {
		return transport_fee;
	}

	public void setTransport_fee(double transport_fee) {
		this.transport_fee = DateUtil.format2fee(transport_fee);
	}

	public String getProduct_fee() {
		return product_fee;
	}

	public void setProduct_fee(double product_fee) {
		this.product_fee = DateUtil.format2fee(product_fee);
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public PayPackageV2() {
		this.bank_type = "WX";
		this.fee_type = "1";
		this.input_charset = "UTF-8";
	}

	public PayPackageV2(String out_trade_no, double total_fee,
			String spbill_create_ip) {
		this(null, null, null, out_trade_no, total_fee, null, spbill_create_ip,
				null, null, 0d, 0d, null);
	}

	public PayPackageV2(String body, String out_trade_no, double total_fee,
			String spbill_create_ip) {
		this(body, null, null, out_trade_no, total_fee, null, spbill_create_ip,
				null, null, 0d, 0d, null);
	}

	public PayPackageV2(String body, String partner, String out_trade_no,
			double total_fee, String notify_url, String spbill_create_ip) {
		this(body, null, partner, out_trade_no, total_fee, notify_url,
				spbill_create_ip, null, null, 0d, 0d, null);
	}

	public PayPackageV2(String body, String attach, String partner,
			String out_trade_no, double total_fee, String notify_url,
			String spbill_create_ip, Date time_start, Date time_expire,
			double transport_fee, double product_fee, String goods_tag) {
		super(body, attach, out_trade_no, total_fee, spbill_create_ip,
				time_start, time_expire, goods_tag, notify_url);
		this.bank_type = "WX";
		this.fee_type = "1";
		this.input_charset = "UTF-8";
		this.transport_fee = transport_fee > 0d ? DateUtil
				.format2fee(transport_fee) : null;
		this.product_fee = product_fee > 0 ? DateUtil.format2fee(product_fee)
				: null;
	}

	@Override
	public String toString() {
		return "PayPackageV2 [bank_type=" + bank_type + ", partner=" + partner
				+ ", fee_type=" + fee_type + ", transport_fee=" + transport_fee
				+ ", product_fee=" + product_fee + ", input_charset="
				+ input_charset + ", goods_tag=" + getGoods_tag()
				+ ", getBank_type()=" + getBank_type() + ", getPartner()="
				+ getPartner() + ", getFee_type()=" + getFee_type()
				+ ", getTransport_fee()=" + getTransport_fee()
				+ ", getProduct_fee()=" + getProduct_fee()
				+ ", getGoods_tag()=" + getGoods_tag()
				+ ", getInput_charset()=" + getInput_charset() + "]";
	}
}
