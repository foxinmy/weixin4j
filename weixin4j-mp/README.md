weixin4j-mp
===========

[微信公众平台](http://mp.weixin.qq.com/wiki)开发工具包
----------------------------------------------------

功能列表
-------

* MediaApi `媒体素材API`

* NotifyApi `客服消息API`

* CustomApi `多客服API`

* MassApi `群发消息API`

* UserApi `用户管理API`

* GroupApi `分组管理API`

* MenuApi `底部菜单API`

* QrApi `二维码API`

* TmplApi `模板消息API`

* HelperApi `辅助API`

* Pay2Api `V2支付API`
  
* Pay3Api `V3支付API`

* CouponApi `代金券API`

* DataApi `数据统计API`

* OauthApi `oauth授权API`

* CashApi `现金API`

如何使用
--------
0.maven依赖(1.4,2015-05-16 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-mp</artifactId>
	    <version>1.4</version>
	</dependency>
1.需新增或拷贝`weixin4j.properties`文件到项目的`classpath`中

weixin4j.properties说明

| 属性名         |       说明      |
| :----------	| :-------------- |
| account     	| 微信公众号信息 `json格式`  |
| token_path  	| 使用FileTokenHolder时token保存的物理路径 |
| qr_path     	| 调用二维码接口时保存二维码图片的物理路径 |
| media_path  	| 调用媒体接口时保存媒体文件的物理路径 |
| bill_path   	| 调用下载对账单接口保存excel文件的物理路径 |
| ca_file     	| 调用某些接口(支付相关)强制需要auth的ca授权文件 |
| redirect_uri     | 调用OauthApi接口时需要填写的重定向路径 |

示例(properties中换行用右斜杆\\)

	account={"id":"appId","secret":"appSecret",\
		"token":"开放者的token",\
		"encodingAesKey":"公众号设置了加密方式且为「安全模式」时需要填入",\
		"mchId":"V3.x版本下的微信商户号",\
		"partnerId":"V2版本下的财付通的商户号",\
		"partnerKey":"V2版本下的财付通商户权限密钥Key",\
		"version":"针对微信支付的版本号(2,3),如果不填则按照mchId非空与否来判断",\
		"paySignKey":"微信支付中调用API的密钥"}
	
	token_path=/tmp/weixin4j/token
	qr_path=/tmp/weixin4j/qr
	media_path=/tmp/weixin4j/media
	bill_path=/tmp/weixin4j/bill
	# ca证书存放的完整路径 (V2版本后缀为*.pfx,V3版本后缀为*.p12)
	ca_file=/tmp/weixin4j/xxxxx.p12
	#classpath路径下:ca_file=classpath:xxxxx.p12
	
	#微信登陆授权的重定向路径(使用OauthApi时需要填写)
	redirect_uri=http://xxx

2.实例化一个`WeixinProxy`对象,调用API,需要强调的是如果只传入appid,appsecret两个参数将无法调用支付相关接口

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(appid,appsecret);
    // weixinProxy = new WeixinProxy(weixinAccount);
    weixinProxy.getUser(openId);

> 针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenHolder`,默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用[RedisTokenHolder](https://github.com/foxinmy/weixin4j/wiki/%E7%94%A8redis%E4%BF%9D%E5%AD%98token).
>
>   WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenHolder());

>   // weixinProxy = new WeixinProxy(new RedisTokenHolder(weixinAccount));

[更新LOG](./CHANGE.md)
----------------------