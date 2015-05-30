package com.foxinmy.weixin4j.http;

import com.foxinmy.weixin4j.http.entity.HttpEntity;

/**
 * request with entity
 * 
 * @className HttpEntityRequest
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public abstract class HttpEntityRequest extends AbstractHttpRequest {
	public abstract HttpEntity getEntity();
}
