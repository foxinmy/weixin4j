package com.foxinmy.weixin4j.mp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.XmlResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.api.CustomApi;
import com.foxinmy.weixin4j.mp.api.GroupApi;
import com.foxinmy.weixin4j.mp.api.HelperApi;
import com.foxinmy.weixin4j.mp.api.MassApi;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.mp.api.NotifyApi;
import com.foxinmy.weixin4j.mp.api.PayApi;
import com.foxinmy.weixin4j.mp.api.QrApi;
import com.foxinmy.weixin4j.mp.api.TmplApi;
import com.foxinmy.weixin4j.mp.api.UserApi;
import com.foxinmy.weixin4j.mp.message.NotifyMessage;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.model.CustomRecord;
import com.foxinmy.weixin4j.mp.model.Following;
import com.foxinmy.weixin4j.mp.model.Group;
import com.foxinmy.weixin4j.mp.model.KfAccount;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.QRParameter;
import com.foxinmy.weixin4j.mp.model.SemQuery;
import com.foxinmy.weixin4j.mp.model.SemResult;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.mp.payment.ApiResult;
import com.foxinmy.weixin4j.mp.payment.Refund;
import com.foxinmy.weixin4j.mp.payment.RefundResult;
import com.foxinmy.weixin4j.mp.payment.v2.Order;
import com.foxinmy.weixin4j.mp.type.BillType;
import com.foxinmy.weixin4j.mp.type.IdQuery;
import com.foxinmy.weixin4j.mp.type.IndustryType;
import com.foxinmy.weixin4j.msg.model.Base;
import com.foxinmy.weixin4j.msg.model.MpArticle;
import com.foxinmy.weixin4j.msg.model.Video;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 微信公众平台接口实现
 * 
 * @className WeixinProxy
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php">api文档</a>
 */
public class WeixinProxy {

	private final MediaApi mediaApi;
	private final NotifyApi notifyApi;
	private final CustomApi customApi;
	private final MassApi massApi;
	private final UserApi userApi;
	private final GroupApi groupApi;
	private final MenuApi menuApi;
	private final QrApi qrApi;
	private final TmplApi tmplApi;
	private final HelperApi helperApi;
	private final PayApi payApi;

	/**
	 * 默认采用文件存放Token信息
	 */
	public WeixinProxy() {
		this(new FileTokenHolder(AccountType.MP));
	}

	/**
	 * appid,appsecret<br>
	 * <font color="red">将无法调用支付相关接口</font>
	 * 
	 * @param appid
	 * @param appsecret
	 */
	public WeixinProxy(String appid, String appsecret) {
		this(new WeixinMpAccount(appid, appsecret));
	}

	/**
	 * WeixinAccount对象
	 * 
	 * @param weixinAccount 微信账户
	 */
	public WeixinProxy(WeixinMpAccount weixinAccount) {
		this(new FileTokenHolder(weixinAccount));
	}

