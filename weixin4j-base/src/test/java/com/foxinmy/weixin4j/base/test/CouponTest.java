package com.foxinmy.weixin4j.base.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.payment.coupon.CouponDetail;
import com.foxinmy.weixin4j.payment.coupon.CouponResult;
import com.foxinmy.weixin4j.payment.coupon.CouponStock;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 代金券测试
 *
 * @className CouponTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月25日
 * @since JDK 1.6
 * @see
 */
public class CouponTest extends PayTest {

	@Test
	public void sendCoupon() throws WeixinException {
		String partnerTradeNo = String.format("%s%s%s", ACCOUNT.getMchId(),
				DateUtil.fortmat2yyyyMMdd(new Date()), "1");
		CouponResult result = PAY.sendCoupon("123", partnerTradeNo,
				"oyFLst1bqtuTcxK-ojF8hOGtLQao", null);
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}

	@Test
	public void queryCouponStock() throws WeixinException {
		CouponStock result = PAY.queryCouponStock("couponStockId");
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}

	@Test
	public void queryCouponDetail() throws WeixinException {
		CouponDetail result = PAY.queryCouponDetail("openId", "couponId",
				"stockId");
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}
}
