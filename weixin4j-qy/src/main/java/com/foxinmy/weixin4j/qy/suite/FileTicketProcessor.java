package com.foxinmy.weixin4j.qy.suite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 用file存储ticket
 * 
 * @className FileTicketProcessor
 * @author jy
 * @date 2015年6月21日
 * @since JDK 1.7
 * @see
 */
public class FileTicketProcessor implements SuiteTicketProcessor {

	private final String ticketPath;

	public FileTicketProcessor() {
		this.ticketPath = ConfigUtil.getValue("ticket_path");
	}

	public FileTicketProcessor(String ticketPath) {
		this.ticketPath = ticketPath;
	}

	@Override
	public void write(SuiteTicketMessage suiteTicket) throws WeixinException {
		try {
			XmlStream
					.toXML(suiteTicket,
							new FileOutputStream(new File(String.format(
									"%s/%s.xml", ticketPath,
									getCacheKey(suiteTicket.getSuiteId())))));
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		}
	}

	private String getCacheKey(String suiteId) {
		return String.format("qy_suite_ticket_%s", suiteId);
	}

	@Override
	public SuiteTicketMessage read(String suiteId) throws WeixinException {
		File ticket_file = new File(String.format("%s/%s.xml", ticketPath,
				getCacheKey(suiteId)));
		try {
			return XmlStream.fromXML(new FileInputStream(ticket_file),
					SuiteTicketMessage.class);
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		}
	}
}
