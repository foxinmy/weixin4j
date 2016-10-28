package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 授权方的选项信息
 * 
 * @className AuthorizerOption
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月5日
 * @since JDK 1.6
 */
public final class AuthorizerOption implements Serializable {

	private static final long serialVersionUID = -3124882411789627403L;

	/**
	 * 选项名称
	 * 
	 * @className AuthorizerOptionName
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2016年7月5日
	 * @since JDK 1.6
	 */
	public enum AuthorizerOptionName {
		/**
		 * 地理位置上报选项
		 */
		location_report,
		/**
		 * 语音识别开关选项
		 */
		voice_recognize,
		/**
		 * 多客服开关选项
		 */
		customer_service;
	}

	private AuthorizerOptionName name;
	private int value;
	private List<Integer> options;

	private AuthorizerOption(AuthorizerOptionName name, int value,
			Integer... options) {
		this.name = name;
		this.value = value;
		this.options = Arrays.asList(options);
	}

	/**
	 * 地理位置上报选项
	 * 
	 * @param value
	 *            选项值
	 * @return 选项对象
	 */
	public static AuthorizerOption createLocationReportOption(int value) {
		return new AuthorizerOption(AuthorizerOptionName.location_report,
				value, 0, 1, 2);
	}

	/**
	 * 语音识别开关选项
	 * 
	 * @param value
	 *            选项值
	 * @return 选项对象
	 */
	public static AuthorizerOption createVoiceRecognizeOption(int value) {
		return new AuthorizerOption(AuthorizerOptionName.voice_recognize,
				value, 0, 1);
	}

	/**
	 * 多客服开关选项
	 * 
	 * @param value
	 *            选项值
	 * @return 选项对象
	 */
	public static AuthorizerOption createCustomerServiceOption(int value) {
		return new AuthorizerOption(AuthorizerOptionName.customer_service,
				value, 0, 1);
	}

	/**
	 * 返回选项对象
	 * 
	 * @param optionName
	 *            选项名
	 * @param optionValue
	 *            选项值
	 * @return 选项对象
	 */
	public static AuthorizerOption parse(AuthorizerOptionName optionName,
			int optionValue) {
		if (optionName == AuthorizerOptionName.customer_service) {
			return createCustomerServiceOption(optionValue);
		} else if (optionName == AuthorizerOptionName.location_report) {
			return createLocationReportOption(optionValue);
		} else if (optionName == AuthorizerOptionName.voice_recognize) {
			return createVoiceRecognizeOption(optionValue);
		} else {
			throw new IllegalArgumentException("unkown option:" + optionName);
		}
	}

	public AuthorizerOptionName getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public List<Integer> getOptions() {
		return options;
	}

	@Override
	public String toString() {
		return "AuthorizerOption [name=" + name + ", value=" + value
				+ ", options=" + options + "]";
	}
}
