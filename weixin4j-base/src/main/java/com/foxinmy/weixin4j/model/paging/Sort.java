package com.foxinmy.weixin4j.model.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
