package com.foxinmy.weixin4j.base.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.payment.mch.MerchantResult;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.payment.mch.PrePay;
import com.foxinmy.weixin4j.payment.mch.RefundRecord;
import com.foxinmy.weixin4j.payment.mch.RefundResult;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.sign.WeixinSignature;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.type.mch.BillType;
import com.foxinmy.weixin4j.type.mch.RefundAccountType;
import com.foxinmy.weixin4j.util.Consts;

/**
 * 支付测试（商户平台）
 *
 * @className PayTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年1月30日
 * @since JDK 1.7
 * @see
 */
public class PayTest {
	protected final static WeixinPayAccount ACCOUNT;
	protected final static WeixinSignature SIGNATURE;
	protected final static WeixinPayProxy PAY;

	static {
		ACCOUNT = new WeixinPayAccount(
				"id",
				"支付秘钥",
				"商户号",
				"加载证书的密码，默认为商户号",
				"证书文件路径");
		SIGNATURE = new WeixinPaymentSignature(ACCOUNT.getPaySignKey());
		PAY = new WeixinPayProxy(ACCOUNT);
	}

	@Test
	public void queryOrder() throws WeixinException {
		Order order = PAY.queryOrder(new IdQuery("BY2016010800025",
				IdType.TRADENO));
		Assert.assertEquals(Consts.SUCCESS, order.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, order.getResultCode());
		System.err.println(order);
		String sign = order.getSign();
		order.setSign(null);
		String valiSign = SIGNATURE.sign(order);
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void queryRefund() throws WeixinException {
		RefundRecord record = PAY.queryRefund(new IdQuery("TT_1427183696238",
				IdType.TRADENO));
		Assert.assertEquals(Consts.SUCCESS, record.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, record.getResultCode());
		System.err.println(record);
		String sign = record.getSign();
		record.setSign(null);
		String valiSign = SIGNATURE.sign(record);
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void downbill() throws WeixinException, IOException {
		Calendar c = Calendar.getInstance();
		// c.set(Calendar.YEAR, 2016);
		// c.set(Calendar.MONTH, 3);
		// c.set(Calendar.DAY_OF_MONTH, 4);
		c.add(Calendar.DAY_OF_MONTH, -1);
		System.err.println(c.getTime());
		OutputStream os = new FileOutputStream("/tmp/bill20160813.txt");
		PAY.downloadBill(c.getTime(), BillType.ALL, os, null);
	}

	@Test
	public void refund() throws WeixinException, IOException {
		IdQuery idQuery = new IdQuery("TT_1427183696238", IdType.TRADENO);
		RefundResult result = PAY.applyRefund(idQuery,
				"TT_R" + System.currentTimeMillis(), 0.01d, 0.01d,
				CurrencyType.CNY, "10020674", "退款描述",
				RefundAccountType.REFUND_SOURCE_RECHARGE_FUNDS);
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
		String sign = result.getSign();
		result.setSign(null);
		String valiSign = SIGNATURE.sign(result);
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void nativePay() throws WeixinException {
		MchPayPackage payPackageV3 = new MchPayPackage("native测试", "T0001",
				0.1d, "notify_url", "127.0.0.1", TradeType.NATIVE, null, null,
				"productId", null);
		PrePay result = PAY.createPrePay(payPackageV3);
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
	}

	@Test
	public void closeOrder() throws WeixinException {
		MerchantResult result = PAY.closeOrder("D111");
		Assert.assertEquals(Consts.SUCCESS, result.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, result.getResultCode());
		System.err.println(result);
		String sign = result.getSign();
		result.setSign(null);
		String valiSign = SIGNATURE.sign(result);
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void shortUrl() throws WeixinException {
		String url = "weixin://wxpay/bizpayurl?xxxxxx";
		String shortUrl = PAY.getPayShorturl(url);
		System.err.println(shortUrl);
	}

	@Test
	public void interfaceReport() throws WeixinException {
		String interfaceUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		int executeTime = 2500;
		String outTradeNo = null;
		String ip = "127.0.0.1";
		Date time = new Date();
		XmlResult returnXml = new XmlResult("SUCCESS", "");
		returnXml.setResultCode("SUCCESS");
		returnXml = PAY.reportInterface(interfaceUrl, executeTime, outTradeNo,
				ip, time, returnXml);
		Assert.assertEquals(Consts.SUCCESS, returnXml.getReturnCode());
		Assert.assertEquals(Consts.SUCCESS, returnXml.getResultCode());
		System.err.println(returnXml);
	}

	@Test
	public void testMicroPay() throws WeixinException {
		String authCode = "扫描码";
		String body = "商品描述";
		String outTradeNo = "M001";
		double totalFee = 1d;
		String createIp = "127.0.0.1";
		MchPayRequest request = PAY.createMicroPayRequest(authCode, body,
				outTradeNo, totalFee, createIp, null);
		System.err.println(request);
	}
}