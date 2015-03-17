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

	private int agentid; // 企业应用的id
	@JSONField(name = "report_location_flag")
	private ReportLocationType reportLocationType; // 企业应用是否打开地理位置上报
	@JSONField(name = "logo_mediaid")
	private String logoMediaid; // 企业应用头像的mediaid，通过多媒体接口上传图片获得mediaid，上传后会自动裁剪成方形和圆形两个头像
	private String name; // 企业应用名称
	private String description; // 企业应用详情
	@JSONField(name = "redirect_domain")
	private String redirectDomain; // 企业应用可信域名
	@JSONField(name = "isreportuser")
	private int isReportUser; // 是否接收用户变更通知。0：不接收；1：接收
	@JSONField(name = "isreportenter")
	private int isReportEnter; // 是否上报用户进入应用事件。0：不接收；1：接收

	public AgentSetter(int agentid) {
		this.agentid = agentid;
	}

	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public ReportLocationType getReportLocationType() {
		return reportLocationType;
	}

	public void setReportLocationType(ReportLocationType reportLocationType) {
		this.reportLocationType = reportLocationType;
	}

	public String getLogoMediaid() {
		return logoMediaid;
	}

	public void setLogoMediaid(String logoMediaid) {
		this.logoMediaid = logoMediaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRedirectDomain() {
		return redirectDomain;
	}

	public void setRedirectDomain(String redirectDomain) {
		this.redirectDomain = redirectDomain;
	}

	public boolean getIsReportUser() {
		return isReportUser == 1;
	}

	public void setIsReportUser(boolean isReportUser) {
		this.isReportUser = isReportUser ? 1 : 0;
	}

	public boolean getIsReportEnter() {
		return isReportEnter == 1;
	}

	public void setIsReportEnter(boolean isReportEnter) {
		this.isReportEnter = isReportEnter ? 1 : 0;
	}

	@Override
	public String toString() {
		return "agentid=" + agentid + ", reportLocationType="
				+ reportLocationType + ", logoMediaid=" + logoMediaid
				+ ", name=" + name + ", description=" + description
				+ ", redirectDomain=" + redirectDomain + ", isReportUser="
				+ isReportUser + ", isReportEnter=" + isReportEnter;
	}
}
