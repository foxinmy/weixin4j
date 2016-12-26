package com.foxinmy.weixin4j.type.card;

/**
 * 会员卡的状态
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/19 11:39
 */
public enum CardStatus {

    /**
     * 待审核；
     */
    CARD_STATUS_NOT_VERIFY,

    /**
     * 审核失败
     */
    CARD_STATUS_VERIFY_FAIL,

    /**
     * 通过审核
     */
    CARD_STATUS_VERIFY_OK,

    /**
     * 卡券被商户删除
     */
    CARD_STATUS_DELETE,

    /**
     * 在公众平台投放过的卡券
     */
    CARD_STATUS_DISPATCH;

}
