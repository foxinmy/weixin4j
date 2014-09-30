package com.foxinmy.weixin4j.api;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.api.token.TokenApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.MpArticle;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.msg.BaseResult;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 群发相关API
 * 
 * @className MassApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3">群发接口</a>
 * @see com.foxinmy.weixin4j.model.MpArticle
 */
public class MassApi extends BaseApi {

	private final TokenApi tokenApi;

	public MassApi(TokenApi tokenApi) {
		this.tokenApi = tokenApi;
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
	 */
	public String uploadArticle(List<MpArticle> articles)
			throws WeixinException {
		String article_upload_uri = ConfigUtil.getValue("article_upload_uri");
		Token token = tokenApi.getToken();
		JSONObject obj = new JSONObject();
		obj.put("articles", articles);
		Response response = request.post(
				String.format(article_upload_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJson().getString("media_id");
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3">高级群发</a>
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 */
	public String uploadVideo(String mediaId, String title, String desc)
			throws WeixinException {
		String video_upload_uri = ConfigUtil.getValue("video_upload_uri");
		Token token = tokenApi.getToken();
		JSONObject obj = new JSONObject();
		obj.put("media_id", mediaId);
		obj.put("title", title);
		obj.put("description", desc);
		Response response = request.post(
				String.format(video_upload_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJson().getString("media_id");
	}

	/**
	 * 分组群发
	 * <p>
	 * 在返回成功时,意味着群发任务提交成功,并不意味着此时群发已经结束,所以,仍有可能在后续的发送过程中出现异常情况导致用户未收到消息,
	 * 如消息有时会进行审核、服务器不稳定等,此外,群发任务一般需要较长的时间才能全部发送完毕
	 * </p>
	 * 
	 * @param jsonPara
	 *            json格式的参数
	 * @param groupId
	 *            分组ID
	 * @return 发送出去的消息ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E6.A0.B9.E6.8D.AE.E5.88.86.E7.BB.84.E8.BF.9B.E8.A1.8C.E7.BE.A4.E5.8F.91">分组群发</a>
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see {@link com.foxinmy.weixin4j.api.GroupApi#getGroupByOpenId(String)}
	 * @see {@link com.foxinmy.weixin4j.api.GroupApi#getGroups()}
	 */
	private String massByGroup(JSONObject jsonPara, String groupId)
			throws WeixinException {
		String mass_group_uri = ConfigUtil.getValue("mass_group_uri");
		Token token = tokenApi.getToken();
		Response response = request.post(
				String.format(mass_group_uri, token.getAccessToken()),
				jsonPara.toJSONString());

		return response.getAsJson().getString("msg_id");
	}

	/**
	 * openId群发
	 * 
	 * @param jsonPara
	 *            json格式的参数
	 * @param openIds
	 *            目标ID列表
	 * @return 发送出去的消息ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E6.A0.B9.E6.8D.AEOpenID.E5.88.97.E8.A1.A8.E7.BE.A4.E5.8F.91">openId群发</a>
	 * @see com.foxinmy.weixin4j.model.User
	 */
	private String massByOpenIds(JSONObject jsonPara, String... openIds)
			throws WeixinException {
		String mass_openid_uri = ConfigUtil.getValue("mass_openid_uri");
		Token token = tokenApi.getToken();
		Response response = request.post(
				String.format(mass_openid_uri, token.getAccessToken()),
				jsonPara.toJSONString());

		return response.getAsJson().getString("msg_id");
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
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.api.GroupApi#getGroupByOpenId(String)}
	 * @see {@link com.foxinmy.weixin4j.api.GroupApi#getGroups()}
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#massByGroup(JSONObject,String)}
	 */
	public String massByGroup(String mediaId, MediaType mediaType,
			String groupId) throws WeixinException {
		JSONObject jsonPara = new JSONObject();
		jsonPara.put("filter", new JSONObject().put("group_id", groupId));
		jsonPara.put(mediaType.name(), new JSONObject().put(
				mediaType == MediaType.text ? "content" : "media_id", mediaId));
		jsonPara.put("msgtype", mediaType.name());

		return massByGroup(jsonPara, groupId);
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
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#uploadNews(List)}
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#massByGroup(String,MediaType,String)}
	 */
	public String massArticleByGroup(List<MpArticle> articles, String groupId)
			throws WeixinException {
		String mediaId = uploadArticle(articles);

		return massByGroup(mediaId, MediaType.mpnews, groupId);
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
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#massByOpenIds(JSONObject,String...)}
	 */
	public String massByOpenIds(String mediaId, MediaType mediaType,
			String... openIds) throws WeixinException {
		JSONObject jsonPara = new JSONObject();
		jsonPara.put("touser", openIds);
		jsonPara.put(mediaType.name(), new JSONObject().put(
				mediaType == MediaType.text ? "content" : "media_id", mediaId));
		jsonPara.put("msgtype", mediaType.name());

		return massByOpenIds(jsonPara, openIds);
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
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.api.MediaApi#uploadNews(List)}
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#massByOpenIds(String,MediaType,String...)}
	 */
	public String massArticleByOpenIds(List<MpArticle> articles,
			String... openIds) throws WeixinException {
		String mediaId = uploadArticle(articles);

		return massByOpenIds(mediaId, MediaType.mpnews, openIds);
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
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#massByGroup(JSONObject, String)}
	 * @see {@link com.foxinmy.weixin4j.api.MassApi#massByOpenIds(JSONObject, String...)
	 */
	public BaseResult deleteMassNews(String msgid) throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("msgid", msgid);
		String mass_delete_uri = ConfigUtil.getValue("mass_delete_uri");
		Token token = tokenApi.getToken();
		Response response = request.post(
				String.format(mass_delete_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsResult();
	}
}
