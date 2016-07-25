package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.CurrencyType;

/**
 * 商户平台交易结果
 * 
 * @className MerchantTradeResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月21日
 * @since JDK 1.7
 * @see
 */
public class MerchantTradeResult extends MerchantResult {

	private static final long serialVersionUID = 4205906286092873877L;
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
	 * 订单总金额,单位为分
	 */
	@XmlElement(name = "total_fee")
	@JSONField(name = "total_fee")
	private Integer totalFee;
	/**
	 * 应结订单金额,单位为分:应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
	 */
	@XmlElement(name = "settlement_total_fee")
	@JSONField(name = "settlement_total_fee")
	private Integer settlementTotalFee;
	/**
	 * 货币类型,符合 ISO 4217 标准的三位字母代码,默认人民币:CNY
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "fee_type")
	@JSONField(name = "fee_type")
	private String feeType;
	/**
	 * 现金支付金额
	 */
	@XmlElement(name = "cash_fee")
	@JSONField(name = "cash_fee")
	private Integer cashFee;

	public Integer getCashFee() {
		return cashFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatCashFee() {
		return cashFee != null ? cashFee / 100d : 0d;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatTotalFee() {
		return totalFee != null ? totalFee / 100d : 0d;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatFeeType() {
		return feeType != null ? CurrencyType.valueOf(feeType.toUpperCase())
				: null;
	}

	public String getFeeType() {
		return feeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatSettlementTotalFee() {
		return settlementTotalFee != null ? settlementTotalFee / 100d : 0d;
	}

	@Override
	public String toString() {
		return "transactionId=" + transactionId + ", outTradeNo=" + outTradeNo
				+ ", totalFee=" + totalFee + ", cashFee=" + cashFee
				+ ", feeType=" + feeType + ", settlementTotalFee="
				+ settlementTotalFee + ", " + super.toString();
	}
}
