package com.foxinmy.weixin4j.mp.test;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.HelperApi;
import com.foxinmy.weixin4j.mp.model.SemQuery;
import com.foxinmy.weixin4j.mp.type.SemCategory;

public class SemanticTest extends TokenTest {
	private HelperApi helperApi;

	@Before
	public void init() {
		helperApi = new HelperApi(tokenManager);
	}

	@Test
	public void testApi() throws WeixinException {
		SemQuery query = SemQuery.build("附近有啥 100 元以内的保龄球馆");
		query.city("北京").category(SemCategory.nearby);
		query.uid("opKwyt6IhrqPmTTZshyqH5W9gIVo");
		System.out.println(query);
		System.out.println(helperApi.semantic(query));
	}
}
