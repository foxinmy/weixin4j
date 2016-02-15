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
import com.foxinmy.weixin4j.payment.mch.ApiResult;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.payment.mch.PrePay;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.Weixin4jSettings;

/**
 * 支付测试（商户平台）
 * 
 * @className PayTest
 * @author jy
 * @date 2016年1月30日
 * @since JDK 1.7
 * @see
 */
public class PayTest {
	protected final static WeixinPayProxy PAY3;
	protected final static WeixinPayAccount ACCOUNT3;
	static {
		ACCOUNT3 = new WeixinPayAccount("wx5518c745065b1f95",
				"请填入v3版本的appSecret", "DTYUNJKL1234fghjkRTGHJNM345678fc",
				"1298173301", null, null, null, null, null);
		PAY3 = new WeixinPayProxy(new Weixin4jSettings(ACCOUNT3));
	}
	/**
	 * 商户证书文件
	 */
	protected File caFile = new File("证书文件，如12333.p12");

	@Test
	public void orderQueryV3() throws WeixinException {
		Order order = PAY3.orderQuery(new IdQuery("BY2016010800025",
				IdType.TRADENO));
		System.err.println(order);
		String sign = order.getSign();
		order.setSign(null);
		String valiSign = DigestUtil
				.paysignMd5(order, ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void refundQueryV3() throws WeixinException {
		com.foxinmy.weixin4j.payment.mch.RefundRecord record = PAY3
				.refundQuery(new IdQuery("TT_1427183696238", IdType.TRADENO));
		System.err.println(record);
		// 这里的验证签名需要把details循环拼接
		String sign = record.getSign();
		record.setSign(null);
		String valiSign = DigestUtil.paysignMd5(record,
				ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void downbillV3() throws WeixinException {
		Calendar c = Calendar.getInstance();
		System.err.println(c.getTime());
		c.set(Calendar.YEAR, 2015);
		c.set(Calendar.MONTH, 2);
		c.set(Calendar.DAY_OF_MONTH, 24);
		System.err.println(c.getTime());
		File file = PAY3.downloadBill(c.getTime(), null);
		System.err.println(file);
	}

	@Test
	public void refundV3() throws WeixinException, IOException {
		IdQuery idQuery = new IdQuery("TT_1427183696238", IdType.TRADENO);
		com.foxinmy.weixin4j.payment.mch.RefundResult result = PAY3
				.refundApply(new FileInputStream(caFile), idQuery, "TT_R"
						+ System.currentTimeMillis(), 0.01d, 0.01d, null,
						"10020674");
		System.err.println(result);
		String sign = result.getSign();
		result.setSign(null);
		String valiSign = DigestUtil.paysignMd5(result,
				ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void nativeV3() throws WeixinException {
		MchPayPackage payPackageV3 = new MchPayPackage(ACCOUNT3,
				"oyFLst1bqtuTcxK-ojF8hOGtLQao", "native测试", "T0001", 0.1d,
				"notify_url", "127.0.0.1", TradeType.NATIVE);
		payPackageV3.setProductId("0001");
		PrePay prePay = null;
		try {
			prePay = PAY3.createPrePay(payPackageV3);
		} catch (WeixinPayException e) {
			e.printStackTrace();
		}
		System.err.println(prePay);
	}

	@Test
	public void closeOrder() throws WeixinException {
		ApiResult result = PAY3.closeOrder("D111");
		System.err.println(result);
		String sign = result.getSign();
		result.setSign(null);
		String valiSign = DigestUtil.paysignMd5(result,
				ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void shortUrl() throws WeixinException {
		String url = "weixin://wxpay/bizpayurl?xxxxxx";
		String shortUrl = PAY3.getPayShorturl(url);
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
		returnXml = PAY3.interfaceReport(interfaceUrl, executeTime, outTradeNo,
				ip, time, returnXml);
		System.err.println(returnXml);
	}
}
