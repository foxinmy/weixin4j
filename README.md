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

  + **weixin-mp**: 分离为`weixin-mp-api`和`weixin-mp-server`两个工程
   
  + **weixin-mp**: 加入支付模块
  
* 2014-11-05

  + 优化了代码

* 2014-11-06
 
  + **weixin-base**: 删除`WeixinConfig`类只保留`WeixinAccount`类
  
  + **weixin-mp**: 新增`退款接口`

* 2014-11-08
 
  + **weixin-mp**: 新增V2版本`退款申请`、`退款查询`、`对账单下载`三个接口
  
  + **weixin-mp**: 新增一个简单的`语义理解`接口

接下来
------
* 公众号智能接口

* 微信消息加密

* 被扫支付

* 企业号API封装

* 公众号多客服

* 微信小店

* 微信卡券