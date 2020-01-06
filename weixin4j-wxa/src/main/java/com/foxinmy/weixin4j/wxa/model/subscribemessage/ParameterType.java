package com.foxinmy.weixin4j.wxa.model.subscribemessage;

/**
 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
 */
public enum ParameterType {

	/**
	 * 20个以内字符	可汉字、数字、字母或符号组合
	 * 可汉字、数字、字母或符号组合
	 */
	THING("thing"),

	/**
	 * 24小时制时间格式（支持+年月日）
	 * 例如：15:01，或：2019年10月1日 15:01
	 */
	TIME("time"),

	/**
	 * 年月日格式（支持+24小时制时间）
	 * 例如：2019年10月1日，或：2019年10月1日 15:01
	 */
	DATE("date"),

	/**
	 * 10个以内纯汉字或20个以内纯字母或符号
	 * 中文名10个汉字内；纯英文名20个字母内；中文和字母混合按中文名算，10个字内
	 */
	NAME("name"),

	/**
	 * 32位以内数字
	 * 只能数字，可带小数
	 */
	NUMBER("number"),

	/**
	 * 32位以内字母
	 * 只能字母
	 */
	LETTER("letter"),

	/**
	 * 5位以内符号
	 * 只能符号
	 */
	SYMBOL("symbol"),

	/**
	 * 5个以内汉字
	 * 5个以内纯汉字，例如：配送中
	 */
	PHRASE("phrase"),

	/**
	 * 1个币种符号+10位以内纯数字，可带小数，结尾可带“元”
	 * 可带小数
	 */
	AMOUNT("amount"),

	/**
	 * 8位以内，第一位与最后一位可为汉字，其余为字母或数字
	 * 车牌号码：粤A8Z888挂
	 */
	CAR_NUMBER("car_number"),

	/**
	 * 17位以内，数字、符号
	 * 电话号码，例：+86-0766-66888866
	 */
	PHONE_NUMBER("phone_number"),

	/**
	 * 32位以内数字、字母或符号
	 * 可数字、字母或符号组合
	 */
	CHARACTER_STRING("character_string");

	private final String value;

	ParameterType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
