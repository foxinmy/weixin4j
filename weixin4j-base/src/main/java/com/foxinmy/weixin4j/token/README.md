### TOKEN的实现

* TokenCreator 负责创建新的token

* TokenStorager 负责查找已缓存的token或者缓存新的token

* TokenHolder 负责获取token(屏蔽了获取方式)

* FileTokenStorager 是系统默认的token存储策略实现

* RedisTokenStorager 如果服务器支持redis,推荐使用(需要自己添加jar包和[java类](https://github.com/foxinmy/weixin4j/wiki/%E7%94%A8redis%E4%BF%9D%E5%AD%98token))
