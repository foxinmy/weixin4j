weixin4j
========

微信开发工具包
-------------
 > `weixin4j`是一个用java编写针对微信开发的工具包,包含[weixin4j-mp](./weixin4j-mp)(微信公众平台API)、[weixin4j-qy](./weixin4j-qy)(微信企业号API)以及[weixin4j-server](./weixin4j-server)(微信回调消息服务器)三个工程.

功能列表
-------
* **weixin4j-mp**

  `公众平台API封装`
	
  `微信支付(刷卡/扫码/公众号)`
	
* **weixin4j-qy**

  `企业号API封装`
  
  `微信支付(刷卡/扫码/公众号)`
  
* **weixin4j-server**
  
  `netty服务器&消息分发`

如何获取
----------
###1.maven依赖
微信公众平台API(1.5.3,2015-08-13 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-mp</artifactId>
	    <version>1.5.3</version>
	</dependency>
微信企业号API(1.5.3,2015-08-13 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-qy</artifactId>
	    <version>1.5.3</version>
	</dependency>
微信回调消息服务器(1.0.5,2015-08-13 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-server</artifactId>
	    <version>1.0.5</version>
	</dependency>

以上依赖如果出现Missing artifact错误 请尝试在eclipse里这么做

  + 进入 Window > Show View > Other > Maven Repositories 展开 Global Repositories 在group或者central上右键执行`update index` 操作
  
  + 或者进入 Windows > Preferences > Maven 选中 `Download repository index updates on startup` 即可


###2.直接下载jar包

  * [weixin4j-mp-xx-full.jar](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.foxinmy%22%20AND%20a%3A%22weixin4j-mp%22)&nbsp;[weixin4j-qy-xx-full.jar](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.foxinmy%22%20AND%20a%3A%22weixin4j-qy%22)&nbsp;[weixin4j-server-xx.jar](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.foxinmy%22%20AND%20a%3A%22weixin4j-server%22)

  * weixin4j-mp & weixin4j-qy 所需的依赖包: [fastjson1.2.x](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.alibaba%22%20AND%20a%3A%22fastjson%22)
  
  * weixin4j-server 所需的依赖包: [netty4.x](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.netty%22%20AND%20a%3A%22netty-all%22)

###3.从源码打包

`git clone`&`mvn package`

[更新LOG](./CHANGE.md)
----------------------
  
接下来
------
* 公众号第三方服务应用

* 硬件设备 & 摇一摇周边

* 微信小店&门店

* 微信卡券