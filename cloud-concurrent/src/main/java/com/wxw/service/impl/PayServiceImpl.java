package com.wxw.service.impl;

import com.wxw.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/8
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private final int totalNum = 100000;

    /**
     * 在minGoodsnum方法上加上@Retryable注解，
     *     - value值表示当哪些异常的时候触发重试，
     *     - maxAttempts表示最大重试次数默认为3，
     *     - delay表示重试的延迟时间，
     *     - multiplier表示上一次延时时间是这一次的倍数。
     * @param num
     * @return
     * @throws Exception
     */
    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    @Override
    public int minGoodsNum(int num) throws Exception{
        log.info("minGoodsNum开始"+ LocalTime.now());
        if(num <= 0){
            throw new Exception("数量不对");
        }
        log.info("minGoodsNum执行结束");
        return totalNum - num;
    }

    /**
     * 重试失败自动捕捉降级处理
     * @param e
     * @return
     */
    @Recover
    public int recover(Exception e) {
        log.info("回调方法执行，重试失败自动捕捉降级处理，可以记录日志到数据库！！！！");
        //记日志到数据库 或者调用其余的方法
        return 400;
    }
}
