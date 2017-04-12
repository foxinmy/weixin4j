package com.zone.weixin4j.controller;

import com.zone.weixin4j.exception.HttpResponseException;
import com.zone.weixin4j.exception.MessageInterceptorException;
import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.response.WeixinResponse;
import com.zone.weixin4j.service.WeiXin4jContextAware;
import com.zone.weixin4j.service.WxService;
import com.zone.weixin4j.util.AesToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Yz on 2017/3/14.
 * WxController
 * Spring 主入口需继承的类 / 模版
 */

public abstract class WxController {

    private final Log logger = LogFactory.getLog(getClass());

    private final String defaultCharset = "UTF-8";

    @Autowired
    protected WxService wxService;

    @Autowired
    protected WeiXin4jContextAware weiXin4jContextAware;

    protected abstract void doRequest(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String encrypt_type, @RequestParam(required = false) String echostr, @RequestParam(required = false) String timestamp, @RequestParam(required = false) String nonce,
                                      @RequestParam(required = false) String signature, @RequestParam(required = false) String msg_signature, @RequestParam(required = false) String weixin_id);

    protected void processMessage(HttpServletRequest request, HttpServletResponse response, String encrypt_type, String echostr, String timestamp, String nonce,
                                  String signature, String msg_signature, String weixin_id) {
        String messageContent = getMessageContent(request);
        logger.info("read original message {}" + messageContent);
        AesToken aesToken = weiXin4jContextAware.getAesTokenMap().get(StringUtils.isEmpty(weixin_id) ? "" : weixin_id);
        WeixinResponse weixinResponse = null;
        try {
            weixinResponse = wxService.processRequest(request.getRequestURI(), encrypt_type, echostr, timestamp, nonce, signature, msg_signature, messageContent, aesToken, request);
            response.setCharacterEncoding(defaultCharset);
            writeMessage(wxService.transferResponse(weixinResponse), response);
        } catch (WeixinException e) {
            logger.error("errorCode : " + e.getErrorCode() + " , errorMsg :  " + e.getErrorMsg(), e.getCause());
            e.printStackTrace();
            writeMessage("", response);
        } catch (HttpResponseException e) {
            logger.error(e.getMessage(), e.getCause());
            response.setStatus(e.getHttpResponseStatus().getCode());
            response.setContentType(e.getHttpResponseStatus().getReasonPhrase());
            writeMessage("", response);
        } catch (MessageInterceptorException e) {
            logger.error("errorCode : " + e.getErrorCode() + " , errorMsg :  " + e.getErrorMsg(), e.getCause());
            writeMessage("", response);
        }
    }

    protected String getMessageContent(HttpServletRequest request) {
        try {
            // 从request中取得输入流
            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, defaultCharset));
            String line;
            StringBuilder buf = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            reader.close();
            inputStream.close();
            return buf.toString();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
        return "";
    }

    protected void writeMessage(String result, HttpServletResponse response) {
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

    public void setWxService(WxService wxService) {
        this.wxService = wxService;
    }

    public void setWeiXin4jContextAware(WeiXin4jContextAware weiXin4jContextAware) {
        this.weiXin4jContextAware = weiXin4jContextAware;
    }

    public WeiXin4jContextAware getWeiXin4jContextAware() {
        return weiXin4jContextAware;
    }
}
