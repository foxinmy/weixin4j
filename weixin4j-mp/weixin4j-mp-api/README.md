weixin4j-mp-api
===============

[微信公众平台](http://mp.weixin.qq.com/wiki)开发工具包
----------------------------------------------------

功能列表
-------

* MediaApi `上传/下载媒体文件API`

* NotifyApi `客服消息API`

* CustomApi `多客服API`

* MassApi `群发消息API`

* UserApi `用户管理API`

* GroupApi `分组管理API`

* MenuApi `底部菜单API`

* QrApi `二维码API`

* TmplApi `模板消息API`

* HelperApi `辅助API`

* PayApi `支付API`

如何使用
--------
1.API工程可以单独打包到其他项目中使用,需新增或拷贝`weixin.properties`文件到项目的`classpath`中

weixin.properties说明

| 属性名       |       说明      |
| :---------- | :-------------- |
| account     | 微信公众号信息 `json格式`  |
| token_path  | 使用FileTokenHolder时token保存的物理路径 |
| qr_path     | 调用二维码接口时保存二维码图片的物理路径 |
| media_path  | 调用媒体接口时保存媒体文件的物理路径 |
| bill_path   | 调用下载对账单接口保存excel文件的物理路径 |
| ca_file     | 调用某些接口(支付相关)强制需要auth的ca授权文件 |

示例(properties中换行用右斜杆\\)

> account={"id":"appId","secret":"appSecret",
> "token":"开放者的token","openId":"公众号的openid 非必须",
> "encodingAesKey":"公众号设置了加密方式且为「安全模式」需要填入",
> "mchId":"V3.x版本下的微信商户号",
> "partnerId":"财付通的商户号","partnerKey":"财付通商户权限密钥Key",
> "version":"针对微信支付的版本号(2,3),如果不填则按照mchId非空与否来判断",
> "paySignKey":"微信支付中调用API的密钥"} </br>
> token_path=/tmp/weixin/token </br>
> qr_path=/tmp/weixin/qr </br>
> media_path=/tmp/weixin/media </br>
> bill_path=/tmp/weixin/bill </br>
> ca_file=/tmp/weixin/xxxxx.p12 | xxxx.pfx </br>

2.实例化一个`WeixinProxy`对象,调用API,需要强调的是如果只传入appid,appsecret两个参数将无法调用支付相关接口

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(appid,appsecret);
    // weixinProxy = new WeixinProxy(weixinAccount);
    weixinProxy.getUser(openId);

3.针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenHolder`(继承`AbstractTokenHolder`并重写`getToken`方法),默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用`RedisTokenHolder`.

    WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenHolder());
    // weixinProxy = new WeixinProxy(new RedisTokenHolder(weixinAccount));
    
4.`mvn package`.
	
更新LOG
-------
* 2014-10-27

  + 用netty构建http服务器&消息分发

* 2014-10-28
   
  + 调整`ActionMapping`抽象化
   
* 2014-10-31

  + `weixin.properties`切分为API调用地址和公众号appid等信息两部分
   
* 2014-11-03

  + 分离为`weixin-mp-api`和`weixin-mp-server`两个工程
   
  + 新增`支付模块`

* 2014-11-06
 
  + 新增V3版本`退款申请`接口
  
* 2014-11-08
 
  + 新增V2版本`退款申请`、`退款查询`、`对账单下载`三个接口
  
  + 新增一个简单的`语义理解`接口

* 2014-11-11

  + 自定义`assembly`将`weixin4j-base`工程也一起打包(`weixin4j-mp-api-full.jar`)
 
* 2014-11-15

  + 新增获取`微信服务器IP地址接口`
  
* 2014-11-16

  + 新增`多客服`接口
 
* 2014-11-17

  + 新增`冲正`和`被扫支付`接口