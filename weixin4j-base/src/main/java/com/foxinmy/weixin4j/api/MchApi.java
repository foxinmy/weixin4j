package com.foxinmy.weixin4j.api;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinSSLRequestExecutor;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.sign.WeixinSignature;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 商户支付
 * 
 * @className MchApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月26日
 * @since JDK 1.6
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/index.html">商户支付平台</a>
 */
public class MchApi extends BaseApi {

	private final static ResourceBundle WEIXIN_BUNDLE;

	static {
		WEIXIN_BUNDLE = ResourceBundle
				.getBundle("com/foxinmy/weixin4j/payment/weixin");
	}

	protected final WeixinPayAccount weixinAccount;
	protected final WeixinSignature weixinSignature;

	public MchApi(WeixinPayAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
		this.weixinSignature = new WeixinPaymentSignature(
				weixinAccount.getPaySignKey());
	}

	@Override
	protected ResourceBundle weixinBundle() {
		return WEIXIN_BUNDLE;
	}

	/**
	 * 创建 SSL支付请求
	 * 
	 * @param certificate
	 *            *.p12证书文件
	 * @return
	 * @throws WeixinException
	 */
	protected WeixinRequestExecutor createSSLRequestExecutor(
			InputStream certificate) throws WeixinException {
		return new WeixinSSLRequestExecutor(weixinAccount.getCertificateKey(),
				certificate);
	}

	/**
	 * 支付接口请求基本数据
	 * 
	 * @param idQuery
	 *            ID信息 可为空
	 * @return 基础map
	 */
	protected Map<String, String> createBaseRequestMap(IdQuery idQuery) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("nonce_str", RandomUtil.generateString(16));
		if (StringUtil.isNotBlank(weixinAccount.getDeviceInfo())) {
			map.put("device_info", weixinAccount.getDeviceInfo());
		}
		if (StringUtil.isNotBlank(weixinAccount.getSubId())) {
			map.put("sub_appid", weixinAccount.getSubId());
		}
		if (StringUtil.isNotBlank(weixinAccount.getSubMchId())) {
			map.put("sub_mch_id", weixinAccount.getSubMchId());
		}
		if (idQuery != null) {
			map.put(idQuery.getType().getName(), idQuery.getId());
		}
		return map;
	}
}
