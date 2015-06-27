package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

/**
 * 部门对象
 * 
 * @className Party
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8">管理部门说明</a>
 */
public class Party implements Serializable {

	private static final long serialVersionUID = -2567893218591084288L;
	/**
	 * 部门ID,指定时必须大于1,不指定时则自动生成.
	 */
	private int id;
	/**
	 * 部门名称。长度限制为1~64个字符
	 */
	private String name;
	/**
	 * 父亲部门id。根部门id为1
	 */
	private int parentid;
	/**
	 * 在父部门中的次序。从1开始，数字越大排序越靠后
	 */
	private int order;

	public Party() {

	}

	public Party(int id, String name) {
		this(id, name, 0, 0);
	}

	public Party(int id, String name, int parentid, int order) {
		this.id = id;
		this.name = name;
		this.parentid = parentid;
		this.order = order;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Party [id=" + id + ", name=" + name + ", parentid=" + parentid
				+ ", order=" + order + "]";
	}
}
