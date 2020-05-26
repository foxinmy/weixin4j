package com.foxinmy.weixin4j.pay.type.profitsharing;

/**
 * 分帐接收方类型
 *
 * @author kit (kit.li@qq.com)
 * @date 2020年05月20日
 * @since weixin4j-pay 1.1.0
 */
public enum ReceiverType {
    /**
     * 商户ID
     */
    MERCHANT_ID,
    /**
     * 个人微信号
     */
    PERSONAL_WECHATID,
    /**
     * 个人微信openid
     */
    PERSONAL_OPENID,
    /**
     * 个人微信sub_openid (对应服务商API的子商户sub_appid转换得到)
     */
    PERSONAL_SUB_OPENID;
}
