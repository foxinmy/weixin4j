package com.zone.weixin4j.service;

import com.zone.weixin4j.exception.HttpResponseException;
import com.zone.weixin4j.exception.MessageInterceptorException;
import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.WeixinResponse;
import com.zone.weixin4j.util.AesToken;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yz on 2017/3/14.
 * WxServiceImpl
 */
public interface WxService {

    WeixinResponse processRequest(String uri, String encryptType, String echostr, String timestamp, String nonce, String signature, String msg_signature, String messageContent, AesToken aesToken, HttpServletRequest request) throws WeixinException, HttpResponseException, MessageInterceptorException;

    String transferResponse(WeixinResponse weixinResponse) throws WeixinException;

}

