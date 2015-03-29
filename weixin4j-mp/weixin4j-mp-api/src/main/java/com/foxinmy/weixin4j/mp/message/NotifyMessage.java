package com.foxinmy.weixin4j.mp.message;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.msg.model.Base;

/**
 * 客服消息(48小时内不限制发送次数)
 * 
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.model.Text
 * @see com.foxinmy.weixin4j.msg.model.Image
 * @see com.foxinmy.weixin4j.msg.model.Voice
 * @see com.foxinmy.weixin4j.msg.model.Video
 * @see com.foxinmy.weixin4j.msg.model.Music
 * @see com.foxinmy.weixin4j.msg.model.News
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF">发送客服消息</a>
 */
public class NotifyMessage implements Serializable {

	private static final long serialVersionUID = 7190233634431087729L;

	/**
	 * 用户的openid
	 */
	private String touser;
	/**
	 * 消息对象
	 */
	@JSONField(name = "%s")
	private Base box;

	public NotifyMessage(Base box) {
		this(null, box);
	}

	public NotifyMessage(String touser, Base box) {
		this.touser = touser;
		this.box = box;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public Base getBox() {
		return box;
	}

	public void setBox(Base box) {
		this.box = box;
	}

	/**
	 * 客服消息json化
	 * 
	 * @return {"touser": "to","msgtype": "text","text": {"content": "123"}}
	 */
	public String toJson() {
		String msgtype = box.getMediaType().name();
		JSONObject obj = (JSONObject) JSON.toJSON(this);
		obj.put("msgtype", msgtype);
		return String.format(obj.toJSONString(), msgtype);
	}

	@Override
	public String toString() {
		return "NotifyMessage [touser=" + touser + ", box=" + box + "]";
	}
}