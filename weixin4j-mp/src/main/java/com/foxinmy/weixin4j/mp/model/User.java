package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.Gender;
import com.foxinmy.weixin4j.mp.type.FaceSize;
import com.foxinmy.weixin4j.mp.type.Lang;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 用户对象
 * <p>
 * 当用户与公众号有交互时,可通过openid获取信息
 * </p>
 * 
 * @author jy.hu
 * @date 2014年4月8日
 * @since JDK 1.7
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1638176217299286265L;

	/**
	 * 用户的唯一标识
	 */
	private String openid;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	@JSONField(name = "sex")
	private Gender gender;
	/**
	 * 用户个人资料填写的省份
	 */
	private String province;
	/**
	 * 普通用户个人资料填写的城市
	 */
	private String city;
	/**
	 * 国家，如中国为CN
	 */
	private String country;
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	 */
	private String headimgurl;
	/**
	 * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	 */
	private String privilege;
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	@JSONField(name = "subscribe")
	private boolean isSubscribe;
	/**
	 * 关注时间
	 */
	@JSONField(name = "subscribe_time")
	private Date subscribeTime;
	/**
	 * 使用语言
	 */
	private Lang language;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	private String unionid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(int sex) {
		if (sex == 1) {
			this.gender = Gender.male;
		} else if (sex == 2) {
			this.gender = Gender.female;
		} else {
			this.gender = Gender.unknown;
		}
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public String getHeadimgurl(FaceSize size) {
		if (StringUtil.isNotBlank(headimgurl)) {
			StringBuilder sb = new StringBuilder(headimgurl);
			return sb.replace(0, (headimgurl.length() - 1), size.getInt() + "")
					.toString();
		}
		return "";
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public Lang getLanguage() {
		return language;
	}

	public void setLanguage(Lang language) {
		this.language = language;
	}

	public boolean isSubscribe() {
		return isSubscribe;
	}

	public void setSubscribe(boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public Date getSubscribeTime() {
		return (Date) subscribeTime.clone();
	}

	public void setSubscribeTime(long subscribeTime) {
		this.subscribeTime = new Date(subscribeTime * 1000l);
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return openid.equals(((User) obj).getOpenid());
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[User openid=").append(openid);
		sb.append(", nickname=").append(nickname);
		sb.append(", gender=").append(gender);
		sb.append(", province=").append(province);
		sb.append(", city=").append(city);
		sb.append(", country=").append(country);
		sb.append(", headimgurl=").append(headimgurl);
		sb.append(", privilege=").append(privilege);
		sb.append(", language=").append(language);
		sb.append(", subscribeTime=").append(subscribeTime);
		sb.append(", unionid=").append(unionid);
		sb.append(", isSubscribe=").append(isSubscribe).append("]");
		return sb.toString();
	}
}
