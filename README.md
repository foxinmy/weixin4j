weixin4j
========

tencent weixin platform java sdk 微信公众平台接口开发工具包

如何使用
--------

1.进入src/main/resources目录编辑config.properties文件,把你的app信息填入.</br>

2.实例化一个weixinproxy对象,调用你想要的接口,例如:
    WeixinProxy weixinProxy = new WeixinProxy();
    weixinProxy.getToken();


注意事项
--------
