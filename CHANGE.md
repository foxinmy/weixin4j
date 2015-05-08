* 2014-10-27
 
  + maven多模块分离
 
  + **weixin4j-mp**: 用netty构建http服务器并支持消息分发
 
* 2014-10-28
   
  + **weixin4j-mp**: 调整`ActionMapping`抽象化

* 2014-10-31

  + **weixin4j-mp**: `weixin.properties`切分为API调用地址和公众号信息两部分
   
  + **weixin4j-base**: `TokenApi`重命名为`TokenHolder`
  
  + **weixin4j-base**:新增`WeixinConfig`等类

* 2014-11-03

  + **weixin4j-mp**: 分离为`weixin-mp-api`和`weixin-mp-server`两个工程
   
  + **weixin4j-mp**: 加入支付模块
  
* 2014-11-05

  + 优化了代码

* 2014-11-06
 
  + **weixin4j-base**: 删除`WeixinConfig`类只保留`WeixinAccount`类
  
  + **weixin4j-mp**: 新增V3版本`退款接口`

* 2014-11-08
 
  + **weixin4j-mp**: 新增V2版本`退款申请`、`退款查询`、`对账单下载`三个接口
  
  + **weixin4j-mp**: 新增一个简单的`语义理解`接口

* 2014-11-11

  + **weixin4j-mp**: 自定义`assembly`将`weixin4j-base`工程也一起打包(`weixin4j-mp-api-full.jar`)

* 2014-11-15

  + **weixin4j-base**: 新增aes加密解密函数
  
  + **weixin4j-mp**: 新增获取`微信服务器IP地址`接口
    
  + **weixin4j-mp**: 解决`server工程`打包后不能运行问题(`ClassUtil`无法获取jar包里面的类)
  
  + **weixin4j-mp**: 新增被动消息的`加密`以及回复消息的`解密`
 
* 2014-11-16

  + **weixin4j-mp**: 新增`多客服`接口
  
* 2014-11-17

  + **weixin4j-mp**: 新增`冲正`和`被扫支付`接口
  
* 2014-11-19

  + **weixin4j-base**: 新增`WeixinQyAccount`企业号账号信息类
  
  + **weixin4j-qy**: 得到`weixin4j-qy`和`weixin4j-qy-server`工程
  
  + **weixin4j-qy**: 新增`部门管理`接口
  
  + **weixin4j-qy**: 新增`用户管理`接口
  
  + **weixin4j-qy**: 新增`标签管理`接口

* 2014-11-23

  + **weixin4j-base**: 新增企业号消息体以及用`Responseable`,`Notifyable`,`Massable`三个接口标记不同的可接受的消息类型
  
  + **weixin4j-mp**: 重新定义(手贱)了「被动消息」「客服消息」「群发消息」的传输实体
  
  + **weixin4j-mp**: `WeixinServerBootstrap`重命名为`WeixinMpServerBootstrap`
  
  + **weixin4j-qy**: 新增`多媒体管理`接口
  
  + **weixin4j-qy**: 新增`发送消息`接口
  
  + **weixin4j-qy**: 新增`菜单管理`接口
  
* 2014-11-24

  + **weixin4j-base**: 将Action跟Mapping基础类并入到项目
  
  + **weixin4j-qy**: 新增netty服务与消息分发
  
* 2014-11-27

  + **weixin-base**: 将BaseApi移入
  
* 2014-11-29

  + 重构了POM
  
* 2014-12-12

  + **weixin4j-mp**: 新增设置`模板消息所处行业`、`获取模板消息ID`接口
  
* 2014-12-15

  + **weixin4j-mp**: 修改某些函数在注释上的参数描述错误
  
  + **weixin4j-mp**: 调整PayUtil类中`createPayJsRequestJsonV3`的形参位置,`notify_url`与`spbill_create_ip`对换与V2保持一致
  
  + **weixin4j-mp**: 在PayUtil类中新增paySign重载版本函数,避免在某些地方产生歧义造成签名错误(appid,appKey)
  
  + **weixin4j-mp**: 修正V3版本JSAPI接口支付签名错误bug(坑)
  
* 2014-12-16

  + **weixin4j-mp**: 调整方法上@see注解的文档说明接口url
  
  + **weixin4j-mp**: 新增群发消息预览、状态查询接口
  
  + **weixin4j-mp**: 新增多客服添加账号、更新账号、上传头像、删除账号接口

* 2014-12-18
  
  + clear code with findbugs plugin
  
  + change the version to 1.1
 
* 2014-12-28

  + **weixin4j-qy**: 增加用户进入应用的callback事件

  + **weixin4j-qy**: 增加批量获取用户详情的接口 
  
  + **weixin4j-qy**: 新增获取微信服务器IP接口
  
  + **weixin4j-qy**: 调整回调模式下的首次验证的签名方式

