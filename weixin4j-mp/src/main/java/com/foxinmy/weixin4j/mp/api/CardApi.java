package com.foxinmy.weixin4j.mp.api;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.card.CardCoupon;
import com.foxinmy.weixin4j.card.CardCoupons;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 卡券API
 * 
 * @className CardApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月3日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056&token=&lang=zh_CN">卡券说明</a>
 */
public class CardApi extends MpApi {
	private final TokenManager tokenManager;

	public CardApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 创建卡券:创建卡券接口是微信卡券的基础接口，用于创建一类新的卡券，获取card_id，创建成功并通过审核后，
	 * 商家可以通过文档提供的其他接口将卡券下发给用户，每次成功领取，库存数量相应扣除。
	 * 
	 * <li>1.需自定义Code码的商家必须在创建卡券时候，设定use_custom_code为true，且在调用投放卡券接口时填入指定的Code码。
	 * 指定OpenID同理。特别注意：在公众平台创建的卡券均为非自定义Code类型。 <li>
	 * 2.can_share字段指领取卡券原生页面是否可分享，建议指定Code码、指定OpenID等强限制条件的卡券填写false。 <li>
	 * 3.创建成功后该卡券会自动提交审核
	 * ，审核结果将通过事件通知商户。开发者可调用设置白名单接口设置用户白名单，领取未通过审核的卡券，测试整个卡券的使用流程。
	 * 
	 * @param cardCoupon
	 *            卡券对象
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056&token=&lang=zh_CN">创建卡券</a>
	 * @see CardCoupons
	 * @see MediaApi#uploadImage(java.io.InputStream, String)
	 * @return 卡券ID
	 * @throws WeixinException
	 */
	public String createCardCoupon(CardCoupon cardCoupon)
			throws WeixinException {
		JSONObject content = new JSONObject();
		String cardType = cardCoupon.getCardType().name();
		content.put("card_type", cardType);
		content.put(cardType.toLowerCase(), cardCoupon);
		JSONObject card = new JSONObject();
		card.put("card", content);
		System.err.println(card);
		Token token = tokenManager.getCache();
		String card_create_uri = getRequestUri("card_create_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(card_create_uri, token.getAccessToken()),
				card.toJSONString());
		return response.getAsJson().getString("card_id");
	}

	/**
	 * 设置卡券买单：创建卡券之后，开发者可以通过设置微信买单接口设置该card_id支持微信买单功能。值得开发者注意的是，
	 * 设置买单的card_id必须已经配置了门店，否则会报错。
	 * 
	 * @param cardId
	 *            卡券ID
	 * @param isOpen
	 *            是否开启买单功能，填true/false
	 * @see #createCardCoupon(CardCoupon)
	 * @return 操作结果
	 * @throws WeixinException
	 */
	public ApiResult setCardPayCell(String cardId, boolean isOpen)
			throws WeixinException {
		JSONObject params = new JSONObject();
		params.put("card_id", cardId);
		params.put("is_open", isOpen);
		Token token = tokenManager.getCache();
		String card_paycell_uri = getRequestUri("card_paycell_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(card_paycell_uri, token.getAccessToken()),
				params.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 设置自助核销:创建卡券之后，开发者可以通过设置微信买单接口设置该card_id支持自助核销功能。值得开发者注意的是，
	 * 设置自助核销的card_id必须已经配置了门店，否则会报错。
	 * 
	 * @param cardId
	 *            卡券ID
	 * @param isOpen
	 *            是否开启买单功能，填true/false
	 * @see #createCardCoupon(CardCoupon)
	 * @return 操作结果
	 * @throws WeixinException
	 */
	public ApiResult setCardSelfConsumeCell(String cardId, boolean isOpen)
			throws WeixinException {
		JSONObject params = new JSONObject();
		params.put("card_id", cardId);
		params.put("is_open", isOpen);
		Token token = tokenManager.getCache();
		String card_selfconsumecell_uri = getRequestUri("card_selfconsumecell_uri");
		WeixinResponse response = weixinExecutor
				.post(String.format(card_selfconsumecell_uri,
						token.getAccessToken()), params.toJSONString());
		return response.getAsResult();
	}
}
