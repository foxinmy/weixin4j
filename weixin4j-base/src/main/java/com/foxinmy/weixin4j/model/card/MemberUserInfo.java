package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.foxinmy.weixin4j.type.Gender;
import com.foxinmy.weixin4j.type.card.UserCardStatus;
import com.foxinmy.weixin4j.util.NameValue;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * 会员卡的基本信息
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/21 11:33
 */
public class MemberUserInfo {

    /**
     * openId
     */
    @JSONField(name = "openid")
    private String openId;
    /**
     * 昵称
     */
    @JSONField(name = "nickname")
    private String nickName;

    /**
     * 会员卡编号
     */
    @JSONField(name = "mmebership_number")
    private String         membershipNumber;
    /**
     * 积分
     */
    private Integer        bonus;
    /**
     * 余额
     */
    private Integer        balance;
    /**
     * 性别
     */
    private String         sex;
    /**
     * 用户会员卡状态
     */
    @JSONField(name = "user_card_status")
    private UserCardStatus userCardStatus;

    /**
     * 是否已经被激活，true表示已经被激活，false表示未被激活
     */
    @JSONField(name = "has_active")
    private boolean hasActive;

    /**
     * 用户信息
     */
    @JSONField(name = "user_info")
    private UserInfo userInfo;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public UserCardStatus getUserCardStatus() {
        return userCardStatus;
    }

    public void setUserCardStatus(UserCardStatus userCardStatus) {
        this.userCardStatus = userCardStatus;
    }

    public boolean isHasActive() {
        return hasActive;
    }

    public void setHasActive(boolean hasActive) {
        this.hasActive = hasActive;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static final class UserInfo {

        @JSONField(name = "common_field_list")
        private ArrayList<NameValue> commonFieldValues;

        @JSONField(name = "custom_field_list")
        private ArrayList<NameValue> customFieldValues;

        public ArrayList<NameValue> getCommonFieldValues() {
            return commonFieldValues;
        }

        public void setCommonFieldValues(ArrayList<NameValue> commonFieldValues) {
            this.commonFieldValues = commonFieldValues;
        }

        public ArrayList<NameValue> getCustomFieldValues() {
            return customFieldValues;
        }

        public void setCustomFieldValues(ArrayList<NameValue> customFieldValues) {
            this.customFieldValues = customFieldValues;
        }
    }


}
