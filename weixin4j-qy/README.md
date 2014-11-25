weixin4j-qy
===========

[微信企业号](http://qydev.weixin.qq.com/wiki/index.php)开发工具包
---------------------------------------------------------------

功能列表
-------
* **weixin4j-qy-api**

  + DepartApi `部门管理API`
	
  + UserApi `成员管理API`
  
  + TagApi `标签管理API`
  
  + MediaApi `多媒体管理API`
  
  + MenuApi `菜单管理API`
  
  + NotifyApi `消息发送API`

* **weixin4j-qy-server**

  + `netty服务器` & `消息分发`
  
  + 回调连接`AES`加密、解密
  
项目说明
-------
1.`weixin4j-qy`包含「微信企业号」的API封装以及一个半成品的netty服务实现.

2.API的成功调用依赖于正确的appid等数据,创建(或者copy项目里面的)一个名为**weixin.properties**的资源文件放在自己工程中的classpath下.

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

3.`mvn package`得到jar包,在自己的项目中可单独引用`weixin4j-qy-api-full`包,也可分别引用`weixin4j-base`跟`weixin4j-qy-api`两个包.

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(corpid,corpsecret);
    // weixinProxy = new WeixinProxy(weixinAccount);
    weixinProxy.getUser(userid);

4.如需使用netty服务,则可以在相应的action中实现自己的业务处理,打包后解压`weixin-qy-server-bin.zip`执行`sh startup.sh start`即可.

	@ActionAnnotation(msgType = MessageType.text)
	public class TextAction extends AbstractAction<TextMessage> {
	
		@Override
		public ResponseMessage execute(TextMessage inMessage) {
			return new ResponseMessage(new Text("Hello World!"), inMessage);
		}
	}

更新LOG
-------
* 2014-11-19

  + 得到`weixin4j-qy-api`和`weixin4j-qy-server`工程
  
  + **weixin4j-qy-api**: 新增部门管理接口
  
  + **weixin4j-qy-api**: 新增用户管理接口
  
  + **weixin4j-qy-api**: 新增标签管理接口
  
* 2014-11-23

  + **weixin4j-qy-api**: 新增`多媒体管理`接口
  
  + **weixin4j-qy-api**: 新增`发送消息`接口
  
  + **weixin4j-qy-api**: 新增`菜单管理`接口
  
* 2014-11-24

  + **weixin4j-qy-server**: 新增netty服务与消息分发
