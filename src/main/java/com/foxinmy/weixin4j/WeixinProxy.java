package com.foxinmy.weixin4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Following;
import com.foxinmy.weixin4j.model.Group;
import com.foxinmy.weixin4j.model.MpArticle;
import com.foxinmy.weixin4j.model.QRParameter;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.User;
import com.foxinmy.weixin4j.model.UserToken;
import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.notify.BaseNotify;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.util.WeixinConfig;
import com.foxinmy.weixin4j.util.WeixinUtil;
import com.foxinmy.weixin4j.xml.XStream;

/**
 * 微信服务实现
 * 
 * @className WeixinServiceImpl
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php">api文档</a>
 */
public class WeixinProxy {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private HttpRequest request = new HttpRequest();

	/**
	 * 验证微信签名
	 * 
	 * @param echostr
	 *            随机字符串
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param signature
	 *            微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
	 * @return 
	 *         开发者通过检验signature对请求进行相关校验。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效
	 *         ，成为开发者成功，否则接入失败
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E5%85%A5%E6%8C%87%E5%8D%97">接入指南</a>
	 */
	public String signature(String echostr, String timestamp, String nonce,
			String signature) {
		String app_token = WeixinConfig.getValue("app_token");
		if (WeixinUtil.isBlank(app_token)) {
			log.error("signature fail : token is null!");
			return null;
		}
		if (WeixinUtil.isBlank(echostr) || WeixinUtil.isBlank(timestamp)
				|| WeixinUtil.isBlank(nonce)) {
			log.error("signature fail : invalid parameter!");
			return null;
		}
		String _signature = null;
		try {
			String[] a = { app_token, timestamp, nonce };
			Arrays.sort(a);
			StringBuilder sb = new StringBuilder(3);
			for (String str : a) {
				sb.append(str);
			}
			_signature = DigestUtils.sha1Hex(sb.toString());
		} catch (Exception e) {
			log.error("signature error", e);
		}
		if (signature.equals(_signature)) {
			return echostr;
		} else {
			log.error("signature fail : invalid signature!");
			return null;
		}
	}

	/**
	 * xml消息转换为消息对象
	 * <p>
	 * 微信服务器在五秒内收不到响应会断掉连接,并且重新发起请求,总共重试三次
	 * </p>
	 * 
	 * @param xml
	 *            消息字符串
	 * @return 消息对象
	 * @throws DocumentException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AA%8C%E8%AF%81%E6%B6%88%E6%81%AF%E7%9C%9F%E5%AE%9E%E6%80%A7">验证消息的合法性</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF">普通消息</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81">事件触发</a>
	 * @see com.foxinmy.weixin4j.type.MessageType
	 * @see com.feican.weixin.msg.BaeMessage
	 * @see com.foxinmy.weixin4j.msg.TextMessage
	 * @see com.foxinmy.weixin4j.msg.in.ImageMessage
	 * @see com.foxinmy.weixin4j.msg.in.VoiceMessage
	 * @see com.foxinmy.weixin4j.msg.in.VideoMessage
	 * @see com.foxinmy.weixin4j.msg.in.LocationMessage
	 * @see com.foxinmy.weixin4j.msg.in.LinkMessage
	 * @see com.foxinmy.weixin4j.msg.event.ScribeEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.ScanEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.LocationEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.MenuEventMessage
	 */
	public BaseMessage xml2msg(String xmlStr) throws DocumentException {
		if (WeixinUtil.isBlank(xmlStr))
			return null;
		Document doc = DocumentHelper.parseText(xmlStr);
		String type = doc.selectSingleNode("/xml/MsgType").getStringValue();
		if (WeixinUtil.isBlank(type)) {
			return null;
		}
		XStream xstream = new XStream();
		MessageType messageType = MessageType.valueOf(type.toLowerCase());
		Class<? extends BaseMessage> messageClass = messageType
				.getMessageClass();
		if (messageType == MessageType.event) {
			type = doc.selectSingleNode("/xml/Event").getStringValue();
			messageClass = EventType.valueOf(type.toLowerCase())
					.getEventClass();
		}
		xstream.alias("xml", messageClass);
		xstream.ignoreUnknownElements();
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(messageClass);
		return xstream.fromXML(doc.asXML(), messageClass);
	}

