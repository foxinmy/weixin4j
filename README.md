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
###1.maven依赖(1.1,2014-12-18 released)
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

`git clone`&`mvn package -Prelease`,到相应的target目录下将`weixin4j-[mp|qy]-full`包或者`weixin4j-base`和`weixin4j-[mp|qy]-api`引入到自己的工程.

如何获取netty部分
---------------
netty的代码没有放到maven中心仓库,也没什么意义,因为最终需要自己去实现具体的业务逻辑,

下载winxin4j-[mp|qy]-server项目的源代码复制到自己的工程内,当然也可以在上面直接开发.

[更新LOG](./change.log)
----------------------
  
接下来
------
* 公众号数据分析接口

* 公众号服务应用

* 企业号第三方应用

* 微信小店

* 微信卡券