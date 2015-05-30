package com.foxinmy.weixin4j.mp.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.QRParameter;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 二维码相关API
 * 
 * @className QrApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">二维码支持</a>
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
	 * @return 二维码图片解析后的地址 开发者可根据该地址自行生成需要的二维码图片
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">生成二维码</a>
	 */
	public String getQRUrl(QRParameter parameter) throws WeixinException {
		return doQR(parameter).getString("url");
	}

	private JSONObject doQR(QRParameter parameter) throws WeixinException {
		Token token = tokenHolder.getToken();
		String qr_uri = getRequestUri("qr_ticket_uri");
		WeixinResponse response = weixinClient.post(
				String.format(qr_uri, token.getAccessToken()),
				parameter.getContent());
		return response.getAsJson();
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param parameter
	 *            二维码参数
	 * @return byte数据包
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.QRParameter
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">生成二维码</a>
	 */
	public byte[] getQRData(QRParameter parameter) throws WeixinException {
		String ticket = doQR(parameter).getString("ticket");
		String qr_uri = getRequestUri("qr_image_uri");
		WeixinResponse response = weixinClient.get(String
				.format(qr_uri, ticket));

		return response.getContent();
	}

	/**
	 * 生成带参数的二维码
	 * <p>
	 * 二维码分为临时跟永久两种,扫描时触发推送带参数事件
	 * </p>
	 * 
	 * @param parameter
	 *            二维码参数
	 * @return 硬盘存储的文件对象
	 * @throws WeixinException
	 * @see <a
	 *      href="mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">二维码</a>
	 * @see com.foxinmy.weixin4j.mp.model.QRParameter
	 */
	public File getQRFile(QRParameter parameter) throws WeixinException {
		String qr_path = ConfigUtil.getValue("qr_path");
		String filename = String.format("%s_%s_%d.jpg", parameter.getQrType()
				.name(), parameter.getSceneValue(), parameter
				.getExpireSeconds());
		File file = new File(qr_path + File.separator + filename);
		if (parameter.getQrType().ordinal() > 0 && file.exists()) {
			return file;
		}
		byte[] datas = getQRData(parameter);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			os.write(datas);
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException ignore) {
				;
			}
		}
		return file;
	}
}
