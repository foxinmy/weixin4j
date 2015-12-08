package com.foxinmy.weixin4j.qy.test;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.qy.api.BatchApi;
import com.foxinmy.weixin4j.qy.api.MediaApi;
import com.foxinmy.weixin4j.qy.model.BatchResult;
import com.foxinmy.weixin4j.qy.model.Callback;
import com.foxinmy.weixin4j.qy.model.Party;

/**
 * 异步任务测试
 * 
 * @className BatchTest
 * @author jy
 * @date 2015年3月31日
 * @since JDK 1.6
 * @see
 */
public class BatchTest extends TokenTest {
	public BatchApi batchApi;
	public MediaApi mediaApi;

	@Before
	public void init() {
		this.batchApi = new BatchApi(tokenHolder);
		this.mediaApi = new MediaApi(tokenHolder);
	}

	@Test
	public void syncuser() throws WeixinException {
		String jobId = batchApi
				.syncUser(
						"1QFmZ8LE9dFxPPx8EH5Kfm3cqGXB0OuXY432ZpsfwMFTJjEDt7QI4GZB1UhYGOSYr",
						new Callback("http://182.254.188.133:8090",
								"gp2eGT5mIpngr",
								"BRYfV4zPFUJb3v3MySNBg1ERKE3vyyMRoScu76vFySv"));
		System.err.println(jobId);
	}

	@Test
	public void replaceparty() throws WeixinException {
		String mediaId = mediaApi.batchUploadParties(Arrays.asList(new Party(5,
				"部门1", 1), new Party(6, "部门2", 1)));
		String jobId = batchApi.replaceParty(mediaId, new Callback(
				"http://182.254.188.133:8090", "gp2eGT5mIpngr",
				"BRYfV4zPFUJb3v3MySNBg1ERKE3vyyMRoScu76vFySv"));
		System.err.println(jobId);
	}

	@Test
	public void getresult() throws WeixinException {
		BatchResult result = batchApi
				.getBatchResult("PVucPBfEapLnvQZ1ru2Vdw3Dbl-jXs3AEQdS24cqmI0");
		System.err.println(result);
	}
}
