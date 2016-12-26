package com.foxinmy.weixin4j.mp.model.shakearound;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 摇一摇设备信息。device_id 和 uuid  major minor 可以2者选一
 * @auther: Feng Yapeng
 * @since: 2016/10/13 9:59
 */
public class Device {

    /**
     * 设备编号
     */
    @JSONField(name = "device_id")
    private Integer deviceId;


    private String uuid;

    private Integer major;

    private Integer minor;

    /**
     * 激活状态，0：未激活，1：已激活
     */
    private int status;

    /**
     * 设备最近一次被摇到的日期（最早只能获取前一天的数据）；新申请的设备该字段值为0
     */
    @JSONField(name = "last_active_time")
    private String lastActiveTime;

    /**
     * 若配置了设备与其他公众账号门店关联关系，则返回配置门店归属的公众账号appid。
     * <a href=
     * "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459246251">查看配置设备与其他公众账号门店关联关系接口</a>
     */
    @JSONField(name = "poi_appid")
    private String poiAppId;

    /**
     * 设备关联的门店ID，关联门店后，在门店1KM的范围内有优先摇出信息的机会。
     * <a href="https://mp.weixin.qq.com/zh_CN/htmledition/comm_htmledition/res/store_manage/store_manage_file.zip"></a>门店相关信息具体可查看门店相关的接口文档
     */
    @JSONField(name = "poi_id")
    private String poiId;

    /**
     * 设备的备注信息
     */
    private String comment;


    public Device() {
    }

    /**
     *
     * @param deviceId
     */
    public Device(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Device(String uuid, Integer major, Integer minor) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(String lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getPoiAppId() {
        return poiAppId;
    }

    public void setPoiAppId(String poiAppId) {
        this.poiAppId = poiAppId;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
