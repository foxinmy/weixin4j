package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SceneInfoStore {
	/**
	 * SZTX001 门店唯一标识
	 */
	private String id;
	/**
	 * 腾讯大厦腾大餐厅 门店名称
	 */
	private String name;
	/**
	 * 门店所在地行政区划码，详细见《最新县及县以上行政区划代码》
	 */
	@XmlElement(name = "area_code")
	@JSONField(name = "area_code")
	private String areaCode;
	/**
	 * 科技园中一路腾讯大厦 门店详细地址
	 */
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SceneInfoStore(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "SceneInfoStore [id=" + id + ", name=" + name + ", areaCode="
				+ areaCode + ", address=" + address + "]";
	}
}