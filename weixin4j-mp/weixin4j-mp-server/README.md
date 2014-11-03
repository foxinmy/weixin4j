weixin4j-mp-server
==================

@(weixin4j)[netty服务]

微信netty服务
------------

功能列表
-------

* `netty构建服务器`

* `消息分发`


如何使用
--------
1.正确填写`weixin.properties`中的属性值

2.mvn package,得到一个zip的压缩包,解压到启动目录(见`src/main/startup.sh/APP_HOME`)

3.启动netty服务(`com.foxinmy.weixin4j.mp.startup.WeixinServiceBootstrap`)
    
    sh startup.sh start
	
更新LOG
-------
* 2014-11-03

  + 得到`weixin-mp-server`工程