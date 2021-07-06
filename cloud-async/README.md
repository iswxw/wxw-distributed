### cloud-async





### 前言

通过异步可以先释放容器分配给请求的线程与相关资源，减轻系统负担，释放了容器所分配线程的请求，其响应将被延后，可以在耗时处理完成（例如长时间的运算）时再对客户端进行响应。

**一句话** 

增加了服务器对客户端请求的吞吐量（实际生产上我们用的比较少，如果并发请求量很大的情况下，我们会通过nginx把请求负载到集群服务的各个节点上来分摊请求压力，当然还可以通过消息队列来做请求的缓冲）

> Spring异步线程池的接口类，其实质是java.util.concurrent.Executor

Spring 已经实现的异常线程池：
1. SimpleAsyncTaskExecutor：不是真的线程池，这个类不重用线程，每次调用都会创建一个新的线程。
2. SyncTaskExecutor：这个类没有实现异步调用，只是一个同步操作。只适用于不需要多线程的地方
3. ConcurrentTaskExecutor：Executor的适配类，不推荐使用。如果ThreadPoolTaskExecutor不满足要求时，才用考虑使用这个类
4. SimpleThreadPoolTaskExecutor：是Quartz的SimpleThreadPool的类。线程池同时被quartz和非quartz使用，才需要使用此类
5. ThreadPoolTaskExecutor ：最常使用，推荐。 其实质是对java.util.concurrent.ThreadPoolExecutor的包装

### 使用

#### 1. 线程池包含异常处理

```java
package com.wxw.manager.pools.threadpool_exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @author weixiaowei
 * @desc: 注意：该线程池被所有的异步任务共享，而不属于某一个异步任务
 * @date: 2021/7/5
 */
@Slf4j
@Component
public class MyAsyncConfigurer implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(1);
        threadPool.setMaxPoolSize(2);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setThreadNamePrefix("MyAsync-");
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

}

/**
 * 自定义异常处理类
 *
 * @author hry
 */
@Slf4j
class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.info("Exception message - " + throwable.getMessage());
        log.info("Method name - " + method.getName());
        for (Object param : obj) {
            log.info("Parameter value - " + param);
        }
    }
}
```

> 测试类



相关文章

