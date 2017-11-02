package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 第三方组件授权信息
 * 
 * @className ComponentAuthorizer
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月5日
 * @since JDK 1.6
 */
public class ComponentAuthorizer implements Serializable {
	private static final long serialVersionUID = -3610172415045923599L;
	/**
	 * 授权方appId
	 */
	@JSONField(name = "appid")
	private String appId;
	/**
	 * 授权方昵称
	 */
	@JSONField(name = "nick_name")
	private String nickName;
	/**
	 * 授权方头像
	 */
	@JSONField(name = "head_img")
	private String headImg;
	/**
	 * 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
	 */
	@JSONField(deserialize = false)
	private int serviceType;
	/**
	 * 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4
	 * 代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
	 */
	@JSONField(deserialize = false)
	private int verifyType;
	/**
	 * 授权方公众号的原始ID
	 */
	@JSONField(name = "user_name")
	private String userName;
	/**
	 * 授权方公众号所设置的微信号，可能为空
	 */
	@JSONField(name = "alias")
	private String alias;
	/**
	 * 二维码图片的URL，开发者最好自行也进行保存
	 */
	@JSONField(name = "qrcode_url")
	private String qrcodeUrl;
	/**
	 * 公众号功能的开通状况
	 */
	@JSONField(name = "business_info")
	private BusinessInfo businessInfo;
	/**
	 * 公众号授权给开发者的权限集列表，ID为1到15时分别代表： 消息管理权限 用户管理权限 帐号服务权限 网页服务权限 微信小店权限 微信多客服权限
	 * 群发与通知权限 微信卡券权限 微信扫一扫权限 微信连WIFI权限 素材管理权限 微信摇周边权限 微信门店权限 微信支付权限 自定义菜单权限
	 */
	@JSONField(deserialize = false)
	private List<Integer> privileges;

	/**
	 * 公众号的主体名称
	 */
	@JSONField(name = "principal_name")
	private String principalName;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public int getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(int verifyType) {
		this.verifyType = verifyType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(BusinessInfo businessInfo) {
		this.businessInfo = businessInfo;
	}

	public List<Integer> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Integer> privileges) {
		this.privileges = privileges;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	@Override
	public String toString() {
		return "ComponentAuthInfo [nickName=" + nickName + ", principalName="
				+ principalName + ", headImg=" + headImg + ", serviceType="
				+ serviceType + ", verifyType=" + verifyType + ", userName="
				+ userName + ", alias=" + alias + ", qrcodeUrl=" + qrcodeUrl
				+ ", businessInfo=" + businessInfo + ", privileges="
				+ privileges + "]";
	}

	public static class BusinessInfo implements Serializable {
		private static final long serialVersionUID = 3106626182191149662L;
		/**
		 * 是否开通微信门店功能
		 */
		@JSONField(name = "open_store")
		private boolean openStore;
		/**
		 * 是否开通微信扫商品功能
		 */
		@JSONField(name = "open_scan")
		private boolean openScan;
		/**
		 * 是否开通微信支付功能
		 */
		@JSONField(name = "open_pay")
		private boolean openPay;
		/**
		 * 是否开通微信卡券功能
		 */
		@JSONField(name = "open_card")
		private boolean openCard;
		/**
		 * 是否开通微信摇一摇功能
		 */
		@JSONField(name = "open_shake")
		private boolean openShake;

		public boolean isOpenStore() {
			return openStore;
		}

		public void setOpenStore(boolean openStore) {
			this.openStore = openStore;
		}

		public boolean isOpenScan() {
			return openScan;
		}

		public void setOpenScan(boolean openScan) {
			this.openScan = openScan;
		}

		public boolean isOpenPay() {
			return openPay;
		}

		public void setOpenPay(boolean openPay) {
			this.openPay = openPay;
		}

		public boolean isOpenCard() {
			return openCard;
		}

		public void setOpenCard(boolean openCard) {
			this.openCard = openCard;
		}

		public boolean isOpenShake() {
			return openShake;
		}

		public void setOpenShake(boolean openShake) {
			this.openShake = openShake;
		}

		@Override
		public String toString() {
			return "BusinessInfo [openStore=" + openStore + ", openScan="
					+ openScan + ", openPay=" + openPay + ", openCard="
					+ openCard + ", openShake=" + openShake + "]";
		}
	}
}
