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
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.type.AccountType;
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
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">微信公众平台获取token说明</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%BB%E5%8A%A8%E8%B0%83%E7%94%A8">微信企业号获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class FileTokenHolder extends AbstractTokenHolder {

	public FileTokenHolder(AccountType accountType) {
		super(accountType);
	}

	public FileTokenHolder(WeixinAccount weixinAccount) {
		super(weixinAccount);
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
		String id = weixinAccount.getId();
		if (StringUtils.isBlank(id)
				|| StringUtils.isBlank(weixinAccount.getSecret())) {
			throw new IllegalArgumentException("id or secret not be null!");
		}
		File token_file = new File(String.format("%s/token_%s.xml",
				ConfigUtil.getValue("token_path"), id));
		Token token = null;
		Calendar ca = Calendar.getInstance();
		long now_time = ca.getTimeInMillis();
		try {
			if (token_file.exists()) {
				token = XStream.get(new FileInputStream(token_file),
						Token.class);
				long expire_time = token.getTime()
						+ (token.getExpiresIn() * 1000) - 2;
				if (expire_time > now_time) {
					return token;
				}
			}
			Response response = request.get(weixinAccount.getTokenUrl());
			token = response.getAsObject(new TypeReference<Token>() {
			});
			token.setTime(now_time);
			XStream.to(token, new FileOutputStream(token_file));
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		}
		return token;
	}
}
