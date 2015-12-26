### TOKEN的实现

* TokenCreator 负责创建新的token

* TokenStorager 负责查找已缓存的token或者缓存新的token

* TokenHolder 负责获取token(屏蔽了获取细节)

* FileTokenStorager 是系统默认的token存储策略实现

* RedisTokenStorager 如果服务器支持redis,推荐使用(需要自己添加jedis包)
