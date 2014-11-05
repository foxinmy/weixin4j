package com.foxinmy.weixin4j.mp.payment.v2;

import com.foxinmy.weixin4j.mp.payment.PayBaseInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 维权POST的数据
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

	@XStreamAlias("FeedBackId")
	private String feedbackId;
	@XStreamAlias("OpenId")
	private String openId;
	@XStreamAlias("TransId")
	private String transId;
	@XStreamAlias("Reason")
	private String reason;
	@XStreamAlias("Solution")
	private String solution;
	@XStreamAlias("ExtInfo")
	private String extInfo;
	@XStreamAlias("PicInfo")
	private String picInfo;
	@XStreamAlias("MsgType")
	private String status;

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(String picInfo) {
		this.picInfo = picInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PayFeedback [feedbackId=" + feedbackId + ", openId=" + openId
				+ ", transId=" + transId + ", reason=" + reason + ", solution="
				+ solution + ", extInfo=" + extInfo + ", picInfo=" + picInfo
				+ ", status=" + status + ", getAppId()=" + getAppId()
				+ ", getTimeStamp()=" + getTimeStamp() + ", getNonceStr()="
				+ getNonceStr() + ", getPaySign()=" + getPaySign()
				+ ", getSignType()=" + getSignType() + "]";
	}
}