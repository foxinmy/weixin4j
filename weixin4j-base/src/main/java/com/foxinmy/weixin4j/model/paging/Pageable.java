package com.foxinmy.weixin4j.model.paging;

import java.io.Serializable;

import com.foxinmy.weixin4j.model.paging.Sort.Direction;

/**
 * 分页数据(页码从1开始
 * 
 * @className Pageable
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月27日
 * @since JDK 1.6
 */
public class Pageable implements Serializable {

	private static final long serialVersionUID = -8051554878205756307L;

	private final int page;
	private final int size;
	private Sort sort;

	/**
	 * 
	 * @param page
	 *            must not be less than one.
	 * @param size
	 *            must not be less than one.
	 */
	public Pageable(int page, int size) {
		if (page < 1) {
			throw new IllegalArgumentException(
					"Page index must not be less than one!");
		}
		if (size < 1) {
			throw new IllegalArgumentException(
					"Page size must not be less than one!");
		}
		this.page = page;
		this.size = size;
	}

	public Pageable(int page, int size, Direction direction,
			String... properties) {
		this(page, size, new Sort(direction, properties));
	}

	public Pageable(int page, int size, Sort sort) {
		this.page = page;
		this.size = size;
		this.sort = sort;
	}

	/**
	 * page=1,size=20
	 * 
	 * @return
	 */
	public static Pageable get() {
		return new Pageable(1, 20);
	}

	public int getPageSize() {
		return size;
	}

	public int getPageNumber() {
		return page;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public int getOffset() {
		return (page - 1) * size;
	}

	public boolean hasPrevious() {
		return page > 1;
	}

	public Pageable next() {
		return new Pageable(getPageNumber() + 1, getPageSize(), getSort());
	}

	public Pageable previous() {
		return getPageNumber() == 1 ? this : new Pageable(getPageNumber() - 1,
				getPageSize(), getSort());
	}

	public Pageable first() {
		return new Pageable(1, getPageSize(), getSort());
	}

	@Override
	public String toString() {
		return "Pageable [page=" + page + ", size=" + size + ", sort=" + sort
				+ "]";
	}
}
