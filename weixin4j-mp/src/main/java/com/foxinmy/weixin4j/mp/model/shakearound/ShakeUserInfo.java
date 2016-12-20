package com.foxinmy.weixin4j.mp.model.shakearound;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取设备信息，包括UUID、major、minor，以及距离、openID等信息。
 *
 * @author fengyapeng
 * @auther: Feng Yapeng
 * @since: 2016 /10/21 19:21
 * @since 2016 -10-21 19:21:59
 */
public class ShakeUserInfo {


    @JSONField(name = "page_id")
    private Long   pageId;
    @JSONField(name = "beacon_info")
    private Device device;

    @JSONField(name = "poi_id")
    private Long poiId;

    @JSONField(name = "openid")
    private String openId;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
