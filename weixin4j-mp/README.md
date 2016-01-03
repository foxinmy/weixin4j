weixin4j-mp
===========

[微信公众平台](http://mp.weixin.qq.com/wiki)开发工具包
----------------------------------------------------

功能列表
-------

  * CustomApi `多客服API`

  * DataApi `数据统计API`

  * GroupApi `分组管理API`

  * HelperApi `辅助API`

  * MassApi `群发消息API`

  * MediaApi `上传/下载媒体文件API`

  * MenuApi `底部菜单API`

  * NotifyApi `客服消息API`

  * OauthApi `oauth授权API`

  * Pay2Api `V2支付API`

  * QrApi `二维码API`

  * TmplApi `模板消息API`

  * UserApi `用户管理API`

如何使用
--------
0.maven依赖(1.6.6,2015-12-31 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-mp</artifactId>
	    <version>1.6.6</version>
	</dependency>
1.需新增或拷贝`weixin4j.properties`文件到项目的`classpath`中

weixin4j.properties说明

| 属性名         |       说明      |
| :----------	| :-------------- |
| weixin4j.account     	| 微信公众号信息 `json格式`(使用new WeixinProxy()缺省构造器时须填写)  |
| weixin4j.token.path  	| 使用FileTokenStorager时token保存的物理路径(非必须填写) |
| weixin4j.qrcode.path     	| 调用二维码接口时保存二维码图片的物理路径(非必须填写) |
| weixin4j.media.path  	| 调用媒体接口时保存媒体文件的物理路径(非必须填写) |
| weixin4j.bill.path   	| 调用下载对账单接口保存文件的物理路径(非必须填写) |
| weixin4j.certificate.file     	| 调用某些接口(支付相关)强制需要auth的ca授权文件(按须填写) |
| weixin4j.user.oauth.redirect.uri     | 调用OauthApi接口时需要填写的重定向路径(非必须填写) |

完整填写示例(properties中换行用右斜杆\\)

	weixin4j.account={"id":"appId","secret":"appSecret",\
		"mchId":"V3.x版本下的微信商户号",\
		"partnerId":"V2版本下的财付通的商户号",\
		"partnerKey":"V2版本下的财付通商户权限密钥Key",\
		"paySignKey":"微信支付中调用API的密钥"}
	
	weixin4j.token.path=/tmp/weixin4j/token
	weixin4j.qrcode.path=/tmp/weixin4j/qrcode
	weixin4j.media.path=/tmp/weixin4j/media
	weixin4j.bill.path=/tmp/weixin4j/bill
	# ca证书存放的完整路径 (V2版本后缀为*.pfx,V3版本后缀为*.p12)
	weixin4j.certificate.file=/tmp/weixin4j/xxxxx.p12
	#classpath路径下:weixin4j.certificate.file=classpath:xxxxx.p12
	
	#公众号登陆授权的重定向路径(使用OauthApi时需要填写)
	weixin4j.user.oauth.redirect.uri=http://xxx

2.实例化微信公众号接口代理对象,调用具体的API方法

	// 微信公众号API 使用classpath的weixin4j.properties
    WeixinProxy weixinProxy = new WeixinProxy();
    // 直接传入公众号信息
    // weixinProxy = new WeixinProxy(appid,appsecret);
    weixinProxy.getUser(openId);
    // 微信支付API 使用classpath的weixin4j.properties
    WeixinPayProxy weixinPayProxy = new WeixinPayProxy();
    // 直接构造WexinAccount对象
    // weixinPayProxy = new WeixinPayProxy(weixinAccount);
    weixinPayProxy.orderQuery(idQuery);

> 针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenStorager`,默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用[RedisTokenStorager](../weixin4j-base/src/main/java/com/foxinmy/weixin4j/token/RedisTokenStorager.java).
>
>   WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenStorager());

>   // weixinProxy = new WeixinProxy(new RedisTokenStorager(appid,appsecret));

[更新LOG](./CHANGE.md)
----------------------