package com.foxinmy.weixin4j.http;


/**
 * header like key-value
 * @className Header
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public class Header extends NameValue {

	private static final long serialVersionUID = -9029136315985402716L;

	public Header(String name, String value) {
		super(name, value);
	}

	@Override
	public String toString() {
		return "Header [name=" + getName() + ", value=" + getValue() + "]";
	}
}
