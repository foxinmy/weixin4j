package com.foxinmy.weixin4j.util;

import java.io.Serializable;

public class NameValue implements Serializable {

	private static final long serialVersionUID = 4557003825642138566L;

	private final String name;
	private final String value;

	public NameValue(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
