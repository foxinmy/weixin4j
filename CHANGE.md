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

  + **weixin4j-server**: 完成基本骨架
  
* 2015-05-08

  + **weixin4j-server**: 完成消息分发器、消息处理器、消息拦截器的骨架
  
* 2015-05-13

  + **weixin4j-server**: 新增了许多被动消息类型
  
* 2015-05-15

  + **weixin4j-server**: 消息拦截器和处理器支持泛型
  
* 2015-05-16

  + **weixin4j-server**: 实现消息处理器的泛型自动匹配
  
  + **weixin4j-server**: 去掉SLF4J-API依赖
  
  + **weixin4j-server**: released 1.0.0!
  
* 2015-05-18

  + **weixin4j-server**: 新增WeixinMessageKeyDefiner类
  
* 2015-05-20

  + **weixin4j-server**: released 1.0.1
  
* 2015-05-30

  + **去掉httpclient依赖**
  
  + **去掉redis依赖**
  
* 2015-06-03

  + **去掉xstream依赖**
  
* 2015-06-04

  + **weixin4j-mp**: 新增查询红包接口
  
* 2015-06-08

  + **weixin4j-base**: 新增群发卡券消息类型

* 2015-06-10

  + **weixin4j-server**: released 1.0.2

  + weixin.properties重命名为weixin4j.properties
  
  + released 1.5.0！
  
* 2015-06-12

  + 修缮token实现机制
  
  + **weixin4j-qy**: 新增企业号[登陆授权](weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/api/OauthApi.java)API
  
* 2015-06-21

  + 新增了默认常量对象在BaseApi.java类
  
  + **weixin4j-qy**: 新增企业号[第三方应用API](weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/suite)。

* 2015-06-22

  + **weixin4j-qy**: 新增企业号[第三方应用代理](weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/WeixinSuiteProxy.java)。
  
* 2015-06-23

  + **weixin4j-mp**: 新增企业付款查询接口
  
  + **weixin4j-server**: 对多个公众号的接入支持
  
* 2015-06-24

  + **weixin4j-qy**: 新增userid与openid互换接口
  
* 2015-06-26

  + 将微信支付模块移到base工程
  
  + **weixin4j-qy**: 管理成员新增头像参数
  
* 2015-06-27

  + 媒体接口(MediaApi)中上传方法中的File类型调整为InputStream
  
* 2015-07-04

  + **weixin4j-qy**: 新增[媒体素材接口](weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/api/MediaApi.java)
  
  + released 1.5.1
  
* 2015-07-22

  + **weixin4j-qy**: 创建标签时可以指定ID
  
* 2015-07-23

  + 微信支付新增授权码查询OPENID接口
  
* 2015-07-25

 + 精简函数上的@link注释
 
 + 新增媒体文件上传、下载结果类([MediaUploadResult.java](./weixin4j-base/src/main/java/com/foxinmy/weixin4j/model/MediaUploadResult.java),[MediaDownloadResult.java](./weixin4j-base/src/main/java/com/foxinmy/weixin4j/model/MediaDownloadResult.java))
 
* 2015-07-29

 + 精简WeixinAccount类及其配置文件
 
 + **weixin4j-mp**: 新增二维码结果类[QRResult.java](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/model/QRResult.java)并将二维码接口[QRApi.java](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/QrApi.java)名称变更为createQR和createQRFile
 
 + **weixin4j-mp**: [Oauth授权](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/OauthApi.java)跳转的uri在配置文件的属性名改为`oauth_redirect_uri`
 
* 2015-07-30
  
  + **weixin4j-qy**: 调整[WeixinSuiteProxy](./weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/WeixinSuiteProxy.java)对多个套件的支持

* 2015-07-31

  + **weixin4j-server**:`WeixinServerBootstrap` 构造函数支持多个公众号
  
  + **weixin4j-server**:`MessageHandlerAdapter` 声明时限定泛型为`WeixinMessage`的子类
  
  + **weixin4j-mp**: 新增图文消息中上传图片接口
  
* 2015-08-01

  + **weixin4j-base**: 整理了Tuple消息元件
  
  + **weixin4j-mp**: 新增了群发消息中的上传视频接口
  
  + **weixin4j-mp**: 调整群发消息接口返回类型为字符串数组[{msg_id,msg_data_id}]
  
  + **weixin4j-qy**: 新增聊天服务接口[ChatApi](./weixin4j-qy/src/main/java/com/foxinmy/weixin4j/qy/api/ChatApi.java)
  
  + **weixin4j-server**: 新增企业号消息服务相关类
  
* 2015-08-03

  + **weixin4j-server**: 新增base64解编码类(来自apache)
  
  + 在WeixinProxy类新增VERSION字段
  
* 2015-08-06

  + **weixin4j-server**: 调整`LocationEventMessage`类中的经纬度字段类型为double
  
* 2015-08-07
  
  + 主要去掉了实体类中的字段上@JSONFiled上的deserialize=false属性和不合理的format方法
  
* 2015-08-09
 
  + **weixin4j-qy**: 会话API暴露到WeixinProxy类
  
  + **weixin4j-qy**: 重命名NotifyApi#sendNotify为sendNotifyMessage
  
  + `release`: weixin4j-[mp|qy] upgrade to 1.5.2,weixin4j-server upgrade to 1.0.4
  
* 2015-08-10

  + **weixin4j-qy**: 新增了会话API测试类
  
* 2015-08-13
  
  + `release`: weixin4j-[mp|qy] upgrade to 1.5.3,weixin4j-server upgrade to 1.0.5
  
 + **weixin4j-[mp|qy]**: 媒体接口类(MediaApi)查询素材接口调整:去掉offset,count替换为Pageable类
 
* 2015-08-18
  
 + 比较大的改动:重构了HttpClient部分
