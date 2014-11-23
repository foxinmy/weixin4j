weixin4j-mp
===========

[微信公众平台](http://mp.weixin.qq.com/wiki)开发工具包
----------------------------------------------------

功能列表
-------
* **weixin4j-mp-api**

  + MediaApi `上传/下载媒体文件API`
	
  + NotifyApi `客服消息API`
  
  + CustomApi `多客服API`
	
  + MassApi `群发消息API`
	
  + UserApi `用户管理API`
	
  + GroupApi `分组管理API`
	
  + MenuApi `底部菜单API`
	
  + QrApi `二维码API`
	
  + TmplApi `模板消息API`
	
  + HelperApi `辅助API`

* **weixin4j-mp-server**

  + `netty服务器` & `消息分发`
  
  + 被动消息`AES`加密、解密

更新LOG
-------
* 2014-10-27

  + 用netty构建http服务器&消息分发

* 2014-10-28
   
  + 调整`ActionMapping`抽象化
   
* 2014-10-31

  + `weixin.properties`切分为API调用地址和公众号appid等信息两部分
   
* 2014-11-03

  + `weixin-mp`分离为`weixin4j-mp-api`和`weixin4j-mp-server`两个工程
   
  + **weixin4j-mp**: 新增`支付`模块

* 2014-11-06
  
  + **weixin4j-mp-api**: 新增V3版本`退款接口`

* 2014-11-08
 
  + **weixin4j-mp-api**: 新增V2版本`退款申请`、`退款查询`、`对账单下载`三个接口
  
  + **weixin4j-mp-api**: 新增一个简单的`语义理解`接口

* 2014-11-11

  + **weixin4j-mp-api**: 自定义`assembly`将`weixin4j-base`工程也一起打包(`weixin4j-mp-api-full.jar`)
 
* 2014-11-15

  + **weixin4j-mp-api**: 新增获取`微信服务器IP地址接口`
  
  + **weixin4j-mp-server**: 解决`server工程`打包后不能运行问题(`ClassUtil`无法获取jar包里面的类)
  
  + **weixin4j-mp-server**: 新增被动消息的`加密`以及回复消息的`解密`
  
* 2014-11-16

  + **weixin4j-mp-api**: 新增`多客服`接口
  
* 2014-11-17

  + **weixin4j-mp-api**: 新增`冲正`和`被扫支付`接口
  
* 2014-11-23

  + **weixin4j-mp-api**: 重新定义(手贱)了「被动消息」「客服消息」「群发消息」的传输实体
  
  + **weixin4j-mp-server**: `WeixinServerBootstrap`重命名为`WeixinMpServerBootstrap`