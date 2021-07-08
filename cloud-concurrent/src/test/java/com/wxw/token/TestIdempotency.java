package com.wxw.token;

import com.wxw.manager.Idempotency.TokenUtilService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 16:32 2020/12/23
 * @description：幂等性测试
 * @link:
 * @version: v_0.0.1
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIdempotency {

    @Resource
    private TokenUtilService tokenUtilService;

    // 用户值
    private static String userInfo = "wxw";

    /**
     * 生成token
     */
    @Test
    public void testIdempotency(){
        String generateToken = tokenUtilService.generateToken(userInfo);
        log.info("generateToken = {}", generateToken);
    }

    /**
     * 验证token
     */
    @Test
    public void testUserInfo(){
        boolean validToken = tokenUtilService.validToken("290aa479-e410-4188-b6bc-8b2f306f8c58", userInfo);
        log.info("validToken = {}",validToken);
    }

}
