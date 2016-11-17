package com.foxinmy.weixin4j.mp.api;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.qr.QRParameter;
import com.foxinmy.weixin4j.model.qr.QRResult;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.IOUtil;

/**
 * 二维码相关API
 * 
 * @className QrApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月25日
 * @since JDK 1.6
 */
public class QrApi extends MpApi {

	private final TokenManager tokenManager;

	public QrApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param parameter
	 *            二维码参数
	 * @return 二维码结果对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.qr.QRResult
	 * @see com.foxinmy.weixin4j.model.qr.QRParameter
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN">生成二维码</a>
	 */
	public QRResult createQR(QRParameter parameter) throws WeixinException {
		Token token = tokenManager.getCache();
		String qr_uri = getRequestUri("qr_ticket_uri");
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
}
