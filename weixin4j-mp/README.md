weixin4j-mp
===========

[微信公众平台](http://mp.weixin.qq.com/wiki)开发工具包
----------------------------------------------------

功能列表
-------
* **weixin4j-mp-api**

  + MediaApi `上传/下载媒体文件API`
	
  + NotifyApi `客服消息API`
  
  + CustomApi `多客服API`
	
  + MassApi `群发消息API`
	
  + UserApi `用户管理API`
	
  + GroupApi `分组管理API`
	
  + MenuApi `底部菜单API`
	
  + QrApi `二维码API`
	
  + TmplApi `模板消息API`
	
  + HelperApi `辅助API`
  
  + Pay2Api `V2支付API`
  
  + Pay3Api `V3支付API`
  
  + DataApi `数据统计API`
  
  + OauthApi `oauth授权API`

* **weixin4j-mp-server**

  + `netty服务器` & `消息分发`
  
  + 被动消息`AES`加密、解密

项目说明
-------
1.`weixin4j-mp`包含「微信公众平台」的API封装以及一个半成品的netty服务实现.

2.API的成功调用依赖于正确的appid等数据,创建(或者copy项目里面的)一个名为**weixin.properties**的资源文件放在自己工程中的classpath下.

| 属性名       	|       说明      |
| :---------- 	| :-------------- |
| account    	| 微信公众号信息 `json格式`  |
| token_path  	| 使用FileTokenHolder时token保存的物理路径 |
| qr_path     	| 调用二维码接口时保存二维码图片的物理路径 |
| media_path  	| 调用媒体接口时保存媒体文件的物理路径 |
| bill_path   	| 调用下载对账单接口保存excel文件的物理路径 |
| ca_file     	| 调用某些接口(支付相关)强制需要auth的ca授权文件 |
| redirect_uri     | 调用OauthApi接口时需要填写的重定向路径 |

示例(properties中换行用右斜杆\\)

	account={"id":"appId","secret":"appSecret",\
		"token":"开放者的token",\
		"encodingAesKey":"公众号设置了加密方式且为「安全模式」时需要填入",\
		"mchId":"V3.x版本下的微信商户号",\
		"partnerId":"财付通的商户号",\
		"partnerKey":"财付通商户权限密钥Key",\
		"version":"针对微信支付的版本号(2,3),如果不填则按照mchId非空与否来判断",\
		"paySignKey":"微信支付中调用API的密钥"}
	
	token_path=/tmp/weixin/token
	qr_path=/tmp/weixin/qr
	media_path=/tmp/weixin/media
	bill_path=/tmp/weixin/bill
	# ca证书存放的完整路径 (V2版本后缀为*.pfx,V3版本后缀为*.p12)
	ca_file=/tmp/weixin/xxxxx.p12
	#classpath路径下:ca_file=classpath:xxxxx.p12
	
	#微信登陆授权的重定向路径
	redirect_uri=http://xxx

3.在项目根目录下执行`mvn package -Prelease`命令后得到jar包,将`weixin4j-mp-full`包或者`weixin4j-base`和`weixin4j-mp-api`两个包引入到自己的工程.

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(appid,appsecret);
    // weixinProxy = new WeixinProxy(weixinAccount);
    weixinProxy.getUser(openId);

4.如需使用netty服务,则可以在相应的action中实现自己的业务处理,打包后放到`正确的目录`下解压`weixin-*-server-bin.zip`执行`sh startup.sh start`便可启动服务.

	@ActionAnnotation(msgType = MessageType.text)
	public class TextAction extends AbstractAction<TextMessage> {
	
		@Override
		public ResponseMessage execute(TextMessage inMessage) {
			return new ResponseMessage(new Text("Hello World!"), inMessage);
		}
	}

更新LOG
-------
* 2014-10-27

  + 用netty构建http服务器&消息分发

* 2014-10-28
   
  + 调整`ActionMapping`抽象化
   
* 2014-10-31

  + `weixin.properties`切分为API调用地址和公众号appid等信息两部分
   
* 2014-11-03

  + `weixin-mp`分离为`weixin4j-mp-api`和`weixin4j-mp-server`两个工程
   
  + **weixin4j-mp**: 新增`支付`模块

* 2014-11-06
  
  + **weixin4j-mp-api**: 新增V3版本`退款接口`

* 2014-11-08
 
  + **weixin4j-mp-api**: 新增V2版本`退款申请`、`退款查询`、`对账单下载`三个接口
  
  + **weixin4j-mp-api**: 新增一个简单的`语义理解`接口

* 2014-11-11

  + **weixin4j-mp-api**: 自定义`assembly`将`weixin4j-base`工程也一起打包(`weixin4j-mp-api-full.jar`)
 
* 2014-11-15

  + **weixin4j-mp-api**: 新增获取`微信服务器IP地址接口`
  
  + **weixin4j-mp-server**: 解决`server工程`打包后不能运行问题(`ClassUtil`无法获取jar包里面的类)
  
  + **weixin4j-mp-server**: 新增被动消息的`加密`以及回复消息的`解密`
  
* 2014-11-16

  + **weixin4j-mp-api**: 新增`多客服`接口
  
* 2014-11-17

  + **weixin4j-mp-api**: 新增`冲正`和`被扫支付`接口
  
* 2014-11-23

  + **weixin4j-mp-api**: 重新定义(手贱)了「被动消息」「客服消息」「群发消息」的传输实体
  
  + **weixin4j-mp-server**: `WeixinServerBootstrap`重命名为`WeixinMpServerBootstrap`
  
* 2014-12-12

  + **weixin4j-mp-api**: 新增设置`模板消息所处行业`、`获取模板消息ID`接口
  
* 2014-12-16

  + **weixin4j-mp-api**: 调整方法上@see注解的文档说明接口url
  
  + **weixin4j-mp-api**: 新增群发消息预览、状态查询接口
  
  + **weixin4j-mp-api**: 新增多客服添加账号、更新账号、上传头像、删除账号接口
  
* 2015-01-04
  
  + **weixin4j-mp-api**: 支付模块拆分为V2跟V3,新增WeixinPayProxy类
  
  + **weixin4j-mp-api**: 退款相关类拆分为V2跟V3
  
  + **weixin4j-mp-api**: 新增接口上报接口
  
* 2015-01-31
  
  + **weixin4j-mp-api**: 新增数据分析接口
  
* 2015-03-06
  
  + **weixin4j-mp-api**: 新增oauth授权接口
  
* 2015-03-21

  + **weixin-mp-api**: 新增群发消息给所有人接口
  
  + **weixin-mp-api**: 新增素材管理多个接口
  
  + **weixin-mp-api**: 新增多客服会话管理多个接口