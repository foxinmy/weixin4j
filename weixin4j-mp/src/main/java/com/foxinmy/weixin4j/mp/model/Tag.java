package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 标签对象
 * 
 * @className Tag
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月24日
 * @since JDK 1.6
 * @see
 */
public class Tag implements Serializable {

	private static final long serialVersionUID = 5204620476267654921L;

	/**
	 * 标签ID
	 */
	private int id;
	/**
	 * 标签名称
	 */
	private String name;
	/**
	 * 人员数量
	 */
	private int count;

	public Tag(@JSONField(name = "id") int id,
			@JSONField(name = "name") String name) {
		this(id, name, 0);
	}

	@JSONCreator
	public Tag(@JSONField(name = "id") int id,
			@JSONField(name = "name") String name,
			@JSONField(name = "count") int count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", count=" + count + "]";
	}
}
