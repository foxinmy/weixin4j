package com.foxinmy.weixin4j.base.test;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.payment.mch.CorpPayment;
import com.foxinmy.weixin4j.payment.mch.CorpPaymentRecord;
import com.foxinmy.weixin4j.payment.mch.CorpPaymentResult;
import com.foxinmy.weixin4j.payment.mch.Redpacket;
import com.foxinmy.weixin4j.payment.mch.RedpacketRecord;
import com.foxinmy.weixin4j.payment.mch.RedpacketSendResult;
import com.foxinmy.weixin4j.type.mch.CorpPaymentCheckNameType;
import com.foxinmy.weixin4j.util.Consts;

/**
 * 现金发放测试
 *
 * @className CashTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年4月1日
 * @since JDK 1.6
 * @see
 */
public class CashTest extends PayTest {

	@Test
	public void sendRedpacket() throws WeixinException {
		Redpacket redpacket = new Redpacket("HB001",
				"oyFLst1bqtuTcxK-ojF8hOGtLQao", "无忧钱庄", 1d, 1, "红包测试",
				"127.0.0.1", "快来领取红包吧！", "来就送钱");
		RedpacketSendResult result = PAY.sendRedpack(redpacket);
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}

	@Test
	public void queryRedpacket() throws WeixinException {
		String outTradeNo = "HB001";
		RedpacketRecord result = PAY.queryRedpack(outTradeNo);
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}

	@Test
	public void sendCorpPayment() throws WeixinException {
		CorpPayment payment = new CorpPayment("MP001",
				"ofW1gwok9vZIyle0YbA-eQe83Uk8",
				CorpPaymentCheckNameType.NO_CHECK, "企业付款测试", 1d, "127.0.0.1");
		CorpPaymentResult result = PAY.sendCorpPayment(payment);
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}

	@Test
	public void queryCorpPayment() throws WeixinException {
		CorpPaymentRecord result = PAY.queryCorpPayment("MP001");
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}
}
