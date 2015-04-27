package com.foxinmy.weixin4j.mp.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.http.SSLHttpRequest;
import com.foxinmy.weixin4j.mp.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponDetail;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponResult;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponStock;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * 代金券API
 * 
 * @className CouponApi
 * @author jy
 * @date 2015年3月25日
 * @since JDK 1.7
 * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/sp_coupon.php">代金券文档</a>
 */
public class CouponApi extends MpApi {

	private final WeixinMpAccount weixinAccount;

	public CouponApi(WeixinMpAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	/**
	 * 发放代金券(需要证书)
	 * 
	 * @param caFile
	 *            证书文件(后缀为*.p12)
	 * @param couponStockId
	 *            代金券批次id
	 * @param partnerTradeNo
	 *            商户发放凭据号（格式：商户id+日期+流水号），商户侧需保持唯一性
	 * @param openId
	 *            用户的openid
	 * @param opUserId
	 *            操作员帐号, 默认为商户号 可在商户平台配置操作员对应的api权限 可为空
	 * @return 发放结果
	 * @see com.foxinmy.weixin4j.mp.payment.coupon.CouponResult
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/sp_coupon.php?chapter=12_3">发放代金券接口</a>
	 * @throws WeixinException
	 */
	public CouponResult sendCoupon(File caFile, String couponStockId,
			String partnerTradeNo, String openId, String opUserId)
			throws WeixinException {
		Map<String, String> map = baseMap();
		map.put("coupon_stock_id", couponStockId);
		map.put("partner_trade_no", partnerTradeNo);
		map.put("openid", openId);
		// openid记录数（目前支持num=1）
		map.put("openid_count", "1");
		// 操作员帐号, 默认为商户号 可在商户平台配置操作员对应的api权限
		if (StringUtils.isBlank(opUserId)) {
			opUserId = weixinAccount.getMchId();
		}
		map.put("op_user_id", opUserId);
		map.put("version", "1.0");
		map.put("type", "XML");
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String coupon_send_uri = getRequestUri("coupon_send_uri");
		Response response = null;
		InputStream ca = null;
		try {
			ca = new FileInputStream(caFile);
			SSLHttpRequest request = new SSLHttpRequest(
					weixinAccount.getMchId(), ca);
			response = request.post(coupon_send_uri, param);
		} catch (WeixinException e) {
			throw e;
		} catch (Exception e) {
			throw new WeixinException(e.getMessage());
		} finally {
			if (ca != null) {
				try {
					ca.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return response.getAsObject(new TypeReference<CouponResult>() {
		});
	}

	/**
	 * 查询代金券批次
	 * 
	 * @param couponStockId
	 *            代金券批次ID
	 * @return 代金券批次信息
	 * @see com.foxinmy.weixin4j.mp.payment.coupon.CouponStock
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/sp_coupon.php?chapter=12_4">查询代金券批次信息</a>
	 * @throws WeixinException
	 */
	public CouponStock queryCouponStock(String couponStockId)
			throws WeixinException {
		Map<String, String> map = baseMap();
		map.put("coupon_stock_id", couponStockId);
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String couponstock_query_uri = getRequestUri("couponstock_query_uri");
		Response response = request.post(couponstock_query_uri, param);
		return response.getAsObject(new TypeReference<CouponStock>() {
		});
	}

	/**
	 * 查询代金券详细
	 * 
	 * @param couponId
	 *            代金券ID
	 * @return 代金券详细信息
	 * @see com.foxinmy.weixin4j.mp.payment.coupon.CouponDetail
	 * @see <a
	 *      href="http://pay.weixin.qq.com/wiki/doc/api/sp_coupon.php?chapter=12_5">查询代金券详细信息</a>
	 * @throws WeixinException
	 */
	public CouponDetail queryCouponDetail(String couponId)
			throws WeixinException {
		Map<String, String> map = baseMap();
		map.put("coupon_id", couponId);
		String sign = PayUtil.paysignMd5(map, weixinAccount.getPaySignKey());
		map.put("sign", sign);
		String param = map2xml(map);
		String coupondetail_query_uri = getRequestUri("coupondetail_query_uri");
		Response response = request.post(coupondetail_query_uri, param);
		return response.getAsObject(new TypeReference<CouponDetail>() {
		});
	}

	/**
	 * 接口请求基本数据
	 * 
	 * @return
	 */
	private Map<String, String> baseMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getId());
		map.put("mch_id", weixinAccount.getMchId());
		map.put("nonce_str", RandomUtil.generateString(16));
		if (StringUtils.isNotBlank(weixinAccount.getDeviceInfo())) {
			map.put("device_info", weixinAccount.getDeviceInfo());
		}
		if (StringUtils.isNotBlank(weixinAccount.getSubMchId())) {
			map.put("sub_mch_id", weixinAccount.getSubMchId());
		}
		return map;
	}
}