* 2015-01-04

  + **weixin4j-base**: 新增获取classpath目录下的资源路径的方法
  
  + **weixin4j-mp**: 支付模块拆分为V2跟V3,新增WeixinPayProxy类
  
  + **weixin4j-mp**: 退款相关类拆分V2跟V3
  
  + **weixin4j-mp**: 新增接口上报接口
  
  + **weixin4j-qy**: 新增批量删除员工接口
  
* 2015-01-10

  + **weixin4j-base**: 重构token实现机制
  
  + **weixin4j-base**: 新增JSTICKET支持
  
* 2015-01-15
  
  + **weixin4j-qy**: 新增邀请成员关注接口
  
 * 2015-01-22
 
  + **weixin4j-base**: 升级fastjson到1.2.3
  
  + **weixin4j-mp**:调整部分实体类(*paypackage)中没有按照骆驼命名规则的属性名
  
* 2015-01-31
  
  + **weixin4j-mp**: 新增数据分析接口
  
* 2015-02-05

  + release 1.2
  
* 2015-03-06
  
  + **weixin4j-mp**: 新增oauth授权接口
  
* 2015-03-08
  
  + **weixin4j-qy**: 新增根据code获取成员信息接口
  
* 2015-03-17
  
  + **weixin4j-qy**: 新增企业应用设置接口
  
* 2015-03-21

  + **weixin4j-mp**: 新增群发消息给所有人接口
  
  + **weixin4j-mp**: 新增素材管理多个接口
  
  + **weixin4j-mp**: 新增多客服会话管理多个接口
  
* 2015-03-25

  + **weixin4j-mp**: 根据《微信商户平台文档》修缮[Pay3Api](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/Pay3Api.java)类
  
  + **weixin4j-mp**: 新增客服创建、关闭、转接会话事件
  
  + **weixin4j-mp**: 新增deploy.xml远程部署ant脚本
  
  + **weixin4j-qy**: 新增deploy.xml远程部署ant脚本
  
* 2015-03-29

  + **weixin4j-base**: 单行注释调整为多行文档注释
  
  + **weixin4j-mp**: 单行注释调整为多行文档注释
  
  + **weixin4j-mp**: 新增[CouponApi](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/CouponApi.java)代金券接口
  
  + **weixin4j-qy**: 单行注释调整为多行文档注释
  
* 2015-04-01

  + **weixin4j-mp**: 新增[CashApi](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/CashApi.java)发红包、企业付款接口
  
  + **weixin4j-qy**: 新增[BatchApi](./weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/api/BatchApi.java)批量异步执行任务接口
  
  + **weixin4j-qy**: <font color="red">DepartApi命名为[PartyApi](./weixin4j-qy/weixin4j-qy-api/src/main/java/com/foxinmy/weixin4j/qy/api/PartyApi.java)</font>
  
* 2015-04-04

  + **weixin4j-qy**: [MediaApi](./weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/api/MediaApi.java)新增批量上传成员和部门接口
  
  + <font color="red">released 1.3</font>
  
* 2015-04-09

  + **weixin4j-qy**: [AgentApi](./weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/api/AgentApi.java)新增获取应用列表概况接口
  
* 2015-04-13

  + **weixin4j-base**: <font color="red">删除WeixinTokenCreator与WeixinJSTicketCreator类</font>
  
  + **weixin4j-mp**: 新增WeixinTokenCreator与WeixinJSTicketCreator类
  
  + **weixin4j-mp**: 新增用户分组批量移动、删除组别接口
  
  + **weixin4j-qy**: 新增WeixinTokenCreator与WeixinJSTicketCreator类
  
* 2015-04-16

  + **weixin4j-mp**: <font color="red">调整[二维码参数](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/model/QRParameter.java)类</font>
  
  + **weixin4j-mp**: 新增获取[自定义菜单配置、自动回复配置](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/HelperApi.java)接口
  
* 2015-04-18

  + **weixin4j-mp**: <font color="red">调整[客服接口](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/CustomApi.java)类的方法名</font>
  
   + **weixin4j-mp**: <font color="red">在[二维码接口](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/QRApi.java)类新增获取二维码url方法</font>
   
   
 * 2015-04-19

  + <font color="red">调整聚合方式,去除原先的weixin4j-mp和weixin4j-qy模块,相应的api模块直接继承weixin4j父模块</font>
  
  + **weixin4j-base**: <font color="red">删除ActionMapping相关类</font>
  
* 2015-04-29

  + <font color="red">released 1.4</font>
  
* 2015-04-30

  + **weixin4j-mp**: 新增`media_id`和`view_limited`两种菜单类型
  
* 2015-05-07

  +**weixin4j-server**: 完成基本骨架
  
* 2015-05-08

  +**weixin4j-server**: 完成消息分发器、消息处理器、消息拦截器的骨架