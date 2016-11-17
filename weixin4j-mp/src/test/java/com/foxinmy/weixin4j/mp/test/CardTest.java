package com.foxinmy.weixin4j.mp.test;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.card.CardCoupon;
import com.foxinmy.weixin4j.model.card.CardCoupons;
import com.foxinmy.weixin4j.model.card.CardQR;
import com.foxinmy.weixin4j.model.card.CouponBaseInfo;
import com.foxinmy.weixin4j.model.qr.QRResult;
import com.foxinmy.weixin4j.mp.api.CardApi;
import com.foxinmy.weixin4j.type.card.CardCodeType;
import com.foxinmy.weixin4j.type.card.CardColor;

/**
 * 卡券测试
 * 
 * @className CardTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月5日
 * @since JDK 1.6
 */
public class CardTest extends TokenTest {
	private CardApi cardApi;

	@Before
	public void init() {
		cardApi = new CardApi(tokenManager);
	}

	/**
	 * 创建卡券测试
	 * 
	 * @throws WeixinException
	 */
	@Test
	public void createCardCoupon() throws WeixinException {
		CouponBaseInfo.Builder builder = CardCoupons.customBase();
		// ... 必选字段
		builder.logoUrl("商户logo").brandName("商户名称").title("双人套餐100元兑换券");
		builder.codeType(CardCodeType.CODE_TYPE_BARCODE).cardColor(
				CardColor.Color010);
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = ca.getTime();
		ca.add(Calendar.DAY_OF_MONTH, 1);
		Date endTime = ca.getTime();
		builder.notice("请出示二维码").description("不可与其他优惠同享").quantity(100)
				.activeAt(beginTime, endTime);
		// ... 可选字段
		CardCoupon coupon = CardCoupons.createGeneralCoupon(builder, "优惠券描述");
		String cardId = cardApi.createCardCoupon(coupon);
		// pwGBft8tDsk_gj2rfVeAfreCxQS8
		Assert.assertNotNull(cardId);
	}

	/**
	 * 设置卡券买单
	 * 
	 * @throws WeixinException
	 */
	@Test
	public void setCardPayCell() throws WeixinException {
		String cardId = "pwGBft8tDsk_gj2rfVeAfreCxQS8";
		cardApi.setCardPayCell(cardId, true);
	}

	/**
	 * 设置自助核销
	 * 
	 * @throws WeixinException
	 */
	@Test
	public void setCardSelfConsumeCell() throws WeixinException {
		String cardId = "pwGBft8tDsk_gj2rfVeAfreCxQS8";
		cardApi.setCardSelfConsumeCell(cardId, true);
	}

	/**
	 * 创建卡券二维码
	 * 
	 * @throws WeixinException
	 */
	@Test
	public void createCardQR() throws WeixinException {
		CardQR.Builder builder = new CardQR.Builder("cardId");
		builder.sceneValuer("sceneValue");
		QRResult qrResult = cardApi.createCardQR(null, builder.build());
		System.err.println(qrResult);
	}
}
