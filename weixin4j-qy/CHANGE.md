* 2014-11-19

  + 新增`部门管理`接口

  + 新增`用户管理`接口

  + 新增`标签管理`接口

* 2014-12-28

  + 增加`批量获取用户详情`的接口

  + 新增`获取微信服务器IP`接口

* 2015-01-04

 + 新增批量删除员工接口

* 2015-01-15

  + 新增邀请成员关注接口

* 2015-03-08

  + 新增根据code获取成员信息接口

* 2015-03-17

  + 新增企业应用设置接口

* 2015-03-29

  + 单行注释调整为多行文档注释

* 2015-04-01

  + 新增[BatchApi](./src/main/java/com/foxinmy/weixin4j/qy/api/BatchApi.java)批量异步执行任务接口

  + <font color="red">DepartApi命名为[PartyApi](./src/main/java/com/foxinmy/weixin4j/qy/api/PartyApi.java)</font>

* 2015-04-04

  + [MediaApi](./src/main/java/com/foxinmy/weixin4j/qy/api/MediaApi.java)新增批量上传成员和部门接口

* 2015-04-09

  + [AgentApi](./src/main/java/com/foxinmy/weixin4j/qy/api/AgentApi.java)新增获取应用列表概况接口

* 2015-04-13

  + 新增WeixinTokenCreator与WeixinJSTicketCreator类

* 2015-06-12

  + 新增企业号[登陆授权](src/main/java/com/foxinmy/weixin4j/qy/api/OauthApi.java)API

* 2015-06-21

  + 新增企业号[第三方应用API](src/main/java/com/foxinmy/weixin4j/qy/suite)。

* 2015-06-22

  + 新增企业号[第三方应用代理](src/main/java/com/foxinmy/weixin4j/qy/WeixinSuiteProxy.java)。

* 2015-06-24

  + 新增userid与openid互换接口

* 2015-06-26

  + 管理成员新增头像参数

* 2015-07-04

  + 新增[媒体素材接口](src/main/java/com/foxinmy/weixin4j/qy/api/MediaApi.java)

  + released 1.5.1

* 2015-07-22

  + 创建标签时可以指定ID

* 2015-07-30

  + **weixin4j-qy**: 调整[WeixinSuiteProxy](.src/main/java/com/foxinmy/weixin4j/qy/WeixinSuiteProxy.java)对多个套件的支持

* 2015-08-01

  + 新增会话服务接口[ChatApi](./src/main/java/com/foxinmy/weixin4j/qy/api/ChatApi.java)

* 2015-08-09

  + 会话API暴露到WeixinProxy类

  + 重命名NotifyApi#sendNotify为sendNotifyMessage

  + version upgrade to 1.5.2

* 2015-08-10

  + 新增了会话API测试类

* 2015-08-13

  + version upgrade to 1.5.3

  + 媒体接口类(MediaApi)查询素材接口调整:去掉offset,count替换为Pageable类

* 2015-09-11

  + version upgrade to 1.6.0

* 2015-09-16

  + version upgrade to 1.6.1

* 2015-09-21

  + version upgrade to 1.6.2

* 2015-11-09

  + version upgrade to 1.6.3

* 2015-11-20

  + 新增客服消息


* 2015-12-04

  +【重要】第三方应用授权时获取永久授权码覆盖问题。


* 2015-12-10

  + version upgrade to 1.6.4

  + 【特大注意】weixin4j.properties全部的属性名添加`weixin4j`前缀，并用`.`代替原来的`_`


* 2015-12-15

  + version upgrade to 1.6.5

* 2015-12-25

  + WeixinProxy新增获取corpid(getCorpId)方法

  + WeixinProxy新增获取jsticket(getJSTicketHolder)方法

  + 私有化WeixinProxy(TokenHolder)构造器

  + SuiteApi新增获取Weixinproxy对象(getWeixinProxy)方法

  + 删除WeixinJSTicketCreator类

  + 新增企业号联系人筛选配置类(JSSDKContactConfigurator)

* 2015-12-30

  + 新增服务商接口(ProviderApi)

* 2015-12-31

  + version upgrade to 1.6.6

* 2016-01-23

  + 新增获取客服列表接口

* 2016-01-26

  + 新增上传图文消息内的图片接口

* 2015-02-04

  + version upgrade to 1.6.7

* 2016-04-02

  + version upgrade to 1.6.8

* 2016-05-07

  + version upgrade to 1.6.9

* 2016-06-20

  + version upgrade to 1.7.0

* 2016-08-09

  + version upgrade to 1.7.1

* 2016-10-10

  + version upgrade to 1.7.2

* 2016-12-13

  + version upgrade to 1.7.3

* 2017-01-09

  + 新增批量发红包接口

  + 新增摇一摇周边接口

  + version upgrade to 1.7.4

* 2017-04-11

  + version upgrade to 1.7.5

* 2017-06-02

  + version upgrade to 1.7.6
  
* 2017-06-23

  + version upgrade to 1.7.7
  
* 2017-08-25

  + 企业号调整为企业微信
  
  + version upgrade to 1.7.8
  
* 2017-11-09

  + version upgrade to 1.7.9