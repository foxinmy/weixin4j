weixin4j
========

tencent weixin platform java sdk 微信公众平台接口开发工具包

如何使用
--------

1.编辑config.properties文件,填入appid/appsecret信息,当然也可以通过构造函数的方式传入.

2.实例化一个WeixinProxy对象,默认appid/appsecret采用config.properties文件中填入的值,也可外部传入.

    WeixinProxy weixinProxy = new WeixinProxy();
    // weixinProxy = new WeixinProxy(appid,appsecret);
    weixinProxy.getUser(openId);
    
3.针对token存储有两种方案,File存储/Redis存储,默认采用文件(xml)的方式保存token,如果环境中支持redis,建议使用RedisTokenApi.

    WeixinProxy weixinProxy = new WeixinProxy(new RedisTokenApi());
    // weixinProxy = new WeixinProxy(new RedisTokenApi(appid,appsecret));

注意事项
--------
> 为了避免引入到工程造成config.properties配置文件的冲突

> 暂且把其放在WeixinProxy类同一目录下,package时会一起打入jar包

> 如果不想使用这种方式可以去掉pom.xml的resources节点最后一个子节点

> 并修改src/main/java/com/foxinmy/weixin4j/util/WeixinConfig类相关代码以便正确获取api的uri.
