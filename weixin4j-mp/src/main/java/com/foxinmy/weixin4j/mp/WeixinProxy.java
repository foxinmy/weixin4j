package com.foxinmy.weixin4j.mp;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.model.card.CardCoupon;
import com.foxinmy.weixin4j.model.card.CardCoupons;
import com.foxinmy.weixin4j.model.card.CardQR;
import com.foxinmy.weixin4j.model.media.MediaCounter;
import com.foxinmy.weixin4j.model.media.MediaDownloadResult;
import com.foxinmy.weixin4j.model.media.MediaItem;
import com.foxinmy.weixin4j.model.media.MediaRecord;
import com.foxinmy.weixin4j.model.media.MediaUploadResult;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.model.paging.Pagedata;
import com.foxinmy.weixin4j.model.qr.QRParameter;
import com.foxinmy.weixin4j.model.qr.QRResult;
import com.foxinmy.weixin4j.mp.api.CardApi;
import com.foxinmy.weixin4j.mp.api.CommentApi;
import com.foxinmy.weixin4j.mp.api.CustomApi;
import com.foxinmy.weixin4j.mp.api.DataApi;
import com.foxinmy.weixin4j.mp.api.GroupApi;
import com.foxinmy.weixin4j.mp.api.HelperApi;
import com.foxinmy.weixin4j.mp.api.MassApi;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.mp.api.NotifyApi;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.mp.api.QrApi;
import com.foxinmy.weixin4j.mp.api.TagApi;
import com.foxinmy.weixin4j.mp.api.TmplApi;
import com.foxinmy.weixin4j.mp.api.UserApi;
import com.foxinmy.weixin4j.mp.component.WeixinTokenComponentCreator;
import com.foxinmy.weixin4j.mp.message.NotifyMessage;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.model.ArticleComment;
import com.foxinmy.weixin4j.mp.model.ArticleComment.ArticleCommentType;
import com.foxinmy.weixin4j.mp.model.AutoReplySetting;
import com.foxinmy.weixin4j.mp.model.Following;
import com.foxinmy.weixin4j.mp.model.Group;
import com.foxinmy.weixin4j.mp.model.KfAccount;
import com.foxinmy.weixin4j.mp.model.KfChatRecord;
import com.foxinmy.weixin4j.mp.model.KfOnlineAccount;
import com.foxinmy.weixin4j.mp.model.KfSession;
import com.foxinmy.weixin4j.mp.model.KfSession.KfSessionCounter;
import com.foxinmy.weixin4j.mp.model.Menu;
import com.foxinmy.weixin4j.mp.model.MenuMatchRule;
import com.foxinmy.weixin4j.mp.model.MenuSetting;
import com.foxinmy.weixin4j.mp.model.SemQuery;
import com.foxinmy.weixin4j.mp.model.SemResult;
import com.foxinmy.weixin4j.mp.model.Tag;
import com.foxinmy.weixin4j.mp.model.TemplateMessageInfo;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.mp.token.WeixinTicketCreator;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.mp.type.DatacubeType;
import com.foxinmy.weixin4j.mp.type.IndustryType;
import com.foxinmy.weixin4j.mp.type.Lang;
import com.foxinmy.weixin4j.token.PerTicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.tuple.MassTuple;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.tuple.MpVideo;
import com.foxinmy.weixin4j.tuple.Tuple;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.type.TicketType;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信公众平台接口实现
 *
 * @className WeixinProxy
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年3月23日
 * @since JDK 1.6
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php">api文档</a>
 */
public class WeixinProxy {
	/**
	 * 授权API
	 */
	private final OauthApi oauthApi;
	/**
	 * 媒体素材API
	 */
	private final MediaApi mediaApi;
	/**
	 * 客服消息API
	 */
	private final NotifyApi notifyApi;
	/**
	 * 多客服API
	 */
	private final CustomApi customApi;
	/**
	 * 群发消息API
	 */
	private final MassApi massApi;
	/**
	 * 用户API
	 */
	private final UserApi userApi;
	/**
	 * 分组API
	 */
	private final GroupApi groupApi;
	/**
	 * 菜单API
	 */
	private final MenuApi menuApi;
	/**
	 * 二维码API
	 */
	private final QrApi qrApi;
	/**
	 * 模板消息API
	 */
	private final TmplApi tmplApi;
	/**
	 * 辅助API
	 */
	private final HelperApi helperApi;
	/**
	 * 数据统计API
	 */
	private final DataApi dataApi;
	/**
	 * 标签API
	 */
	private final TagApi tagApi;
	/**
	 * 卡券API
	 */
	private final CardApi cardApi;
	/**
	 * 文章评论API
	 */
	private final CommentApi commentApi;
	/**
	 * token管理
	 */
	private final TokenManager tokenManager;
	/**
	 * 账号信息
	 */
	private final WeixinAccount weixinAccount;
	/**
	 * token存储
	 */
	private final CacheStorager<Token> cacheStorager;

	/**
	 * 微信接口实现(使用weixin4j.properties配置的account账号信息,
	 * 使用FileCacheStorager文件方式缓存TOKEN)
	 */
	public WeixinProxy() {
		this(new FileCacheStorager<Token>());
	}

	/**
	 * 微信接口实现(使用weixin4j.properties配置的account账号信息)
	 *
	 * @param cacheStorager
	 *            token管理
	 */
	public WeixinProxy(CacheStorager<Token> cacheStorager) {
		this(Weixin4jConfigUtil.getWeixinAccount(), cacheStorager);
	}

	/**
	 * 微信接口实现
	 *
	 * @param weixinAccount
	 *            账号信息
	 * @param cacheStorager
	 *            token管理
	 */
	public WeixinProxy(WeixinAccount weixinAccount, CacheStorager<Token> cacheStorager) {
		this(weixinAccount, new WeixinTokenCreator(weixinAccount.getId(), weixinAccount.getSecret()), cacheStorager);
	}

	/**
	 * 第三方组件方式创建微信接口实现(永久刷新令牌机制)
	 *
	 * @param perTicketManager
	 *            第三方组件永久刷新token
	 * @param componentTokenManager
	 *            第三方组件凭证token
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi#getPerCodeManager(String)
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi#getTokenManager
	 */
	public WeixinProxy(PerTicketManager perTicketManager, TokenManager componentTokenManager) {
		this(new WeixinAccount(perTicketManager.getThirdId(), perTicketManager.getThirdSecret()),
				new WeixinTokenComponentCreator(perTicketManager, componentTokenManager),
				perTicketManager.getCacheStorager());
	}

