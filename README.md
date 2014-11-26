weixin4j
========

微信开发工具包
-------------

功能列表
-------
* **weixin4j-mp**

  `公众平台API封装`
	
  `微信支付(公众号)`
  
  `netty服务器&消息分发`
	
* **weixin4j-qy**

  `企业号API封装`
  
  `netty服务器&消息分发`
  
项目说明
-------
1.`weixin4j`包含「微信公众平台」和「微信企业号」的API封装以及一个半成品的netty服务实现.

2.API的成功调用依赖于正确的appid等数据,其填写格式在每个项目下的README.md文件中都有说明.

3.在`weixin-4j`根目录执行`mvn package`命令得到jar包后,将`weixin4j-*-api-full`包或者`weixin4j-base`跟`weixin4j-*-api`引入到自己的工程.

4.如需使用netty服务,则可以在相应的action中实现自己的业务处理,打包后放到`正确的目录`下解压`weixin-*-server-bin.zip`执行`sh startup.sh start`便可启动服务.

	
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
  
* 2014-11-19

  + **weixin4j-base**: 新增`WeixinQyAccount`企业号账号信息类
  
  + **weixin4j-qy**: 得到`weixin4j-qy`和`weixin4j-qy-server`工程
  
  + **weixin4j-qy**: 新增`部门管理`接口
  
  + **weixin4j-qy**: 新增`用户管理`接口
  
  + **weixin4j-qy**: 新增`标签管理`接口

* 2014-11-23

  + **weixin4j-base**: 新增企业号消息体以及用`Responseable`,`Notifyable`,`Massable`三个接口标记不同的可接受的消息类型
  
  + **weixin4j-mp**: 重新定义(手贱)了「被动消息」「客服消息」「群发消息」的传输实体
  
  + **weixin4j-mp**: `WeixinServerBootstrap`重命名为`WeixinMpServerBootstrap`
  
  + **weixin4j-qy**: 新增`多媒体管理`接口
  
  + **weixin4j-qy**: 新增`发送消息`接口
  
  + **weixin4j-qy**: 新增`菜单管理`接口
  
* 2014-11-24

  + **weixin4j-base**: 将Action跟Mapping基础类并入到项目
  
  + **weixin4j-qy**: 新增netty服务与消息分发

接下来
------
* 微信小店

* 微信卡券