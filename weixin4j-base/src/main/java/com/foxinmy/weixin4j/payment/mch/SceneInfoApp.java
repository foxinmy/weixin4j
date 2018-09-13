package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SceneInfoApp {
	/**
	 * 终端类型
	 */
	private String type;
	/**
	 * 应用名称
	 */
	private String name;
	/**
	 * 应用路径
	 */
	private String path;
	private String sceneInfo;

	private SceneInfoApp(String type, String name, String path) {
		this.type = type;
		this.name = name;
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getSceneInfo() {
		return sceneInfo;
	}

	public void setSceneInfo(String sceneInfo) {
		this.sceneInfo = sceneInfo;
	}

	/**
	 * IOS应用
	 * 
	 * @param appName 应用名
	 * @param bundleId 模块ID
	 * @return
	 */
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
	 * 
	 * @param appName 应用名
	 * @param packageName 包名
	 * @return
	 */
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
}
