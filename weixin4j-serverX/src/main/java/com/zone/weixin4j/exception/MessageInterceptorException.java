package com.zone.weixin4j.exception;

/**
 * Created by Yz on 2017/3/15.
 * 微信消息拦截器异常
 */
public class MessageInterceptorException extends Exception {

    private static final long serialVersionUID = -1094109872039360113L;

    private String code;
    private String msg;

    public MessageInterceptorException(String errorCode, String errorMsg) {
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public MessageInterceptorException(String errorMsg) {
        this.code = "-1";
        this.msg = errorMsg;
    }

    public MessageInterceptorException(Exception e) {
        super(e);
    }

    public MessageInterceptorException(String errorMsg, Exception e) {
        super(e);
        this.msg = errorMsg;
    }

    public String getErrorCode() {
        return code;
    }

    public String getErrorMsg() {
        return msg;
    }

    @Override
    public String getMessage() {
        return this.code + "," + this.msg;
    }

}
