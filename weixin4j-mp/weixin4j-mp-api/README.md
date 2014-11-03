weixin4j-mp-api
===============

微信[公众平台](http://mp.weixin.qq.com/wiki)开发工具包

功能列表
-------

* MediaApi `上传/下载媒体文件API`

* NotifyApi `客服消息API`

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
1.API工程可以单独使用,需新增或拷贝`weixin.properties`文件到`classpath`

weixin.properties

| 属性名       |       说明      |
| :---------- | :-------------- |
| account     | 微信公众号信息 `json格式`  |
| token_path  | 使用FileTokenHolder时token保存的物理路径 |
| qr_path     | 调用二维码接口时保存的物理路径 |

示例

> account={"appId":"appId","appSecret":"appSecret",
> "token":"开放者的token 非必须","openId":"公众号的openid 非必须",
> "mchId":"V3.x版本下的微信商户号",
> "partnerId":"财付通的商户号","partnerKey":"财付通商户权限密钥Key",
> "paySignKey":"微信支付中调用API的密钥"} <br/>
> token_path=/tmp/weixin/token <br/>
> qr_path=/tmp/weixin/qr <br/>
> media_path=/tmp/weixin/media <br/>
> bill_path=/tmp/weixin/bill <br/>

2.实例化一个`WeixinProxy`对象,调用API.

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(appid,appsecret);
    weixinProxy.getUser(openId);

3.针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenHolder`(继承`AbstractTokenHolder`类并重写`getToken`方法),默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用`RedisTokenHolder`.

    WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenHolder());
    // weixinProxy = new WeixinProxy(new RedisTokenHolder(appid,appsecret));
    
4.mvn package.
	
更新LOG
-------
* 2014-10-27

  + 用netty构建http服务器并支持消息分发

* 2014-10-28
   
  + 调整`ActionMapping`抽象化
   
* 2014-10-31

  + `weixin.properties`切分为API调用地址/公众号信息两部分
   
* 2014-11-03

  + 分离为`weixin-mp-api`和`weixin-mp-server`两个工程
   
  + 加入`支付模块`