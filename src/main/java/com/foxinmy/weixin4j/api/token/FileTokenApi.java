package com.foxinmy.weixin4j.api.token;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.jsoup.helper.StringUtil;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.xml.XStream;

/**
 * 基于文件保存的Token获取类
 * 
 * @className FileTokenApi
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class FileTokenApi extends AbstractTokenApi {

	private final HttpRequest request = new HttpRequest();

	private final String appid;
	private final String appsecret;

	public FileTokenApi() {
		this.appid = getAppid();
		this.appsecret = getAppsecret();
	}

	public FileTokenApi(String appid, String appsecret) {
		this.appid = appid;
		this.appsecret = appsecret;
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
		if (StringUtil.isBlank(appid) || StringUtil.isBlank(appsecret)) {
			throw new IllegalArgumentException(
					"appid or appsecret not be null!");
		}
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(Token.class);
		File token_file = new File(String.format("%s/token_%s.xml",
				ConfigUtil.getValue("token_path"), appid));
		Token token = null;
		Calendar ca = Calendar.getInstance();
		long now_time = ca.getTimeInMillis();
		try {
			String api_token_uri = String.format(
					ConfigUtil.getValue("api_token_uri"), appid, appsecret);
			Response response = null;
			if (token_file.exists()) {
				token = (Token) xstream.fromXML(token_file);

				long expise_time = token.getTime()
						+ (token.getExpiresIn() * 1000) - 3;
				if (expise_time > now_time) {
					return token;
				}
				response = request.get(api_token_uri);
			} else {
				response = request.get(api_token_uri);
				try {
					token_file.createNewFile();
				} catch (IOException e) {
					token_file.getParentFile().mkdirs();
				}
			}
			token = response.getAsObject(Token.class);
			token.setTime(now_time);
			token.setOpenid(appid);
			xstream.toXML(token, new FileOutputStream(token_file));
		} catch (IOException e) {
			;
		}
		return token;
	}
}
