package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.Map;

import com.foxinmy.weixin4j.http.JsonResult;

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
	private String ret_code;
	// 是查询结果出错信息;
	private String ret_msg;
	// 是返回信息中的编码方式;
	private String input_charset;
	// 是订单状态,0 为成功,其他为失败;
	private String trade_state;
	// 是交易模式,1 为即时到帐,其他保留;
	private String trade_mode;
	// 是财付通商户号,即前文的 partnerid;
	private String partner;
	// 是银行类型;
	private String bank_type;
	// 是银行订单号;
	private String bank_billno;
	// 是总金额,单位为分;
	private String total_fee;
	// 是币种,1 为人民币;
	private String fee_type;
	// 是财付通订单号;
	private String transaction_id;
	// 是第三方订单号;
	private String out_trade_no;
	// 表明是否分账,false 为无分账,true 为有分账;
	private boolean is_split;
	// 表明是否退款,false 为无退款,ture 为退款;
	private boolean is_refund;
	// attach 是商户数据包,即生成订单package 时商户填入的 attach;
	private String attach;
	// 支付完成时间;
	private String time_end;
	// 物流费用,单位为分;
	private String transport_fee;
	// 物品费用,单位为分;
	private String product_fee;
	// 折扣价格,单位为分;
	private String discount;
	// 换算成人民币之后的总金额,单位为分,一般看 total_fee 即可。
	private String rmb_total_fee;

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getTrade_state() {
		return trade_state;
	}

	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	public String getTrade_mode() {
		return trade_mode;
	}

	public void setTrade_mode(String trade_mode) {
		this.trade_mode = trade_mode;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getBank_billno() {
		return bank_billno;
	}

	public void setBank_billno(String bank_billno) {
		this.bank_billno = bank_billno;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public boolean isIs_split() {
		return is_split;
	}

	public void setIs_split(boolean is_split) {
		this.is_split = is_split;
	}

	public boolean isIs_refund() {
		return is_refund;
	}

	public void setIs_refund(boolean is_refund) {
		this.is_refund = is_refund;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getTransport_fee() {
		return transport_fee;
	}

	public void setTransport_fee(String transport_fee) {
		this.transport_fee = transport_fee;
	}

	public String getProduct_fee() {
		return product_fee;
	}

	public void setProduct_fee(String product_fee) {
		this.product_fee = product_fee;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getRmb_total_fee() {
		return rmb_total_fee;
	}

	public void setRmb_total_fee(String rmb_total_fee) {
		this.rmb_total_fee = rmb_total_fee;
	}

	private Map<String, String> mapData;

	public Map<String, String> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, String> mapData) {
		this.mapData = mapData;
	}

	@Override
	public String toString() {
		return "Order [ret_code=" + ret_code + ", ret_msg=" + ret_msg
				+ ", input_charset=" + input_charset + ", trade_state="
				+ trade_state + ", trade_mode=" + trade_mode + ", partner="
				+ partner + ", bank_type=" + bank_type + ", bank_billno="
				+ bank_billno + ", total_fee=" + total_fee + ", fee_type="
				+ fee_type + ", transaction_id=" + transaction_id
				+ ", out_trade_no=" + out_trade_no + ", is_split=" + is_split
				+ ", is_refund=" + is_refund + ", attach=" + attach
				+ ", time_end=" + time_end + ", transport_fee=" + transport_fee
				+ ", product_fee=" + product_fee + ", discount=" + discount
				+ ", rmb_total_fee=" + rmb_total_fee + "]";
	}
}
