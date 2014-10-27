weixin4j-mp
===========

tencent weixin platform java sdk 微信公众平台开发工具包 http://mp.weixin.qq.com/wiki

功能列表
-------

* TokenApi token实现API

* MediaApi 上传/下载媒体文件API

* NotifyApi 客服消息API

* MassApi 群发消息API

* UserApi 用户管理API

* GroupApi 分组管理API

* MenuApi 底部菜单API

* QrApi 二维码API

* TmplApi 模板消息API

* HelperApi 辅助API

* netty服务器 & 消息分发


如何使用
--------

1.编辑weixin.properties文件,填入appid/appsecret信息,当然也可通过构造函数传入.

2.实例化一个WeixinProxy对象,如无特别指明appid/appsecret则使用weixin.properties中的值.

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(appid,appsecret);
    weixinProxy.getUser(openId);
    
3.针对token存储有两种方案,File存储/Redis存储,当然也可自己实现TokenApi,如无特别指明默认使用文件(xml)的方式保存token,如果环境中支持redis,建议使用RedisTokenApi.

    WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenApi());
    // weixinProxy = new WeixinProxy(new RedisTokenApi(appid,appsecret));

4.mvn package,得到一个zip的压缩包,解压到启动目录(见src/main/startup.sh/APP_HOME)

5.启动netty服务

    com.foxinmy.weixin4j.mp.startup.WeixinServiceBootstrap
    sh startup.sh start
	
更新LOG
-------
* 2014-10-27

   1).用netty构建http服务器并支持消息分发
