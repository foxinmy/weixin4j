package com.foxinmy.weixin4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.api.GroupApi;
import com.foxinmy.weixin4j.api.HelperApi;
import com.foxinmy.weixin4j.api.MassApi;
import com.foxinmy.weixin4j.api.MediaApi;
import com.foxinmy.weixin4j.api.MenuApi;
import com.foxinmy.weixin4j.api.NotifyApi;
import com.foxinmy.weixin4j.api.QrApi;
import com.foxinmy.weixin4j.api.TmplApi;
import com.foxinmy.weixin4j.api.UserApi;
import com.foxinmy.weixin4j.api.token.FileTokenApi;
import com.foxinmy.weixin4j.api.token.TokenApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.BaseResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.CustomRecord;
import com.foxinmy.weixin4j.model.Following;
import com.foxinmy.weixin4j.model.Group;
import com.foxinmy.weixin4j.model.MpArticle;
import com.foxinmy.weixin4j.model.QRParameter;
import com.foxinmy.weixin4j.model.User;
import com.foxinmy.weixin4j.model.UserToken;
import com.foxinmy.weixin4j.msg.model.Article;
import com.foxinmy.weixin4j.msg.model.BaseMsg;
import com.foxinmy.weixin4j.msg.notify.BaseNotify;
import com.foxinmy.weixin4j.msg.out.TemplateMessage;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 微信服务实现
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
	private final MassApi massApi;
	private final UserApi userApi;
	private final GroupApi groupApi;
	private final MenuApi menuApi;
	private final QrApi qrApi;
	private final TmplApi tmplApi;
	private final HelperApi helperApi;

	/**
	 * 默认采用文件存放Token跟配置文件中的appi信息
	 */
	public WeixinProxy() {
		this(new FileTokenApi());
	}

	/**
	 * 也可接受传递过来的appid跟appsecret
	 * 
	 * @param appid
	 * @param appsecret
	 */
	public WeixinProxy(String appid, String appsecret) {
		this(new FileTokenApi(appid, appsecret));
	}

	public WeixinProxy(TokenApi tokenApi) {
		this.mediaApi = new MediaApi(tokenApi);
		this.notifyApi = new NotifyApi(tokenApi);
		this.massApi = new MassApi(tokenApi);
		this.userApi = new UserApi(tokenApi);
		this.groupApi = new GroupApi(tokenApi);
		this.menuApi = new MenuApi(tokenApi);
		this.qrApi = new QrApi(tokenApi);
		this.tmplApi = new TmplApi(tokenApi);
		this.helperApi = new HelperApi(tokenApi);
	}

	/**
	 * 上传媒体文件
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param file
	 *            文件对象
	 * @param mediaType
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @throws IOException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.api.MediaApi
	 */
	public String uploadMedia(File file, MediaType mediaType)
			throws WeixinException, IOException {
		return mediaApi.uploadMedia(file, mediaType);
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param bytes
	 *            媒体数据包
	 * @param mediaType
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.api.MediaApi
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#uploadMedia(File, MediaType)}
	 */
	public String uploadMedia(String fileName, byte[] data, MediaType mediaType)
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
	 * @see com.foxinmy.weixin4j.api.MediaApi
	 */
	public File downloadMedia(String mediaId, MediaType mediaType)
			throws WeixinException, IOException {
		return mediaApi.downloadMedia(mediaId, mediaType);
	}

	/**
	 * 下载媒体文件
	 * 
	 * @param mediaId
	 * @param mediaType
	 * @return 二进制数据包
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.api.MediaApi
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#downloadMedia(String, MediaType)}
	 */
	public byte[] downloadMediaData(String mediaId, MediaType mediaType)
			throws WeixinException {
		return mediaApi.downloadMediaData(mediaId, mediaType);
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 * 
	 * @param notify
	 *            客服消息对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">发送客服消息</a>
	 * @see com.foxinmy.weixin4j.msg.notify.TextNotify
	 * @see com.foxinmy.weixin4j.msg.notify.ImageNotify
	 * @see com.foxinmy.weixin4j.msg.notify.MusicNotify
	 * @see com.foxinmy.weixin4j.msg.notify.VideoNotify
	 * @see com.foxinmy.weixin4j.msg.notify.VoiceNotify
	 * @see com.foxinmy.weixin4j.msg.notify.ArticleNotify
	 * @see com.foxinmy.weixin4j.api.NotifyApi
	 */
	public BaseResult sendNotify(BaseNotify notify) throws WeixinException {
		return notifyApi.sendNotify(notify);
	}

	/**
	 * 发送图文消息
	 * 
	 * @param touser
	 *            目标ID
	 * @param articles
	 *            图文列表
	 * @return 发送结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.msg.model.Article
	 * @see com.foxinmy.weixin4j.msg.notify.ArticleNotify
	 * @see com.foxinmy.weixin4j.api.NotifyApi
	 */
	public BaseResult sendNotify(String touser, List<Article> articles)
			throws WeixinException {
		return notifyApi.sendNotify(touser, articles);
	}

	/**
	 * 发送客服消息(不包含图文消息)
	 * 
	 * @param touser
	 *            目标用户
	 * @param baseMsg
	 *            消息类型
	 * @return 发送结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.msg.model.Text
	 * @see com.foxinmy.weixin4j.msg.model.Image
	 * @see com.foxinmy.weixin4j.msg.model.Music
	 * @see com.foxinmy.weixin4j.msg.model.Video
	 * @see com.foxinmy.weixin4j.msg.model.Voice
	 * @see com.foxinmy.weixin4j.api.NotifyApi
	 */
	public BaseResult sendNotify(String touser, BaseMsg baseMsg)
			throws WeixinException {
		return notifyApi.sendNotify(touser, baseMsg);
	}

	/**
	 * 客服聊天记录
	 * 
	 * @param openId
	 *            用户标识 可为空
	 * @param starttime
	 *            查询开始时间
	 * @param endtime
	 *            查询结束时间 每次查询不能跨日查询
	 * @param pagesize
	 *            每页大小 每页最多拉取1000条
	 * @param pageindex
	 *            查询第几页 从1开始
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.CustomRecord
	 * @see com.foxinmy.weixin4j.api.NotifyApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AE%A2%E6%9C%8D%E8%81%8A%E5%A4%A9%E8%AE%B0%E5%BD%95">查询客服聊天记录</a>
	 */
	public List<CustomRecord> getCustomRecord(String openId, long starttime,
			long endtime, int pagesize, int pageindex) throws WeixinException {
		return notifyApi.getCustomRecord(openId, starttime, endtime, pagesize,
				pageindex);
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
	 * @see com.foxinmy.weixin4j.model.MpArticle
	 * @see com.foxinmy.weixin4j.api.MassApi
	 */
	public String uploadArticle(List<MpArticle> articles)
			throws WeixinException {
		return massApi.uploadArticle(articles);
	}

	/**
	 * 上传分组群发的视频素材
	 * 
	 * @param mediaId
	 *            媒体文件中上传得到的Id
	 * @param title
	 *            标题 可为空
	 * @param desc
	 *            描述 可为空
	 * @return 上传后的ID
	 * @throws WeixinException
	 * 
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3">高级群发</a>
	 * @see com.foxinmy.weixin4j.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 */
	public String uploadVideo(String mediaId, String title, String desc)
			throws WeixinException {
		return massApi.uploadVideo(mediaId, title, desc);
	}

	/**
	 * 分组群发
	 * 
	 * @param mediaId
	 *            媒体ID 如果为text 则表示content
	 * @param mediaType
	 *            媒体类型
	 * @param groupId
	 *            分组ID
	 * @return
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.api.GroupApi#getGroupByOpenId(String)}
	 * @see {@link com.foxinmy.weixin4j.api.GroupApi#getGroups()}
	 */
	public String massByGroup(String mediaId, MediaType mediaType,
			String groupId) throws WeixinException {
		return massApi.massByGroup(mediaId, mediaType, groupId);
	}

	/**
	 * 分组群发图文消息
	 * 
	 * @param articles
	 *            图文消息列表
	 * @param groupId
	 *            分组ID
	 * @return 发送出去的消息ID
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.MpArticle
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#uploadNews(List)}
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#massByGroup(String,MediaType,String)}
	 */
	public String massArticleByGroup(List<MpArticle> articles, String groupId)
			throws WeixinException {
		return massApi.massArticleByGroup(articles, groupId);
	}

	/**
	 * openId群发
	 * 
	 * @param mediaId
	 *            媒体ID 如果为text 则表示content
	 * @param mediaType
	 *            媒体类型
	 * @param openIds
	 *            openId列表
	 * @return
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.User
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#massByOpenIds(JSONObject,String...)}
	 */
	public String massByOpenIds(String mediaId, MediaType mediaType,
			String... openIds) throws WeixinException {
		return massApi.massByOpenIds(mediaId, mediaType, openIds);
	}

	/**
	 * openId图文群发
	 * 
	 * @param articles
	 *            图文消息列表
	 * @param openIds
	 *            目标ID列表
	 * @return 发送出去的消息ID
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.MpArticle
	 * @see com.foxinmy.weixin4j.model.User
	 * @see com.foxinmy.weixin4j.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadNews(List)}
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#massByOpenIds(String,MediaType,String...)}
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
	 * @see com.foxinmy.weixin4j.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#massByGroup(JSONObject, String)}
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#massByOpenIds(JSONObject, String...)
	 */
	public BaseResult deleteMassNews(String msgid) throws WeixinException {
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
	 * @see com.foxinmy.weixin4j.model.UserToken
	 * @see com.foxinmy.weixin4j.api.UserApi
	 */
	public UserToken getAccessToken(String code) throws WeixinException {
		return userApi.getAccessToken(code);
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
	 * @see com.foxinmy.weixin4j.model.User
	 * @see com.foxinmy.weixin4j.model.UserToken
	 * @see com.foxinmy.weixin4j.api.UserApi
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#getAccessToken(String)}
	 */
	public User getUser(UserToken token) throws WeixinException {
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
	 * @see com.foxinmy.weixin4j.model.User
	 * @see com.foxinmy.weixin4j.api.UserApi
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
	 * @see com.foxinmy.weixin4j.model.Following
	 * @see com.foxinmy.weixin4j.api.UserApi
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
	 * @see com.foxinmy.weixin4j.model.Following
	 * @see com.foxinmy.weixin4j.api.UserApi
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#getFollowing(String)}
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
	 * @see com.foxinmy.weixin4j.api.UserApi
	 */
	public BaseResult remarkUserName(String openId, String remark)
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
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.model.Group#toCreateJson()
	 * @see com.foxinmy.weixin4j.api.GroupApi
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
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.api.GroupApi
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
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.api.GroupApi
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
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.model.Group#toModifyJson()
	 * @see com.foxinmy.weixin4j.api.GroupApi
	 */
	public BaseResult modifyGroup(int groupId, String name)
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
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.api.GroupApi
	 */
	public BaseResult moveGroup(String openId, int groupId)
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
	 * @see com.foxinmy.weixin4j.api.MenuApi
	 */
	public BaseResult createMenu(List<Button> btnList) throws WeixinException {
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
	 * @see com.foxinmy.weixin4j.api.MenuApi
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
	 * @see com.foxinmy.weixin4j.api.MenuApi
	 */
	public BaseResult deleteMenu() throws WeixinException {
		return menuApi.deleteMenu();
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param parameter
	 * @return byte数据包
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.api.QrApi
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy.QrApi#getQR(QRParameter)}
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
	 * @see com.foxinmy.weixin4j.api.QrApi
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy.QrApi#getQR(QRParameter)}
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
	 * @see com.foxinmy.weixin4j.model.QRParameter
	 * @see com.foxinmy.weixin4j.api.QrApi
	 */
	public File getQR(QRParameter parameter) throws WeixinException,
			IOException {
		return qrApi.getQR(parameter);
	}

	/**
	 * 发送模板消息
	 * 
	 * @param message
	 * @return 发送结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%A8%A1%E6%9D%BF%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3">模板消息</a>
	 * @see com.foxinmy.weixin4j.msg.out.TemplateMessage
	 * @seee com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 * @see com.foxinmy.weixin4j.api.TmplApi
	 */
	public BaseResult sendTmplMessage(TemplateMessage tplMessage)
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
	 * @see com.foxinmy.weixin4j.api.HelperApi
	 */
	public String getShorturl(String url) throws WeixinException {
		return helperApi.getShorturl(url);
	}
}
