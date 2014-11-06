package com.foxinmy.weixin4j.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.xml.XStream;

/**
 * 基于文件保存的Token获取类
 * 
 * @className FileTokenHolder
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class FileTokenHolder extends AbstractTokenHolder {

	public FileTokenHolder() {
		super();
	}

	public FileTokenHolder(String appid, String appsecret) {
		super(appid, appsecret);
	}

	/**
	 * 获取token
	 * <p>
	 * 正常情况下返回{"access_token":"ACCESS_TOKEN","expires_in":7200},否则抛出异常.
	 * </p>
	 * 
	 * @return token对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">获取token说明</a>
	 * @see com.foxinmy.weixin4j.model.Token
	 */
	@Override
	public Token getToken() throws WeixinException {
		String appid = getConfig().getAppId();
		String appsecret = getConfig().getAppSecret();
		if (StringUtils.isBlank(appid) || StringUtils.isBlank(appsecret)) {
			throw new IllegalArgumentException(
					"appid or appsecret not be null!");
		}
		File token_file = new File(String.format("%s/token_%s.xml",
				ConfigUtil.getValue("token_path"), appid));
		Token token = null;
		Calendar ca = Calendar.getInstance();
		long now_time = ca.getTimeInMillis();
		try {
			if (token_file.exists()) {
				token = XStream.get(new FileInputStream(token_file),
						Token.class);

				long expise_time = token.getTime()
						+ (token.getExpiresIn() * 1000) - 3;
				if (expise_time > now_time) {
					return token;
				}
			} else {
				token_file.createNewFile();
			}
			String api_token_uri = String.format(tokenUrl, appid, appsecret);
			Response response = request.get(api_token_uri);
			token = response.getAsObject(new TypeReference<Token>() {
			});
			token.setTime(now_time);
			token.setOpenid(appid);
			XStream.to(token, new FileOutputStream(token_file));
		} catch (IOException e) {
			throw new WeixinException("-1", "IO ERROR");
		}
		return token;
	}
}
