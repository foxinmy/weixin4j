 package com.foxinmy.weixin4j.mp.api;

import java.io.IOException;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.QRParameter;
import com.foxinmy.weixin4j.mp.model.QRResult;
import com.foxinmy.weixin4j.token.TokenHolder;
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

	private final TokenHolder tokenHolder;

	public QrApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param parameter
	 *            二维码参数
	 * @return 二维码结果对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.QRResult
	 * @see com.foxinmy.weixin4j.mp.model.QRParameter
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN">生成二维码</a>
	 */
	public QRResult createQR(QRParameter parameter) throws WeixinException {
		Token token = tokenHolder.getToken();
		String qr_uri = getRequestUri("qr_ticket_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(qr_uri, token.getAccessToken()),
				parameter.getContent());
		QRResult result = response.getAsObject(new TypeReference<QRResult>() {
		});
		qr_uri = getRequestUri("qr_image_uri");
		response = weixinExecutor
				.get(String.format(qr_uri, result.getTicket()));
		try {
			result.setContent(IOUtil.toByteArray(response.getBody()));
		} catch (IOException e) {
			throw new WeixinException(e);
		}
		return result;
	}
}
