weixin4j-qy
===========

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
0.maven依赖(1.4,2015-04-29 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-qy</artifactId>
	    <version>1.4</version>
	</dependency>
1.需新增或拷贝`weixin.properties`文件到项目的`classpath`中

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

> 针对`token`存储有两种方案,`File存储`/`Redis存储`,当然也可自己实现`TokenHolder`,默认使用文件(xml)的方式保存token,如果环境中支持`redis`,建议使用`RedisTokenHolder`.
>
>   WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenHolder());
>   // weixinProxy = new WeixinProxy(new RedisTokenHolder(weixinAccount));

[更新LOG](./CHANGE.md)
----------------------