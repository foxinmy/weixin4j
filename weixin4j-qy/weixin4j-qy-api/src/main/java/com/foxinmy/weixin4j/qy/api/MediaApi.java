package com.foxinmy.weixin4j.qy.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.mime.content.ByteArrayBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.PartParameter;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.Callback;
import com.foxinmy.weixin4j.qy.model.Party;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.FileUtil;
import com.foxinmy.weixin4j.util.IOUtil;

/**
 * 媒体相关API
 * 
 * @className MediaApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">管理多媒体文件</a>
 * @see com.foxinmy.weixin4j.type.MediaType
 */
public class MediaApi extends QyApi {

	private final TokenHolder tokenHolder;

	public MediaApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param file
	 *            媒体对象
	 * @return 上传到微信服务器返回的媒体标识
	 * @see {@link com.foxinmy.weixin4j.qy.api.MediaApi#uploadMedia(File, MediaType)}
	 * @throws WeixinException
	 * @throws IOException
	 */
	public String uploadMedia(File file) throws WeixinException, IOException {
		String mediaTypeKey = IOUtil.getExtension(file.getName());
		if (StringUtils.isBlank(mediaTypeKey)) {
			mediaTypeKey = FileUtil.getFileType(file);
		}
		MediaType mediaType = MediaType.getMediaType(mediaTypeKey);
		return uploadMedia(file, mediaType);
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
	 * @see {@link com.foxinmy.weixin4j.qy.api.MediaApi#uploadMedia(String, byte[],String)}
	 */
	public String uploadMedia(File file, MediaType mediaType)
			throws WeixinException, IOException {
		byte[] datas = IOUtil.toByteArray(new FileInputStream(file));
		return uploadMedia(file.getName(), datas, mediaType.name());
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
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传媒体文件说明</a>
	 * @throws WeixinException
	 */
	public String uploadMedia(String fileName, byte[] bytes, String mediaType)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String file_upload_uri = getRequestUri("file_upload_uri");
		Response response = request.post(String.format(file_upload_uri,
				token.getAccessToken(), mediaType), new PartParameter("media",
				new ByteArrayBody(bytes, fileName)));

		return response.getAsJson().getString("media_id");
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
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">获取媒体说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.qy.api.MediaApi#downloadMedia(String)}
	 */
	public File downloadMedia(String mediaId, String extension)
			throws WeixinException {
		String media_path = ConfigUtil.getValue("media_path");
		File file = new File(media_path + File.separator + mediaId + "."
				+ extension);
		if (file.exists()) {
			return file;
		}
		byte[] datas = downloadMedia(mediaId);
		OutputStream os = null;
		try {
			boolean flag = file.createNewFile();
			if (flag) {
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
	 * 下载媒体文件(完全公开。所有管理员均可调用，media_id可以共享)
	 * 
	 * @param mediaId
	 *            媒体ID
	 * @return 二进制数据包
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">获取媒体说明</a>
	 * @throws WeixinException
	 */
	public byte[] downloadMedia(String mediaId) throws WeixinException {
		Token token = tokenHolder.getToken();
		String file_download_uri = getRequestUri("file_download_uri");
		Response response = request.get(String.format(file_download_uri,
				token.getAccessToken(), mediaId));
		return response.getBody();
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
		JSONObject csvObj = JSON.parseObject(getConfigValue(batchName));
		JSONArray columns = csvObj.getJSONArray("column");
		StringWriter writer = new StringWriter();
		writer.write(csvObj.getString("header"));
		final Map<String, Object> column = new LinkedHashMap<String, Object>();
		for (Object col : columns) {
			column.put(col.toString(), "");
		}
		writer.write("\r\n");
		for (T model : models) {
			JSON.toJSONString(model, new PropertyFilter() {
				@Override
				public boolean apply(Object object, String name, Object value) {
					if (column.containsKey(name)) {
						column.put(name, value);
					}
					return true;
				}
			});
			writer.write(StringUtils.join(column.values(), ","));
			writer.write("\r\n");
		}
		String mediaId = uploadMedia(batchName,
				writer.toString().getBytes(Consts.UTF_8), MediaType.file.name());
		try {
			writer.close();
		} catch (IOException e) {
			;
		}
		return mediaId;
	}
}
