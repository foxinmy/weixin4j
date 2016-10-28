package com.foxinmy.weixin4j.mp.oldpayment;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.TradeState;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * V2订单信息
 * 
 * @className OrderV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月2日
 * @since JDK 1.6
 * @see
 */
public class OrderV2 extends ApiResultV2 {

	private static final long serialVersionUID = 4543552984506609920L;

	/**
	 * 是订单状态,0 为成功,其他为失败;
	 */
	@JSONField(name = "trade_state")
	private int tradeState;
	/**
	 * 是交易模式,1 为即时到帐,其他保留;
	 */
	@JSONField(name = "trade_mode")
	private int tradeMode;
	/**
	 * 是银行类型;
	 */
	@JSONField(name = "bank_type")
	private String bankType;
	/**
	 * 是银行订单号;
	 */
	@JSONField(name = "bank_billno")
	private String bankBillno;
	/**
	 * 是总金额,单位为分;
	 */
	@JSONField(name = "total_fee")
	private int totalFee;
	/**
	 * 是币种,1 为人民币;
	 */
	@JSONField(name = "fee_type")
	private int feeType;
	/**
	 * 是财付通订单号;
	 */
	@JSONField(name = "transaction_id")
	private String transactionId;
	/**
	 * 是第三方订单号;
	 */
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	/**
	 * 表明是否分账,false 为无分账,true 为有分账;
	 */
	@JSONField(name = "is_split")
	private boolean isSplit;
	/**
	 * 表明是否退款,false 为无退款,ture 为退款;
	 */
	@JSONField(name = "is_refund")
	private boolean isRefund;
	/**
	 * attach 是商户数据包,即生成订单package 时商户填入的 attach;
	 */
	private String attach;
	/**
	 * 支付完成时间;
	 */
	@JSONField(name = "time_end")
	private String timeEnd;
	/**
	 * 物流费用,单位为分;
	 */
	@JSONField(name = "transport_fee")
	private int transportFee;
	/**
	 * 物品费用,单位为分;
	 */
	@JSONField(name = "product_fee")
	private int productFee;
	/**
	 * 折扣价格,单位为分;
	 */
	private int discount;
	/**
	 * 换算成人民币之后的总金额,单位为分,一般看 total_fee 即可。
	 */
	@JSONField(name = "rmb_total_fee")
	private Integer rmbTotalFee;

	public int getTradeState() {
		return tradeState;
	}

	@JSONField(serialize = false)
	public TradeState getFormatTradeState() {
		return tradeState == 0 ? TradeState.SUCCESS : null;
	}

	public void setTradeState(int tradeState) {
		this.tradeState = tradeState;
	}

	public int getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(int tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankBillno() {
		return bankBillno;
	}

	public void setBankBillno(String bankBillno) {
		this.bankBillno = bankBillno;
	}

	public int getTotalFee() {
		return totalFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatTotalFee() {
		return totalFee / 100d;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getFeeType() {
		return feeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatFeeType() {
		return feeType == 1 ? CurrencyType.CNY : null;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public boolean isSplit() {
		return isSplit;
	}

	public void setSplit(boolean isSplit) {
		this.isSplit = isSplit;
	}

	public boolean isRefund() {
		return isRefund;
	}

	public void setRefund(boolean isRefund) {
		this.isRefund = isRefund;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	@JSONField(serialize = false)
	public Date getFormatTimeEnd() {
		return timeEnd != null ? DateUtil.parse2yyyyMMddHHmmss(timeEnd) : null;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getTransportFee() {
		return transportFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatTransportFee() {
		return transportFee / 100d;
	}

	public void setTransportFee(int transportFee) {
		this.transportFee = transportFee;
	}

	public int getProductFee() {
		return productFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatProductFee() {
		return productFee / 100d;
	}

	public void setProductFee(int productFee) {
		this.productFee = productFee;
	}

	public int getDiscount() {
		return discount;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatDiscount() {
		return discount / 100d;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Integer getRmbTotalFee() {
		return rmbTotalFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatRmbTotalFee() {
		return rmbTotalFee != null ? rmbTotalFee.doubleValue() / 100d : 0d;
	}

	public void setRmbTotalFee(int rmbTotalFee) {
		this.rmbTotalFee = rmbTotalFee;
	}

	@Override
	public String toString() {
		return "Order [tradeState=" + tradeState + ", tradeMode=" + tradeMode
				+ ", bankType=" + bankType + ", bankBillno=" + bankBillno
				+ ", totalFee=" + totalFee + ", feeType=" + feeType
				+ ", transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", isSplit=" + isSplit + ", isRefund="
				+ isRefund + ", attach=" + attach + ", timeEnd=" + timeEnd
				+ ", transportFee=" + transportFee + ", productFee="
				+ productFee + ", discount=" + discount + ", rmbTotalFee="
				+ rmbTotalFee + ", tradeState=" + getFormatTradeState()
				+ ", totalFee=" + getFormatTotalFee() + ", feeType="
				+ getFormatFeeType() + ", timeEnd=" + getFormatTimeEnd()
				+ ", transportFee=" + getFormatTransportFee() + ", productFee="
				+ getFormatProductFee() + ", discount=" + getFormatDiscount()
				+ ", rmbTotalFee=" + getFormatRmbTotalFee() + ", "
				+ super.toString() + "]";
	}
}
