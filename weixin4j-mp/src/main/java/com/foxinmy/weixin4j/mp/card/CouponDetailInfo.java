package com.foxinmy.weixin4j.mp.card;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 卡券详情信息
 *
 * @className CouponDetailInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年6月1日
 * @since JDK 1.6
 * @see
 */
public interface CouponDetailInfo extends Serializable {
	public JSONObject toContent();
}
