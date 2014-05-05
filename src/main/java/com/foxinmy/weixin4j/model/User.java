package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.foxinmy.weixin4j.util.WeixinUtil;

/**
 * 用户对象
 * <p>当用户与公众号有交互时,可通过openid获取信息</p>
 * @author jy.hu
 * @date 2014年4月8日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF">获取用户基本资料</a>
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String openid; // 用户的唯一标识
	private String nickname; // 用户昵称
	private int sex; // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String province; // 用户个人资料填写的省份
	private String city; // 普通用户个人资料填写的城市
	private String country; // 国家，如中国为CN
	private String headimgurl; // 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	private String privilege; // 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	private int subscribe; // 是否关注
	private long subscribe_time; // 关注时间
	private Lang language; // 使用语言

	// 国家地区语言版本
	public enum Lang {
		zh_CN("简体"), zh_TW("繁体"), en("英语");

		private String desc;

		Lang(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	}

	// 用户性别 值为1时是男性，值为2时是女性，值为0时是未知
	public enum Gender {
		male(1), female(2), unknown(0);

		private int sex;

		Gender(int sex) {
			this.sex = sex;
		}

		public int getInt() {
			return sex;
		}
	}

	// （有0、46、64、96、132数值可选，0代表640*640正方形头像）
	public enum Size {
		small(46), middle1(64), middle2(96), big(132);
		private int size;

		Size(int size) {
			this.size = size;
		}

		public int getInt() {
			return size;
		}
	}

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

	public int getSex() {
		return sex;
	}

	public Gender getGender(){
		if(sex == 1){
			return Gender.male;
		}else if(sex == 2){
			return Gender.female;
		}else{
			return Gender.unknown;
	}
	}

	public void setSex(int sex) {
		this.sex = sex;
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

	public String getHeadimgurl(Size size) {
		if (!WeixinUtil.isBlank(headimgurl)) {
			StringBuilder sb = new StringBuilder(headimgurl);
			return sb.replace(0, (headimgurl.length() - 1), size.getInt() + "").toString();
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

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public Lang getLanguage() {
		return language;
	}

	public void setLanguage(Lang language) {
		this.language = language;
	}

	public long getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(long subscribe_time) {
		this.subscribe_time = subscribe_time;
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
		sb.append(", sex=").append(sex);
		sb.append(", province=").append(province);
		sb.append(", city=").append(city);
		sb.append(", country=").append(country);
		sb.append(", headimgurl=").append(headimgurl);
		sb.append(", privilege=").append(privilege);
		sb.append(", language=").append(language);
		sb.append(", subscribe_time=").append(subscribe_time);
		sb.append(", subscribe=").append(subscribe).append("]");
		return sb.toString();
	}
}
