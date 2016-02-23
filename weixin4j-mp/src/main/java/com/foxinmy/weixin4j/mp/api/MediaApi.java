package com.foxinmy.weixin4j.mp.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.apache.ByteArrayBody;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.InputStreamBody;
import com.foxinmy.weixin4j.http.apache.StringBody;
import com.foxinmy.weixin4j.http.entity.StringEntity;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.MediaCounter;
import com.foxinmy.weixin4j.model.MediaDownloadResult;
import com.foxinmy.weixin4j.model.MediaItem;
import com.foxinmy.weixin4j.model.MediaRecord;
import com.foxinmy.weixin4j.model.MediaUploadResult;
import com.foxinmy.weixin4j.model.Pageable;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.tuple.MpVideo;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.util.FileUtil;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.ObjectId;
import com.foxinmy.weixin4j.util.RegexUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.WeixinErrorUtil;

/**
 * 素材相关API
 * 
 * @className MediaApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.type.MediaType
 */
public class MediaApi extends MpApi {

	private final TokenHolder tokenHolder;

	public MediaApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
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
		String image_upload_uri = getRequestUri("image_upload_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(String.format(
				image_upload_uri, token.getAccessToken()),
				new FormBodyPart("media", new InputStreamBody(is,
						ContentType.IMAGE_JPG.getMimeType(), fileName)));
		return response.getAsJson().getString("url");
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html">高级群发</a>
	 * @see com.foxinmy.weixin4j.tuple.MpVideo
	 */
	public MpVideo uploadVideo(InputStream is, String fileName, String title,
			String description) throws WeixinException {
		MediaUploadResult uploadResult = uploadMedia(false, is, fileName);
		JSONObject obj = new JSONObject();
		obj.put("media_id", uploadResult.getMediaId());
		obj.put("title", title);
		obj.put("description", description);
		String video_upload_uri = getRequestUri("video_upload_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(video_upload_uri, token.getAccessToken()),
				obj.toJSONString());

		String mediaId = response.getAsJson().getString("media_id");
		return new MpVideo(mediaId);
	}

	/**
	 * 上传媒体文件:图片（image）、语音（voice）、视频(video)和缩略图（thumb） </br> <font
	 * color="red">此接口只包含图片、语音、缩略图、视频(临时)四种媒体类型的上传</font>
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param isMaterial
	 *            是否永久上传
	 * @param is
	 *            媒体数据流
	 * @param fileName
	 *            文件名
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/963fc70b80dc75483a271298a76a8d59.html">上传临时素材</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久素材</a>
	 * @see com.foxinmy.weixin4j.model.MediaUploadResult
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @throws WeixinException
	 */
	public MediaUploadResult uploadMedia(boolean isMaterial, InputStream is,
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
		MediaType mediaType;
		if (",bmp,png,jpeg,jpg,gif,"
				.contains(String.format(",%s,", suffixName))) {
			mediaType = MediaType.image;
		} else if (",mp3,wma,wav,amr,".contains(String.format(",%s,",
				suffixName))) {
			mediaType = MediaType.voice;
		} else if (",rm,rmvb,wmv,avi,mpg,mpeg,mp4,".contains(String.format(
				",%s,", suffixName))) {
			mediaType = MediaType.video;
		} else {
			throw new WeixinException("cannot handle mediaType:" + suffixName);
		}
		if (mediaType == MediaType.video && isMaterial) {
			throw new WeixinException(
					"please invoke uploadMaterialVideo method");
		}
		Token token = tokenHolder.getToken();
		WeixinResponse response = null;
		try {
			if (isMaterial) {
				String material_media_upload_uri = getRequestUri("material_media_upload_uri");
				response = weixinExecutor
						.post(String.format(material_media_upload_uri,
								token.getAccessToken()), new FormBodyPart(
								"media", new ByteArrayBody(content, mediaType
										.getContentType().getMimeType(),
										fileName)), new FormBodyPart("type",
								new StringBody(mediaType.name(), Consts.UTF_8)));
				return new MediaUploadResult(response.getAsJson().getString(
						"media_id"), mediaType, new Date());
			} else {
				String media_upload_uri = getRequestUri("media_upload_uri");
				response = weixinExecutor.post(String.format(media_upload_uri,
						token.getAccessToken(), mediaType.name()),
						new FormBodyPart("media", new InputStreamBody(
								new ByteArrayInputStream(content), mediaType
										.getContentType().getMimeType(),
								fileName)));
				JSONObject obj = response.getAsJson();
				return new MediaUploadResult(obj.getString("media_id"),
						obj.getObject("type", MediaType.class), new Date(
								obj.getLong("created_at") * 1000l));
				/*
				 * return response.getAsObject(new
				 * TypeReference<MediaUploadResult>() { });
				 */
			}
		} catch (UnsupportedEncodingException e) {
			throw new WeixinException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				;
			}
		}
	}

	/**
	 * 下载媒体素材
	 * 
	 * @param mediaId
	 *            媒体ID
	 * @param isMaterial
	 *            是否下载永久素材
	 * @return 媒体下载结果
	 * 
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.MediaDownloadResult
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/11/07b6b76a6b6e8848e855a435d5e34a5f.html">下载临时媒体素材</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html">下载永久媒体素材</a>
	 */
	public MediaDownloadResult downloadMedia(String mediaId, boolean isMaterial)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		try {
			HttpRequest request = null;
			if (isMaterial) {
				String material_media_download_uri = getRequestUri("material_media_download_uri");
				request = new HttpRequest(HttpMethod.POST, String.format(
						material_media_download_uri, token.getAccessToken()));
				request.setEntity(new StringEntity(String.format(
						"{\"media_id\":\"%s\"}", mediaId)));
			} else {
				String meida_download_uri = getRequestUri("meida_download_uri");
				request = new HttpRequest(HttpMethod.GET, String.format(
						meida_download_uri, token.getAccessToken(), mediaId));
			}
			HttpParams params = weixinExecutor.getExecuteParams();
			request.setParams(params);
			HttpResponse response = weixinExecutor.getExecuteClient().execute(
					request);
			byte[] content = IOUtil.toByteArray(response.getBody());
			HttpHeaders headers = response.getHeaders();
			String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);
			String disposition = headers
					.getFirst(HttpHeaders.CONTENT_DISPOSITION);
			if (contentType
					.contains(ContentType.APPLICATION_JSON.getMimeType())
					|| (disposition != null && disposition.indexOf(".json") > 0)) {
				JsonResult jsonResult = JSON.parseObject(content, 0,
						content.length, Consts.UTF_8.newDecoder(),
						JsonResult.class);
				if (jsonResult.getCode() != 0) {
					if (StringUtil.isBlank(jsonResult.getDesc())) {
						jsonResult.setDesc(WeixinErrorUtil.getText(Integer
								.toString(jsonResult.getCode())));
					}
					throw new WeixinException(Integer.toString(jsonResult
							.getCode()), jsonResult.getDesc());
				}
			}
			String fileName = RegexUtil
					.regexFileNameFromContentDispositionHeader(disposition);
			if (StringUtil.isBlank(fileName)) {
				fileName = String.format("%s.%s", mediaId,
						contentType.split("/")[1]);
			}
			return new MediaDownloadResult(content,
					ContentType.create(contentType), fileName);
		} catch (IOException e) {
			throw new WeixinException("I/O Error on getBody", e);
		} catch (HttpClientException e) {
			throw new WeixinException(e);
		}
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 */
	public String uploadMaterialArticle(List<MpArticle> articles)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_article_upload_uri = getRequestUri("material_article_upload_uri");
		JSONObject obj = new JSONObject();
		obj.put("articles", articles);
		WeixinResponse response = weixinExecutor.post(
				String.format(material_article_upload_uri,
						token.getAccessToken()), obj.toJSONString());

		return response.getAsJson().getString("media_id");
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
	 */
	public List<MpArticle> downloadArticle(String mediaId)
			throws WeixinException {
		MediaDownloadResult result = downloadMedia(mediaId, true);
		byte[] content = result.getContent();
		JSONObject obj = JSON.parseObject(content, 0, content.length,
				Consts.UTF_8.newDecoder(), JSONObject.class);
		return JSON.parseArray(obj.getString("news_item"), MpArticle.class);
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
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/19a59cba020d506e767360ca1be29450.html">更新永久图文素材</a>
	 */
	public JsonResult updateMaterialArticle(String mediaId, int index,
			List<MpArticle> articles) throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_article_update_uri = getRequestUri("material_article_update_uri");
		JSONObject obj = new JSONObject();
		obj.put("articles", articles);
		obj.put("media_id", mediaId);
		obj.put("index", index);
		WeixinResponse response = weixinExecutor.post(
				String.format(material_article_update_uri,
						token.getAccessToken()), obj.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 删除永久媒体素材
	 * 
	 * @param mediaId
	 *            媒体素材的media_id
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/e66f61c303db51a6c0f90f46b15af5f5.html">删除永久媒体素材</a>
	 */
	public JsonResult deleteMaterialMedia(String mediaId)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_del_uri = getRequestUri("material_media_del_uri");
		JSONObject obj = new JSONObject();
		obj.put("media_id", mediaId);
		WeixinResponse response = weixinExecutor.post(
				String.format(material_media_del_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久媒体素材</a>
	 * @throws WeixinException
	 */
	public String uploadMaterialVideo(InputStream is, String fileName,
			String title, String introduction) throws WeixinException {
		if (StringUtil.isBlank(fileName)) {
			fileName = ObjectId.get().toHexString();
		}
		if (StringUtil.isBlank(FileUtil.getFileExtension(fileName))) {
			fileName = String.format("%s.mp4", fileName);
		}
		String material_media_upload_uri = getRequestUri("material_media_upload_uri");
		Token token = tokenHolder.getToken();
		try {
			JSONObject description = new JSONObject();
			description.put("title", title);
			description.put("introduction", introduction);
			WeixinResponse response = weixinExecutor.post(
					String.format(material_media_upload_uri,
							token.getAccessToken()),
					new FormBodyPart("media", new InputStreamBody(is,
							ContentType.VIDEO_MPEG4.getMimeType(), fileName)),
					new FormBodyPart("description", new StringBody(description
							.toJSONString(), Consts.UTF_8)));
			return response.getAsJson().getString("media_id");
		} catch (UnsupportedEncodingException e) {
			throw new WeixinException(e);
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
	 * 获取永久媒体素材的总数</br> .图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
	 * 
	 * @return 总数对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.model.MediaCounter
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/16/8cc64f8c189674b421bee3ed403993b8.html">获取素材总数</a>
	 */
	public MediaCounter countMaterialMedia() throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_count_uri = getRequestUri("material_media_count_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				material_media_count_uri, token.getAccessToken()));

		return response.getAsObject(new TypeReference<MediaCounter>() {
		});
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
	 * @see com.foxinmy.weixin4j.model.MediaRecord
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.model.MediaItem
	 * @see com.foxinmy.weixin4j.model.Pageable
	 * @see com.foxinmy.weixin4j.model.Pagedata
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html">获取素材列表</a>
	 */
	public MediaRecord listMaterialMedia(MediaType mediaType, Pageable pageable)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_list_uri = getRequestUri("material_media_list_uri");
		JSONObject obj = new JSONObject();
		obj.put("type", mediaType.name());
		obj.put("offset", pageable.getOffset());
		obj.put("count", pageable.getPageSize());
		WeixinResponse response = weixinExecutor.post(
				String.format(material_media_list_uri, token.getAccessToken()),
				obj.toJSONString());
		MediaRecord mediaRecord = null;
		if (mediaType == MediaType.news) {
			mediaRecord = JSON.parseObject(response.getAsString(),
					MediaRecord.class, new ExtraProcessor() {
						@Override
						public void processExtra(Object object, String key,
								Object value) {
							if (key.equals("content")) {
								((MediaItem) object).setArticles(JSON
										.parseArray(((JSONObject) value)
												.getString("news_item"),
												MpArticle.class));
							}
						}
					});
		} else {
			mediaRecord = response
					.getAsObject(new TypeReference<MediaRecord>() {
					});
		}
		mediaRecord.setMediaType(mediaType);
		mediaRecord.setPageable(pageable);
		return mediaRecord;
	}

	/**
	 * 获取全部的媒体素材
	 * 
	 * @param mediaType
	 *            媒体类型
	 * @return 素材列表
	 * @see {@link #listMaterialMedia(MediaType, Pageable)}
	 * @throws WeixinException
	 */
	public List<MediaItem> listAllMaterialMedia(MediaType mediaType)
			throws WeixinException {
		Pageable pageable = new Pageable(1, 20);
		List<MediaItem> mediaList = new ArrayList<MediaItem>();
		MediaRecord mediaRecord = null;
		for (;;) {
			mediaRecord = listMaterialMedia(mediaType, pageable);
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
}
