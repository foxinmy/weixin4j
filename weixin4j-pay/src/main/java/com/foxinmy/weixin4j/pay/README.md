支付模块【JSAPI】【NATIVE】【MICROPAY】

目前只支持商户平台v2版本的支付API

**V3版在计划中，但目前因V3版针对的电商平台、微信支付分两类商户目前都是邀请制的，所以具体什么时候开展还未知。有能力的朋友可以帮忙贡献代码**


[WeixinPayProxy](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-pay/src/main/java/com/foxinmy/weixin4j/pay/WeixinPayProxy.java)
-------------------------
这是所有支付平台所有API的代理类，入参是一个WeixinPayAccount，建议调用API都通过代理类去调用，会相对简单些。



[PayApi](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-pay/src/main/java/com/foxinmy/weixin4j/pay/api/PayApi.java)
-------------------------
微信支付API

[CashApi](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-pay/src/main/java/com/foxinmy/weixin4j/pay/api/CashApi.java)
-------------------------
微信现金红包API

[CouponApi](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-pay/src/main/java/com/foxinmy/weixin4j/pay/api/CouponApi.java)
-------------------------
微信代金券API

[ProfitSharingApi](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-pay/src/main/java/com/foxinmy/weixin4j/pay/api/ProfitSharingApi.java)
-------------------------
微信分帐API


