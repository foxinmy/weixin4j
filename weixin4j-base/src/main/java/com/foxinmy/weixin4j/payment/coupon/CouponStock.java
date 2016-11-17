package com.foxinmy.weixin4j.payment.coupon;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.mch.MerchantResult;
import com.foxinmy.weixin4j.type.mch.CouponStockStatus;
import com.foxinmy.weixin4j.type.mch.CouponType;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 代金券信息
 * 
 * @className CouponStock
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月27日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CouponStock extends MerchantResult {

	private static final long serialVersionUID = -8627202879200080499L;

	/**
	 * 代金券批次ID
	 */
	@XmlElement(name = "coupon_stock_id")
	@JSONField(name = "coupon_stock_id")
	private String couponStockId;
	/**
	 * 代金券名称
	 */
	@XmlElement(name = "coupon_name")
	@JSONField(name = "coupon_name")
	private String couponName;
	/**
	 * 代金券面额
	 */
	@XmlElement(name = "coupon_value")
	@JSONField(name = "coupon_value")
	private int couponValue;
	/**
	 * 代金券使用最低限额
	 */
	@XmlElement(name = "coupon_mininumn")
	@JSONField(name = "coupon_mininumn")
	private Integer couponMininumn;
	/**
	 * 代金券类型：1-代金券无门槛，2-代金券有门槛互斥，3-代金券有门槛叠加
	 */
	@XmlElement(name = "coupon_type")
	@JSONField(name = "coupon_type")
	private int couponType;
	/**
	 * 批次状态: 1-未激活；2-审批中；4-已激活；8-已作废；16-中止发放；
	 */
	@XmlElement(name = "coupon_stock_status")
	@JSONField(name = "coupon_stock_status")
	private int couponStockStatus;
	/**
	 * 代金券数量
	 */
	@XmlElement(name = "coupon_total")
	@JSONField(name = "coupon_total")
	private int couponTotal;
	/**
	 * 代金券每个人最多能领取的数量, 如果为0，则表示没有限制
	 */
	@XmlElement(name = "max_quota")
	@JSONField(name = "max_quota")
	private Integer maxQuota;
	/**
	 * 代金券锁定数量
	 */
	@XmlElement(name = "locked_num")
	@JSONField(name = "locked_num")
	private Integer lockedNum;
	/**
	 * 代金券已使用数量
	 */
	@XmlElement(name = "used_num")
	@JSONField(name = "used_num")
	private Integer usedNum;
	/**
	 * 代金券已经发送的数量
	 */
	@XmlElement(name = "is_send_num")
	@JSONField(name = "is_send_num")
	private Integer sendNum;
	/**
	 * 生效开始时间 格式为yyyyMMddhhmmss，如2009年12月27日9点10分10秒表示为20091227091010。
	 */
	@XmlElement(name = "begin_time")
	@JSONField(name = "begin_time")
	private String beginTime;
	/**
	 * 生效结束时间 格式为yyyyMMddhhmmss，如2009年12月27日9点10分10秒表示为20091227091010。
	 */
	@XmlElement(name = "end_time")
	@JSONField(name = "end_time")
	private String endTime;
	/**
	 * 创建时间 格式为yyyyMMddhhmmss，如2009年12月27日9点10分10秒表示为20091227091010。
	 */
	@XmlElement(name = "create_time")
	@JSONField(name = "create_time")
	private String createTime;
	/**
	 * 代金券预算额度
	 */
	@XmlElement(name = "coupon_budget")
	@JSONField(name = "coupon_budget")
	private Integer couponBudget;

	public CouponStock() {

	}

	public String getCouponStockId() {
		return couponStockId;
	}

	public String getCouponName() {
		return couponName;
	}

	public int getCouponValue() {
		return couponValue;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatCouponValue() {
		return couponValue / 100d;
	}

	public Integer getCouponMininumn() {
		return couponMininumn;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatCouponMininumn() {
		return couponMininumn != null ? couponMininumn.intValue() / 100d : 0d;
	}

	public int getCouponType() {
		return couponType;
	}

	@JSONField(serialize = false)
	public CouponType getFormatCouponType() {
		for (CouponType couponType : CouponType.values()) {
			if (couponType.getVal() == this.couponType) {
				return couponType;
			}
		}
		return null;
	}

	public int getCouponStockStatus() {
		return couponStockStatus;
	}

	@JSONField(serialize = false)
	public CouponStockStatus getFormatCouponStockStatus() {
		for (CouponStockStatus couponStockStatus : CouponStockStatus.values()) {
			if (couponStockStatus.getVal() == this.couponStockStatus) {
				return couponStockStatus;
			}
		}
		return null;
	}

	public int getCouponTotal() {
		return couponTotal;
	}

	public Integer getMaxQuota() {
		return maxQuota;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatMaxQuota() {
		return maxQuota != null ? maxQuota.intValue() / 100d : 0d;
	}

	public Integer getLockedNum() {
		return lockedNum;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatLockedNum() {
		return lockedNum != null ? lockedNum.intValue() / 100d : 0d;
	}

	public Integer getUsedNum() {
		return usedNum;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatUsedNum() {
		return usedNum != null ? usedNum.intValue() / 100d : 0d;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatSendNum() {
		return sendNum != null ? sendNum.intValue() / 100d : 0d;
	}

	public String getBeginTime() {
		return beginTime;
	}

	@JSONField(serialize = false)
	public Date getFormatBeginTime() {
		return beginTime != null ? DateUtil.parse2yyyyMMddHHmmss(beginTime)
				: null;
	}

	public String getEndTime() {
		return endTime;
	}

	@JSONField(serialize = false)
	public Date getFormatEndTime() {
		return endTime != null ? DateUtil.parse2yyyyMMddHHmmss(endTime) : null;
	}

	public String getCreateTime() {
		return createTime;
	}

	@JSONField(serialize = false)
	public Date getFormatCreateTime() {
		return createTime != null ? DateUtil.parse2yyyyMMddHHmmss(createTime)
				: null;
	}

	public Integer getCouponBudget() {
		return couponBudget;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatCouponBudget() {
		return couponBudget != null ? couponBudget.intValue() / 100d : 0d;
	}

	@Override
	public String toString() {
		return "CouponDetail [couponStockId=" + couponStockId + ", couponName="
				+ couponName + ", couponValue=" + getFormatCouponValue()
				+ ", couponMininumn=" + getFormatCouponMininumn()
				+ ", couponType=" + getFormatCouponType()
				+ ", couponStockStatus=" + getFormatCouponStockStatus()
				+ ", couponTotal=" + couponTotal + ", maxQuota="
				+ getFormatMaxQuota() + ", lockedNum=" + getFormatLockedNum()
				+ ", usedNum=" + getFormatUsedNum() + ", sendNum="
				+ getFormatSendNum() + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", createTime=" + createTime
				+ ", couponBudget=" + getFormatCouponBudget() + ", "
				+ super.toString() + "]";
	}
}
