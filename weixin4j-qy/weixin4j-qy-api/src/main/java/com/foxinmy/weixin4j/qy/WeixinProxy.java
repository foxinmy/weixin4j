package com.foxinmy.weixin4j.qy;

import java.util.List;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.qy.api.DepartApi;
import com.foxinmy.weixin4j.qy.api.HelperApi;
import com.foxinmy.weixin4j.qy.api.TagApi;
import com.foxinmy.weixin4j.qy.api.UserApi;
import com.foxinmy.weixin4j.qy.model.Department;
import com.foxinmy.weixin4j.qy.model.Tag;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.type.InviteType;
import com.foxinmy.weixin4j.qy.type.UserStatus;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.type.AccountType;

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
	private final DepartApi departApi;
	private final UserApi userApi;
	private final TagApi tagApi;
	private final HelperApi helperApi;

	/**
	 * 默认采用文件存放Token信息
	 */
	public WeixinProxy() {
		this(new FileTokenHolder(new WeixinTokenCreator(AccountType.QY)));
	}

	/**
	 * appid,appsecret
	 * 
	 * @param corpid
	 * @param corpsecret
	 */
	public WeixinProxy(String corpid, String corpsecret) {
		this(new FileTokenHolder(new WeixinTokenCreator(corpid, corpsecret,
				AccountType.QY)));
	}

	/**
	 * TokenHolder对象
	 * 
	 * @param tokenHolder
	 */
	public WeixinProxy(TokenHolder tokenHolder) {
		this.departApi = new DepartApi(tokenHolder);
		this.userApi = new UserApi(tokenHolder);
		this.tagApi = new TagApi(tokenHolder);
		this.helperApi = new HelperApi(tokenHolder);
	}

	/**
	 * 创建部门(根部门的parentid为1)
	 * 
	 * @param depart
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Department
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E5.88.9B.E5.BB.BA.E9.83.A8.E9.97.A8">创建部门说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.DepartApi
	 * @return 部门ID
	 * @throws WeixinException
	 */
	public int createDepart(Department depart) throws WeixinException {
		return departApi.createDepart(depart);
	}

	/**
	 * 更新部门(如果非必须的字段未指定 则不更新该字段之前的设置值)
	 * 
	 * @param depart
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Department
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E6.9B.B4.E6.96.B0.E9.83.A8.E9.97.A8">更新部门说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.DepartApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateDepart(Department depart) throws WeixinException {
		return departApi.updateDepart(depart);
	}

	/**
	 * 查询部门列表(以部门的order字段从小到大排列)
	 * 
	 * @param departId
	 *            部门ID。获取指定部门ID下的子部门
	 * @see com.foxinmy.weixin4j.qy.model.Department
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E5.88.97.E8.A1.A8">获取部门列表</a>
	 * @see com.foxinmy.weixin4j.qy.api.DepartApi
	 * @return 部门列表
	 * @throws WeixinException
	 */
	public List<Department> listDepart(int departId) throws WeixinException {
		return departApi.listDepart(departId);
	}

	/**
	 * 删除部门(不能删除根部门；不能删除含有子部门、成员的部门)
	 * 
	 * @param departId
	 *            部门ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E5.88.A0.E9.99.A4.E9.83.A8.E9.97.A8">删除部门说明</a>
	 * @see com.foxinmy.weixin4j.qy.api.DepartApi
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult deleteDepart(int departId) throws WeixinException {
		return departApi.deleteDepart(departId);
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
	 * @param departId
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
	public List<User> listUser(int departId, boolean fetchChild,
			UserStatus userStatus, boolean findDetail) throws WeixinException {
		return userApi.listUser(departId, fetchChild, userStatus, findDetail);
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
}
