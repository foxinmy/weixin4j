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
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 用户昵称
	 */
	@JSONField(name = "nickname")
	private String nickName;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	@JSONField(name = "sex")
	private int gender;
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
	private long subscribeTime;
	/**
	 * 使用语言
	 */
	private String language;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	@JSONField(name = "unionid")
	private String unionId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenid(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getGender() {
		return gender;
	}

	@JSONField(serialize = false)
	public Gender getFormatGender() {
		if (gender == 1) {
			return Gender.male;
		} else if (gender == 2) {
			return Gender.female;
		} else {
			return Gender.unknown;
		}
	}

	public void setGender(int gender) {
		this.gender = gender;
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

	public String getLanguage() {
		return language;
	}

	@JSONField(serialize = false)
	public Lang getFormatLanguage() {
		return language != null ? Lang.valueOf(language) : null;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isSubscribe() {
		return isSubscribe;
	}

	public void setSubscribe(boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public long getSubscribeTime() {
		return subscribeTime;
	}

	@JSONField(serialize = false)
	public Date getFormatSubscribeTime() {
		return new Date(subscribeTime * 1000l);
	}

	public void setSubscribeTime(long subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return openId.equals(((User) obj).getOpenId());
		}
		return false;
	}

	@Override
	public String toString() {
		return "User [openId=" + openId + ", nickName=" + nickName
				+ ", gender=" + gender + ", province=" + province + ", city="
				+ city + ", country=" + country + ", headimgurl=" + headimgurl
				+ ", privilege=" + privilege + ", isSubscribe=" + isSubscribe
				+ ", subscribeTime=" + subscribeTime + ", language=" + language
				+ ", unionId=" + unionId + "]";
	}
}
