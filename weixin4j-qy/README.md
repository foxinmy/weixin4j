weixin4j-qy
===========

[微信企业号](http://qydev.weixin.qq.com/wiki/index.php)开发工具包
---------------------------------------------------------------

功能列表
-------

  * PartyApi `部门管理API`
	
  * UserApi `成员管理API`
  
  * TagApi `标签管理API`
  
  * MediaApi `媒体素材API`
  
  * MenuApi `菜单管理API`
  
  * NotifyApi `消息发送API`
  
  * AgentApi `应用设置API`
  
  * BatchApi `批量任务API`
  
  * OauthApi `oauth授权登陆API`
  
  * SuiteApi `第三方应用API`
  
  * Pay3Api `商户平台支付API`
	
  * CouponApi `代金券API`
	
  * CashApi `现金API`
  
  * ChatApi `会话服务API`

如何使用
--------
0.maven依赖(1.5.3,2015-08-13 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-qy</artifactId>
	    <version>1.5.3</version>
	</dependency>
1.需新增或拷贝`weixin4j.properties`文件到项目的`classpath`中

weixin4j.properties说明

| 属性名       |       说明      |
| :---------- | :-------------- |
| account     | 微信企业号信息 `json格式`(按需填写) |
| token_path  | 使用FileTokenStorager时token保存的物理路径 |
| media_path  | 调用媒体接口时保存媒体文件的物理路径 |
| bill_path   	| 调用下载对账单接口保存excel文件的物理路径 |
| ca_file     	| 调用某些接口(支付相关)强制需要auth的ca授权文件 |
| user_oauth_redirect_uri     | 企业号用户身份授权后重定向的url(OauthApi接口) |
| third_oauth_redirect_uri	  | 企业号第三方提供商授权后重定向的url(OauthApi接口) |
| suite_oauth_redirect_uri     | 企业号第三方应用套件授权后重定向的url(OauthApi接口) |

示例(properties中换行用右斜杆\\)

	account={"id":"corpid","secret":"corpsecret",\
		"suites":[{"id":"应用套件的id","secret":"应用套件的secret"}],\
		"providerSecret:"第三方提供商secret(企业号登陆)",\
		"chatSecret":"消息服务secret(企业号消息服务,暂时没用到)"}
	
	token_path=/tmp/weixin4j/token
	media_path=/tmp/weixin4j/media
	bill_path=/tmp/weixin4j/bill
	# ca证书存放的完整路径 (证书文件后缀为*.p12)
	ca_file=/tmp/weixin4j/xxxxx.p12
	#classpath路径下:ca_file=classpath:xxxxx.p12
	
	#企业号用户身份授权后重定向的url(使用OauthApi时需要填写)
	user_oauth_redirect_uri=http://xxx
	
	#企业号第三方管理员授权后重定向的url(使用OauthApi时需要填写)
	third_oauth_redirect_uri=http://xxx
	
	#企业号第三方应用套件授权后重定向的url(使用OauthApi时需要填写)
	suite_oauth_redirect_uri=http://xxx

2.实例化微信企业号接口代理对象,调用具体的API方法

	// 微信企业号API
    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(corpid,corpsecret);
    weixinProxy.getUser(userid);
    // 微信第三方应用API
    WeixinSuiteProxy weixinSuiteProxy = new WeixinSuiteProxy();
    //weixinSuiteProxy = new WeixinSuiteProxy(suiteId,suiteSecret);
    weixinSuiteProxy.api().getOAuthInfo(authCorpid);

> 针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenStorager`,默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用[RedisTokenStorager](https://github.com/foxinmy/weixin4j/wiki/%E7%94%A8redis%E4%BF%9D%E5%AD%98token).

>   WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenStorager());

>   // weixinProxy = new WeixinProxy(new RedisTokenStorager(corpid,corpsecret));

[更新LOG](./CHANGE.md)
----------------------