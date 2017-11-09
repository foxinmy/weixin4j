weixin4j
========

微信开发工具包
-------------
 > `weixin4j`是一个用Java编写针对微信开发的工具包,包含[weixin4j-mp](./weixin4j-mp)(微信公众平台API)、[weixin4j-qy](./weixin4j-qy)(微信企业号API)以及[weixin4j-server](./weixin4j-server)(微信回调消息服务器)三个工程.

模块说明
-------
* **weixin4j-base[1.7.8]**

  `Http Client实现&token实现&微信支付实现(刷卡/扫码/JS/APP/WAP/现金红包/企业付款)`,如果只使用`微信支付`功能可只引用此工程

* **weixin4j-mp[1.7.8]**

  `公众平台API封装`
	
* **weixin4j-qy[1.7.8]**

  `企业号API封装`
  
* **weixin4j-server[1.1.8]**
  
  `netty服务器&消息分发`
  
* **weixin4j-example**

  示例工程,包含了如何构建weixin4j-server服务器和与spring集成,建议看看

[如何使用](https://github.com/foxinmy/weixin4j/wiki)
--------

[更新LOG](./CHANGE.md)
----------------------
  
接下来
------
* [微信硬件平台](http://iot.weixin.qq.com/)

* [微信小店](http://mp.weixin.qq.com/wiki/6/ae98ac4a7219405153cedc9dddccacca.html)

* [微信卡券](http://mp.weixin.qq.com/wiki/10/597cb57750f375a4b37e2536fd3331ea.html)

* [微信门店](http://mp.weixin.qq.com/wiki/11/081986f089826bf94393bef9bf287b8b.html)

* [微信摇一摇](http://mp.weixin.qq.com/wiki/19/9fe9fdbb50fee9f9660438c551142ccf.html)

* [微信连WI-FI](http://mp.weixin.qq.com/wiki/9/fd2d692e28b938a8d618f57cf9c79fb1.html)

* [微信扫一扫](http://mp.weixin.qq.com/wiki/19/e833eb10470cc25cad4719677c46ecdb.html)

交流群
-----
559850102