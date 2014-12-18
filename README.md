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
+ `weixin4j`包含「微信公众平台」和「微信企业号」的API封装以及一个半成品的netty服务实现.

+ API的成功调用依赖于正确的appid等数据,填写格式说明见API工程下的README.md文件.

+ 如需使用netty服务,可以在相应的action中实现自己的具体业务,打包后启动服务即可.

如何获取API
----------
###1.maven依赖(目前最新版本1.1)
微信公众平台API

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-mp-api</artifactId>
	    <version>1.1</version>
	</dependency>
微信企业号API

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-qy-api</artifactId>
	    <version>1.1</version>
	</dependency>
以上依赖如果出现Missing artifact错误 请尝试在eclipse里这么做

  + 进入 Window > Show View > Other > Maven Repositories 展开 Global Repositories 在group或者central上右键执行`update index` 操作
  
  + 或者进入 Windows > Preferences > Maven 选中 `Download repository index updates on startup` 即可


###2.直接下载jar包

https://github.com/foxinmy/weixin4j/releases

###3.从源码打包

`git clone`到本地在根目录下执行`mvn package`命令得到jar包,到target目录下将`weixin4j-*-full`包或者`weixin4j-base`和`weixin4j-*-api`引入到自己的工程.

如何获取netty部分
---------------
netty的代码没有放到maven中心仓库,也没什么意义,因为最终需要自己去实现具体的业务逻辑,

目前的做法是建议下载server部分的源代码复制到自己的工程内,当然你也可以直接在上面进行开发.

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
  
* 2014-11-27

  + **weixin-base**: 将BaseApi移入
  
* 2014-11-29

  + 重构了POM
  
* 2014-12-12

  + **weixin4j-mp**: 新增设置`模板消息所处行业`、`获取模板消息ID`接口
  
* 2014-12-15

  + **weixin4j-mp**: 修改某些函数在注释上的参数描述错误
  
  + **weixin4j-mp**: 调整PayUtil类中`createPayJsRequestJsonV3`的形参位置,`notify_url`与`spbill_create_ip`对换与V2保持一致
  
  + **weixin4j-mp**: 在PayUtil类中新增paySign重载版本函数,避免在某些地方产生歧义造成签名错误(appid,appKey)
  
  + **weixin4j-mp**: 修正V3版本JSAPI接口支付签名错误bug(坑)
  
* 2014-12-16

  + **weixin4j-mp**: 调整方法上@see注解的文档说明接口url
  
  + **weixin4j-mp**: 新增群发消息预览、状态查询接口
  
  + **weixin4j-mp**: 新增多客服添加账号、更新账号、上传头像、删除账号接口

* 2014-12-18
  
  + clear code with findbugs plugin
  
  + change the version to 1.1
  
接下来
------
* 微信小店

* 微信卡券