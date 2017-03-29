package com.zone.weixin4j.spring;

import com.zone.weixin4j.util.AesToken;

import java.util.List;

/**
 * Created by Yz on 2017/3/15.
 * 生成Token
 */
public abstract class TokenGenerater {

    abstract public List<AesToken> getAesTokens();

}
