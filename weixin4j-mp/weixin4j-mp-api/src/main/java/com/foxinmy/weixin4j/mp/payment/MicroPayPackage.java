package com.foxinmy.weixin4j.mp.payment;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 刷卡支付
 * 
 * @className MicroPayPackage
 * @author jy
 * @date 2014年11月17日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class MicroPayPackage extends PayPackage {

	private static final long serialVersionUID = 8944928173669656177L;

	private String appid; // 微信分配的公众账号 必须
	private String mch_id; // 微信支付分配的商户号 必须
	private String device_info; // 微信支付分配的终端设备号 非必须
	private String nonce_str; // 随机字符串,不长于 32 位 必须
	private String sign; // 签名 必须
	private String auth_code; // 扫码支付授权码 ,设备读取用 户微信中的条码或者二维码 信息

	public MicroPayPackage() {

	}

	public MicroPayPackage(WeixinAccount weixinAccount, String body,
			String attach, String out_trade_no, double total_fee,
			String spbill_create_ip, String auth_code) {
		this(weixinAccount.getAppId(), weixinAccount.getMchId(), weixinAccount
				.getDeviceInfo(), RandomUtil.generateString(16), body, attach,
				out_trade_no, total_fee, spbill_create_ip, null, null, null,
				auth_code);
	}

	public MicroPayPackage(String appid, String mch_id, String device_info,
			String nonce_str, String body, String attach, String out_trade_no,
			double total_fee, String spbill_create_ip, Date time_start,
			Date time_expire, String goods_tag, String auth_code) {
		super(body, attach, out_trade_no, total_fee, spbill_create_ip,
				time_start, time_expire, goods_tag, null);
		this.appid = appid;
		this.mch_id = mch_id;
		this.device_info = device_info;
		this.nonce_str = nonce_str;
		this.auth_code = auth_code;
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

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	@Override
	public String toString() {
		return "MicroPayPackage [appid=" + appid + ", mch_id=" + mch_id
				+ ", device_info=" + device_info + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", auth_code=" + auth_code
				+ ", getBody()=" + getBody() + ", getAttach()=" + getAttach()
				+ ", getOut_trade_no()=" + getOut_trade_no()
				+ ", getTotal_fee()=" + getTotal_fee()
				+ ", getSpbill_create_ip()=" + getSpbill_create_ip()
				+ ", getTime_start()=" + getTime_start()
				+ ", getTime_expire()=" + getTime_expire()
				+ ", getGoods_tag()=" + getGoods_tag() + "]";
	}
}
