package com.wxw.controller;

import com.wxw.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/8
 */
@Slf4j
@RestController
@RequestMapping("/retry")
public class RetryController {

    @Resource
    private PayService payService;

    /**
     * 异常重试-- 重试失败后 抛出异常
     * @url curl -G -d "num=-1" http://localhost:8082/retry/createOrder
     * @param num
     * @return
     * @throws Exception
     */
    @GetMapping("/createOrder")
    public String createOrder01(@RequestParam int num) throws Exception{
        int remainingnum = payService.minGoodsNum(num == 0 ? 1: num);
        log.info("剩余的数量==="+remainingnum);
        return "库库存成功";
    }

}
