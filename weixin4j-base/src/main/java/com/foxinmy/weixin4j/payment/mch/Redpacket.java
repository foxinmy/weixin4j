package com.foxinmy.weixin4j.payment.mch;

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
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月28日
 * @since JDK 1.6
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_1">普通红包</a>
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=16_1">裂变红包</a>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Redpacket extends MerchantResult {

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
	private String openId;
	/**
	 * 付款金额，单位分
	 */
	@XmlElement(name = "total_amount")
	@JSONField(name = "total_amount")
	private int totalAmount;
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

	/**
	 * 服务商模式下触达用户时的appid(可填服务商自己的appid或子商户的appid)，服务商模式下必填，
	 * 服务商模式下填入的子商户appid必须在微信支付商户平台中先录入，否则会校验不过。 非必须
	 */
	@XmlElement(name = "msgappid")
	@JSONField(name = "msgappid")
	private String msgAppId;
	/**
	 * 扣钱方mchid,常规模式下无效，服务商模式下选填，服务商模式下不填默认扣子商户的钱.非必须
	 */
	@XmlElement(name = "consume_mch_id")
	@JSONField(name = "consume_mch_id")
	private String consumeMchId;

	protected Redpacket() {
		// jaxb required
	}

	/**
	 * 红包
	 *
	 * @param outTradeNo
	 *            商户侧一天内不可重复的订单号 接口根据商户订单号支持重入 如出现超时可再调用 必填
	 * @param sendName
	 *            红包发送者名称 必填
	 * @param openId
	 *            接受收红包的用户的openid 必填
	 * @param totalAmount
	 *            付款金额 <font color="red">单位为元,自动格式化为分</font> 必填
	 * @param totalNum
	 *            红包发放总人数 大于1视为裂变红包 必填
	 * @param wishing
	 *            红包祝福语 必填
	 * @param clientIp
	 *            Ip地址 必填
	 * @param actName
	 *            活动名称 必填
	 * @param remark
	 *            备注 必填
	 */
	public Redpacket(String outTradeNo, String sendName, String openId,
			double totalAmount, int totalNum, String wishing, String clientIp,
			String actName, String remark) {
		this.outTradeNo = outTradeNo;
		this.sendName = sendName;
		this.openId = openId;
		this.totalNum = totalNum;
		this.wishing = wishing;
		this.clientIp = clientIp;
		this.actName = actName;
		this.remark = remark;
		this.totalAmount = DateUtil.formatYuan2Fen(totalAmount);
		this.amtType = totalNum > 1 ? "ALL_RAND" : null;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getSendName() {
		return sendName;
	}

	public String getOpenId() {
		return openId;
	}

	public int getTotalAmount() {
		return totalAmount;
	}
	
	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatTotalAmount() {
		return totalAmount / 100d;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public String getWishing() {
		return wishing;
	}

	public String getAmtType() {
		return amtType;
	}

	public String getClientIp() {
		return clientIp;
	}

	public String getActName() {
		return actName;
	}

	public String getRemark() {
		return remark;
	}

	public String getMsgAppId() {
		return msgAppId;
	}

	public void setMsgAppId(String msgAppId) {
		this.msgAppId = msgAppId;
	}

	public String getConsumeMchId() {
		return consumeMchId;
	}

	public void setConsumeMchId(String consumeMchId) {
		this.consumeMchId = consumeMchId;
	}

	@Override
	public String toString() {
		return "Redpacket [msgAppId=" + msgAppId + ", consumeMchId="
				+ consumeMchId + ", outTradeNo=" + outTradeNo + ", sendName="
				+ sendName + ", openId=" + openId + ", totalAmount="
				+ totalAmount + ", totalNum=" + totalNum + ", amtType="
				+ amtType + ", wishing=" + wishing + ", clientIp=" + clientIp
				+ ", actName=" + actName + ", remark=" + remark + ", "
				+ super.toString() + "]";
	}
}
