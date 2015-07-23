package com.foxinmy.weixin4j.qy.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpGet;
import com.foxinmy.weixin4j.http.apache.FormBodyPart;
import com.foxinmy.weixin4j.http.apache.InputStreamBody;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.MediaCounter;
import com.foxinmy.weixin4j.model.MediaItem;
import com.foxinmy.weixin4j.model.MediaRecord;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.Callback;
import com.foxinmy.weixin4j.qy.model.Party;
import com.foxinmy.weixin4j.qy.model.User;
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
 * 媒体相关API
 * 
 * @className MediaApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E7%B4%A0%E6%9D%90%E6%96%87%E4%BB%B6">管理素材文件</a>
 * @see com.foxinmy.weixin4j.type.MediaType
 */
public class MediaApi extends QyApi {

	private final TokenHolder tokenHolder;

	public MediaApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 上传媒体文件
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
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%B4%E6%97%B6%E7%B4%A0%E6%9D%90%E6%96%87%E4%BB%B6">上传临时素材文件说明</a>
	 * @see <a
	 *      href="http://http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">上传永久素材文件说明</a>
	 * @throws WeixinException
	 */
	public String uploadMedia(int agentid, InputStream is, String fileName)
			throws WeixinException {
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
			mediaType = MediaType.file;
		}
		Token token = tokenHolder.getToken();
		try {
			WeixinResponse response = null;
			if (agentid > 0) {
				String material_media_upload_uri = getRequestUri("material_media_upload_uri");
				response = weixinClient.post(String.format(
						material_media_upload_uri, token.getAccessToken(),
						mediaType.name(), agentid), new FormBodyPart("media",
						new InputStreamBody(new ByteArrayInputStream(content),
								mediaType.getContentType().getMimeType(),
								fileName)));
			} else {
				String file_upload_uri = getRequestUri("file_upload_uri");
				response = weixinClient.post(String.format(file_upload_uri,
						token.getAccessToken(), mediaType.name()),
						new FormBodyPart("media", new InputStreamBody(
								new ByteArrayInputStream(content), mediaType
										.getContentType().getMimeType(),
								fileName)));
			}
			return response.getAsJson().getString("media_id");
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
	 *            存储在微信服务器上的媒体标识
	 * @return 写入硬盘后的文件对象,存储路径见weixin4j.properties配置
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.qy.api.MediaApi#downloadMedia(int,String)}
	 */
	public File downloadMediaFile(int agentid, String mediaId)
			throws WeixinException {
		String media_path = ConfigUtil.getValue("media_path",
				Weixin4jConst.DEFAULT_MEDIA_PATH);
		File file = new File(media_path + File.separator + mediaId);
		if (file.exists()) {
			return file;
		}
		byte[] datas = downloadMedia(agentid, mediaId);
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
	 * 下载媒体文件
	 * 
	 * @param agentid
	 *            企业应用Id(<font color="red">大于0时视为获取永久媒体文件</font>)
	 * @param mediaId
	 *            媒体ID
	 * @return 二进制数据包(需自行判断类型)
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%B8%B4%E6%97%B6%E7%B4%A0%E6%9D%90%E6%96%87%E4%BB%B6">获取临时媒体说明</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">获取永久媒体说明</a>
	 * @throws WeixinException
	 */
	public byte[] downloadMedia(int agentid, String mediaId)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		try {
			HttpGet method = null;
			if (agentid > 0) {
				String material_media_download_uri = getRequestUri("material_media_download_uri");
				method = new HttpGet(String.format(material_media_download_uri,
						token.getAccessToken(), mediaId, agentid));
			} else {
				String meida_download_uri = getRequestUri("meida_download_uri");
				method = new HttpGet(String.format(meida_download_uri,
						token.getAccessToken(), mediaId));
			}
			return weixinClient.execute(method).getContent();
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
	 * @param agentid
	 *            企业应用的id
	 * @param articles
	 *            图文列表
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">上传永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 */
	public String uploadMaterialArticle(int agentid, List<MpArticle> articles)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_article_upload_uri = getRequestUri("material_article_upload_uri");
		JSONObject obj = new JSONObject();
		obj.put("agentid", agentid);
		JSONObject news = new JSONObject();
		news.put("articles", articles);
		obj.put("mpnews", news);
		WeixinResponse response = weixinClient.post(
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
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%88%A0%E9%99%A4%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90">删除永久媒体素材</a>
	 */
	public JsonResult deleteMaterialMedia(int agentid, String mediaId)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_del_uri = getRequestUri("material_media_del_uri");
		WeixinResponse response = weixinClient.get(String.format(
				material_media_del_uri, token.getAccessToken(), mediaId,
				agentid));
		return response.getAsJsonResult();
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
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BF%AE%E6%94%B9%E6%B0%B8%E4%B9%85%E5%9B%BE%E6%96%87%E7%B4%A0%E6%9D%90">修改永久媒体素材</a>
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 */
	public String updateMaterialArticle(int agentid, String mediaId,
			List<MpArticle> articles) throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_article_update_uri = getRequestUri("material_article_update_uri");
		JSONObject obj = new JSONObject();
		obj.put("agentid", agentid);
		JSONObject news = new JSONObject();
		news.put("articles", articles);
		obj.put("mpnews", news);
		obj.put("media_id", mediaId);
		WeixinResponse response = weixinClient.post(
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
	 * @see com.foxinmy.weixin4j.model.MediaCounter
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%B4%A0%E6%9D%90%E6%80%BB%E6%95%B0">获取素材总数</a>
	 */
	public MediaCounter countMaterialMedia(int agentid) throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_count_uri = getRequestUri("material_media_count_uri");
		WeixinResponse response = weixinClient.get(String.format(
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
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%B4%A0%E6%9D%90%E5%88%97%E8%A1%A8">获取素材列表</a>
	 */
	public MediaRecord listMaterialMedia(int agentid, MediaType mediaType,
			int offset, int count) throws WeixinException {
		Token token = tokenHolder.getToken();
		String material_media_list_uri = getRequestUri("material_media_list_uri");
		JSONObject obj = new JSONObject();
		obj.put("type",
				mediaType == MediaType.news ? "mpnews" : mediaType.name());
		obj.put("offset", offset);
		obj.put("count", count);
		WeixinResponse response = weixinClient.post(
				String.format(material_media_list_uri, token.getAccessToken()),
				obj.toJSONString());
		obj = response.getAsJson();

		MediaRecord mediaRecord = JSON.toJavaObject(obj, MediaRecord.class);
		if (mediaType == MediaType.news) {
			mediaRecord.setItems(JSON.parseArray(obj.getString("itemlist"),
					MediaItem.class));
		}
		mediaRecord.setMediaType(mediaType);
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
	 * @see {@link com.foxinmy.weixin4j.qy.api.MediaApi#listMaterialMedia(int,MediaType, int, int)}
	 * @throws WeixinException
	 */
	public List<MediaItem> listAllMaterialMedia(int agentid, MediaType mediaType)
			throws WeixinException {
		int offset = 0;
		int count = 20;
		List<MediaItem> mediaList = new ArrayList<MediaItem>();
		MediaRecord mediaRecord = null;
		for (;;) {
			mediaRecord = listMaterialMedia(agentid, mediaType, offset, count);
			mediaList.addAll(mediaRecord.getItems());
			if (offset >= mediaRecord.getTotalCount()) {
				break;
			}
			offset += count;
		}
		return mediaList;
	}

	/**
	 * 批量上传成员
	 * 
	 * @param users
	 *            成员列表
	 * @see {@link com.foxinmy.weixin4j.qy.api.BatchApi#syncuser(String,Callback)}
	 * @see {@link com.foxinmy.weixin4j.qy.api.BatchApi#replaceuser(String,Callback)}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E9.80.9A.E8.AE.AF.E5.BD.95.E6.9B.B4.E6.96.B0">批量任务</a>
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
	 * @see {@link com.foxinmy.weixin4j.qy.api.BatchApi#replaceparty(String,Callback)}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BC%82%E6%AD%A5%E4%BB%BB%E5%8A%A1%E6%8E%A5%E5%8F%A3#.E9.80.9A.E8.AE.AF.E5.BD.95.E6.9B.B4.E6.96.B0">批量任务</a>
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
							column.put(name, value);
						}
						return true;
					}
				});
				writer.write(StringUtil.join(column.values(), ','));
				writer.write("\r\n");
			}
			return uploadMedia(0, new ByteArrayInputStream(writer.getBuffer()
					.toString().getBytes(Consts.UTF_8)), batchName);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				;
			}
		}
	}
}
