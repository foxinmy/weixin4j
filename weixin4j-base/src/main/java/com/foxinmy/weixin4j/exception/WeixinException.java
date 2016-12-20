package com.foxinmy.weixin4j.exception;

import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.WeixinErrorUtil;
import com.foxinmy.weixin4j.util.WeixinErrorUtil2;

/**
 * 调用微信接口抛出的异常
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className WeixinException
 * @date 2014年4月10日
 * @see
 * @since JDK 1.6
 */
public class WeixinException extends Exception {

    private static final long serialVersionUID = 7148145661883468514L;

    private String code;
    private String desc;
    private String describeErrorMsg;

    public WeixinException(String code, String desc) {
        this(code, desc, null);
    }


    public WeixinException(String errorCode, String errorMsg, String describeErrorMsg) {
        this.code = errorCode;
        this.desc = errorMsg;
        if (describeErrorMsg == null) {
            this.describeErrorMsg = errorMsg;
        } else {
            this.describeErrorMsg = describeErrorMsg;
        }
    }

    public WeixinException(String desc) {
        this.code = "-1";
        this.desc = desc;
    }

    public String getDescribeErrorMsg() {
        return describeErrorMsg;
    }


    public WeixinException(Throwable e) {
        super(e);
    }

    public WeixinException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return code;
    }

    public String getErrorDesc() {
        return desc;
    }

    public String getErrorText() {
        return WeixinErrorUtil2.getText(code);
    }

    @Override
    public String getMessage() {
        if (StringUtil.isNotBlank(code)) {
            StringBuilder buf = new StringBuilder();
            buf.append(code).append(" >> ").append(desc);
            String text = getErrorText();
            if (StringUtil.isNotBlank(text)) {
                buf.append(" >> ").append(text);
            }
            return buf.toString();
        } else {
            return super.getMessage();
        }
    }
}
