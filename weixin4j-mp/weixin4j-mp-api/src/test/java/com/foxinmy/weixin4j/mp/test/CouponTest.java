package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.WeixinPayProxy;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponDetail;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponResult;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponStock;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.type.AccountType;
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
public class CouponTest {
	protected final static WeixinPayProxy WEIXINPAY;
	protected final static WeixinMpAccount ACCOUNT;
	static {
		ACCOUNT = new WeixinMpAccount("公众号的id",
				"公众号的secret",
				"公众号的支付密钥", "商户平台的id");
		WEIXINPAY = new WeixinPayProxy(ACCOUNT, new FileTokenHolder(
				new WeixinTokenCreator(ACCOUNT.getId(), ACCOUNT.getSecret(),
						AccountType.MP)));
	}
	protected final File caFile = new File(
			"证书文件的路径(*.p12)");

	@Test
	public void sendCoupon() throws WeixinException {
		String partnerTradeNo = String.format("%s%s%s", ACCOUNT.getMchId(),
				DateUtil.fortmat2yyyyMMdd(new Date()), "1");
		CouponResult result = WEIXINPAY.sendCoupon(caFile, "123",
				partnerTradeNo, "oyFLst1bqtuTcxK-ojF8hOGtLQao", null);
		Assert.assertTrue(result.getRetCode().equalsIgnoreCase(Consts.SUCCESS));
	}

	@Test
	public void queryCouponStock() throws WeixinException {
		CouponStock couponStock = WEIXINPAY.queryCouponStock("couponStockId");
		System.err.println(couponStock);
	}
	
	@Test
	public void queryCouponDetail() throws WeixinException {
		CouponDetail couponDetail = WEIXINPAY.queryCouponDetail("couponId");
		System.err.println(couponDetail);
	}
}
