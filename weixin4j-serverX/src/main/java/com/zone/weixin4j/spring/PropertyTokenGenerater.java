package com.zone.weixin4j.spring;

import com.zone.weixin4j.util.AesToken;

import java.util.List;

/**
 * Created by Yz on 2017/3/15.
 * 配置文件中获取Token
 */
public class PropertyTokenGenerater extends TokenGenerater {

    private List<AesToken> aesTokens;

    @Override
    public List<AesToken> getAesTokens() {
        return aesTokens;
    }

    public void setAesTokens(List<AesToken> aesTokens) {
        this.aesTokens = aesTokens;
    }

}
