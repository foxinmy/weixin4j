package com.foxinmy.weixin4j.mp.payment.v3;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponInfo;
import com.foxinmy.weixin4j.mp.type.CurrencyType;
import com.foxinmy.weixin4j.mp.type.TradeState;
import com.foxinmy.weixin4j.mp.type.TradeType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * V3订单信息
 * 
 * @className Order
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order extends ApiResult {

	private static final long serialVersionUID = 5636828325595317079L;
	/**
	 * 交易状态
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.TradeState
	 */
	@XmlElement(name = "trade_state")
	@JSONField(name = "trade_state")
	private TradeState tradeState;
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
	 * @see com.foxinmy.weixin4j.mp.type.TradeType
	 */
	@XmlElement(name = "trade_type")
	@JSONField(name = "trade_type")
	private TradeType tradeType;
	/**
	 * 银行类型
	 */
	@XmlElement(name = "bank_type")
	@JSONField(name = "bank_type")
	private String bankType;
	/**
	 * 订单总金额,单位为分
	 */
	@XmlElement(name = "total_fee")
	@JSONField(name = "total_fee")
	private int totalFee;
	/**
	 * 现金券支付金额<=订单总金 额,订单总金额-现金券金额 为现金支付金额
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
	 * 代金券信息
	 */
	@XmlTransient
	@JSONField(serialize = false)
	private List<CouponInfo> couponList;
	/**
	 * 现金支付金额
	 */
	@XmlElement(name = "cash_fee")
	@JSONField(name = "cash_fee")
	private int cashFee;
	/**
	 * 货币类型,符合 ISO 4217 标准的三位字母代码,默认人民币:CNY
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "fee_type")
	@JSONField(name = "fee_type")
	private CurrencyType feeType;
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

	protected Order() {
		// jaxb required
	}
	
	public TradeState getTradeState() {
		return tradeState;
	}

	public String getOpenId() {
		return openId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	@JSONField(serialize = false, deserialize = false)
	public boolean getFormatIsSubscribe() {
		return isSubscribe != null && isSubscribe.equalsIgnoreCase("y");
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public String getBankType() {
		return bankType;
	}

	public int getTotalFee() {
		return totalFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatTotalFee() {
		return totalFee / 100d;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatCouponFee() {
		return couponFee != null ? couponFee / 100d : 0d;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	@JSONField(serialize = false, deserialize = false)
	public int getFormatCouponCount() {
		return couponCount != null ? couponCount.intValue() : 0;
	}

	public int getCashFee() {
		return cashFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatCashFee() {
		return cashFee / 100d;
	}

	public CurrencyType getFeeType() {
		return feeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getAttach() {
		return attach;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	@JSONField(serialize = false, deserialize = false)
	public Date getFormatTimeEnd() {
		if (StringUtil.isNotBlank(timeEnd)) {
			return DateUtil.parse2yyyyMMddHHmmss(timeEnd);
		}
		return null;
	}

	public String getTradeStateDesc() {
		return tradeStateDesc;
	}

	public List<CouponInfo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<CouponInfo> couponList) {
		this.couponList = couponList;
	}

	@Override
	public String toString() {
		return "Order [tradeState=" + tradeState + ", openId=" + openId
				+ ", isSubscribe=" + getFormatIsSubscribe() + ", tradeType="
				+ tradeType + ", bankType=" + bankType + ", feeType=" + feeType
				+ ", transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", attach=" + attach + ", timeEnd=" + timeEnd
				+ ", totalFee=" + getFormatTotalFee() + ", couponFee="
				+ getFormatCouponFee() + ", couponCount="
				+ getFormatCouponCount() + ", couponList=" + couponList
				+ ", cashFee=" + getFormatCashFee() + ", timeEnd="
				+ getFormatTimeEnd() + ", tradeStateDesc=" + tradeStateDesc
				+ ", " + super.toString() + "]";
	}
}
