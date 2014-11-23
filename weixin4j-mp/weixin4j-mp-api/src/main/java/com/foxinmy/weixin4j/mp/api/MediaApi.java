package com.foxinmy.weixin4j.mp.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.mime.content.ByteArrayBody;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.PartParameter;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
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
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传多媒体文件</a>
 * @see com.foxinmy.weixin4j.type.MediaType
 */
public class MediaApi extends BaseApi {

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
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(File, MediaType)}
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
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(String, byte[],String)}
	 */
	public String uploadMedia(File file, MediaType mediaType)
			throws WeixinException, IOException {
		byte[] datas = IOUtil.toByteArray(new FileInputStream(file));
		return uploadMedia(file.getName(), datas, mediaType.name());
	}

	/**
	 * 上传媒体文件
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
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
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
	 * 下载媒体文件
	 * <p>
	 * 正常情况下返回表头如Content-Type: image/jpeg,否则抛出异常.
	 * </p>
	 * 
	 * @param mediaId
	 *            存储在微信服务器上的媒体标识
	 * @param mediaType
	 *            媒体类型
	 * @return 写入硬盘后的文件对象
	 * @throws WeixinException
	 * @throws IOException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#downloadMedia(String)}
	 */
	public File downloadMedia(String mediaId, MediaType mediaType)
			throws WeixinException, IOException {
		String media_path = ConfigUtil.getValue("media_path");
		File file = new File(media_path + File.separator + mediaId + "."
				+ mediaType.getFormatName());
		if (file.exists()) {
			return file;
		}
		byte[] datas = downloadMedia(mediaId);
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		out.write(datas);
		out.close();
		return file;
	}

	/**
	 * 下载媒体文件
	 * 
	 * @param mediaId
	 *            媒体ID
	 * @return 二进制数据包
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 */
	public byte[] downloadMedia(String mediaId) throws WeixinException {
		Token token = tokenHolder.getToken();
		String file_download_uri = getRequestUri("file_download_uri");
		Response response = request.get(String.format(file_download_uri,
				token.getAccessToken(), mediaId));

		return response.getBody();
	}
}
