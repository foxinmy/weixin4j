package com.foxinmy.weixin4j.mp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.MediaCounter;
import com.foxinmy.weixin4j.model.MediaDownloadResult;
import com.foxinmy.weixin4j.model.MediaItem;
import com.foxinmy.weixin4j.model.MediaRecord;
import com.foxinmy.weixin4j.model.MediaUploadResult;
import com.foxinmy.weixin4j.model.Pageable;
import com.foxinmy.weixin4j.mp.api.CustomApi;
import com.foxinmy.weixin4j.mp.api.DataApi;
import com.foxinmy.weixin4j.mp.api.GroupApi;
import com.foxinmy.weixin4j.mp.api.HelperApi;
import com.foxinmy.weixin4j.mp.api.MassApi;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.mp.api.MpApi;
import com.foxinmy.weixin4j.mp.api.NotifyApi;
import com.foxinmy.weixin4j.mp.api.QrApi;
import com.foxinmy.weixin4j.mp.api.TmplApi;
import com.foxinmy.weixin4j.mp.api.UserApi;
import com.foxinmy.weixin4j.mp.message.NotifyMessage;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.model.AutoReplySetting;
import com.foxinmy.weixin4j.mp.model.CustomRecord;
import com.foxinmy.weixin4j.mp.model.Following;
import com.foxinmy.weixin4j.mp.model.Group;
import com.foxinmy.weixin4j.mp.model.KfAccount;
import com.foxinmy.weixin4j.mp.model.KfSession;
import com.foxinmy.weixin4j.mp.model.MenuSetting;
import com.foxinmy.weixin4j.mp.model.QRParameter;
import com.foxinmy.weixin4j.mp.model.QRResult;
import com.foxinmy.weixin4j.mp.model.SemQuery;
import com.foxinmy.weixin4j.mp.model.SemResult;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.mp.type.DatacubeType;
import com.foxinmy.weixin4j.mp.type.IndustryType;
import com.foxinmy.weixin4j.mp.type.Lang;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.TokenStorager;
import com.foxinmy.weixin4j.tuple.MassTuple;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.tuple.MpVideo;
import com.foxinmy.weixin4j.tuple.Tuple;
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
	private final DataApi dataApi;

	private final TokenHolder tokenHolder;

	/**
	 * 默认使用文件方式保存token、使用weixin4j.properties配置的账号信息
	 */
	public WeixinProxy() {
		this(MpApi.DEFAULT_TOKEN_STORAGER);
	}

	/**
	 * 默认使用weixin4j.properties配置的账号信息
	 * 
	 * @param tokenStorager
	 */
	public WeixinProxy(TokenStorager tokenStorager) {
		this(MpApi.DEFAULT_WEIXIN_ACCOUNT.getId(), MpApi.DEFAULT_WEIXIN_ACCOUNT
				.getSecret(), tokenStorager);
	}

	/**
	 * 
	 * @param appid
	 * @param appsecret
	 */
	public WeixinProxy(String appid, String appsecret) {
		this(appid, appsecret, MpApi.DEFAULT_TOKEN_STORAGER);
	}

	public WeixinProxy(String appid, String appsecret,
			TokenStorager tokenStorager) {
		this(new TokenHolder(new WeixinTokenCreator(appid, appsecret),
				tokenStorager));
	}

	public WeixinProxy(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
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
		this.dataApi = new DataApi(tokenHolder);
	}

	public TokenHolder getTokenHolder() {
		return this.tokenHolder;
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
	public String uploadImage(InputStream is, String fileName)
			throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html">高级群发</a>
	 * @see com.foxinmy.weixin4j.tuple.MpVideo
	 */
	public MpVideo uploadVideo(InputStream is, String fileName, String title,
			String description) throws WeixinException {
		return mediaApi.uploadVideo(is, fileName, title, description);
	}

	/**
	 * 上传媒体文件 </br> <font color="red">此接口只包含图片、语音、缩略图、视频(临时)四种媒体类型的上传</font>
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param is
	 *            媒体数据流
	 * @param mediaType
	 *            媒体文件类型：分别有图片（image）、语音（voice）、视频(video)和缩略图（thumb）
	 * @param fileName
	 *            文件名
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/963fc70b80dc75483a271298a76a8d59.html">上传临时素材</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久素材</a>
	 * @see com.foxinmy.weixin4j.model.MediaUploadResult
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @throws WeixinException
	 */
	public MediaUploadResult uploadMedia(boolean isMaterial, InputStream is,
			String fileName) throws WeixinException {
		return mediaApi.uploadMedia(isMaterial, is, fileName);
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
	 * @param isMaterial
	 *            是否永久素材
	 * @return 写入硬盘后的文件对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/78b15308b053286e2a66b33f0f0f5fb6.html">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see {@link #downloadMedia(String)}
	 */
	public File downloadMediaFile(String mediaId, boolean isMaterial)
			throws WeixinException {
		return mediaApi.downloadMediaFile(mediaId, isMaterial);
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
	 * @see com.foxinmy.weixin4j.model.MediaDownloadResult
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/78b15308b053286e2a66b33f0f0f5fb6.html">上传下载说明</a>
	 */
	public MediaDownloadResult downloadMedia(String mediaId, boolean isMaterial)
			throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久媒体素材</a>
	 */
	public String uploadMaterialArticle(List<MpArticle> articles)
			throws WeixinException {
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
	public List<MpArticle> downloadArticle(String mediaId)
			throws WeixinException {
		return mediaApi.downloadArticle(mediaId);
	}

	/**
	 * 更新永久图文素材
	 * 
	 * @param mediaId
	 *            要修改的图文消息的id
	 * @param index
	 *            要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
	 * @param articles
	 *            图文列表
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/19a59cba020d506e767360ca1be29450.html">更新永久图文素材</a>
	 */
	public JsonResult updateMaterialArticle(String mediaId, int index,
			List<MpArticle> articles) throws WeixinException {
		return mediaApi.updateMaterialArticle(mediaId, index, articles);
	}

	/**
	 * 删除永久媒体素材
	 * 
	 * @param mediaId
	 *            媒体素材的media_id
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/e66f61c303db51a6c0f90f46b15af5f5.html">删除永久媒体素材</a>
	 */
	public JsonResult deleteMaterialMedia(String mediaId)
			throws WeixinException {
		return mediaApi.deleteMaterialMedia(mediaId);
	}

	/**
	 * 上传永久视频素材
	 * 
	 * @param is
	 *            大小不超过1M且格式为MP4的视频文件
	 * @param title
	 *            视频标题
	 * @param introduction
	 *            视频描述
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @throws WeixinException
	 */
	public String uploadMaterialVideo(InputStream is, String title,
			String introduction) throws WeixinException {
		return mediaApi.uploadMaterialVideo(is, title, introduction);
	}

	/**
	 * 获取永久媒体素材的总数</br> .图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
	 * 
	 * @return 总数对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.MediaCounter
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/16/8cc64f8c189674b421bee3ed403993b8.html">获取素材总数</a>
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
	 * @see com.foxinmy.weixin4j.mp.model.MediaRecord
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.mp.model.MediaItem
	 * @see com.foxinmy.weixin4j.model.MediaItem
	 * @see com.foxinmy.weixin4j.model.Pageable
	 * @see com.foxinmy.weixin4j.model.Pagedata
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html">获取素材列表</a>
	 */
	public MediaRecord listMaterialMedia(MediaType mediaType, Pageable pageable)
			throws WeixinException {
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
	public List<MediaItem> listAllMaterialMedia(MediaType mediaType)
			throws WeixinException {
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
	public JsonResult sendNotify(NotifyMessage notify) throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF">发送客服消息</a>
	 * @see com.foxinmy.weixin4j.tuple.Text
	 * @see com.foxinmy.weixin4j.tuple.Image
	 * @see com.foxinmy.weixin4j.tuple.Voice
	 * @see com.foxinmy.weixin4j.tuple.Video
	 * @see com.foxinmy.weixin4j.tuple.Music
	 * @see com.foxinmy.weixin4j.tuple.News
	 * @see com.foxinmy.weixin4j.mp.api.NotifyApi
	 */
	public JsonResult sendNotify(NotifyMessage notify, String kfAccount)
			throws WeixinException {
		return notifyApi.sendNotify(notify, kfAccount);
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
	 *            每页大小 每页最多拉取50条
	 * @param pageindex
	 *            查询第几页 从1开始
	 * @see com.foxinmy.weixin4j.mp.model.CustomRecord
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a href="http://dkf.qq.com/document-1_1.html">查询客服聊天记录</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/19/7c129ec71ddfa60923ea9334557e8b23.html">查询客服聊天记录</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E8.8E.B7.E5.8F.96.E5.AE.A2.E6.9C.8D.E5.9F.BA.E6.9C.AC.E4.BF.A1.E6.81.AF">获取客服基本信息</a>
	 * @see <a href="http://dkf.qq.com/document-3_2.html">获取在线客服接待信息</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E8.8E.B7.E5.8F.96.E5.9C.A8.E7.BA.BF.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.BE.85.E4.BF.A1.E6.81.AF">获取在线客服接待信息</a>
	 * @throws WeixinException
	 */
	public List<KfAccount> listKfAccount(boolean isOnline)
			throws WeixinException {
		return customApi.listKfAccount(isOnline);
	}

	/**
	 * 新增客服账号
	 * 
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，
	 *            请前往微信公众平台设置。
	 * @param name
	 *            客服昵称，最长6个汉字或12个英文字符
	 * @param pwd
	 *            客服账号登录密码
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E5.AE.A2.E6.9C.8D.E7.AE.A1.E7.90.86.E6.8E.A5.E5.8F.A3.E8.BF.94.E5.9B.9E.E7.A0.81.E8.AF.B4.E6.98.8E">客服管理接口返回码</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E6.B7.BB.E5.8A.A0.E5.AE.A2.E6.9C.8D.E8.B4.A6.E5.8F.B7">新增客服账号</a>
	 */
	public JsonResult createAccount(String id, String name, String pwd)
			throws WeixinException {
		return customApi.createAccount(id, name, pwd);
	}

	/**
	 * 更新客服账号
	 * 
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，
	 *            请前往微信公众平台设置。
	 * @param name
	 *            客服昵称，最长6个汉字或12个英文字符
	 * @param pwd
	 *            客服账号登录密码
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E5.AE.A2.E6.9C.8D.E7.AE.A1.E7.90.86.E6.8E.A5.E5.8F.A3.E8.BF.94.E5.9B.9E.E7.A0.81.E8.AF.B4.E6.98.8E">客服管理接口返回码</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E8.AE.BE.E7.BD.AE.E5.AE.A2.E6.9C.8D.E4.BF.A1.E6.81.AF">新增客服账号</a>
	 */
	public JsonResult updateAccount(String id, String name, String pwd)
			throws WeixinException {
		return customApi.updateAccount(id, name, pwd);
	}

	/**
	 * 上传客服头像
	 * 
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @param headimg
	 *            头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
	 * @return 处理结果
	 * @throws WeixinException
	 * @throws IOException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E5.AE.A2.E6.9C.8D.E7.AE.A1.E7.90.86.E6.8E.A5.E5.8F.A3.E8.BF.94.E5.9B.9E.E7.A0.81.E8.AF.B4.E6.98.8E">客服管理接口返回码</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E4.B8.8A.E4.BC.A0.E5.AE.A2.E6.9C.8D.E5.A4.B4.E5.83.8F">上传客服头像</a>
	 */
	public JsonResult uploadAccountHeadimg(String id, File headimg)
			throws WeixinException, IOException {
		return customApi.uploadAccountHeadimg(id, headimg);
	}

	/**
	 * 删除客服账号
	 * 
	 * @param id
	 *            完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E5.AE.A2.E6.9C.8D.E7.AE.A1.E7.90.86.E6.8E.A5.E5.8F.A3.E8.BF.94.E5.9B.9E.E7.A0.81.E8.AF.B4.E6.98.8E">客服管理接口返回码</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E5.88.A0.E9.99.A4.E5.AE.A2.E6.9C.8D.E8.B4.A6.E5.8F.B7">删除客服账号</a>
	 */
	public JsonResult deleteAccount(String id) throws WeixinException {
		return customApi.deleteAccount(id);
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/2/6c20f3e323bdf5986cfcb33cbd3b829a.html#.E5.88.9B.E5.BB.BA.E4.BC.9A.E8.AF.9D">创建会话</a>
	 */
	public JsonResult createKfSession(String userOpenId, String kfAccount,
			String text) throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/2/6c20f3e323bdf5986cfcb33cbd3b829a.html#.E5.85.B3.E9.97.AD.E4.BC.9A.E8.AF.9D">创建会话</a>
	 */
	public JsonResult closeKfSession(String userOpenId, String kfAccount,
			String text) throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/2/6c20f3e323bdf5986cfcb33cbd3b829a.html#.E8.8E.B7.E5.8F.96.E5.AE.A2.E6.88.B7.E7.9A.84.E4.BC.9A.E8.AF.9D.E7.8A.B6.E6.80.81">获取会话状态</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/2/6c20f3e323bdf5986cfcb33cbd3b829a.html#.E8.8E.B7.E5.8F.96.E5.AE.A2.E6.9C.8D.E7.9A.84.E4.BC.9A.E8.AF.9D.E5.88.97.E8.A1.A8">获取客服的会话列表</a>
	 */
	public List<KfSession> listKfSession(String kfAccount)
			throws WeixinException {
		return customApi.listKfSession(kfAccount);
	}

	/**
	 * 获取未接入会话列表:获取当前正在等待队列中的会话列表，此接口最多返回最早进入队列的100个未接入会话。</br> <font
	 * color="red">缺陷：没有count字段</font>
	 * 
	 * @return 会话列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.CustomApi
	 * @see com.foxinmy.weixin4j.mp.model.KfSession
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/2/6c20f3e323bdf5986cfcb33cbd3b829a.html#.E8.8E.B7.E5.8F.96.E6.9C.AA.E6.8E.A5.E5.85.A5.E4.BC.9A.E8.AF.9D.E5.88.97.E8.A1.A8">获取客服的会话列表</a>
	 */
	public List<KfSession> listKfSessionWait() throws WeixinException {
		return customApi.listKfSessionWait();
	}

	/**
	 * 上传群发的图文消息,一个图文消息支持1到10条图文
	 * 
	 * @param articles
	 *            图片消息
	 * @return 媒体ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF.E7.B4.A0.E6.9D.90.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">上传图文素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 */
	public String uploadMassArticle(List<MpArticle> articles)
			throws WeixinException {
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
	 * @see {@link com.foxinmy.weixin4j.mp.api.GroupApi#getGroups()}
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AE.E5.88.86.E7.BB.84.E8.BF.9B.E8.A1.8C.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">根据分组群发</a>
	 */
	public String[] massByGroupId(MassTuple tuple, boolean isToAll, int groupId)
			throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AE.E5.88.86.E7.BB.84.E8.BF.9B.E8.A1.8C.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">根据分组群发</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @throws WeixinException
	 */
	public String[] massArticleByGroupId(List<MpArticle> articles, int groupId)
			throws WeixinException {
		return massApi.massArticleByGroupId(articles, groupId);
	}

	/**
	 * openId群发
	 * 
	 * <p>
	 * 在返回成功时,意味着群发任务提交成功,并不意味着此时群发已经结束,所以,仍有可能在后续的发送过程中出现异常情况导致用户未收到消息,
	 * 如消息有时会进行审核、服务器不稳定等,此外,群发任务一般需要较长的时间才能全部发送完毕
	 * </p>
	 * 
	 * @param tuple
	 *            消息元件
	 * @param openIds
	 *            openId列表
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.tuple.Text
	 * @see com.foxinmy.weixin4j.tuple.Image
	 * @see com.foxinmy.weixin4j.tuple.Voice
	 * @see com.foxinmy.weixin4j.tuple.MpVideo
	 * @see com.foxinmy.weixin4j.tuple.MpNews
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see com.foxinmy.weixin4j.tuple.MassTuple
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AEOpenID.E5.88.97.E8.A1.A8.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8D.E5.8F.AF.E7.94.A8.EF.BC.8C.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.8F.AF.E7.94.A8.E3.80.91">根据openid群发</a>
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(File)}
	 * @see {@link com.foxinmy.weixin4j.mp.api.UserApi#getUser(String)}
	 */
	public String[] massByOpenIds(MassTuple tuple, String... openIds)
			throws WeixinException {
		return massApi.massByOpenIds(tuple, openIds);
	}

	/**
	 * 根据openid群发图文消息
	 * 
	 * @param articles
	 *            图文列表
	 * @param openIds
	 *            openId列表
	 * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AEOpenID.E5.88.97.E8.A1.A8.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8D.E5.8F.AF.E7.94.A8.EF.BC.8C.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.8F.AF.E7.94.A8.E3.80.91">根据openid群发</a>
	 * @see {@link #massByOpenIds(Tuple,String...)}
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @throws WeixinException
	 */
	public String[] massArticleByOpenIds(List<MpArticle> articles,
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
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E5.88.A0.E9.99.A4.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">删除群发</a>
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see {@link #massByGroupId(Tuple, int)}
	 * @see {@link #massByOpenIds(Tuple, String...)
	 */
	public JsonResult deleteMassNews(String msgid) throws WeixinException {
		return massApi.deleteMassNews(msgid);
	}

	/**
	 * 预览群发消息</br> 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E9.A2.84.E8.A7.88.E6.8E.A5.E5.8F.A3.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">预览群发消息</a>
	 */
	public JsonResult previewMassNews(String toUser, String toWxName,
			MassTuple tuple) throws WeixinException {
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
	 * @see {@link com.foxinmy.weixin4j.util.MessageUtil#getStatusDesc(String)}
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.9F.A5.E8.AF.A2.E7.BE.A4.E5.8F.91.E6.B6.88.E6.81.AF.E5.8F.91.E9.80.81.E7.8A.B6.E6.80.81.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">查询群发状态</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html">获取用户信息</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html">获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.type.Lang
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public User getUser(String openId, Lang lang) throws WeixinException {
		return userApi.getUser(openId, lang);
	}

	/**
	 * 获取用户一定数量(10000)的关注者列表
	 * 
	 * @param nextOpenId
	 *            第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 关注信息
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/3/17e6919a39c1c53555185907acf70093.html">获取关注者列表</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/3/17e6919a39c1c53555185907acf70093.html">获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 * @see {@link #getFollowing(String)}
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
	 *      href="http://mp.weixin.qq.com/wiki/10/bf8f4e3074e1cf91eb6518b6d08d223e.html">设置用户备注名</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84">创建分组</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E6.9F.A5.E8.AF.A2.E6.89.80.E6.9C.89.E5.88.86.E7.BB.84">查询所有分组</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E6.9F.A5.E8.AF.A2.E7.94.A8.E6.88.B7.E6.89.80.E5.9C.A8.E5.88.86.E7.BB.84">查询用户所在分组</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E4.BF.AE.E6.94.B9.E5.88.86.E7.BB.84.E5.90.8D">修改分组名</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public JsonResult modifyGroup(int groupId, String name)
			throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public JsonResult moveGroup(int groupId, String openId)
			throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/56d992c605a97245eb7e617854b169fc.html#.E6.89.B9.E9.87.8F.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">批量移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public JsonResult moveGroup(int groupId, String... openIds)
			throws WeixinException {
		return groupApi.moveGroup(groupId, openIds);
	}

	/**
	 * 删除用户分组,所有该分组内的用户自动进入默认分组.
	 * 
	 * @param groupId
	 *            组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/56d992c605a97245eb7e617854b169fc.html#.E5.88.A0.E9.99.A4.E5.88.86.E7.BB.84">删除用户分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.api.GroupApi
	 */
	public JsonResult deleteGroup(int groupId) throws WeixinException {
		return groupApi.deleteGroup(groupId);
	}

	/**
	 * 自定义菜单
	 * 
	 * @param btnList
	 *            菜单列表
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html">创建自定义菜单</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html">查询菜单</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/16/8ed41ba931e4845844ad6d1eeb8060c8.html">删除菜单</a>
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
	 *            二维码参数
	 * @return 二维码结果对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.QRResult
	 * @see com.foxinmy.weixin4j.mp.model.QRParameter
	 * @see com.foxinmy.weixin4j.mp.api.QrApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">生成二维码</a>
	 */
	public QRResult createQR(QRParameter parameter) throws WeixinException {
		return qrApi.createQR(parameter);
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @return 硬盘存储的文件对象
	 * @throws WeixinException
	 * @see {@link #createQR(QRParameter)}
	 */
	public File createQRFile(QRParameter parameter) throws WeixinException {
		return qrApi.createQRFile(parameter);
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E8.AE.BE.E7.BD.AE.E6.89.80.E5.B1.9E.E8.A1.8C.E4.B8.9A">设置所处行业</a>
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
	 * @param tplMessage
	 *            模板消息主体
	 * @return 发送结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E5.8F.91.E9.80.81.E6.A8.A1.E6.9D.BF.E6.B6.88.E6.81.AF">模板消息</a>
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
	 *            待转换的链接
	 * @return 短链接
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/165c9b15eddcfbd8699ac12b0bd89ae6.html">长链接转短链接</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/0ce78b3c9524811fee34aba3e33f3448.html">语义理解</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html">获取IP地址</a>
	 * @see com.foxinmy.weixin4j.mp.api.HelperApi
	 * @throws WeixinException
	 */
	public List<String> getCallbackip() throws WeixinException {
		return helperApi.getCallbackip();
	}

	/**
	 * 获取公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
	 * 而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
	 * 
	 * @return 菜单集合
	 * @see {@link #getMenu()}
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/4dc4b0514fdad7a5fbbd477aa9aab5ed.html">获取自定义菜单配置</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/7/7b5789bb1262fb866d01b4b40b0efecb.html">获取自动回复规则</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html">用户分析</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/8/c0453610fb5131d1fcb17b4e87c82050.html">图文分析</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/12/32d42ad542f2e4fc8a8aa60e1bce9838.html">消息分析</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/8/30ed81ae38cf4f977194bf1a5db73668.html">接口分析</a>
	 * @throws WeixinException
	 */
	public List<?> datacube(DatacubeType datacubeType, Date beginDate,
			Date endDate) throws WeixinException {
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
	public List<?> datacube(DatacubeType datacubeType, Date beginDate,
			int offset) throws WeixinException {
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
	public List<?> datacube(DatacubeType datacubeType, int offset, Date endDate)
			throws WeixinException {
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
	public List<?> datacube(DatacubeType datacubeType, Date date)
			throws WeixinException {
		return dataApi.datacube(datacubeType, date);
	}

	public final static String VERSION = "1.5.2";
}
