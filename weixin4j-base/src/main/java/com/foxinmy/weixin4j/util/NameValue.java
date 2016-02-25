package com.foxinmy.weixin4j.util;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * name-value
 * 
 * @className NameValue
 * @author jy
 * @date 2015年3月29日
 * @since JDK 1.6
 * @see
 */
public class NameValue implements Serializable {

	private static final long serialVersionUID = -348620146718819093L;
	private String name;
	private String value;

	public NameValue(){
		
	}
	
	@JSONCreator
	public NameValue(@JSONField(name = "name") String name,
			@JSONField(name = "value") String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NameValue [name=" + name + ", value=" + value + "]";
	}
}
