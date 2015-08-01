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