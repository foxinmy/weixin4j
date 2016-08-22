package com.foxinmy.weixin4j.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.mch.MerchantResult;
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
	protected WeixinRequestExecutor createSSLRequestExecutor()
			throws WeixinException {
		InputStream certificateFile;
		try {
			File certificate = new File(weixinAccount.getCertificateFile());
			if (!certificate.exists() || !certificate.isFile()) {
				throw new WeixinException("invalid certificate file : "
						+ certificate.toString());
			}
			certificateFile = new FileInputStream(
					weixinAccount.getCertificateFile());
		} catch (IOException e) {
			throw new WeixinException("IO Error on createSSLRequestExecutor", e);
		}
		return weixinExecutor.createSSLRequestExecutor(
				weixinAccount.getCertificateKey(), certificateFile);
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

	/**
	 * 微信签名类
	 *
	 * @return
	 */
	public WeixinSignature getWeixinSignature() {
		return this.weixinSignature;
	}

	/**
	 * 设置商户信息
	 *
	 * @param merchant
	 */
	protected <T extends MerchantResult> void declareMerchant(T merchant) {
		merchant.setAppId(weixinAccount.getId());
		merchant.setMchId(weixinAccount.getMchId());
		merchant.setDeviceInfo(weixinAccount.getDeviceInfo());
		merchant.setSubId(weixinAccount.getSubId());
		merchant.setSubMchId(weixinAccount.getSubMchId());
		merchant.setNonceStr(RandomUtil.generateString(16));
	}
}
