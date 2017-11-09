

import java.io.IOException;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.payment.mch.RefundResult;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.sign.WeixinSignature;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.type.mch.RefundAccountType;

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
				"wx0d1d598c0c03c999",
				"GATFzDwbQdbbci3QEQxX2rUBvwTrsMiZ",
				"10020674",
				"10020674",
				"/Users/jy/workspace/feican/canyi-weixin-parent/canyi-weixin-service/src/main/resources/10020674.p12");
		SIGNATURE = new WeixinPaymentSignature(ACCOUNT.getPaySignKey());
		PAY = new WeixinPayProxy(ACCOUNT);
	}

	public static void refund() throws WeixinException, IOException {
		IdQuery idQuery = new IdQuery("TT_1427183696238", IdType.TRADENO);
		RefundResult result = PAY.applyRefund(idQuery,
				"TT_R" + System.currentTimeMillis(), 0.01d, 0.01d,
				CurrencyType.CNY, "10020674", "退款描述",
				RefundAccountType.REFUND_SOURCE_RECHARGE_FUNDS);
		System.err.println(result);
		String sign = result.getSign();
		result.setSign(null);
		String valiSign = SIGNATURE.sign(result);
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
	}
	
	public static void main(String[] args) throws WeixinException, IOException{
		refund();
	}
}