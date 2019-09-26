package com.foxinmy.weixin4j.pay.payment.face;

import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.pay.type.SignType;
import com.foxinmy.weixin4j.util.*;
import com.foxinmy.weixin4j.xml.XmlStream;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信刷脸支付， 获取调用凭证(get_wxpayface_authinfo)API接口请求参数封装
 *
 * @className PayfaceAuthinfoRequest
 * @author kit(kit_21cn@21cn.com)
 * @date 2019年9月18日
 * @since JDK 1.6
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/wxfacepay/develop/sdk-android.html#获取调用凭证-get-wxpayface-authinfo">
 *     获取调用凭证-get-wxpayface-authinfo</a>
 */
public class PayfaceAuthinfoRequest {
    private WeixinPayAccount payAccount;
    /**
     * 门店编号， 由商户定义， 各门店唯一。
     */
    private String storeId;
    /**
     * 门店名称，由商户定义。（可用于展示）
     */
    private String storeName;
    /**
     * 终端设备编号，由商户定义。
     */
    private String deviceId;
    /**
     * 附加字段。字段格式使用Json
     */
    private String attach;

    private String nonceStr = RandomUtil.generateString(16);

    private String now = DateUtil.timestamp2string();
    /**
     * 初始化数据。由微信人脸SDK的接口返回。
     *
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/wxfacepay/develop/sdk-android.html#获取数据-getwxpayfacerawdata">
     *  *     获取数据-getwxpayfacerawdata</a>
     */
    private String rawdata;

    private WeixinPaymentSignature paymentSignature;

    public PayfaceAuthinfoRequest(WeixinPayAccount account, String storeId, String storeName, String deviceId,
                                  String attach, String rawdata){
        this.payAccount = account;
        this.deviceId = deviceId;
        this.rawdata = rawdata;
        this.storeId = storeId;
        this.storeName = storeName;
        this.attach = attach;
        this.paymentSignature = new WeixinPaymentSignature(account.getPaySignKey());
    }

    public String toRequestString(){
        Map paramsMap = getRequestParam();
        return XmlStream.map2xml(paramsMap);
    }

    private Map<String, String> getRequestParam(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", payAccount.getId());
        map.put("mch_id", payAccount.getMchId());
        if(StringUtil.isNotBlank(payAccount.getSubId())) {
            map.put("sub_appid", payAccount.getSubId());
        }
        if(StringUtil.isNotBlank(payAccount.getSubMchId())){
            map.put("sub_mch_id", payAccount.getSubMchId());
        }
        map.put("now", now);
        map.put("version", "1");
        map.put("sign_type", SignType.MD5.name());
        map.put("nonce_str", nonceStr);
        map.put("store_id", storeId);
        map.put("store_name", storeName);
        map.put("device_id", deviceId);
        map.put("rawdata", rawdata);
        if(StringUtil.isNotBlank(attach)) {
            map.put("attach", attach);
        }
        String sign = paymentSignature.sign(map);
        map.put("sign", sign);

        return map;
    }
}
