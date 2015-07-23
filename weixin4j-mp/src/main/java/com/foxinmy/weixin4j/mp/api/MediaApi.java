package com.foxinmy.weixin4j.mp.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpGet;
import com.foxinmy.weixin4j.http.HttpPost;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.InputStreamBody;
import com.foxinmy.weixin4j.http.apache.StringBody;
import com.foxinmy.weixin4j.http.entity.StringEntity;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.MediaCounter;
import com.foxinmy.weixin4j.model.MediaItem;
import com.foxinmy.weixin4j.model.MediaRecord;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.FileUtil;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.ObjectId;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConst;

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
	 * 上传媒体文件:图片（image）、语音（voice）、视频(video)和缩略图（thumb） </br> <font
	 * color="red">此接口只包含图片、语音、缩略图、视频(临时)四种媒体类型的上传</font>
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param is
	 *            媒体数据流
	 * @param fileName
	 *            文件名
	 * @param isMaterial
	 *            是否永久上传
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/963fc70b80dc75483a271298a76a8d59.html">上传临时素材</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html">上传永久素材</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @throws WeixinException
	 */
	public String uploadMedia(InputStream is, String fileName,
			boolean isMaterial) throws WeixinException {
		byte[] content;
		try {
			content = IOUtil.toByteArray(is);
		} catch (IOException e) {
			throw new WeixinException(e);
		}
		if (StringUtil.isBlank(fileName)) {
			fileName = ObjectId.get().toHexString();
		}
		String suffixName = IOUtil.getExtension(fileName);
		if (StringUtil.isBlank(suffixName)) {
			suffixName = FileUtil
					.getFileType(new ByteArrayInputStream(content));
			fileName = String.format("%s.%s", fileName, suffixName);
		}
		MediaType mediaType = null;
		if ("bmp/png/jpeg/jpg/gif".contains(suffixName)) {
			mediaType = MediaType.image;
		} else if ("mp3/wma/wav/amr".contains(suffixName)) {
			mediaType = MediaType.voice;
		} else if ("rm/rmvb/wmv/avi/mpg/mpeg/mp4".equals(suffixName)) {
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
				response = weixinClient
						.post(String.format(material_media_upload_uri,
								token.getAccessToken()), new FormBodyPart(
								"media", new InputStreamBody(is, mediaType
										.getContentType().getMimeType(),
										fileName)), new FormBodyPart("type",
								new StringBody(mediaType.name(), Consts.UTF_8)));
			} else {
				String media_upload_uri = getRequestUri("media_upload_uri");
				response = weixinClient.post(String.format(media_upload_uri,
						token.getAccessToken(), mediaType.name()),
						new FormBodyPart("media", new InputStreamBody(is,
								mediaType.getContentType().getMimeType(),
								fileName)));
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
	 * @return 写入硬盘后的文件对象,存储路径见weixin4j.properties配置
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/11/07b6b76a6b6e8848e855a435d5e34a5f.html">下载临时媒体文件</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html">下载永久媒体素材</a>
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#downloadMedia(String,boolean)}
	 */
	public File downloadMediaFile(String mediaId, boolean isMaterial)
			throws WeixinException {
		String media_path = ConfigUtil.getValue("media_path",
				Weixin4jConst.DEFAULT_MEDIA_PATH);
		File file = new File(media_path + File.separator + mediaId);
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
			} catch (IOException e) {
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
	 * @return 二进制数据包(需自行判断类型)
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/11/07b6b76a6b6e8848e855a435d5e34a5f.html">下载临时媒体素材</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html">下载永久媒体素材</a>
	 */
	public byte[] downloadMedia(String mediaId, boolean isMaterial)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		try {
			if (isMaterial) {
				String material_media_download_uri = getRequestUri("material_media_download_uri");
				HttpPost method = new HttpPost(String.format(
						material_media_download_uri, token.getAccessToken()));
				method.setEntity(new StringEntity(String.format(
						"{\"media_id\":\"%s\"}", mediaId)));
				return weixinClient.execute(method).getContent();
			} else {
				String meida_download_uri = getRequestUri("meida_download_uri");
				HttpGet method = new HttpGet(String.format(meida_download_uri,
						token.getAccessToken(), mediaId));
				return weixinClient.execute(method).getContent();
			}
		} catch (IOException e) {
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
		WeixinResponse response = weixinClient.post(
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
		WeixinResponse response = weixinClient.post(
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
		WeixinResponse response = weixinClient.post(
				String.format(material_media_del_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
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
	 * @throws WeixinException
	 */
	public String uploadMaterialVideo(InputStream is, String title,
			String introduction) throws WeixinException {
		String material_media_upload_uri = getRequestUri("material_media_upload_uri");
		Token token = tokenHolder.getToken();
		try {
			JSONObject description = new JSONObject();
			description.put("title", title);
			description.put("introduction", introduction);
			WeixinResponse response = weixinClient.post(
					String.format(material_media_upload_uri,
							token.getAccessToken()),
					new FormBodyPart("media", new InputStreamBody(is,
							MediaType.video.getContentType().getMimeType(),
							ObjectId.get().toHexString())),
					new FormBodyPart("type", new StringBody(MediaType.video
							.name(), Consts.UTF_8)),
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
		WeixinResponse response = weixinClient.get(String.format(
				material_media_count_uri, token.getAccessToken()));

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
	 * @see com.foxinmy.weixin4j.model.MediaRecord
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see com.foxinmy.weixin4j.model.MediaItem
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
		WeixinResponse response = weixinClient.post(
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
