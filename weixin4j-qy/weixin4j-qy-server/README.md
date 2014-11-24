weixin4j-qy-server
==================

微信企业号netty服务
------------

功能列表
-------

* `netty构建服务器`

* `消息分发`

如何使用
--------
1.正确填写`weixin.properties`中的属性值

| 属性名       |       说明      |
| :---------- | :-------------- |
| account     | 微信企业号信息 `json格式`  |
| token_path  | 使用FileTokenHolder时token保存的物理路径 |
| media_path  | 调用媒体接口时保存媒体文件的物理路径 |

示例(properties中换行用右斜杆\\)

	account={"id":"corpid","secret":"corpsecret",\
		"token":"企业号中应用在回调模式下的token",\
		"encodingAesKey":"企业号中应用在回调模式下AES加密密钥"}
	
	token_path=/tmp/weixin/token
	media_path=/tmp/weixin/media

2.mvn package,得到一个zip的压缩包,解压到启动目录(见`src/main/startup.sh/APP_HOME`)

3.启动netty服务(`com.foxinmy.weixin4j.mp.startup.WeixinQyServerBootstrap`)
    
    sh startup.sh start
	
更新LOG
-------
* 2014-11-19

  + 得到`weixin4j-qy-server`工程

* 2014-11-24

  + 新增netty服务与消息分发