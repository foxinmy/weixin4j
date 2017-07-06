package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.cache.CacheManager;
import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 对token的缓存获取
 *
 * @className TokenManager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月12日
 * @since JDK 1.6
 * @see TokenCreator
 * @see CacheStorager
 */
public class TokenManager extends CacheManager<Token> {
    /**
     *
     * @param tokenCreator
     *            负责微信各种token的创建
     * @param cacheStorager
     *            负责token的存储
     */
    public TokenManager(TokenCreator tokenCreator, CacheStorager<Token> cacheStorager) {
        super(tokenCreator, cacheStorager);
    }

    /**
     * 获取token字符串
     *
     * @return token字符串
     * @throws WeixinException
     */
    public String getAccessToken() throws WeixinException {
        return super.getCache().getAccessToken();
    }

    /**
     * 返回唯一标识ID
     *
     * @return
     */
    public String getWeixinId() {
        return ((TokenCreator) cacheCreator).uniqueid();
    }
}
