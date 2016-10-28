package com.foxinmy.weixin4j.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.mch.CustomsOrder;
import com.foxinmy.weixin4j.payment.mch.CustomsOrderRecord;
import com.foxinmy.weixin4j.payment.mch.CustomsOrderResult;
import com.foxinmy.weixin4j.type.CustomsCity;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 报关接口
 *
 * @className CustomsApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月67日
 * @since JDK 1.6
 * @see
 */
public class CustomsApi extends MchApi {

	public CustomsApi(WeixinPayAccount weixinAccount) {
		super(weixinAccount);
	}

	/**
	 * 订单附加信息提交
	 *
	 * @param customsOrder
	 *            附加订单信息
	 * @return 报关结果
	 * @see com.foxinmy.weixin4j.payment.mch.CustomsOrder
	 * @see com.foxinmy.weixin4j.payment.mch.CustomsOrderResult
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/external/declarecustom.php?chapter=18_1">附加订单信息提交接口</a>
	 * @throws WeixinException
	 */
	public CustomsOrderResult declareCustomsOrder(CustomsOrder customsOrder)
			throws WeixinException {
		JSONObject para = (JSONObject) JSON.toJSON(customsOrder);
		para.put("appid", weixinAccount.getId());
		para.put("mch_id", weixinAccount.getMchId());
		para.put("sign", weixinSignature.sign(para));
		String param = XmlStream.map2xml(para);
		WeixinResponse response = weixinExecutor.post(
				getRequestUri("customsorder_declare_uri"), param);
		return response.getAsObject(new TypeReference<CustomsOrderResult>() {
		});
	}

	/**
	 * 订单附加信息查询
	 *
	 * @param idQuery
	 *            out_trade_no,transaction_id,sub_order_no,sub_order_id四选一
	 * @param customsCity
	 *            海关
	 * @return 报关记录
	 * @see com.foxinmy.weixin4j.payment.mch.CustomsOrderRecord
	 * @see <a
	 *      href="https://pay.weixin.qq.com/wiki/doc/api/external/declarecustom.php?chapter=18_1">附加订单信息查询接口</a>
	 * @throws WeixinException
	 */
	public CustomsOrderRecord queryCustomsOrder(IdQuery idQuery,
			CustomsCity customsCity) throws WeixinException {
		JSONObject para = new JSONObject();
		para.put("appid", weixinAccount.getId());
		para.put("mch_id", weixinAccount.getMchId());
		para.put(idQuery.getType().getName(), idQuery.getId());
		para.put("customs", customsCity.name());
		para.put("sign", weixinSignature.sign(para));
		String param = XmlStream.map2xml(para);
		WeixinResponse response = weixinExecutor.post(
				getRequestUri("customsorder_query_uri"), param);
		return ListsuffixResultDeserializer.deserialize(response.getAsString(),
				CustomsOrderRecord.class);
	}
}
