### TOKEN的实现

##### TokenHolder.java token持有者接口
##### FileTokenHolder.java 基于文件保存的TokenHolder实现
##### RedisTokenHolder.java 基于redis保存的TokenHolder实现(推荐)


##### TokenCreator.java token创建者接口
##### WeixinTokenCreator.java 微信公众平台及企业号access_token的获取
##### WeixinJSTicketCreator.java 微信js接口的jsapi_ticket的获取