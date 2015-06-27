package com.foxinmy.weixin4j.qy;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.qy.api.QyApi;
import com.foxinmy.weixin4j.qy.api.SuiteApi;
import com.foxinmy.weixin4j.qy.model.AgentInfo;
import com.foxinmy.weixin4j.qy.model.AgentSetter;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.suite.SuitePerCodeHolder;
import com.foxinmy.weixin4j.qy.suite.SuiteTicketHolder;
import com.foxinmy.weixin4j.token.TokenHolder;
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
	/**
	 * 第三方应用API
	 */
	private final SuiteApi suiteApi;

	public WeixinSuiteProxy() throws WeixinException {
		this(QyApi.DEFAULT_WEIXIN_ACCOUNT.getSuiteId(),
				QyApi.DEFAULT_WEIXIN_ACCOUNT.getSuiteSecret());
	}

	public WeixinSuiteProxy(String suiteId, String suiteSecret)
			throws WeixinException {
		this(suiteId, suiteSecret, QyApi.DEFAULT_TOKEN_STORAGER);
	}

	/**
	 * 
	 * @param suiteId
	 *            应用ID
	 * @param suiteSecret
	 *            应用secret
	 * @param ticketStorager
	 *            应用ticket存储器(用于读取)
	 * @param tokenStorager
	 *            应用token存储器
	 * @throws WeixinException
	 */
	public WeixinSuiteProxy(String suiteId, String suiteSecret,
			TokenStorager tokenStorager) throws WeixinException {
		this.suiteApi = new SuiteApi(suiteId, suiteSecret, tokenStorager);
	}

	/**
	 * 
	 * @param suiteTicketHolder
	 *            套件ticket存取
	 * @throws WeixinException
	 */
	public WeixinSuiteProxy(SuiteTicketHolder suiteTicketHolder)
			throws WeixinException {
		this.suiteApi = new SuiteApi(suiteTicketHolder);
	}

	/**
	 * 应用套件token
	 * 
	 * @return
	 */
	public TokenHolder getTokenHolder() {
		return suiteApi.getTokenHolder();
	}

	/**
	 * 应用套件ticket
	 * 
	 * @return
	 */
	public SuiteTicketHolder getTicketHolder() {
		return suiteApi.getTicketHolder();
	}

	/**
	 * 应用套件永久授权码
	 * 
	 * @return
	 */
	public SuitePerCodeHolder getPerCodeHolder() {
		return suiteApi.getPerCodeHolder();
	}

	/**
	 * 应用套件预授权码
	 * 
	 * @return
	 */
	public TokenHolder getPreCodeHolder() {
		return suiteApi.getPreCodeHolder();
	}

	/**
	 * 获取企业号access_token(永久授权码)
	 * 
	 * @param authCorpid
	 *            授权方corpid
	 * @return 企业号token
	 */
	public TokenHolder crateTokenHolder(String authCorpid) {
		return suiteApi.createTokenHolder(authCorpid);
	}

	/**
	 * 设置套件授权配置:如果需要对某次授权进行配置，则调用本接口，目前仅可以设置哪些应用可以授权，不调用则默认允许所有应用进行授权。
	 * 
	 * @param appids
	 *            允许进行授权的应用id，如1、2、3
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.AE.BE.E7.BD.AE.E6.8E.88.E6.9D.83.E9.85.8D.E7.BD.AE"
	 *      >设置套件授权配置</a>
	 */
	public JsonResult setSuiteSession(int... appids) throws WeixinException {
		return suiteApi.setSuiteSession(appids);
	}

	/**
	 * 获取企业号的永久授权码
	 * 
	 * @param authCode
	 *            临时授权码会在授权成功时附加在redirect_uri中跳转回应用提供商网站。
	 * @return 授权得到的信息
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.8E.B7.E5.8F.96.E4.BC.81.E4.B8.9A.E5.8F.B7.E7.9A.84.E6.B0.B8.E4.B9.85.E6.8E.88.E6.9D.83.E7.A0.81"
	 *      >获取企业号的永久授权码</a>
	 */
	public OUserInfo exchangePermanentCode(String authCode)
			throws WeixinException {
		return suiteApi.exchangePermanentCode(authCode);
	}

	/**
	 * 获取企业号的授权信息
	 * 
	 * @param authCorpid
	 *            授权方corpid
	 * @return 授权方信息
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.8E.B7.E5.8F.96.E4.BC.81.E4.B8.9A.E5.8F.B7.E7.9A.84.E6.8E.88.E6.9D.83.E4.BF.A1.E6.81.AF">获取企业号的授权信息</a>
	 */
	public OUserInfo getOAuthInfo(String authCorpid) throws WeixinException {
		return suiteApi.getOAuthInfo(authCorpid);
	}

	/**
	 * 获取企业号应用
	 * 
	 * @param authCorpid
	 *            授权方corpid
	 * @param agentid
	 *            授权方应用id
	 * @return 应用信息
	 * @see com.foxinmy.weixin4j.qy.model.AgentInfo
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.8E.B7.E5.8F.96.E4.BC.81.E4.B8.9A.E5.8F.B7.E5.BA.94.E7.94.A8">获取企业号应用</a>
	 * @throws WeixinException
	 */
	public AgentInfo getAgent(String authCorpid, int agentid)
			throws WeixinException {
		return suiteApi.getAgent(authCorpid, agentid);
	}

	/**
	 * 设置企业应用的选项设置信息，如：地理位置上报等
	 * 
	 * @param authCorpid
	 *            授权方corpid
	 * @param agentSet
	 *            设置信息
	 * @see com.foxinmy.weixin4j.qy.model.AgentSetter
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%AE%BE%E7%BD%AE%E4%BC%81%E4%B8%9A%E5%8F%B7%E5%BA%94%E7%94%A8">设置企业号信息</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult setAgent(String authCorpid, AgentSetter agentSet)
			throws WeixinException {
		return suiteApi.setAgent(authCorpid, agentSet);
	}
}
