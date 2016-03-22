package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.util.Calendar;

import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.mp.api.Pay2Api;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.util.Weixin4jSettings;

/**
 * 支付测试（V2版本 2014年9月之前申请微信支付的公众号）
 * 
 * @className PayTest
 * @author jy
 * @date 2016年1月30日
 * @since JDK 1.7
 * @see
 */
public class PayTest {
	protected final static Pay2Api PAY2;
	protected final static WeixinPayAccount ACCOUNT2;
	static {
		ACCOUNT2 = new WeixinPayAccount("请填入v2版本的appid", "请填入v2版本的appSecret",
				"请填入v2版本的paysignkey", null, null, null, null,
				"请填入v2版本的partnerId", "请填入v2版本的partnerKey");
		PAY2 = new Pay2Api(new Weixin4jSettings(ACCOUNT2));
	}
	/**
	 * 商户证书文件
	 */
	protected File caFile = new File("证书文件，如12333.p12");

	@Test
	public void orderQueryV2() throws WeixinException {
		System.err.println(PAY2.orderQuery(new IdQuery("D14110500021",
				IdType.REFUNDNO)));
	}

	@Test
	public void refundV2() throws WeixinException {
		IdQuery idQuery = new IdQuery("D15020300005", IdType.TRADENO);
		System.err.println(PAY2.refundApply(caFile, idQuery, "1422925555037",
				16d, 16d, "1221928801", "111111", null, null, null));
	}

	@Test
	public void refundQueryV2() throws WeixinException {
		System.err.println(PAY2.refundQuery(new IdQuery("D14123000004",
				IdType.TRADENO)));
	}

	@Test
	public void downbillV2() throws WeixinException {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2014);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 22);
		File file = PAY2.downloadBill(c.getTime(), null);
		System.err.println(file);
	}
}
