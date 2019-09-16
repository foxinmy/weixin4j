package com.foxinmy.weixin4j.mp.api;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.card.*;
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
	 * 查询某个card_id的创建信息、审核状态以及库存数量。
	 *
	 * @param cardId
	 * @return
	 * @throws WeixinException
	 */
	public JSONObject getCardInfo(String cardId) throws WeixinException {
		JSONObject requestObj = new JSONObject();
		requestObj.put("card_id", cardId);
		String card_get_uri = getRequestUri("card_get_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_get_uri, token.getAccessToken()),
				requestObj.toJSONString());
		JSONObject responseJson = response.getAsJson();
		return responseJson.getJSONObject("card");
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

	/**
	 * 创建一个礼品卡货架
	 *
	 * @param page
	 * @return 货架ID
	 * @throws WeixinException
	 */
	public String addGiftCardPage(GiftCardPage page) throws WeixinException {
		String card_gift_card_page_add = getRequestUri("card_gift_card_page_add_uri");
		JSONObject pageJson = new JSONObject();
		pageJson.put("page", page);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_page_add,
						token.getAccessToken()), JSON.toJSONString(pageJson));
		JSONObject jsonObject = response.getAsJson();
		return jsonObject.getString("page_id");
	}

	/**
	 * 查询礼品卡货架信息
	 *
	 * @param pageId
	 * 			货架ID
	 * @return
	 * @throws WeixinException
	 */
	public JSONObject getGiftCardPage(String pageId) throws WeixinException {
		String card_gift_card_page_get = getRequestUri("card_gift_card_page_get_uri");
		JSONObject param = new JSONObject();
		param.put("page_id", pageId);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_page_get,
						token.getAccessToken()), JSON.toJSONString(param));
		JSONObject jsonObject = response.getAsJson();

		return jsonObject.getJSONObject("page");
	}

	/**
	 * 查询当前商户下所有的礼品卡货架id
	 *
	 * @return
	 * @throws WeixinException
	 */
	public String[] getGiftCardPageIdList() throws WeixinException {
		String card_gift_card_page_batchget = getRequestUri("card_gift_card_page_batchget_uri");
		JSONObject param = new JSONObject();
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_page_batchget,
						token.getAccessToken()), JSON.toJSONString(param));
		JSONObject jsonObject = response.getAsJson();
		JSONArray idList = jsonObject.getJSONArray("page_id_list");
		if(idList==null || idList.size()==0){
			return new String[0];
		}

		return idList.toArray(new String[idList.size()]);
	}

	/**
	 * 下架礼品卡货架
	 *
	 * @param pageId
	 * 			礼品卡货架ID
	 * @return
	 * @throws WeixinException
	 */
	public ApiResult maintainGiftCardPage(String pageId) throws WeixinException {
		String card_gift_card_maintain_set = getRequestUri("card_gift_card_maintain_set_uri");
		JSONObject param = new JSONObject();
		param.put("page_id", pageId);
		param.put("maintain", true);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_maintain_set,
						token.getAccessToken()), JSON.toJSONString(param));
		return response.getAsResult();
	}

	/**
	 * 下架所有礼品卡货架
	 *
	 * @return
	 * @throws WeixinException
	 */
	public ApiResult maintainAllGiftCardPage() throws WeixinException {
		String card_gift_card_maintain_set = getRequestUri("card_gift_card_maintain_set_uri");
		JSONObject param = new JSONObject();
		param.put("all", true);
		param.put("maintain", true);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_maintain_set,
						token.getAccessToken()), JSON.toJSONString(param));
		return response.getAsResult();
	}

	/**
	 * 查询某个订单号对应的订单详情
	 *
	 * @param orderId
	 * 			礼品卡订单号，商户可以通过购买成功的事件推送或者批量查询订单接口获得
	 * @return
	 * @throws WeixinException
	 */
	public JSONObject getOrderInfo(String orderId) throws WeixinException {
		String card_gift_card_order_get = getRequestUri("card_gift_card_order_get_uri");
		JSONObject param = new JSONObject();
		param.put("order_id", orderId);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_order_get,
						token.getAccessToken()), JSON.toJSONString(param));

		return response.getAsJson();
	}

	/**
	 * 批量查询礼品卡订单信息接口
	 *
	 * @param beginTime
	 * 			查询的时间起点，十位时间戳（utc+8）
	 * @param endTime
	 * 			查询的时间终点，十位时间戳（utc+8）
	 * @param sortType
	 * 			填"ASC" / "DESC"，表示对订单创建时间进行“升 / 降”排序
	 * @param offset
	 * 			查询的订单偏移量，如填写100则表示从第100个订单开始拉取
	 * @param limit
	 * 			查询订单的数量，如offset填写100，count填写10，则表示查询第100个到第110个订单
	 * @return
	 * @throws WeixinException
	 */
	public JSONObject getOrders(long beginTime, long endTime, String sortType, int offset, int limit) throws WeixinException {
		String card_gift_card_order_batchget_uri = getRequestUri("card_gift_card_order_batchget_uri");
		JSONObject param = new JSONObject();
		param.put("begin_time", beginTime);
		param.put("end_time", endTime);
		param.put("sort_type", sortType);
		param.put("offset", offset);
		param.put("count", limit);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_order_batchget_uri,
						token.getAccessToken()), JSON.toJSONString(param));

		return response.getAsJson();
	}

	/**
	 * 更新礼品卡货架
	 *
	 * @param page
	 * @return
	 * @throws WeixinException
	 */
	public ApiResult updateGiftCardPage(GiftCardPage page) throws WeixinException {
		String card_gift_card_page_update_uri = getRequestUri("card_gift_card_page_update_uri");
		JSONObject pageJson = new JSONObject();
		pageJson.put("page", page);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_page_update_uri,
						token.getAccessToken()), JSON.toJSONString(pageJson));
		return response.getAsResult();
	}

	/**
	 * 申请礼品卡的微信支付权限
	 *
	 * @param subMchId
	 * 			微信支付子商户号，须为普通服务商模式或者直连商户号，建议为礼品卡专用商户号；商户号必须为公众号申请的商户号
	 * 			公众号须与商户号同主体，非同主体情况须和对接人联系申请
	 * @return 商户平台确认地址，请获得后点击打开登录商户平台后台并点击确认
	 * @throws WeixinException
	 */
	public String addGiftCardPayWhitelist(String subMchId) throws WeixinException{
		String card_gift_card_pay_whitelist_add = getRequestUri("card_gift_card_pay_whitelist_add_uri");
		JSONObject param = new JSONObject();
		param.put("sub_mch_id", subMchId);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_pay_whitelist_add,
						token.getAccessToken()), JSON.toJSONString(param));
		JSONObject jsonObject = response.getAsJson();
		return jsonObject.getString("url");
	}

	/**
	 * 绑定商户号到礼品卡小程序
	 *
	 * @param wxaAppid
	 * 			礼品卡小程序APPID
	 * @param subMchId
	 * 			微信支付商户号
	 * @return
	 * @throws WeixinException
	 */
	public ApiResult bindGiftCardPaySubMch(String wxaAppid, String subMchId) throws WeixinException {
		String card_gift_card_pay_submch_bind = getRequestUri("card_gift_card_pay_submch_bind_uri");
		JSONObject param = new JSONObject();
		param.put("sub_mch_id", subMchId);
		param.put("wxa_appid", wxaAppid);

		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_pay_submch_bind,
						token.getAccessToken()), JSON.toJSONString(param));

		return response.getAsResult();
	}

	/**
	 * 上传礼品卡小程序代码
	 * （提供小程序APPID及货架ID，由微信平台为你小程序帐号上传一套现成的礼品卡小程序，直接用于礼品卡售卖）
	 *
	 * @param wxaAppid
	 * 			微信小程序APPID
	 * @param pageId
	 * 			礼品卡货架ID
	 * @return
	 * @throws WeixinException
	 */
	public ApiResult setGiftCardWxaCode(String wxaAppid, String pageId) throws WeixinException {
		String card_gift_card_wxa_set = getRequestUri("card_gift_card_wxa_set_uri");
		JSONObject param = new JSONObject();
		param.put("wxa_appid", wxaAppid);
		param.put("page_id", pageId);

		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_wxa_set,
						token.getAccessToken()), JSON.toJSONString(param));

		return response.getAsResult();
	}

	/**
	 * 更新用户礼品卡信息
	 * 当礼品卡被使用后，可以通过该接口变更某个礼品卡的余额信息。
	 *
	 * @param cardInfo
	 * @return
	 * @throws WeixinException
	 */
	public JSONObject updateGiftCardUserBalance(CardInfo cardInfo) throws WeixinException {
		String card_gift_card_wxa_set = getRequestUri("card_general_card_update_user_uri");

		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_wxa_set,
						token.getAccessToken()), JSON.toJSONString(cardInfo));
		return response.getAsJson();
	}

	/**
	 * 当礼品卡被使用完毕或者发生转存、绑定等操作后，开发者可以通过该接口核销用户的礼品卡，使礼品卡在列表中沉底并不再被使用。
	 *
	 * @param code
	 * 			卡券Code码。
	 * @param cardId
	 * 			卡券ID,自定义code卡券必填，否则非必填。
	 * @return
	 * @throws WeixinException
	 */
	public ApiResult consumeGiftCard(String code, String cardId) throws WeixinException {
		String card_code_consume = getRequestUri("card_code_consume_uri");
		JSONObject param = new JSONObject();
		param.put("code", code);
		if(cardId!=null && cardId.length()>0){
			param.put("card_id", cardId);
		}

		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_code_consume,
						token.getAccessToken()), JSON.toJSONString(param));

		return response.getAsResult();
	}

	/**
	 * 开发者可以通过该接口查询到code对应的信息，如余额、有效期、订单号等，主要用于防止在交易完成后丢单的情况下，用于核销/余额变动时兜底处理。
	 * 注意：需在礼品卡核销前调用，否则会报40099 已核销的错误
	 *
	 * @param code
	 * 			卡券Code码
	 * @param cardId
	 * 			卡券ID,自定义code卡券必填，否则非必填。
	 * @return
	 * @throws WeixinException
	 */
	public JSONObject getGiftCardInfo(String code, String cardId) throws WeixinException {
		String card_code_get = getRequestUri("card_code_get_uri");
		JSONObject param = new JSONObject();
		param.put("code", code);
		if(cardId!=null && cardId.length()>0){
			param.put("card_id", cardId);
		}

		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_code_get,
						token.getAccessToken()), JSON.toJSONString(param));

		return response.getAsJson();
	}

	/**
	 * 对一笔礼品卡订单操作退款
	 *
	 * @param orderId
	 * 			订单ID
	 * @return
	 * @throws WeixinException
	 */
	public ApiResult orderRefund(String orderId) throws WeixinException {
		String card_gift_card_order_refund_uri = getRequestUri("card_gift_card_order_refund_uri");
		JSONObject param = new JSONObject();
		param.put("order_id", orderId);

		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(card_gift_card_order_refund_uri,
						token.getAccessToken()), JSON.toJSONString(param));

		return response.getAsResult();
	}
}
