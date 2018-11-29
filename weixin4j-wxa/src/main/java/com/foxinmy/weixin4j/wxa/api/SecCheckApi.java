package com.foxinmy.weixin4j.wxa.api;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.apache.content.InputStreamBody;
import com.foxinmy.weixin4j.http.apache.mime.FormBodyPart;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 违法违规内容检查。
 *
 * @since 1.9
 */
public class SecCheckApi extends TokenManagerApi {

	public SecCheckApi(TokenManager tokenManager) {
		super(tokenManager);
	}

	public SecCheckApi(TokenManager tokenManager, Properties properties) {
		super(tokenManager, properties);
	}

	private InputStream scale(InputStream inputStream, int maxWidth, int maxHeight) throws IOException {
		final BufferedImage srcBufferedImage = ImageIO.read(inputStream);

		final int srcWidth = srcBufferedImage.getWidth();
		final int srcHeight = srcBufferedImage.getHeight();
		final float scale = Math.min((float) maxWidth / (float) srcWidth, (float) maxHeight / (float) srcHeight);

		final BufferedImage scaledBufferedImage;
		if (scale < 1F) {
			final int width = (int) (srcWidth * scale);
			final int height = (int) (srcHeight * scale);
			final Image scaledImage = srcBufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);

			scaledBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			scaledBufferedImage.getGraphics().drawImage(scaledImage, 0, 0 , null);
		} else {
			scaledBufferedImage = srcBufferedImage;
		}

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(scaledBufferedImage, "png", outputStream);
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	/**
	 * 校验一张图片是否含有违法违规内容。
	 *
	 * @param inputStream the image input stream.
	 * @throws WeixinException indicates getting access token failed, or the content is risky.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/imgSecCheck.html">校验一张图片是否含有违法违规内容</a>
	 */
	public void imgSecCheck(InputStream inputStream) throws WeixinException {
		final String imgSecCheckUri = this.getAccessTokenRequestUri("wxa_img_sec_check");
		final InputStreamBody inputStreamBody = new InputStreamBody(inputStream, "media");
		final FormBodyPart formBodyPart = new FormBodyPart("media", inputStreamBody);
		final WeixinResponse response = weixinExecutor.post(imgSecCheckUri, formBodyPart);
		final WxaApiResult r = response.getAsObject(WxaApiResult.TYPE_REFERENCE);
		r.checkErrCode();
	}

	public void imgSecCheck(InputStream inputStream, int maxWidth, int maxHeight) throws WeixinException {
		try {
			this.imgSecCheck(this.scale(inputStream, maxWidth, maxHeight));
		} catch (IOException e) {
			throw new WeixinException(e);
		}
	}

	/**
	 * 检查一段文本是否含有违法违规内容。
	 *
	 * @param content 要检测的文本内容，长度不超过 500KB，编码格式为 UTF-8。
	 * @throws WeixinException indicates getting access token failed, or the content is risky.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/msgSecCheck.html">检查一段文本是否含有违法违规内容</a>
	 */
	public void msgSecCheck(String content) throws WeixinException {
		final Map<String, String> params = new HashMap<String, String>(1);
		params.put("content", content);
		final WxaApiResult r = this.post("wxa_msg_sec_check", params, WxaApiResult.TYPE_REFERENCE);
		r.checkErrCode();
	}

}
