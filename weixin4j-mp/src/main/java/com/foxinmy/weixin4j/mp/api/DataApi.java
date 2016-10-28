package com.foxinmy.weixin4j.mp.api;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.DatacubeType;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 数据分析API
 * <p>
 * 1、接口侧的公众号数据的数据库中仅存储了2014年12月1日之后的数据，将查询不到在此之前的日期，即使有查到，也是不可信的脏数据；</br>
 * 2、请开发者在调用接口获取数据后，将数据保存在自身数据库中，即加快下次用户的访问速度，也降低了微信侧接口调用的不必要损耗。</br>
 * </p>
 * 
 * @className DataApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月7日
 * @since JDK 1.6
 * @see
 */
public class DataApi extends MpApi {
	private final TokenManager tokenManager;

	public DataApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 数据统计
	 * 
	 * @param datacubeType
	 *            统计类型
	 * @param beginDate
	 *            开始日期
	 * @param offset
	 *            增量 表示向前几天 比如 offset=1 则查询 beginDate的后一天之间的数据
	 * @see {@link #datacube(DatacubeType, Date,Date)}
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, Date beginDate,
			int offset) throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.setTime(beginDate);
		ca.add(Calendar.DAY_OF_MONTH, offset);
		return datacube(datacubeType, beginDate, ca.getTime());
	}

	/**
	 * 数据统计
	 * 
	 * @param datacubeType
	 *            统计类型
	 * @param offset
	 *            增量 表示向后几天 比如 offset=1 则查询 beginDate的前一天之间的数据
	 * @param endDate
	 *            截至日期
	 * @see {@link #datacube(DatacubeType, Date,Date)}
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, int offset, Date endDate)
			throws WeixinException {
		Calendar ca = Calendar.getInstance();
		ca.setTime(endDate);
		ca.add(Calendar.DAY_OF_MONTH, 0 - offset);
		return datacube(datacubeType, ca.getTime(), endDate);
	}

	/**
	 * 查询日期跨度为0的统计数据(当天)
	 * 
	 * @param datacubeType
	 *            统计类型
	 * @param date
	 *            统计日期
	 * @see {@link #datacube(DatacubeType, Date,Date)}
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, Date date)
			throws WeixinException {
		return datacube(datacubeType, date, date);
	}

	/**
	 * 数据统计
	 * 
	 * @param datacubeType
	 *            数据统计类型
	 * @param beginDate
	 *            获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，
	 *            begin_date和end_date的差值只能为0，才能小于1），否则会报错
	 * @param endDate
	 *            获取数据的结束日期，end_date允许设置的最大值为昨日
	 * @see com.foxinmy.weixin4j.mp.datacube.UserSummary
	 * @see com.foxinmy.weixin4j.mp.datacube.ArticleSummary
	 * @see com.foxinmy.weixin4j.mp.datacube.ArticleTotal
	 * @see com.foxinmy.weixin4j.mp.datacube.ArticleDatacubeShare
	 * @see com.foxinmy.weixin4j.mp.datacube.UpstreamMsg
	 * @see com.foxinmy.weixin4j.mp.datacube.UpstreamMsgDist
	 * @see com.foxinmy.weixin4j.mp.datacube.InterfaceSummary
	 * @return 统计结果
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">用户分析</a>
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析</a>
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析</a>
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141086&token=&lang=zh_CN">接口分析</a>
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, Date beginDate,
			Date endDate) throws WeixinException {
		String datacube_uri = getRequestUri("datacube_uri");
		Token token = tokenManager.getCache();
		JSONObject obj = new JSONObject();
		obj.put("begin_date", DateUtil.fortmat2yyyy_MM_dd(beginDate));
		obj.put("end_date", DateUtil.fortmat2yyyy_MM_dd(endDate));
		WeixinResponse response = weixinExecutor.post(String.format(datacube_uri,
				datacubeType.name().toLowerCase(), token.getAccessToken()), obj
				.toJSONString());

		return JSON.parseArray(response.getAsJson().getString("list"),
				datacubeType.getClazz());
	}
}
