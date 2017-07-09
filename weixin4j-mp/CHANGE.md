* 2014-10-27

  + 用netty构建http服务器&消息分发

* 2014-10-28

  + 调整`ActionMapping`抽象化

* 2014-10-31

  + `weixin.properties`切分为API调用地址和公众号appid等信息两部分

* 2014-11-03

  + 分离为`weixin-mp-api`和`weixin-mp-server`两个工程

  + 新增`支付模块`

* 2014-11-06

  + 新增V3版本`退款申请`接口

* 2014-11-08

  + 新增V2版本`退款申请`、`退款查询`、`对账单下载`三个接口

  + 新增一个简单的`语义理解`接口

* 2014-11-11

  + 自定义`assembly`将`weixin4j-base`工程也一起打包(`weixin4j-mp-api-full.jar`)

* 2014-11-15

  + 新增获取`微信服务器IP地址接口`

* 2014-11-16

  + 新增`多客服`接口

* 2014-11-17

  + 新增`冲正`和`被扫支付`接口

* 2014-12-12

  + 新增设置`模板消息所处行业`、`获取模板消息ID`接口

* 2014-12-16

  + 调整方法上@see注解的文档说明接口url

  + 新增群发消息预览、状态查询接口

  + 新增多客服添加账号、更新账号、上传头像、删除账号接口

* 2015-01-04

  + 支付模块拆分为V2跟V3,新增WeixinPayProxy类

  + 退款相关类拆分为V2跟V3

  + 新增接口上报接口

* 2015-01-31

  + 新增数据分析接口

* 2015-03-06

  + 新增oauth授权接口

* 2015-03-21

  + 新增群发消息给所有人接口

  + 新增素材管理多个接口

  + 新增多客服会话管理多个接口

* 2015-03-25

  + 根据《微信商户平台文档》修缮[Pay3Api](./src/main/java/com/foxinmy/weixin4j/mp/api/Pay3Api.java)类

* 2015-03-29

  + 单行注释调整为多行文档注释

  + 新增[CouponApi](./src/main/java/com/foxinmy/weixin4j/mp/api/CouponApi.java)代金券接口

* 2015-04-01

  + 新增[CashApi](./src/main/java/com/foxinmy/weixin4j/mp/api/CashApi.java)发红包、企业付款接口

* 2015-04-13

  + 新增WeixinTokenCreator与WeixinJSTicketCreator类

  + 新增用户分组批量移动、删除组别接口

* 2015-04-16

  + **weixin4j-mp-api**: <font color="red">调整[二维码参数](./src/main/java/com/foxinmy/weixin4j/mp/model/QRParameter.java)类</font>

  + **weixin4j-mp-api**: 新增获取[自定义菜单配置、自动回复配置](./src/main/java/com/foxinmy/weixin4j/mp/api/HelperApi.java)接口

* 2015-04-18

  + <font color="red">调整[客服接口](./src/main/java/com/foxinmy/weixin4j/mp/api/CustomApi.java)类的方法名</font>

  + <font color="red">在[二维码接口](./src/main/java/com/foxinmy/weixin4j/mp/api/QRApi.java)类新增获取二维码url方法</font>

* 2015-06-04

  + 新增查询红包接口

* 2015-06-23

  + 新增企业付款查询接口

* 2015-07-04

  + released 1.5.1

* 2015-07-29

 + 新增二维码结果类[QRResult.java](./src/main/java/com/foxinmy/weixin4j/mp/model/QRResult.java)并将二维码接口[QRApi.java](./weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/QrApi.java)名称变更为createQR和createQRFile

 + [Oauth授权](./src/main/java/com/foxinmy/weixin4j/mp/api/OauthApi.java)跳转的uri在配置文件的属性名改为`oauth_redirect_uri`

* 2015-07-31

 + 新增图文消息中上传图片接口

* 2015-08-01

  + 新增了群发消息中的上传视频接口

  + 调整群发消息接口返回类型为字符串数组[{msg_id,msg_data_id}]

* 2015-08-09

  + version upgrade to 1.5.2

  + oauth_redirect_uri配置属性名更改为user_oauth_redirect_uri

* 2015-08-13

  + version upgrade to 1.5.3

  + 媒体接口类(MediaApi)查询素材接口调整:去掉offset,count替换为Pageable类

* 2015-09-08

  + 新增批量获取用户信息接口

* 2015-09-11

  + version upgrade to 1.6.0

* 2015-09-16

  + version upgrade to 1.6.1

* 2015-09-21

  + version upgrade to 1.6.2

* 2015-11-09

  + version upgrade to 1.6.3

* 2015-12-10

  + version upgrade to 1.6.4

  + 【特大注意】weixin4j.properties全部的属性名添加`weixin4j`前缀，并用`.`代替原来的`_`

* 2015-12-15

  + version upgrade to 1.6.5

* 2015-12-18

  + 新增个性化菜单接口

  + WeixinProxy.getCustomRecord 参数变更为 Date startTime, Date endTime, Pageable pageable

* 2015-12-25

  + WeixinProxy新增获取appid(getAppId)方法

  + WeixinProxy新增获取jsticket(getJSTicketHolder)方法

  + 私有化WeixinProxy(TokenHolder)构造器

  + 调整WeixinTicketCreator类

* 2015-12-31

  + version upgrade to 1.6.6

* 2016-01-20

  + 新增获取模板和删除模板接口

  + 新增自定义个性化菜单语言信息匹配项

* 2015-02-04

  + version upgrade to 1.6.7

* 2016-04-02

  + version upgrade to 1.6.8

* 2016-04-29

  + weixin4j-mp:新增标签管理API

* 2016-05-07

  + version upgrade to 1.6.9

* 2016-05-30

  + 新增接口调用次数清零接口

* 2016-06-20

  + version upgrade to 1.7.0

* 2016-07-05

  + 初始化开放平台第三方组件TokenCreator

  + 新增第三方组件ComponentApi

* 2016-07-06

  + 新增第三方组件WeixinComponentProxy

* 2016-08-05

  + 新增CardApi:创建卡券接口

* 2016-08-09

  + 新增创建卡券二维码接口

  + version upgrade to 1.7.1

* 2016-10-10

  + version upgrade to 1.7.2

* 2016-11-22

  + 新增黑名单接口

* 2016-12-13

  + version upgrade to 1.7.3

* 2017-01-09

  + 新增批量发红包接口

  + version upgrade to 1.7.4

* 2017-04-11

  + version upgrade to 1.7.5

* 2017-05-19

  + 新增评论管理接口

* 2017-06-02

  + version upgrade to 1.7.6
  
* 2017-06-23

  + version upgrade to 1.7.7