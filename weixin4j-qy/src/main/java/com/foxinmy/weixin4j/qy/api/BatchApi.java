package com.foxinmy.weixin4j.qy.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.BatchResult;
import com.foxinmy.weixin4j.qy.model.Callback;
import com.foxinmy.weixin4j.qy.model.IdParameter;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 批量异步任务API
 * <p>
 * 异步任务接口用于大批量数据的处理，提交后接口即返回，企业号会在后台继续执行任务。执行完成后，通过任务事件通知企业获取结果
 * </p>
 * 
 * @className BatchApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月30日
 * @since JDK 1.6
 * @see <a href="https://work.weixin.qq.com/api/doc#10138">批量任务</a>
 */
public class BatchApi extends QyApi {

	private final TokenManager tokenManager;

	public BatchApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
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
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E9.82.80.E8.AF.B7.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8">邀请成员关注</a>
	 * @throws WeixinException
	 */
	public String inviteUser(IdParameter parameter, Callback callback,
			String tips) throws WeixinException {
		String batch_inviteuser_uri = getRequestUri("batch_inviteuser_uri");
		Token token = tokenManager.getCache();
		JSONObject obj = new JSONObject();
		obj.putAll(parameter.getParameter());
		obj.put("callback", callback);
		obj.put("invite_tips", tips);
		WeixinResponse response = weixinExecutor.post(
				String.format(batch_inviteuser_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJson().getString("jobid");
	}

	/**
	 * 批量更新成员,本接口以userid为主键，增量更新企业号通讯录成员。
	 * 
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
	 * @see {@link MediaApi#batchUploadUsers(java.util.List)}
	 * @see com.foxinmy.weixin4j.qy.model.Callback
	 * @see <a href="https://work.weixin.qq.com/api/doc#10138/增量更新成员">批量更新成员</a>
	 * @throws WeixinException
	 */
	public String syncUser(String mediaId, Callback callback)
			throws WeixinException {
		String batch_syncuser_uri = getRequestUri("batch_syncuser_uri");
		return batch(batch_syncuser_uri, mediaId, callback);
	}

	private String batch(String batchUrl, String mediaId, Callback callback)
			throws WeixinException {
		Token token = tokenManager.getCache();
		JSONObject obj = new JSONObject();
		obj.put("media_id", mediaId);
		obj.put("callback", callback);
		WeixinResponse response = weixinExecutor.post(
				String.format(batchUrl, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJson().getString("jobid");
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
	 * @see {@link MediaApi#batchUploadUsers(java.util.List)}
	 * @see com.foxinmy.weixin4j.qy.model.Callback
	 * @see <a href="https://work.weixin.qq.com/api/doc#10138/全量覆盖成员">批量覆盖成员</a>
	 * @throws WeixinException
	 */
	public String replaceUser(String mediaId, Callback callback)
			throws WeixinException {
		String batch_replaceuser_uri = getRequestUri("batch_replaceuser_uri");
		return batch(batch_replaceuser_uri, mediaId, callback);
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
	 * @see {@link MediaApi#batchUploadParties(java.util.List)}
	 * @see com.foxinmy.weixin4j.qy.model.Callback
	 * @see <a href="https://work.weixin.qq.com/api/doc#10138/全量覆盖部门">批量覆盖部门</a>
	 * @throws WeixinException
	 */
	public String replaceParty(String mediaId, Callback callback)
			throws WeixinException {
		String batch_replaceparty_uri = getRequestUri("batch_replaceparty_uri");
		return batch(batch_replaceparty_uri, mediaId, callback);
	}

	/**
	 * 获取异步任务执行的结果
	 * 
	 * @param jobId
	 *            任务ID
	 * @return 执行结果对象
	 * @see com.foxinmy.weixin4j.qy.model.BatchResult
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E8.8E.B7.E5.8F.96.E5.BC.82.E6.AD.A5.E4.BB.BB.E5.8A.A1.E7.BB.93.E6.9E.9C">获取异步任务执行结果</a>
	 * @throws WeixinException
	 */
	public BatchResult getBatchResult(String jobId) throws WeixinException {
		Token token = tokenManager.getCache();
		String batch_getresult_uri = getRequestUri("batch_getresult_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				batch_getresult_uri, token.getAccessToken(), jobId));
		return response.getAsObject(new TypeReference<BatchResult>() {
		});
	}
}
