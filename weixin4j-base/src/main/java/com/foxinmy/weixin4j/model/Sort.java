package com.foxinmy.weixin4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Sort implements Serializable {

	private static final long serialVersionUID = -4298853295391613880L;

	public static final Direction DEFAULT_DIRECTION = Direction.ASC;
	private Map<Direction, List<String>> orders;
	public Sort() {
	}
	public Sort(String... properties) {
		this(DEFAULT_DIRECTION, properties);
	}

	public Sort(Direction direction, String... properties) {
		this(direction, properties == null ? new ArrayList<String>() : Arrays
				.asList(properties));
	}

	public Sort(Direction direction, List<String> properties) {
		if (properties == null || properties.isEmpty()) {
			throw new IllegalArgumentException(
					"You have to provide at least one property to sort by!");
		}
		this.orders = new LinkedHashMap<Direction, List<String>>(
				properties.size());
		this.orders.put(direction, properties);
	}

	public Map<Direction, List<String>> getOrders() {
		return orders;
	}
	public Map.Entry<String, String> getFirst() {
		if (hasSort()) {
			Entry<Direction, List<String>> firstEntry = orders.entrySet()
					.iterator().next();
			Map<String, String> firstMap = new HashMap<String, String>();
			firstMap.put(firstEntry.getKey().name().toLowerCase(), firstEntry
					.getValue().get(0));
			return firstMap.entrySet().iterator().next();
		}
		return null;
	}
	public boolean hasSort() {
		return orders != null && !orders.isEmpty();
	}

	public static enum Direction {
		ASC, DESC;
	}

	@Override
	public String toString() {
		return "Sort [" + orders + "]";
	}
}
