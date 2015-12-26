package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 标签对象
 * 
 * @className Tag
 * @author jy
 * @date 2014年11月24日
 * @since JDK 1.6
 * @see
 */
public class Tag implements Serializable {

	private static final long serialVersionUID = 5204620476267654921L;

	/**
	 * 标签ID
	 */
	@JSONField(name = "tagid")
	private int id;
	/**
	 * 标签名称
	 */
	@JSONField(name = "tagname")
	private String name;

	protected Tag() {

	}

	public Tag(String name) {
		this.name = name;
	}

	public Tag(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// ---------- setter 应该全部去掉

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}
}
