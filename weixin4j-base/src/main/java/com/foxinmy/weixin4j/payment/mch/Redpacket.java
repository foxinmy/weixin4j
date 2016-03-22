package com.foxinmy.weixin4j.payment.mch;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 红包
 * 
 * @className Redpacket
 * @author jy
 * @date 2015年3月28日
 * @since JDK 1.6
 * @see <a
 *      href="http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_1">普通红包</a>
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=16_1">裂变红包</a>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Redpacket implements Serializable {

	private static final long serialVersionUID = -7021352305575714281L;
	/**
	 * 商户订单号（每个订单号必须唯一） 组成： mch_id+yyyymmdd+10位一天内不能重复的数字。
	 */
	@XmlElement(name = "mch_billno")
	@JSONField(name = "mch_billno")
	private String outTradeNo;
	/**
	 * 红包发送者名称 必填
	 */
	@XmlElement(name = "send_name")
	@JSONField(name = "send_name")
	private String sendName;
	/**
	 * 接收红包的用户的openid
	 */
	@XmlElement(name = "re_openid")
	@JSONField(name = "re_openid")
	private String openid;
	/**
	 * 付款金额，单位分
	 */
	@XmlElement(name = "total_amount")
	@JSONField(name = "total_amount")
	private String totalAmount;
	/**
	 * 红包发放总人数
	 */
	@XmlElement(name = "total_num")
	@JSONField(name = "total_num")
	private int totalNum;
	/**
	 * 红包金额设置方式(裂变红包) ALL_RAND—全部随机,商户指定总金额和红包发放总人数，由微信支付随机计算出各红包金额
	 */
	@XmlElement(name = "amt_type")
	@JSONField(name = "amt_type")
	private String amtType;
	/**
	 * 红包祝福语
	 */
	private String wishing;
	/**
	 * ip地址
	 */
	@XmlElement(name = "client_ip")
	@JSONField(name = "client_ip")
	private String clientIp;
	/**
	 * 活动名称
	 */
	@XmlElement(name = "act_name")
	@JSONField(name = "act_name")
	private String actName;
	/**
	 * 备注
	 */
	private String remark;

	protected Redpacket() {
		// jaxb required
	}

	/**
	 * 红包
	 * 
	 * @param outTradeNo
	 *            商户侧一天内不可重复的订单号 接口根据商户订单号支持重入 如出现超时可再调用
	 * @param sendName
	 *            红包发送者名称
	 * @param openid
	 *            接受收红包的用户的openid
	 * @param totalAmount
	 *            付款金额 <font color="red">单位为元,自动格式化为分</font>
	 * @param totalNum
	 *            红包发放总人数 大于1视为裂变红包
	 */
	public Redpacket(String outTradeNo, String sendName, String openid,
			double totalAmount, int totalNum) {
		this.outTradeNo = outTradeNo;
		this.sendName = sendName;
		this.openid = openid;
		this.totalAmount = DateUtil.formaFee2Fen(totalAmount);
		this.totalNum = totalNum;
		this.amtType = totalNum > 1 ? "ALL_RAND" : null;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getSendName() {
		return sendName;
	}

	public String getOpenid() {
		return openid;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getAmtType() {
		return amtType;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Redpacket [ sendName=" + sendName + ", openid=" + openid
				+ ", amtType=" + amtType + ", totalAmount=" + totalAmount
				+ ", totalNum=" + totalNum + ", wishing=" + wishing
				+ ", clientIp=" + clientIp + ", actName=" + actName
				+ ", remark=" + remark + "]";
	}
}
