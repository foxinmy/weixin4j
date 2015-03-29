package com.foxinmy.weixin4j.mp.payment.v2;

import com.foxinmy.weixin4j.mp.payment.PayBaseInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * V2维权POST的数据
 * 
 * @className PayFeedback
 * @author jy
 * @date 2014年10月29日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class PayFeedback extends PayBaseInfo {

	private static final long serialVersionUID = 7230049346213966310L;

	/**
	 * 投诉单号
	 */
	@XStreamAlias("FeedBackId")
	private String feedbackId;
	/**
	 * 用户ID
	 */
	@XStreamAlias("OpenId")
	private String openId;
	/**
	 * 订单交易单号
	 */
	@XStreamAlias("TransId")
	private String transId;
	/**
	 * 投诉原因
	 */
	@XStreamAlias("Reason")
	private String reason;
	/**
	 * 用户希望解决方案
	 */
	@XStreamAlias("Solution")
	private String solution;
	/**
	 * 备注信息+电话
	 */
	@XStreamAlias("ExtInfo")
	private String extInfo;
	/**
	 * 用户上传的图片凭证,最多五张
	 */
	@XStreamAlias("PicInfo")
	private String picInfo;
	/**
	 * 通知类型 request 用户提交投诉 confirm 用户确认消除 投诉 reject 用户拒绝消除投诉
	 */
	@XStreamAlias("MsgType")
	private String status;

	public String getFeedbackId() {
		return feedbackId;
	}

	public String getOpenId() {
		return openId;
	}

	public String getTransId() {
		return transId;
	}

	public String getReason() {
		return reason;
	}

	public String getSolution() {
		return solution;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public String getPicInfo() {
		return picInfo;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "PayFeedback [feedbackId=" + feedbackId + ", openId=" + openId
				+ ", transId=" + transId + ", reason=" + reason + ", solution="
				+ solution + ", extInfo=" + extInfo + ", picInfo=" + picInfo
				+ ", status=" + status + ", " + super.toString() + "]";
	}
}