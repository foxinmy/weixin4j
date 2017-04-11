package com.foxinmy.weixin4j.type;

/**
 * 微信支付类型
 *
 * @className TradeType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月21日
 * @since JDK 1.6
 * @see
 */
public enum TradeType {
    /**
     * JS支付
     */
    JSAPI,
    /**
     * 刷卡支付
     */
    MICROPAY,
    /**
     * 扫码支付
     */
    NATIVE,
    /**
     * APP支付
     */
    APP,
    /**
     * WAP支付
     */
    MWEB;
}
