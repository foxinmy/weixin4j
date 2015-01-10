package com.foxinmy.weixin4j.mp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.mp.api.CustomApi;
import com.foxinmy.weixin4j.mp.api.GroupApi;
import com.foxinmy.weixin4j.mp.api.HelperApi;
import com.foxinmy.weixin4j.mp.api.MassApi;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.mp.api.NotifyApi;
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
import com.foxinmy.weixin4j.mp.type.IndustryType;
import com.foxinmy.weixin4j.mp.type.Lang;
import com.foxinmy.weixin4j.msg.model.Base;
import com.foxinmy.weixin4j.msg.model.MpArticle;
import com.foxinmy.weixin4j.msg.model.Video;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.WeixinTokenCreator;
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

	/**
	 * 默认采用文件存放Token信息
	 */
	public WeixinProxy() {
		this(new FileTokenHolder(new WeixinTokenCreator(AccountType.MP)));
	}

	/**
	 * 
	 * @param appid
	 * @param appsecret
	 */
	public WeixinProxy(String appid, String appsecret) {
		this(new FileTokenHolder(new WeixinTokenCreator(appid, appsecret,
				AccountType.MP)));
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
	 *      href="http://mp.weixin.qq.com/wiki/10/78b15308b053286e2a66b33f0f0f5fb6.html">上传下载说明</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/78b15308b053286e2a66b33f0f0f5fb6.html">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.mp.api.MediaApi
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#downloadMedia(String)}
	 */
	public File downloadMedia(String mediaId, MediaType mediaType)
			throws WeixinException {
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/78b15308b053286e2a66b33f0f0f5fb6.html">上传下载说明</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/7/12a5a320ae96fecdf0e15cb06123de9f.html">发送客服消息</a>
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
	public List<KfAccount> getKfAccountList(boolean isOnline)
			throws WeixinException {
		return customApi.getKfAccountList(isOnline);
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
	public JsonResult addAccount(String id, String name, String pwd)
			throws WeixinException {
		return customApi.addAccount(id, name, pwd);
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
	 * 上传图文消息,一个图文消息支持1到10条图文
	 * 
	 * @param articles
	 *            图片消息
	 * @return 媒体ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF.E7.B4.A0.E6.9D.90.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">上传图文素材</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html">高级群发</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AE.E5.88.86.E7.BB.84.E8.BF.9B.E8.A1.8C.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">根据分组群发</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AE.E5.88.86.E7.BB.84.E8.BF.9B.E8.A1.8C.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">根据分组群发</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AEOpenID.E5.88.97.E8.A1.A8.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8D.E5.8F.AF.E7.94.A8.EF.BC.8C.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.8F.AF.E7.94.A8.E3.80.91">根据openid群发</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AEOpenID.E5.88.97.E8.A1.A8.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8D.E5.8F.AF.E7.94.A8.EF.BC.8C.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.8F.AF.E7.94.A8.E3.80.91">根据openid群发</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E5.88.A0.E9.99.A4.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">删除群发</a>
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#massByGroupId(Base, int)}
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#massByOpenIds(Base, String...)
	 */
	public JsonResult deleteMassNews(String msgid) throws WeixinException {
		return massApi.deleteMassNews(msgid);
	}

	/**
	 * 预览群发消息<br/>
	 * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版
	 * 
	 * @param openId
	 *            接收用户的ID
	 * @param box
	 *            消息体
	 * @return 处理结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E9.A2.84.E8.A7.88.E6.8E.A5.E5.8F.A3.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">预览群发消息</a>
	 */
	public JsonResult previewMassNews(String openId, Base box)
			throws WeixinException {
		return massApi.previewMassNews(openId, box);
	}

	/**
	 * 查询群发发送状态
	 * 
	 * @param msgId
	 *            消息ID
	 * @return 消息发送状态
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.MassApi
	 * @see {@link com.foxinmy.weixin4j.msg.event.MassEventMessage#getStatusDesc(String)}
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.9F.A5.E8.AF.A2.E7.BE.A4.E5.8F.91.E6.B6.88.E6.81.AF.E5.8F.91.E9.80.81.E7.8A.B6.E6.80.81.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91">查询群发状态</a>
	 */
	public String getMassNews(String msgId) throws WeixinException {
		return massApi.getMassNews(msgId);
	}

	/**
	 * oauth授权code换取token
	 * 
	 * @param code
	 *            用户授权后返回的code
	 * @return token对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">获取用户token</a>
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public OauthToken getOauthToken(String code, String appid, String appsecret)
			throws WeixinException {
		return userApi.getOauthToken(code, appid, appsecret);
	}

	/**
	 * ouath获取用户信息
	 * 
	 * @param token
	 *            授权票据
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">拉取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @see com.foxinmy.weixin4j.mp.api.UserApi
	 */
	public User getUser(OauthToken token) throws WeixinException {
		return userApi.getUser(token);
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
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#getUser(String,Lang)}
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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">移动分组</a>
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
	 * @return byte数据包
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.api.QrApi
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">生成二维码</a>
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">生成二维码</a>
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
	 * @see <a
	 *      href="mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html">二维码</a>
	 * @see com.foxinmy.weixin4j.mp.model.QRParameter
	 * @see com.foxinmy.weixin4j.mp.api.QrApi
	 */
	public File getQR(QRParameter parameter) throws WeixinException {
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
	public List<String> getcallbackip() throws WeixinException {
		return helperApi.getcallbackip();
	}
}