	/**
	 * TokenHolder对象
	 * 
	 * @param tokenHolder
	 */
	public WeixinProxy(TokenHolder tokenHolder) {
		this.mediaApi = new MediaApi(tokenHolder);
		this.notifyApi = new NotifyApi(tokenHolder);
		this.customApi = new CustomApi(tokenHolder);
		this.massApi = new MassApi(tokenHolder);
		this.userApi = new UserApi(tokenHolder);
		this.groupApi = new GroupApi(tokenHolder);
		this.menuApi = new MenuApi(tokenHolder);
		this.qrApi = new QrApi(tokenHolder);
		this.tmplApi = new TmplApi(tokenHolder);
		this.helperApi = new HelperApi(tokenHolder);
		this.payApi = new PayApi(tokenHolder);
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param file
	 *            媒体对象
	 * @return 上传到微信服务器返回的媒体标识
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#uploadMedia(File, MediaType)}
	 * @throws WeixinException
	 * @throws IOException
	 */
	public String uploadMedia(File file) throws WeixinException, IOException {
		return mediaApi.uploadMedia(file);
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param file
	 *            文件对象
	 * @param mediaType
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @throws IOException
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#uploadMedia(String, byte[],String)}
	 */
	public String uploadMedia(File file, MediaType mediaType)
			throws WeixinException, IOException {
		return mediaApi.uploadMedia(file, mediaType);
	}

	/**
	 * 上传媒体文件
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param fileName
	 *            文件名
	 * @param data
	 *            媒体数据包
	 * @param mediaType
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @throws WeixinException
	 */
	public String uploadMedia(String fileName, byte[] data, String mediaType)
			throws WeixinException {
		return mediaApi.uploadMedia(fileName, data, mediaType);
	}

	/**
	 * 下载媒体文件
	 * <p>
	 * 正常情况下返回表头如Content-Type: image/jpeg,否则抛出异常.
	 * </p>
	 * 
	 * @param mediaId
	 *            存储在微信服务器上的媒体标识
	 * @param mediaType
	 *            媒体类型
	 * @return 写入硬盘后的文件对象
	 * @throws WeixinException
	 * @throws IOException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#downloadMedia(String)}
	 */
	public File downloadMedia(String mediaId, MediaType mediaType)
			throws WeixinException, IOException {
		return mediaApi.downloadMedia(mediaId, mediaType);
	}

	/**
	 * 下载媒体文件
	 * 
	 * @param mediaId
	 *            媒体ID
	 * @return 二进制数据包
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 */
	public byte[] downloadMedia(String mediaId) throws WeixinException {
		return mediaApi.downloadMedia(mediaId);
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 * 
	 * @param notify
	 *            客服消息对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">发送客服消息</a>
	 * @see com.foxinmy.weixin4j.msg.model.Text
	 * @see com.foxinmy.weixin4j.msg.model.Image
	 * @see com.foxinmy.weixin4j.msg.model.Voice
	 * @see com.foxinmy.weixin4j.msg.model.Video
	 * @see com.foxinmy.weixin4j.msg.model.Music
	 * @see com.foxinmy.weixin4j.msg.model.News
	 * @see com.foxinmy.weixin4j.mp.api.NotifyApi
	 */
	public JsonResult sendNotify(NotifyMessage notify) throws WeixinException {
		return notifyApi.sendNotify(notify);
	}

	/**
	 * 客服聊天记录
	 * 
	 * @param openId
	 *            用户标识 为空时则查询全部记录
	 * @param starttime
	 *            查询开始时间
	 * @param endtime
	 *            查询结束时间 每次查询不能跨日查询
	 * @param pagesize
	 *            每页大小 每页最多拉取1000条
	 * @param pageindex
	 *            查询第几页 从1开始
	 * @see com.foxinmy.weixin4j.mp.model.CustomRecord
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href="http://dkf.qq.com/document-1_1.html">查询客服聊天记录</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AE%A2%E6%9C%8D%E8%81%8A%E5%A4%A9%E8%AE%B0%E5%BD%95">查询客服聊天记录</a>
	 * @throws WeixinException
	 */
	public List<CustomRecord> getCustomRecord(String openId, Date starttime,
			Date endtime, int pagesize, int pageindex) throws WeixinException {
		return customApi.getCustomRecord(openId, starttime, endtime, pagesize,
				pageindex);
	}

	/**
	 * 获取公众号中所设置的客服基本信息，包括客服工号、客服昵称、客服登录账号
	 * 
	 * @param isOnline
	 *            是否在线 为ture时可以可以获取客服在线状态（手机在线、PC客户端在线、手机和PC客户端全都在线）、客服自动接入最大值、
	 *            客服当前接待客户数
	 * @return 多客服信息列表
	 * @see com.foxinmy.weixin4j.mp.model.KfAccount
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href="http://dkf.qq.com/document-3_1.html">获取客服基本信息</a>
	 * @see <a href="http://dkf.qq.com/document-3_2.html">获取在线客服接待信息</a>
	 * @throws WeixinException
	 */
	public List<KfAccount> getKfAccountList(boolean isOnline)
			throws WeixinException {
		return customApi.getKfAccountList(isOnline);
	}

	/**
	 * 上传图文消息,一个图文消息支持1到10条图文
	 * 
	 * @param articles
	 *            图片消息
	 * @return 媒体ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3">高级群发</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF.E7.B4.A0.E6.9D.90">上传图文消息</a>
	 * @see com.foxinmy.weixin4j.msg.model.MpArticle
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 */
	public String uploadArticle(List<MpArticle> articles)
			throws WeixinException {
		return massApi.uploadArticle(articles);
	}

	/**
	 * 上传分组群发的视频素材
	 * 
	 * @param video
	 *            视频对象 其中mediaId媒体文件中上传得到的Id 不能为空
	 * @return 上传后的ID
	 * @throws WeixinException
	 * 
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3">高级群发</a>
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see com.foxinmy.weixin4j.msg.model.Video
	 * @see com.foxinmy.weixin4j.msg.model.MpVideo
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(File)}
	 */
	public String uploadVideo(Video video) throws WeixinException {
		return massApi.uploadVideo(video);
	}

	/**
	 * 分组群发
	 * 
	 * @param box
	 *            消息项
	 * @param groupId
	 *            分组ID
	 * @return 群发后的消息ID
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see com.foxinmy.weixin4j.msg.model.Text
	 * @see com.foxinmy.weixin4j.msg.model.Image
	 * @see com.foxinmy.weixin4j.msg.model.Voice
	 * @see com.foxinmy.weixin4j.msg.model.MpVideo
	 * @see com.foxinmy.weixin4j.msg.model.MpNews
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(File)}
	 * @see {@link com.foxinmy.weixin4j.mp.api.GroupApi#getGroupByOpenId(String)}
	 * @see {@link com.foxinmy.weixin4j.mp.api.GroupApi#getGroups()}
	 */
	public String massByGroupId(Base box, int groupId) throws WeixinException {
		return massApi.massByGroupId(box, groupId);
	}

	/**
	 * 分组ID群发图文消息
	 * 
	 * @param articles
	 *            图文列表
	 * @param groupId
	 *            分组ID
	 * @return 群发后的消息ID
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#massByGroupId(Base,int)}
	 * @throws WeixinException
	 */
	public String massArticleByGroupId(List<MpArticle> articles, int groupId)
			throws WeixinException {
		return massApi.massArticleByGroupId(articles, groupId);
	}

	/**
	 * openId群发
	 * 
	 * @param box
	 *            消息项
	 * @param openIds
	 *            openId列表
	 * @return 群发后的消息ID
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see com.foxinmy.weixin4j.msg.model.Text
	 * @see com.foxinmy.weixin4j.msg.model.Image
	 * @see com.foxinmy.weixin4j.msg.model.Voice
	 * @see com.foxinmy.weixin4j.msg.model.MpVideo
	 * @see com.foxinmy.weixin4j.msg.model.MpNews
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(File)}
	 * @see {@link com.foxinmy.weixin4j.mp.api.UserApi#getUser(String)}
	 */
	public String massByOpenIds(Base box, String... openIds)
			throws WeixinException {
		return massApi.massByOpenIds(box, openIds);
	}

	/**
	 * 根据openid群发图文消息
	 * 
	 * @param articles
	 *            图文列表
	 * @param openIds
	 *            openId列表
	 * @return 群发后的消息ID
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#massByOpenIds(Base,String...)}
	 * @throws WeixinException
	 */
	public String massArticleByOpenIds(List<MpArticle> articles,
			String... openIds) throws WeixinException {
		return massApi.massArticleByOpenIds(articles, openIds);
	}

	/**
	 * 删除群发消息
	 * <p>
	 * 请注意,只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效,已经收到的用户,还是能在其本地看到消息卡片
	 * </p>
	 * 
	 * @param msgid
	 *            发送出去的消息ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E5.88.A0.E9.99.A4.E7.BE.A4.E5.8F.91">删除群发</a>
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#massByGroupId(Base, int)}
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#massByOpenIds(Base, String...)

	 */
	public JsonResult deleteMassNews(String msgid) throws WeixinException {
		return massApi.deleteMassNews(msgid);
	}

	/**
	 * 获取token
	 * 
	 * @param code
	 *            用户授权后返回的code
	 * @return token对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.80.9A.E8.BF.87code.E6.8D.A2.E5.8F.96.E7.BD.91.E9.A1.B5.E6.8E.88.E6.9D.83access_token">获取用户token</a>
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public OauthToken getOauthToken(String code) throws WeixinException {
		return userApi.getOauthToken(code);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param token
	 *            授权票据
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF#.E7.AC.AC.E5.9B.9B.E6.AD.A5.EF.BC.9A.E6.8B.89.E5.8F.96.E7.94.A8.E6.88.B7.E4.BF.A1.E6.81.AF.28.E9.9C.80scope.E4.B8.BA_snsapi_userinfo.29">拉取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public User getUser(OauthToken token) throws WeixinException {
		return userApi.getUser(token);
	}

	/**
	 * 获取用户信息
	 * <p>
	 * 在关注者与公众号产生消息交互后,公众号可获得关注者的OpenID（加密后的微信号,每个用户对每个公众号的OpenID是唯一的,对于不同公众号,
	 * 同一用户的openid不同）,公众号可通过本接口来根据OpenID获取用户基本信息,包括昵称、头像、性别、所在城市、语言和关注时间
	 * </p>
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF">获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public User getUser(String openId) throws WeixinException {
		return userApi.getUser(openId);
	}

	/**
	 * 获取用户一定数量(10000)的关注者列表
	 * 
	 * @param nextOpenId
	 *            下一次拉取数据的openid
	 * @return 关注信息
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%85%B3%E6%B3%A8%E8%80%85%E5%88%97%E8%A1%A8">获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public Following getFollowing(String nextOpenId) throws WeixinException {
		return userApi.getFollowing(nextOpenId);
	}

	/**
	 * 获取用户全部的关注者列表
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 * 
	 * @return 用户对象集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%85%B3%E6%B3%A8%E8%80%85%E5%88%97%E8%A1%A8">获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#getFollowing(String)}
	 */
	public List<User> getAllFollowing() throws WeixinException {
		return userApi.getAllFollowing();
	}

	/**
	 * 设置用户备注名
	 * 
	 * @param openId
	 *            用户ID
	 * @param remark
	 *            备注名
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E5%A4%87%E6%B3%A8%E5%90%8D%E6%8E%A5%E5%8F%A3">设置用户备注名</a>
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public JsonResult remarkUserName(String openId, String remark)
			throws WeixinException {
		return userApi.remarkUserName(openId, remark);
	}

	/**
	 * 创建分组
	 * 
	 * @param name
	 *            组名称
	 * @return group对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84">创建分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toCreateJson()
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public Group createGroup(String name) throws WeixinException {
		return groupApi.createGroup(name);
	}

	/**
	 * 查询所有分组
	 * 
	 * @return 组集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E6.9F.A5.E8.AF.A2.E6.89.80.E6.9C.89.E5.88.86.E7.BB.84">查询所有分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public List<Group> getGroups() throws WeixinException {
		return groupApi.getGroups();
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @return 组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E6.9F.A5.E8.AF.A2.E7.94.A8.E6.88.B7.E6.89.80.E5.9C.A8.E5.88.86.E7.BB.84">查询用户所在分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public int getGroupByOpenId(String openId) throws WeixinException {
		return groupApi.getGroupByOpenId(openId);
	}

	/**
	 * 修改分组名
	 * 
	 * @param groupId
	 *            组ID
	 * @param name
	 *            组名称
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E4.BF.AE.E6.94.B9.E5.88.86.E7.BB.84.E5.90.8D">修改分组名</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toModifyJson()
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public JsonResult modifyGroup(int groupId, String name)
			throws WeixinException {
		return groupApi.modifyGroup(groupId, name);
	}

	/**
	 * 移动分组
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @param groupId
	 *            组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public JsonResult moveGroup(String openId, int groupId)
			throws WeixinException {
		return groupApi.moveGroup(openId, groupId);
	}

	/**
	 * 自定义菜单
	 * 
	 * @param btnList
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%9B%E5%BB%BA%E6%8E%A5%E5%8F%A3">创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.type.ButtonType
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 */
	public JsonResult createMenu(List<Button> btnList) throws WeixinException {
		return menuApi.createMenu(btnList);
	}

	/**
	 * 查询菜单
	 * 
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E6%9F%A5%E8%AF%A2%E6%8E%A5%E5%8F%A3">查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 */
	public List<Button> getMenu() throws WeixinException {
		return menuApi.getMenu();
	}

	/**
	 * 删除菜单
	 * 
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%A0%E9%99%A4%E6%8E%A5%E5%8F%A3">删除菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 */
	public JsonResult deleteMenu() throws WeixinException {
		return menuApi.deleteMenu();
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param parameter
	 * @return byte数据包
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.QrApi
	 */
	public byte[] getQRData(QRParameter parameter) throws WeixinException {
		return qrApi.getQRData(parameter);
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param sceneId
	 *            场景值
	 * @param expireSeconds
	 *            过期秒数 如果小于等于0则 视为永久二维码
	 * @return byte数据包
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.QrApi
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#getQR(QRParameter)}
	 */
	public byte[] getQRData(int sceneId, int expireSeconds)
			throws WeixinException {
		return qrApi.getQRData(sceneId, expireSeconds);
	}

	/**
	 * 生成带参数的二维码
	 * <p>
	 * 二维码分为临时跟永久两种,扫描时触发推送带参数事件
	 * </p>
	 * 
	 * @param parameter
	 *            二维码参数
	 * @return 硬盘存储的文件对象
	 * @throws WeixinException
	 * @throws IOException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%94%9F%E6%88%90%E5%B8%A6%E5%8F%82%E6%95%B0%E7%9A%84%E4%BA%8C%E7%BB%B4%E7%A0%81">二维码</a>
	 * @see com.foxinmy.weixin4j.mp.model.QRParameter
	 * @see com.foxinmy.weixin4j.mp.api.QrApi
	 */
	public File getQR(QRParameter parameter) throws WeixinException,
			IOException {
		return qrApi.getQR(parameter);
	}

	/**
	 * 设置所属行业(每月可修改行业1次，账号仅可使用所属行业中相关的模板)
	 * 
	 * @param industryType
	 *            所处行业 目前不超过两个
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.type.IndustryType
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 * @see <a href="http://mp.weixin.qq.com/wiki/17/304
	 *      c1885ea66dbedf7dc170d84999a9d
	 *      .html#.E8.AE.BE.E7.BD.AE.E6.89.80.E5.B1
	 *      .9E.E8.A1.8C.E4.B8.9A">设置所处行业</a>
	 */
	public JsonResult setTmplIndustry(IndustryType... industryType)
			throws WeixinException {
		return tmplApi.setTmplIndustry(industryType);
	}

	/**
	 * 获取模板ID
	 * 
	 * @param shortId
	 *            模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return 模板ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E8.8E.B7.E5.BE.97.E6.A8.A1.E6.9D.BFID">获得模板ID</a>
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 */
	public String getTemplateId(String shortId) throws WeixinException {
		return tmplApi.getTemplateId(shortId);
	}

	/**
	 * 发送模板消息
	 * 
	 * @param tplMessage 模板消息主体
	 * @return 发送结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%A8%A1%E6%9D%BF%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3">模板消息</a>
	 * @see com.foxinmy.weixin4j.mp.message.TemplateMessage
	 * @seee com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 */
	public JsonResult sendTmplMessage(TemplateMessage tplMessage)
			throws WeixinException {
		return tmplApi.sendTmplMessage(tplMessage);
	}

	/**
	 * 长链接转短链接
	 * 
	 * @param url
	 * @return 短链接
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%95%BF%E9%93%BE%E6%8E%A5%E8%BD%AC%E7%9F%AD%E9%93%BE%E6%8E%A5%E6%8E%A5%E5%8F%A3">长链接转短链接</a>
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 */
	public String getShorturl(String url) throws WeixinException {
		return helperApi.getShorturl(url);
	}

	/**
	 * 发货通知
	 * 
	 * @param openId
	 *            用户ID
	 * @param transid
	 *            交易单号
	 * @param outTradeNo
	 *            订单号
	 * @param status
	 *            成功|失败
	 * @param statusMsg
	 *            status为失败时携带的信息
	 * @return 发货处理结果
	 * @since V2 & V3
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @throws WeixinException
	 */
	public JsonResult deliverNotify(String openId, String transid,
			String outTradeNo, boolean status, String statusMsg)
			throws WeixinException {
		return payApi.deliverNotify(openId, transid, outTradeNo, status,
				statusMsg);
	}

	/**
	 * 订单查询
	 * 
	 * @param outTradeNo
	 *            订单号
	 * @return 订单信息
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 */
	public Order orderQueryV2(WeixinMpAccount weixinAccount, String outTradeNo)
			throws WeixinException {
		return payApi.orderQueryV2(outTradeNo);
	}

	/**
	 * 维权处理
	 * 
	 * @param openId
	 *            用户ID
	 * @param feedbackId
	 *            维权单号
	 * @return 调用结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @since V2 & V3
	 * @throws WeixinException
	 */
	public JsonResult updateFeedback(String openId, String feedbackId)
			throws WeixinException {
		return payApi.updateFeedback(openId, feedbackId);
	}

	/**
	 * V3订单查询
	 * 
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id、out_trade_no 二 选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @throws WeixinException
	 */
	public com.foxinmy.weixin4j.mp.payment.v3.Order orderQueryV3(IdQuery idQuery)
			throws WeixinException {
		return payApi.orderQueryV3(idQuery);
	}

	/**
	 * 下载对账单<br>
	 * 1.微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账 单中,跟原支付单订单号一致,bill_type 为
	 * REVOKED;<br>
	 * 2.微信在次日 9 点启动生成前一天的对账单,建议商户 9 点半后再获取;<br>
	 * 3.对账单中涉及金额的字段单位为“元”。<br>
	 * 
	 * @param billDate
	 *            下载对账单的日期
	 * @param billType
	 *            下载对账单的类型 ALL,返回当日所有订单信息, 默认值 SUCCESS,返回当日成功支付的订单
	 *            REFUND,返回当日退款订单
	 * @return excel表格
	 * @since V2 & V3
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @throws WeixinException
	 * @throws IOException
	 */
	public File downloadbill(Date billDate, BillType billType)
			throws WeixinException, IOException {
		return payApi.downloadbill(billDate, billType);
	}

	/**
	 * 申请退款(V3请求需要双向证书)</br>
	 * <p style="color:red">
	 * 交易时间超过 1 年的订单无法提交退款; </br> 支持部分退款,部分退需要设置相同的订单号和不同的 out_refund_no。一笔退款失
	 * 败后重新提交,要采用原来的 out_refund_no。总退款金额不能超过用户实际支付金额。</br>
	 * </p>
	 * 
	 * @param ca
	 *            证书文件<font color="red">V2版本时无需传入</font>
	 * @param idQuery
	 *            ) 商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @param outRefundNo
	 *            商户系统内部的退款单号,商 户系统内部唯一,同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额,单位为元
	 * @param refundFee
	 *            退款总金额,单位为元,可以做部分退款
	 * @param opUserId
	 *            操作员帐号, 默认为商户号
	 * @param opUserPasswd
	 *            <font color="red">V3版本留空,V2版本需传入值</font>
	 * 
	 * @return 退款申请结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.payment.RefundResult
	 * @since V2 & V3
	 * @throws WeixinException
	 * @throws IOException
	 */
	public RefundResult refund(InputStream ca, IdQuery idQuery,
			String outRefundNo, double totalFee, double refundFee,
			String opUserId, String opUserPasswd) throws WeixinException,
			IOException {
		return payApi.refund(ca, idQuery, outRefundNo, totalFee, refundFee,
				opUserId, opUserPasswd);
	}

	/**
	 * 不同的退款接口选择</br> V3支付则采用properties中配置的ca文件</br>
	 * V2支付则需要传入opUserPasswd参数</br>
	 * 
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#refund(InputStream, IdQuery, String, double, double, String,String)}
	 */
	public RefundResult refund(IdQuery idQuery, String outRefundNo,
			double totalFee, double refundFee, String opUserId,
			String opUserPasswd) throws WeixinException, IOException {
		return payApi.refund(idQuery, outRefundNo, totalFee, refundFee,
				opUserId, opUserPasswd);
	}

	/**
	 * 退款查询</br> 退款有一定延时,用零钱支付的退款20分钟内到账,银行卡支付的退款 3 个工作日后重新查询退款状态
	 * 
	 * @param idQuery
	 *            单号 refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必填一个,优先级为:
	 *            refund_id>out_refund_no>transaction_id>out_trade_no
	 * @return 退款记录
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @see com.foxinmy.weixin4j.mp.payment.Refund
	 * @since V2 & V3
	 * @throws WeixinException
	 */
	public Refund refundQuery(IdQuery idQuery) throws WeixinException {
		return payApi.refundQuery(idQuery);
	}

	/**
	 * 关闭订单</br> 当订单支付失败,调用关单接口后用新订单号重新发起支付,如果关单失败,返回已完
	 * 成支付请按正常支付处理。如果出现银行掉单,调用关单成功后,微信后台会主动发起退款。
	 * 
	 * @param outTradeNo
	 *            商户系统内部的订单号
	 * @return 执行结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @since V3
	 * @throws WeixinException
	 */
	public XmlResult closeOrder(String outTradeNo) throws WeixinException {
		return payApi.closeOrder(outTradeNo);
	}

	/**
	 * native支付URL转短链接
	 * 
	 * @param url
	 *            具有native标识的支付URL
	 * @return 转换后的短链接
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @throws WeixinException
	 */
	public String getPayShorturl(String url) throws WeixinException {
		return payApi.getShorturl(url);
	}

	/**
	 * 语义理解
	 * 
	 * @param semQuery
	 *            语义理解协议
	 * @return 语义理解结果
	 * @see com.foxinmy.weixin4j.mp.model.SemQuery
	 * @see com.foxinmy.weixin4j.mp.model.SemResult
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%AF%AD%E4%B9%89%E7%90%86%E8%A7%A3">语义理解</a>
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 * @throws WeixinException
	 */
	public SemResult semantic(SemQuery semQuery) throws WeixinException {
		return helperApi.semantic(semQuery);
	}

	/**
	 * 获取微信服务器IP地址
	 * 
	 * @return IP地址
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%BE%AE%E4%BF%A1%E6%9C%8D%E5%8A%A1%E5%99%A8IP%E5%9C%B0%E5%9D%80">获取IP地址</a>
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 * @throws WeixinException
	 */
	public List<String> getcallbackip() throws WeixinException {
		return helperApi.getcallbackip();
	}

	/**
	 * 冲正订单(需要证书)</br> 当支付返回失败,或收银系统超时需要取消交易,可以调用该接口</br> 接口逻辑:支
	 * 付失败的关单,支付成功的撤销支付</br> <font color="red">7天以内的单可撤销,其他正常支付的单
	 * 如需实现相同功能请调用退款接口</font></br> <font
	 * color="red">调用扣款接口后请勿立即调用撤销,需要等待5秒以上。先调用查单接口,如果没有确切的返回,再调用撤销</font></br>
	 * 
	 * @param ca
	 *            证书文件
	 * @param idQuery
	 *            商户系统内部的订单号, transaction_id 、 out_trade_no 二选一,如果同时存在优先级:
	 *            transaction_id> out_trade_no
	 * @return 撤销结果
	 * @see com.foxinmy.weixin4j.mp.api.PayApi
	 * @throws WeixinException
	 */
	public ApiResult reverse(InputStream ca, IdQuery idQuery)
			throws WeixinException {
		return payApi.reverse(ca, idQuery);
	}

	/**
	 * 冲正撤销:默认采用properties中配置的ca文件
	 * 
	 * @param idQuery
	 *            transaction_id、out_trade_no 二选一
	 * @return 撤销结果
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#reverse(InputStream, IdQuery)}
	 * @throws WeixinException
	 * @throws IOException
	 */
	public ApiResult reverse(IdQuery idQuery) throws WeixinException,
			IOException {
		return payApi.reverse(idQuery);
	}
}
