package com.foxinmy.weixin4j.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.foxinmy.weixin4j.api.token.TokenApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.QRParameter;
import com.foxinmy.weixin4j.model.QRParameter.QRType;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 二维码相关API
 * 
 * @className QrApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%94%9F%E6%88%90%E5%B8%A6%E5%8F%82%E6%95%B0%E7%9A%84%E4%BA%8C%E7%BB%B4%E7%A0%81">二维码支持</a>
 */
public class QrApi extends BaseApi {

	private final TokenApi tokenApi;

	public QrApi(TokenApi tokenApi) {
		this.tokenApi = tokenApi;
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param parameter
	 * @return byte数据包
	 * @throws WeixinException
	 * @see {@link com.foxinmy.weixin4j.api.QrApi#getQR(QRParameter)}
	 */
	public byte[] getQRData(QRParameter parameter) throws WeixinException {
		Token token = tokenApi.getToken();
		String qr_uri = ConfigUtil.getValue("qr_ticket_uri");
		Response response = request.post(
				String.format(qr_uri, token.getAccessToken()),
				parameter.toJson());
		String ticket = response.getAsJson().getString("ticket");
		qr_uri = ConfigUtil.getValue("qr_image_uri");
		response = request.get(String.format(qr_uri, ticket));

		return response.getBody();
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param sceneId
	 *            场景值
	 * @param expireSeconds
	 *            过期秒数 如果小于等于0则 视为永久二维码
	 * @return byte数据包
	 * @throws WeixinException
	 * @see {@link com.foxinmy.weixin4j.api.QrApi#getQR(QRParameter)}
	 */
	public byte[] getQRData(int sceneId, int expireSeconds)
			throws WeixinException {
		QRParameter parameter = new QRParameter(sceneId, QRType.TEMPORARY,
				expireSeconds);
		if (expireSeconds <= 0) {
			parameter.setQrType(QRType.PERMANENCE);
		}
		return getQRData(parameter);
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
	 * @throws FileNotFoundException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%94%9F%E6%88%90%E5%B8%A6%E5%8F%82%E6%95%B0%E7%9A%84%E4%BA%8C%E7%BB%B4%E7%A0%81">二维码</a>
	 * @see com.foxinmy.weixin4j.model.QRParameter
	 */
	public File getQR(QRParameter parameter) throws WeixinException,
			IOException {
		String qr_path = ConfigUtil.getValue("qr_path");
		String filename = String.format("%s_%d_%d.jpg", parameter.getQrType()
				.name(), parameter.getSceneId(), parameter.getExpireSeconds());
		File file = new File(qr_path + File.separator + filename);
		if (parameter.getQrType() == QRType.PERMANENCE && file.exists()) {
			return file;
		}
		byte[] datas = getQRData(parameter);
		FileOutputStream out = null;
		try {
			file.createNewFile();
		} catch (IOException e) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		out = new FileOutputStream(file);
		out.write(datas);
		out.close();
		return file;
	}
}
