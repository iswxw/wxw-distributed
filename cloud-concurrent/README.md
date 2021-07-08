### 并发编程实战



### 1. Redis分布式锁

### 2. Zookeeper 分布式锁

### 3. Redis 布隆过滤器

### 4. Redis 限流

### 5. Redis 幂等性

**相关文章**

1. [Token保证幂等实现](https://www.oschina.net/question/3089641_2320565?sort=default) 
2. [接口幂等性解决方案](https://github.com/GitHubWxw/wxw-document/tree/master/%E9%A1%B9%E7%9B%AE%E5%AE%9E%E8%B7%B5/%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86/%E5%B9%82%E7%AD%89%E6%80%A7) 



### 6. 重试机制

#### 6.1 注解说明

```bash
## 被注解的方法发生异常时会重试
@Retryable注解
 - value:指定发生的异常进行重试
 - include:和value一样，默认空，当exclude也为空时，所有异常都重试
 - exclude:指定异常不重试，默认空，当include也为空时，所有异常都重试
 - maxAttemps:重试次数，默认3
 - backoff:重试补偿机制，默认没有

@Backoff 注解说明
 - delay:指定延迟后重试
 - multiplier:指定延迟的倍数，比如delay=2000,multiplier=1.5时，
     - 第二次重试与第一次执行间隔：2秒；
     - 第三次重试与第二次重试间隔：3秒；
     - 第四次重试与第三次重试间隔：4.5秒。。。

@Recover
  - 当重试到达指定次数时，被注解的方法将被回调，可以在该方法中进行日志处理。需要注意的是发生的异常和入参类型一致时才会回调

```



相关文章

1. [springboot 整合retry（重试机制）](https://www.jianshu.com/p/314059943f1c) 
2. https://github.com/spring-projects/spring-retry 



更多内容福利：[快速访问](https://wwxw.gitee.io/wxw-document) 