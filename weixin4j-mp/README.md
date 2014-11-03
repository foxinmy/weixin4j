weixin4j-mp
===========

@(weixin4j)[公众平台]

微信[公众平台](http://mp.weixin.qq.com/wiki)开发工具包
----------------------------------------------------

功能列表
-------
* weixin4j-mp-api

  + MediaApi `上传/下载媒体文件API`
	
  + NotifyApi `客服消息API`
	
  + MassApi `群发消息API`
	
  + UserApi `用户管理API`
	
  + GroupApi `分组管理API`
	
  + MenuApi `底部菜单API`
	
  + QrApi `二维码API`
	
  + TmplApi `模板消息API`
	
  + HelperApi `辅助API`

* weixin4j-mp-server

  `netty服务器 & 消息分发`

更新LOG
-------
* 2014-10-27

  + 用netty构建http服务器并支持消息分发

* 2014-10-28
   
  + 调整`ActionMapping`抽象化
   
* 2014-10-31

  + `weixin.properties`切分为API调用地址/公众号信息两部分
   
* 2014-11-03

  + `weixin-mp`分离为`weixin-mp-api`和`weixin-mp-server`两个工程
   
  + `weixin-mp`:加入支付模块