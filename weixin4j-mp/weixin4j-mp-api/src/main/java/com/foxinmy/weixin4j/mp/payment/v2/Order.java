package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 订单信息
 * 
 * @className Order
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.7
 * @see
 */
public class Order extends JsonResult {

	private static final long serialVersionUID = 4543552984506609920L;

	// 是查询结果状态码,0 表明成功,其他表明错误;
	@JSONField(name = "ret_code")
	private int retCode;
	// 是查询结果出错信息;
	@JSONField(name = "ret_msg")
	private String retMsg;
	// 是返回信息中的编码方式;
	@JSONField(name = "input_charset")
	private String inputCharset;
	// 是订单状态,0 为成功,其他为失败;
	@JSONField(name = "trade_state")
	private int tradeState;
	// 是交易模式,1 为即时到帐,其他保留;
	@JSONField(name = "trade_mode")
	private int tradeMode;
	// 是财付通商户号,即前文的 partnerid;
	private String partner;
	// 是银行类型;
	@JSONField(name = "bank_type")
	private String bankType;
	// 是银行订单号;
	@JSONField(name = "bank_billno")
	private String bankBillno;
	// 是总金额,单位为分;
	@JSONField(name = "total_fee")
	private int totalFee;
	// 是币种,1 为人民币;
	@JSONField(name = "fee_type")
	private String feeType;
	// 是财付通订单号;
	@JSONField(name = "transaction_id")
	private String transactionId;
	// 是第三方订单号;
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	// 表明是否分账,false 为无分账,true 为有分账;
	@JSONField(name = "is_split")
	private boolean isSplit;
	// 表明是否退款,false 为无退款,ture 为退款;
	@JSONField(name = "is_refund")
	private boolean isRefund;
	// attach 是商户数据包,即生成订单package 时商户填入的 attach;
	private String attach;
	// 支付完成时间;
	@JSONField(name = "time_end")
	private String timeEnd;
	// 物流费用,单位为分;
	@JSONField(name = "transport_fee")
	private int transportFee;
	// 物品费用,单位为分;
	@JSONField(name = "product_fee")
	private int productFee;
	// 折扣价格,单位为分;
	private int discount;
	// 换算成人民币之后的总金额,单位为分,一般看 total_fee 即可。
	@JSONField(name = "rmb_total_fee")
	private int rmbTotalFee;

	@JSONField(serialize = false)
	private Map<String, String> mapData;

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public int getTradeState() {
		return tradeState;
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

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
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

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getTotalFee() {
		return totalFee / 100d;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
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

	public Date getTimeEnd() {
		return DateUtil.parse2yyyyMMddHHmmss(timeEnd);
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getTransportFee() {
		return transportFee / 100d;
	}

	public void setTransportFee(int transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getProductFee() {
		return productFee / 100d;
	}

	public void setProductFee(int productFee) {
		this.productFee = productFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getDiscount() {
		return discount / 100d;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getRmbTotalFee() {
		return rmbTotalFee / 100d;
	}

	public void setRmbTotalFee(int rmbTotalFee) {
		this.rmbTotalFee = rmbTotalFee;
	}

	public Map<String, String> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, String> mapData) {
		this.mapData = mapData;
	}

	@Override
	public String toString() {
		return "Order [retCode=" + retCode + ", retMsg=" + retMsg
				+ ", inputCharset=" + inputCharset + ", tradeState="
				+ tradeState + ", tradeMode=" + tradeMode + ", partner="
				+ partner + ", bankType=" + bankType + ", bankBillno="
				+ bankBillno + ", totalFee=" + totalFee + ", feeType="
				+ feeType + ", transactionId=" + transactionId
				+ ", outTradeNo=" + outTradeNo + ", isSplit=" + isSplit
				+ ", isRefund=" + isRefund + ", attach=" + attach
				+ ", timeEnd=" + timeEnd + ", transportFee=" + transportFee
				+ ", productFee=" + productFee + ", discount=" + discount
				+ ", rmbTotalFee=" + rmbTotalFee + ", mapData=" + mapData + "]";
	}
}
