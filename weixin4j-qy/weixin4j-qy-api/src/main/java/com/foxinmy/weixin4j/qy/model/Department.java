package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

/**
 * 部门对象
 * 
 * @className Department
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8">管理部门说明</a>
 */
public class Department implements Serializable {

	private static final long serialVersionUID = -2567893218591084288L;
	private int id; // 部门ID
	private String name; // 部门名称。长度限制为1~64个字符
	private int parentid;// 父亲部门id。根部门id为1
	private int order;// 在父部门中的次序。从1开始，数字越大排序越靠后

	public Department() {

	}

	public Department(String name) {
		this(name, 1, 1);
	}

	public Department(String name, int parentid, int order) {
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
		return "Department [id=" + id + ", name=" + name + ", parentid="
				+ parentid + ", order=" + order + "]";
	}
}