	/**
	 * 微信接口实现
	 *
	 * @param weixinAccount
	 *            微信账号
	 * @param tokenCreator
	 *            token的创建
	 * @param cacheStorager
	 *            token的存储
	 */
	private WeixinProxy(WeixinAccount weixinAccount, TokenCreator tokenCreator, CacheStorager<Token> cacheStorager) {
		if (weixinAccount == null) {
			throw new IllegalArgumentException("weixinAccount must not be empty");
		}
		if (tokenCreator == null) {
			throw new IllegalArgumentException("tokenCreator must not be empty");
		}
		if (cacheStorager == null) {
			throw new IllegalArgumentException("cacheStorager must not be empty");
		}
		this.tokenManager = new TokenManager(tokenCreator, cacheStorager);
		this.weixinAccount = weixinAccount;
		this.cacheStorager = cacheStorager;
		this.oauthApi = new OauthApi(weixinAccount);
		this.mediaApi = new MediaApi(tokenManager);
		this.notifyApi = new NotifyApi(tokenManager);
		this.customApi = new CustomApi(tokenManager);
		this.massApi = new MassApi(tokenManager);
		this.userApi = new UserApi(tokenManager);
		this.groupApi = new GroupApi(tokenManager);
		this.menuApi = new MenuApi(tokenManager);
		this.qrApi = new QrApi(tokenManager);
		this.tmplApi = new TmplApi(tokenManager);
		this.helperApi = new HelperApi(tokenManager);
		this.dataApi = new DataApi(tokenManager);
		this.tagApi = new TagApi(tokenManager);
		this.cardApi = new CardApi(tokenManager);
		this.commentApi = new CommentApi(tokenManager);
	}

	/**
	 * 获取微信账号信息
	 *
	 * @return
	 */
	public WeixinAccount getWeixinAccount() {
		return weixinAccount;
	}

	/**
	 * token管理
	 *
	 * @return
	 */
	public TokenManager getTokenManager() {
		return this.tokenManager;
	}

	/**
	 * 获取oauth授权API
	 *
	 * @see com.foxinmy.weixin4j.mp.api.OauthApi
	 * @return
	 */
	public OauthApi getOauthApi() {
		return oauthApi;
	}

	/**
	 * 获取JSSDK Ticket的tokenManager
	 *
	 * @param ticketType
	 *            票据类型
	 * @return
	 */
	public TokenManager getTicketManager(TicketType ticketType) {
		return new TokenManager(new WeixinTicketCreator(ticketType, this.tokenManager), this.cacheStorager);
	}

	/**
	 * 上传图文消息内的图片获取URL
	 * 请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。
	 *
	 * @param is
	 *            图片数据流
	 * @param fileName
	 *            文件名 为空时将自动生成
	 * @return 图片URL 可用于后续群发中，放置到图文消息中
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @throws WeixinException
	 */
	public String uploadImage(InputStream is, String fileName) throws WeixinException {
		return mediaApi.uploadImage(is, fileName);
	}

	/**
	 * 上传群发中的视频素材
	 *
	 * @param is
	 *            图片数据流
	 * @param fileName
	 *            文件名 为空时将自动生成
	 * @param title
	 *            视频标题 非空
	 * @param description
	 *            视频描述 可为空
	 * @return 群发视频消息对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      高级群发</a>
	 * @see com.foxinmy.weixin4j.tuple.MpVideo
	 */
	public MpVideo uploadVideo(InputStream is, String fileName, String title, String description)
			throws WeixinException {
		return mediaApi.uploadVideo(is, fileName, title, description);
	}

	/**
	 * 上传媒体文件 </br>
	 * <font color="red">此接口只包含图片、语音、缩略图、视频(临时)四种媒体类型的上传</font>
	 * </p>
	 *
	 * @param isMaterial
	 *            是否永久上传
	 * @param is
	 *            媒体数据流
	 * @param fileName
	 *            文件名 为空时将自动生成
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726&token=&lang=zh_CN">
	 *      上传临时素材</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738729&token=&lang=zh_CN">
	 *      上传永久素材</a>
	 * @see com.foxinmy.weixin4j.model.media.MediaUploadResult
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @throws WeixinException
	 */
	public MediaUploadResult uploadMedia(boolean isMaterial, InputStream is, String fileName) throws WeixinException {
		return mediaApi.uploadMedia(isMaterial, is, fileName);
	}

	/**
	 * 下载媒体文件
	 *
	 * @param mediaId
	 *            媒体ID
	 * @param isMaterial
	 *            是否永久素材
	 * @return 媒体文件下载结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see com.foxinmy.weixin4j.model.media.MediaDownloadResult
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738727&token=&lang=zh_CN">
	 *      下载临时媒体素材</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738730&token=&lang=zh_CN">
	 *      下载永久媒体素材</a>
	 */
	public MediaDownloadResult downloadMedia(String mediaId, boolean isMaterial) throws WeixinException {
		return mediaApi.downloadMedia(mediaId, isMaterial);
	}

	/**
	 * 上传永久图文素材
	 * <p>
	 * 、新增的永久素材也可以在公众平台官网素材管理模块中看到,永久素材的数量是有上限的，请谨慎新增。图文消息素材和图片素材的上限为5000，
	 * 其他类型为1000
	 * </P>
	 *
	 * @param articles
	 *            图文列表
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738729&token=&lang=zh_CN">
	 *      上传永久媒体素材</a>
	 */
	public String uploadMaterialArticle(List<MpArticle> articles) throws WeixinException {
		return mediaApi.uploadMaterialArticle(articles);
	}

	/**
	 * 下载永久图文素材
	 *
	 * @param mediaId
	 *            媒体ID
	 * @return 图文列表
	 * @throws WeixinException
	 * @see {@link #downloadMedia(String, boolean)}
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 */
	public List<MpArticle> downloadArticle(String mediaId) throws WeixinException {
		return mediaApi.downloadArticle(mediaId);
	}

	/**
	 * 更新永久图文素材
	 *
	 * @param mediaId
	 *            要修改的图文消息的id
	 * @param index
	 *            要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
	 * @param article
	 *            图文对象
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738732&token=&lang=zh_CN">
	 *      更新永久图文素材</a>
	 */
	public ApiResult updateMaterialArticle(String mediaId, int index, MpArticle article) throws WeixinException {
		return mediaApi.updateMaterialArticle(mediaId, index, article);
	}

	/**
	 * 删除永久媒体素材
	 *
	 * @param mediaId
	 *            媒体素材的media_id
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738731&token=&lang=zh_CN">
	 *      删除永久媒体素材</a>
	 */
	public ApiResult deleteMaterialMedia(String mediaId) throws WeixinException {
		return mediaApi.deleteMaterialMedia(mediaId);
	}

	/**
	 * 上传永久视频素材
	 *
	 * @param is
	 *            大小不超过1M且格式为MP4的视频文件
	 * @param fileName
	 *            文件名 为空时将自动生成
	 * @param title
	 *            视频标题
	 * @param introduction
	 *            视频描述
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738729&token=&lang=zh_CN">
	 *      上传永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @throws WeixinException
	 */
	public String uploadMaterialVideo(InputStream is, String fileName, String title, String introduction)
			throws WeixinException {
		return mediaApi.uploadMaterialVideo(is, fileName, title, introduction);
	}

