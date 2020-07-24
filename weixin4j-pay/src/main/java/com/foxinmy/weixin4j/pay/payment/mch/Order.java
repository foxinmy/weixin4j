package com.foxinmy.weixin4j.pay.payment.mch;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.coupon.OrderCouponInfo;
import com.foxinmy.weixin4j.pay.type.BankType;
import com.foxinmy.weixin4j.pay.type.CurrencyType;
import com.foxinmy.weixin4j.pay.type.TradeState;
import com.foxinmy.weixin4j.pay.type.TradeType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * 订单信息
 * 
 * @className Order
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月2日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order extends MerchantTradeResult {

	private static final long serialVersionUID = 5636828325595317079L;
	/**
	 * 交易状态
	 * 
	 * @see TradeState
	 */
	@XmlElement(name = "trade_state")
	@JSONField(name = "trade_state")
	private String tradeState;
	/**
	 * 用户的openid
	 */
	@XmlElement(name = "openid")
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 用户是否关注公众账号,Y- 关注,N-未关注,仅在公众 账号类型支付有效
	 */
	@XmlElement(name = "is_subscribe")
	@JSONField(name = "is_subscribe")
	private String isSubscribe;
	/**
	 * 交易类型
	 *
	 * @see TradeType
	 */
	@XmlElement(name = "trade_type")
	@JSONField(name = "trade_type")
	private String tradeType;
	/**
	 * 银行类型
	 */
	@XmlElement(name = "bank_type")
	@JSONField(name = "bank_type")
	private String bankType;

	/**
	 * 现金支付货币类型,符合 ISO 4217 标准的三位字母代码,默认人民币:CNY
	 * 
	 * @see com.foxinmy.weixin4j.pay.type.CurrencyType
	 */
	@XmlElement(name = "cash_fee_type")
	@JSONField(name = "cash_fee_type")
	private String cashFeeType;
	/**
	 * 代金券金额:“代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额
	 */
	@XmlElement(name = "coupon_fee")
	@JSONField(name = "coupon_fee")
	private Integer couponFee;
	/**
	 * 代金券或立减优惠使用数量
	 */
	@XmlElement(name = "coupon_count")
	@JSONField(name = "coupon_count")
	private Integer couponCount;
	/**
	 * 代金券信息 验证签名有点麻烦
	 */
	@ListsuffixResult
	private List<OrderCouponInfo> couponList;
	/**
	 * 商家数据包
	 */
	private String attach;
	/**
	 * 支付完成时间,格式为 yyyyMMddhhmmss
	 */
	@XmlElement(name = "time_end")
	@JSONField(name = "time_end")
	private String timeEnd;
	/**
	 * 交易状态描述
	 */
	@XmlElement(name = "trade_state_desc")
	@JSONField(name = "trade_state_desc")
	private String tradeStateDesc;

	/**
	 * 用户在子商户下的openid
	 */
	@XmlElement(name = "sub_openid")
	@JSONField(name = "sub_openid")
	private String subOpenId;
	/**
	 * 是否关注子公众账号,Y- 关注,N-未关注,仅在公众 账号类型支付有效
	 */
	@XmlElement(name = "sub_is_subscribe")
	@JSONField(name = "sub_is_subscribe")
	private String subIsSubscribe;

	protected Order() {
		// jaxb required
	}

	@JSONField(serialize = false)
	public TradeState getFormatTradeState() {
		return tradeState != null ? TradeState
				.valueOf(tradeState.toUpperCase()) : null;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	@JSONField(serialize = false)
	public boolean getFormatIsSubscribe() {
		return isSubscribe != null && isSubscribe.equalsIgnoreCase("y");
	}

	@JSONField(serialize = false)
	public TradeType getFormatTradeType() {
		return tradeType != null ? TradeType.valueOf(tradeType.toUpperCase())
				: null;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	@JSONField(serialize = false)
	public BankType getFormatBankType() {
		return bankType != null ? BankType.valueOf(bankType.toUpperCase())
				: null;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatCouponFee() {
		return couponFee != null ? couponFee / 100d : 0d;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
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

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	@JSONField(serialize = false)
	public Date getFormatTimeEnd() {
		return timeEnd != null ? DateUtil.parse2yyyyMMddHHmmss(timeEnd) : null;
	}

	public String getTradeStateDesc() {
		return tradeStateDesc;
	}

	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}

	public List<OrderCouponInfo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<OrderCouponInfo> couponList) {
		this.couponList = couponList;
	}

	public String getSubOpenId() {
		return subOpenId;
	}

	public void setSubOpenId(String subOpenId) {
		this.subOpenId = subOpenId;
	}

	public String getSubIsSubscribe() {
		return subIsSubscribe;
	}

	public void setSubIsSubscribe(String subIsSubscribe) {
		this.subIsSubscribe = subIsSubscribe;
	}

	@JSONField(serialize = false)
	public boolean getFormatSubIsSubscribe() {
		return subIsSubscribe != null && subIsSubscribe.equalsIgnoreCase("y");
	}

	public String getCashFeeType() {
		return cashFeeType;
	}

	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatCashFeeType() {
		return cashFeeType != null ? CurrencyType.valueOf(cashFeeType
				.toUpperCase()) : null;
	}

	@Override
	public String toString() {
		return "Order [tradeState=" + tradeState + ", openId=" + openId
				+ ", isSubscribe=" + isSubscribe + ", tradeType=" + tradeType
				+ ", bankType=" + bankType + ", cashFeeType=" + cashFeeType
				+ ", couponFee=" + couponFee + ", couponCount=" + couponCount
				+ ", couponList=" + couponList + ", attach=" + attach
				+ ", timeEnd=" + timeEnd + ", tradeStateDesc=" + tradeStateDesc
				+ ", subOpenId=" + subOpenId + ", subIsSubscribe="
				+ subIsSubscribe + ", " + super.toString() + "]";
	}
}
