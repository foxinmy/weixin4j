weixin4j-qy-api
===============

[微信企业号](http://qydev.weixin.qq.com/wiki/index.php)开发工具包
---------------------------------------------------------------

功能列表
-------

  * PartyApi `部门管理API`
	
  * UserApi `成员管理API`
  
  * TagApi `标签管理API`
  
  * MediaApi `多媒体管理API`
  
  * MenuApi `菜单管理API`
  
  * NotifyApi `消息发送API`
  
  * AgentApi `应用设置API`
  
  * BatchApi `批量操作API`

如何使用
--------
1.API工程可以单独打包到其他项目中使用,需新增或拷贝`weixin.properties`文件到项目的`classpath`中

weixin.properties说明

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

2.实例化一个`WeixinProxy`对象,调用API

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(corpid,corpsecret);
    // weixinProxy = new WeixinProxy(weixinAccount);
    weixinProxy.getUser(userid);

3.针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenHolder`,默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用`RedisTokenHolder`.

    WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenHolder());
    // weixinProxy = new WeixinProxy(new RedisTokenHolder(weixinAccount));
    
4.`mvn package`.

更新LOG
-------
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