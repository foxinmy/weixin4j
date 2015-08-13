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
  
* 2015-05-07

  + 完成基本骨架
  
* 2015-05-08

  + 完成消息分发器、消息处理器、消息拦截器的骨架
  
* 2015-05-13

  + 新增了许多被动消息类型
  
* 2015-05-15

  + 消息拦截器和处理器支持泛型
  
* 2015-05-16

  + 实现消息处理器的泛型自动匹配
  
  + 去掉SLF4J-API依赖
  
  + released 1.0.0!
  
* 2015-05-18

  + 新增WeixinMessageKeyDefiner类
  
* 2015-05-20

  + released 1.0.1
  
* 2015-06-10

  + released 1.0.2
  
* 2015-07-04

  + released 1.0.3
  
* 2015-07-31

  `WeixinServerBootstrap` 构造函数支持多个公众号
  
  `MessageHandlerAdapter` 声明时限定泛型为`WeixinMessage`的子类
  
* 2015-08-01

  新增企业号消息服务相关类
  
* 2015-08-03

  + 新增base64解编码类(来自apache)
  
  + 删除`BlankMessageHandler`类,新增`SingleContentResponse`类
  
* 2015-08-06

  + 调整`LocationEventMessage`类中的经纬度字段类型为double
  
* 2015-08-09
 
  + version upgrade to 1.0.4
  
  + 调整WeixinServerBootstrap明文构造函数的参数为token
  
* 2015-08-13

  + version upgrade to 1.0.5