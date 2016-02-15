weixin4j
========

微信开发工具包
-------------
 > `weixin4j`是一个用java编写针对微信开发的工具包,包含[weixin4j-mp](./weixin4j-mp)(微信公众平台API)、[weixin4j-qy](./weixin4j-qy)(微信企业号API)以及[weixin4j-server](./weixin4j-server)(微信回调消息服务器)三个工程.

模块说明
-------
* **weixin4j-base**

  `Http Client实现&token实现&微信支付实现(刷卡/扫码/JS/APP/WAP/现金红包/企业付款)`

* **weixin4j-mp**

  `公众平台API封装`
	
* **weixin4j-qy**

  `企业号API封装`
  
* **weixin4j-server**
  
  `netty服务器&消息分发`

如何获取
----------
###1.maven依赖
微信公众平台API(1.6.7,2016-02-04 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-mp</artifactId>
	    <version>1.6.7</version>
	</dependency>
微信企业号API(1.6.7,2016-02-04 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-qy</artifactId>
	    <version>1.6.7</version>
	</dependency>
微信回调消息服务器(1.1.6,2016-02-04 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-server</artifactId>
	    <version>1.1.6</version>
	</dependency>

###2.直接下载jar包

  * [weixin4j-mp-xx-full.jar](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.foxinmy%22%20AND%20a%3A%22weixin4j-mp%22)&nbsp;[weixin4j-qy-xx-full.jar](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.foxinmy%22%20AND%20a%3A%22weixin4j-qy%22)&nbsp;[weixin4j-server-xx.jar](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.foxinmy%22%20AND%20a%3A%22weixin4j-server%22)

  * weixin4j-mp & weixin4j-qy 所需的依赖包: [fastjson1.2+](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.alibaba%22%20AND%20a%3A%22fastjson%22)&nbsp;[jedis2.6+(可选)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22redis.clients%22%20AND%20a%3A%22jedis%22)
  
  * weixin4j-server 所需的依赖包: [netty4.x](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.netty%22%20AND%20a%3A%22netty-all%22)

###3.从源码打包

`git clone`&`mvn package`

[如何使用](https://github.com/foxinmy/weixin4j/wiki/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8SDK)
--------

[更新LOG](./CHANGE.md)
----------------------
  
接下来
------
* [公众号第三方服务应用](https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318292&token=&lang=zh_CN)

* [微信硬件平台](http://iot.weixin.qq.com/)

* [微信小店](http://mp.weixin.qq.com/wiki/6/ae98ac4a7219405153cedc9dddccacca.html)

* [微信卡券](http://mp.weixin.qq.com/wiki/10/597cb57750f375a4b37e2536fd3331ea.html)

* [微信门店](http://mp.weixin.qq.com/wiki/11/081986f089826bf94393bef9bf287b8b.html)

* [微信摇一摇](http://mp.weixin.qq.com/wiki/19/9fe9fdbb50fee9f9660438c551142ccf.html)

* [微信连WI-FI](http://mp.weixin.qq.com/wiki/9/fd2d692e28b938a8d618f57cf9c79fb1.html)

* [微信扫一扫](http://mp.weixin.qq.com/wiki/19/e833eb10470cc25cad4719677c46ecdb.html)