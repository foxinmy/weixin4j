package com.foxinmy.weixin4j.mp.payment.v3;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.PayPackage;
import com.foxinmy.weixin4j.mp.type.TradeType;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * V3支付的订单详情<br/>
 * 注意: <font color="red">total_fee字段传入时单位为元,创建支付时会转换为分</font>
 * 
 * @className PayPackageV3
 * @author jy
 * @date 2014年10月21日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class PayPackageV3 extends PayPackage {

	private static final long serialVersionUID = 8944928173669656177L;

	private String appid; // 微信分配的公众账号 必须
	private String mch_id; // 微信支付分配的商户号 必须
	private String device_info; // 微信支付分配的终端设备号 非必须
	private String nonce_str; // 随机字符串,不长于 32 位 必须
	private String sign; // 签名 必须
	private String trade_type; // 交易类型JSAPI、NATIVE、APP 必须
	private String openid; // 用户在商户 appid 下的唯一 标识, trade_type 为 JSAPI 时,此参数必传
	private String product_id; // 只在 trade_type 为 NATIVE 时需要填写 非必须

	public PayPackageV3() {

	}

	public PayPackageV3(WeixinMpAccount weixinAccount, String openId,
			String body, String out_trade_no, double total_fee,
			String spbill_create_ip, TradeType tradeType) {
		this(weixinAccount, openId, body, null, out_trade_no, total_fee,
				spbill_create_ip, null, tradeType);
	}

	public PayPackageV3(WeixinMpAccount weixinAccount, String openId,
			String body, String attach, String out_trade_no, double total_fee,
			String spbill_create_ip, String notify_url, TradeType tradeType) {
		this(weixinAccount.getId(), weixinAccount.getMchId(), weixinAccount
				.getDeviceInfo(), RandomUtil.generateString(16), body, attach,
				out_trade_no, total_fee, spbill_create_ip, null, null, null,
				notify_url, tradeType, openId, null);
	}

	public PayPackageV3(String appid, String mch_id, String device_info,
			String nonce_str, String body, String attach, String out_trade_no,
			double total_fee, String spbill_create_ip, Date time_start,
			Date time_expire, String goods_tag, String notify_url,
			TradeType tradeType, String openid, String product_id) {
		super(body, attach, out_trade_no, total_fee, spbill_create_ip,
				time_start, time_expire, goods_tag, notify_url);
		this.appid = appid;
		this.mch_id = mch_id;
		this.device_info = device_info;
		this.nonce_str = nonce_str;
		this.trade_type = tradeType.name();
		this.openid = openid;
		this.product_id = product_id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setBody(String body) {
		super.setBody(StringUtils.isBlank(body) ? "服务费用" : body);
	}

	public void setNotify_url(String notify_url) {
		super.setNotify_url(notify_url);
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(TradeType tradeType) {
		this.trade_type = tradeType.name();
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	@Override
	public String toString() {
		return "PayPackageV3 [appid=" + appid + ", mch_id=" + mch_id
				+ ", device_info=" + device_info + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", trade_type=" + trade_type + ", openid="
				+ openid + ", product_id=" + product_id + ", getAppid()="
				+ getAppid() + ", getMch_id()=" + getMch_id()
				+ ", getDevice_info()=" + getDevice_info()
				+ ", getNonce_str()=" + getNonce_str() + ", getSign()="
				+ getSign() + ", getTrade_type()=" + getTrade_type()
				+ ", getOpenid()=" + getOpenid() + ", getProduct_id()="
				+ getProduct_id() + "]";
	}
}
