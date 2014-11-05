package com.foxinmy.weixin4j.mp.type;

import java.io.Serializable;

/**
 * ID查询
 * 
 * @className IdQuery
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 * @see
 */
public class IdQuery implements Serializable {

	private static final long serialVersionUID = -5273675987521807370L;
	private String id;
	private IdType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IdType getType() {
		return type;
	}

	public void setType(IdType type) {
		this.type = type;
	}

	public IdQuery(String id, IdType idType) {
		this.id = id;
		this.type = idType;
	}
}
