package com.foxinmy.weixin4j.mp.api;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.card.CardCoupon;
import com.foxinmy.weixin4j.model.card.CardCoupons;
import com.foxinmy.weixin4j.model.card.CardQR;
import com.foxinmy.weixin4j.model.card.MemberInitInfo;
import com.foxinmy.weixin4j.model.card.MemberUpdateInfo;
import com.foxinmy.weixin4j.model.card.MemberUserForm;
import com.foxinmy.weixin4j.model.card.MemberUserInfo;
import com.foxinmy.weixin4j.model.qr.QRParameter;
import com.foxinmy.weixin4j.model.qr.QRResult;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.type.card.CardStatus;
import com.foxinmy.weixin4j.type.card.CardType;
import com.foxinmy.weixin4j.util.IOUtil;

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
	protected final TokenManager tokenManager;

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

	/**
	 * 创建卡券二维码： 开发者可调用该接口生成一张卡券二维码供用户扫码后添加卡券到卡包。
	 *
	 * @param expireSeconds
	 *            指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为365天有效
	 * @param cardQRs
	 *            二维码参数:二维码领取单张卡券/多张卡券
	 * @return 二维码结果对象
	 * @see com.foxinmy.weixin4j.model.qr.QRResult
	 * @see com.foxinmy.weixin4j.model.qr.QRParameter
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062&token=&lang=zh_CN">投放卡券</a>
	 * @throws WeixinException
	 */
	public QRResult createCardQR(Integer expireSeconds, CardQR... cardQRs)
			throws WeixinException {
		QRParameter parameter = QRParameter.createCardCouponQR(expireSeconds,
				cardQRs);
		Token token = tokenManager.getCache();
		String qr_uri = getRequestUri("card_qr_ticket_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(qr_uri, token.getAccessToken()),
				JSON.toJSONString(parameter));
		QRResult result = response.getAsObject(new TypeReference<QRResult>() {
		});
		qr_uri = String.format(getRequestUri("qr_image_uri"),
				result.getTicket());
		response = weixinExecutor.get(qr_uri);
		result.setShowUrl(qr_uri);
		try {
			result.setContent(IOUtil.toByteArray(response.getBody()));
		} catch (IOException e) {
			throw new WeixinException(e);
		}
		return result;
	}

	/**
	 * 由于卡券有审核要求，为方便公众号调试，可以设置一些测试帐号，这些帐号可领取未通过审核的卡券，体验整个流程。
	 * 1.同时支持“openid”、“username”两种字段设置白名单，总数上限为10个。
	 * 2.设置测试白名单接口为全量设置，即测试名单发生变化时需调用该接口重新传入所有测试人员的ID.
	 * 3.白名单用户领取该卡券时将无视卡券失效状态，请开发者注意。
	 * 
	 * @param openIds
	 *            the open ids
	 * @param userNames
	 *            the user names
	 * @author fengyapeng
	 * @since 2016 -12-20 11:22:57
	 * @see <a href=
	 *      'https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062&token=&lang=zh_CN&anchor=6'>设置测试白名单</
	 *      a >
	 */
	public ApiResult setTestWhiteList(List<String> openIds,
			List<String> userNames) throws WeixinException {
		JSONObject requestObj = new JSONObject();
		if (openIds != null && openIds.size() > 0) {
			requestObj.put("openid", openIds);
		}
		if (userNames != null && userNames.size() > 0) {
			requestObj.put("username", userNames);
		}
		String card_set_test_whitelist_uri = getRequestUri("card_set_test_whitelist_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_set_test_whitelist_uri,
						token.getAccessToken()), requestObj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 查看获取卡券的审核状态
	 * 
	 * @see <a href=
	 *      'https://mp.weixin.qq.com/wiki?action=doc&id=mp1451025272&t=0.18670321276182844#3'
	 *      > 查看卡券详情</a>
	 *
	 * @author fengyapeng
	 * @since 2016 -12-20 11:48:23
	 */
	public CardStatus queryCardStatus(String cardId) throws WeixinException {
		JSONObject requestObj = new JSONObject();
		requestObj.put("card_id", cardId);
		String card_get_uri = getRequestUri("card_get_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_get_uri, token.getAccessToken()),
				requestObj.toJSONString());
		JSONObject responseAsJson = response.getAsJson();
		JSONObject card = responseAsJson.getJSONObject("card");
		String cardType = card.getString("card_type");
		JSONObject baseInfo = card.getJSONObject(cardType.toLowerCase())
				.getJSONObject("base_info");
		String status = baseInfo.getString("status");
		return CardStatus.valueOf(status);
	}

	/**
	 * 支持更新所有卡券类型的部分通用字段及特殊卡券（会员卡、飞机票、电影票、会议门票）中特定字段的信息。
	 *
	 * @param cardId
	 *            the card id
	 * @param card
	 *            the card
	 * @return 是否提交审核，false为修改后不会重新提审，true为修改字段后重新提审，该卡券的状态变为审核中
	 * @throws WeixinException
	 *             the weixin exception
	 * @author fengyapeng
	 * @see
	 * @since 2016 -12-21 15:29:10
	 */
	public Boolean updateCardCoupon(String cardId, CardCoupon card)
			throws WeixinException {
		JSONObject request = new JSONObject();
		request.put("card_id", cardId);
		CardType cardType = card.getCardType();
		card.cleanCantUpdateField();
		request.put(cardType.name().toLowerCase(), card);
		String card_update_uri = getRequestUri("card_update_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_update_uri, token.getAccessToken()),
				JSON.toJSONString(request));
		JSONObject jsonObject = response.getAsJson();
		return jsonObject.getBoolean("send_check");
	}

	/**
	 * 激活方式说明 接口激活通常需要开发者开发用户填写资料的网页。通常有两种激活流程： 1.
	 * 用户必须在填写资料后才能领卡，领卡后开发者调用激活接口为用户激活会员卡； 2.
	 * 是用户可以先领取会员卡，点击激活会员卡跳转至开发者设置的资料填写页面，填写完成后开发者调用激活接口为用户激活会员卡。
	 *
	 * @see <a href=
	 *      'https://mp.weixin.qq.com/wiki?action=doc&id=mp1451025283&t=0.8029895777585161#6.1'>接口激活</
	 *      a >
	 */
	public ApiResult activateMemberCard(MemberInitInfo memberInitInfo)
			throws WeixinException {
		String card_member_card_activate_uri = getRequestUri("card_member_card_activate_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_member_card_activate_uri,
						token.getAccessToken()),
				JSON.toJSONString(memberInitInfo));
		return response.getAsResult();
	}

	/**
	 * 设置开卡字段接口 开发者在创建时填入wx_activate字段后， 需要调用该接口设置用户激活时需要填写的选项，否则一键开卡设置不生效。
	 *
	 * @see <a href=
	 *      'https://mp.weixin.qq.com/wiki?action=doc&id=mp1451025283&t=0.8029895777585161#6.2'>一键激活</
	 *      a >
	 */
	public ApiResult setActivateUserForm(MemberUserForm memberUserForm)
			throws WeixinException {
		String user_form_uri = getRequestUri("card_member_card_activate_user_form_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(user_form_uri, token.getAccessToken()),
				JSON.toJSONString(memberUserForm));
		return response.getAsResult();
	}

	/**
	 * 拉取会员信息接口。
	 *
	 * @param cardId
	 *            the card id
	 * @param code
	 *            the code
	 * @author fengyapeng
	 * @since 2016 -12-21 11:28:45
	 */
	public MemberUserInfo getMemberUserInfo(String cardId, String code)
			throws WeixinException {
		String user_info_uri = getRequestUri("card_member_card_user_info_uri");
		Token token = tokenManager.getCache();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("card_id", cardId);
		jsonObject.put("code", code);
		WeixinResponse response = weixinExecutor.post(
				String.format(user_info_uri, token.getAccessToken()),
				JSON.toJSONString(jsonObject));
		return response.getAsObject(new TypeReference<MemberUserInfo>() {
		});
	}

	/**
	 * 更新会员 result_bonus 当前用户积分总额 result_balance 当前用户预存总金额 openid 用户openid
	 * 
	 * @param updateInfo
	 * @return
	 * @throws WeixinException
	 */
	public JSONObject updateMemberUserInfo(MemberUpdateInfo updateInfo)
			throws WeixinException {
		String card_member_card_update_user_uri = getRequestUri("card_member_card_update_user_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_member_card_update_user_uri,
						token.getAccessToken()), JSON.toJSONString(updateInfo));
		return response.getAsJson();
	}
}
