package com.foxinmy.weixin4j.mp.oldpayment;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.PayPackage;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * V2支付的订单详情
 *
 * @className PayPackageV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年8月17日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayPackageV2 extends PayPackage {

	private static final long serialVersionUID = 5557542103637795834L;

	/**
	 * 银行通道类型 固定为"WX" 非空
	 */
	@XmlElement(name = "bank_type")
	@JSONField(name = "bank_type")
	private String bankType;
	/**
	 * 商户号 注册时分配的财付通商户号 非空
	 */
	private String partner;
	/**
	 * 支付币种 默认值是"1" 非空
	 */
	@XmlElement(name = "fee_type")
	@JSONField(name = "fee_type")
	private String feeType;
	/**
	 * 物流费用 可为空 如果有值,必须保 证 transport_fee + product_fee=total_fee
	 */
	@XmlElement(name = "transport_fee")
	@JSONField(name = "transport_fee")
	private Integer transportFee;
	/**
	 * 商品费用 可为空 商品费用,单位为分。如果有值,必须保 证 transport_fee +product_fee=total_fee;
	 */
	@XmlElement(name = "product_fee")
	@JSONField(name = "product_fee")
	private Integer productFee;
	/**
	 * 传入参数字符编码 取值范围:"GBK"、"UTF-8",默认:"GBK" 可为空
	 */
	@XmlElement(name = "input_charset")
	@JSONField(name = "input_charset")
	private String inputCharset;

	protected PayPackageV2() {
		// jaxb required
	}

	/**
	 * 支付信息
	 *
	 * @param partner
	 *            商户号 <font color="red">必填</font>
	 * @param body
	 *            支付详情 <font color="red">必填</font>
	 * @param outTradeNo
	 *            订单号 <font color="red">必填</font>
	 * @param totalFee
	 *            订单总额(元) <font color="red">必填</font>
	 * @param notifyUrl
	 *            支付回调URL <font color="red">必填</font>
	 * @param createIp
	 *            订单生成的机器 IP <font color="red">必填</font>
	 */
	public PayPackageV2(String partner, String body, String outTradeNo,
			double totalFee, String notifyUrl, String createIp) {
		this(partner, body, outTradeNo, totalFee, notifyUrl, createIp, null,
				null, null, 0d, 0d, null);
	}

	/**
	 * 支付信息 完整参数
	 *
	 * @param partner
	 *            商户号 <font color="red">必填</font>
	 * @param body
	 *            支付详情 <font color="red">必填</font>
	 * @param outTradeNo
	 *            订单号 <font color="red">必填</font>
	 * @param totalFee
	 *            订单总额(元) <font color="red">必填</font>
	 * @param notifyUrl
	 *            支付回调URL <font color="red">必填</font>
	 * @param createIp
	 *            订单生成的机器 IP <font color="red">必填</font>
	 * @param attach
	 *            附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * @param timeStart
	 *            订单生成时间，格式为yyyyMMddHHmmss
	 * @param timeExpire
	 *            订单失效时间，格式为yyyyMMddHHmmss;注意：最短失效时间间隔必须大于5分钟
	 * @param transportFee
	 *            物流费用 如有值 必须保证 transportFee+productFee=totalFee
	 * @param transportFee
	 *            商品费用 如有值 必须保证 transportFee+productFee=totalFee
	 * @param goodsTag
	 *            商品标记，代金券或立减优惠功能的参数
	 */
	public PayPackageV2(String partner, String body, String outTradeNo,
			double totalFee, String notifyUrl, String createIp, String attach,
			Date timeStart, Date timeExpire, double transportFee,
			double productFee, String goodsTag) {
		setBody(body);
		setOutTradeNo(outTradeNo);
		setTotalFee(totalFee);
		setNotifyUrl(notifyUrl);
		setCreateIp(createIp);
		setAttach(attach);
		setTimeStart(timeStart);
		setTimeExpire(timeExpire);
		setGoodsTag(goodsTag);
		this.bankType = "WX";
		this.feeType = "1";
		this.inputCharset = "UTF-8";
		this.partner = partner;
		this.transportFee = transportFee > 0d ? DateUtil
				.formatYuan2Fen(transportFee) : null;
		this.productFee = productFee > 0 ? DateUtil.formatYuan2Fen(productFee)
				: null;
	}

	public String getBankType() {
		return bankType;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getFeeType() {
		return feeType;
	}

	public Integer getTransportFee() {
		return transportFee;
	}
	
	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatTransportFee() {
		return transportFee != null ? transportFee / 100d : 0d;
	}

	/**
	 * <font color="red">单位为元,自动格式化为分</font>
	 *
	 * @param transportFee
	 *            物流费用 单位为元
	 */
	public void setTransportFee(double transportFee) {
		this.transportFee = DateUtil.formatYuan2Fen(transportFee);
	}

	public Integer getProductFee() {
		return productFee;
	}
	
	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatProductFee() {
		return productFee != null ? productFee / 100d : 0d;
	}

	/**
	 * <font color="red">单位为元,自动格式化为分</font>
	 *
	 * @param productFee
	 *            商品 单位为元
	 */
	public void setProductFee(double productFee) {
		this.productFee = DateUtil.formatYuan2Fen(productFee);
	}

	public String getInputCharset() {
		return inputCharset;
	}

	@Override
	public String toString() {
		return "PayPackageV2 [bankType=" + bankType + ", partner=" + partner
				+ ", feeType=" + feeType + ", transportFee=" + transportFee
				+ ", productFee=" + productFee + ", inputCharset="
				+ inputCharset + ", " + super.toString() + "]";
	}
}