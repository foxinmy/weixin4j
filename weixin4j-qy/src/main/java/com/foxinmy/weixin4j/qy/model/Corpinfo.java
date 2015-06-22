package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.qy.type.CorpType;

/**
 * 授权方企业号信息
 * 
 * @className Corpinfo
 * @author jy
 * @date 2015年6月12日
 * @since JDK 1.7
 * @see
 */
public class Corpinfo implements Serializable {

	private static final long serialVersionUID = 1251033124778972419L;
	/**
	 * 授权方企业号id
	 */
	private String corpid;
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
	private CorpType corpType;
	/**
	 * 授权方企业号用户规模
	 */
	@JSONField(name = "corp_user_max")
	private int userMax;
	/**
	 * 授权方企业号应用规模
	 */
	@JSONField(name = "corp_agent_max")
	private int agentMax;
	/**
	 * 授权方企业号二维码
	 */
	@JSONField(name = "corp_wxqrcode")
	private String wxqrcode;

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public CorpType getCorpType() {
		return corpType;
	}

	public void setCorpType(CorpType corpType) {
		this.corpType = corpType;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getSquareLogoUrl() {
		return squareLogoUrl;
	}

	public void setSquareLogoUrl(String squareLogoUrl) {
		this.squareLogoUrl = squareLogoUrl;
	}

	public String getRoundLogoUrl() {
		return roundLogoUrl;
	}

	public void setRoundLogoUrl(String roundLogoUrl) {
		this.roundLogoUrl = roundLogoUrl;
	}

	public int getUserMax() {
		return userMax;
	}

	public void setUserMax(int userMax) {
		this.userMax = userMax;
	}

	public int getAgentMax() {
		return agentMax;
	}

	public void setAgentMax(int agentMax) {
		this.agentMax = agentMax;
	}

	public String getWxqrcode() {
		return wxqrcode;
	}

	public void setWxqrcode(String wxqrcode) {
		this.wxqrcode = wxqrcode;
	}

	@Override
	public String toString() {
		return "Corpinfo [corpid=" + corpid + ", corpName=" + corpName
				+ ", squareLogoUrl=" + squareLogoUrl + ", roundLogoUrl="
				+ roundLogoUrl + ", corpType=" + corpType + ", userMax="
				+ userMax + ", agentMax=" + agentMax + ", wxqrcode=" + wxqrcode
				+ "]";
	}
}
