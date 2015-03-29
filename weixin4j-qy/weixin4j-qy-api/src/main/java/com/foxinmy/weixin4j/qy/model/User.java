package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.Gender;
import com.foxinmy.weixin4j.qy.type.UserStatus;

/**
 * 部门成员对象
 * 
 * @className User
 * @author jy
 * @date 2014年11月19日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98">管理成员说明</a>
 */
public class User implements Serializable {

	private static final long serialVersionUID = 4747301605060801611L;
	/**
	 * 必须 员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
	 */
	private String userid;
	/**
	 * 必须 成员名称。长度为1~64个字符
	 */
	private String name;
	/**
	 * 非必须 成员所属部门id列表。注意，每个部门的直属员工上限为1000个
	 */
	private List<Integer> department;
	/**
	 * 非必须 职位信息。长度为0~64个字符
	 */
	private String position;
	/**
	 * 非必须 手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
	 */
	private String mobile;
	/**
	 * 非必须 性别。gender=0表示男，=1表示女。默认gender=0
	 */
	private int gender;
	/**
	 * 非必须 办公电话。长度为0~64个字符
	 */
	private String tel;
	/**
	 * 非必须 邮箱。长度为0~64个字符。企业内必须唯一
	 */
	private String email;
	/**
	 * 非必须 微信号。企业内必须唯一
	 */
	private String weixinid;
	/**
	 * 头像url。注：如果要获取小图将url最后的"/0"改成"/64"即可
	 */
	private String avatar;
	/**
	 * 关注状态: 1=已关注，2=已冻结，4=未关注
	 */
	private int status;
	/**
	 * 非必须 扩展属性。扩展属性需要在WEB管理端创建后才生效，否则忽略未知属性的赋值
	 */
	private List<NameValue> extattr;

	public User() {
		this.extattr = new ArrayList<NameValue>();
	}

	public User(String userid, String name) {
		this(userid, name, null, null, null);
	}

	/**
	 * mobile/weixinid/email三者不能同时为空
	 * 
	 * @param userid
	 *            用户ID
	 * @param name
	 *            用户昵称
	 * @param tel
	 *            号码
	 * @param email
	 *            邮箱
	 * @param weixinid
	 *            微信ID
	 */
	public User(String userid, String name, String tel, String email,
			String weixinid) {
		this.userid = userid;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.weixinid = weixinid;
		this.extattr = new ArrayList<NameValue>();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getDepartment() {
		return department;
	}

	public void setDepartment(List<Integer> department) {
		this.department = department;
	}

	public void setDepartment(Integer... department) {
		this.department = Arrays.asList(department);
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getGender() {
		return gender;
	}

	@JSONField(serialize = false)
	public Gender getEnumGender() {
		if (gender == 0) {
			return Gender.male;
		} else if (gender == 1) {
			return Gender.female;
		} else {
			return Gender.unknown;
		}
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeixinid() {
		return weixinid;
	}

	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}

	@JSONField(serialize = false)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@JSONField(serialize = false)
	public UserStatus getStatus() {
		for (UserStatus userStatus : UserStatus.values()) {
			if (userStatus.getVal() == status) {
				return userStatus;
			}
		}
		return null;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<NameValue> getExtattr() {
		return extattr;
	}

	public void setExtattr(List<NameValue> extattr) {
		this.extattr = extattr;
	}

	public void setExtattr(NameValue... extattr) {
		this.extattr = Arrays.asList(extattr);
	}

	public void pushExattr(String name, String value) {
		pushExattr(new NameValue(name, value));
	}

	public void pushExattr(NameValue nameValue) {
		extattr.add(nameValue);
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", name=" + name + ", department="
				+ department + ", position=" + position + ", mobile=" + mobile
				+ ", gender=" + gender + ", tel=" + tel + ", email=" + email
				+ ", weixinid=" + weixinid + ", avatar=" + avatar + ", status="
				+ status + ", extattr=" + extattr + "]";
	}
}
