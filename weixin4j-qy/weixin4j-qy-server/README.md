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

2.在对应的action中实现自己的具体业务 如 TextAction 则表示收到文本消息

	@Override
	public ResponseMessage execute(TextMessage inMessage) {
		return new ResponseMessage(new Text("Hello World!"), inMessage);
	}

3.`mvn package`,得到一个zip的压缩包,解压到启动目录(见`src/main/startup.sh/APP_HOME`)

4.启动netty服务(`com.foxinmy.weixin4j.mp.startup.WeixinQyServerBootstrap`)
    
    sh startup.sh start
	
更新LOG
-------
* 2014-11-19

  + 得到`weixin4j-qy-server`工程

* 2014-11-24

  + 新增netty服务与消息分发
  
* 2014-12-28

  + 增加用户进入应用的callback事件
  
  + 调整回调模式下的首次验证的签名方式