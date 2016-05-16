package com.foxinmy.weixin4j.mp.type;

import com.foxinmy.weixin4j.mp.datacube.ArticleDatacubeShare;
import com.foxinmy.weixin4j.mp.datacube.ArticleSummary;
import com.foxinmy.weixin4j.mp.datacube.ArticleTotal;
import com.foxinmy.weixin4j.mp.datacube.InterfaceSummary;
import com.foxinmy.weixin4j.mp.datacube.UpstreamMsg;
import com.foxinmy.weixin4j.mp.datacube.UpstreamMsgDist;
import com.foxinmy.weixin4j.mp.datacube.UserSummary;

/**
 * 数据统计类型
 * 
 * @className DatacubeType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月25日
 * @since JDK 1.6
 * @see
 */
public enum DatacubeType {
	/**
	 * 获取用户增减数据
	 */
	GETUSERSUMMARY(UserSummary.class),
	/**
	 * 获取累计用户数据
	 */
	GETUSERCUMULATE(UserSummary.class),
	/**
	 * 获取图文群发每日数据
	 */
	GETARTICLESUMMARY(ArticleSummary.class),
	/**
	 * 获取图文群发总数据,获取的是某天所有被阅读过的文章（仅包括群发的文章）在当天的阅读次数等数据。
	 */
	GETARTICLETOTAL(ArticleTotal.class),
	/**
	 * 获取图文统计数据,获取的是某天群发的文章，从群发日起到接口调用日（但最多统计发表日后7天数据），
	 * 每天的到当天的总等数据。例如某篇文章是12月1日发出的，发出后在1日
	 * 、2日、3日的阅读次数分别为1万，则getarticletotal获取到的数据为
	 * ，距发出到12月1日24时的总阅读量为1万，距发出到12月2日24时的总阅读量为2万，距发出到12月1日24时的总阅读量为3万。
	 */
	GETUSERREAD(ArticleSummary.class),
	/**
	 * 获取图文统计分时数据
	 */
	GETUSERREADHOUR(ArticleSummary.class),
	/**
	 * 获取图文分享转发数据
	 */
	GETUSERSHARE(ArticleDatacubeShare.class),
	/**
	 * 获取图文分享转发分时数据
	 */
	GETUSERSHAREHOUR(ArticleDatacubeShare.class),

	/**
	 * 获取消息发送概况数据
	 */
	GETUPSTREAMMSG(UpstreamMsg.class),
	/**
	 * 获取消息分送分时数据
	 */
	GETUPSTREAMMSGHOUR(UpstreamMsg.class),
	/**
	 * 获取消息发送周数据
	 * 关于周数据与月数据，请注意：每个月/周的周期数据的数据标注日期在当月/当周的第一天（当月1日或周一）。在某一月/周过后去调用接口
	 * ，才能获取到该周期的数据
	 * 。比如，在12月1日以（11月1日-11月5日）作为（begin_date和end_date）调用获取月数据接口，可以获取到11月1日的月数据
	 * （即11月的月数据）。
	 */
	GETUPSTREAMMSGWEEK(UpstreamMsg.class),
	/**
	 * 获取消息发送月数据
	 */
	GETUPSTREAMMSGMONTH(UpstreamMsg.class),

	/**
	 * 获取消息发送分布数据
	 */
	GETUPSTREAMMSGDIST(UpstreamMsgDist.class),

	/**
	 * 获取消息发送分布周数据
	 */
	GETUPSTREAMMSGDISTWEEK(UpstreamMsgDist.class),

	/**
	 * 获取消息发送分布月数据
	 */
	GETUPSTREAMMSGDISTMONTH(UpstreamMsgDist.class),
	/**
	 * 获取接口分析数据
	 */
	GETINTERFACESUMMARY(InterfaceSummary.class),
	/**
	 * 获取接口分析分时数据
	 */
	GETINTERFACESUMMARYHOUR(InterfaceSummary.class);
	
	private Class<?> clazz;

	DatacubeType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
