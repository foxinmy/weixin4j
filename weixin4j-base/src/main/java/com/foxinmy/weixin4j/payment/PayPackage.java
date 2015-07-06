package com.foxinmy.weixin4j.payment;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 订单信息
 * 
 * @className PayPackage
 * @author jy
 * @date 2014年12月18日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayPackage implements Serializable {

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
	 * 附加数据,原样返回 非必须
	 */
	private String attach;
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
	private String totalFee;
	/**
	 * 订单生成的机器 IP 必须
	 */
	@XmlElement(name = "spbill_create_ip")
	@JSONField(name = "spbill_create_ip")
	private String createIp;
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
	/**
	 * 通知地址接收微信支付成功通知 必须
	 */
	@XmlElement(name = "notify_url")
	@JSONField(name = "notify_url")
	private String notifyUrl;

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

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	/**
	 * <font color="red">单位为元,自动格式化为分</font>
	 * 
	 * @param totalFee
	 *            订单总额 单位为元
	 */
	public void setTotalFee(double totalFee) {
		this.totalFee = DateUtil.formaFee2Fen(totalFee);
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	protected PayPackage(){
		// jaxb required
	}
	
	/**
	 * 订单对象
	 * 
	 * @param body
	 *            订单描述
	 * @param attach
	 *            附加数据
	 * @param outTradeNo
	 *            商户内部ID
	 * @param totalFee
	 *            订单总额 <font color="red">单位为元</font>
	 * @param spbillCreateIp
	 *            生成订单数据的机器IP
	 * @param timeStart
	 *            订单生成时间
	 * @param timeExpire
	 *            订单失效时间
	 * @param goodsTag
	 *            订单标记
	 * @param notifyUrl
	 *            回调地址
	 */
	public PayPackage(String body, String attach, String outTradeNo,
			double totalFee, String createIp, Date timeStart,
			Date timeExpire, String goodsTag, String notifyUrl) {
		this.body = body;
		this.attach = attach;
		this.outTradeNo = outTradeNo;
		this.totalFee = DateUtil.formaFee2Fen(totalFee);
		this.createIp = createIp;
		this.timeStart = timeStart != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeStart) : null;
		this.timeExpire = timeExpire != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(timeExpire) : null;
		this.goodsTag = goodsTag;
		this.notifyUrl = notifyUrl;
	}

	@Override
	public String toString() {
		return "PayPackage [body=" + body + ", detail=" + detail + ", attach="
				+ attach + ", outTradeNo=" + outTradeNo + ", totalFee="
				+ totalFee + ", createIp=" + createIp
				+ ", timeStart=" + timeStart + ", timeExpire=" + timeExpire
				+ ", goodsTag=" + goodsTag + ", notifyUrl=" + notifyUrl + "]";
	}
}
