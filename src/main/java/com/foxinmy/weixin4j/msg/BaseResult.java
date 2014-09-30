package com.foxinmy.weixin4j.msg;

import java.io.Serializable;

/**
 * 调用接口响应值
 * 
 * @className BaseResult
 * @author jy.hu
 * @date 2014年9月24日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E">全局返回码</a>
 */
public class BaseResult implements Serializable {

	private static final long serialVersionUID = -6185313616955051150L;

	private int errcode;
	private String errmsg;
	private String msgid;
	private String text;

	public BaseResult() {

	}

	public BaseResult(int errcode, String errmsg, String text) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.text = text;
	}

	public BaseResult(int errcode, String errmsg, String msgid, String text) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.msgid = msgid;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	@Override
	public String toString() {
		return "BaseResult [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", msgid=" + msgid + ", text=" + text + "]";
	}
}
