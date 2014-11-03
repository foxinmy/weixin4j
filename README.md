weixin4j
========

微信开发工具包
-------------

功能列表
-------
* **weixin4j-mp**

  `公众平台API封装`
	
  `netty构建服务器`
	
  `微信支付(公众号)`
	
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

  + **weixin4j-mp**: `weixin.properties`切分为API调用地址/公众号信息两部分
   
  + **weixin4j-base**: `TokenApi`重命名为`TokenHolder`
  
  + **weixin4j-base**:新增`WeixinConfig`等类

* 2014-11-03

  + **weixin-mp**: 分离为`weixin-mp-api`和`weixin-mp-server`两个工程
   
  + **weixin-mp**: 加入支付模块

接下来
------
* 退款&对账

* 企业号API封装

* 公众号智能接口

* 微信消息加密

* 被扫支付

* 公众号多客服

* 微信小店

* 微信卡券