package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.qy.type.CorpType;
import com.foxinmy.weixin4j.qy.type.CorporateType;

/**
 * 授权方企业号信息
 * 
 * @className CorpInfo
 * @author jy
 * @date 2015年6月12日
 * @since JDK 1.6
 * @see
 */
public class CorpInfo implements Serializable {

	private static final long serialVersionUID = 1251033124778972419L;
	/**
	 * 授权方企业号id
	 */
	@JSONField(name = "corpid")
	private String corpId;
	/**
	 * 授权方企业号名称
	 */
	@JSONField(name = "corp_name")
	private String corpName;
	/**
	 * 企业方形头像
	 */
	@JSONField(name = "corp_square_logo_url")
	private String squareLogoUrl;
	/**
	 * 企业圆形头像
	 */
	@JSONField(name = "corp_round_logo_url")
	private String roundLogoUrl;
	/**
	 * 授权方企业号类型
	 */
	@JSONField(name = "corp_type")
	private String corpType;
	/**
	 * 授权方企业号用户规模
	 */
	@JSONField(name = "corp_user_max")
	private Integer userMax;
	/**
	 * 授权方企业号应用规模
	 */
	@JSONField(name = "corp_agent_max")
	private Integer agentMax;
	/**
	 * 授权方企业号二维码
	 */
	@JSONField(name = "corp_wxqrcode")
	private String wxQrCode;
	/**
	 * 所绑定的企业号主体名称
	 */
	@JSONField(name = "corp_full_name")
	private String fullName;
	/**
	 * 认证到期时间
	 */
	@JSONField(name = "verified_end_time")
	private long verifiedEndTime;
	/**
	 * 企业类型
	 */
	@JSONField(name = "subject_type")
	private int corporateType;

	public String getCorpId() {
		return corpId;
	}

	public String getCorpType() {
		return corpType;
	}

	@JSONField(serialize = false)
	public CorpType getFormatCorpType() {
		return corpType != null ? CorpType.valueOf(corpType) : null;
	}

	public String getCorpName() {
		return corpName;
	}

	public String getSquareLogoUrl() {
		return squareLogoUrl;
	}

	public String getRoundLogoUrl() {
		return roundLogoUrl;
	}

	public Integer getUserMax() {
		return userMax;
	}

	public Integer getAgentMax() {
		return agentMax;
	}

	public String getWxQrCode() {
		return wxQrCode;
	}

	public String getFullName() {
		return fullName;
	}

	public long getVerifiedEndTime() {
		return verifiedEndTime;
	}

	@JSONField(serialize = false)
	public Date getFormatVerifiedEndTime() {
		return verifiedEndTime > 0l ? new Date(verifiedEndTime * 1000l) : null;
	}

	public int getCorporateType() {
		return corporateType;
	}

	@JSONField(serialize = false)
	public CorporateType getFormatCorporateType() {
		return corporateType > 0
				&& corporateType <= CorporateType.values().length ? CorporateType
				.values()[corporateType - 1] : null;
	}

	// ---------- setter 应该全部去掉

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public void setSquareLogoUrl(String squareLogoUrl) {
		this.squareLogoUrl = squareLogoUrl;
	}

	public void setRoundLogoUrl(String roundLogoUrl) {
		this.roundLogoUrl = roundLogoUrl;
	}

	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}

	public void setUserMax(Integer userMax) {
		this.userMax = userMax;
	}

	public void setAgentMax(Integer agentMax) {
		this.agentMax = agentMax;
	}

	public void setWxQrCode(String wxQrCode) {
		this.wxQrCode = wxQrCode;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setVerifiedEndTime(long verifiedEndTime) {
		this.verifiedEndTime = verifiedEndTime;
	}

	public void setCorporateType(int corporateType) {
		this.corporateType = corporateType;
	}

	@Override
	public String toString() {
		return "CorpInfo [corpId=" + corpId + ", corpName=" + corpName
				+ ", squareLogoUrl=" + squareLogoUrl + ", roundLogoUrl="
				+ roundLogoUrl + ", corpType=" + corpType + ", userMax="
				+ userMax + ", agentMax=" + agentMax + ", wxQrCode=" + wxQrCode
				+ ", fullName=" + fullName + ", verifiedEndTime="
				+ verifiedEndTime + ", corporateType=" + corporateType + "]";
	}
}