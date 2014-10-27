package com.foxinmy.weixin4j.mp.spider;

import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * 模拟微信WEB登陆
 * 
 * <p>
 * (模拟登录|启用开发者模式|修改服务器配置|修改回调地址|创建自定义菜单....more)
 * </p>
 * 
 * @className WeixinExecutor
 * @author jy
 * @date 2014年8月15日
 * @since JDK 1.7
 * @see
 */
public class WeixinExecutor implements Serializable {

	private static final long serialVersionUID = 4253859892138066462L;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final static Charset charset = StandardCharsets.UTF_8;

	private final static Map<String, String> accountMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put("名称", "name");
			put("头像", "avatar");
			put("登录邮箱", "loginEmail");
			put("原始ID", "originalId");
			put("微信号", "weixinNo");
			put("类型", "accountType");
			put("认证情况", "weixinVerify");
			put("主体信息", "bodyInfo");
			put("介绍", "introduce");
			put("所在地址", "address");
			put("二维码", "qrcodeUrl");
		}
	};

	private AbstractHttpClient client;
	private HttpHost host;
	private JSONObject weixin;

	// 服务器响应地址
	private String pushurl;
	// oauth授权回调地址
	private String backurl;
	// 服务器校验token
	private String token;

	// 公众号用户名
	private String uname;
	// 公众号密码
	private String pwd;
	// 登录时验证码(如果有)
	private String imgcode;
	// 当要求输入验证码时,cookie需带上
	private String sig;

	public WeixinExecutor(String backurl, String pushurl, String token,
			String uname, String pwd, String imgcode, String sig) {
		this.backurl = backurl;
		this.pushurl = pushurl;
		this.token = token;

		this.uname = uname;
		this.pwd = pwd;
		this.imgcode = StringUtils.isBlank(imgcode) ? "" : imgcode;
		this.sig = sig;

		weixin = new JSONObject();
		weixin.put("host", "mp.weixin.qq.com");
		weixin.put("base", "https://mp.weixin.qq.com");
		weixin.put("auth", "https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN");
		weixin.put(
				"call",
				"https://mp.weixin.qq.com/advanced/callbackprofile?t=ajax-response&token=%s&lang=zh_CN");
		weixin.put("start",
				"https://mp.weixin.qq.com/misc/skeyform?form=advancedswitchform");
		weixin.put("back",
				"https://mp.weixin.qq.com/merchant/myservice?action=set_oauth_domain&f=json");
		weixin.put("verifycode",
				"https://mp.weixin.qq.com/cgi-bin/verifycode?username=" + uname
						+ "&r=%s");
		weixin.put("bedeveloper",
				"https://mp.weixin.qq.com/advanced/advanced?action=agreement");

		List<BasicHeader> headers = new ArrayList<BasicHeader>();
		headers.add(new BasicHeader("Origin", weixin.getString("base")));
		headers.add(new BasicHeader("Connection", "keep-alive"));
		headers.add(new BasicHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36"));

		client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.BROWSER_COMPATIBILITY);
		client.getParams().setBooleanParameter(
				"http.protocol.single-cookie-header", true);
		client.getParams().setParameter(ClientPNames.DEFAULT_HEADERS, headers);

		host = new HttpHost(weixin.getString("host"), -1, "https");
	}

	public JSONObject process() {
		// 1.登陆微信公众号
		step1_login();
		int code = weixin.getIntValue("code");
		if (code == 0) {
			// 2.收集相关信息
			step2_collect();
			code = weixin.getIntValue("code");
			// 3.填写服务器配置
			// 3-1.未初始化账号
			// 3-2.已配置账号
			if (code == 0) {
				step3_setting();
			}
			code = weixin.getIntValue("code");
			// 4.修改网页授权地址
			if (code == 0) {
				step4_back();
			}
			// 5.创建底部菜单 (调用封装好的API)
			// 5-1.订阅号
			// 5-2.服务号
			// 6.完成
		}
		return weixin;
	}

	/**
	 * step1:登录操作
	 */
	private void step1_login() {
		HttpPost method = new HttpPost(weixin.getString("auth"));
		try {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("username", uname));
			parameters.add(new BasicNameValuePair("pwd", DigestUtils.md5Hex(pwd
					.getBytes())));
			parameters.add(new BasicNameValuePair("f", "json"));
			parameters.add(new BasicNameValuePair("imgcode", imgcode));
			if (!StringUtils.isBlank(imgcode)) {
				method.addHeader("Cookie", "sig=" + sig);
			}
			method.setEntity(new UrlEncodedFormEntity(parameters, charset));
			method.addHeader("Referer", weixin.getString("base"));

			HttpResponse response = client.execute(host, method);
			HttpEntity entity = response.getEntity();
			Document root = Jsoup.parse(entity.getContent(), charset.name(),
					weixin.getString("base"));
			StatusLine line = response.getStatusLine();
			logger.info("step1_login--->status={},body=\n{}", line,
					root.toString());
			if (line.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject body = JSON.parseObject(root.body().text());

				String msg = "";
				int code = 0;
				switch (body.getIntValue("ret")
						+ body.getJSONObject("base_resp").getIntValue("ret")) {
				case -1:
					msg = "系统错误，请稍候再试。";
					code = -1;
					break;
				case -2:
					msg = "帐号或密码错误。";
					code = 100;
					break;
				case -23:
					msg = "您输入的帐号或者密码不正确，请重新输入。";
					code = 101;
					break;
				case -21:
					msg = "不存在该帐户。";
					code = 102;
					break;
				case -7:
					msg = "您目前处于访问受限状态。";
					code = 103;
					break;
				case -8:
					msg = "请输入图中的验证码";
					code = 104;
					break;
				case -27:
					msg = "您输入的验证码不正确，请重新输入";
					code = 105;
					break;
				case -26:
					msg = "该公众会议号已经过期，无法再登录使用。";
					code = 106;
					break;
				case 0:
					msg = "成功登录，正在跳转...";
					break;
				case -25:
					msg = "海外帐号请在公众平台海外版登录,<a href=\"http://admin.wechat.com/\">点击登录</a>";
					code = 107;
					break;
				default:
					msg = "未知错误";
					code = 108;
					break;
				}
				if (code == 0) {
					weixin.put(
							"urlToken",
							getQueryMap(body.getString("redirect_url")).get(
									"token"));
					weixin.put("indexUrl", String.format("%s%s",
							weixin.getString("base"),
							body.getString("redirect_url")));
					weixin.put("step", "1");
				} else {
					if (code == 104 || code == 105) {
						// 下载验证码
						HttpGet get = new HttpGet(String.format(
								weixin.getString("verifycode"),
								System.currentTimeMillis()));
						get.setHeaders(method.getAllHeaders());
						response = client.execute(host, get);
						StringBuffer base64 = new StringBuffer();
						base64.append("data:")
								.append(response.getFirstHeader("Content-Type")
										.getValue()).append(";base64,");
						base64.append(new String(
								Base64.encodeBase64(IOUtil.toByteArray(response
										.getEntity().getContent())), charset));
						weixin.put("verifydata", base64.toString());
						List<Cookie> cookieList = client.getCookieStore()
								.getCookies();
						for (Cookie cookie : cookieList) {
							if (cookie.getName().equals("sig")) {
								weixin.put("sig", cookie.getValue());
								break;
							}
						}
					}
					weixin.put("code", code);
					weixin.put("msg", msg);
				}
			} else {
				weixin.put("code", "-3");
				weixin.put("msg", "网络异常，请稍后重试！");
			}
		} catch (Exception e) {
			weixin.put("code", "-2");
			weixin.put("msg", "服务器繁忙，请稍后重试！");
			weixin.put("exception", e.getMessage());
			logger.error("step1_login catch error", e);
		} finally {
			if (weixin.getIntValue("code") != 0) {
				client.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * step2:收集信息
	 */
	private void step2_collect() {
		String url = weixin.getString("indexUrl");
		HttpGet method = new HttpGet(url);
		try {
			method.addHeader("Referer", weixin.getString("base"));

			HttpResponse response = client.execute(host, method);
			HttpEntity entity = response.getEntity();
			Document root = Jsoup.parse(entity.getContent(), charset.name(),
					weixin.getString("base"));
			StatusLine line = response.getStatusLine();
			logger.info("step2_setting--->status={},body=\n{}", line,
					root.toString());
			if (line.getStatusCode() == HttpStatus.SC_OK) {
				Element ele = root.getElementById("menuBar")
						.getElementsByTag("dl").last();
				url = ele.getElementsByTag("a").last().absUrl("href");

				weixin.put("developerUrl", url);

				method.addHeader("Referer", url);
				url = ele.previousElementSibling().getElementsByTag("a")
						.first().absUrl("href");
				weixin.put("settingUrl", url);
				method.setURI(URI.create(url));

				response = client.execute(host, method);
				entity = response.getEntity();
				root = Jsoup.parse(entity.getContent(), charset.name(),
						weixin.getString("base"));
				line = response.getStatusLine();
				weixin.put("step", "2-1");
				// 公众号配置页面
				if (line.getStatusCode() == HttpStatus.SC_OK) {
					Elements eles = root.getElementById("settingArea")
							.getElementsByTag("li");
					String key, value;
					for (Element element : eles) {
						key = element.getElementsByTag("h4").first().text();
						ele = element.getElementsByClass("meta_content")
								.first();
						if (ele.children().isEmpty()) {
							value = ele.text();
						} else {
							if (ele.child(0).tagName().equalsIgnoreCase("a")) {
								value = ele.child(0).absUrl("href");
							} else if (ele.child(0).tagName()
									.equalsIgnoreCase("img")) {
								value = ele.child(0).absUrl("src");
							} else {
								value = ele.text();
							}
						}
						weixin.put(accountMap.get(key), value);
					}
					weixin.put("isVerify", weixin.getString("weixinVerify")
							.contains("微信认证"));
					weixin.put("isService", weixin.getString("accountType")
							.contains("服务号"));
					weixin.put("isSubscribe", weixin.getString("accountType")
							.contains("订阅号"));
					value = weixin.getString("qrcodeUrl");

					method.setURI(URI.create(value));
					response = client.execute(host, method);
					weixin.put("qrcodeData", IOUtil.toByteArray(response
							.getEntity().getContent()));

					weixin.put("step", "2-2");
					// 开发者页面
					method.addHeader("Referer", url);
					method.setURI(URI.create(weixin.getString("developerUrl")));

					response = client.execute(host, method);
					entity = response.getEntity();
					root = Jsoup.parse(entity.getContent(), charset.name(),
							weixin.getString("base"));
					line = response.getStatusLine();

					if (line.getStatusCode() == HttpStatus.SC_OK) {
						// 还没有成为开发者 2014.10-06 jy.hu
						// 触发成为开发者动作
						ele = root.getElementById("js_toBeDeveloper");
						if (ele != null && ele.hasText()) {
							HttpPost post = new HttpPost(URI.create(weixin
									.getString("bedeveloper")));
							post.addHeader("Referer", url);
							List<NameValuePair> parameters = new ArrayList<NameValuePair>();
							parameters = new ArrayList<NameValuePair>();
							parameters.add(new BasicNameValuePair("token",
									weixin.getString("urlToken")));
							parameters.add(new BasicNameValuePair("f", "json"));
							parameters.add(new BasicNameValuePair("ajax", "1"));
							parameters.add(new BasicNameValuePair("lang",
									"zh_CN"));
							parameters.add(new BasicNameValuePair("random",
									System.currentTimeMillis() + ""));

							post.setEntity(new UrlEncodedFormEntity(parameters,
									charset));
							response = client.execute(host, post);
							entity = response.getEntity();
							root = Jsoup.parse(entity.getContent(),
									charset.name(), weixin.getString("base"));
							line = response.getStatusLine();
							logger.info(
									"step2_bedeveloper--->status={},body=\n{}",
									line, root.toString());
							if (line.getStatusCode() == HttpStatus.SC_OK) {
								JSONObject body = JSON.parseObject(root.body()
										.text());
								if (body.getIntValue("ret") == 0) {
									method.addHeader("Referer", url);
									method.setURI(URI.create(weixin
											.getString("developerUrl")));

									response = client.execute(host, method);
									entity = response.getEntity();
									root = Jsoup.parse(entity.getContent(),
											charset.name(),
											weixin.getString("base"));
								} else {
									weixin.put("code", "-100");
									weixin.put("msg", "成为开发者失败！");
									return;
								}
							}
						}
						// 初始化状态
						// 配置未启用状态
						// 配置已启用状态
						eles = root.getElementsByClass("developer_info_opr");
						if (eles != null && eles.hasText()) {
							weixin.put("developerModifyUrl", eles.first()
									.children().first().absUrl("href"));
							weixin.put("status",
									eles.text().contains("启用") ? "READY"
											: "RUNNING");
						} else {
							weixin.put("status", "INIT");
						}
						// appid&appsecret
						if (weixin.getBooleanValue("isService")
								|| (weixin.getBooleanValue("isSubscribe") && weixin
										.getBooleanValue("isVerify"))) {
							eles = root
									.getElementsByClass("developer_info_item")
									.first().children().last()
									.getElementsByClass("frm_controls");
							weixin.put("appId", eles.first().text());
							weixin.put("appSecret",
									eles.last().text().replace("重置", "").trim());
						}
						weixin.put("step", "2-3");
					}
				} else {
					weixin.put("code", "-3");
					weixin.put("msg", "网络异常，请稍后重试！");
				}
			} else {
				weixin.put("code", "-3");
				weixin.put("msg", "网络异常，请稍后重试！");
			}
		} catch (Exception e) {
			weixin.put("code", "-2");
			weixin.put("msg", "服务器繁忙，请稍后重试！");
			weixin.put("exception", e.getMessage());
			logger.error("step2_collect catch error", e);
		} finally {
			if (weixin.getIntValue("code") != 0) {
				client.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * step3:填写配置
	 */
	private void step3_setting() {
		HttpPost method = new HttpPost(String.format(weixin.getString("call"),
				weixin.getString("urlToken")));
		try {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("url", pushurl));
			parameters.add(new BasicNameValuePair("callback_token", token));
			// EncodingAESKey | 消息加解密方式(明文0,兼容1,安全2)
			parameters.add(new BasicNameValuePair("encoding_aeskey", RandomUtil
					.generateString(43)));
			parameters
					.add(new BasicNameValuePair("callback_encrypt_mode", "0"));
			parameters.add(new BasicNameValuePair("operation_seq", RandomUtil
					.generateStringByNumberChar(9)));
			method.setEntity(new UrlEncodedFormEntity(parameters, charset));
			method.addHeader("Referer", weixin.getString("developerModifyUrl"));

			HttpResponse response = client.execute(host, method);
			HttpEntity entity = response.getEntity();
			Document root = Jsoup.parse(entity.getContent(), charset.name(),
					weixin.getString("base"));
			StatusLine line = response.getStatusLine();
			logger.info("step3_setting--->status={},body=\n{}", line,
					root.toString());
			if (line.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject body = JSON.parseObject(root.body().text());
				String msg = "";
				int code = 0;
				switch (body.getIntValue("ret")
						+ body.getJSONObject("base_resp").getIntValue("ret")) {
				case -201:
					msg = "无效的URL";
					code = 200;
					break;
				case -202:
					msg = "无效的Token";
					code = 201;
					break;
				case -203:
					msg = "操作频率太快，请休息一下。";
					code = 202;
					break;
				case -204:
					msg = "请先在设置页面完善当前帐号信息";
					code = 203;
					break;
				case -205:
					msg = "该URL可能存在安全风险，请检查";
					code = 207;
				case -301:
					msg = "请求URL超时";
					code = 204;
					break;
				case -302:
					msg = "服务器没有正确响应Token验证，请稍后重试";
					code = 205;
					break;
				case -104:
					msg = "参数错误，请重新填写。";
					code = 206;
					break;
				case 0:
					msg = "配置成功..";
					break;
				default:
					msg = "未知错误";
					code = 108;
					break;
				}
				if (code == 0) {
					// 触发启用按钮
					if (!weixin.getString("status").equals("RUNNING")) {
						parameters = new ArrayList<NameValuePair>();
						parameters.add(new BasicNameValuePair("token", weixin
								.getString("urlToken")));
						parameters.add(new BasicNameValuePair("f", "json"));
						parameters.add(new BasicNameValuePair("ajax", "1"));
						parameters.add(new BasicNameValuePair("flag", "1"));
						parameters.add(new BasicNameValuePair("type", "2"));
						parameters.add(new BasicNameValuePair("lang", "zh_CN"));
						parameters.add(new BasicNameValuePair("random", System
								.currentTimeMillis() + ""));

						method.setEntity(new UrlEncodedFormEntity(parameters,
								charset));
						method.setURI(URI.create(weixin.getString("start")));

						response = client.execute(host, method);
						entity = response.getEntity();
						root = Jsoup.parse(entity.getContent(), charset.name(),
								weixin.getString("base"));
						line = response.getStatusLine();
						logger.info("step3_setting--->status={},body=\n{}",
								line, root.toString());
						if (line.getStatusCode() == HttpStatus.SC_OK) {
							body = JSON.parseObject(root.body().text());
							if (body.getIntValue("ret")
									+ body.getJSONObject("base_resp")
											.getIntValue("ret") != 0) {
								weixin.put("code", 300);
								weixin.put("msg", "启用开发者模式失败，请稍后再试!");
							}
						}
					}
					weixin.put("step", "3");
				} else {
					weixin.put("code", code);
					weixin.put("msg", msg);
				}
			} else {
				weixin.put("code", "-3");
				weixin.put("msg", "网络异常，请稍后重试！");
			}
		} catch (Exception e) {
			weixin.put("code", "-2");
			weixin.put("msg", "服务器繁忙，请稍后重试！");
			weixin.put("exception", e.getMessage());
			logger.error("step3_setting catch error", e);
		} finally {
			if (weixin.getIntValue("code") != 0) {
				client.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * step4:修改回调
	 */
	private void step4_back() {
		try {
			if (weixin.getBooleanValue("isVerify")) {
				HttpPost method = new HttpPost(weixin.getString("back"));

				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("domain", backurl));
				parameters.add(new BasicNameValuePair("token", weixin
						.getString("urlToken")));
				parameters.add(new BasicNameValuePair("f", "json"));
				parameters.add(new BasicNameValuePair("ajax", "1"));
				parameters.add(new BasicNameValuePair("flag", "1"));
				parameters.add(new BasicNameValuePair("lang", "zh_CN"));
				parameters.add(new BasicNameValuePair("random", System
						.currentTimeMillis() + ""));
				method.setEntity(new UrlEncodedFormEntity(parameters, charset));
				method.addHeader("Referer", weixin.getString("developerUrl"));

				HttpResponse response = client.execute(host, method);
				HttpEntity entity = response.getEntity();
				Document root = Jsoup.parse(entity.getContent(),
						charset.name(), weixin.getString("base"));
				StatusLine line = response.getStatusLine();
				logger.info("step4_back--->status={},body=\n{}", line,
						root.toString());
				if (line.getStatusCode() == HttpStatus.SC_OK) {
					JSONObject body = JSON.parseObject(root.body().text());
					if (body.getIntValue("ret")
							+ body.getJSONObject("base_resp")
									.getIntValue("ret") != 0) {
						weixin.put("code", "400");
						weixin.put("msg", "修改授权回调地址失败!");
					}
					weixin.put("step", "4");
				}
			} else {
				logger.info("公众号尚未认证，放弃本次修改授权回调地址操作。{}", weixin);
			}
		} catch (Exception e) {
			weixin.put("code", "-2");
			weixin.put("msg", "服务器繁忙，请稍后重试！");
			weixin.put("exception", e.getMessage());
			logger.error("step4_back catch error", e);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

	private Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}
}
