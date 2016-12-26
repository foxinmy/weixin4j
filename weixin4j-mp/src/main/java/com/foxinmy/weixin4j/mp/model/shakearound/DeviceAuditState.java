package com.foxinmy.weixin4j.mp.model.shakearound;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 摇一摇周边设备审核信息
 *
 * @auther: Feng Yapeng
 * @since: 2016/10/12 21:47
 */
public class DeviceAuditState {

    /**
     * 申请设备ID时所返回的批次ID
     */
    @JSONField(name = "apply_id")
    private Integer applyId;
    /**
     * 审核状态。0：审核未通过、1：审核中、2：审核已通过；
     * 若单次申请的设备ID数量小于等于500个，系统会进行快速审核；
     * 若单次申请的设备ID数量大于500个，会在三个工作日内完成审核；
     */
    @JSONField(name = "audit_status")
    private String  auditStatus;

    /**
     * 审核备注，对审核状态的文字说明
     */
    @JSONField(name = "audit_comment")
    private String auditComment;
    /**
     * 提交申请的时间戳
     */
    @JSONField(name = "apply_time")
    private long applyTime;

    /**
     * 确定审核结果的时间戳，若状态为审核中，则该时间值为0
     */
    @JSONField(name = "audit_time")
    private long auditTime;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(long applyTime) {
        this.applyTime = applyTime;
    }

    public long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(long auditTime) {
        this.auditTime = auditTime;
    }
}
