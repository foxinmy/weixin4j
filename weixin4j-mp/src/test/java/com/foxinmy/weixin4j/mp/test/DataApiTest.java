package com.foxinmy.weixin4j.mp.test;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.DataApi;
import com.foxinmy.weixin4j.mp.datacube.ArticleDatacubeShare;
import com.foxinmy.weixin4j.mp.datacube.ArticleSummary;
import com.foxinmy.weixin4j.mp.datacube.ArticleTotal;
import com.foxinmy.weixin4j.mp.type.DatacubeType;

/**
 * 数据分析测试
 * 
 * @className DataApiTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月25日
 * @since JDK 1.6
 * @see
 */
@SuppressWarnings("unchecked")
public class DataApiTest extends TokenTest {
	private DataApi dataApi;

	@Before
	public void init() {
		dataApi = new DataApi(tokenManager);
	}

	@Test
	public void testUserCumulate() throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -7);
		List<?> userSummaryList = dataApi.datacube(
				DatacubeType.GETUSERCUMULATE, ca.getTime(), 3);
		Assert.assertFalse(userSummaryList.isEmpty());
		System.err.println(userSummaryList);
	}

	@Test
	public void testUserSummary() throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -7);
		List<?> userSummaryList = dataApi.datacube(DatacubeType.GETUSERSUMMARY,
				ca.getTime(), 3);
		Assert.assertFalse(userSummaryList.isEmpty());
		System.err.println(userSummaryList);
	}

	@Test
	public void testArticleSummary() throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -7);
		List<ArticleSummary> articleSummaryList = (List<ArticleSummary>) dataApi
				.datacube(DatacubeType.GETARTICLESUMMARY, ca.getTime());
		Assert.assertFalse(articleSummaryList.isEmpty());
		System.err.println(articleSummaryList);
	}

	@Test
	public void testArticleTotal() throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -7);
		List<ArticleTotal> articleTotalList = (List<ArticleTotal>) dataApi
				.datacube(DatacubeType.GETARTICLETOTAL, ca.getTime());
		Assert.assertFalse(articleTotalList.isEmpty());
		System.err.println(articleTotalList);
	}

	@Test
	public void testUserReadHour() throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -7);
		List<ArticleSummary> articleSummaryList = (List<ArticleSummary>) dataApi
				.datacube(DatacubeType.GETUSERREADHOUR, ca.getTime());
		Assert.assertFalse(articleSummaryList.isEmpty());
		System.err.println(articleSummaryList);
	}

	@Test
	public void testUserShare() throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -7);
		List<ArticleDatacubeShare> userShareList = (List<ArticleDatacubeShare>) dataApi
				.datacube(DatacubeType.GETUSERSHAREHOUR, ca.getTime());
		Assert.assertFalse(userShareList.isEmpty());
		System.err.println(userShareList);
	}

	@Test
	public void testDatecube() throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -1);
		List<?> datacuteList = dataApi.datacube(DatacubeType.GETUPSTREAMMSG,
				ca.getTime());
		Assert.assertFalse(datacuteList.isEmpty());
		System.err.println(datacuteList);
		//dataApi.datacube(DatacubeType.GETUPSTREAMMSGMONTH, ca.getTime());
		System.err.println(datacuteList);
		
		//dataApi.datacube(DatacubeType.GETUPSTREAMMSGDISTMONTH, ca.getTime());
		System.err.println(datacuteList);
		
		dataApi.datacube(DatacubeType.GETINTERFACESUMMARYHOUR, ca.getTime());
		System.err.println(datacuteList);
	}
}
