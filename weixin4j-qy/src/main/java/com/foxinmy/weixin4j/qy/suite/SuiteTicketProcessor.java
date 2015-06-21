package com.foxinmy.weixin4j.qy.suite;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * 对套件ticket的处理：写入与读取
 * 
 * @className SuiteTicketProcessor
 * @author jy
 * @date 2015年6月18日
 * @since JDK 1.7
 * @see
 */
public interface SuiteTicketProcessor {
	/**
	 * 写入微信推送过来的suite_ticket
	 * 
	 * @param suiteTicket
	 * @throws WeixinException
	 */
	public void write(SuiteTicketMessage suiteTicket) throws WeixinException;

	/**
	 * 读取最新的suite_ticket
	 * 
	 * @param suiteId
	 * @return 最新的ticket
	 * @throws WeixinException
	 */
	public SuiteTicketMessage read(String suiteId) throws WeixinException;
}