	/**
	 * 获取永久媒体素材的总数</br>
	 * .图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
	 *
	 * @return 总数对象
	 * @throws WeixinException
	 * @see com.com.foxinmy.weixin4j.model.media.MediaCounter
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738733&token=&lang=zh_CN">
	 *      获取素材总数</a>
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 */
	public MediaCounter countMaterialMedia() throws WeixinException {
		return mediaApi.countMaterialMedia();
	}

	/**
	 * 获取媒体素材记录列表
	 *
	 * @param mediaType
	 *            素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param pageable
	 *            分页数据
	 * @return 媒体素材的记录对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see com.foxinmy.weixin4j.model.media.MediaRecord
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.model.media.MediaItem
	 * @see com.foxinmy.weixin4j.model.paging.Pageable
	 * @see com.foxinmy.weixin4j.model.paging.Pagedata
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738734&token=&lang=zh_CN">
	 *      获取素材列表</a>
	 */
	public MediaRecord listMaterialMedia(MediaType mediaType, Pageable pageable) throws WeixinException {
		return mediaApi.listMaterialMedia(mediaType, pageable);
	}

	/**
	 * 获取全部的媒体素材
	 *
	 * @param mediaType
	 *            媒体类型
	 * @return 素材列表
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see {@link #listMaterialMedia(MediaType, Pageable)}
	 * @throws WeixinException
	 */
	public List<MediaItem> listAllMaterialMedia(MediaType mediaType) throws WeixinException {
		return mediaApi.listAllMaterialMedia(mediaType);
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 *
	 * @param notify
	 *            客服消息对象
	 * @return 处理结果
	 * @see {@link #sendNotify(NotifyMessage,String) }
	 * @throws WeixinException
	 */
	public ApiResult sendNotify(NotifyMessage notify) throws WeixinException {
		return notifyApi.sendNotify(notify);
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 *
	 * @param notify
	 *            客服消息对象
	 * @param kfAccount
	 *            客服账号 可为空
	 * @throws WeixinException
	 * @return 处理结果
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547&token=&lang=zh_CN">
	 *      发送客服消息</a>
	 * @see com.foxinmy.weixin4j.tuple.Text
	 * @see com.foxinmy.weixin4j.tuple.Image
	 * @see com.foxinmy.weixin4j.tuple.Voice
	 * @see com.foxinmy.weixin4j.tuple.Video
	 * @see com.foxinmy.weixin4j.tuple.Music
	 * @see com.foxinmy.weixin4j.tuple.News
	 * @see com.foxinmy.weixin4j.mp.api.NotifyApi
	 */
	public ApiResult sendNotify(NotifyMessage notify, String kfAccount) throws WeixinException {
		return notifyApi.sendNotify(notify, kfAccount);
	}

	/**
	 * 客服聊天记录
	 *
	 * @param startTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间 每次查询不能跨日查询
	 * @param number
	 *            最多10000条
	 * @see com.foxinmy.weixin4j.mp.model.CustomRecord
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href="http://dkf.qq.com/document-1_1.html">查询客服聊天记录</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044854&token=&lang=zh_CN">
	 *      查询客服聊天记录</a>
	 * @throws WeixinException
	 */
	public List<KfChatRecord> getKfChatRecord(Date startTime, Date endTime, int number) throws WeixinException {
		return customApi.getKfChatRecord(startTime, endTime, number);
	}

	/**
	 * 获取公众号中所设置的客服基本信息，包括客服工号、客服昵称、客服登录账号
	 *
	 * @return 多客服信息列表
	 * @see com.foxinmy.weixin4j.mp.model.KfAccount
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      获取客服基本信息</a>
	 * @throws WeixinException
	 */
	public List<KfAccount> listKfAccount() throws WeixinException {
		return customApi.listKfAccount();
	}

	/**
	 * 获取在线客服在线状态（手机在线、PC客户端在线、手机和PC客户端全都在线）、客服自动接入最大值、 客服当前接待客户数
	 *
	 * @return 多客服在线信息列表
	 * @see com.foxinmy.weixin4j.mp.model.KfOnlineAccount
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      获取客服在线信息</a>
	 * @throws WeixinException
	 */
	public List<KfOnlineAccount> listOnlineKfAccount() throws WeixinException {
		return customApi.listOnlineKfAccount();
	}

	/**
	 * 新增客服账号
	 *
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，
	 *            请前往微信公众平台设置。
	 * @param name
	 *            客服昵称，最长6个汉字或12个英文字符
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi 客服管理接口返回码</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      新增客服账号</a>
	 */
	public ApiResult createKfAccount(String id, String name) throws WeixinException {
		return customApi.createKfAccount(id, name);
	}

	/**
	 * 更新客服账号
	 *
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，
	 *            请前往微信公众平台设置。
	 * @param name
	 *            客服昵称，最长6个汉字或12个英文字符
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      更新客服账号</a>
	 */
	public ApiResult updateKfAccount(String id, String name) throws WeixinException {
		return customApi.updateKfAccount(id, name);
	}

	/**
	 * 邀请绑定客服帐号
	 * 新添加的客服帐号是不能直接使用的，只有客服人员用微信号绑定了客服账号后，方可登录Web客服进行操作。此接口发起一个绑定邀请到客服人员微信号
	 * ，客服人员需要在微信客户端上用该微信号确认后帐号才可用。尚未绑定微信号的帐号可以进行绑定邀请操作，邀请未失效时不能对该帐号进行再次绑定微信号邀请。
	 *
	 * @param kfAccount
	 *            完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param inviteAccount
	 *            接收绑定邀请的客服微信号
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
	 *      >邀请绑定客服帐号<a/>
	 * @throws WeixinException
	 */
	public ApiResult inviteKfAccount(String kfAccount, String inviteAccount) throws WeixinException {
		return customApi.inviteKfAccount(kfAccount, inviteAccount);
	}

	/**
	 * 上传客服头像
	 *
	 * @param accountId
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @param is
	 *            头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
	 * @param fileName
	 *            文件名 为空时将自动生成
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      上传客服头像</a>
	 */
	public ApiResult uploadKfAvatar(String accountId, InputStream is, String fileName) throws WeixinException {
		return customApi.uploadKfAvatar(accountId, is, fileName);
	}

	/**
	 * 删除客服账号
	 *
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      删除客服账号</a>
	 */
	public ApiResult deleteKfAccount(String id) throws WeixinException {
		return customApi.deleteKfAccount(id);
	}

	/**
	 * 创建客服会话
	 * <p>
	 * 开发者可以使用本接口，为多客服的客服工号创建会话，将某个客户直接指定给客服工号接待，需要注意此接口不会受客服自动接入数以及自动接入开关限制。
	 * 只能为在线的客服（PC客户端在线，或者已绑定多客服助手）创建会话。
	 * </p>
	 *
	 * @param userOpenId
	 *            用户的userOpenId
	 * @param kfAccount
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @param text
	 *            附加信息，文本会展示在客服人员的多客服客户端
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">
	 *      创建会话</a>
	 */
	public ApiResult createKfSession(String userOpenId, String kfAccount, String text) throws WeixinException {
		return customApi.createKfSession(userOpenId, kfAccount, text);
	}

	/**
	 * 关闭客服会话
	 *
	 * @param userOpenId
	 *            用户的userOpenId
	 * @param kfAccount
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @param text
	 *            附加信息，文本会展示在客服人员的多客服客户端
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      关闭会话</a>
	 */
	public ApiResult closeKfSession(String userOpenId, String kfAccount, String text) throws WeixinException {
		return customApi.closeKfSession(userOpenId, kfAccount, text);
	}

	/**
	 * 获取客户的会话状态:获取客户当前的会话状态。
	 *
	 * @param userOpenId
	 *            用户的openid
	 * @return 会话对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see com.foxinmy.weixin4j.mp.model.KfSession
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      获取会话状态</a>
	 */
	public KfSession getKfSession(String userOpenId) throws WeixinException {
		return customApi.getKfSession(userOpenId);
	}

	/**
	 * 获取客服的会话列表:获取某个客服正在接待的会话列表。
	 *
	 * @param kfAccount
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。
	 * @return 会话列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see com.foxinmy.weixin4j.mp.model.KfSession
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      获取客服的会话列表</a>
	 */
	public List<KfSession> listKfSession(String kfAccount) throws WeixinException {
		return customApi.listKfSession(kfAccount);
	}

	/**
	 * 获取未接入会话列表:获取当前正在等待队列中的会话列表，此接口最多返回最早进入队列的100个未接入会话
	 *
	 * @return 会话列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see com.foxinmy.weixin4j.mp.model.KfSession
	 * @see com.foxinmy.weixin4j.mp.model.KfSession.KfSessionCounter
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">
	 *      获取客服的会话列表</a>
	 */
	public KfSessionCounter listKfWaitSession() throws WeixinException {
		return customApi.listKfWaitSession();
	}

	/**
	 * 上传群发的图文消息,一个图文消息支持1到10条图文
	 *
	 * @param articles
	 *            图片消息
	 * @return 媒体ID
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      上传图文素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 */
	public String uploadMassArticle(List<MpArticle> articles) throws WeixinException {
		return massApi.uploadArticle(articles);
	}

	/**
	 * 群发消息
	 * <p>
	 * 在返回成功时,意味着群发任务提交成功,并不意味着此时群发已经结束,所以,仍有可能在后续的发送过程中出现异常情况导致用户未收到消息,
	 * 如消息有时会进行审核、服务器不稳定等,此外,群发任务一般需要较长的时间才能全部发送完毕
	 * </p>
	 *
	 * @param MassTuple
	 *            消息元件
	 * @param isToAll
	 *            用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，
	 *            选择false可根据group_id发送给指定群组的用户
	 * @param groupId
	 *            分组ID
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.tuple.Text
	 * @see com.foxinmy.weixin4j.tuple.Image
	 * @see com.foxinmy.weixin4j.tuple.Voice
	 * @see com.foxinmy.weixin4j.tuple.MpVideo
	 * @see com.foxinmy.weixin4j.tuple.MpNews
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see com.foxinmy.weixin4j.tuple.MassTuple
	 * @see {@link #getGroups()}
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      根据分组群发</a>
	 */
	public String[] massByGroupId(MassTuple tuple, boolean isToAll, int groupId) throws WeixinException {
		return massApi.massByGroupId(tuple, isToAll, groupId);
	}

	/**
	 * 分组ID群发图文消息
	 *
	 * @param articles
	 *            图文列表
	 * @param groupId
	 *            分组ID
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
	 * @see {@link #massByGroupId(Tuple,int)}
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      根据分组群发</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @throws WeixinException
	 */
	public String[] massArticleByGroupId(List<MpArticle> articles, int groupId) throws WeixinException {
		return massApi.massArticleByGroupId(articles, groupId);
	}

	/**
	 * 群发消息给所有粉丝
	 *
	 * @param tuple
	 *            消息元件
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      根据标签群发</a>
	 */
	public String[] massToAll(MassTuple tuple) throws WeixinException {
		return massApi.massToAll(tuple);
	}

	/**
	 * 标签群发消息
	 *
	 * @param tuple
	 *            消息元件
	 * @param tagId
	 *            标签ID
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
	 * @throws WeixinException
	 * @see Tag
	 * @see {@link TagApi#listTags()}
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      根据标签群发</a>
	 */
	public String[] massByTagId(MassTuple tuple, int tagId) throws WeixinException {
		return massApi.massByTagId(tuple, tagId);
	}

	/**
	 * 标签群发图文消息
	 *
	 * @param articles
	 *            图文列表
	 * @param tagId
	 *            标签ID
	 * @param ignoreReprint
	 *            图文消息被判定为转载时，是否继续群发
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现。
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      根据标签群发</a>
	 * @see {@link #massByTagId(Tuple,int)}
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @throws WeixinException
	 */
	public String[] massArticleByTagId(List<MpArticle> articles, int tagId, boolean ignoreReprint)
			throws WeixinException {
		return massApi.massArticleByTagId(articles, tagId, ignoreReprint);
	}

	/**
	 * openId群发消息
	 *
	 * @param tuple
	 *            消息元件
	 * @param openIds
	 *            openId列表
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      根据openid群发</a>
	 * @see {@link UserApi#getUser(String)}
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 */
	public String[] massByOpenIds(MassTuple tuple, String... openIds) throws WeixinException {
		return massApi.massByOpenIds(tuple, openIds);
	}

	/**
	 * openid群发图文消息
	 *
	 * @param articles
	 *            图文列表
	 * @param ignoreReprint
	 *            图文消息被判定为转载时，是否继续群发
	 * @param openIds
	 *            openId列表
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中.
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      根据openid群发</a>
	 * @see {@link #massByOpenIds(Tuple,String...)}
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @throws WeixinException
	 */
	public String[] massArticleByOpenIds(List<MpArticle> articles, boolean ignoreReprint, String... openIds)
			throws WeixinException {
		return massApi.massArticleByOpenIds(articles, ignoreReprint, openIds);
	}

	/**
	 * 删除群发消息
	 *
	 * @param msgid
	 *            发送出去的消息ID
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      删除群发</a>
	 * @see #deleteMassNews(String, int)
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 */
	public ApiResult deleteMassNews(String msgid) throws WeixinException {
		return massApi.deleteMassNews(msgid);
	}

	/**
	 * 删除群发消息
	 * <p>
	 * 请注意,只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效,已经收到的用户,还是能在其本地看到消息卡片
	 * </p>
	 *
	 * @param msgid
	 *            发送出去的消息ID
	 * @param articleIndex
	 *            要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填或填0会删除全部文章
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      删除群发</a>
	 * @see {@link #massByTagId(Tuple, int)}
	 * @see {@link #massByOpenIds(Tuple, String...)
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 */
	public ApiResult deleteMassNews(String msgid, int articleIndex) throws WeixinException {
		return massApi.deleteMassNews(msgid, articleIndex);
	}

	/**
	 * 预览群发消息</br>
	 * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版
	 *
	 * @param toUser
	 *            接收用户的openID
	 * @param toWxName
	 *            接收用户的微信号 towxname和touser同时赋值时，以towxname优先
	 * @param tuple
	 *            消息元件
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see com.foxinmy.weixin4j.tuple.MassTuple
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      预览群发消息</a>
	 */
	public ApiResult previewMassNews(String toUser, String toWxName, MassTuple tuple) throws WeixinException {
		return massApi.previewMassNews(toUser, toWxName, tuple);
	}

	/**
	 * 查询群发发送状态
	 *
	 * @param msgId
	 *            消息ID
	 * @return 消息发送状态
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">
	 *      查询群发状态</a>
	 */
	public String getMassNewStatus(String msgId) throws WeixinException {
		return massApi.getMassNewStatus(msgId);
	}

	/**
	 * 获取用户信息
	 *
	 * @param openId
	 *            用户对应的ID
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @see {@link #getUser(String,Lang)}
	 */
	public User getUser(String openId) throws WeixinException {
		return userApi.getUser(openId);
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
	 * @param lang
	 *            国家地区语言版本
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.type.Lang
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public User getUser(String openId, Lang lang) throws WeixinException {
		return userApi.getUser(openId, lang);
	}

	/**
	 * 批量获取用户信息
	 *
	 * @param openIds
	 *            用户ID
	 * @return 用户列表
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @throws WeixinException
	 * @see {@link #getUsers(Lang,String[])}
	 */
	public List<User> getUsers(String... openIds) throws WeixinException {
		return userApi.getUsers(openIds);
	}

	/**
	 * 批量获取用户信息
	 *
	 * @param lang
	 *            国家地区语言版本
	 * @param openIds
	 *            用户ID
	 * @return 用户列表
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.type.Lang
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @throws WeixinException
	 */
	public List<User> getUsers(Lang lang, String... openIds) throws WeixinException {
		return userApi.getUsers(lang, openIds);
	}

	/**
	 * 获取公众号一定数量(10000)的关注者列表 <font corlor="red">请慎重使用</font>
	 *
	 * @param nextOpenId
	 *            下一次拉取数据的openid 不填写则默认从头开始拉取
	 * @return 关注者信息 <font color="red">包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      批量获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see com.foxinmy.weixin4j.mp.model.User
	 */
	public Following getFollowing(String nextOpenId) throws WeixinException {
		return userApi.getFollowing(nextOpenId);
	}

	/**
	 * 获取公众号一定数量(10000)的关注者列表
	 *
	 * @param nextOpenId
	 *            下一次拉取数据的openid 不填写则默认从头开始拉取
	 * @return 关注者信息 <font color="red">不包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 */
	public Following getFollowingOpenIds(String nextOpenId) throws WeixinException {
		return userApi.getFollowingOpenIds(nextOpenId);
	}

	/**
	 * 获取公众号全部的关注者列表 <font corlor="red">请慎重使用</font>
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 *
	 * @return 用户对象集合
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      批量获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see #getFollowing(String)
	 */
	public List<User> getAllFollowing() throws WeixinException {
		return userApi.getAllFollowing();
	}

	/**
	 * 获取公众号全部的关注者列表 <font corlor="red">请慎重使用</font>
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 *
	 * @return 用户openid集合
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @see #getFollowingOpenIds(String)
	 */
	public List<String> getAllFollowingOpenIds() throws WeixinException {
		return userApi.getAllFollowingOpenIds();
	}

	/**
	 * 设置用户备注名
	 *
	 * @param openId
	 *            用户ID
	 * @param remark
	 *            备注名
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140838&token=&lang=zh_CN">
	 *      设置用户备注名</a>
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public ApiResult remarkUserName(String openId, String remark) throws WeixinException {
		return userApi.remarkUserName(openId, remark);
	}

	/**
	 * 创建分组
	 *
	 * @param name
	 *            组名称
	 * @return group对象
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      创建分组</a>
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
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      查询所有分组</a>
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
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      查询用户所在分组</a>
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
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      修改分组名</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public ApiResult modifyGroup(int groupId, String name) throws WeixinException {
		return groupApi.modifyGroup(groupId, name);
	}

	/**
	 * 移动用户到分组
	 *
	 * @param groupId
	 *            组ID
	 * @param openId
	 *            用户对应的ID
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN4">
	 *      移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public ApiResult moveGroup(int groupId, String openId) throws WeixinException {
		return groupApi.moveGroup(groupId, openId);
	}

	/**
	 * 批量移动分组
	 *
	 * @param groupId
	 *            组ID
	 * @param openIds
	 *            用户ID列表(不能超过50个)
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      批量移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public ApiResult moveGroup(int groupId, String... openIds) throws WeixinException {
		return groupApi.moveGroup(groupId, openIds);
	}

	/**
	 * 删除用户分组,所有该分组内的用户自动进入默认分组.
	 *
	 * @param groupId
	 *            组ID
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      删除用户分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public ApiResult deleteGroup(int groupId) throws WeixinException {
		return groupApi.deleteGroup(groupId);
	}

	/**
	 * 自定义菜单
	 *
	 * @param buttons
	 *            菜单列表
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN">
	 *      创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.type.ButtonType
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 */
	public ApiResult createMenu(List<Button> buttons) throws WeixinException {
		return menuApi.createMenu(buttons);
	}

	/**
	 * 查询菜单
	 *
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN">
	 *      查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 */
	public List<Button> getMenu() throws WeixinException {
		return menuApi.getMenu();
	}

	/**
	 * 查询全部菜单(包含个性化菜单)
	 *
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN">
	 *      普通菜单</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
	 *      个性化菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.mp.model.Menu
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 */
	public List<Menu> getAllMenu() throws WeixinException {
		return menuApi.getAllMenu();
	}

	/**
	 * 删除菜单
	 *
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141015&token=&lang=zh_CN">
	 *      删除菜单</a>
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 * @return 处理结果
	 */
	public ApiResult deleteMenu() throws WeixinException {
		return menuApi.deleteMenu();
	}

	/**
	 * 创建个性化菜单
	 *
	 * @param buttons
	 *            菜单列表
	 * @param matchRule
	 *            匹配规则 至少要有一个匹配信息是不为空
	 * @return 菜单ID
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
	 *      创建个性化菜单</a>
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public String createCustomMenu(List<Button> buttons, MenuMatchRule matchRule) throws WeixinException {
		return menuApi.createCustomMenu(buttons, matchRule);
	}

	/**
	 * 删除个性化菜单
	 *
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
	 *      删除个性化菜单</a>
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 * @return 处理结果
	 */
	public ApiResult deleteCustomMenu(String menuId) throws WeixinException {
		return menuApi.deleteCustomMenu(menuId);
	}

	/**
	 * 测试个性化菜单匹配结果
	 *
	 * @param userId
	 *            可以是粉丝的OpenID，也可以是粉丝的微信号。
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
	 *      测试个性化菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.mp.api.MenuApi
	 * @throws WeixinException
	 * @return 匹配到的菜单配置
	 */
	public List<Button> matchCustomMenu(String userId) throws WeixinException {
		return menuApi.matchCustomMenu(userId);
	}

	/**
	 * 生成带参数的二维码
	 *
	 * @param parameter
	 *            二维码参数
	 * @return 二维码结果对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.qr.QRResult
	 * @see com.foxinmy.weixin4j.model.qr.QRParameter
	 * @see com.foxinmy.weixin4j.mp.api.QrApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN">
	 *      生成二维码</a>
	 */
	public QRResult createQR(QRParameter parameter) throws WeixinException {
		return qrApi.createQR(parameter);
	}

	/**
	 * 设置所属行业(每月可修改行业1次，账号仅可使用所属行业中相关的模板)
	 *
	 * @param industryTypes
	 *            所处行业 目前不超过两个
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.type.IndustryType
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">
	 *      设置所处行业</a>
	 */
	public ApiResult setTmplIndustry(IndustryType... industryTypes) throws WeixinException {
		return tmplApi.setTmplIndustry(industryTypes);
	}

	/**
	 * 获取模板ID
	 *
	 * @param shortId
	 *            模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return 模板ID
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">
	 *      获得模板ID</a>
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 */
	public String getTemplateId(String shortId) throws WeixinException {
		return tmplApi.getTemplateId(shortId);
	}

	/**
	 * 获取模板列表
	 *
	 * @return 模板列表
	 * @see com.foxinmy.weixin4j.mp.model.TemplateMessageInfo
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">
	 *      获取模板列表</a>
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 * @throws WeixinException
	 */
	public List<TemplateMessageInfo> getAllTemplates() throws WeixinException {
		return tmplApi.getAllTemplates();
	}

	/**
	 * 删除模板
	 *
	 * @param templateId
	 *            公众帐号下模板消息ID
	 * @return 处理结果
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">
	 *      删除模板</a>
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 * @throws WeixinException
	 */
	public ApiResult deleteTemplate(String templateId) throws WeixinException {
		return tmplApi.deleteTemplate(templateId);
	}

	/**
	 * 发送模板消息
	 *
	 * @param tplMessage
	 *            模板消息主体
	 * @return 发送的消息ID
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">
	 *      模板消息</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751288&token=&lang=zh_CN"
	 *      >运营规范</a>
	 * @see com.foxinmy.weixin4j.mp.message.TemplateMessage
	 * @seee com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 * @see com.foxinmy.weixin4j.mp.api.TmplApi
	 */
	public String sendTmplMessage(TemplateMessage tplMessage) throws WeixinException {
		return tmplApi.sendTmplMessage(tplMessage);
	}

	/**
	 * 长链接转短链接
	 *
	 * @param url
	 *            待转换的链接
	 * @return 短链接
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433600&token=&lang=zh_CN">
	 *      长链接转短链接</a>
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 */
	public String getShorturl(String url) throws WeixinException {
		return helperApi.getShorturl(url);
	}

	/**
	 * 语义理解
	 *
	 * @param semQuery
	 *            语义理解协议
	 * @return 语义理解结果
	 * @see com.foxinmy.weixin4j.mp.model.SemQuery
	 * @see com.foxinmy.weixin4j.mp.model.SemResult
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141241&token=&lang=zh_CN">
	 *      语义理解</a>
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
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140187&token=&lang=zh_CN">
	 *      获取IP地址</a>
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 * @throws WeixinException
	 */
	public List<String> getWechatServerIp() throws WeixinException {
		return helperApi.getWechatServerIp();
	}

	/**
	 * 接口调用次数调用清零：公众号调用接口并不是无限制的。为了防止公众号的程序错误而引发微信服务器负载异常，默认情况下，
	 * 每个公众号调用接口都不能超过一定限制 ，当超过一定限制时，调用对应接口会收到{"errcode":45009,"errmsg":"api freq
	 * out of limit" }错误返回码。
	 *
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433744592&token=&lang=zh_CN">
	 *      接口清零</a>
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 * @return 操作结果
	 * @throws WeixinException
	 */
	public ApiResult clearQuota() throws WeixinException {
		return helperApi.clearQuota(weixinAccount.getId());
	}

	/**
	 * 获取公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
	 * 而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
	 *
	 * @return 菜单集合
	 * @see {@link #getMenu()}
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1434698695&token=&lang=zh_CN">
	 *      获取自定义菜单配置</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @se com.foxinmy.weixin4j.mp.model.MenuSetting
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 * @throws WeixinException
	 */
	public MenuSetting getMenuSetting() throws WeixinException {
		return helperApi.getMenuSetting();
	}

	/**
	 * 获取公众号当前使用的自动回复规则，包括关注后自动回复、消息自动回复（60分钟内触发一次）、关键词自动回复。
	 *
	 * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751299&token=&lang=zh_CN">
	 *      获取自动回复规则</a>
	 * @throws WeixinException
	 */
	public AutoReplySetting getAutoReplySetting() throws WeixinException {
		return helperApi.getAutoReplySetting();
	}

	/**
	 * 数据统计
	 *
	 * @param datacubeType
	 *            数据统计类型
	 * @param beginDate
	 *            获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，
	 *            begin_date和end_date的差值只能为0，才能小于1），否则会报错
	 * @param endDate
	 *            获取数据的结束日期，end_date允许设置的最大值为昨日
	 * @see com.foxinmy.weixin4j.mp.api.DataApi
	 * @see com.foxinmy.weixin4j.mp.datacube.UserSummary
	 * @see com.foxinmy.weixin4j.mp.datacube.ArticleSummary
	 * @see com.foxinmy.weixin4j.mp.datacube.ArticleTotal
	 * @see com.foxinmy.weixin4j.mp.datacube.ArticleDatacubeShare
	 * @see com.foxinmy.weixin4j.mp.datacube.UpstreamMsg
	 * @see com.foxinmy.weixin4j.mp.datacube.UpstreamMsgDist
	 * @see com.foxinmy.weixin4j.mp.datacube.InterfaceSummary
	 * @return 统计结果
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">
	 *      用户分析</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">
	 *      图文分析</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">
	 *      消息分析</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141086&token=&lang=zh_CN">
	 *      接口分析</a>
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, Date beginDate, Date endDate) throws WeixinException {
		return dataApi.datacube(datacubeType, beginDate, endDate);
	}

	/**
	 * 数据统计
	 *
	 * @param datacubeType
	 *            统计类型
	 * @param beginDate
	 *            开始日期
	 * @param offset
	 *            增量 表示向前几天 比如 offset=1 则查询 beginDate的后一天之间的数据
	 * @see {@link #datacube(DatacubeType, Date,Date)}
	 * @see com.foxinmy.weixin4j.mp.api.DataApi
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, Date beginDate, int offset) throws WeixinException {
		return dataApi.datacube(datacubeType, beginDate, offset);
	}

	/**
	 * 数据统计
	 *
	 * @param datacubeType
	 *            统计类型
	 * @param offset
	 *            增量 表示向后几天 比如 offset=1 则查询 beginDate的前一天之间的数据
	 * @param endDate
	 *            截至日期
	 * @see {@link #datacube(DatacubeType, Date,Date)}
	 * @see com.foxinmy.weixin4j.mp.api.DataApi
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, int offset, Date endDate) throws WeixinException {
		return dataApi.datacube(datacubeType, offset, endDate);
	}

	/**
	 * 查询日期跨度为0的统计数据(当天)
	 *
	 * @param datacubeType
	 *            统计类型
	 * @param date
	 *            统计日期
	 * @see {@link #datacube(DatacubeType, Date,Date)}
	 * @see com.foxinmy.weixin4j.mp.api.DataApi
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, Date date) throws WeixinException {
		return dataApi.datacube(datacubeType, date);
	}

	/**
	 * 创建标签
	 *
	 * @param name
	 *            标签名（30个字符以内）
	 * @return 标签对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see com.foxinmy.weixin4j.mp.model.Tag
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      创建标签</a>
	 */
	public Tag createTag(String name) throws WeixinException {
		return tagApi.createTag(name);
	}

	/**
	 * 获取标签
	 *
	 * @return 标签列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see com.foxinmy.weixin4j.mp.model.Tag
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      获取标签</a>
	 */
	public List<Tag> listTags() throws WeixinException {
		return tagApi.listTags();
	}

	/**
	 * 更新标签
	 *
	 * @param tag
	 *            标签对象
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see com.foxinmy.weixin4j.mp.model.Tag
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      更新标签</a>
	 */
	public ApiResult updateTag(Tag tag) throws WeixinException {
		return tagApi.updateTag(tag);
	}

	/**
	 * 删除标签
	 *
	 * @param tagId
	 *            标签id
	 * @return 操作结果
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @throws WeixinException
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      删除标签</a>
	 */
	public ApiResult deleteTag(int tagId) throws WeixinException {
		return tagApi.deleteTag(tagId);
	}

	/**
	 * 批量为用户打标签:标签功能目前支持公众号为用户打上最多三个标签
	 *
	 * @param tagId
	 *            标签ID
	 * @param openIds
	 *            用户ID
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      批量为用户打标签</a>
	 */
	public ApiResult taggingUsers(int tagId, String... openIds) throws WeixinException {
		return tagApi.taggingUsers(tagId, openIds);
	}

	/**
	 * 批量为用户取消标签
	 *
	 * @param tagId
	 *            标签ID
	 * @param openIds
	 *            用户ID
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      批量为用户取消标签</a>
	 */
	public ApiResult untaggingUsers(int tagId, String... openIds) throws WeixinException {
		return tagApi.untaggingUsers(tagId, openIds);
	}

	/**
	 * 获取标签下粉丝列表
	 *
	 * @param tagId
	 *            标签ID
	 * @param nextOpenId
	 *            第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 用户openid列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      获取标签下粉丝列表</a>
	 */
	public Following getTagFollowingOpenIds(int tagId, String nextOpenId) throws WeixinException {
		return tagApi.getTagFollowingOpenIds(tagId, nextOpenId);
	}

	/**
	 * 获取标签下粉丝列表 <font corlor="red">请慎重使用</font>
	 *
	 * @param tagId
	 *            标签ID
	 * @param nextOpenId
	 *            第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 被打标签者信息 <font color="red">包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      获取标签下粉丝列表</a>
	 */
	public Following getTagFollowing(int tagId, String nextOpenId) throws WeixinException {
		return tagApi.getTagFollowing(tagId, nextOpenId);
	}

	/**
	 * 获取标签下全部的粉丝列表 <font corlor="red">请慎重使用</font>
	 *
	 * @param tagId
	 *            标签ID
	 * @return 用户openid列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see #getTagFollowingOpenIds(int,String)
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      获取标签下粉丝列表</a>
	 */
	public List<String> getAllTagFollowingOpenIds(int tagId) throws WeixinException {
		return tagApi.getAllTagFollowingOpenIds(tagId);
	}

	/**
	 * 获取标签下全部的粉丝列表 <font corlor="red">请慎重使用</font>
	 *
	 * @param tagId
	 *            标签ID
	 * @return 被打标签者信息 <font color="red">包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see #getTagFollowing(int,String)
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      获取标签下粉丝列表</a>
	 */
	public List<User> getAllTagFollowing(int tagId) throws WeixinException {
		return tagApi.getAllTagFollowing(tagId);
	}

	/**
	 * 获取用户身上的标签列表
	 *
	 * @param openId
	 *            用户ID
	 * @return 标签ID集合
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      获取用户身上的标签列表</a>
	 */
	public Integer[] getUserTags(String openId) throws WeixinException {
		return tagApi.getUserTags(openId);
	}

	/**
	 * 获取公众号的黑名单列表
	 *
	 * @param nextOpenId
	 *            下一次拉取数据的openid 不填写则默认从头开始拉取
	 * @return 拉黑用户列表 <font color="red">不包含用户的详细信息</font>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN"
	 *      >获取黑名单列表</a>
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @throws WeixinException
	 */
	public Following getBalcklistOpenIds(String nextOpenId) throws WeixinException {
		return tagApi.getBalcklistOpenIds(nextOpenId);
	}

	/**
	 * 获取公众号全部的黑名单列表 <font corlor="red">请慎重使用</font>
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 *
	 * @return 用户openid集合
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN">
	 *      获取黑名单列表</a>
	 * @see #getFollowingOpenIds(String)
	 */
	public List<String> getAllBalcklistOpenIds() throws WeixinException {
		return tagApi.getAllBalcklistOpenIds();
	}

	/**
	 * 黑名单操作
	 *
	 * @param blacklist
	 *            true=拉黑用户,false=取消拉黑用户
	 * @param openIds
	 *            用户ID列表
	 * @return 操作结果
	 * @see com.foxinmy.weixin4j.mp.api.TagApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN">
	 *      黑名单操作</a>
	 * @throws WeixinException
	 */
	public ApiResult batchBlacklist(boolean blacklist, String... openIds) throws WeixinException {
		return tagApi.batchBlacklist(blacklist, openIds);
	}

	/**
	 * 创建卡券:创建卡券接口是微信卡券的基础接口，用于创建一类新的卡券，获取card_id，创建成功并通过审核后，
	 * 商家可以通过文档提供的其他接口将卡券下发给用户，每次成功领取，库存数量相应扣除。
	 *
	 * <li>1.需自定义Code码的商家必须在创建卡券时候，设定use_custom_code为true，且在调用投放卡券接口时填入指定的Code码。
	 * 指定OpenID同理。特别注意：在公众平台创建的卡券均为非自定义Code类型。
	 * <li>2.can_share字段指领取卡券原生页面是否可分享，建议指定Code码、指定OpenID等强限制条件的卡券填写false。
	 * <li>3.创建成功后该卡券会自动提交审核
	 * ，审核结果将通过事件通知商户。开发者可调用设置白名单接口设置用户白名单，领取未通过审核的卡券，测试整个卡券的使用流程。
	 *
	 * @param cardCoupon
	 *            卡券对象
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056&token=&lang=zh_CN">
	 *      创建卡券</a>
	 * @see CardCoupons
	 * @see MediaApi#uploadImage(java.io.InputStream, String)
	 * @see com.foxinmy.weixin4j.mp.api.CardApi
	 * @return 卡券ID
	 * @throws WeixinException
	 */
	public String createCardCoupon(CardCoupon cardCoupon) throws WeixinException {
		return cardApi.createCardCoupon(cardCoupon);
	}

	/**
	 * 设置卡券买单：创建卡券之后，开发者可以通过设置微信买单接口设置该card_id支持微信买单功能。值得开发者注意的是，
	 * 设置买单的card_id必须已经配置了门店，否则会报错。
	 *
	 * @param cardId
	 *            卡券ID
	 * @param isOpen
	 *            是否开启买单功能，填true/false
	 * @see #createCardCoupon(CardCoupon)
	 * @see com.foxinmy.weixin4j.mp.api.CardApi
	 * @return 操作结果
	 * @throws WeixinException
	 */
	public ApiResult setCardPayCell(String cardId, boolean isOpen) throws WeixinException {
		return cardApi.setCardPayCell(cardId, isOpen);
	}

	/**
	 * 设置自助核销:创建卡券之后，开发者可以通过设置微信买单接口设置该card_id支持自助核销功能。值得开发者注意的是，
	 * 设置自助核销的card_id必须已经配置了门店，否则会报错。
	 *
	 * @param cardId
	 *            卡券ID
	 * @param isOpen
	 *            是否开启买单功能，填true/false
	 * @see #createCardCoupon(CardCoupon)
	 * @see com.foxinmy.weixin4j.mp.api.CardApi
	 * @return 操作结果
	 * @throws WeixinException
	 */
	public ApiResult setCardSelfConsumeCell(String cardId, boolean isOpen) throws WeixinException {
		return cardApi.setCardSelfConsumeCell(cardId, isOpen);
	}

	/**
	 * 创建卡券二维码： 开发者可调用该接口生成一张卡券二维码供用户扫码后添加卡券到卡包。
	 *
	 * @param expireSeconds
	 *            指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为365天有效
	 * @param cardQRs
	 *            二维码参数:二维码领取单张卡券/多张卡券
	 * @return 二维码结果对象
	 * @see com.foxinmy.weixin4j.model.qr.QRResult
	 * @see com.foxinmy.weixin4j.model.qr.QRParameter
	 * @see com.foxinmy.weixin4j.mp.api.CardApi
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062&token=&lang=zh_CN">
	 *      投放卡券</a>
	 * @throws WeixinException
	 */
	public QRResult createCardQR(Integer expireSeconds, CardQR... cardQRs) throws WeixinException {
		return cardApi.createCardQR(expireSeconds, cardQRs);
	}

	/**
	 * 打开/关闭已群发文章评论
	 *
	 * @param open
	 *            true为打开，false为关闭
	 * @param msgid
	 *            群发返回的msg_data_id
	 * @param index
	 *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @return 操作结果
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see {@link MassApi#massByTagId(com.foxinmy.weixin4j.tuple.MassTuple, int)}
	 * @see {@link MassApi#massByOpenIds(com.foxinmy.weixin4j.tuple.MassTuple, String...)}
	 * @throws WeixinException
	 */
	public ApiResult openComment(boolean open, String msgid, int index) throws WeixinException {
		return commentApi.openComment(open, msgid, index);
	}

	/**
	 * 获取评论列表
	 *
	 * @param page
	 *            分页信息
	 * @param commentType
	 *            评论类型 为空获取全部类型
	 * @param msgid
	 *            群发返回的msg_data_id
	 * @param index
	 *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @return 分页数据
	 * @see ArticleComment
	 * @see ArticleCommentType
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see {@link MassApi#massByTagId(com.foxinmy.weixin4j.tuple.MassTuple, int)}
	 * @see {@link MassApi#massByOpenIds(com.foxinmy.weixin4j.tuple.MassTuple, String...)}
	 * @throws WeixinException
	 */
	public Pagedata<ArticleComment> listArticleComments(Pageable page, ArticleCommentType commentType, String msgid,
			int index) throws WeixinException {
		return commentApi.listArticleComments(page, commentType, msgid, index);
	}

	/**
	 * 获取评论列表
	 *
	 * @param commentType
	 *            评论类型 为空获取全部类型
	 * @param msgid
	 *            群发返回的msg_data_id
	 * @param index
	 *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @return 分页数据
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
	 * @throws WeixinException
	 */
	public List<ArticleComment> listAllArticleComments(ArticleCommentType commentType, String msgid, int index)
			throws WeixinException {
		return commentApi.listAllArticleComments(commentType, msgid, index);
	}

	/**
	 * 评论标记/取消精选
	 *
	 * @param markelect
	 *            true为标记，false为取消
	 * @param msgid
	 *            群发返回的msg_data_id
	 * @param index
	 *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param commentId
	 *            用户评论ID
	 * @return 操作结果
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
	 * @throws WeixinException
	 */
	public ApiResult markelectComment(boolean markelect, String msgid, int index, String commentId)
			throws WeixinException {
		return commentApi.markelectComment(markelect, msgid, index, commentId);
	}

	/**
	 * 删除评论
	 *
	 * @param msgid
	 *            群发返回的msg_data_id
	 * @param index
	 *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param commentId
	 *            用户评论ID
	 * @return 操作结果
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
	 * @throws WeixinException
	 */
	public ApiResult deleteComment(String msgid, int index, String commentId) throws WeixinException {
		return commentApi.deleteComment(msgid, index, commentId);
	}

	/**
	 * 回复评论
	 *
	 * @param msgid
	 *            群发返回的msg_data_id
	 * @param index
	 *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param commentId
	 *            用户评论ID
	 * @param content
	 *            回复内容
	 * @return 操作结果
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
	 * @throws WeixinException
	 */
	public ApiResult replyComment(String msgid, int index, String commentId, String content) throws WeixinException {
		return commentApi.replyComment(msgid, index, commentId, content);
	}

	/**
	 * 删除回复
	 *
	 * @param msgid
	 *            群发返回的msg_data_id
	 * @param index
	 *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
	 * @param commentId
	 *            用户评论ID
	 * @return 操作结果
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
	 * @throws WeixinException
	 */
	public ApiResult deleteCommentReply(String msgid, int index, String commentId) throws WeixinException {
		return commentApi.deleteCommentReply(msgid, index, commentId);
	}

	public final static String VERSION = "1.7.9";
}
