package com.foxinmy.weixin4j.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 用FILE保存TOKEN
 * 
 * @className FileTokenHolder
 * @author jy
 * @date 2015年1月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.token.TokenCreator
 */
public class FileTokenHolder implements TokenHolder {
	private final String tokenPath;
	private final TokenCreator tokenCreator;

	public FileTokenHolder(TokenCreator tokenCreator) {
		this(ConfigUtil.getValue("token_path"), tokenCreator);
	}

	public FileTokenHolder(String tokenPath, TokenCreator tokenCreator) {
		this.tokenPath = tokenPath;
		this.tokenCreator = tokenCreator;
	}

	@Override
	public Token getToken() throws WeixinException {
		File token_file = new File(String.format("%s/%s.xml", tokenPath,
				tokenCreator.getCacheKey()));
		Token token = null;
		Calendar ca = Calendar.getInstance();
		long now_time = ca.getTimeInMillis();
		try {
			if (token_file.exists()) {
				token = XmlStream.fromXML(new FileInputStream(token_file),
						Token.class);
				long expire_time = token.getTime()
						+ (token.getExpiresIn() * 1000) - 2;
				if (expire_time > now_time) {
					return token;
				}
			}
			token = tokenCreator.createToken();
			XmlStream.toXML(token, new FileOutputStream(token_file));
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		}
		return token;
	}
}
