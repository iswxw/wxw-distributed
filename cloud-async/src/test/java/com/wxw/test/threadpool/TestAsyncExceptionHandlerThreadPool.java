package com.wxw.test.threadpool;

import com.wxw.TestAsync;
import com.wxw.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/6
 */
@Slf4j
public class TestAsyncExceptionHandlerThreadPool extends TestAsync {

    @Resource
    private AsyncService asyncService;

    @Test
    public void test_threadpool_exception_handler() {
        asyncService.asyncMethodWithVoidReturnType();
    }

}
