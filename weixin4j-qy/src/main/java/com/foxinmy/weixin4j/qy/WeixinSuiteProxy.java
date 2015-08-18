package com.foxinmy.weixin4j.qy;

import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.qy.api.QyApi;
import com.foxinmy.weixin4j.qy.api.SuiteApi;
import com.foxinmy.weixin4j.qy.suite.SuiteTicketHolder;
import com.foxinmy.weixin4j.token.TokenStorager;

/**
 * 微信第三方应用接口实现
 * 
 * @className WeixinSuiteProxy
 * @author jy
 * @date 2015年6月22日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%88%E6%9D%83">企业号第三方应用</a>
 */
public class WeixinSuiteProxy {

	private final Map<String, SuiteApi> suiteMap;

	public WeixinSuiteProxy() {
		this(QyApi.DEFAULT_TOKEN_STORAGER);
	}

	/**
	 * 
	 * @param suiteId
	 *            应用ID
	 * @param suiteSecret
	 *            应用secret
	 * @throws WeixinException
	 */
	public WeixinSuiteProxy(String suiteId, String suiteSecret) {
		this(QyApi.DEFAULT_TOKEN_STORAGER, new WeixinAccount(suiteId,
				suiteSecret));
	}

	/**
	 * 
	 * @param tokenStorager
	 *            token存储
	 */
	public WeixinSuiteProxy(TokenStorager tokenStorager) {
		this(tokenStorager, QyApi.DEFAULT_WEIXIN_ACCOUNT.suitesToArray());
	}

	/**
	 * 
	 * @param tokenStorager
	 *            token存储
	 * @param suites
	 *            套件信息
	 */
	public WeixinSuiteProxy(TokenStorager tokenStorager,
			WeixinAccount... suites) {
		this.suiteMap = new HashMap<String, SuiteApi>();
		for (WeixinAccount suite : suites) {
			this.suiteMap.put(suite.getId(), new SuiteApi(
					new SuiteTicketHolder(suite.getId(), suite.getSecret(),
							tokenStorager)));
		}
		this.suiteMap.put(null, suiteMap.get(suites[0].getId()));
	}

	/**
	 * 只关注第一个套件获取API(如果只有一个套件
	 * 
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
	 * @return API实例
	 */
	public SuiteApi api() {
		return this.suiteMap.get(null);
	}

	/**
	 * 多个套件获取API
	 * 
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
	 * @param suiteId
	 *            套件ID
	 * @return API实例
	 */
	public SuiteApi api(String suiteId) {
		return this.suiteMap.get(suiteId);
	}

	public final static String VERSION = "1.5.3";
}
