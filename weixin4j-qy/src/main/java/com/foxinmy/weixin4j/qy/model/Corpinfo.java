package com.foxinmy.weixin4j.qy.model;

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
public class Corpinfo extends AgentOverview {

	private static final long serialVersionUID = 1251033124778972419L;
	/**
	 * 授权方企业号id
	 */
	private String corpid;
	/**
	 * 授权方企业号类型
	 */
	@JSONField(name = "corp_type")
	private CorpType corpType;
	/**
	 * 授权方企业号用户规模
	 */
	@JSONField(name = "corp_user_max")
	private int corpUserMax;
	/**
	 * 授权方企业号应用规模
	 */
	@JSONField(name = "corp_agent_max")
	private int corpAgentMax;
	/**
	 * 授权方企业号二维码
	 */
	@JSONField(name = "corp_wxqrcode")
	private String corpWxqrcode;

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

	public int getCorpUserMax() {
		return corpUserMax;
	}

	public void setCorpUserMax(int corpUserMax) {
		this.corpUserMax = corpUserMax;
	}

	public int getCorpAgentMax() {
		return corpAgentMax;
	}

	public void setCorpAgentMax(int corpAgentMax) {
		this.corpAgentMax = corpAgentMax;
	}

	public String getCorpWxqrcode() {
		return corpWxqrcode;
	}

	public void setCorpWxqrcode(String corpWxqrcode) {
		this.corpWxqrcode = corpWxqrcode;
	}

	@Override
	public String toString() {
		return "Corpinfo [corpid=" + corpid + ", corpType=" + corpType
				+ ", corpUserMax=" + corpUserMax + ", corpAgentMax="
				+ corpAgentMax + ", corpWxqrcode=" + corpWxqrcode + ", "
				+ super.toString() + "]";
	}
}
