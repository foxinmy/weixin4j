package com.foxinmy.weixin4j.qy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.qy.api.AgentApi;
import com.foxinmy.weixin4j.qy.api.BatchApi;
import com.foxinmy.weixin4j.qy.api.HelperApi;
import com.foxinmy.weixin4j.qy.api.MediaApi;
import com.foxinmy.weixin4j.qy.api.MenuApi;
import com.foxinmy.weixin4j.qy.api.NotifyApi;
import com.foxinmy.weixin4j.qy.api.PartyApi;
import com.foxinmy.weixin4j.qy.api.TagApi;
import com.foxinmy.weixin4j.qy.api.UserApi;
import com.foxinmy.weixin4j.qy.message.NotifyMessage;
import com.foxinmy.weixin4j.qy.model.AgentInfo;
import com.foxinmy.weixin4j.qy.model.AgentOverview;
import com.foxinmy.weixin4j.qy.model.AgentSetter;
import com.foxinmy.weixin4j.qy.model.BatchResult;
import com.foxinmy.weixin4j.qy.model.Callback;
import com.foxinmy.weixin4j.qy.model.IdParameter;
import com.foxinmy.weixin4j.qy.model.Party;
import com.foxinmy.weixin4j.qy.model.Tag;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.qy.type.InviteType;
import com.foxinmy.weixin4j.qy.type.UserStatus;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 微信企业号接口实现
 * 
 * @className WeixinProxy
 * @author jy
 * @date 2014年11月19日
 * @since JDK 1.7
 * @see <a href="http://qydev.weixin.qq.com/wiki/index.php">api文档</a>
 */
public class WeixinProxy {
	private final MediaApi mediaApi;
	private final MenuApi menuApi;
	private final NotifyApi notifyApi;
	private final PartyApi partyApi;
	private final UserApi userApi;
	private final TagApi tagApi;
	private final HelperApi helperApi;
	private final AgentApi agentApi;
	private final BatchApi batchApi;

	/**
	 * 默认采用文件存放Token信息
	 */
	public WeixinProxy() {
		this(new FileTokenHolder(new WeixinTokenCreator()));
	}

	/**
	 * appid,appsecret
	 * 
	 * @param corpid
	 * @param corpsecret
	 */
	public WeixinProxy(String corpid, String corpsecret) {
		this(new FileTokenHolder(new WeixinTokenCreator(corpid, corpsecret)));
	}

	/**
	 * TokenHolder对象
	 * 
	 * @param tokenHolder
	 */
	public WeixinProxy(TokenHolder tokenHolder) {
		this.partyApi = new PartyApi(tokenHolder);
		this.userApi = new UserApi(tokenHolder);
		this.tagApi = new TagApi(tokenHolder);
		this.helperApi = new HelperApi(tokenHolder);
		this.agentApi = new AgentApi(tokenHolder);
		this.batchApi = new BatchApi(tokenHolder);
		this.notifyApi = new NotifyApi(tokenHolder);
		this.menuApi = new MenuApi(tokenHolder);
		this.mediaApi = new MediaApi(tokenHolder);
	}

	/**
	 * 发送消息(需要管理员对应用有使用权限，对收件人touser、toparty、totag有查看权限，否则本次调用失败)
	 * <p>
	 * 1） 发送人员列表存在错误的userid：执行发送，开发者需注意返回结果说明</br>
	 * 2）发送人员不在通讯录权限范围内：不执行发送任务，返回首个出错的userid</br>
	 * 3）发送人员不在应用可见范围内：不执行发送任务，返回首个出错的userid</br>
	 * </p>
	 * 
	 * @param notify
	 *            客服消息对象
	 * @return 
	 *         如果对应用或收件人、部门、标签任何一个无权限，则本次发送失败；如果收件人、部门或标签不存在，发送仍然执行，但返回无效的部分</br>
	 *         { "errcode": 0, "errmsg": "ok", "invaliduser": "UserID1",
	 *         "invalidparty":"PartyID1", "invalidtag":"TagID1" }
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.api.NotifyApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E">发送接口说明</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E5%8F%8A%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F">发送格式说明</a>
	 * @see com.foxinmy.weixin4j.msg.model.Text
	 * @see com.foxinmy.weixin4j.msg.model.Image
	 * @see com.foxinmy.weixin4j.msg.model.Voice
	 * @see com.foxinmy.weixin4j.msg.model.Video
	 * @see com.foxinmy.weixin4j.msg.model.File
	 * @see com.foxinmy.weixin4j.msg.model.News
	 * @see com.foxinmy.weixin4j.msg.model.MpNews
	 * @see com.foxinmy.weixin4j.qy.message.NotifyMessage
	 */
	public JSONObject sendNotify(NotifyMessage notify) throws WeixinException {
		return notifyApi.sendNotify(notify);
	}

