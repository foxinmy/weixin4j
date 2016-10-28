package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.ClientPlatformType;
import com.foxinmy.weixin4j.mp.type.Lang;
import com.foxinmy.weixin4j.type.Gender;

/**
 * 个性化菜单匹配规则
 * 
 * @className MenuMatchRule
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月17日
 * @since JDK 1.6
 * @see
 */
public class MenuMatchRule implements Serializable {

	private static final long serialVersionUID = 8115117407710728580L;

	private JSONObject matchRule;

	public MenuMatchRule() {
		this.matchRule = new JSONObject();
	}

	/**
	 * 用户分组id，可通过用户分组管理接口获取
	 */
	private Integer groupId;

	@JSONField(name = "group_id")
	public MenuMatchRule group(int groupId) {
		matchRule.put("group_id", groupId);
		this.groupId = groupId;
		return this;
	}

	/**
	 * 性别
	 */
	private Gender gender;

	@JSONField(name = "sex")
	public void gender0(int sex) {
		this.gender = Gender.values().length >= sex ? Gender.values()[sex - 1]
				: null;
	}

	public MenuMatchRule gender(Gender gender) {
		if (gender != null && gender != Gender.unknown) {
			matchRule.put("sex", gender.ordinal() + 1);
		}
		this.gender = gender;
		return this;
	}

	/**
	 * 客户端版本
	 */
	private ClientPlatformType platformType;

	/**
	 * 请使用 {@link #platform(ClientPlatformType platformType)}}
	 * @param platform
	 */
	@JSONField(name = "client_platform_type")
	public void platform0(int platform) {
		this.platformType = ClientPlatformType.values().length >= platform ? ClientPlatformType
				.values()[platform - 1] : null;
	}

	public MenuMatchRule platform(ClientPlatformType platformType) {
		if (platformType != null) {
			matchRule.put("client_platform_type", platformType.ordinal() + 1);
		}
		this.platformType = platformType;
		return this;
	}

	private String country;

	/**
	 * 国家信息，是用户在微信中设置的地区
	 * <p>
	 * country、province、city组成地区信息，将按照country、province、city的顺序进行验证
	 * ，要符合地区信息表的内容。地区信息从大到小验证，小的可以不填，即若填写了省份信息，则国家信息也必填并且匹配，城市信息可以不填。 例如 “中国
	 * 广东省 广州市”、“中国 广东省”都是合法的地域信息，而“中国 广州市”则不合法，因为填写了城市信息但没有填写省份信息
	 * 
	 * @param country
	 * @return
	 */
	@JSONField(name = "country")
	public MenuMatchRule country(String country) {
		matchRule.put("country", country);
		this.country = country;
		return this;
	}

	private String province;

	/**
	 * 省份信息，是用户在微信中设置的地区
	 * <p>
	 * country、province、city组成地区信息，将按照country、province、city的顺序进行验证，要符合地区信息表的内容。
	 * 地区信息从大到小验证，小的可以不填，即若填写了省份信息，则国家信息也必填并且匹配，城市信息可以不填。 例如 “中国 广东省 广州市”、“中国
	 * 广东省”都是合法的地域信息，而“中国 广州市”则不合法，因为填写了城市信息但没有填写省份信息
	 * 
	 * @param country
	 * @return
	 */
	@JSONField(name = "province")
	public MenuMatchRule province(String province) {
		matchRule.put("province", province);
		this.province = province;
		return this;
	}

	private String city;

	/**
	 * 城市信息，是用户在微信中设置的地区
	 * <p>
	 * country、province、city组成地区信息，将按照country、province、city的顺序进行验证，要符合地区信息表的内容。
	 * 地区信息从大到小验证，小的可以不填，即若填写了省份信息，则国家信息也必填并且匹配，城市信息可以不填。 例如 “中国 广东省 广州市”、“中国
	 * 广东省”都是合法的地域信息，而“中国 广州市”则不合法，因为填写了城市信息但没有填写省份信息
	 * 
	 * @param city
	 * @return
	 */
	@JSONField(name = "city")
	public MenuMatchRule city(String city) {
		matchRule.put("city", city);
		this.city = city;
		return this;
	}

	/**
	 * 语言信息，是用户在微信中设置的语言
	 */
	private Lang language;

	/**
	 * 请使用 {@link #language(Lang language)}
	 * @param language
	 */
	@JSONField(name = "language")
	public void language0(int language) {
		this.language = Lang.values().length >= language ? Lang.values()[language - 1]
				: null;
	}

	public MenuMatchRule language(Lang language) {
		if (language != null) {
			matchRule.put("language", language.ordinal() + 1);
		}
		this.language = language;
		return this;
	}

	public ClientPlatformType getPlatformType() {
		return platformType;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public Gender getGender() {
		return gender;
	}

	public String getCountry() {
		return country;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public Lang getLanguage() {
		return language;
	}

	public boolean hasRule() {
		return !matchRule.isEmpty();
	}

	public JSONObject getRule() {
		return this.matchRule;
	}

	@Override
	public String toString() {
		return "MenuMatchRule [groupId=" + groupId + ", gender=" + gender
				+ ", platformType=" + platformType + ", country=" + country
				+ ", province=" + province + ", city=" + city + ", language="
				+ language + "]";
	}
}
