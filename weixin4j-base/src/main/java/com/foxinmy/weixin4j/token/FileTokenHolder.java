package com.foxinmy.weixin4j.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.thoughtworks.xstream.XStream;

/**
 * 用FILE保存TOKEN
 * 
 * @className FileTokenHolder
 * @author jy
 * @date 2015年1月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.token.TokenCreator
 * @see com.foxinmy.weixin4j.token.WeixinTokenCreator
 */
public class FileTokenHolder implements TokenHolder {
	private final XStream xstream;
	private final String tokenPath;
	private final TokenCreator tokenCretor;

	public FileTokenHolder(TokenCreator tokenCretor) {
		this(ConfigUtil.getValue("token_path"), tokenCretor);
	}

	public FileTokenHolder(String tokenPath, TokenCreator tokenCretor) {
		this.tokenPath = tokenPath;
		this.tokenCretor = tokenCretor;
		xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.autodetectAnnotations(true);
		xstream.alias("xml", Token.class);
		xstream.processAnnotations(Token.class);
	}

	@Override
	public Token getToken() throws WeixinException {
		File token_file = new File(String.format("%s/%s.xml", tokenPath,
				tokenCretor.getCacheKey()));
		Token token = null;
		Calendar ca = Calendar.getInstance();
		long now_time = ca.getTimeInMillis();
		try {
			if (token_file.exists()) {
				token = (Token) xstream
						.fromXML(new FileInputStream(token_file));
				long expire_time = token.getTime()
						+ (token.getExpiresIn() * 1000) - 2;
				if (expire_time > now_time) {
					return token;
				}
			}
			token = tokenCretor.createToken();
			xstream.toXML(token, new FileOutputStream(token_file));
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		}
		return token;
	}
}
