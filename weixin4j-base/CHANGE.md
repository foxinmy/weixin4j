* 2014-10-31

  + `TokenApi`重命名为`TokenHolder`
  
  + 新增`WeixinConfig`等类

* 2014-11-06
 
  + 删除`WeixinConfig`类只保留`WeixinAccount`类

* 2014-11-15

  + 新增`aes加密解密`函数
  
* 2014-11-19

  + 新增`WeixinQyAccount`企业号账号信息类

* 2014-11-23

  + 新增企业号消息体以及用`Responseable`,`Notifyable`,`Massable`三个接口标记不同的可接受的消息类型
  
* 2014-11-24

  + 将Action跟Mapping基础类并入到项目

* 2015-01-04

  + ConfigUtil类新增获取classpath目录下的资源路径的方法
  
* 2015-01-10

  + 重构token实现机制
  
  + 新增JSTICKET支持
  
* 2015-03-29

  + 单行注释调整为多行文档注释
  
* 2015-04-01

  + 新增异步消息事件[BatchjobresultMessage](./src/main/java/com/foxinmy/weixin4j/msg/event/BatchjobresultMessage.java)
  
* 2015-04-13

  + 删除WeixinTokenCreator与WeixinJSTicketCreator类
  
* 2015-04-19

  + 删除ActionMapping相关类
  
* 2015-05-07

  + 删除ResponseTuple接口
  
* 2015-06-08

  + 新增群发卡券消息类型
  
* 2015-06-26

  + 移入微信支付模块
  
* 2015-08-01

  + 整理了Tuple消息元件并新增ChatTuple企业号聊天消息元件
  
* 2015-09-27

 + 新增手动刷新token方法
 
 
* 2015-11-23

  + PayException重命名为WeixinPayException
  
  + 调整PayPackageV2构造函数：从主到次
  
  + 调整PayUtil2#createPayJsRequestJsonV2参数位置：从主到次
  
  + 调整MicroPayPackage构造函数：从主到次
  
  + 调整MicroPayPackage构造函数：从主到次
  
  + 调整PayUtil#createPayJsRequestJson参数位置：从主到次
  
  + 调整PayUtil#createNativePayRequestURL参数位置：从主到次
  
* 2015-12-04

  +【重要】修改PayUtil中的createPayJsRequest方法的返回值为MchPayRequest，便于二次发起支付。
  
  +【重要】添加MchPayRequest的构造函数，便于二次发起支付。
  
* 2015-12-19

  + 删除PayUtil类,接口转移到PayApi类
  
* 2015-12-25
  
  + WeixinPayProxy类新增获取支付信息#getWeixinAccount方法
  
  + 新增JSSDK的config生成类
  
  + JSSDKHelper 重命名为 JSSDKConfigurator
  
  + 重构了token类
  
* 2015-12-26
  
  + MchPayRequest抽象化
  
* 2016-01-24

  + 新增MemoryTokenStorager(内存保存token)类
  
  + TokenStorager类新增evict和clear接口
  
* 2016-01-29

  + 新增Weixin4jSettings配置类
  
* 2016-03-22

  + 企业付款相关类更名
  
* 2016-03-25

  + v2和v3支付改名
  
  + 支持服务商版支付
  
  + 签名类接口化
  
  + 新增查询结算金额接口
  
  + 新增查询汇率接口
  
* 2016-03-27

  + 删除Mciro支付接口,新增MCIROPayRequest对象
  
  + 支付对象优化
  
  + 新增海关接口
  
  + 添加日志支持
 
* 2016-05-12

  + 添加MemcacheTokenStorager支持
  
* 2016-07-30

  + 重新整理HttpClinet
  
  + 新增OkHttp实现
  
  
* 2016-08-05

  + model包拆分media/paging
  
  + type包拆分card/mch
  
  + 新增card卡券相关类
  
* 2016-08-22

  + 删除`Weixin4jSettings`配置类