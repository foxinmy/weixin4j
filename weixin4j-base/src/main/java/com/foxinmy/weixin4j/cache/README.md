### CACHE的实现

* CacheCreator 负责创建新的缓存对象

* CacheStorager 负责查找已缓存的对象或者缓存新的对象

* TokenManager 负责对缓存对象的管理(屏蔽细节)

* FileCacheStorager 是系统默认的缓存存储策略实现

* Redis(Cluster)CacheStorager 使用redis保存缓存对象(需要自行添加客户端包,[jedis](https://github.com/xetorthio/jedis))

* MemcacheCacheStorager 使用memcache保存缓存对象(需要自行添加客户端包,[Memcached-Java-Client](https://github.com/gwhalin/Memcached-Java-Client))
