package com.foxinmy.weixin4j.payment.mch;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 结算资金
 * 
 * @className Settlement
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月26日
 * @since JDK 1.6
 * @see
 */
public class SettlementRecord extends MerchantResult {

	private static final long serialVersionUID = 7952659545609519979L;

	/**
	 * 付款批次号
	 */
	@XmlElement(name = "fbatchno")
	@JSONField(name = "fbatchno")
	private String batchNo;
	/**
	 * 结算日期
	 */
	@XmlElement(name = "date_settlement")
	@JSONField(name = "date_settlement")
	private String settleDate;
	/**
	 * 交易开始日期
	 */
	@XmlElement(name = "date_start")
	@JSONField(name = "date_start")
	private String startDate;
	/**
	 * 交易结束日期
	 */
	@XmlElement(name = "date_end")
	@JSONField(name = "date_end")
	private String endDate;
	/**
	 * 划账金额:外币标价，外币最小单位
	 */
	@XmlElement(name = "transaction_id")
	@JSONField(name = "transaction_id")
	private int settleFee;
	/**
	 * 未划账金额:外币标价，外币最小单位
	 */
	@XmlElement(name = "unsettlement_fee")
	@JSONField(name = "unsettlement_fee")
	private int unSettleFee;
	/**
	 * 结算币种
	 */
	@XmlElement(name = "settlementfee_type")
	@JSONField(name = "settlementfee_type")
	private String settleFeeType;
	/**
	 * 支付金额:外币标价，外币最小单位
	 */
	@XmlElement(name = "pay_fee")
	@JSONField(name = "pay_fee")
	private int payFee;
	/**
	 * 退款金额:外币标价，外币最小单位
	 */
	@XmlElement(name = "refund_fee")
	@JSONField(name = "refund_fee")
	private int refundFee;
	/**
	 * 支付净额:外币标价，外币最小单位
	 */
	@XmlElement(name = "pay_net_fee")
	@JSONField(name = "pay_net_fee")
	private int payNetFee;
	/**
	 * 手续费金额:外币标价，外币最小单位
	 */
	@XmlElement(name = "poundage_fee")
	@JSONField(name = "poundage_fee")
	private int poundageFee;

	protected SettlementRecord() {
		// jaxb required
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSettleDate() {
		return settleDate;
	}

	@JSONField(serialize = false)
	public Date getFormatSettleDate() {
		return DateUtil.parse2yyyyMMddHHmmss(settleDate);
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getStartDate() {
		return startDate;
	}

	@JSONField(serialize = false)
	public Date getFormatStartDate() {
		return DateUtil.parse2yyyyMMddHHmmss(startDate);
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	@JSONField(serialize = false)
	public Date getFormatEndDate() {
		return DateUtil.parse2yyyyMMddHHmmss(settleDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getSettleFee() {
		return settleFee;
	}

	/**
	 * <font color="red">最小单位除100得到的值</font>
	 * 
	 * @return /100
	 */
	@JSONField(serialize = false)
	public double getFormatSettleFee() {
		return settleFee / 100d;
	}

	public void setSettleFee(int settleFee) {
		this.settleFee = settleFee;
	}

	public int getUnSettleFee() {
		return unSettleFee;
	}

	/**
	 * <font color="red">最小单位除100得到的值</font>
	 * 
	 * @return /100
	 */
	@JSONField(serialize = false)
	public double getFormatUnSettleFee() {
		return unSettleFee / 100d;
	}

	public void setUnSettleFee(int unSettleFee) {
		this.unSettleFee = unSettleFee;
	}

	public String getSettleFeeType() {
		return settleFeeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatSettleFeeType() {
		return CurrencyType.valueOf(settleFeeType.toUpperCase());
	}

	public void setSettleFeeType(String settleFeeType) {
		this.settleFeeType = settleFeeType;
	}

	public int getPayFee() {
		return payFee;
	}

	/**
	 * <font color="red">最小单位除100得到的值</font>
	 * 
	 * @return /100
	 */
	@JSONField(serialize = false)
	public double getFormatPayFee() {
		return payFee / 100d;
	}

	public void setPayFee(int payFee) {
		this.payFee = payFee;
	}

	public int getRefundFee() {
		return refundFee;
	}

	/**
	 * <font color="red">最小单位除100得到的值</font>
	 * 
	 * @return /100
	 */
	@JSONField(serialize = false)
	public double getFormatRefundFee() {
		return refundFee / 100d;
	}

	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}

	public int getPayNetFee() {
		return payNetFee;
	}

	public void setPayNetFee(int payNetFee) {
		this.payNetFee = payNetFee;
	}

	/**
	 * <font color="red">最小单位除100得到的值</font>
	 * 
	 * @return /100
	 */
	@JSONField(serialize = false)
	public double getFormatPayNetFee() {
		return payNetFee / 100d;
	}

	public int getPoundageFee() {
		return poundageFee;
	}

	/**
	 * <font color="red">最小单位除100得到的值</font>
	 * 
	 * @return /100
	 */
	@JSONField(serialize = false)
	public double getFormatPoundageFee() {
		return poundageFee / 100d;
	}

	public void setPoundageFee(int poundageFee) {
		this.poundageFee = poundageFee;
	}

	@Override
	public String toString() {
		return "SettlementRecord [batchNo=" + batchNo + ", settleDate="
				+ settleDate + ", startDate=" + startDate + ", endDate="
				+ endDate + ", settleFee=" + settleFee + ", unSettleFee="
				+ unSettleFee + ", settleFeeType=" + settleFeeType
				+ ", payFee=" + payFee + ", refundFee=" + refundFee
				+ ", payNetFee=" + payNetFee + ", poundageFee=" + poundageFee
				+ ", " + super.toString() + "]";
	}
}
