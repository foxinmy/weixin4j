package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.paging.Pagedata;
import com.foxinmy.weixin4j.mp.model.shakearound.Device;
import com.foxinmy.weixin4j.mp.model.shakearound.DeviceAuditState;
import com.foxinmy.weixin4j.mp.model.shakearound.ShakeUserInfo;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 摇一摇周边
 *
 * @author fengyapeng
 * @auther: Feng Yapeng feng27156@gmail.com
 * @since: 2016 /10/12 21:13
 * @since 2016 -10-13 10:49:39
 */
public class ShakeAroundApi extends MpApi {


    private final TokenManager tokenManager;


    /**
     * Instantiates a new Shake around api.
     *
     * @param tokenManager the token manager
     */
    public ShakeAroundApi(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 申请配置设备所需的UUID、Major、Minor。申请成功后返回批次ID，可用返回的批次ID通过“查询设备ID申请状态”接口查询目前申请的审核状态。
     * 若单次申请的设备ID数量小于500个，系统会进行快速审核；若单次申请的设备ID数量大于等 500个 ，会在三个工作日内完成审核。
     * 如果已审核通过，可用返回的批次ID通过“查询设备列表”接口拉取本次申请的设备ID。 通过接口申请的设备ID，需先配置页面，若未配置页面，则摇不出页面信息。
     *
     * @param quantity    the quantity  申请的设备ID的数量，单次新增设备超过500个，需走人工审核流程
     * @param applyReason the apply reason 申请理由，不超过100个汉字或200个英文字母
     * @param comment     the comment 备注，不超过15个汉字或30个英文字母
     * @return the api result
     * @throws WeixinException the weixin exception
     * @author fengyapeng
     * @see <a href= "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459246241&token=&lang=zh_CN"></a>
     * @since 2016 -10-12 21:21:47
     */
    public DeviceAuditState deviceApply(Integer quantity, String applyReason, String comment) throws WeixinException {
        String device_apply_uri = getRequestUri("shake_around_device_apply");
        Token token = this.tokenManager.getCache();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", quantity);
        jsonObject.put("apply_reason", applyReason);
        jsonObject.put("comment", comment);
        WeixinResponse response = weixinExecutor.post(String.format(device_apply_uri, token.getAccessToken()), jsonObject.toJSONString());
        DeviceAuditState result = JSON.parseObject(response.getAsJson().getString("data"), DeviceAuditState.class);
        result.setApplyTime(System.currentTimeMillis() / 1000);
        result.setAuditTime(0);
        return result;
    }


    /**
     * 查询设备ID申请的审核状态。若单次申请的设备ID数量小于等于500个，
     * 系统会进行快速审核；若单次申请的设备ID数量大于500个，则在三个工作日内完成审核。
     *
     * @param applyId the apply id 批次ID，申请设备ID时所返回的批次ID
     * @return the device audit state
     * @throws WeixinException the weixin exception
     * @author fengyapeng
     * @see <a href= "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459246243&token=&lang=zh_CN"></a>
     * @since 2016 -10-12 21:57:04
     */
    public DeviceAuditState deviceQueryApplyStatus(int applyId) throws WeixinException {
        String device_apply_status_uri = getRequestUri("shake_around_device_apply_status_uri");
        Token token = this.tokenManager.getCache();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("apply_id", applyId);
        WeixinResponse response = weixinExecutor
                .post(String.format(device_apply_status_uri, token.getAccessToken()), jsonObject.toJSONString());
        DeviceAuditState result = JSON.parseObject(response.getAsJson().getString("data"), DeviceAuditState.class);
        result.setApplyId(applyId);
        return result;
    }

    /**
     * 查询已有的设备ID、UUID、Major、Minor、激活状态、备注信息、关联门店、关联页面等信息。
     * 查询指定设备的信息
     *
     * @param device the device
     * @return the list
     * @throws WeixinException the weixin exception
     * @author fengyapeng
     * @since 2016 -10-13 10:11:34
     */
    public List<Device> deviceSearchDevices(List<Device> device) throws WeixinException {
        String device_search_uri = getRequestUri("shake_around_device_search_uri");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", 1);
        jsonObject.put("device_identifiers", device);
        WeixinResponse response = weixinExecutor
                .post(String.format(device_search_uri, tokenManager.getAccessToken()), jsonObject.toJSONString());
        JSONObject json = response.getAsJson();
        String deviceStr = json.getJSONObject("data").getString("devices");
        return JSON.parseArray(deviceStr, Device.class);
    }

    /**
     * 查询已有的设备ID、UUID、Major、Minor、激活状态、备注信息、关联门店、关联页面等信息。
     * 按照分页信息查询设备
     *
     * @return the list
     * @throws WeixinException the weixin exception
     * @author fengyapeng
     * @since 2016 -10-13 10:11:34
     */
    public Pagedata<Device> deviceSearchDevices(int pageSize) throws WeixinException {
        return this.deviceSearchDevices(0, pageSize);
    }

    /**
     * 查询已有的设备ID、UUID、Major、Minor、激活状态、备注信息、关联门店、关联页面等信息
     * 根据上次查询的最后的设备编号按照分页查询
     *
     * @param lastDeviceId the last device id
     * @param pageSize     the page size
     * @return list pagedata
     * @throws WeixinException the weixin exception
     * @author fengyapeng
     * @since 2016 -10-13 10:52:20
     */
    public Pagedata<Device> deviceSearchDevices(int lastDeviceId, int pageSize) throws WeixinException {
        String device_search_uri = getRequestUri("shake_around_device_search_uri");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", 2);
        jsonObject.put("last_seen", lastDeviceId);
        if (pageSize > 50) {
            pageSize = 50;
        }
        jsonObject.put("count", pageSize);
        WeixinResponse response = weixinExecutor
                .post(String.format(device_search_uri, tokenManager.getAccessToken()), jsonObject.toJSONString());
        JSONObject json = response.getAsJson();
        JSONObject data = json.getJSONObject("data");
        String deviceStr = data.getString("devices");
        List<Device> devices = JSON.parseArray(deviceStr, Device.class);
        Pagedata<Device> pagedata = new Pagedata<Device>(null, data.getIntValue("total_count"), devices);
        return pagedata;
    }

    /**
     * 查询已有的设备ID、UUID、Major、Minor、激活状态、备注信息、关联门店、关联页面等信息
     * 查询 设备Id 下的所有的设备
     *
     * @param applyId the apply id
     * @return the list
     * @author fengyapeng
     * @since 2016 -10-13 10:49:39
     */
    public List<Device> deviceSearchDevicesByApplyId(Integer applyId) throws WeixinException {
        List<Device> devices = new ArrayList<Device>();
        Pagedata<Device> pagedata = this.deviceSearchDevicesByApplyId(applyId, 50);
        devices = pagedata.getContent();
        for (int page = 50; page < pagedata.getTotalElements(); page = page + 50) {
            List<Device> _devices = pagedata.getContent();
            pagedata = this.deviceSearchDevicesByApplyId(applyId, _devices.get(_devices.size() - 1).getDeviceId(), 50);
            _devices = pagedata.getContent();
            devices.addAll(_devices);
        }
        return devices;
    }


    /**
     * 查询已有的设备ID、UUID、Major、Minor、激活状态、备注信息、关联门店、关联页面等信息
     * 分页获取设备id下的前多少设备
     *
     * @param applyId the apply id
     * @return the list
     * @author fengyapeng
     * @since 2016 -10-13 10:49:39
     */
    public Pagedata<Device> deviceSearchDevicesByApplyId(Integer applyId, int pageSize) throws WeixinException {
        return this.deviceSearchDevicesByApplyId(applyId, 0, pageSize);
    }

    /**
     * 查询已有的设备ID、UUID、Major、Minor、激活状态、备注信息、关联门店、关联页面等信息
     * 分页获取设备id下的根据上次查询的最后的设备编号前多少设备
     *
     * @param applyId the apply id
     * @return the list
     * @author fengyapeng
     * @since 2016 -10-13 10:49:39
     */
    public Pagedata<Device> deviceSearchDevicesByApplyId(Integer applyId, int lastDeviceId, int pageSize) throws WeixinException {
        String device_search_uri = getRequestUri("shake_around_device_search_uri");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", 3);
        jsonObject.put("apply_id", applyId);
        jsonObject.put("last_seen", lastDeviceId);
        if (pageSize > 50) {
            pageSize = 50;
        }
        jsonObject.put("count", pageSize);
        WeixinResponse response = weixinExecutor
                .post(String.format(device_search_uri, tokenManager.getAccessToken()), jsonObject.toJSONString());
        JSONObject json = response.getAsJson();
        JSONObject data = json.getJSONObject("data");
        String deviceStr = data.getString("devices");
        List<Device> devices = JSON.parseArray(deviceStr, Device.class);
        Pagedata<Device> pagedata = new Pagedata<Device>(null, data.getIntValue("total_count"), devices);
        return pagedata;
    }


    /**
     * 编辑设备的备注信息。可用设备ID或完整的UUID、Major、Minor指定设备，二者选其一。
     *
     * @param device  the device
     * @param comment the comment
     * @return api result
     * @author fengyapeng
     * @since 2016 -10-13 14:33:06
     */
    public ApiResult deviceUpdateComment(Device device, String comment) throws WeixinException {
        String device_update_uri = getRequestUri("shake_around_device_update_uri");
        JSONObject jsonObject = new JSONObject();
        JSONObject deviceJsonObj = new JSONObject();
        jsonObject.put("device_identifier", deviceJsonObj);
        jsonObject.put("comment", comment);
        if (device.getDeviceId() == null) {
            deviceJsonObj.put("uuid", device.getUuid());
            deviceJsonObj.put("major", device.getMajor());
            deviceJsonObj.put("minor", device.getMinor());
        } else {
            deviceJsonObj.put("device_id", device.getDeviceId());
        }
        WeixinResponse weixinResponse = weixinExecutor
                .post(String.format(device_update_uri, tokenManager.getAccessToken()), jsonObject.toJSONString());
        return weixinResponse.getAsResult();

    }

    /**
     * 获取设备信息，包括UUID、major、minor，以及距离、openID等信息.
     *
     *
     * <a herf="
     * http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443447963&token=&lang=zh_CN"></a>
     *
     * @param ticket     the ticket 摇周边业务的ticket，可在摇到的URL中得到，ticket生效时间为30分钟，每一次摇都会重新生成新的ticket
     * @return shake user info
     * @author fengyapeng
     * @since 2016 -10-21 19:34:38
     */
    public ShakeUserInfo getShakeUserInfo(String ticket) throws WeixinException {
        String user_get_shake_info_url = getRequestUri("shake_around_user_get_shake_info");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ticket", ticket);
        WeixinResponse weixinResponse = weixinExecutor
                .post(String.format(user_get_shake_info_url, tokenManager.getAccessToken()), jsonObject.toJSONString());
        return weixinResponse.getAsJson().getObject("data", ShakeUserInfo.class);

    }
}
