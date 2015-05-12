package com.foxinmy.weixin4j.mp.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.PartParameter;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.MediaCounter;
import com.foxinmy.weixin4j.mp.model.MediaItem;
import com.foxinmy.weixin4j.mp.model.MediaRecord;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.FileUtil;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 素材相关API
 * 
 * @className MediaApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.type.MediaType
 */
public class MediaApi extends MpApi {

	private final TokenHolder tokenHolder;

	public MediaApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param file
	 *            文件对象
	 * @param isMaterial
	 *            是否永久上传
	 * @return 上传到微信服务器返回的媒体标识
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(File, MediaType)}
	 * @throws WeixinException
	 * @throws IOException
	 */
	public String uploadMedia(File file, boolean isMaterial)
			throws WeixinException, IOException {
		String mediaTypeKey = IOUtil.getExtension(file.getName());
		if (StringUtil.isBlank(mediaTypeKey)) {
			mediaTypeKey = FileUtil.getFileType(file);
		}
		MediaType mediaType = MediaType.getMediaType(mediaTypeKey);
		return uploadMedia(file, mediaType, isMaterial);
	}

	/**
	 * 上传媒体文件</br> <font color="red">此接口只包含图片、语音、缩略图三种媒体类型的上传</font>
	 * 
	 * @param file
	 *            文件对象
	 * @param mediaType
	 *            媒体类型 （image）、语音（voice）和缩略图（thumb）
	 * @param isMaterial
	 *            是否永久上传
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(String, byte[],String,boolean)}
	 */
	public String uploadMedia(File file, MediaType mediaType, boolean isMaterial)
			throws WeixinException, IOException {
		byte[] datas = IOUtil.toByteArray(new FileInputStream(file));
		return uploadMedia(file.getName(), datas, mediaType.name(), isMaterial);
	}

	/**
	 * 上传媒体文件 </br> <font color="red">此接口只包含图片、语音、缩略图、视频(临时)四种媒体类型的上传</font>
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param fileName
	 *            文件名
	 * @param bytes
	 *            媒体数据包
	 * @param mediaType
	 *            媒体文件类型：分别有图片（image）、语音（voice）、视频(video)和缩略图（thumb）
	 * @param isMaterial
	 *            是否永久上传
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/963fc70b80dc75483a271298a76a8d59.html">上传临时素材</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久素材</a>
	 * @throws WeixinException
	 */
	public String uploadMedia(String fileName, byte[] bytes, String mediaType,
			boolean isMaterial) throws WeixinException {
		if (",image,voice,video,thumb,".indexOf(String
				.format(",%s,", mediaType)) < 0) {
			throw new WeixinException(String.format(
					"unsupported media type:%s", mediaType));
		}
		if (mediaType.equals(MediaType.video.name()) && isMaterial) {
			throw new WeixinException(
					"please invoke uploadMaterialVideo method");
		}
		Token token = tokenHolder.getToken();
		Response response = null;
		if (isMaterial) {
			String material_media_upload_uri = getRequestUri("material_media_upload_uri");
			try {
				response = request.post(String.format(
						material_media_upload_uri, token.getAccessToken()),
						new PartParameter("media", new ByteArrayBody(bytes,
								fileName)), new PartParameter("type",
								new StringBody(mediaType, Consts.UTF_8)));
			} catch (UnsupportedEncodingException e) {
				; // ignore
			}
		} else {
			String file_upload_uri = getRequestUri("file_upload_uri");
			response = request.post(String.format(file_upload_uri,
					token.getAccessToken(), mediaType), new PartParameter(
					"media", new ByteArrayBody(bytes, fileName)));
		}
		return response.getAsJson().getString("media_id");
	}

	/**
	 * 下载媒体素材
	 * <p>
	 * 正常情况下返回表头如Content-Type: image/jpeg,否则抛出异常.
	 * </p>
	 * 
	 * @param mediaId
	 *            存储在微信服务器上的媒体标识
	 * @param mediaType
	 *            媒体文件类型：分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @return 写入硬盘后的文件对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/11/07b6b76a6b6e8848e855a435d5e34a5f.html">下载临时媒体文件</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html">下载永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#downloadMedia(String,boolean)}
	 */
	public File downloadMedia(String mediaId, MediaType mediaType,
			boolean isMaterial) throws WeixinException {
		if (",image,voice,video,thumb,".indexOf(String.format(",%s,",
				mediaType.name())) < 0) {
			throw new WeixinException(String.format(
					"unsupported media type:%s", mediaType.name()));
		}
		String media_path = ConfigUtil.getValue("media_path");
		File file = new File(media_path + File.separator + mediaId + "."
				+ mediaType.getFormatName());
		if (file.exists()) {
			return file;
		}
		byte[] datas = downloadMedia(mediaId, isMaterial);
		OutputStream os = null;
		try {
			if (file.createNewFile()) {
				os = new FileOutputStream(file);
				os.write(datas);
			} else {
				throw new WeixinException(String.format("create file fail:%s",
						file.getAbsolutePath()));
			}
		} catch (IOException e) {
			throw new WeixinException(e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException ignore) {
				;
			}
		}
		return file;
	}

	/**
	 * 下载媒体素材
	 * 
	 * @param mediaId
	 *            媒体ID
	 * @param isMaterial
	 *            是否下载永久素材
	 * @return 二进制数据包
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/11/07b6b76a6b6e8848e855a435d5e34a5f.html">下载临时媒体素材</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html">下载永久媒体素材</a>
	 */
	public byte[] downloadMedia(String mediaId, boolean isMaterial)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		Response response = null;
		if (isMaterial) {
			JSONObject media = new JSONObject();
			media.put("media_id", mediaId);
			String material_media_download_uri = getRequestUri("material_media_download_uri");
			response = request.post(
					String.format(material_media_download_uri,
							token.getAccessToken()), media.toJSONString());
		} else {
			String file_download_uri = getRequestUri("file_download_uri");
			response = request.get(String.format(file_download_uri,
					token.getAccessToken(), mediaId));
		}
		return response.getBody();
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
		Response response = request.post(
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
	 * @see <a href=
	 *      "http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html">下载永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 */
	public List<MpArticle> downloadArticle(String mediaId)
			throws WeixinException {
		byte[] bytes = downloadMedia(mediaId, true);
		JSONObject obj = JSON.parseObject(bytes, 0, bytes.length,
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
		Response response = request.post(
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
		Response response = request.post(
				String.format(material_media_del_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 上传永久视频素材
	 * 
	 * @param file
	 *            大小不超过1M且格式为MP4的视频文件
	 * @param title
	 *            视频标题
	 * @param introduction
	 *            视频描述
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久媒体素材</a>
	 * @throws WeixinException
	 * @throws IOException
	 */
	public String uploadMaterialVideo(File file, String title,
			String introduction) throws WeixinException, IOException {
		String material_media_upload_uri = getRequestUri("material_media_upload_uri");
		Token token = tokenHolder.getToken();
		try {
			JSONObject description = new JSONObject();
			description.put("title", title);
			description.put("introduction", introduction);
			byte[] bytes = IOUtil.toByteArray(new FileInputStream(file));
			Response response = request.post(
					String.format(material_media_upload_uri,
							token.getAccessToken()),
					new PartParameter("media", new ByteArrayBody(bytes, file
							.getName())),
					new PartParameter("type", new StringBody(MediaType.video
							.name(), Consts.UTF_8)),
					new PartParameter("description", new StringBody(description
							.toJSONString(), Consts.UTF_8)));
			return response.getAsJson().getString("media_id");
		} catch (UnsupportedEncodingException e) {
			throw new WeixinException("unsupported encoding");
		}
	}

	/**
	 * 获取永久媒体素材的总数</br> .图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
	 * 
	 * @return 总数对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.MediaCounter
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/16/8cc64f8c189674b421bee3ed403993b8.html">获取素材总数</a>
	 */
	public MediaCounter countMaterialMedia() throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_count_uri = getRequestUri("material_media_count_uri");
		Response response = request.get(String.format(material_media_count_uri,
				token.getAccessToken()));

		return response.getAsObject(new TypeReference<MediaCounter>() {
		});
	}

	/**
	 * 获取媒体素材记录列表
	 * 
	 * @param mediaType
	 *            素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset
	 *            从全部素材的该偏移位置开始返回，0表示从第一个素材返回
	 * @param count
	 *            返回素材的数量，取值在1到20之间
	 * @return 媒体素材的记录对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.MediaRecord
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.mp.model.MediaItem
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html">获取素材列表</a>
	 */
	public MediaRecord listMaterialMedia(MediaType mediaType, int offset,
			int count) throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_list_uri = getRequestUri("material_media_list_uri");
		JSONObject obj = new JSONObject();
		obj.put("type", mediaType.name());
		obj.put("offset", offset);
		obj.put("count", count);
		Response response = request.post(
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
		return mediaRecord;
	}

	/**
	 * 获取全部的媒体素材
	 * 
	 * @param mediaType
	 *            媒体类型
	 * @return 素材列表
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#listMaterialMedia(MediaType, int, int)}
	 * @throws WeixinException
	 */
	public List<MediaItem> listAllMaterialMedia(MediaType mediaType)
			throws WeixinException {
		int offset = 0;
		int count = 20;
		List<MediaItem> mediaList = new ArrayList<MediaItem>();
		MediaRecord mediaRecord = null;
		for (;;) {
			mediaRecord = listMaterialMedia(mediaType, offset, count);
			mediaList.addAll(mediaRecord.getItems());
			if (offset >= mediaRecord.getTotalCount()) {
				break;
			}
			offset += count;
		}
		return mediaList;
	}
}