	/**
	 * 自定义菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式)
	 * 
	 * @param btnList
	 *            菜单列表
	 * @param agentid
	 *            应用ID
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.api.MenuApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%88%9B%E5%BB%BA%E5%BA%94%E7%94%A8%E8%8F%9C%E5%8D%95">创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public JsonResult createMenu(List<Button> btnList, int agentid)
			throws WeixinException {
		return menuApi.createMenu(btnList, agentid);
	}

	/**
	 * 查询菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式。)
	 * 
	 * @param agentid
	 *            应用ID
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.api.MenuApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E8%8F%9C%E5%8D%95%E5%88%97%E8%A1%A8">查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public List<Button> getMenu(int agentid) throws WeixinException {
		return menuApi.getMenu(agentid);
	}

	/**
	 * 删除菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式)
	 * 
	 * @param agentid
	 *            应用ID
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.api.MenuApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%88%A0%E9%99%A4%E8%8F%9C%E5%8D%95">删除菜单</a>
	 * @return 处理结果
	 */
	public JsonResult deleteMenu(int agentid) throws WeixinException {
		return menuApi.deleteMenu(agentid);
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param file
	 *            媒体对象
	 * @return 上传到微信服务器返回的媒体标识
	 * @see com.foxinmy.weixin4j.qy.api.MediaApi
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy.MediaApi#uploadMedia(File, MediaType)}
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
	 * @see com.foxinmy.weixin4j.qy.api.MediaApi
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy#uploadMedia(String, byte[],String)}
	 */
	public String uploadMedia(File file, MediaType mediaType)
			throws WeixinException, IOException {
		return mediaApi.uploadMedia(file, mediaType);
	}

	/**
	 * 上传媒体文件(完全公开。所有管理员均可调用，media_id可以共享)
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param bytes
	 *            媒体数据包
	 * @param mediaType
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @see com.foxinmy.weixin4j.qy.api.MediaApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传媒体文件说明</a>
	 * @throws WeixinException
	 */
	public String uploadMedia(String fileName, byte[] bytes, String mediaType)
			throws WeixinException {
		return mediaApi.uploadMedia(fileName, bytes, mediaType);
	}

	/**
	 * 下载媒体文件(完全公开。所有管理员均可调用，media_id可以共享)
	 * 
	 * @param mediaId
	 *            媒体ID
	 * @return 二进制数据包
	 * @see com.foxinmy.weixin4j.qy.api.MediaApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">获取媒体说明</a>
	 * @throws WeixinException
	 */
	public byte[] downloadMedia(String mediaId) throws WeixinException {
		return mediaApi.downloadMedia(mediaId);
	}

	/**
	 * 下载媒体文件(完全公开。所有管理员均可调用，media_id可以共享)
	 * <p>
	 * 正常情况下返回表头如Content-Type: image/jpeg,否则抛出异常.
	 * </p>
	 * 
	 * @param mediaId
	 *            存储在微信服务器上的媒体标识
	 * @param extension
	 *            媒体后缀名
	 * @return 写入硬盘后的文件对象
	 * @throws WeixinException
	 * @throws IOException
	 * @see com.foxinmy.weixin4j.qy.api.MediaApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">获取媒体说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy.MediaApi#downloadMedia(String)}
	 */
	public File downloadMedia(String mediaId, String extension)
			throws WeixinException {
		return mediaApi.downloadMedia(mediaId, extension);
	}

	/**
	 * 创建部门(根部门的parentid为1)
	 * 
	 * @param party
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Party
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E5.88.9B.E5.BB.BA.E9.83.A8.E9.97.A8">创建部门说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.PartyApi
	 * @return 部门ID
	 * @throws WeixinException
	 */
	public int createParty(Party party) throws WeixinException {
		return partyApi.createParty(party);
	}

	/**
	 * 更新部门(如果非必须的字段未指定 则不更新该字段之前的设置值)
	 * 
	 * @param party
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Party
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E6.9B.B4.E6.96.B0.E9.83.A8.E9.97.A8">更新部门说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.PartyApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateParty(Party party) throws WeixinException {
		return partyApi.updateParty(party);
	}

	/**
	 * 查询部门列表(以部门的order字段从小到大排列)
	 * 
	 * @param partyId
	 *            部门ID。获取指定部门ID下的子部门
	 * @see com.foxinmy.weixin4j.qy.model.Party
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E5.88.97.E8.A1.A8">获取部门列表</a>
	 * @see com.foxinmy.weixin4j.qy.api.PartyApi
	 * @return 部门列表
	 * @throws WeixinException
	 */
	public List<Party> listParty(int partyId) throws WeixinException {
		return partyApi.listParty(partyId);
	}

	/**
	 * 删除部门(不能删除根部门；不能删除含有子部门、成员的部门)
	 * 
	 * @param partyId
	 *            部门ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E5.88.A0.E9.99.A4.E9.83.A8.E9.97.A8">删除部门说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.PartyApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult deleteParty(int partyId) throws WeixinException {
		return partyApi.deleteParty(partyId);
	}

	/**
	 * 批量上传部门
	 * 
	 * @param parties
	 *            部门列表
	 * @see com.foxinmy.weixin4j.qy.api.MediaApi
	 * @see com.foxinmy.weixin4j.qy.api.BatchApi
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy#replaceparty(String,Callback)}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E9.80.9A.E8.AE.AF.E5.BD.95.E6.9B.B4.E6.96.B0">批量任务</a>
	 * @return 上传后的mediaId
	 * @throws WeixinException
	 */
	public String batchUploadParties(List<Party> parties)
			throws WeixinException {
		return mediaApi.batchUploadParties(parties);
	}

	/**
	 * 创建成员
	 * 
	 * @param user
	 *            成员对象
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E5.88.9B.E5.BB.BA.E6.88.90.E5.91.98">创建成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult createUser(User user) throws WeixinException {
		return userApi.createUser(user);
	}

	/**
	 * 更新用户(如果非必须的字段未指定 则不更新该字段之前的设置值)
	 * 
	 * @param user
	 *            成员对象
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E6.9B.B4.E6.96.B0.E6.88.90.E5.91.98">更新成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateUser(User user) throws WeixinException {
		return userApi.updateUser(user);
	}

	/**
	 * 获取成员
	 * 
	 * @param userid
	 *            成员唯一ID
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E8.8E.B7.E5.8F.96.E6.88.90.E5.91.98">获取成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 成员对象
	 * @throws WeixinException
	 */
	public User getUser(String userid) throws WeixinException {
		return userApi.getUser(userid);
	}

	/**
	 * code获取userid(管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同。)
	 * 
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @param agentid
	 *            跳转链接时所在的企业应用ID
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 成员对象
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy#getUser(String)}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E8%8E%B7%E5%8F%96code">企业获取code</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%A0%B9%E6%8D%AEcode%E8%8E%B7%E5%8F%96%E6%88%90%E5%91%98%E4%BF%A1%E6%81%AF">根据code获取成员信息</a>
	 * @throws WeixinException
	 */
	public User getUserByCode(String code, int agentid) throws WeixinException {
		return userApi.getUserByCode(code, agentid);
	}

	/**
	 * 获取部门成员
	 * 
	 * @param partyId
	 *            部门ID 必须
	 * @param fetchChild
	 *            是否递归获取子部门下面的成员 非必须
	 * @param userStatus
	 *            成员状态 status可叠加 非必须
	 * @param findDetail
	 *            是否获取详细信息
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E6.88.90.E5.91.98">获取部门成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> listUser(int partyId, boolean fetchChild,
			UserStatus userStatus, boolean findDetail) throws WeixinException {
		return userApi.listUser(partyId, fetchChild, userStatus, findDetail);
	}

	/**
	 * 获取部门下所有状态成员(不进行递归)
	 * 
	 * @param departId
	 *            部门ID
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy#listUser(int, boolean,UserStatus)}
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> listUser(int departId) throws WeixinException {
		return userApi.listUser(departId);
	}

	/**
	 * 删除成员
	 * 
	 * @param userid
	 *            成员ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E5.88.A0.E9.99.A4.E6.88.90.E5.91.98">删除成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult deleteUser(String userid) throws WeixinException {
		return userApi.deleteUser(userid);
	}

	/**
	 * 批量删除成员
	 * 
	 * @param userIds
	 *            成员列表
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E6.89.B9.E9.87.8F.E5.88.A0.E9.99.A4.E6.88.90.E5.91.98"
	 *      >批量删除成员说明</a
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult batchDeleteUser(List<String> userIds)
			throws WeixinException {
		return userApi.batchDeleteUser(userIds);
	}

	/**
	 * 邀请成员关注(管理员须拥有该成员的查看权限)
	 * 
	 * @param userId
	 *            成员ID
	 * @param tips
	 *            推送到微信上的提示语（只有认证号可以使用）。当使用微信推送时，该字段默认为“请关注XXX企业号”，邮件邀请时，该字段无效。
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 调用结果
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E9.82.80.E8.AF.B7.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8">邀请成员关注说明</a>
	 * @throws WeixinException
	 */
	public InviteType inviteUser(String userId, String tips)
			throws WeixinException {
		return userApi.inviteUser(userId, tips);
	}

	/**
	 * 创建标签(创建的标签属于管理组;默认为未加锁状态)
	 * 
	 * @param tagName
	 *            标签名称
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.88.9B.E5.BB.BA.E6.A0.87.E7.AD.BE">创建标签说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.TagApi
	 * @return 标签ID
	 * @throws WeixinException
	 */
	public int createTag(String tagName) throws WeixinException {
		return tagApi.createTag(tagName);
	}

	/**
	 * 更新标签(管理组必须是指定标签的创建者)
	 * 
	 * @param tag
	 *            标签信息
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E6.9B.B4.E6.96.B0.E6.A0.87.E7.AD.BE.E5.90.8D.E5.AD.97"
	 *      >更新标签说明</a>
	 * @see com.foxinmy.weixin4j.qy.model.Tag
	 * @see com.foxinmy.weixin4j.qy.api.TagApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateTag(Tag tag) throws WeixinException {
		return tagApi.updateTag(tag);
	}

	/**
	 * 删除标签(管理组必须是指定标签的创建者 并且标签的成员列表为空)
	 * 
	 * @param tagId
	 *            标签ID
	 * @return 处理结果
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.88.A0.E9.99.A4.E6.A0.87.E7.AD.BE">删除标签说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.TagApi
	 * @throws WeixinException
	 */
	public JsonResult deleteTag(int tagId) throws WeixinException {
		return tagApi.deleteTag(tagId);
	}

	/**
	 * 获取标签列表
	 * 
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E8.8E.B7.E5.8F.96.E6.A0.87.E7.AD.BE.E5.88.97.E8.A1.A8">获取标签列表说明</a>
	 * @see com.foxinmy.weixin4j.qy.model.Tag
	 * @see com.foxinmy.weixin4j.qy.api.TagApi
	 * @return 标签列表
	 * @throws WeixinException
	 */
	public List<Tag> listTag() throws WeixinException {
		return tagApi.listTag();
	}

	/**
	 * 获取标签成员(管理组须拥有“获取标签成员”的接口权限，标签须对管理组可见；返回列表仅包含管理组管辖范围的成员)
	 * 
	 * @param tagId
	 *            标签ID
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E8.8E.B7.E5.8F.96.E6.A0.87.E7.AD.BE.E6.88.90.E5.91.98">获取标签成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.TagApi
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> getTagUsers(int tagId) throws WeixinException {
		return tagApi.getTagUsers(tagId);
	}

	/**
	 * 新增标签成员(标签对管理组可见且未加锁，成员属于管理组管辖范围)<br>
	 * <font color="red">若部分userid非法，则在text中体现</font>
	 * 
	 * @param tagId
	 *            标签ID
	 * @param userIds
	 *            成员ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.A2.9E.E5.8A.A0.E6.A0.87.E7.AD.BE.E6.88.90.E5.91.98">新增标签成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.TagApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult addTagUsers(int tagId, List<String> userIds)
			throws WeixinException {
		return tagApi.addTagUsers(tagId, userIds);
	}

	/**
	 * 删除标签成员(标签对管理组可见且未加锁，成员属于管理组管辖范围)<br>
	 * <font color="red">若部分userid非法，则在text中体现</font>
	 * 
	 * @param tagId
	 *            标签ID
	 * @param userIds
	 *            成员ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.88.A0.E9.99.A4.E6.A0.87.E7.AD.BE.E6.88.90.E5.91.98">删除标签成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.TagApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult deleteTagUsers(int tagId, List<String> userIds)
			throws WeixinException {
		return tagApi.deleteTagUsers(tagId, userIds);
	}

	/**
	 * 获取微信服务器IP地址
	 * 
	 * @return IP地址
	 * @see com.foxinmy.weixin4j.qy.api.HelperApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%9B%9E%E8%B0%83%E6%A8%A1%E5%BC%8F#.E8.8E.B7.E5.8F.96.E5.BE.AE.E4.BF.A1.E6.9C.8D.E5.8A.A1.E5.99.A8.E7.9A.84ip.E6.AE.B5">获取IP地址</a>
	 * @throws WeixinException
	 */
	public List<String> getcallbackip() throws WeixinException {
		return helperApi.getcallbackip();
	}

	/**
	 * 获取企业号某个应用的基本信息，包括头像、昵称、帐号类型、认证类型、可见范围等信息
	 * 
	 * @param agentid
	 *            授权方应用id
	 * @return 应用信息
	 * @see com.foxinmy.weixin4j.qy.model.AgentInfo
	 * @see com.foxinmy.weixin4j.qy.api.AgentApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%BC%81%E4%B8%9A%E5%8F%B7%E5%BA%94%E7%94%A8">企业号应用的信息</a>
	 * @throws WeixinException
	 */
	public AgentInfo getAgent(int agentid) throws WeixinException {
		return agentApi.getAgent(agentid);
	}

	/**
	 * 设置企业应用的选项设置信息，如：地理位置上报等
	 * 
	 * @param agentSet
	 *            设置参数
	 * @see com.foxinmy.weixin4j.qy.model.AgentSetter
	 * @see com.foxinmy.weixin4j.qy.api.AgentApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%AE%BE%E7%BD%AE%E4%BC%81%E4%B8%9A%E5%8F%B7%E5%BA%94%E7%94%A8">设置企业号信息</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult setAgent(AgentSetter agentSet) throws WeixinException {
		return agentApi.setAgent(agentSet);
	}

	/**
	 * 获取应用概况列表
	 * 
	 * @see com.foxinmy.weixin4j.qy.model.AgentOverview
	 * @see com.foxinmy.weixin4j.qy.api.AgentApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%BA%94%E7%94%A8%E6%A6%82%E5%86%B5%E5%88%97%E8%A1%A8">获取应用概况</a>
	 * @return 应用概况列表
	 * @throws WeixinException
	 */
	public List<AgentOverview> listAgentOverview() throws WeixinException {
		return agentApi.listAgentOverview();
	}

	/**
	 * 批量邀请成员关注
	 * 
	 * @param parameter
	 *            成员ID,标签ID,部门ID
	 * @param callback
	 *            接收任务执行结果的回调地址等信息
	 * @param tips
	 *            推送到微信上的提示语（只有认证号可以使用）。当使用微信推送时，该字段默认为“请关注XXX企业号”，邮件邀请时，该字段无效。
	 * @return 异步任务id，最大长度为64字符
	 * @see com.foxinmy.weixin4j.qy.model.IdParameter
	 * @see com.foxinmy.weixin4j.qy.model.Callback
	 * @see com.foxinmy.weixin4j.qy.api.BatchApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E9.82.80.E8.AF.B7.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8">邀请成员关注</a>
	 * @throws WeixinException
	 */
	public String inviteuser(IdParameter parameter, Callback callback,
			String tips) throws WeixinException {
		return batchApi.inviteuser(parameter, callback, tips);
	}

	/**
	 * 批量更新成员,本接口以userid为主键，增量更新企业号通讯录成员。
	 * <p>
	 * 1.模板中的部门需填写部门ID，多个部门用分号分隔，部门ID必须为数字</br>
	 * 2.文件中存在、通讯录中也存在的成员，更新成员在文件中指定的字段值 </br> 3.文件中存在、通讯录中不存在的成员，执行添加操作</br>
	 * 4.通讯录中存在、文件中不存在的成员，保持不变</br>
	 * </p>
	 * 
	 * @param mediaId
	 *            带user信息的cvs文件上传后的media_id
	 * @param callback
	 *            接收任务执行结果的回调地址等信息
	 * @return 异步任务id，最大长度为64字符
	 * @see com.foxinmy.weixin4j.qy.model.Callback
	 * @see com.foxinmy.weixin4j.qy.api.BatchApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E5.A2.9E.E9.87.8F.E6.9B.B4.E6.96.B0.E6.88.90.E5.91.98">批量更新成员</a>
	 * @throws WeixinException
	 */
	public String syncuser(String mediaId, Callback callback)
			throws WeixinException {
		return batchApi.syncuser(mediaId, callback);
	}

	/**
	 * 批量覆盖成员,本接口以userid为主键，全量覆盖企业号通讯录成员，任务完成后企业号通讯录成员与提交的文件完全保持一致。
	 * <p>
	 * 1.模板中的部门需填写部门ID，多个部门用分号分隔，部门ID必须为数字</br> 2.文件中存在、通讯录中也存在的成员，完全以文件为准</br>
	 * 3.文件中存在、通讯录中不存在的成员，执行添加操作</br>
	 * 4.通讯录中存在、文件中不存在的成员，执行删除操作。出于安全考虑，如果需要删除的成员多于50人，
	 * 且多于现有人数的20%以上，系统将中止导入并返回相应的错误码
	 * </p>
	 * 
	 * @param mediaId
	 *            带userid信息的cvs文件上传后的media_id
	 * @param callback
	 *            接收任务执行结果的回调地址等信息
	 * @return 异步任务id，最大长度为64字符
	 * @see com.foxinmy.weixin4j.qy.model.Callback
	 * @see com.foxinmy.weixin4j.qy.api.BatchApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E5.85.A8.E9.87.8F.E8.A6.86.E7.9B.96.E6.88.90.E5.91.98">批量覆盖成员</a>
	 * @throws WeixinException
	 */
	public String replaceuser(String mediaId, Callback callback)
			throws WeixinException {
		return batchApi.replaceuser(mediaId, callback);
	}

	/**
	 * 批量上传成员
	 * 
	 * @param users
	 *            成员列表
	 * @see com.foxinmy.weixin4j.qy.api.MediaApi
	 * @see com.foxinmy.weixin4j.qy.api.BatchApi
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy#syncuser(String,Callback)}
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy#replaceuser(String,Callback)}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E9.80.9A.E8.AE.AF.E5.BD.95.E6.9B.B4.E6.96.B0">批量任务</a>
	 * @return 上传后的mediaId
	 * @throws WeixinException
	 */
	public String batchUploadUsers(List<User> users) throws WeixinException {
		return mediaApi.batchUploadUsers(users);
	}

	/**
	 * 批量覆盖部门,本接口以partyid为键，全量覆盖企业号通讯录组织架构，任务完成后企业号通讯录组织架构与提交的文件完全保持一致。
	 * <p>
	 * 1.文件中存在、通讯录中也存在的部门，执行修改操作</br> 2.文件中存在、通讯录中不存在的部门，执行添加操作</br>
	 * 3.文件中不存在、通讯录中存在的部门，当部门为空时，执行删除操作</br>
	 * 4.CSV文件中，部门名称、部门ID、父部门ID为必填字段，部门ID必须为数字；排序为可选字段，置空或填0不修改排序
	 * </p>
	 * 
	 * @param mediaId
	 *            带partyid信息的cvs文件上传后的media_id
	 * @param callback
	 *            接收任务执行结果的回调地址等信息
	 * @return 异步任务id，最大长度为64字符
	 * @see com.foxinmy.weixin4j.qy.model.Callback
	 * @see com.foxinmy.weixin4j.qy.api.BatchApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E5.85.A8.E9.87.8F.E8.A6.86.E7.9B.96.E9.83.A8.E9.97.A8">批量覆盖部门</a>
	 * @throws WeixinException
	 */
	public String replaceparty(String mediaId, Callback callback)
			throws WeixinException {
		return batchApi.replaceparty(mediaId, callback);
	}

	/**
	 * 获取异步任务执行的结果
	 * 
	 * @param jobId
	 *            任务ID
	 * @return 效果信息
	 * @see com.foxinmy.weixin4j.qy.model.BatchResult
	 * @see com.foxinmy.weixin4j.qy.api.BatchApi
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E8.8E.B7.E5.8F.96.E5.BC.82.E6.AD.A5.E4.BB.BB.E5.8A.A1.E7.BB.93.E6.9E.9C">获取异步任务执行结果</a>
	 * @throws WeixinException
	 */
	public BatchResult getresult(String jobId) throws WeixinException {
		return batchApi.getresult(jobId);
	}
}
