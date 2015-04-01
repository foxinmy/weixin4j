package com.foxinmy.weixin4j.qy.test;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.qy.api.BatchApi;
import com.foxinmy.weixin4j.qy.model.BatchResult;
import com.foxinmy.weixin4j.qy.model.Callback;

/**
 * 异步任务测试
 * 
 * @className BatchTest
 * @author jy
 * @date 2015年3月31日
 * @since JDK 1.7
 * @see
 */
public class BatchTest extends TokenTest {
	public BatchApi batchApi;

	@Before
	public void init() {
		this.batchApi = new BatchApi(tokenHolder);
	}

	@Test
	public void replaceparty() throws WeixinException {
		String jobId = batchApi.replaceparty("mediaId", new Callback("url", "token", "aesKey"));
		System.err.println(jobId);
	}
	
	@Test
	public void getresult() throws WeixinException {
		BatchResult result = batchApi.getresult("jobId");
		System.err.println(result);
	}
}
