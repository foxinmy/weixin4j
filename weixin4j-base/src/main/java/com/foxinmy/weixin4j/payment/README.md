支付模块【JSAPI】【NATIVE】【MICROPAY】

微信公众平台[V2版本支付](https://mp.weixin.qq.com/paymch/readtemplate?t=mp/business/course2_tmpl&lang=zh_CN)文档

微信公众平台[V3版本支付](https://mp.weixin.qq.com/paymch/readtemplate?t=mp/business/course3_tmpl&lang=zh_CN)文档

**在`2014年10月9号`之前申请并审核通过的支付接口应该属于`V2版本`支付,而之后申请的接口则为`V3版本(商户平台)`支付**


[WeixinPayProxy](WeixinPayProxy.java)
-------------------------

* createPayJsRequestJson: 创建V3版本(商户平台)的JSAPI支付串

* createNativePayRequestURL: 创建V3版本(商户平台)的扫码支付链接

* createPrePay: 调用V3版本(商户平台)的统一订单接口生成预订单数据

* createMicroPay: 创建刷卡支付(商户平台)请求

* orderQuery: 订单查询接口

* refundOrder: 退款申请接口

* reverseOrder: 冲正订单接口

* closeOrder: 关闭订单接口

* downloadBill: 下载对账单接口

* refundQuery: 退款查询接口


[Pay2Api](https://github.com/foxinmy/weixin4j/blob/master/weixin4j-mp/src/main/java/com/foxinmy/weixin4j/mp/api/Pay2Api.java)
-------------------------

* createPayJsRequestJson: 创建V2版本的JSAPI支付串

* createNativePayRequestURL: 创建V2版本的扫码支付链接

* orderQuery: 订单查询接口

* refundOrder: 退款申请接口

* downloadBill: 下载对账单接口

* refundQuery: 退款查询接口
