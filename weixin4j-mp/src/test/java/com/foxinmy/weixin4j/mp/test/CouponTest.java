package com.foxinmy.weixin4j.mp.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.payment.coupon.CouponDetail;
import com.foxinmy.weixin4j.payment.coupon.CouponResult;
import com.foxinmy.weixin4j.payment.coupon.CouponStock;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 代金券测试
 * 
 * @className CouponTest
 * @author jy
 * @date 2015年3月25日
 * @since JDK 1.7
 * @see
 */
public class CouponTest extends PayTest {

	@Test
	public void sendCoupon() throws WeixinException, IOException {
		String partnerTradeNo = String.format("%s%s%s", ACCOUNT3.getMchId(),
				DateUtil.fortmat2yyyyMMdd(new Date()), "1");
		CouponResult result = PAY3.sendCoupon(new FileInputStream(caFile),
				"123", partnerTradeNo, "oyFLst1bqtuTcxK-ojF8hOGtLQao", null);
		Assert.assertTrue(result.getRetCode().equalsIgnoreCase(Consts.SUCCESS));
	}

	@Test
	public void queryCouponStock() throws WeixinException {
		CouponStock couponStock = PAY3.queryCouponStock("couponStockId");
		System.err.println(couponStock);
	}

	@Test
	public void queryCouponDetail() throws WeixinException {
		CouponDetail couponDetail = PAY3.queryCouponDetail("couponId");
		System.err.println(couponDetail);
	}
}
