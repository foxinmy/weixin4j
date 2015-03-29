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

3.`mvn package`,得到一个zip的压缩包,解压(也可使用deploy.xml部署到远程服务器)到`启动目录`

4.启动netty服务(`com.foxinmy.weixin4j.mp.startup.WeixinQyServerBootstrap`)
    
    sh startup.sh start

 > 1.服务的启动脚本[startup.sh](./src/main/startup.sh)需要被注意到,有`JAVA_HOME`和`APP_HOME`两个参数.
 
 > 2.其中`JAVA_HOME`参数值指的是java运行环境(jre|jdk)的安装根目录,如果与脚本中的值不一致,可以改更为实际的路径或者使用`ln -s /usr/local/java target`软链接命令创建期望的链接.
 
 > 3.其中`APP_HOME`参数值指的是本服务的启动目录,此目录需要被正确事先创建好,同时[`deploy.xml`](./deploy.xml)远程部署命令也依赖于此.
 
 > 4.Ant远程部署[`deploy.xml`](./deploy.xml)的正确执行需要[`jsch`](http://www.jcraft.com/jsch/)包的支持,下载jar包将其引入执行Ant命令时的`classpath`中.
 
 > 5.一般来说*Action事件处理类中应该有自己的实际业务类(`service`)需要被注入,可以使用org.springframework.context.ApplicationContext#getBeansWithAnnotation(ActionAnnotation.class)函数获取Action集合后再来实现[`AbstractActionMapping`](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-base/src/main/java/com/foxinmy/weixin4j/action/mapping/AbstractActionMapping.java).

更新LOG
-------
* 2014-11-19

  + 得到`weixin4j-qy-server`工程

* 2014-11-24

  + 新增netty服务与消息分发
  
* 2014-12-28

  + 增加用户进入应用的callback事件
  
  + 调整回调模式下的首次验证的签名方式
  
* 2015-03-25
 
  + 新增deploy.xml远程部署ant脚本