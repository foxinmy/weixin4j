package com.foxinmy.weixin4j.messagekey;

/**
 * 微信消息匹配
 * 
 * @className WeixinMessageMatcher
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.request.WeixinMessage
 * @see com.foxinmy.weixin4j.messagekey.WeixinMessageKeyDefiner
 */
public interface WeixinMessageMatcher {

	/**
	 * 消息是否匹配
	 * 
	 * @param messageKeyOrMessageClass
	 *            消息key或者消息类型
	 * @return 匹配结果
	 */
	public boolean match(Object messageKeyOrMessageClass);

	/**
	 * 匹配到消息类型
	 * 
	 * @param messageKey
	 *            消息key
	 * @return 消息类型
	 */
	public Class<?> match(String messageKey);

	/**
	 * 注册messageClass
	 * 
	 * @param messageKey
	 *            消息key
	 * @param messageClass
	 *            消息类型
	 */
	public void regist(String messageKey, Class<?> messageClass);
}
