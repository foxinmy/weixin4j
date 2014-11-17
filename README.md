weixin4j
========

微信开发工具包
-------------

功能列表
-------
* **weixin4j-mp**

  `公众平台API封装`
	
  `微信支付(公众号)`
  
  `netty构建服务器`
	
* **weixin4j-qy**

  `企业号API封装`
	
更新LOG
-------
* 2014-10-27
 
  + maven多模块分离
 
  + **weixin4j-mp**: 用netty构建http服务器并支持消息分发
 
* 2014-10-28
   
  + **weixin4j-mp**: 调整`ActionMapping`抽象化

* 2014-10-31

  + **weixin4j-mp**: `weixin.properties`切分为API调用地址和公众号信息两部分
   
  + **weixin4j-base**: `TokenApi`重命名为`TokenHolder`
  
  + **weixin4j-base**:新增`WeixinConfig`等类

* 2014-11-03

  + **weixin4j-mp**: 分离为`weixin-mp-api`和`weixin-mp-server`两个工程
   
  + **weixin4j-mp**: 加入支付模块
  
* 2014-11-05

  + 优化了代码

* 2014-11-06
 
  + **weixin4j-base**: 删除`WeixinConfig`类只保留`WeixinAccount`类
  
  + **weixin4j-mp**: 新增V3版本`退款接口`

* 2014-11-08
 
  + **weixin4j-mp**: 新增V2版本`退款申请`、`退款查询`、`对账单下载`三个接口
  
  + **weixin4j-mp**: 新增一个简单的`语义理解`接口

* 2014-11-11

  + **weixin4j-mp**: 自定义`assembly`将`weixin4j-base`工程也一起打包(`weixin4j-mp-api-full.jar`)

* 2014-11-15

  + **weixin4j-base**: 新增aes加密解密函数
  
  + **weixin4j-mp**: 新增获取`微信服务器IP地址`接口
    
  + **weixin4j-mp**: 解决`server工程`打包后不能运行问题(`ClassUtil`无法获取jar包里面的类)
  
  + **weixin4j-mp**: 新增被动消息的`加密`以及回复消息的`解密`
 
* 2014-11-16

  + **weixin4j-mp**: 新增`多客服`接口
  
* 2014-11-17

  + **weixin4j-mp**: 新增`冲正`和`被扫支付`接口

接下来
------
* 企业号API封装

* 微信小店

* 微信卡券