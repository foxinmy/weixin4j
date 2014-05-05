weixin4j
========

tencent weixin platform java sdk 微信公众平台接口开发工具包

如何使用
--------

1.进入src/main/resources目录编辑config.properties文件,把你的app信息填入.</br>

2.实例化一个weixinproxy对象,调用你想要的接口,例如:</br>
                WeixinProxy weixinProxy = new WeixinProxy();
                weixinProxy.getToken();


注意事项
--------
> config.properties会随着一起打进jar包,为避免与其它项目中的配置文件造成冲突,可考虑将config.properties放在与WeixinProxy同一目> 录下,或者打包时排除在外,另拷贝一份至classpath下以便正确加载.
