package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

/**
 * 分组
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3">分组</a>
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 6979565973974005954L;

	private int id;
	private String name;
	private int count;

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

	public int getCount() {
		return count;
	}

	public Group(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Group(String name) {
		this.name = name;
	}

	public Group() {

	}

	/**
	 * 返回创建分组所需的json格式字符串
	 * @return {"group": {"id": 107, "name": "test"}}
	 */
	public String toCreateJson() {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		xstream.omitField(this.getClass(), "id");
		xstream.omitField(this.getClass(), "count");
		xstream.alias("group", this.getClass());
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(this.getClass());
		return xstream.toXML(this);
	}

	/**
	 * 返回修改分组所需的json格式字符串
	 * @return
	 */
	public String toModifyJson() {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		xstream.omitField(this.getClass(), "count");
		xstream.alias("group", this.getClass());
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(this.getClass());
		return xstream.toXML(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Group) {
			return id == ((Group) obj).getId();
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.format("[Group id=%d ,name=%s ,count=%d]", id, name, count);
	}
}
