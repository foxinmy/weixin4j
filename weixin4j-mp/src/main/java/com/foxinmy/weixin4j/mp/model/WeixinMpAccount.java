package com.foxinmy.weixin4j.mp.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinAccount;

/**
 * 微信公众号信息
 * 
 * @className WeixinMpAccount
 * @author jinyu
 * @date Jul 6, 2016
 * @since JDK 1.6
 */
public class WeixinMpAccount extends WeixinAccount {

	private static final long serialVersionUID = 3689999353867189585L;
	/**
	 * 多个应用组件信息
	 */
	private List<WeixinAccount> components;

	/**
	 *
	 * @param appId
	 *            应用ID 使用普通接口(WeixinProxy对象)必须填写
	 * @param appSecret
	 *            应用密钥 使用普通接口(WeixinProxy对象)必须填写
	 * @param components
	 *            应用组件集合 使用套件接口(WeixinComponentProxy#ComponentApi)必须填写
	 */
	@JSONCreator
	public WeixinMpAccount(@JSONField(name = "id") String appId, @JSONField(name = "secret") String appSecret,
			@JSONField(name = "components") List<WeixinAccount> components) {
		super(appId, appSecret);
		this.components = components;
	}

	public List<WeixinAccount> getComponents() {
		return components;
	}

	@Override
	public String toString() {
		return "WeixinMpAccount [" + super.toString() + ", components=" + components + "]";
	}
}
