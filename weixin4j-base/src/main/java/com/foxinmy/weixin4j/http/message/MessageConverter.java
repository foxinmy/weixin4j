package com.foxinmy.weixin4j.http.message;

import java.io.IOException;
import java.util.List;

import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.MimeType;

/**
 * 消息转换接口
 * 
 * @className MessageConverter
 * @author jinyu
 * @date Jul 20, 2016
 * @since JDK 1.6
 * @see JsonMessageConverter
 * @see XmlMessageConverter
 */
public interface MessageConverter {
	/**
	 * 获取可以转换的媒体类型
	 * 
	 * @return 媒体列表
	 */
	public List<MimeType> supportedMimeTypes();

	/**
	 * 是否可以转换
	 * 
	 * @param clazz
	 *            转换类型
	 * @param response
	 *            响应对象
	 * @return 是否标识
	 */
	public boolean canConvert(Class<?> clazz, HttpResponse response);

	/**
	 * 转换消息
	 * 
	 * @param clazz
	 *            转换类型
	 * @param response
	 *            响应对象
	 * @throws IOException
	 * @return 消息对象
	 */
	public <T> T convert(Class<? extends T> clazz, HttpResponse response)
			throws IOException;
}
