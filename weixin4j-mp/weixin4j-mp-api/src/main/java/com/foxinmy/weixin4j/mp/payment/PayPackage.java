package com.foxinmy.weixin4j.mp.payment;

import java.io.Serializable;
import java.util.Date;

import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 订单信息
 * @className PayPackage
 * @author jy
 * @date 2014年12月18日
 * @since JDK 1.7
 * @see
 */
public class PayPackage implements Serializable {

	private static final long serialVersionUID = 3450161267802545790L;

	private String body; // 商品描述 必须
	private String attach; // 附加数据,原样返回 非必须
	private String out_trade_no; // 商户系统内部的订单号 ,32 个字符内 、可包含字母 ,确保 在商户系统唯一 必须
	private String total_fee; // 订单总金额,单位为分,不 能带小数点 必须
	private String spbill_create_ip; // 订单生成的机器 IP 必须
	private String time_start; // 订单生成时间,格式 为 yyyyMMddHHmmss,如 2009 年
								// 12月25日9点10分10秒表 示为 20091225091010。时区 为 GMT+8
								// beijing。该时间取 自商户服务器 非必须
	private String time_expire; // 订单失效时间,格式 为 yyyyMMddHHmmss,如 2009 年
								// 12月27日9点10分10秒表 示为 20091227091010。时区 为 GMT+8
								// beijing。该时间取 自商户服务商品标记 非必须
	private String goods_tag; // 商品标记,该字段不能随便 填,不使用请填空 非必须
	private String notify_url; // 通知地址接收微信支付成功通知 必须

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(double total_fee) {
		this.total_fee = DateUtil.formaFee2Fen(total_fee);
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public void setTime_start(Date time_start) {
		this.time_start = time_start != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(time_start) : null;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(Date time_expire) {
		this.time_expire = time_expire != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(time_expire) : null;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public PayPackage() {
	}

	public PayPackage(String body, String attach, String out_trade_no,
			double total_fee, String spbill_create_ip, Date time_start,
			Date time_expire, String goods_tag, String notify_url) {
		this.body = body;
		this.attach = attach;
		this.out_trade_no = out_trade_no;
		this.total_fee = DateUtil.formaFee2Fen(total_fee);
		this.spbill_create_ip = spbill_create_ip;
		this.time_start = time_start != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(time_start) : null;
		this.time_expire = time_expire != null ? DateUtil
				.fortmat2yyyyMMddHHmmss(time_expire) : null;
		this.goods_tag = goods_tag;
		this.notify_url = notify_url;
	}

	@Override
	public String toString() {
		return "body=" + body + ", attach=" + attach + ", out_trade_no="
				+ out_trade_no + ", total_fee=" + total_fee
				+ ", spbill_create_ip=" + spbill_create_ip + ", time_start="
				+ time_start + ", time_expire=" + time_expire + ", goods_tag="
				+ goods_tag + ", notify_url=" + notify_url;
	}
}
