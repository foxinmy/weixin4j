package com.foxinmy.weixin4j.qy.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.MimeType;
import com.foxinmy.weixin4j.http.apache.ByteArrayBody;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.InputStreamBody;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.media.MediaCounter;
import com.foxinmy.weixin4j.model.media.MediaDownloadResult;
import com.foxinmy.weixin4j.model.media.MediaItem;
import com.foxinmy.weixin4j.model.media.MediaRecord;
import com.foxinmy.weixin4j.model.media.MediaUploadResult;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.qy.model.Callback;
import com.foxinmy.weixin4j.qy.model.Party;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.FileUtil;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.ObjectId;
import com.foxinmy.weixin4j.util.RegexUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 媒体相关API
 *
 * @className MediaApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.type.MediaType
 */
public class MediaApi extends QyApi {

	private final TokenManager tokenManager;

	public MediaApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 上传图文消息内的图片:用于上传图片到企业号服务端，接口返回图片url，请注意，该url仅可用于图文消息的发送，
	 * 且每个企业每天最多只能上传100张图片。
	 *
	 * @param is
	 *            图片数据
	 * @param fileName
	 *            文件名
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E5%9B%BE%E6%96%87%E6%B6%88%E6%81%AF%E5%86%85%E7%9A%84%E5%9B%BE%E7%89%87">上传图文消息内的图片</a>
	 * @return 图片url
	 * @throws WeixinException
	 */
	public String uploadImage(InputStream is, String fileName)
			throws WeixinException {
		if (StringUtil.isBlank(fileName)) {
			fileName = ObjectId.get().toHexString();
		}
		if (StringUtil.isBlank(FileUtil.getFileExtension(fileName))) {
			fileName = String.format("%s.jpg", fileName);
		}
		String media_uploadimg_uri = getRequestUri("media_uploadimg_uri");
		MimeType mimeType = new MimeType("image",
				FileUtil.getFileExtension(fileName));
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(media_uploadimg_uri, token.getAccessToken()),
				new FormBodyPart("media", new InputStreamBody(is, mimeType
						.toString(), fileName)));
		return response.getAsJson().getString("url");
	}

	/**
	 * 上传媒体文件:分别有图片（image）、语音（voice）、视频（video），普通文件(file)
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 *
	 * @param agentid
	 *            企业应用ID(<font color="red">大于0时视为上传永久媒体文件</font>)
	 * @param is
	 *            媒体数据流
	 * @param fileName
	 *            文件名
	 * @return 上传到微信服务器返回的媒体标识
	 * @see com.foxinmy.weixin4j.model.media.MediaUploadResult
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10112">上传临时素材文件说明</a>
	 * @see <a href=
	 *      "http://http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">上传永久素材文件说明</a>
	 * @throws WeixinException
	 */
	public MediaUploadResult uploadMedia(int agentid, InputStream is,
			String fileName) throws WeixinException {
		byte[] content;
		try {
			content = IOUtil.toByteArray(is);
		} catch (IOException e) {
			throw new WeixinException(e);
		}
		if (StringUtil.isBlank(fileName)) {
			fileName = ObjectId.get().toHexString();
		}
		String suffixName = FileUtil.getFileExtension(fileName);
		if (StringUtil.isBlank(suffixName)) {
			suffixName = FileUtil
					.getFileType(new ByteArrayInputStream(content));
			fileName = String.format("%s.%s", fileName, suffixName);
		}
		MediaType mediaType = MediaType.file;
		if (",bmp,png,jpeg,jpg,gif,"
				.contains(String.format(",%s,", suffixName))) {
			mediaType = MediaType.image;
		} else if (",mp3,wma,wav,amr,".contains(String.format(",%s,",
				suffixName))) {
			mediaType = MediaType.voice;
		} else if (",rm,rmvb,wmv,avi,mpg,mpeg,mp4,".contains(String.format(
				",%s,", suffixName))) {
			mediaType = MediaType.video;
		}
		Token token = tokenManager.getCache();
		try {
			WeixinResponse response = null;
			if (agentid > 0) {
				String material_media_upload_uri = getRequestUri("material_media_upload_uri");
				response = weixinExecutor.post(String.format(
						material_media_upload_uri, token.getAccessToken(),
						mediaType.name(), agentid), new FormBodyPart("media",
						new ByteArrayBody(content, mediaType.getMimeType()
								.toString(), fileName)));
				JSONObject obj = response.getAsJson();
				return new MediaUploadResult(obj.getString("media_id"),
						mediaType, new Date(), obj.getString("url"));
			} else {
				String media_upload_uri = getRequestUri("media_upload_uri");
				response = weixinExecutor.post(String.format(media_upload_uri,
						token.getAccessToken(), mediaType.name()),
						new FormBodyPart("media", new ByteArrayBody(content,
								mediaType.getMimeType().toString(), fileName)));
				JSONObject obj = response.getAsJson();
				return new MediaUploadResult(obj.getString("media_id"),
						obj.getObject("type", MediaType.class), new Date(
								obj.getLong("created_at") * 1000l),
						obj.getString("url"));
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}

	/**
	 * 下载媒体文件
	 *
	 * @param agentid
	 *            企业应用Id(<font color="red">大于0时视为获取永久媒体文件</font>)
	 * @param mediaId
	 *            媒体ID
	 * @return 媒体下载结果
	 * @see com.foxinmy.weixin4j.model.media.MediaDownloadResult
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10115">获取临时媒体说明</a>
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">获取永久媒体说明</a>
	 * @throws WeixinException
	 */
	public MediaDownloadResult downloadMedia(int agentid, String mediaId)
			throws WeixinException {
		Token token = tokenManager.getCache();
		HttpRequest request = null;
		if (agentid > 0) {
			String material_media_download_uri = getRequestUri("material_media_download_uri");
			request = new HttpRequest(HttpMethod.GET, String.format(
					material_media_download_uri, token.getAccessToken(),
					mediaId, agentid));
		} else {
			String media_download_uri = getRequestUri("media_download_uri");
			request = new HttpRequest(HttpMethod.GET, String.format(
					media_download_uri, token.getAccessToken(), mediaId));
		}
		HttpResponse response = weixinExecutor.doRequest(request);
		HttpHeaders headers = response.getHeaders();
		String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);
		String disposition = headers.getFirst(HttpHeaders.CONTENT_DISPOSITION);
		String fileName = RegexUtil
				.regexFileNameFromContentDispositionHeader(disposition);
		if (StringUtil.isBlank(fileName)) {
			fileName = String.format("%s.%s", mediaId,
					contentType.split("/")[1]);
		}
		return new MediaDownloadResult(response.getContent(),
				ContentType.create(contentType), fileName);
	}

	/**
	 * 上传永久图文素材
	 * <p>
	 * 、新增的永久素材也可以在公众平台官网素材管理模块中看到,永久素材的数量是有上限的，请谨慎新增。图文消息素材和图片素材的上限为5000，
	 * 其他类型为1000
	 * </P>
	 *
	 * @param agentid
	 *            企业应用的id
	 * @param articles
	 *            图文列表
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">上传永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 */
	public String uploadMaterialArticle(int agentid, List<MpArticle> articles)
			throws WeixinException {
		Token token = tokenManager.getCache();
		String material_article_upload_uri = getRequestUri("material_article_upload_uri");
		JSONObject obj = new JSONObject();
		obj.put("agentid", agentid);
		JSONObject news = new JSONObject();
		news.put("articles", articles);
		obj.put("mpnews", news);
		WeixinResponse response = weixinExecutor.post(
				String.format(material_article_upload_uri,
						token.getAccessToken()), obj.toJSONString());

		return response.getAsJson().getString("media_id");
	}

	/**
	 * 删除永久媒体素材
	 *
	 * @param agentid
	 *            企业应用ID
	 * @param mediaId
	 *            媒体素材的media_id
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%88%A0%E9%99%A4%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">删除永久媒体素材</a>
	 */
	public ApiResult deleteMaterialMedia(int agentid, String mediaId)
			throws WeixinException {
		Token token = tokenManager.getCache();
		String material_media_del_uri = getRequestUri("material_media_del_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				material_media_del_uri, token.getAccessToken(), mediaId,
				agentid));
		return response.getAsResult();
	}

	/**
	 * 下载永久图文素材
	 *
	 * @param agentid
	 *            企业应用ID
	 * @param mediaId
	 *            媒体素材的media_id
	 * @return 图文列表
	 * @throws WeixinException
	 * @see {@link #downloadMedia(int, String)}
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 */
	public List<MpArticle> downloadArticle(int agentid, String mediaId)
			throws WeixinException {
		MediaDownloadResult result = downloadMedia(agentid, mediaId);
		byte[] content = result.getContent();
		JSONObject obj = JSON.parseObject(content, 0, content.length,
				Consts.UTF_8.newDecoder(), JSONObject.class);
		return JSON.parseArray(obj.getJSONObject("mpnews")
				.getString("articles"), MpArticle.class);
	}

	/**
	 * 修改永久图文素材
	 *
	 * @param agentid
	 *            企业应用的id
	 * @param mediaId
	 *            上传后的media_id
	 * @param articles
	 *            图文列表
	 * @return 操作结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BF%AE%E6%94%B9%E6%B0%B8%E4%B9%85%E5%9B%BE%E6%96%87%E7%B4%A0%E6%9D%90">修改永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 */
	public String updateMaterialArticle(int agentid, String mediaId,
			List<MpArticle> articles) throws WeixinException {
		Token token = tokenManager.getCache();
		String material_article_update_uri = getRequestUri("material_article_update_uri");
		JSONObject obj = new JSONObject();
		obj.put("agentid", agentid);
		JSONObject news = new JSONObject();
		news.put("articles", articles);
		obj.put("mpnews", news);
		obj.put("media_id", mediaId);
		WeixinResponse response = weixinExecutor.post(
				String.format(material_article_update_uri,
						token.getAccessToken()), obj.toJSONString());

		return response.getAsJson().getString("media_id");
	}

	/**
	 * 获取永久媒体素材的总数
	 *
	 * @param agentid
	 *            企业应用id
	 * @return 总数对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.media.MediaCounter
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%B4%A0%E6%9D%90%E6%80%BB%E6%95%B0">获取素材总数</a>
	 */
	public MediaCounter countMaterialMedia(int agentid) throws WeixinException {
		Token token = tokenManager.getCache();
		String material_media_count_uri = getRequestUri("material_media_count_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				material_media_count_uri, token.getAccessToken(), agentid));
		JSONObject result = response.getAsJson();
		MediaCounter counter = JSON.toJavaObject(result, MediaCounter.class);
		counter.setNewsCount(result.getIntValue("mpnews_count"));
		return counter;
	}

	/**
	 * 获取媒体素材记录列表
	 *
	 * @param agentid
	 *            企业应用ID
	 * @param mediaType
	 *            素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）、文件（file）
	 * @param pageable
	 *            分页数据
	 * @return 媒体素材的记录对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.media.MediaRecord
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.model.media.MediaItem
	 * @see com.foxinmy.weixin4j.model.paging.Pageable
	 * @see com.foxinmy.weixin4j.model.paging.Pagedata
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%B4%A0%E6%9D%90%E5%88%97%E8%A1%A8">获取素材列表</a>
	 */
	public MediaRecord listMaterialMedia(int agentid, MediaType mediaType,
			Pageable pageable) throws WeixinException {
		Token token = tokenManager.getCache();
		String material_media_list_uri = getRequestUri("material_media_list_uri");
		JSONObject obj = new JSONObject();
		obj.put("agentid", agentid);
		obj.put("type",
				mediaType == MediaType.news ? "mpnews" : mediaType.name());
		obj.put("offset", pageable.getOffset());
		obj.put("count", pageable.getPageSize());
		WeixinResponse response = weixinExecutor.post(
				String.format(material_media_list_uri, token.getAccessToken()),
				obj.toJSONString());
		obj = response.getAsJson();

		obj.put("items", obj.remove("itemlist"));
		MediaRecord mediaRecord = JSON.toJavaObject(obj, MediaRecord.class);
		mediaRecord.setMediaType(mediaType);
		mediaRecord.setPageable(pageable);
		return mediaRecord;
	}

	/**
	 * 获取全部的媒体素材
	 *
	 * @param agentid
	 *            企业应用id
	 * @param mediaType
	 *            媒体类型
	 * @return 素材列表
	 * @see {@link #listMaterialMedia(int,MediaType, Pageable)}
	 * @throws WeixinException
	 */
	public List<MediaItem> listAllMaterialMedia(int agentid, MediaType mediaType)
			throws WeixinException {
		Pageable pageable = new Pageable(1, 20);
		List<MediaItem> mediaList = new ArrayList<MediaItem>();
		MediaRecord mediaRecord = null;
		for (;;) {
			mediaRecord = listMaterialMedia(agentid, mediaType, pageable);
			if (mediaRecord.getItems() == null
					|| mediaRecord.getItems().isEmpty()) {
				break;
			}
			mediaList.addAll(mediaRecord.getItems());
			if (!mediaRecord.getPagedata().hasNext()) {
				break;
			}
			pageable = pageable.next();
		}
		return mediaList;
	}

	/**
	 * 批量上传成员
	 *
	 * @param users
	 *            成员列表
	 * @see {@link BatchApi#syncUser(String,Callback)}
	 * @see {@link BatchApi#replaceUser(String,Callback)}
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10138">批量任务</a>
	 * @return 上传后的mediaId
	 * @throws WeixinException
	 */
	public String batchUploadUsers(List<User> users) throws WeixinException {
		return batchUpload("batch_syncuser.cvs", users);
	}

	/**
	 * 批量上传部门
	 *
	 * @param parties
	 *            部门列表
	 * @see {@link BatchApi#replaceParty(String,Callback)}
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10138">批量任务</a>
	 * @return 上传后的mediaId
	 * @throws WeixinException
	 */
	public String batchUploadParties(List<Party> parties)
			throws WeixinException {
		return batchUpload("batch_replaceparty.cvs", parties);
	}

	private <T> String batchUpload(String batchName, List<T> models)
			throws WeixinException {
		StringWriter writer = new StringWriter();
		try {
			JSONObject csvObj = JSON.parseObject(weixinBundle().getString(
					batchName));
			JSONArray columns = csvObj.getJSONArray("column");
			writer.write(csvObj.getString("header"));
			final Map<String, Object> column = new LinkedHashMap<String, Object>();
			for (Object col : columns) {
				column.put(col.toString(), "");
			}
			writer.write("\r\n");
			for (T model : models) {
				JSON.toJSONString(model, new PropertyFilter() {
					@Override
					public boolean apply(Object object, String name,
							Object value) {
						if (column.containsKey(name)) {
							if (value instanceof Collection) {
								column.put(name,
										StringUtil.join(((Collection<?>) value)
												.iterator(), ';'));
							} else {
								column.put(name, value);
							}
						}
						return true;
					}
				});
				writer.write(StringUtil.join(column.values(), ','));
				writer.write("\r\n");
			}
			return uploadMedia(
					0,
					new ByteArrayInputStream(writer.getBuffer().toString()
							.getBytes(Consts.UTF_8)), batchName).getMediaId();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				;
			}
		}
	}
}