	/**
	 * xml消息转换为消息对象
	 * 
	 * @param inputStream
	 *            带消息字符串的输入流
	 * @return 消息对象
	 * @throws DocumentException
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#xml2msg(String)}
	 */
	public BaseMessage xml2msg(InputStream inputStream)
			throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inputStream);
		return xml2msg(doc.asXML());
	}

	/**
	 * 获取token
	 * <p>
	 * 正常情况下返回{"access_token":"ACCESS_TOKEN","expires_in":7200},否则抛出异常.
	 * </p>
	 * 
	 * @return token对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">获取token说明</a>
	 * @see com.foxinmy.weixin4j.model.Token
	 */
	public Token getToken() throws WeixinException {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(Token.class);
		String appOpenId = WeixinConfig.getValue("app_openId");
		String token_path = WeixinConfig.getValue("token_path");
		File token_file = new File(String.format("%s/%s_token.xml", token_path,
				appOpenId));
		Token token = null;
		Calendar ca = Calendar.getInstance();
		long now_time = ca.getTimeInMillis();
		try {
			if (token_file.exists()) {
				token = (Token) xstream.fromXML(token_file);

				long expise_time = token.getTime()
						+ (token.getExpires_in() * 1000) - 5;
				if (expise_time > now_time) {
					return token;
				}
			} else {
				try {
					token_file.createNewFile();
				} catch (IOException e) {
					token_file.getParentFile().mkdirs();
				}
			}
			token_path = WeixinConfig.getValue("api_token_uri");
			Response response = request.get(token_path);
			token = response.getAsObject(Token.class);
			token.setTime(now_time);
			token.setOpenid(appOpenId);
			xstream.toXML(token, new FileOutputStream(token_file));
		} catch (IOException e) {
			log.error("获取token出错", e);
		}
		return token;
	}

	/**
	 * 获取token
	 * 
	 * @param code
	 *            用户授权后返回的code
	 * @return token对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.80.9A.E8.BF.87code.E6.8D.A2.E5.8F.96.E7.BD.91.E9.A1.B5.E6.8E.88.E6.9D.83access_token">获取用户token</a>
	 * @see com.foxinmy.weixin4j.model.UserToken
	 */
	public UserToken getAccessToken(String code) throws WeixinException {
		String user_token_uri = WeixinConfig.getValue("sns_user_token_uri");
		Response response = request.get(String.format(user_token_uri, code));
		// 暂时不保存用户token
		return response.getAsObject(UserToken.class);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param token
	 *            授权票据
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF#.E7.AC.AC.E5.9B.9B.E6.AD.A5.EF.BC.9A.E6.8B.89.E5.8F.96.E7.94.A8.E6.88.B7.E4.BF.A1.E6.81.AF.28.E9.9C.80scope.E4.B8.BA_snsapi_userinfo.29">拉取用户信息</a>
	 * @see com.foxinmy.weixin4j.model.User
	 * @see com.foxinmy.weixin4j.model.UserToken
	 *      {@link com.foxinmy.weixin4j.WeixinProxy#getAccessToken(String)}
	 */
	public User getUser(UserToken token) throws WeixinException {
		String user_info_uri = WeixinConfig.getValue("sns_user_info_uri");
		Response response = request.get(String.format(user_info_uri,
				token.getAccess_token(), token.getOpenid()));
		return response.getAsObject(User.class);
	}

	/**
	 * 获取用户信息
	 * <p>
	 * 在关注者与公众号产生消息交互后,公众号可获得关注者的OpenID（加密后的微信号,每个用户对每个公众号的OpenID是唯一的,对于不同公众号,
	 * 同一用户的openid不同）,公众号可通过本接口来根据OpenID获取用户基本信息,包括昵称、头像、性别、所在城市、语言和关注时间
	 * </p>
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF">获取用户信息</a>
	 * @see com.foxinmy.weixin4j.model.User
	 */
	public User getUser(String openId) throws WeixinException {
		String user_info_uri = WeixinConfig.getValue("api_user_info_uri");
		Token token = getToken();
		Response response = request.get(String.format(user_info_uri,
				token.getAccess_token(), openId));
		return response.getAsObject(User.class);
	}

	/**
	 * 生成带参数的二维码
	 * 
	 * @param parameter
	 * @return byte数据包
	 * @throws WeixinException
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#getQR(QRParameter)}
	 */
	public byte[] getQRData(QRParameter parameter) throws WeixinException {
		Token token = getToken();
		String qr_uri = WeixinConfig.getValue("qr_ticket_uri");
		Response response = null;
		try {
			response = request.post(String.format(qr_uri,
					token.getAccess_token()),
					new StringRequestEntity(parameter.toJson(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String ticket = response.getAsJson().getString("ticket");
		qr_uri = WeixinConfig.getValue("qr_image_uri");
		response = request.get(String.format(qr_uri, ticket));

		return response.getBody();
	}

	/**
	 * 生成带参数的二维码
	 * <p>
	 * 二维码分为临时跟永久两种,扫描时触发推送带参数事件
	 * </p>
	 * 
	 * @param parameter
	 *            二维码参数
	 * @return 硬盘存储的文件对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%94%9F%E6%88%90%E5%B8%A6%E5%8F%82%E6%95%B0%E7%9A%84%E4%BA%8C%E7%BB%B4%E7%A0%81">二维码</a>
	 * @see com.foxinmy.weixin4j.model.QRParameter
	 */
	public File getQR(QRParameter parameter) throws WeixinException {
		String qr_path = WeixinConfig.getValue("qr_path");
		String filename = String
				.format("%s_%d_%d.jpg", parameter.getQrType().name(),
						parameter.getScene_id(), parameter.getExpire_seconds());
		File file = new File(qr_path + File.separator + filename);
		if (file.exists()) {
			return file;
		}
		FileOutputStream out = null;
		try {
			try {
				file.createNewFile();
			} catch (IOException e) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			byte[] b = getQRData(parameter);
			out.write(b);
		} catch (IOException e) {

		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {

			}
		}
		return file;
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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 */
	public String uploadMedia(File file, MediaType mediaType)
			throws WeixinException {
		byte[] b = null;
		ByteArrayOutputStream out = null;
		FileInputStream in = null;
		try {
			b = new byte[4096];
			out = new ByteArrayOutputStream();
			in = new FileInputStream(file);
			int len = -1;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			b = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return uploadMedia(file.getName(), b, mediaType);
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
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#uploadMedia(File, MediaType)}
	 */
	public String uploadMedia(String fileName, byte[] bytes, MediaType mediaType)
			throws WeixinException {
		Token token = getToken();
		String file_upload_uri = WeixinConfig.getValue("file_upload_uri");
		Response response = null;

		response = request.post(String.format(file_upload_uri,
				token.getAccess_token(), mediaType.name()), new FilePart(
				"media", new ByteArrayPartSource(fileName, bytes), null,
				"UTF-8"));

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
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6">上传下载说明</a>
	 * @see com.foxinmy.weixin4j.type.MediaType
	 */
	public File downloadMedia(String mediaId, MediaType mediaType)
			throws WeixinException {
		String media_path = WeixinConfig.getValue("media_path");
		String filename = mediaId + mediaType.getFormatType();
		File file = new File(media_path + File.separator + filename);
		if (file.exists()) {
			return file;
		}
		FileOutputStream out = null;
		try {
			try {
				file.createNewFile();
			} catch (IOException e) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			byte[] b = downloadMediaData(mediaId, mediaType);
			out.write(b);
		} catch (IOException e) {

		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {

			}
		}
		return file;
	}

	/**
	 * 下载媒体文件
	 * 
	 * @param mediaId
	 * @param mediaType
	 * @return 二进制数据包
	 * @throws WeixinException
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#downloadMedia(String, MediaType)}
	 */
	public byte[] downloadMediaData(String mediaId, MediaType mediaType)
			throws WeixinException {
		Token token = getToken();
		String file_download_uri = WeixinConfig.getValue("file_download_uri");
		Response response = request.get(String.format(file_download_uri,
				token.getAccess_token(), mediaId));
		return response.getBody();
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 * 
	 * @param notify
	 *            客服消息对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">发送客服消息</a>
	 */
	public void sendNotify(BaseNotify notify) throws WeixinException {
		String custom_notify_uri = WeixinConfig.getValue("custom_notify_uri");
		Token token = getToken();
		try {
			request.post(String.format(custom_notify_uri,
					token.getAccess_token(), notify.getTouser()),
					new StringRequestEntity(notify.toJson(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建分组
	 * 
	 * @param name
	 *            组名称
	 * @return group对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84">创建分组</a>
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.model.Group#toCreateJson()
	 */
	public Group createGroup(String name) throws WeixinException {
		String group_create_uri = WeixinConfig.getValue("group_create_uri");
		Token token = getToken();
		Response response = null;
		Group group = new Group(name);
		try {
			response = request.post(String.format(group_create_uri,
					token.getAccess_token()),
					new StringRequestEntity(group.toCreateJson(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		JSONObject obj = response.getAsJson();
		return JSON.parseObject(obj.getString("group"), Group.class);
	}

	/**
	 * 查询所有分组
	 * 
	 * @return 组集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E6.9F.A5.E8.AF.A2.E6.89.80.E6.9C.89.E5.88.86.E7.BB.84">查询所有分组</a>
	 * @see com.foxinmy.weixin4j.model.Group
	 */
	public List<Group> getGroups() throws WeixinException {
		String group_get_uri = WeixinConfig.getValue("group_get_uri");
		Token token = getToken();
		Response response = request.get(String.format(group_get_uri,
				token.getAccess_token()));
		JSONObject obj = response.getAsJson();
		return JSON.parseArray(obj.getString("groups"), Group.class);
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @return 组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E6.9F.A5.E8.AF.A2.E7.94.A8.E6.88.B7.E6.89.80.E5.9C.A8.E5.88.86.E7.BB.84">查询用户所在分组</a>
	 * @see com.foxinmy.weixin4j.model.Group
	 */
	public int findGroupByOpenId(String openId) throws WeixinException {
		String group_getid_uri = WeixinConfig.getValue("group_getid_uri");
		Token token = getToken();
		Response response = null;
		try {
			response = request.post(
					String.format(group_getid_uri, token.getAccess_token()),
					new StringRequestEntity(String.format(
							"{\"openid\":\"%s\"}", openId), "application/json",
							"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return response.getAsJson().getIntValue("groupid");
	}

	/**
	 * 修改分组名
	 * 
	 * @param groupId
	 *            组ID
	 * @param name
	 *            组名称
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E4.BF.AE.E6.94.B9.E5.88.86.E7.BB.84.E5.90.8D">修改分组名</a>
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see com.foxinmy.weixin4j.model.Group#toModifyJson()
	 */
	public void modifyGroup(int groupId, String name) throws WeixinException {
		String group_modify_uri = WeixinConfig.getValue("group_modify_uri");
		Token token = getToken();
		Group group = new Group(groupId, name);
		try {
			request.post(String.format(group_modify_uri,
					token.getAccess_token()),
					new StringRequestEntity(group.toModifyJson(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移动分组
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @param groupId
	 *            组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">移动分组</a>
	 * @see com.foxinmy.weixin4j.model.Group
	 */
	public void moveGroup(String openId, int groupId) throws WeixinException {
		String group_move_uri = WeixinConfig.getValue("group_move_uri");
		Token token = getToken();
		try {
			request.post(
					String.format(group_move_uri, token.getAccess_token()),
					new StringRequestEntity(String.format(
							"{\"openid\":\"%s\",\"to_groupid\":%d}", openId,
							groupId), "application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户一定数量(10000)的关注者列表
	 * 
	 * @param nextOpenId
	 *            下一次拉取数据的openid
	 * @return 关注信息
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%85%B3%E6%B3%A8%E8%80%85%E5%88%97%E8%A1%A8">获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.model.Following
	 */
	public Following getFollowing(String nextOpenId) throws WeixinException {
		String fllowing_uri = WeixinConfig.getValue("following_uri");
		Token token = getToken();
		Response response = request.get(String.format(fllowing_uri,
				token.getAccess_token(), nextOpenId == null ? "" : nextOpenId));

		Following following = response.getAsObject(Following.class);

		if (following.getCount() > 0) {
			List<String> openIds = JSON.parseArray(following.getDataJson()
					.getString("openid"), String.class);
			List<User> userList = new ArrayList<User>();
			for (String openId : openIds) {
				userList.add(getUser(openId));
			}
			following.setUserList(userList);
		}
		return following;
	}

	/**
	 * 获取用户全部的关注者列表
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 * 
	 * @return 用户对象集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%85%B3%E6%B3%A8%E8%80%85%E5%88%97%E8%A1%A8">获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.model.Following
	 * @see com.foxinmy.weixin4j.WeixinProxy#getFollowing(String)
	 */
	public List<User> getAllFollowing() throws WeixinException {
		List<User> userList = new ArrayList<User>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getFollowing(nextOpenId);
			if (f.getCount() == 0) {
				break;
			}
			userList.addAll(f.getUserList());
			nextOpenId = f.getNextOpenId();
		}
		return userList;
	}

	/**
	 * 自定义菜单
	 * 
	 * @param btnList
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%9B%E5%BB%BA%E6%8E%A5%E5%8F%A3">创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public void createMenu(List<Button> btnList) throws WeixinException {
		String menu_create_uri = WeixinConfig.getValue("menu_create_uri");
		Token token = getToken();
		try {
			JSONObject obj = new JSONObject();
			obj.put("button", btnList);
			request.post(
					String.format(menu_create_uri, token.getAccess_token()),
					new StringRequestEntity(obj.toJSONString(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询菜单
	 * 
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E6%9F%A5%E8%AF%A2%E6%8E%A5%E5%8F%A3">查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public List<Button> getMenu() throws WeixinException {
		String menu_get_uri = WeixinConfig.getValue("menu_get_uri");
		Token token = getToken();
		Response response = request.get(String.format(menu_get_uri,
				token.getAccess_token()));
		String text = response.getAsJson().getJSONObject("menu")
				.getString("button");
		return JSON.parseArray(text, Button.class);
	}

	/**
	 * 删除菜单
	 * 
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%A0%E9%99%A4%E6%8E%A5%E5%8F%A3">删除菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public void deleteMenu() throws WeixinException {
		String menu_delete_uri = WeixinConfig.getValue("menu_delete_uri");
		Token token = getToken();
		request.get(String.format(menu_delete_uri, token.getAccess_token()));
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
	public String uploadNews(List<MpArticle> articles) throws WeixinException {
		String news_upload_uri = WeixinConfig.getValue("news_upload_uri");
		Token token = getToken();
		Response response = null;
		try {
			JSONObject obj = new JSONObject();
			obj.put("articles", articles);
			response = request.post(String.format(news_upload_uri,
					token.getAccess_token()),
					new StringRequestEntity(obj.toJSONString(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return response.getAsJson().getString("media_id");
	}

	/**
	 * 分组群发
	 * <p>
	 * 在返回成功时,意味着群发任务提交成功,并不意味着此时群发已经结束,所以,仍有可能在后续的发送过程中出现异常情况导致用户未收到消息,
	 * 如消息有时会进行审核、服务器不稳定等,此外,群发任务一般需要较长的时间才能全部发送完毕
	 * </p>
	 * 
	 * @param articles
	 *            图文消息列表
	 * @param groupId
	 *            分组ID
	 * @return 发送出去的消息ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E6.A0.B9.E6.8D.AE.E5.88.86.E7.BB.84.E8.BF.9B.E8.A1.8C.E7.BE.A4.E5.8F.91">分组群发</a>
	 * @see com.foxinmy.weixin4j.model.MpArticle
	 * @see com.foxinmy.weixin4j.model.Group
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#uploadNews(List)}
	 */
	public String massNewsByGroup(List<MpArticle> articles, String groupId)
			throws WeixinException {
		String mediaId = uploadNews(articles);
		JSONObject obj = new JSONObject();
		obj.put("filter", new JSONObject().put("group_id", groupId));
		obj.put("mpnews", new JSONObject().put("media_id", mediaId));
		obj.put("msgtype", "mpnews");
		String mass_group_uri = WeixinConfig.getValue("mass_group_uri");
		Token token = getToken();
		Response response = null;
		try {
			response = request.post(String.format(mass_group_uri,
					token.getAccess_token()),
					new StringRequestEntity(obj.toJSONString(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return response.getAsJson().getString("msg_id");
	}

	/**
	 * openId群发
	 * 
	 * @param articles
	 *            图文消息列表
	 * @param openIds
	 *            目标ID列表
	 * @return 发送出去的消息ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E6.A0.B9.E6.8D.AEOpenID.E5.88.97.E8.A1.A8.E7.BE.A4.E5.8F.91">openId群发</a>
	 * @see com.foxinmy.weixin4j.model.MpArticle
	 * @see com.foxinmy.weixin4j.model.User
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#uploadMedia(File, MediaType)}
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#uploadNews(List)}
	 */
	public String massNewsByOpenIds(List<MpArticle> articles, String... openIds)
			throws WeixinException {
		String mediaId = uploadNews(articles);
		JSONObject obj = new JSONObject();
		obj.put("touser", openIds);
		obj.put("mpnews", new JSONObject().put("media_id", mediaId));
		obj.put("msgtype", "mpnews");
		String mass_openid_uri = WeixinConfig.getValue("mass_openid_uri");
		Token token = getToken();
		Response response = null;
		try {
			response = request.post(String.format(mass_openid_uri,
					token.getAccess_token()),
					new StringRequestEntity(obj.toJSONString(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return response.getAsJson().getString("msg_id");
	}

	/**
	 * 删除群发消息
	 * <p>
	 * 请注意,只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效,已经收到的用户，还是能在其本地看到消息卡片
	 * </p>
	 * 
	 * @param msgid
	 *            发送出去的消息ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E5.88.A0.E9.99.A4.E7.BE.A4.E5.8F.91">删除群发</a>
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#massNewsByGroup(List, String)}
	 * @see {@link com.foxinmy.weixin4j.WeixinProxy#massNewsByOpenIds(List, String...)
	 */
	public void deleteMassNews(String msgid) throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("msgid", msgid);
		String mass_delete_uri = WeixinConfig.getValue("mass_delete_uri");
		Token token = getToken();
		try {
			request.post(
					String.format(mass_delete_uri, token.getAccess_token()),
					new StringRequestEntity(obj.toJSONString(),
							"application/json", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
