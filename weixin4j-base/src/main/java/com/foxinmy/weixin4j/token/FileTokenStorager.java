package com.foxinmy.weixin4j.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
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

	private final String cachePath;

	public FileTokenStorager(String cachePath) {
		this.cachePath = cachePath;
	}

	@Override
	public Token lookup(String cacheKey) throws WeixinException {
		File token_file = new File(String.format("%s/%s.xml", cachePath,
				cacheKey));
		try {
			if (token_file.exists()) {
				Token token = XmlStream.fromXML(
						new FileInputStream(token_file), Token.class);
				if (token.getTime() < 0) {
					return token;
				}
				if ((token.getTime() + (token.getExpiresIn() * 1000l) - 2) > System
						.currentTimeMillis()) {
					return token;
				}
			}
			return null;
		} catch (IOException e) {
			throw new WeixinException(e);
		}
	}

	@Override
	public void caching(String cacheKey, Token token) throws WeixinException {
		try {
			XmlStream.toXML(
					token,
					new FileOutputStream(new File(String.format("%s/%s.xml",
							cachePath, cacheKey))));
		} catch (IOException e) {
			throw new WeixinException(e);
		}
	}
}
