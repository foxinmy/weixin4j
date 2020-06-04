package com.foxinmy.weixin4j.pay.type.profitsharing;

/**
 * 子商户与接收方的关系
 *
 * @author kit (kit.li@qq.com)
 * @date 2020年05月20日
 * @since weixin4j-pay 1.1.0
 */
public enum RelationType {
    /**
     * 服务商
     */
    SERVICE_PROVIDER,
    /**
     * 门店
     */
    STORE,
    /**
     * 员工
     */
    STAFF,
    /**
     * 店主
     */
    STORE_OWNER,
    /**
     * 合作伙伴
     */
    PARTNER,
    /**
     * 总部
     */
    HEADQUARTER,
    /**
     * 品牌方
     */
    BRAND,
    /**
     * 分销商
     */
    DISTRIBUTOR,
    /**
     * 用户
     */
    USER,
    /**
     * 供应商
     */
    SUPPLIER,
    /**
     * 自定义
     */
    CUSTOM;
}
