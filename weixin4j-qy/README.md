weixin4j-qy
===========

[微信企业号](http://qydev.weixin.qq.com/wiki/index.php)开发工具包
---------------------------------------------------------------

功能列表
-------

  * AgentApi `应用设置API`

  * BatchApi `批量任务API`

  * ChatApi `会话服务API`

  * Helper `辅助API`

  * MediaApi `媒体素材API`
  
  * MenuApi `菜单管理API`
  
  * NotifyApi `消息发送API`

  * OauthApi `oauth授权登陆API`

  * PartyApi `部门管理API`

  * ProviderApi `服务商API`

  * SuiteApi `第三方应用API`

  * TagApi `标签管理API`
	
  * UserApi `成员管理API`
  

如何使用
--------
0.maven依赖(1.6.6,2015-12-31 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-qy</artifactId>
	    <version>1.6.6</version>
	</dependency>
1.需新增或拷贝`weixin4j.properties`文件到项目的`classpath`中

weixin4j.properties说明

| 属性名       |       说明      |
| :---------- | :-------------- |
| weixin4j.account     | 微信企业号信息 `json格式`(使用new WeixinProxy()缺省构造器时须填写) |
| weixin4j.token.path  | 使用FileTokenStorager时token保存的物理路径(非必须填写) |
| weixin4j.media.path  | 调用媒体接口时保存媒体文件的物理路径(非必须填写) |
| weixin4j.bill.path   	| 调用下载对账单接口保存文件的物理路径(非必须填写) |
| weixin4j.certificate.file     	| 调用某些接口(支付相关)强制需要auth的ca授权文件(非必须填写) |
| weixin4j.user.oauth.redirect.uri     | 企业号用户身份授权后重定向的url(OauthApi接口) |
| weixin4j.third.oauth.redirect.uri	  | 企业号第三方提供商授权后重定向的url(OauthApi接口) |
| weixin4j.suite.oauth.redirect.uri     | 企业号第三方应用套件授权后重定向的url(OauthApi接口) |

示例(properties中换行用右斜杆\\)

	weixin4j.account={"id":"corpid","secret":"corpsecret",\
	"suites":[{"id":"应用套件的id","secret":"应用套件的secret"}],\
	"providerSecret":"第三方提供商secret(企业号登陆)",\
	"chatSecret":"消息服务secret(企业号消息服务,暂时没用到)",\
	"mchId":"微信商户号 微信支付时需要填入",\
	"certificateKey":"加载支付证书文件的密码 如果不填写则默认获取mchId作为密码",\
	"paySignKey":"微信支付中调用API的密钥 微信支付时需要填入"}
	
	# 使用FileTokenStorager时token的存放路径(如果不填则默认为Weixin4jConst#DEFAULT_TOKEN_PATH)
	weixin4j.token.path=/tmp/weixin4j/token
	# 二维码保存路径(如果不填则默认为Weixin4jConst#DEFAULT_TOKEN_PATH)
	weixin4j.qrcode.path=/tmp/weixin4j/qrcode
	# 媒体文件保存路径(如果不填则默认为Weixin4jConst#DEFAULT_MEDIA_PATH)
	weixin4j.media.path=/tmp/weixin4j/media
	# 对账单保存路径(如果不填则默认为Weixin4jConst#DEFAULT_BILL_PATH)
	weixin4j.bill.path=/tmp/weixin4j/bill
	# ca证书存放的完整路径
	weixin4j.certificate.file=/tmp/weixin4j/xxxxx.p12
	# classpath路径下可以这么写(如果不填则默认为Weixin4jConst#DEFAULT_CAFILE_PATH)
	# weixin4j.certificate.file=classpath:xxxxx.pfx
	
	# 企业号用户身份授权后重定向的url(在使用OauthApi时填写)
	weixin4j.user.oauth.redirect.uri=
	# 企业号第三方提供商授权后重定向的url(在使用OauthApi时填写)
	weixin4j.third.oauth.redirect.uri=
	# 企业号第三方应用套件授权后重定向的url(在使用OauthApi时填写)
	weixin4j.suite.oauth.redirect.uri=

2.实例化微信企业号接口代理对象,调用具体的API方法

	// 微信企业号API 使用classpath的weixin4j.properties
    WeixinProxy weixinProxy = new WeixinProxy();
     // 直接传入企业号信息
    // weixinProxy = new WeixinProxy(corpid,corpsecret);
    weixinProxy.getUser(userid);
     // 微信支付API 使用classpath的weixin4j.properties
    WeixinPayProxy weixinPayProxy = new WeixinPayProxy();
    // 直接构造WexinAccount对象
    // weixinPayProxy = new WeixinPayProxy(weixinAccount);
    weixinPayProxy.orderQuery(idQuery);
    // 微信第三方应用API 使用classpath的weixin4j.properties
    WeixinSuiteProxy weixinSuiteProxy = new WeixinSuiteProxy();
    // 直接传入套件信息
    //weixinSuiteProxy = new WeixinSuiteProxy(suiteId,suiteSecret);
    weixinSuiteProxy.api().getOAuthInfo(authCorpid);

> 针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenStorager`,默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用[RedisTokenStorager](../weixin4j-base/src/main/java/com/foxinmy/weixin4j/token/RedisTokenStorager.java).

>   WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenStorager());

>   // weixinProxy = new WeixinProxy(new RedisTokenStorager(corpid,corpsecret));

[更新LOG](./CHANGE.md)
----------------------