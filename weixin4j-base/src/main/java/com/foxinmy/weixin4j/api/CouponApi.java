package com.foxinmy.weixin4j.api;

import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.coupon.CouponDetail;
import com.foxinmy.weixin4j.payment.coupon.CouponResult;
import com.foxinmy.weixin4j.payment.coupon.CouponStock;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 代金券API
 *
 * @className CouponApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月25日
 * @since JDK 1.6
 * @see <a href=
 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1">代金券</a>
 */
public class CouponApi extends MchApi {

	public CouponApi(WeixinPayAccount weixinAccount) {
		super(weixinAccount);
	}

	/**
	 * 发放代金券(需要证书)
	 *
	 * @param couponStockId
	 *            代金券批次id
	 * @param partnerTradeNo
	 *            商户发放凭据号（格式：商户id+日期+流水号），商户侧需保持唯一性
	 * @param openId
	 *            用户的openid
	 * @param opUserId
	 *            操作员帐号, 默认为商户号 可在商户平台配置操作员对应的api权限 可为空
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.payment.coupon.CouponResult
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_3">发放代金券接口</a>
	 * @throws WeixinException
	 */
	public CouponResult sendCoupon(String couponStockId, String partnerTradeNo,
			String openId, String opUserId) throws WeixinException {
		Map<String, String> map = createBaseRequestMap(null);
		map.put("coupon_stock_id", couponStockId);
		map.put("partner_trade_no", partnerTradeNo);
		map.put("openid", openId);
		// openid记录数（目前支持num=1）
		map.put("openid_count", "1");
		// 操作员帐号, 默认为商户号 可在商户平台配置操作员对应的api权限
		if (StringUtil.isBlank(opUserId)) {
			opUserId = weixinAccount.getMchId();
		}
		map.put("op_user_id", opUserId);
		map.put("version", "1.0");
		map.put("type", "XML");
		map.put("sign", weixinSignature.sign(map));
		String param = XmlStream.map2xml(map);
		WeixinResponse response = getWeixinSSLExecutor().post(
				getRequestUri("coupon_send_uri"), param);
		return response.getAsObject(new TypeReference<CouponResult>() {
		});
	}

	/**
	 * 查询代金券批次
	 *
	 * @param couponStockId
	 *            代金券批次ID
	 * @return 代金券批次信息
	 * @see com.foxinmy.weixin4j.payment.coupon.CouponStock
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_4">查询代金券批次信息接口</a>
	 * @throws WeixinException
	 */
	public CouponStock queryCouponStock(String couponStockId)
			throws WeixinException {
		Map<String, String> map = createBaseRequestMap(null);
		map.put("coupon_stock_id", couponStockId);
		map.put("sign", weixinSignature.sign(map));
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				getRequestUri("couponstock_query_uri"), param);
		return response.getAsObject(new TypeReference<CouponStock>() {
		});
	}

	/**
	 * 查询代金券详细
	 *
	 * @param openId
	 *            用户ID
	 * @param couponId
	 *            代金券ID
	 * @param stockId
	 *            代金劵对应的批次号
	 * @return 代金券详细信息
	 * @see com.foxinmy.weixin4j.payment.coupon.CouponDetail
	 * @see <a href=
	 *      "https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_5">查询代金券详细信息接口</a>
	 * @throws WeixinException
	 */
	public CouponDetail queryCouponDetail(String openId, String couponId,
			String stockId) throws WeixinException {
		Map<String, String> map = createBaseRequestMap(null);
		map.put("openid", openId);
		map.put("coupon_id", couponId);
		map.put("stock_id", stockId);
		map.put("sign", weixinSignature.sign(map));
		String param = XmlStream.map2xml(map);
		WeixinResponse response = weixinExecutor.post(
				getRequestUri("coupondetail_query_uri"), param);
		return response.getAsObject(new TypeReference<CouponDetail>() {
		});
	}
}