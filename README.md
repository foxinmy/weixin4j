weixin4j
========

tencent weixin platform java sdk 微信公众平台接口开发工具包

如何使用
--------

1.进入src/main/java/com/foxinmy/weixin4j目录编辑config.properties文件,把你的app信息填入.</br>

2.实例化一个weixinproxy对象,调用你想要的接口,例如:

    WeixinProxy weixinProxy = new WeixinProxy();
    weixinProxy.getToken();


注意事项
--------
> 为了避免引入到工程造成config.properties配置文件的冲突

> 暂且把其放在WeixinProxy类同一目录下,package时会一起打入jar包

> 如果不想使用这种方式可以去掉pom.xml的resources节点最后一个子节点

> 并修改src/main/java/com/foxinmy/weixin4j/util/WeixinConfig类相关代码以便正确加载配置文件.
