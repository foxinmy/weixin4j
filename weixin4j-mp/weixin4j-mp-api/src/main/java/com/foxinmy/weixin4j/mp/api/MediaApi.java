package com.foxinmy.weixin4j.mp.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.entity.mime.content.ByteArrayBody;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.PartParameter;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.MediaType;
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
	 * <p>
	 * 正常情况下返回{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789},
	 * 否则抛出异常.
	 * </p>
	 * 
	 * @param file
	 *            文件对象
	 * @param mediaType
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @throws IOException
	 * @throws
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 */
	public String uploadMedia(File file, MediaType mediaType)
			throws WeixinException, IOException {
		byte[] datas = IOUtil.toByteArray(new FileInputStream(file));
		return uploadMedia(file.getName(), datas, mediaType);
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param bytes
	 *            媒体数据包
	 * @param mediaType
	 *            媒体类型
	 * @return 上传到微信服务器返回的媒体标识
	 * @throws WeixinException
	 * @see {@link com.foxinmy.weixin4j.mp.api.MediaApi#uploadMedia(File, MediaType)}
	 */
	public String uploadMedia(String fileName, byte[] bytes, MediaType mediaType)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String file_upload_uri = getRequestUri("file_upload_uri");
		Response response = request.post(String.format(file_upload_uri,
				token.getAccessToken(), mediaType.name()), new PartParameter(
				"media", new ByteArrayBody(bytes, fileName)));

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
	 */
	public File downloadMedia(String mediaId, MediaType mediaType)
			throws WeixinException, IOException {
		String media_path = getRequestUri("media_path");
		byte[] datas = downloadMediaData(mediaId, mediaType);
		String filename = mediaId + "." + mediaType.getFormatType();
		File file = new File(media_path + File.separator + filename);
		if (file.exists()) {
			return file;
		}
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
	 * @param mediaType
	 * @return 二进制数据包
	 * @throws WeixinException
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinProxy#downloadMedia(String, MediaType)}
	 */
	public byte[] downloadMediaData(String mediaId, MediaType mediaType)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String file_download_uri = getRequestUri("file_download_uri");
		Response response = request.get(String.format(file_download_uri,
				token.getAccessToken(), mediaId));

		return response.getBody();
	}
}
