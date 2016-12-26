package com.foxinmy.weixin4j.payment;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.mch.MerchantResult;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 订单信息
 * 
 * @className PayPackage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月18日
 * @since JDK 1.6
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayPackage extends MerchantResult {

	private static final long serialVersionUID = 3450161267802545790L;

	/**
	 * 商品描述 必须
	 */
	private String body;
	/**
	 * 商品详情 非必须
	 */
	private String detail;
	/**
	 * 商户系统内部的订单号 ,32 个字符内 、可包含字母 ,确保 在商户系统唯一 必须
	 */
	@XmlElement(name = "out_trade_no")
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	/**
	 * 订单总金额,单位为分,不能带小数点 必须
	 */
	@XmlElement(name = "total_fee")
	@JSONField(name = "total_fee")
	private int totalFee;
	/**
	 * 通知地址接收微信支付成功通知 必须
	 */
	@XmlElement(name = "notify_url")
	@JSONField(name = "notify_url")
	private String notifyUrl;
	/**
	 * 订单生成的机器 IP 必须
	 */
	@XmlElement(name = "spbill_create_ip")
	@JSONField(name = "spbill_create_ip")
	private String createIp;
	/**
	 * 附加数据,原样返回 非必须
	 */
	private String attach;
	/**
	 * 订单生成时间,格式为 yyyyMMddHHmmss,如 2009 年 12月25日9点10分10秒表示为 20091225091010。时区 为
	 * GMT+8 beijing。该时间取 自商户服务器 非必须
	 */
	@XmlElement(name = "time_start")
	@JSONField(name = "time_start")
	private String timeStart;
	/**
	 * 订单失效时间,格为 yyyyMMddHHmmss,如 2009 年 12月27日9点10分10秒表示为 20091227091010。时区 为
	 * GMT+8 beijing。该时间取 自商户服务商品标记 非必须
	 */
	@XmlElement(name = "time_expire")
	@JSONField(name = "time_expire")
	private String timeExpire;
	/**
	 * 商品标记,该字段不能随便填,不使用请填空 非必须
	 */
	@XmlElement(name = "goods_tag")
	@JSONField(name = "goods_tag")
	private String goodsTag;

	protected PayPackage() {
		// jaxb required
	}

	/**
	 * 订单对象
	 * 
	 * @param body
	 *            订单描述 必填
	 * @param detail
	 *            订单详情 非必填
	 * @param outTradeNo
	 *            商户内部ID 必填
	 * @param totalFee
	 *            订单总额 必填 <font color="red">单位为元</font>
	 * @param notifyUrl
	 *            回调地址 必填
	 * @param createIp
	 *            生成订单数据的机器IP 必填
	 * @param attach
	 *            附加数据 非必填
	 * @param timeStart
	 *            订单生成时间 非必填
	 * @param timeExpire
	 *            订单失效时间 非必填
	 * @param goodsTag
	 *            订单标记 非必填
	 */
	public PayPackage(String body, String detail, String outTradeNo,
			double totalFee, String notifyUrl, String createIp, String attach,
			Date timeStart, Date timeExpire, String goodsTag) {
		this.body = body;
		this.detail = detail;
		this.outTradeNo = outTradeNo;
		this.totalFee = DateUtil.formatYuan2Fen(totalFee);
		this.notifyUrl = notifyUrl;
		this.createIp = createIp;
		this.attach = attach;
		this.timeStart = timeStart != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeStart) : null;
		this.timeExpire = timeExpire != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeExpire) : null;
		this.goodsTag = goodsTag;
	}

	/**
	 * 订单对象
	 *
	 * @param body
	 *            订单描述 必填
	 * @param detail
	 *            订单详情 非必填
	 * @param outTradeNo
	 *            商户内部ID 必填
	 * @param totalFee
	 *            订单总额 必填 <font color="red">单位为分</font>
	 * @param notifyUrl
	 *            回调地址 必填
	 * @param createIp
	 *            生成订单数据的机器IP 必填
	 * @param attach
	 *            附加数据 非必填
	 * @param timeStart
	 *            订单生成时间 非必填
	 * @param timeExpire
	 *            订单失效时间 非必填
	 * @param goodsTag
	 *            订单标记 非必填
	 */
	public PayPackage(String body, String detail, String outTradeNo,
					  long totalFee, String notifyUrl, String createIp, String attach,
					  Date timeStart, Date timeExpire, String goodsTag) {
		this.body = body;
		this.detail = detail;
		this.outTradeNo = outTradeNo;
		this.totalFee = Long.valueOf(totalFee).intValue();
		this.notifyUrl = notifyUrl;
		this.createIp = createIp;
		this.attach = attach;
		this.timeStart = timeStart != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeStart) : null;
		this.timeExpire = timeExpire != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeExpire) : null;
		this.goodsTag = goodsTag;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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

	/**
	 * <font color="red">单位为元,自动格式化为分</font>
	 * 
	 * @param totalFee
	 *            订单总额 单位为元
	 */
	public void setTotalFee(double totalFee) {
		this.totalFee = DateUtil.formatYuan2Fen(totalFee);
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeStart) : null;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeExpire) : null;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	@Override
	public String toString() {
		return "body=" + body + ", detail=" + detail + ", outTradeNo="
				+ outTradeNo + ", totalFee=" + totalFee + ", notifyUrl="
				+ notifyUrl + ", createIp=" + createIp + ", attach=" + attach
				+ ", timeStart=" + timeStart + ", timeExpire=" + timeExpire
				+ ", goodsTag=" + goodsTag;
	}
}