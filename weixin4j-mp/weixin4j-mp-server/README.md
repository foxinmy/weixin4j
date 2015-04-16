weixin4j-mp-server
==================

微信公众平台netty服务
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
| account     | 微信公众号信息 `json格式`  |
| token_path  | 使用FileTokenHolder时token保存的物理路径 |
| qr_path     | 调用二维码接口时保存二维码图片的物理路径 |
| media_path  | 调用媒体接口时保存媒体文件的物理路径 |
| bill_path   | 调用下载对账单接口保存excel文件的物理路径 |
| ca_file     | 调用某些接口(支付相关)强制需要auth的ca授权文件 |

示例(properties中换行用右斜杆\\)

	account={"id":"appId","secret":"appSecret",\\
		"token":"开放者的token","openId":"公众号的openid 非必须",\\
		"encodingAesKey":"公众号设置了加密方式且为「安全模式」时需要填入",\\
		"mchId":"V3.x版本下的微信商户号",\\
		"partnerId":"V2版本下的财付通的商户号","partnerKey":"V2版本下的财付通商户权限密钥Key",\\
		"version":"针对微信支付的版本号(目前可能为2,3),如果不填则按照mchId非空与否来做判断",\\
		"paySignKey":"微信支付中调用API的密钥"}
	
	token_path=/tmp/weixin/token
	qr_path=/tmp/weixin/qr
	media_path=/tmp/weixin/media
	bill_path=/tmp/weixin/bill
	ca_file=/tmp/weixin/xxxxx.p12 | xxxxx.pfx

2.在对应的action中实现自己的具体业务 如 TextAction 则表示收到文本消息

	@Override
	public ResponseMessage execute(TextMessage inMessage) {
		return new ResponseMessage(new Text("Hello World!"), inMessage);
	}
	
3.`mvn package`,得到一个zip的压缩包,解压(也可使用deploy.xml部署到远程服务器)到`启动目录`

4.启动netty服务(`com.foxinmy.weixin4j.mp.startup.WeixinMpServerBootstrap`)
    
    sh startup.sh start
 
 > 1.服务的启动脚本[startup.sh](./src/main/startup.sh)需要被注意到,有`JAVA_HOME`和`APP_HOME`两个参数.
 
 > 2.其中`JAVA_HOME`参数值指的是java运行环境(jre|jdk)的安装根目录,如果与脚本中的值不一致,可以改更为实际的路径或者使用`ln -s t/usr/local/java target`软链接命令创建期望的链接.
 
 > 3.其中`APP_HOME`参数值指的是本服务的启动目录,此目录需要被正确事先创建好,同时[`deploy.xml`](./deploy.xml)远程部署命令也依赖于此.
 
 > 4.Ant远程部署[`deploy.xml`](./deploy.xml)的正确执行需要[`jsch`](http://www.jcraft.com/jsch/)包的支持,下载jar包将其引入执行Ant命令时的`classpath`中.
 
 > 5.一般来说*Action事件处理类中应该有自己的实际业务类(`service`)需要被注入,可以使用org.springframework.context.ApplicationContext#getBeansWithAnnotation(ActionAnnotation.class)函数获取Action集合后再来实现[`AbstractActionMapping`](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-base/src/main/java/com/foxinmy/weixin4j/action/mapping/AbstractActionMapping.java).
    
更新LOG
-------
* 2014-11-03

  + 得到`weixin4j-mp-server`工程

* 2014-11-15

  +  解决`server工程`打包后不能运行问题(`ClassUtil`无法获取jar包里面的类)
  
  + 新增被动消息的`加密`以及回复消息的`解密`
  
* 2014-11-23

  + `WeixinServerBootstrap`重命名为`WeixinMpServerBootstrap`
  
* 2015-03-25

  + 新增客服创建、关闭、转接会话事件
  
  + 新增deploy.xml远程部署ant脚本