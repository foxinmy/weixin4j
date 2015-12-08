package com.foxinmy.weixin4j.http.weixin;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 调用接口返回JSON格式
 * 
 * @className JsonResult
 * @author jy.hu
 * @date 2014年9月24日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/17/fa4e1434e57290788bde25603fa2fcbd.html">公众平台全局返回码说明</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E">企业号全局返回码说明</a>
 */
public class JsonResult implements Serializable {

	private static final long serialVersionUID = -6185313616955051150L;

	@JSONField(name = "errcode")
	private int code;
	@JSONField(name = "errmsg")
	private String desc;
	private String text;

	public JsonResult() {
		this.desc = "";
		this.text = "";
	}

	public JsonResult(int code, String desc, String text) {
		this.code = code;
		this.desc = desc;
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", desc=" + desc + ", text=" + text
				+ "]";
	}
}
