package com.zone.weixin4j.response;


/**
 * 微信被动消息回复
 * 
 * @className WeixinResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see TextResponse
 * @see ImageResponse
 * @see MusicResponse
 * @see VoiceResponse
 * @see VideoResponse
 * @see NewsResponse
 * @see TransferCustomerResponse
 * @see SingleResponse
 * @see BlankResponse
 * @see <a href=
 *      "http://https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140543&token=&lang=zh_CN">订阅号、服务号的被动响应消息</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF">企业号的被动响应消息</a>
 */
public interface WeixinResponse {
	/**
	 * 回复的消息类型
	 * 
	 * @return
	 */
	public String getMsgType();

	/**
	 * 回复的消息内容
	 * 
	 * @return
	 */
	public String toContent();
}
