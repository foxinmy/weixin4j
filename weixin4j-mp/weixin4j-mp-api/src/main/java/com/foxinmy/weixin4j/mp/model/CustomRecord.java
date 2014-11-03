package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 客服聊天记录
 * 
 * @className CustomRecord
 * @author jy
 * @date 2014年6月28日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AE%A2%E6%9C%8D%E8%81%8A%E5%A4%A9%E8%AE%B0%E5%BD%95">客服聊天记录</a>
 */
public class CustomRecord implements Serializable {

	private static final long serialVersionUID = -4024147769411601325L;
	private String worker;// 客服账号
	private String openid;// 用户的标识
	private Opercode opercode;// 操作ID（会话状态）
	private Date time;// 操作时间
	private String text;// 聊天记录

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Opercode getOpercode() {
		return opercode;
	}

	public void setOpercode(Opercode opercode) {
		this.opercode = opercode;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public enum Opercode {
		MISS(1000, "创建未接入会话"), ONLINE(1001, "接入会话"), CALL(1002, "主动发起会话"), CLOSE(
				1004, "关闭会话"), RASE(1005, "抢接会话"), RECEIVE1(2001, "公众号收到消息"), SEND(
				2002, "客服发送消息"), RECEIVE2(2003, "客服收到消息");
		private int code;
		private String desc;
		Opercode(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		public int getCode() {
			return code;
		}
		public String getDesc() {
			return desc;
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[CustomRecord worker=").append(worker);
		sb.append(" ,openid=").append(openid);
		sb.append(" ,opercode=").append(opercode);
		sb.append(" ,time=").append(time);
		sb.append(" ,text=").append(text);
		sb.append("]");
		return sb.toString();
	}
}
