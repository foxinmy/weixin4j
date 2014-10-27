package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

/**
 * 网页授权结果
 * 
 * @className AuthResult
 * @author jy.hu
 * @date 2014年4月8日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF">网页授权获取用户基本资料</a>
 */
public class AuthResult implements Serializable {

	private static final long serialVersionUID = 654855396163854805L;

	/**
	 * 网页授权获取code
	 * 
	 * @className AuthScope
	 * @author jy.hu
	 * @date 2014年4月8日
	 * @since JDK 1.7
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF#.E7.AC.AC.E4.B8.80.E6.AD.A5.EF.BC.9A.E7.94.A8.E6.88.B7.E5.90.8C.E6.84.8F.E6.8E.88.E6.9D.83.EF.BC.8C.E8.8E.B7.E5.8F.96code">获取code</a>
	 */
	public enum AuthScope {
		BASE("snsapi_base"), USERINFO("snsapi_userinfo");

		private String name;

		AuthScope(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum AuthCode {
		OK, FAILED, REDIRECT
	}

	private AuthCode authCode;
	private String location;
	private UserToken accessToken;

	public AuthResult(String location) {
		this.location = location;
		this.authCode = AuthCode.REDIRECT;
	}

	public AuthResult(UserToken accessToken) {
		this.authCode = AuthCode.OK;
		this.accessToken = accessToken;
	}

	public AuthResult(String location, AuthCode authCode) {
		this.location = location;
		this.authCode = authCode;
	}

	public AuthCode getAuthCode() {
		return authCode;
	}

	public void setAuthCode(AuthCode authCode) {
		this.authCode = authCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public UserToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(UserToken accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[AuthResult authCode=").append(authCode);
		sb.append(", location=").append(location);
		sb.append(", accessToken=").append(accessToken).append("]");
		return sb.toString();
	}
}
