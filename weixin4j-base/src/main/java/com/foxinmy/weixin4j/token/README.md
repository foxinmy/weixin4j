### TOKEN的实现

* TokenCreator 负责创建新的token

* TokenStorager 负责查找已缓存的token或者缓存新的token

* TokenHolder 负责获取token(屏蔽了获取细节)

* FileTokenStorager 是系统默认的token存储策略实现

* RedisTokenStorager 使用redis保存token(需要自行添加客户端包,[jedis](https://github.com/xetorthio/jedis))

* MemcacheTokenStorager 使用memcache保存token(需要自行添加客户端包,[Memcached-Java-Client](https://github.com/gwhalin/Memcached-Java-Client))
