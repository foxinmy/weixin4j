package com.foxinmy.weixin4j.base.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.exception.WeixinPayException;
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
import com.foxinmy.weixin4j.setting.Weixin4jSettings;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.sign.WeixinSignature;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.type.TradeType;

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
		ACCOUNT = new WeixinPayAccount("appid", "paysignkey", "mchid");
		SIGNATURE = new WeixinPaymentSignature(ACCOUNT.getPaySignKey());
		PAY = new WeixinPayProxy(new Weixin4jSettings(ACCOUNT));
	}
	/**
	 * 商户证书文件
	 */
	protected File caFile = new File("商户证书:*.p12");

	@Test
	public void queryOrder() throws WeixinException {
		Order order = PAY.queryOrder(new IdQuery("BY2016010800025",
				IdType.TRADENO));
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
		System.err.println(record);
		// 这里的验证签名需要把details循环拼接
		String sign = record.getSign();
		record.setSign(null);
		String valiSign = SIGNATURE.sign(record);
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void downbill() throws WeixinException {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2016);
		c.set(Calendar.MONTH, 3);
		c.set(Calendar.DAY_OF_MONTH, 4);
		System.err.println(c.getTime());
		File file = PAY.downloadBill(c.getTime(), null);
		System.err.println(file);
	}

	@Test
	public void refund() throws WeixinException, IOException {
		IdQuery idQuery = new IdQuery("TT_1427183696238", IdType.TRADENO);
		RefundResult result = PAY.applyRefund(new FileInputStream(caFile),
				idQuery, "TT_R" + System.currentTimeMillis(), 0.01d, 0.01d,
				null, "10020674");
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
		PrePay prePay = null;
		try {
			prePay = PAY.createPrePay(payPackageV3);
		} catch (WeixinPayException e) {
			e.printStackTrace();
		}
		System.err.println(prePay);
	}

	@Test
	public void closeOrder() throws WeixinException {
		MerchantResult result = PAY.closeOrder("D111");
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
		returnXml = PAY.interfaceReport(interfaceUrl, executeTime, outTradeNo,
				ip, time, returnXml);
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
