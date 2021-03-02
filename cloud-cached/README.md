### 缓存问题处理



### 1. Spring与Redis 缓存

> 常用注解

| 名称           | 解释                                                         |
| -------------- | ------------------------------------------------------------ |
| Cache          | 缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等 |
| CacheManager   | 缓存管理器，管理各种缓存（cache）组件                        |
| @Cacheable     | 将方法参数结果缓存起来，下一次方法执行参数相同时，将不执行方法，返回缓存中的结果 |
| @CacheEvict    | 清空指定缓存，allEntries：是否清空所有缓存内容，缺省为 false，如果指定为 true |
| @CachePut      | 标记该注解的方法总会执行，根据注解的配置将结果缓存，常用于更新 |
| @EnableCaching | 开启基于注解的缓存，一般放在启动类上                         |
| keyGenerator   | 缓存数据时key生成策略                                        |
| serialize      | 缓存数据时value序列化策略                                    |
| @CacheConfig   | 统一配置本类的缓存注解的属性,，@CacheConfig(cacheNames = "user"),代表该类下的方法均使用这个cacheName |
| @Caching       | 可以指定相同类型的多个缓存注解，例如根据不同的条件           |

> **@Cacheable/@CachePut/@CacheEvict 主要的参数** 

| 名称                           | 解释                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| value                          | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个 例如： @Cacheable(value=”mycache”) 或者 @Cacheable(value={”cache1”,”cache2”} |
| key                            | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写， 如果不指定，则缺省按照方法的所有参数进行组合 例如： @Cacheable(value=”testcache”,key=”#id”) |
| condition                      | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false， 只有为 true 才进行缓存/清除缓存 例如：@Cacheable(value=”testcache”,condition=”#userName.length()>2”) |
| unless                         | 否定缓存。当条件结果为TRUE时，就不会缓存。 @Cacheable(value=”testcache”,unless=”#userName.length()>2”) |
| allEntries (@CacheEvict )      | 是否清空所有缓存内容，缺省为 false，如果指定为 true， 则方法调用后将立即清空所有缓存 例如： @CachEvict(value=”testcache”,allEntries=true) |
| beforeInvocation (@CacheEvict) | 是否在方法执行前就清空，缺省为 false，如果指定为 true， 则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法 执行抛出异常，则不会清空缓存 例如： @CachEvict(value=”testcache”，beforeInvocation=true) |

相关文章

1. [Spring Cache 结合Redis 缓存](http://www.macrozheng.com/#/reference/spring_data_redis) 
2. [史上最全面的Spring-Boot-Cache使用与整合](https://www.cnblogs.com/xiang--liu/p/9720344.html) 

### 2. Spring 与 MyBatis 缓存

MyBatis 内置了一个强大的事务性查询缓存机制，它可以非常方便地配置和定制。默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存。

- 官方文档：https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#cache

#### 2.1 缓存的效果

- 映射语句文件中的所有 select 语句的结果将会被缓存。
- 映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。
- 缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。
- 缓存不会定时进行刷新（也就是说，没有刷新间隔）。
- 缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
- 缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。

提示：

1.  缓存只作用于 cache 标签所在的映射文件中的语句。如果你混合使用 Java API 和 XML 映射文件，在共用接口中的语句将不会被默认缓存。你需要使用 @CacheNamespaceRef 注解指定缓存作用域。
2. 二级缓存是事务性的。这意味着，当 SqlSession 完成并提交或是完成并回滚但没有执行 flushCache=true 的 insert/delete/update 语句时，缓存会获得更新。

2.2 测试准备



相关文章

1. [Mybatis深入源码分析之基于装饰模式纯手写一级，二级，三级缓存](https://cloud.tencent.com/developer/article/1457941) 
2. [Mybatis深入源码分析之SQLSession一级缓存原理分析](https://my.oschina.net/u/3995125/blog/3079788) 
3. [Mybatis深入源码分析之SqlSessionFactory二级缓存原理分析](https://my.oschina.net/u/3995125/blog/3079857) 
4. [两种方式（xml+代码）构建SqlSessionFactory+完整实现](https://www.cnblogs.com/weibanggang/p/10117596.html) 

### 3. Spring 与 Session 缓存

### 4.  本地缓存