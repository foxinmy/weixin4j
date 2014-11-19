package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

public class NameValue implements Serializable {

	private static final long serialVersionUID = -348620146718819093L;
	private String name;
	private String value;

	public NameValue() {

	}

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

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NameValue [name=" + name + ", value=" + value + "]";
	}
}
