package com.foxinmy.weixin4j.type.card;

/**
 * 用户的会员卡状态
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/21 11:42
 */
public enum UserCardStatus {

    NORMAL,//正常
    EXPIRE,//已过期
    GIFTING,// 转赠中
    GIFT_SUCC,// 转赠成功
    GIFT_TIMEOUT,// 转赠超时
    DELETE,//已删除
    UNAVAILABLE,//已失效
    ;
}