1. [SpringBoot 线程池配置 实现AsyncConfigurer接口方法](https://www.cnblogs.com/sbj-dawn/p/9075573.html)  

### 实践

#### 1. 异步请求

> 异步请求概述

- Servlet方式实现异步请求
- 直接返回的参数包裹一层callable即可，可以继承WebMvcConfigurerAdapter类来设置默认线程池和超时处理
- 在Callable外包一层，给WebAsyncTask设置一个超时回调，即可实现超时处理
- DeferredResult可以处理一些相对复杂一些的业务逻辑，最主要还是可以在另一个线程里面进行业务处理及返回，即可在两个完全不相干的线程间的通信。

##### 1.1 [Servlet方式实现异步请求](https://mp.weixin.qq.com/s?__biz=MzUzMTA2NTU2Ng==&mid=2247487551&idx=1&sn=18f64ba49f3f0f9d8be9d1fdef8857d9&scene=21#wechat_redirect)

 ```java
 @RequestMapping(value = "/email/servletReq", method = GET)
   public void servletReq (HttpServletRequest request, HttpServletResponse response) {
       AsyncContext asyncContext = request.startAsync();
       //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
       asyncContext.addListener(new AsyncListener() {
           @Override
           public void onTimeout(AsyncEvent event) throws IOException {
               System.out.println("超时了...");
               //做一些超时后的相关操作...
           }
           @Override
           public void onStartAsync(AsyncEvent event) throws IOException {
               System.out.println("线程开始");
           }
           @Override
           public void onError(AsyncEvent event) throws IOException {
               System.out.println("发生错误："+event.getThrowable());
           }
           @Override
           public void onComplete(AsyncEvent event) throws IOException {
               System.out.println("执行完成");
               //这里可以做一些清理资源的操作...
           }
       });
       //设置超时时间
       asyncContext.setTimeout(20000);
       asyncContext.start(new Runnable() {
           @Override
           public void run() {
               try {
                   Thread.sleep(10000);
                   System.out.println("内部线程：" + Thread.currentThread().getName());
                   asyncContext.getResponse().setCharacterEncoding("utf-8");
                   asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                   asyncContext.getResponse().getWriter().println("这是异步的请求返回");
               } catch (Exception e) {
                   System.out.println("异常："+e);
               }
               //异步请求完成通知
               //此时整个请求才完成
               asyncContext.complete();
           }
       });
       //此时之类 request的线程连接已经释放了
       System.out.println("主线程：" + Thread.currentThread().getName());
   }
 ```

#### 2. 异步调用

除了异步请求，一般上我们用的比较多的应该是异步调用。通常在开发过程中，会遇到一个方法是和实际业务无关的，没有紧密性的。比如记录日志信息等业务。这个时候正常就是启一个新线程去做一些业务处理，让主线程异步的执行其他业务。

> 使用方式

- 需要在启动类加入@EnableAsync使异步调用@Async注解生效

  - 在启动类上可以直接使用` @EnableAsync ` 注解，使` @Async ` 注解生效
  - 在其它配置类需要加`@Configuration` 注解，使` @Async ` 注解生效

  ```java
  @EnableAsync
  @Configuration
  public class AppStartConfig {
  }
  ```

- 在需要异步执行的方法上加入此注解即可@Async("threadPool")，threadPool为自定义线程池。

##### 2.1 注意事项

在默认情况下，未设置TaskExecutor时，默认是使用SimpleAsyncTaskExecutor这个线程池，但此线程不是真正意义上的线程池，因为线程不重用，每次调用都会创建一个新的线程。可通过控制台日志输出可以看出，每次输出线程名都是递增的。所以最好我们来自定义一个线程池。

调用的异步方法，不能为同一个类的方法（包括同一个类的内部类），简单来说，因为Spring在启动扫描时会为其创建一个代理类，而同类调用时，还是调用本身的代理类的，所以和平常调用是一样的。

其他的注解如@Cache等也是一样的道理，说白了，就是Spring的代理机制造成的。所以在开发中，最好把异步服务单独抽出一个类来管理

##### 2.2 什么情况下会导致@Async异步方法会失效？

- 调用同一个类下注有@Async异步方法：

- 在spring中像@Async和@Transactional、cache等注解本质使用的是动态代理，其实Spring容器在初始化的时候Spring容器会将含有AOP注解的类对象“替换”为代理对象（简单这么理解），那么注解失效的原因就很明显了，就是因为调用方法的是对象本身而不是代理对象，因为没有经过Spring容器，那么解决方法也会沿着这个思路来解决。【[参考](https://blog.csdn.net/u011277123/article/details/85250012)】

- 调用的是静态(static )方法

- 调用(private)私有化方法

> 解决思路



#### 3. 异步请求和异步调用的区别

> 两者的使用场景不同

1. 异步请求用来解决并发请求对服务器造成的压力，从而提高对请求的吞吐量，异步调用是用来做一些非主线流程且不需要实时计算和响应的任务，比如同步日志到kafka中做日志分析等

2. 异步请求是会一直等待response相应的，需要返回结果给客户端的；而异步调用我们往往会马上返回给客户端响应，完成这次整个的请求，至于异步调用的任务后台自己慢慢跑就行，客户端不会关心。

相关文章

1. [ 异步请求和异步调用](https://mp.weixin.qq.com/s?__biz=MzUzMTA2NTU2Ng==&mid=2247510922&idx=2&sn=d9afaea38f4bbabf6d2d7ef24604ec4e&chksm=fa4ad43bcd3d5d2d88b113c5e68eafa6f6581817b3f8af5d13abceaaa954f3dee6558adfd106&scene=27#wechat_redirect)  
2. [SpringBoot异步处理任务](https://blog.csdn.net/weixin_39800144/article/details/79046237) 