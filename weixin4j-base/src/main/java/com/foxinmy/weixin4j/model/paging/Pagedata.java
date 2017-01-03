package com.foxinmy.weixin4j.model.paging;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Pagedata<T> implements Serializable, Iterable<T> {

	private static final long serialVersionUID = 163159826528502864L;

	private final int total;
	private final Pageable pageable;
	private final List<T> content;

	public Pagedata(Pageable pageable, int total, List<T> content) {
		this.pageable = pageable;
		this.total = total;
		this.content = content;
	}

	public int getNumber() {
		return pageable == null ? 0 : pageable.getPageNumber();
	}

	public int getSize() {
		return pageable == null ? 0 : pageable.getPageSize();
	}

	public int getTotalPages() {
		return getSize() == 0 ? 1 : (int) Math.ceil((double) total
				/ (double) getSize());
	}

	public int getTotalElements() {
		return total;
	}

	public int getNumberOfElements() {
		return hasContent() ? 0 : content.size();
	}

	public boolean hasContent() {
		return content != null && !content.isEmpty();
	}

	public boolean hasPrevious() {
		return pageable == null ? false : pageable.hasPrevious();
	}

	public boolean hasNext() {
		return getNumber() + 1 < getTotalPages();
	}

	public Sort getSort() {
		return pageable == null ? null : pageable.getSort();
	}

	public List<T> getContent() {
		return content;
	}

	@Override
	public Iterator<T> iterator() {
		return hasContent() ? content.iterator() : null;
	}

	@Override
	public String toString() {
		return "Pagedata [total=" + total + ", pageable=" + pageable + "]";
	}
}
