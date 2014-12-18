package com.foxinmy.weixin4j.mp.payment.v3;

import java.util.Date;

import com.foxinmy.weixin4j.mp.payment.ApiResult;
import com.foxinmy.weixin4j.mp.type.CurrencyType;
import com.foxinmy.weixin4j.mp.type.TradeState;
import com.foxinmy.weixin4j.mp.type.TradeType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订单信息
 * 
 * @className Order
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class Order extends ApiResult {

	private static final long serialVersionUID = 5636828325595317079L;
	// SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭 REVOKED—已撤销
	// USERPAYING--用户支付中 NOPAY--未支付(输入密码或 确认支付超时) PAYERROR--支付失败(其他 原因,如银行返回失败)
	// 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
	@XStreamAlias("trade_state")
	private TradeState tradeState;
	// 用户标识ID
	@XStreamAlias("openid")
	private String openId;
	// 用户是否关注公众账号,Y- 关注,N-未关注,仅在公众 账号类型支付有效
	@XStreamAlias("is_subscribe")
	private String isSubscribe;
	// 交易类型
	@XStreamAlias("trade_type")
	private TradeType tradeType;
	// 银行类型
	@XStreamAlias("bank_type")
	private String bankType;
	// 订单总金额,单位为分
	@XStreamAlias("total_fee")
	private int totalFee;
	// 现金券支付金额<=订单总金 额,订单总金额-现金券金额 为现金支付金额
	@XStreamAlias("coupon_fee")
	private int couponFee;
	// 货币类型,符合 ISO 4217 标准的三位字母代码,默认人民币:CNY
	@XStreamAlias("fee_type")
	private CurrencyType feeType;
	// 微信支付订单号
	@XStreamAlias("transaction_id")
	private String transactionId;
	// 商户订单号
	@XStreamAlias("out_rade_no")
	private String outTradeNo;
	// 商家数据包
	@XStreamAlias("attach")
	private String attach;
	// 支付完成时间,格式为 yyyyMMddhhmmss
	@XStreamAlias("time_end")
	private String timeEnd;

	public TradeState getTradeState() {
		return tradeState;
	}

	public String getOpenId() {
		return openId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public String getBankType() {
		return bankType;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getTotalFee() {
		return totalFee / 100d;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getCouponFee() {
		return couponFee / 100d;
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

	public Date getTimeEnd() {
		return DateUtil.parse2yyyyMMddHHmmss(timeEnd);
	}

	@Override
	public String toString() {
		return "Order [tradeState=" + tradeState + ", openId=" + openId
				+ ", isSubscribe=" + isSubscribe + ", tradeType=" + tradeType
				+ ", bankType=" + bankType + ", totalFee=" + totalFee
				+ ", couponFee=" + couponFee + ", feeType=" + feeType
				+ ", transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", attach=" + attach + ", timeEnd=" + timeEnd
				+ ", " + super.toString() + "]";
	}
}
