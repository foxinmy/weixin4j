package com.foxinmy.weixin4j.mp.oldpayment;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinAccount;

/**
 * 微信支付账户(2014年10月申请支付的老版本)
 * 
 * @className WeixinOldPayAccount
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月26日
 * @since JDK 1.6
 * @see
 */
public class WeixinOldPayAccount extends WeixinAccount {

	private static final long serialVersionUID = -2791256176906048632L;
	/**
	 * 公众号支付请求中用于加密的密钥 Key,可验证商户唯一身份,PaySignKey 对应于支付场景中的 appKey 值
	 */
	private String paySignKey;
	/**
	 * 财付通商户身份的标识
	 */
	private String partnerId;
	/**
	 * 财付通商户权限密钥Key
	 */
	private String partnerKey;

	/**
	 * 支付商户信息
	 * 
	 * @param appId
	 *            公众号唯一的身份ID(必填)
	 * @param appSecret
	 *            调用接口的凭证(必填)
	 * @param paySignKey
	 *            支付密钥字符串(必填)
	 * @param partnerId
	 *            财付通的商户号(必填)
	 * @param partnerKey
	 *            财付通商户权限密钥Key(必填)
	 */
	@JSONCreator
	public WeixinOldPayAccount(@JSONField(name = "id") String appId,
			@JSONField(name = "secret") String appSecret,
			@JSONField(name = "paySignKey") String paySignKey,
			@JSONField(name = "partnerId") String partnerId,
			@JSONField(name = "partnerKey") String partnerKey) {
		super(appId, appSecret);
		this.paySignKey = paySignKey;
		this.partnerId = partnerId;
		this.partnerKey = partnerKey;
	}

	public String getPaySignKey() {
		return paySignKey;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	@Override
	public String toString() {
		return "WeixinOldPayAccount [" + super.toString() + ", paySignKey="
				+ paySignKey + ", partnerId=" + partnerId + ", partnerKey="
				+ partnerKey + "]";
	}
}
