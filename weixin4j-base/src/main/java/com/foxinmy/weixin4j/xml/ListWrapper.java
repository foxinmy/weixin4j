package com.foxinmy.weixin4j.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

public class ListWrapper<T> implements Serializable {

	private static final long serialVersionUID = 7550802632983954221L;

	private List<T> items;

	public ListWrapper() {
		items = new ArrayList<T>();
	}

	@XmlAnyElement(lax = true)
	public List<T> getItems() {
		return items;
	}
}
