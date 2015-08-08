package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.qy.type.ReportLocationType;

/**
 * 设置企业号应用
 * 
 * @className AgentSetter
 * @author jy
 * @date 2015年3月16日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%AE%BE%E7%BD%AE%E4%BC%81%E4%B8%9A%E5%8F%B7%E5%BA%94%E7%94%A8">设置企业号应用</a>
 */
public class AgentSetter implements Serializable {
	private static final long serialVersionUID = 5420335232308079801L;

	/**
	 * 企业应用的id
	 */
	@JSONField(name = "agentid")
	private int agentId;
	/**
	 * 企业应用是否打开地理位置上报
	 */
	@JSONField(name = "report_location_flag")
	private ReportLocationType reportLocationType;
	/**
	 * 企业应用头像的mediaid，通过多媒体接口上传图片获得mediaid，上传后会自动裁剪成方形和圆形两个头像
	 */
	@JSONField(name = "logo_mediaid")
	private String logoMediaId;
	/**
	 * 企业应用名称
	 */
	private String name;
	/**
	 * 企业应用详情
	 */
	private String description;
	/**
	 * 企业应用可信域名
	 */
	@JSONField(name = "redirect_domain")
	private String redirectDomain;
	/**
	 * 是否接收用户变更通知。0：不接收；1：接收
	 */
	@JSONField(name = "isreportuser")
	private boolean isReportUser;
	/**
	 * 是否上报用户进入应用事件。0：不接收；1：接收
	 */
	@JSONField(name = "isreportenter")
	private boolean isReportEnter;

	public AgentSetter(int agentId) {
		this.agentId = agentId;
	}

	public int getAgentId() {
		return agentId;
	}

	public ReportLocationType getReportLocationType() {
		return reportLocationType;
	}

	public String getLogoMediaId() {
		return logoMediaId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getRedirectDomain() {
		return redirectDomain;
	}

	// ---------- setter 应该全部去掉

	public void setReportLocationType(ReportLocationType reportLocationType) {
		this.reportLocationType = reportLocationType;
	}

	public void setLogoMediaid(String logoMediaId) {
		this.logoMediaId = logoMediaId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRedirectDomain(String redirectDomain) {
		this.redirectDomain = redirectDomain;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public void setLogoMediaId(String logoMediaId) {
		this.logoMediaId = logoMediaId;
	}

	public void setReportUser(boolean isReportUser) {
		this.isReportUser = isReportUser;
	}

	public void setReportEnter(boolean isReportEnter) {
		this.isReportEnter = isReportEnter;
	}

	@Override
	public String toString() {
		return "agentId=" + agentId + ", reportLocationType="
				+ reportLocationType + ", logoMediaId=" + logoMediaId
				+ ", name=" + name + ", description=" + description
				+ ", redirectDomain=" + redirectDomain + ", isReportUser="
				+ isReportUser + ", isReportEnter=" + isReportEnter;
	}
}
