package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.qy.type.CorpType;

/**
 * 授权方企业号信息
 * 
 * @className CorpInfo
 * @author jy
 * @date 2015年6月12日
 * @since JDK 1.7
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

	@Override
	public String toString() {
		return "CorpInfo [corpType=" + corpId + ", corpName=" + corpName
				+ ", squareLogoUrl=" + squareLogoUrl + ", roundLogoUrl="
				+ roundLogoUrl + ", corpType=" + corpType + ", userMax="
				+ userMax + ", agentMax=" + agentMax + ", wxQrCode=" + wxQrCode
				+ "]";
	}
}