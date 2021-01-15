package com.foxinmy.weixin4j.pay.payment.mch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SceneInfoApp implements SceneInfo {
	/**
	 * 终端类型
	 */
	private String type;
	/**
	 * WAP 网站名
	 */
	@XmlElement(name = "wap_name")
	@JSONField(name = "wap_name")
	private String name;
	/**
	 * WAP网站URL地址
	 */
	@XmlElement(name = "wap_url")
	@JSONField(name = "wap_url")
	private String path;

	@JSONField(serialize = false)
	private String sceneInfo;

	protected SceneInfoApp(){

	}

	private SceneInfoApp(String type, String name, String path) {
		this.type = type;
		this.name = name;
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Deprecated
	public String getSceneInfo() {
		return sceneInfo;
	}
	@Deprecated
	public void setSceneInfo(String sceneInfo) {
		this.sceneInfo = sceneInfo;
	}

	/**
	 * IOS应用
	 * APP环境直接使用APP支付，此方法将作废
	 * 
	 * @param appName 应用名
	 * @param bundleId 模块ID
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public static SceneInfoApp createIOSAPP(String appName, String bundleId) {
		SceneInfoApp app = new SceneInfoApp("IOS", appName, bundleId);
		String sceneInfo = String
				.format("{\"type\": \"%s\",\"app_name\": \"%s\",\"bundle_id\": \"%s\"}",
						app.getType(), app.getName(), app.getPath());
		app.setSceneInfo(sceneInfo);
		return app;
	}

	/**
	 * Android应用
	 * APP环境直接使用APP支付，此方法将作废
	 * 
	 * @param appName 应用名
	 * @param packageName 包名
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public static SceneInfoApp createAndroidAPP(String appName, String packageName) {
		SceneInfoApp app = new SceneInfoApp("Android", appName, packageName);
		String sceneInfo = String
				.format("{\"type\": \"%s\",\"app_name\": \"%s\",\"package_name\": \"%s\"}",
						app.getType(), app.getName(), app.getPath());
		app.setSceneInfo(sceneInfo);
		return app;
	}

	/**
	 * Wap应用
	 * 
	 * @param name
	 *            网站名
	 * @param url
	 *            网站URL地址
	 * @return
	 */
	public static SceneInfoApp createWapAPP(String name, String url) {
		SceneInfoApp app = new SceneInfoApp("Wap", name, url);
		String sceneInfo = String.format(
				"{\"type\": \"%s\",\"wap_name\": \"%s\",\"wap_url\": \"%s\"}",
				app.getType(), app.getName(), app.getPath());
		app.setSceneInfo(sceneInfo);
		return app;
	}

	@Override
	public String toJson() {
		return String.format("{\"h5_info\": %s}", JSON.toJSONString(this));
	}
}
