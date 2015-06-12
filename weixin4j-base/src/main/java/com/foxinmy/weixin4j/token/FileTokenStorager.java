package com.foxinmy.weixin4j.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 用FILE保存TOKEN
 * 
 * @className FileTokenStorager
 * @author jy
 * @date 2015年1月9日
 * @since JDK 1.7
 */
public class FileTokenStorager implements TokenStorager {

	private final String tokenPath;

	public FileTokenStorager() {
		this(ConfigUtil.getValue("token_path"));
	}

	public FileTokenStorager(String tokenPath) {
		this.tokenPath = tokenPath;
	}

	@Override
	public Token lookupToken(String cacheKey) throws WeixinException {
		File token_file = new File(String.format("%s/%s.xml", tokenPath,
				cacheKey));
		try {
			if (token_file.exists()) {
				Token token = XmlStream.fromXML(
						new FileInputStream(token_file), Token.class);
				long expire_time = token.getTime()
						+ (token.getExpiresIn() * 1000) - 2;
				if (expire_time > System.currentTimeMillis()) {
					return token;
				}
			}
			return null;
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		}
	}

	@Override
	public void cachingToken(Token token, String cacheKey)
			throws WeixinException {
		try {
			XmlStream.toXML(
					token,
					new FileOutputStream(new File(String.format("%s/%s.xml",
							tokenPath, cacheKey))));
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		}
	}
}
